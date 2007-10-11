/* From Linux 2.4.0 drivers/sound/esssolo1.c */
static int solo1_midi_release(struct inode *inode, struct file *file)
{
	struct solo1_state *s = (struct solo1_state *)file->private_data;
	DECLARE_WAITQUEUE(wait, current);
	unsigned long flags;
	unsigned count, tmo;

	VALIDATE_STATE(s);

	lock_kernel();
	if (file->f_mode & FMODE_WRITE) {
		add_wait_queue(&s->midi.owait, &wait);
		for (;;) {
			__set_current_state(TASK_INTERRUPTIBLE);
			spin_lock_irqsave(&s->lock, flags);
			count = s->midi.ocnt;
			spin_unlock_irqrestore(&s->lock, flags);
			if (count <= 0)
				break;
			if (signal_pending(current))
				break;
			if (file->f_flags & O_NONBLOCK) {
				remove_wait_queue(&s->midi.owait, &wait);
				set_current_state(TASK_RUNNING);
				return -EBUSY;
			}
			tmo = (count * HZ) / 3100;
			if (!schedule_timeout(tmo ? : 1) && tmo)
				printk("solo1: midi timed out??\n");
		}
		remove_wait_queue(&s->midi.owait, &wait);
		set_current_state(TASK_RUNNING);
	}
	down(&s->open_sem);
	s->open_mode &= (~(file->f_mode << FMODE_MIDI_SHIFT)) & (FMODE_MIDI_READ|FMODE_MIDI_WRITE);
	spin_lock_irqsave(&s->lock, flags);
	if (!(s->open_mode & (FMODE_MIDI_READ | FMODE_MIDI_WRITE))) {
		outb(0x30, s->iobase + 7);
		del_timer(&s->midi.timer);
	}
	spin_unlock_irqrestore(&s->lock, flags);
	wake_up(&s->open_wait);
	up(&s->open_sem);
	unlock_kernel();
	return 0;
}