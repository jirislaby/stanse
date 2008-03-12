/* From Linux 2.4.1 fs/coda/psdev.c */
static int coda_psdev_open(struct inode * inode, struct file * file)
{
    struct venus_comm *vcp;
    int idx;
    ENTRY;

    lock_kernel();
    idx = MINOR(inode->i_rdev);
    if(idx >= MAX_CODADEVS)
        return -ENODEV;

    vcp = &coda_comms[idx];
    if(vcp->vc_inuse)
        return -EBUSY;

    if (!vcp->vc_inuse++) {
        INIT_LIST_HEAD(&vcp->vc_pending);
        INIT_LIST_HEAD(&vcp->vc_processing);
        init_waitqueue_head(&vcp->vc_waitq);
        vcp->vc_sb = 0;
        vcp->vc_seq = 0;
    }

    file->private_data = vcp;

    CDEBUG(D_PSDEV, "device %i - inuse: %d\n", idx, vcp->vc_inuse);

    EXIT;
    unlock_kernel();
    return 0;
}