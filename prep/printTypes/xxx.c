typedef unsigned int size_t;
struct aaa;
struct sysinfo {
 long uptime;
 unsigned long loads[3];
 unsigned long totalram;
 unsigned long freeram;
 unsigned long sharedram;
 unsigned long bufferram;
 unsigned long totalswap;
 unsigned long freeswap;
 unsigned short procs;
 char _f[22];
};
typedef unsigned short umode_t;






typedef __signed__ char __s8;
typedef unsigned char __u8;

typedef __signed__ short __s16;
typedef unsigned short __u16;

typedef __signed__ int __s32;
typedef unsigned int __u32;
__extension__ typedef __signed__ long long __s64;


__extension__ typedef unsigned long long __u64;
static inline int generic_ffs(int x)
{
 int r = 1;

 if (!x)
  return 0;
 if (!(x & 0xffff)) {
  x >>= 16;
  r += 16;
 }
 if (!(x & 0xff)) {
  x >>= 8;
  r += 8;
 }
 if (!(x & 0xf)) {
  x >>= 4;
  r += 4;
 }
 if (!(x & 3)) {
  x >>= 2;
  r += 2;
 }
 if (!(x & 1)) {
  x >>= 1;
  r += 1;
 }
 return r;
}





static __inline__ int generic_fls(int x)
{
 int r = 32;

 if (!x)
  return 0;
 if (!(x & 0xffff0000u)) {
  x <<= 16;
  r -= 16;
 }
 if (!(x & 0xff000000u)) {
  x <<= 8;
  r -= 8;
 }
 if (!(x & 0xf0000000u)) {
  x <<= 4;
  r -= 4;
 }
 if (!(x & 0xc0000000u)) {
  x <<= 2;
  r -= 2;
 }
 if (!(x & 0x80000000u)) {
  x <<= 1;
  r -= 1;
 }
 return r;
}
static __inline__ void set_bit(int nr, volatile void * addr)
{
 __asm__ __volatile__( "lock ; btsl %1,%0" :"=m" ((*(volatile long *) addr))  :"dIr" (nr) : "memory");
}
static __inline__ void __set_bit(int nr, volatile void * addr)
{
 __asm__ volatile(
  "btsl %1,%0"
  :"=m" ((*(volatile long *) addr))
  :"dIr" (nr) : "memory");
}
static __inline__ void clear_bit(int nr, volatile void * addr)
{
 __asm__ __volatile__( "lock ; "
  "btrl %1,%0"
  :"=m" ((*(volatile long *) addr))
  :"dIr" (nr));
}

static __inline__ void __clear_bit(int nr, volatile void * addr)
{
 __asm__ __volatile__(
  "btrl %1,%0"
  :"=m" ((*(volatile long *) addr))
  :"dIr" (nr));
}
static __inline__ void __change_bit(int nr, volatile void * addr)
{
 __asm__ __volatile__(
  "btcl %1,%0"
  :"=m" ((*(volatile long *) addr))
  :"dIr" (nr));
}
static __inline__ void change_bit(int nr, volatile void * addr)
{
 __asm__ __volatile__( "lock ; "
  "btcl %1,%0"
  :"=m" ((*(volatile long *) addr))
  :"dIr" (nr));
}
static __inline__ int test_and_set_bit(int nr, volatile void * addr)
{
 int oldbit;

 __asm__ __volatile__( "lock ; "
  "btsl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"=m" ((*(volatile long *) addr))
  :"dIr" (nr) : "memory");
 return oldbit;
}
static __inline__ int __test_and_set_bit(int nr, volatile void * addr)
{
 int oldbit;

 __asm__(
  "btsl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"=m" ((*(volatile long *) addr))
  :"dIr" (nr));
 return oldbit;
}
static __inline__ int test_and_clear_bit(int nr, volatile void * addr)
{
 int oldbit;

 __asm__ __volatile__( "lock ; "
  "btrl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"=m" ((*(volatile long *) addr))
  :"dIr" (nr) : "memory");
 return oldbit;
}
static __inline__ int __test_and_clear_bit(int nr, volatile void * addr)
{
 int oldbit;

 __asm__(
  "btrl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"=m" ((*(volatile long *) addr))
  :"dIr" (nr));
 return oldbit;
}


static __inline__ int __test_and_change_bit(int nr, volatile void * addr)
{
 int oldbit;

 __asm__ __volatile__(
  "btcl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"=m" ((*(volatile long *) addr))
  :"dIr" (nr) : "memory");
 return oldbit;
}
static __inline__ int test_and_change_bit(int nr, volatile void * addr)
{
 int oldbit;

 __asm__ __volatile__( "lock ; "
  "btcl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"=m" ((*(volatile long *) addr))
  :"dIr" (nr) : "memory");
 return oldbit;
}
static __inline__ int constant_test_bit(int nr, const volatile void * addr)
{
 return ((1UL << (nr & 31)) & (((const volatile unsigned int *) addr)[nr >> 5])) != 0;
}

static __inline__ int variable_test_bit(int nr, volatile const void * addr)
{
 int oldbit;

 __asm__ __volatile__(
  "btl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit)
  :"m" ((*(volatile long *) addr)),"dIr" (nr));
 return oldbit;
}
extern long find_first_zero_bit(const unsigned long * addr, unsigned long size);
extern long find_next_zero_bit (const unsigned long * addr, long size, long offset);
extern long find_first_bit(const unsigned long * addr, unsigned long size);
extern long find_next_bit(const unsigned long * addr, long size, long offset);


static inline unsigned long __scanbit(unsigned long val, unsigned long max)
{
 __asm__("bsfq %1,%0 ; cmovz %2,%0" : "=&r" (val) : "r" (val), "r" (max));
 return val;
}
extern unsigned long
find_next_zero_string(unsigned long *bitmap, long start, long nbits, int len);

static inline void set_bit_string(unsigned long *bitmap, unsigned long i,
      int len)
{
 unsigned long end = i + len;
 while (i < end) {
  __set_bit(i, bitmap);
  i++;
 }
}

static inline void __clear_bit_string(unsigned long *bitmap, unsigned long i,
        int len)
{
 unsigned long end = i + len;
 while (i < end) {
  __clear_bit(i, bitmap);
  i++;
 }
}







static __inline__ unsigned long ffz(unsigned long word)
{
 __asm__("bsfq %1,%0"
  :"=r" (word)
  :"r" (~word));
 return word;
}







static __inline__ unsigned long __ffs(unsigned long word)
{
 __asm__("bsfq %1,%0"
  :"=r" (word)
  :"rm" (word));
 return word;
}
static __inline__ int get_bitmask_order(unsigned int count)
{
 int order;

 order = generic_fls(count);
 return order;
}






static inline unsigned int generic_hweight32(unsigned int w)
{
        unsigned int res = (w & 0x55555555) + ((w >> 1) & 0x55555555);
        res = (res & 0x33333333) + ((res >> 2) & 0x33333333);
        res = (res & 0x0F0F0F0F) + ((res >> 4) & 0x0F0F0F0F);
        res = (res & 0x00FF00FF) + ((res >> 8) & 0x00FF00FF);
        return (res & 0x0000FFFF) + ((res >> 16) & 0x0000FFFF);
}

static inline unsigned int generic_hweight16(unsigned int w)
{
        unsigned int res = (w & 0x5555) + ((w >> 1) & 0x5555);
        res = (res & 0x3333) + ((res >> 2) & 0x3333);
        res = (res & 0x0F0F) + ((res >> 4) & 0x0F0F);
        return (res & 0x00FF) + ((res >> 8) & 0x00FF);
}

static inline unsigned int generic_hweight8(unsigned int w)
{
        unsigned int res = (w & 0x55) + ((w >> 1) & 0x55);
        res = (res & 0x33) + ((res >> 2) & 0x33);
        return (res & 0x0F) + ((res >> 4) & 0x0F);
}

static inline unsigned long generic_hweight64(__u64 w)
{

 return generic_hweight32((unsigned int)(w >> 32)) +
    generic_hweight32((unsigned int)w);
}

static inline unsigned long hweight_long(unsigned long w)
{
 return sizeof(w) == 4 ? generic_hweight32(w) : generic_hweight64(w);
}
extern inline void set_64bit(volatile unsigned long *ptr, unsigned long val)
{
 *ptr = val;
}
static inline unsigned long __xchg(unsigned long x, volatile void * ptr, int size)
{
 switch (size) {
  case 1:
   __asm__ __volatile__("xchgb %b0,%1"
    :"=q" (x)
    :"m" (*((volatile long *)(ptr))), "0" (x)
    :"memory");
   break;
  case 2:
   __asm__ __volatile__("xchgw %w0,%1"
    :"=r" (x)
    :"m" (*((volatile long *)(ptr))), "0" (x)
    :"memory");
   break;
  case 4:
   __asm__ __volatile__("xchgl %k0,%1"
    :"=r" (x)
    :"m" (*((volatile long *)(ptr))), "0" (x)
    :"memory");
   break;
  case 8:
   __asm__ __volatile__("xchgq %0,%1"
    :"=r" (x)
    :"m" (*((volatile long *)(ptr))), "0" (x)
    :"memory");
   break;
 }
 return x;
}
static inline unsigned long __cmpxchg(volatile void *ptr, unsigned long old,
          unsigned long __new, int size)
{
 unsigned long prev;
 switch (size) {
 case 1:
  __asm__ __volatile__("lock ; " "cmpxchgb %b1,%2"
         : "=a"(prev)
         : "q"(__new), "m"(*((volatile long *)(ptr))), "0"(old)
         : "memory");
  return prev;
 case 2:
  __asm__ __volatile__("lock ; " "cmpxchgw %w1,%2"
         : "=a"(prev)
         : "q"(__new), "m"(*((volatile long *)(ptr))), "0"(old)
         : "memory");
  return prev;
 case 4:
  __asm__ __volatile__("lock ; " "cmpxchgl %k1,%2"
         : "=a"(prev)
         : "q"(__new), "m"(*((volatile long *)(ptr))), "0"(old)
         : "memory");
  return prev;
 case 8:
  __asm__ __volatile__("lock ; " "cmpxchgq %1,%2"
         : "=a"(prev)
         : "q"(__new), "m"(*((volatile long *)(ptr))), "0"(old)
         : "memory");
  return prev;
 }
 return old;
}
void cpu_idle_wait(void);





void disable_hlt(void);
void enable_hlt(void);


void eat_key(void);
struct restart_block {
 long (*fn)(struct restart_block *);
 unsigned long arg0, arg1, arg2, arg3;
};

extern long do_no_restart_syscall(struct restart_block *parm);

typedef int __sig_atomic_t;




typedef struct
  {
    unsigned long int __val[(1024 / (8 * sizeof (unsigned long int)))];
  } __sigset_t;
extern int __sigismember (__const __sigset_t *, int);
extern int __sigaddset (__sigset_t *, int);
extern int __sigdelset (__sigset_t *, int);

typedef __sig_atomic_t sig_atomic_t;








typedef __sigset_t sigset_t;
typedef unsigned char __u_char;
typedef unsigned short int __u_short;
typedef unsigned int __u_int;
typedef unsigned long int __u_long;


typedef signed char __int8_t;
typedef unsigned char __uint8_t;
typedef signed short int __int16_t;
typedef unsigned short int __uint16_t;
typedef signed int __int32_t;
typedef unsigned int __uint32_t;

typedef signed long int __int64_t;
typedef unsigned long int __uint64_t;







typedef long int __quad_t;
typedef unsigned long int __u_quad_t;
typedef unsigned long int __dev_t;
typedef unsigned int __uid_t;
typedef unsigned int __gid_t;
typedef unsigned long int __ino_t;
typedef unsigned long int __ino64_t;
typedef unsigned int __mode_t;
typedef unsigned long int __nlink_t;
typedef long int __off_t;
typedef long int __off64_t;
typedef int __pid_t;
typedef struct { int __val[2]; } __fsid_t;
typedef long int __clock_t;
typedef unsigned long int __rlim_t;
typedef unsigned long int __rlim64_t;
typedef unsigned int __id_t;
typedef long int __time_t;
typedef unsigned int __useconds_t;
typedef long int __suseconds_t;

typedef int __daddr_t;
typedef long int __swblk_t;
typedef int __key_t;


typedef int __clockid_t;


typedef void * __timer_t;


typedef long int __blksize_t;




typedef long int __blkcnt_t;
typedef long int __blkcnt64_t;


typedef unsigned long int __fsblkcnt_t;
typedef unsigned long int __fsblkcnt64_t;


typedef unsigned long int __fsfilcnt_t;
typedef unsigned long int __fsfilcnt64_t;

typedef long int __ssize_t;



typedef __off64_t __loff_t;
typedef __quad_t *__qaddr_t;
typedef char *__caddr_t;


typedef long int __intptr_t;


typedef unsigned int __socklen_t;
typedef __pid_t pid_t;





typedef __uid_t uid_t;






typedef void (*__sighandler_t) (int);




extern __sighandler_t __sysv_signal (int __sig, __sighandler_t __handler)
      ;


extern __sighandler_t signal (int __sig, __sighandler_t __handler)
      ;

extern int kill (__pid_t __pid, int __sig)  ;






extern int killpg (__pid_t __pgrp, int __sig)  ;




extern int raise (int __sig)  ;




extern __sighandler_t ssignal (int __sig, __sighandler_t __handler)
      ;
extern int gsignal (int __sig)  ;




extern void psignal (int __sig, __const char *__s);
extern int __sigpause (int __sig_or_mask, int __is_sig);
extern int sigblock (int __mask)  ;


extern int sigsetmask (int __mask)  ;


extern int siggetmask (void)  ;
typedef __sighandler_t sig_t;
struct timespec
  {
    __time_t tv_sec;
    long int tv_nsec;
  };
typedef union sigval
  {
    int sival_int;
    void *sival_ptr;
  } sigval_t;
typedef struct siginfo
  {
    int si_signo;
    int si_errno;

    int si_code;

    union
      {
 int _pad[((128 / sizeof (int)) - 4)];


 struct
   {
     __pid_t si_pid;
     __uid_t si_uid;
   } _kill;


 struct
   {
     int si_tid;
     int si_overrun;
     sigval_t si_sigval;
   } _timer;


 struct
   {
     __pid_t si_pid;
     __uid_t si_uid;
     sigval_t si_sigval;
   } _rt;


 struct
   {
     __pid_t si_pid;
     __uid_t si_uid;
     int si_status;
     __clock_t si_utime;
     __clock_t si_stime;
   } _sigchld;


 struct
   {
     void *si_addr;
   } _sigfault;


 struct
   {
     long int si_band;
     int si_fd;
   } _sigpoll;
      } _sifields;
  } siginfo_t;
enum
{
  SI_ASYNCNL = -60,

  SI_TKILL = -6,

  SI_SIGIO,

  SI_ASYNCIO,

  SI_MESGQ,

  SI_TIMER,

  SI_QUEUE,

  SI_USER,

  SI_KERNEL = 0x80

};



enum
{
  ILL_ILLOPC = 1,

  ILL_ILLOPN,

  ILL_ILLADR,

  ILL_ILLTRP,

  ILL_PRVOPC,

  ILL_PRVREG,

  ILL_COPROC,

  ILL_BADSTK

};


enum
{
  FPE_INTDIV = 1,

  FPE_INTOVF,

  FPE_FLTDIV,

  FPE_FLTOVF,

  FPE_FLTUND,

  FPE_FLTRES,

  FPE_FLTINV,

  FPE_FLTSUB

};


enum
{
  SEGV_MAPERR = 1,

  SEGV_ACCERR

};


enum
{
  BUS_ADRALN = 1,

  BUS_ADRERR,

  BUS_OBJERR

};


enum
{
  TRAP_BRKPT = 1,

  TRAP_TRACE

};


enum
{
  CLD_EXITED = 1,

  CLD_KILLED,

  CLD_DUMPED,

  CLD_TRAPPED,

  CLD_STOPPED,

  CLD_CONTINUED

};


enum
{
  POLL_IN = 1,

  POLL_OUT,

  POLL_MSG,

  POLL_ERR,

  POLL_PRI,

  POLL_HUP

};
typedef struct sigevent
  {
    sigval_t sigev_value;
    int sigev_signo;
    int sigev_notify;

    union
      {
 int _pad[((64 / sizeof (int)) - 4)];



 __pid_t _tid;

 struct
   {
     void (*_function) (sigval_t);
     void *_attribute;
   } _sigev_thread;
      } _sigev_un;
  } sigevent_t;






enum
{
  SIGEV_SIGNAL = 0,

  SIGEV_NONE,

  SIGEV_THREAD,


  SIGEV_THREAD_ID = 4

};






extern int sigemptyset (sigset_t *__set)  ;


extern int sigfillset (sigset_t *__set)  ;


extern int sigaddset (sigset_t *__set, int __signo)  ;


extern int sigdelset (sigset_t *__set, int __signo)  ;


extern int sigismember (__const sigset_t *__set, int __signo)
      ;
struct sigaction
  {


    union
      {

 __sighandler_t sa_handler;

 void (*sa_sigaction) (int, siginfo_t *, void *);
      }
    __sigaction_handler;







    __sigset_t sa_mask;


    int sa_flags;


    void (*sa_restorer) (void);
  };
extern int sigprocmask (int __how, __const sigset_t *__restrict __set,
   sigset_t *__restrict __oset)  ;






extern int sigsuspend (__const sigset_t *__set)  ;


extern int sigaction (int __sig, __const struct sigaction *__restrict __act,
        struct sigaction *__restrict __oact)  ;


extern int sigpending (sigset_t *__set)  ;






extern int sigwait (__const sigset_t *__restrict __set, int *__restrict __sig)
      ;






extern int sigwaitinfo (__const sigset_t *__restrict __set,
   siginfo_t *__restrict __info)  ;






extern int sigtimedwait (__const sigset_t *__restrict __set,
    siginfo_t *__restrict __info,
    __const struct timespec *__restrict __timeout)
      ;



extern int sigqueue (__pid_t __pid, int __sig, __const union sigval __val)
      ;
extern __const char *__const _sys_siglist[65];
extern __const char *__const sys_siglist[65];


struct sigvec
  {
    __sighandler_t sv_handler;
    int sv_mask;

    int sv_flags;

  };
extern int sigvec (int __sig, __const struct sigvec *__vec,
     struct sigvec *__ovec)  ;
struct _fpreg
{
  unsigned short significand[4];
  unsigned short exponent;
};

struct _fpxreg
{
  unsigned short significand[4];
  unsigned short exponent;
  unsigned short padding[3];
};

struct _xmmreg
{
  __uint32_t element[4];
};
struct _fpstate
{

  __uint16_t cwd;
  __uint16_t swd;
  __uint16_t ftw;
  __uint16_t fop;
  __uint64_t rip;
  __uint64_t rdp;
  __uint32_t mxcsr;
  __uint32_t mxcr_mask;
  struct _fpxreg _st[8];
  struct _xmmreg _xmm[16];
  __uint32_t padding[24];
};

struct sigcontext
{
  unsigned long r8;
  unsigned long r9;
  unsigned long r10;
  unsigned long r11;
  unsigned long r12;
  unsigned long r13;
  unsigned long r14;
  unsigned long r15;
  unsigned long rdi;
  unsigned long rsi;
  unsigned long rbp;
  unsigned long rbx;
  unsigned long rdx;
  unsigned long rax;
  unsigned long rcx;
  unsigned long rsp;
  unsigned long rip;
  unsigned long eflags;
  unsigned short cs;
  unsigned short gs;
  unsigned short fs;
  unsigned short __pad0;
  unsigned long err;
  unsigned long trapno;
  unsigned long oldmask;
  unsigned long cr2;
  struct _fpstate * fpstate;
  unsigned long __reserved1 [8];
};







extern int sigreturn (struct sigcontext *__scp)  ;
extern int siginterrupt (int __sig, int __interrupt)  ;
struct sigstack
  {
    void *ss_sp;
    int ss_onstack;
  };



enum
{
  SS_ONSTACK = 1,

  SS_DISABLE

};
typedef struct sigaltstack
  {
    void *ss_sp;
    int ss_flags;
    size_t ss_size;
  } stack_t;
extern int sigstack (struct sigstack *__ss, struct sigstack *__oss)
      ;



extern int sigaltstack (__const struct sigaltstack *__restrict __ss,
   struct sigaltstack *__restrict __oss)  ;
typedef unsigned long int pthread_t;


typedef union
{
  char __size[56];
  long int __align;
} pthread_attr_t;



typedef struct __pthread_internal_list
{
  struct __pthread_internal_list *__prev;
  struct __pthread_internal_list *__next;
} __pthread_list_t;
typedef union
{
  struct __pthread_mutex_s
  {
    int __lock;
    unsigned int __count;
    int __owner;

    unsigned int __nusers;



    int __kind;

    int __spins;
    __pthread_list_t __list;
  } __data;
  char __size[40];
  long int __align;
} pthread_mutex_t;

typedef union
{
  char __size[4];
  int __align;
} pthread_mutexattr_t;




typedef union
{
  struct
  {
    int __lock;
    unsigned int __futex;
    __extension__ unsigned long long int __total_seq;
    __extension__ unsigned long long int __wakeup_seq;
    __extension__ unsigned long long int __woken_seq;
    void *__mutex;
    unsigned int __nwaiters;
    unsigned int __broadcast_seq;
  } __data;
  char __size[48];
  __extension__ long long int __align;
} pthread_cond_t;

typedef union
{
  char __size[4];
  int __align;
} pthread_condattr_t;



typedef unsigned int pthread_key_t;



typedef int pthread_once_t;





typedef union
{

  struct
  {
    int __lock;
    unsigned int __nr_readers;
    unsigned int __readers_wakeup;
    unsigned int __writer_wakeup;
    unsigned int __nr_readers_queued;
    unsigned int __nr_writers_queued;
    int __writer;
    int __pad1;
    unsigned long int __pad2;
    unsigned long int __pad3;


    unsigned int __flags;
  } __data;
  char __size[56];
  long int __align;
} pthread_rwlock_t;

typedef union
{
  char __size[8];
  long int __align;
} pthread_rwlockattr_t;





typedef __volatile__ int pthread_spinlock_t;




typedef union
{
  char __size[32];
  long int __align;
} pthread_barrier_t;

typedef union
{
  char __size[4];
  int __align;
} pthread_barrierattr_t;
extern int pthread_sigmask (int __how,
       __const __sigset_t *__restrict __newmask,
       __sigset_t *__restrict __oldmask) ;


extern int pthread_kill (pthread_t __threadid, int __signo)  ;
extern int __libc_current_sigrtmin (void)  ;

extern int __libc_current_sigrtmax (void)  ;




extern __inline__ void cpuid(int op, unsigned int *eax, unsigned int *ebx,
    unsigned int *ecx, unsigned int *edx)
{
 __asm__("cpuid"
  : "=a" (*eax),
    "=b" (*ebx),
    "=c" (*ecx),
    "=d" (*edx)
  : "0" (op));
}




extern __inline__ unsigned int cpuid_eax(unsigned int op)
{
 unsigned int eax;

 __asm__("cpuid"
  : "=a" (eax)
  : "0" (op)
  : "bx", "cx", "dx");
 return eax;
}
extern __inline__ unsigned int cpuid_ebx(unsigned int op)
{
 unsigned int eax, ebx;

 __asm__("cpuid"
  : "=a" (eax), "=b" (ebx)
  : "0" (op)
  : "cx", "dx" );
 return ebx;
}
extern __inline__ unsigned int cpuid_ecx(unsigned int op)
{
 unsigned int eax, ecx;

 __asm__("cpuid"
  : "=a" (eax), "=c" (ecx)
  : "0" (op)
  : "bx", "dx" );
 return ecx;
}
extern __inline__ unsigned int cpuid_edx(unsigned int op)
{
 unsigned int eax, edx;

 __asm__("cpuid"
  : "=a" (eax), "=d" (edx)
  : "0" (op)
  : "bx", "cx");
 return edx;
}
struct task_struct;
typedef struct {
 unsigned int fds_bits [(1024/(8 * sizeof(unsigned int)))];
} __kernel_fd_set;
typedef unsigned long __kernel_ino_t;
typedef unsigned int __kernel_mode_t;
typedef unsigned long __kernel_nlink_t;
typedef long __kernel_off_t;
typedef int __kernel_pid_t;
typedef int __kernel_ipc_pid_t;
typedef unsigned int __kernel_uid_t;
typedef unsigned int __kernel_gid_t;
typedef unsigned long __kernel_size_t;
typedef long __kernel_ssize_t;
typedef long __kernel_ptrdiff_t;
typedef long __kernel_time_t;
typedef long __kernel_suseconds_t;
typedef long __kernel_clock_t;
typedef int __kernel_timer_t;
typedef int __kernel_clockid_t;
typedef int __kernel_daddr_t;
typedef char * __kernel_caddr_t;
typedef unsigned short __kernel_uid16_t;
typedef unsigned short __kernel_gid16_t;


typedef long long __kernel_loff_t;


typedef struct {
 int val[2];
} __kernel_fsid_t;

typedef unsigned short __kernel_old_uid_t;
typedef unsigned short __kernel_old_gid_t;
typedef __kernel_uid_t __kernel_uid32_t;
typedef __kernel_gid_t __kernel_gid32_t;

typedef unsigned long __kernel_old_dev_t;
struct ustat {
 __kernel_daddr_t f_tfree;
 __kernel_ino_t f_tinode;
 char f_fname[6];
 char f_fpack[6];
};
struct x8664_pda {
 struct task_struct *pcurrent;
 unsigned long data_offset;
 struct x8664_pda *me;
 unsigned long kernelstack;
 unsigned long oldrsp;
 unsigned long irqrsp;
        int irqcount;
 int cpunumber;
 char *irqstackptr;
 unsigned int __softirq_pending;
 unsigned int __nmi_count;
 struct mm_struct *active_mm;
 int mmu_state;
 unsigned apic_timer_irqs;
} ;





extern struct x8664_pda cpu_pda[];
extern void __bad_pda_field(void);
static __inline__ struct task_struct *get_current(void)
{
 return t;
}
typedef struct {
 unsigned long seg;
} mm_segment_t;
extern void setup_per_cpu_areas(void);
struct pt_regs {
 unsigned long r15;
 unsigned long r14;
 unsigned long r13;
 unsigned long r12;
 unsigned long rbp;
 unsigned long rbx;

  unsigned long r11;
 unsigned long r10;
 unsigned long r9;
 unsigned long r8;
 unsigned long rax;
 unsigned long rcx;
 unsigned long rdx;
 unsigned long rsi;
 unsigned long rdi;
 unsigned long orig_rax;


 unsigned long rip;
 unsigned long cs;
 unsigned long eflags;
 unsigned long rsp;
 unsigned long ss;

};
typedef void (*lcall7_func)(struct pt_regs *);







struct exec_domain {
 const char *name;
 lcall7_func handler;
 unsigned char pers_low, pers_high;
 unsigned long * signal_map;
 unsigned long * signal_invmap;
 long *use_count;
 struct exec_domain *next;
};

extern struct exec_domain default_exec_domain;

extern struct exec_domain *lookup_exec_domain(unsigned long personality);
extern int register_exec_domain(struct exec_domain *it);
extern int unregister_exec_domain(struct exec_domain *it);
extern int sys_personality(unsigned long personality);
struct cpuinfo_x86 {
 __u8 x86;
 __u8 x86_vendor;
 __u8 x86_model;
 __u8 x86_mask;
 int cpuid_level;
 __u32 x86_capability[6];
 char x86_vendor_id[16];
 char x86_model_id[64];
 int x86_cache_size;
 int x86_clflush_size;
 int x86_cache_alignment;
 int x86_tlbsize;
        __u8 x86_virt_bits, x86_phys_bits;
 __u8 x86_num_cores;
 __u8 x86_apicid;
        __u32 x86_power;
 __u32 x86_cpuid_level;
 unsigned long loops_per_jiffy;
} ;
extern struct cpuinfo_x86 cpu_data[];






extern char ignore_irq13;

extern void identify_cpu(struct cpuinfo_x86 *);
extern void print_cpu_info(struct cpuinfo_x86 *);
extern unsigned int init_intel_cacheinfo(struct cpuinfo_x86 *c);
extern void dodgy_tsc(void);
extern unsigned long mmu_cr4_features;




static __inline__ void set_in_cr4 (unsigned long mask)
{
 mmu_cr4_features |= mask;
 __asm__("movq %%cr4,%%rax\n\t"
  "orq %0,%%rax\n\t"
  "movq %%rax,%%cr4\n"
  : : "irg" (mask)
  :"ax");
}

static __inline__ void clear_in_cr4 (unsigned long mask)
{
 mmu_cr4_features &= ~mask;
 __asm__("movq %%cr4,%%rax\n\t"
  "andq %0,%%rax\n\t"
  "movq %%rax,%%cr4\n"
  : : "irg" (~mask)
  :"ax");
}
struct i387_fxsave_struct {
 __u16 cwd;
 __u16 swd;
 __u16 twd;
 __u16 fop;
 __u64 rip;
 __u64 rdp;
 __u32 mxcsr;
 __u32 mxcsr_mask;
 __u32 st_space[32];
 __u32 xmm_space[64];
 __u32 padding[24];
}  ;

union i387_union {
 struct i387_fxsave_struct fxsave;
};

struct tss_struct {
 __u32 reserved1;
 __u64 rsp0;
 __u64 rsp1;
 __u64 rsp2;
 __u64 reserved2;
 __u64 ist[7];
 __u32 reserved3;
 __u32 reserved4;
 __u16 reserved5;
 __u16 io_bitmap_base;
 unsigned long io_bitmap[((65536/8)/sizeof(long)) + 1];
};

extern struct cpuinfo_x86 boot_cpu_data;
extern __typeof__(struct tss_struct) per_cpu__init_tss;



struct thread_struct {
 unsigned long rsp0;
 unsigned long rsp;
 unsigned long userrsp;
 unsigned long fs;
 unsigned long gs;
 unsigned short es, ds, fsindex, gsindex;

 unsigned long debugreg0;
 unsigned long debugreg1;
 unsigned long debugreg2;
 unsigned long debugreg3;
 unsigned long debugreg6;
 unsigned long debugreg7;

 unsigned long cr2, trap_no, error_code;

 union i387_union i387 ;


 int ioperm;
 unsigned long *io_bitmap_ptr;
 unsigned io_bitmap_max;

 __u64 tls_array[3];
} ;
struct task_struct;
struct mm_struct;


extern void release_thread(struct task_struct *);


extern void prepare_to_copy(struct task_struct *tsk);




extern long kernel_thread(int (*fn)(void *), void * arg, unsigned long flags);







extern unsigned long get_wchan(struct task_struct *p);





struct microcode_header {
 unsigned int hdrver;
 unsigned int rev;
 unsigned int date;
 unsigned int sig;
 unsigned int cksum;
 unsigned int ldrver;
 unsigned int pf;
 unsigned int datasize;
 unsigned int totalsize;
 unsigned int reserved[3];
};

struct microcode {
 struct microcode_header hdr;
 unsigned int bits[0];
};

typedef struct microcode microcode_t;
typedef struct microcode_header microcode_header_t;


struct extended_signature {
 unsigned int sig;
 unsigned int pf;
 unsigned int cksum;
};

struct extended_sigtable {
 unsigned int count;
 unsigned int cksum;
 unsigned int reserved[3];
 struct extended_signature sigs[0];
};
extern __inline__ void rep_nop(void)
{
 __asm__ __volatile__("rep;nop": : :"memory");
}


extern __inline__ void sync_core(void)
{
 int tmp;
 __asm__ __volatile__("cpuid" : "=a" (tmp) : "0" (1) : "ebx","ecx","edx","memory");
}




static __inline__ void prefetch(void *x)
{
 __asm__ __volatile__("prefetcht0 %0" :: "m" (*(unsigned long *)x));
}


static __inline__ void prefetchw(void *x)
{
 __asm__ __volatile__ ("661:\n\t" ".byte 0x66,0x66,0x90\n" ".byte 0x66,0x90\n" "\n662:\n" ".section .altinstructions,\"a\"\n" "  .align 8\n" "  .quad 661b\n" "  .quad 663f\n" "  .byte %c0\n" "  .byte 662b-661b\n" "  .byte 664f-663f\n" ".previous\n" ".section .altinstr_replacement,\"ax\"\n" "663:\n\t" "prefetchw (%1)" "\n664:\n" ".previous" :: "i" ((1*32+31)), "r" (x));



}
static __inline__ void __monitor(const void *eax, unsigned long ecx,
  unsigned long edx)
{

 __asm__ __volatile__(
  ".byte 0x0f,0x01,0xc8;"
  : :"a" (eax), "c" (ecx), "d"(edx));
}

static __inline__ void __mwait(unsigned long eax, unsigned long ecx)
{

 __asm__ __volatile__(
  ".byte 0x0f,0x01,0xc9;"
  : :"a" (eax), "c" (ecx));
}
extern unsigned long boot_option_idle_override;

extern int bootloader_type;
typedef struct { __volatile__ int counter; } atomic_t;
static __inline__ void atomic_add(int i, atomic_t *v)
{
 __asm__ __volatile__(
  "lock ; " "addl %1,%0"
  :"=m" (v->counter)
  :"ir" (i), "m" (v->counter));
}
static __inline__ void atomic_sub(int i, atomic_t *v)
{
 __asm__ __volatile__(
  "lock ; " "subl %1,%0"
  :"=m" (v->counter)
  :"ir" (i), "m" (v->counter));
}
static __inline__ int atomic_sub_and_test(int i, atomic_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "lock ; " "subl %2,%0; sete %1"
  :"=m" (v->counter), "=qm" (c)
  :"ir" (i), "m" (v->counter) : "memory");
 return c;
}







static __inline__ void atomic_inc(atomic_t *v)
{
 __asm__ __volatile__(
  "lock ; " "incl %0"
  :"=m" (v->counter)
  :"m" (v->counter));
}







static __inline__ void atomic_dec(atomic_t *v)
{
 __asm__ __volatile__(
  "lock ; " "decl %0"
  :"=m" (v->counter)
  :"m" (v->counter));
}
static __inline__ int atomic_dec_and_test(atomic_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "lock ; " "decl %0; sete %1"
  :"=m" (v->counter), "=qm" (c)
  :"m" (v->counter) : "memory");
 return c != 0;
}
static __inline__ int atomic_inc_and_test(atomic_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "lock ; " "incl %0; sete %1"
  :"=m" (v->counter), "=qm" (c)
  :"m" (v->counter) : "memory");
 return c != 0;
}
static __inline__ int atomic_add_negative(int i, atomic_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "lock ; " "addl %2,%0; sets %1"
  :"=m" (v->counter), "=qm" (c)
  :"ir" (i), "m" (v->counter) : "memory");
 return c;
}



typedef struct { __volatile__ long counter; } atomic64_t;
static __inline__ void atomic64_add(long i, atomic64_t *v)
{
 __asm__ __volatile__(
  "lock ; " "addq %1,%0"
  :"=m" (v->counter)
  :"ir" (i), "m" (v->counter));
}
static __inline__ void atomic64_sub(long i, atomic64_t *v)
{
 __asm__ __volatile__(
  "lock ; " "subq %1,%0"
  :"=m" (v->counter)
  :"ir" (i), "m" (v->counter));
}
static __inline__ int atomic64_sub_and_test(long i, atomic64_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "lock ; " "subq %2,%0; sete %1"
  :"=m" (v->counter), "=qm" (c)
  :"ir" (i), "m" (v->counter) : "memory");
 return c;
}







static __inline__ void atomic64_inc(atomic64_t *v)
{
 __asm__ __volatile__(
  "lock ; " "incq %0"
  :"=m" (v->counter)
  :"m" (v->counter));
}







static __inline__ void atomic64_dec(atomic64_t *v)
{
 __asm__ __volatile__(
  "lock ; " "decq %0"
  :"=m" (v->counter)
  :"m" (v->counter));
}
static __inline__ int atomic64_dec_and_test(atomic64_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "lock ; " "decq %0; sete %1"
  :"=m" (v->counter), "=qm" (c)
  :"m" (v->counter) : "memory");
 return c != 0;
}
static __inline__ int atomic64_inc_and_test(atomic64_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "lock ; " "incq %0; sete %1"
  :"=m" (v->counter), "=qm" (c)
  :"m" (v->counter) : "memory");
 return c != 0;
}
static __inline__ long atomic64_add_negative(long i, atomic64_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "lock ; " "addq %2,%0; sets %1"
  :"=m" (v->counter), "=qm" (c)
  :"ir" (i), "m" (v->counter) : "memory");
 return c;
}
static __inline__ int atomic_add_return(int i, atomic_t *v)
{
 int __i = i;
 __asm__ __volatile__(
  "lock ; " "xaddl %0, %1;"
  :"=r"(i)
  :"m"(v->counter), "0"(i));
 return i + __i;
}

static __inline__ int atomic_sub_return(int i, atomic_t *v)
{
 return atomic_add_return(-i,v);
}
extern int printk(const char * fmt, ...)
  ;





typedef struct {
 __volatile__ unsigned int lock;






} spinlock_t;
static __inline__ void _raw_spin_unlock(spinlock_t *lock)
{




 __asm__ __volatile__(
  "movb $1,%0" :"=m" (lock->lock) : : "memory"
 );
}
static __inline__ int _raw_spin_trylock(spinlock_t *lock)
{
 char oldval;
 __asm__ __volatile__(
  "xchgb %b0,%1"
  :"=q" (oldval), "=m" (lock->lock)
  :"0" (0) : "memory");
 return oldval > 0;
}

static __inline__ void _raw_spin_lock(spinlock_t *lock)
{






 __asm__ __volatile__(
  "\n1:\t" "lock ; decb %0\n\t" "js 2f\n" ".subsection 1\n\t" "" ".ifndef " ".text.lock." "KBUILD_BASENAME" "\n\t" ".text.lock." "KBUILD_BASENAME" ":\n\t" ".endif\n" "2:\t" "rep;nop\n\t" "cmpb $0,%0\n\t" "jle 2b\n\t" "jmp 1b\n" ".previous\n\t"
  :"=m" (lock->lock) : : "memory");
}
typedef struct {
 __volatile__ unsigned int lock;






} rwlock_t;
static __inline__ void _raw_read_lock(rwlock_t *rw)
{



}

static __inline__ void _raw_write_lock(rwlock_t *rw)
{



}




static __inline__ int _raw_read_trylock(rwlock_t *lock)
{
 atomic_t *count = (atomic_t *)lock;
 atomic_dec(count);
 if (((count)->counter) >= 0)
  return 1;
 atomic_inc(count);
 return 0;
}

static __inline__ int _raw_write_trylock(rwlock_t *lock)
{
 atomic_t *count = (atomic_t *)lock;
 if (atomic_sub_and_test(0x01000000, count))
  return 1;
 atomic_add(0x01000000, count);
 return 0;
}




int   _spin_trylock(spinlock_t *lock);
int   _read_trylock(rwlock_t *lock);
int   _write_trylock(rwlock_t *lock);

void   _spin_lock(spinlock_t *lock) ;
void   _read_lock(rwlock_t *lock) ;
void   _write_lock(rwlock_t *lock) ;

void   _spin_unlock(spinlock_t *lock) ;
void   _read_unlock(rwlock_t *lock) ;
void   _write_unlock(rwlock_t *lock) ;

unsigned long   _spin_lock_irqsave(spinlock_t *lock) ;
unsigned long   _read_lock_irqsave(rwlock_t *lock) ;
unsigned long   _write_lock_irqsave(rwlock_t *lock) ;

void   _spin_lock_irq(spinlock_t *lock) ;
void   _spin_lock_bh(spinlock_t *lock) ;
void   _read_lock_irq(rwlock_t *lock) ;
void   _read_lock_bh(rwlock_t *lock) ;
void   _write_lock_irq(rwlock_t *lock) ;
void   _write_lock_bh(rwlock_t *lock) ;

void   _spin_unlock_irqrestore(spinlock_t *lock, unsigned long flags) ;
void   _spin_unlock_irq(spinlock_t *lock) ;
void   _spin_unlock_bh(spinlock_t *lock) ;
void   _read_unlock_irqrestore(rwlock_t *lock, unsigned long flags) ;
void   _read_unlock_irq(rwlock_t *lock) ;
void   _read_unlock_bh(rwlock_t *lock) ;
void   _write_unlock_irqrestore(rwlock_t *lock, unsigned long flags) ;
void   _write_unlock_irq(rwlock_t *lock) ;
void   _write_unlock_bh(rwlock_t *lock) ;

int   _spin_trylock_bh(spinlock_t *lock);
int   generic_raw_read_trylock(rwlock_t *lock);
int in_lock_functions(unsigned long addr);
extern char * ___strtok;
extern char * strcpy(char *,const char *);
extern char * strncpy(char *,const char *,size_t);
extern char * strcat(char *, const char *);
extern char * strncat(char *, const char *, size_t);
extern char * strchr(const char *,int);
extern char * strrchr(const char *,int);
extern char * strpbrk(const char *,const char *);
extern char * strtok(char *,const char *);
extern char * strstr(const char *,const char *);
extern size_t strlen(const char *);
extern size_t strnlen(const char *,size_t);
extern size_t strspn(const char *,const char *);
extern int strcmp(const char *,const char *);
extern int strncmp(const char *,const char *,size_t);

extern void * memset(void *,int,size_t);
extern void * memcpy(void *,const void *,size_t);
extern void * memmove(void *,const void *,size_t);
extern void * memscan(void *,int,size_t);
extern int memcmp(const void *,const void *,size_t);
struct percpu_data {
 void *ptrs[8];
 void *blkp;
};
extern void *__alloc_percpu(size_t size, size_t align);
extern void free_percpu(const void *);
typedef struct
{
 __volatile__ unsigned int counter;
} local_t;






static __inline__ void local_inc(local_t *v)
{
 __asm__ __volatile__(
  "incl %0"
  :"=m" (v->counter)
  :"m" (v->counter));
}

static __inline__ void local_dec(local_t *v)
{
 __asm__ __volatile__(
  "decl %0"
  :"=m" (v->counter)
  :"m" (v->counter));
}

static __inline__ void local_add(unsigned long i, local_t *v)
{
 __asm__ __volatile__(
  "addl %1,%0"
  :"=m" (v->counter)
  :"ir" (i), "m" (v->counter));
}

static __inline__ void local_sub(unsigned long i, local_t *v)
{
 __asm__ __volatile__(
  "subl %1,%0"
  :"=m" (v->counter)
  :"ir" (i), "m" (v->counter));
}
struct timespec {
 long tv_sec;
 long tv_nsec;
};


struct timeval {
 int tv_sec;
 int tv_usec;
};

struct timezone {
 int tz_minuteswest;
 int tz_dsttime;
};
struct itimerspec {
        struct timespec it_interval;
        struct timespec it_value;
};

struct itimerval {
 struct timeval it_interval;
 struct timeval it_value;
};




struct siginfo;
typedef void __signalfn_t(int);
typedef __signalfn_t *__sighandler_t;

typedef void __restorefn_t(void);
typedef __restorefn_t *__sigrestore_t;
struct k_sigaction {
 struct sigaction sa;
};
struct flock {
 short l_type;
 short l_whence;
 __off_t l_start;
 __off_t l_len;
 pid_t l_pid;
};
extern unsigned long event;
struct linux_binprm{
 char buf[128];
 unsigned long page[32];
 unsigned long p;
 int sh_bang;
 struct inode * inode;
 int e_uid, e_gid;
 int argc, envc;
 char * filename;
 unsigned long loader, exec;
 int dont_iput;
};





struct linux_binfmt {
 struct linux_binfmt * next;
 long *use_count;
 int (*load_binary)(struct linux_binprm *, struct pt_regs * regs);
 int (*load_shlib)(int fd);
 int (*core_dump)(long signr, struct pt_regs * regs);
};

extern int register_binfmt(struct linux_binfmt *);
extern int unregister_binfmt(struct linux_binfmt *);

extern int read_exec(struct inode *inode, unsigned long offset,
 char * addr, unsigned long count, int to_kmem);

extern int open_inode(struct inode * inode, int mode);

extern int init_elf_binfmt(void);
extern int init_aout_binfmt(void);
extern int init_script_binfmt(void);
extern int init_java_binfmt(void);

extern int prepare_binprm(struct linux_binprm *);
extern void remove_arg_zero(struct linux_binprm *);
extern int search_binary_handler(struct linux_binprm *,struct pt_regs *);
extern void flush_old_exec(struct linux_binprm * bprm);
extern unsigned long setup_arg_pages(unsigned long p, struct linux_binprm * bprm);
extern unsigned long copy_strings(int argc,char ** argv,unsigned long *page,
  unsigned long p, int from_kmem);
typedef int key_t;


struct ipc_perm
{
  __key_t key;
  //ushort uid;
  //ushort gid;
  //ushort cuid;
  //ushort cgid;
  //ushort mode;
  //ushort seq;
};
struct semid_ds {
  struct ipc_perm sem_perm;
  __time_t sem_otime;
  __time_t sem_ctime;
  struct sem *sem_base;
  struct sem_queue *sem_pending;
  struct sem_queue **sem_pending_last;
  struct sem_undo *undo;
  //ushort sem_nsems;
};


struct sembuf {
  //ushort sem_num;
  short sem_op;
  short sem_flg;
};


union semun {
  int val;
  struct semid_ds *buf;
  //ushort *array;
  struct seminfo *__buf;
  void *__pad;
};

struct seminfo {
    int semmap;
    int semmni;
    int semmns;
    int semmnu;
    int semmsl;
    int semopm;
    int semume;
    int semusz;
    int semvmx;
    int semaem;
};
extern unsigned long avenrun[];
extern int nr_running, nr_tasks;
extern int last_pid;
typedef struct desc_struct {
 unsigned long a,b;
} desc_table[256];

extern desc_table idt,gdt;
struct statfs {
 long f_type;
 long f_bsize;
 long f_blocks;
 long f_bfree;
 long f_bavail;
 long f_files;
 long f_ffree;
 __kernel_fsid_t f_fsid;
 long f_namelen;
 long f_frsize;
 long f_spare[5];
};

struct statfs64 {
 long f_type;
 long f_bsize;
 long f_blocks;
 long f_bfree;
 long f_bavail;
 long f_files;
 long f_ffree;
 __kernel_fsid_t f_fsid;
 long f_namelen;
 long f_frsize;
 long f_spare[5];
};

struct compat_statfs64 {
 __u32 f_type;
 __u32 f_bsize;
 __u64 f_blocks;
 __u64 f_bfree;
 __u64 f_bavail;
 __u64 f_files;
 __u64 f_ffree;
 __kernel_fsid_t f_fsid;
 __u32 f_namelen;
 __u32 f_frsize;
 __u32 f_spare[5];
} ((packed));
struct iovec
{
 void *iov_base;
 int iov_len;
};






struct sockaddr
{
 unsigned short sa_family;
 char sa_data[14];
};

struct linger {
 int l_onoff;
 int l_linger;
};







struct msghdr
{
 void * msg_name;
 int msg_namelen;
 struct iovec * msg_iov;
 int msg_iovlen;
 void * msg_control;
 int msg_controllen;
 int msg_flags;
};
typedef enum {
  SS_FREE = 0,
  SS_UNCONNECTED,
  SS_CONNECTING,
  SS_CONNECTED,
  SS_DISCONNECTING
} socket_state;
extern int max_inodes, nr_inodes;
extern int max_files, nr_files;
struct rusage {
 struct timeval ru_utime;
 struct timeval ru_stime;
 long ru_maxrss;
 long ru_ixrss;
 long ru_idrss;
 long ru_isrss;
 long ru_minflt;
 long ru_majflt;
 long ru_nswap;
 long ru_inblock;
 long ru_oublock;
 long ru_msgsnd;
 long ru_msgrcv;
 long ru_nsignals;
 long ru_nvcsw;
 long ru_nivcsw;
};



struct rlimit {
 long rlim_cur;
 long rlim_max;
};
struct timer_struct {
 unsigned long expires;
 void (*fn)(void);
};

extern unsigned long timer_active;
extern struct timer_struct timer_table[32];
struct timer_list {
 struct timer_list *next;
 struct timer_list *prev;
 unsigned long expires;
 unsigned long data;
 void (*function)(unsigned long);
};

extern void add_timer(struct timer_list * timer);
extern int del_timer(struct timer_list * timer);

extern void it_real_fn(unsigned long);

extern __inline__ void init_timer(struct timer_list * timer)
{
 timer->next = ((void *)0);
 timer->prev = ((void *)0);
}
struct sched_param {
 int sched_priority;
};
extern unsigned char _ctype[];
extern char _ctmp;
extern __inline__ unsigned char inb(unsigned short port) { unsigned char _v; __asm__ __volatile__ ("in" "b" " %" "w" "1,%" "" "0" : "=a" (_v) : "Nd" (port) ); return _v; } extern __inline__ unsigned char inb_p(unsigned short port) { unsigned char _v; __asm__ __volatile__ ("in" "b" " %" "w" "1,%" "" "0" "\noutb %%al,$0x80" : "=a" (_v) : "Nd" (port) ); return _v; }


extern __inline__ unsigned short inw(unsigned short port) { unsigned short _v; __asm__ __volatile__ ("in" "w" " %" "w" "1,%" "" "0" : "=a" (_v) : "Nd" (port) ); return _v; } extern __inline__ unsigned short inw_p(unsigned short port) { unsigned short _v; __asm__ __volatile__ ("in" "w" " %" "w" "1,%" "" "0" "\noutb %%al,$0x80" : "=a" (_v) : "Nd" (port) ); return _v; }


extern __inline__ unsigned int inl(unsigned short port) { unsigned int _v; __asm__ __volatile__ ("in" "l" " %" "w" "1,%" "" "0" : "=a" (_v) : "Nd" (port) ); return _v; } extern __inline__ unsigned int inl_p(unsigned short port) { unsigned int _v; __asm__ __volatile__ ("in" "l" " %" "w" "1,%" "" "0" "\noutb %%al,$0x80" : "=a" (_v) : "Nd" (port) ); return _v; }


extern __inline__ void outb(unsigned char value, unsigned short port) { __asm__ __volatile__ ("out" "b" " %" "b" "0,%" "w" "1" : : "a" (value), "Nd" (port)); } extern __inline__ void outb_p(unsigned char value, unsigned short port) { __asm__ __volatile__ ("out" "b" " %" "b" "0,%" "w" "1" "\noutb %%al,$0x80" : : "a" (value), "Nd" (port));}
extern __inline__ void outw(unsigned short value, unsigned short port) { __asm__ __volatile__ ("out" "w" " %" "w" "0,%" "w" "1" : : "a" (value), "Nd" (port)); } extern __inline__ void outw_p(unsigned short value, unsigned short port) { __asm__ __volatile__ ("out" "w" " %" "w" "0,%" "w" "1" "\noutb %%al,$0x80" : : "a" (value), "Nd" (port));}
extern __inline__ void outl(unsigned int value, unsigned short port) { __asm__ __volatile__ ("out" "l" " %" "0,%" "w" "1" : : "a" (value), "Nd" (port)); } extern __inline__ void outl_p(unsigned int value, unsigned short port) { __asm__ __volatile__ ("out" "l" " %" "0,%" "w" "1" "\noutb %%al,$0x80" : : "a" (value), "Nd" (port));}

extern __inline__ void insb(unsigned short port, void * addr, unsigned long count) { __asm__ __volatile__ ("rep ; ins" "b" : "=D" (addr), "=c" (count) : "d" (port),"0" (addr),"1" (count)); }
extern __inline__ void insw(unsigned short port, void * addr, unsigned long count) { __asm__ __volatile__ ("rep ; ins" "w" : "=D" (addr), "=c" (count) : "d" (port),"0" (addr),"1" (count)); }
extern __inline__ void insl(unsigned short port, void * addr, unsigned long count) { __asm__ __volatile__ ("rep ; ins" "l" : "=D" (addr), "=c" (count) : "d" (port),"0" (addr),"1" (count)); }

extern __inline__ void outsb(unsigned short port, const void * addr, unsigned long count) { __asm__ __volatile__ ("rep ; outs" "b" : "=S" (addr), "=c" (count) : "d" (port),"0" (addr),"1" (count)); }
extern __inline__ void outsw(unsigned short port, const void * addr, unsigned long count) { __asm__ __volatile__ ("rep ; outs" "w" : "=S" (addr), "=c" (count) : "d" (port),"0" (addr),"1" (count)); }
extern __inline__ void outsl(unsigned short port, const void * addr, unsigned long count) { __asm__ __volatile__ ("rep ; outs" "l" : "=S" (addr), "=c" (count) : "d" (port),"0" (addr),"1" (count)); }
extern unsigned long loops_per_sec;
extern void __bad_udelay(void);
extern void __bad_ndelay(void);

extern void __udelay(unsigned long usecs);
extern void __ndelay(unsigned long usecs);
extern void __const_udelay(unsigned long usecs);
extern void __delay(unsigned long loops);
extern spinlock_t dma_spin_lock;

static __inline__ unsigned long claim_dma_lock(void)
{
 unsigned long flags;
 spin_lock_irqsave(&dma_spin_lock, flags);
 return flags;
}

static __inline__ void release_dma_lock(unsigned long flags)
{
 spin_unlock_irqrestore(&dma_spin_lock, flags);
}


static __inline__ void enable_dma(unsigned int dmanr)
{
 if (dmanr<=3)
  outb(dmanr, 0x0A);
 else
  outb(dmanr & 3, 0xD4);
}

static __inline__ void disable_dma(unsigned int dmanr)
{
 if (dmanr<=3)
  outb(dmanr | 4, 0x0A);
 else
  outb((dmanr & 3) | 4, 0xD4);
}
static __inline__ void clear_dma_ff(unsigned int dmanr)
{
 if (dmanr<=3)
  outb(0, 0x0C);
 else
  outb(0, 0xD8);
}


static __inline__ void set_dma_mode(unsigned int dmanr, char mode)
{
 if (dmanr<=3)
  outb(mode | dmanr, 0x0B);
 else
  outb(mode | (dmanr&3), 0xD6);
}






static __inline__ void set_dma_page(unsigned int dmanr, char pagenr)
{
 switch(dmanr) {
  case 0:
   outb(pagenr, 0x87);
   break;
  case 1:
   outb(pagenr, 0x83);
   break;
  case 2:
   outb(pagenr, 0x81);
   break;
  case 3:
   outb(pagenr, 0x82);
   break;
  case 5:
   outb(pagenr & 0xfe, 0x8B);
   break;
  case 6:
   outb(pagenr & 0xfe, 0x89);
   break;
  case 7:
   outb(pagenr & 0xfe, 0x8A);
   break;
 }
}





static __inline__ void set_dma_addr(unsigned int dmanr, unsigned int a)
{
 set_dma_page(dmanr, a>>16);
 if (dmanr <= 3) {
     outb( a & 0xff, ((dmanr&3)<<1) + 0x00 );
            outb( (a>>8) & 0xff, ((dmanr&3)<<1) + 0x00 );
 } else {
     outb( (a>>1) & 0xff, ((dmanr&3)<<2) + 0xC0 );
     outb( (a>>9) & 0xff, ((dmanr&3)<<2) + 0xC0 );
 }
}
static __inline__ void set_dma_count(unsigned int dmanr, unsigned int count)
{
        count--;
 if (dmanr <= 3) {
     outb( count & 0xff, ((dmanr&3)<<1) + 1 + 0x00 );
     outb( (count>>8) & 0xff, ((dmanr&3)<<1) + 1 + 0x00 );
        } else {
     outb( (count>>1) & 0xff, ((dmanr&3)<<2) + 2 + 0xC0 );
     outb( (count>>9) & 0xff, ((dmanr&3)<<2) + 2 + 0xC0 );
        }
}
static __inline__ int get_dma_residue(unsigned int dmanr)
{
 unsigned int io_port = (dmanr<=3)? ((dmanr&3)<<1) + 1 + 0x00
      : ((dmanr&3)<<2) + 2 + 0xC0;


 unsigned short count;

 count = 1 + inb(io_port);
 count += inb(io_port) << 8;

 return (dmanr<=3)? count : (count<<1);
}



extern int request_dma(unsigned int dmanr, const char * device_id);
extern void free_dma(unsigned int dmanr);




extern int isa_dma_bridge_buggy;
void * kmalloc(unsigned int size, int priority);
void kfree(void * obj);
extern void reserve_setup(char *str, int *ints);
extern int check_region(unsigned int from, unsigned int extent);
extern void request_region(unsigned int from, unsigned int extent,const char *name);
extern void release_region(unsigned int from, unsigned int extent);
extern int get_ioport_list(char *);



extern void *irq2dev_map[16];
extern int autoirq_setup(int waittime);
extern int autoirq_report(int waittime);
struct oldold_utsname {
 char sysname[9];
 char nodename[9];
 char release[9];
 char version[9];
 char machine[9];
};



struct old_utsname {
 char sysname[65];
 char nodename[65];
 char release[65];
 char version[65];
 char machine[65];
};

struct new_utsname {
 char sysname[65];
 char nodename[65];
 char release[65];
 char version[65];
 char machine[65];
 char domainname[65];
};

extern struct new_utsname system_utsname;
 struct seq_event_rec {
   unsigned char arr[8];
  };
struct patch_info {
  unsigned short key;



  short device_no;
  short instr_no;

  unsigned int mode;
  int len;
  int loop_start, loop_end;
  unsigned int base_freq;
  unsigned int base_note;
  unsigned int high_note;
  unsigned int low_note;
  int panning;
  int detuning;




  unsigned char env_rate[ 6 ];
  unsigned char env_offset[ 6 ];







  unsigned char tremolo_sweep;
  unsigned char tremolo_rate;
  unsigned char tremolo_depth;

  unsigned char vibrato_sweep;
  unsigned char vibrato_rate;
  unsigned char vibrato_depth;

  int scale_frequency;
  unsigned int scale_factor;

         int volume;
  int fractions;
         int spare[3];
  char data[1];
 };

struct sysex_info {
  short key;


  short device_no;
  int len;
  unsigned char data[1];
 };
struct patmgr_info {
   unsigned int key;





   int device;
   int command;
   int parm1;
   int parm2;
   int parm3;

   union {
  unsigned char data8[4000];
  unsigned short data16[2000];
  unsigned int data32[1000];
  struct patch_info patch;
   } data;
 };
typedef unsigned char sbi_instr_data[32];

struct sbi_instrument {
  unsigned short key;


  short device;
  int channel;
  sbi_instr_data operators;
 };

struct synth_info {
  char name[30];
  int device;
  int synth_type;




  int synth_subtype;






  int perc_mode;
  int nr_voices;
  int nr_drums;
  int instr_bank_size;
  unsigned int capabilities;



  int dummies[19];
 };

struct sound_timer_info {
  char name[32];
  int caps;
 };



struct midi_info {
  char name[30];
  int device;
  unsigned int capabilities;
  int dev_type;
  int dummies[18];
 };




typedef struct {
  unsigned char cmd;
  char nr_args, nr_returns;
  unsigned char data[30];
 } mpu_command_rec;
typedef struct audio_buf_info {
   int fragments;
   int fragstotal;
   int fragsize;

   int bytes;

  } audio_buf_info;
typedef struct count_info {
  int bytes;
  int blocks;
  int ptr;
 } count_info;




typedef struct buffmem_desc {
  unsigned *buffer;
  int size;
 } buffmem_desc;
typedef struct copr_buffer {
  int command;
  int flags;



  int len;
  int offs;

  unsigned char data[4000];
 } copr_buffer;

typedef struct copr_debug_buf {
  int command;
  int parm1;
  int parm2;
  int flags;
  int len;
 } copr_debug_buf;

typedef struct copr_msg {
  int len;
  unsigned char data[4000];
 } copr_msg;
typedef struct mixer_info
{
  char id[16];
  char name[32];
} mixer_info;
typedef unsigned char mixer_record[128];
void seqbuf_dump(void);
struct snd_wait {
   int flags;
 };

extern int sound_alloc_dma(int chn, char *deviceID);
extern int sound_open_dma(int chn, char *deviceID);
extern void sound_free_dma(int chn);
extern void sound_close_dma(int chn);



extern __caddr_t sound_mem_blocks[1024];
extern int sound_nblocks;
struct fileinfo {
          int mode;
   int flags;
   int dummy;
       };

struct address_info {
 int io_base;
 int irq;
 int dma;
 int dma2;
 int always_detect;
 char *name;
 int driver_use_1;
 int driver_use_2;
 int *osp;
 int card_subtype;
};



struct voice_alloc_info {
  int max_voice;
  int used_voices;
  int ptr;
  unsigned short map[32];
  int timestamp;
  int alloc_times[32];
 };

struct channel_info {
  int pgm_num;
  int bender_value;
  unsigned char controllers[128];
 };
int DMAbuf_open(int dev, int mode);
int DMAbuf_release(int dev, int mode);
int DMAbuf_getwrbuffer(int dev, char **buf, int *size, int dontblock);
int DMAbuf_get_curr_buffer(int dev, int *buff_no, char **dma_buf, int *buff_ptr, int *buff_size);
int DMAbuf_getrdbuffer(int dev, char **buf, int *len, int dontblock);
int DMAbuf_rmchars(int dev, int buff_no, int c);
int DMAbuf_start_output(int dev, int buff_no, int l);
int DMAbuf_set_count(int dev, int buff_no, int l);
int DMAbuf_ioctl(int dev, unsigned int cmd, __caddr_t arg, int local);
void DMAbuf_init(void);
int DMAbuf_start_dma (int dev, unsigned long physaddr, int count, int dma_mode);
int DMAbuf_open_dma (int dev);
void DMAbuf_close_dma (int dev);
void DMAbuf_reset_dma (int dev);
void DMAbuf_inputintr(int dev);
void DMAbuf_outputintr(int dev, int underflow_flag);
int DMAbuf_select(int dev, struct fileinfo *file, int sel_type, int * wait);
void DMAbuf_start_devices(unsigned int devmask);





int audio_read (int dev, struct fileinfo *file, char *buf, int count);
int audio_write (int dev, struct fileinfo *file, const char *buf, int count);
int audio_open (int dev, struct fileinfo *file);
void audio_release (int dev, struct fileinfo *file);
int audio_ioctl (int dev, struct fileinfo *file,
    unsigned int cmd, __caddr_t arg);
int audio_lseek (int dev, struct fileinfo *file, __off_t offset, int orig);
void audio_init (void);

int audio_select(int dev, struct fileinfo *file, int sel_type, int * wait);





int sequencer_read (int dev, struct fileinfo *file, char *buf, int count);
int sequencer_write (int dev, struct fileinfo *file, const char *buf, int count);
int sequencer_open (int dev, struct fileinfo *file);
void sequencer_release (int dev, struct fileinfo *file);
int sequencer_ioctl (int dev, struct fileinfo *file,
    unsigned int cmd, __caddr_t arg);
int sequencer_lseek (int dev, struct fileinfo *file, __off_t offset, int orig);
void sequencer_init (void);
void sequencer_timer(unsigned long dummy);
int note_to_freq(int note_num);
unsigned long compute_finetune(unsigned long base_freq, int bend, int range);
void seq_input_event(unsigned char *event, int len);
void seq_copy_to_input (unsigned char *event, int len);

int sequencer_select(int dev, struct fileinfo *file, int sel_type, int * wait);





int MIDIbuf_read (int dev, struct fileinfo *file, char *buf, int count);
int MIDIbuf_write (int dev, struct fileinfo *file, const char *buf, int count);
int MIDIbuf_open (int dev, struct fileinfo *file);
void MIDIbuf_release (int dev, struct fileinfo *file);
int MIDIbuf_ioctl (int dev, struct fileinfo *file,
    unsigned int cmd, __caddr_t arg);
int MIDIbuf_lseek (int dev, struct fileinfo *file, __off_t offset, int orig);
void MIDIbuf_bytes_received(int dev, unsigned char *buf, int count);
void MIDIbuf_init(void);

int MIDIbuf_select(int dev, struct fileinfo *file, int sel_type, int * wait);







void soundcard_init(void);
void tenmicrosec(int *osp);
void request_sound_timer (int count);
void sound_stop_timer(void);
int snd_ioctl_return(int *addr, int value);
int snd_set_irq_handler (int interrupt_level, void(*iproc)(int, void*, struct pt_regs *), char *name, int *osp);
void snd_release_irq(int vect);
void sound_dma_malloc(int dev);
void sound_dma_free(int dev);
void conf_printf(char *name, struct address_info *hw_config);
void conf_printf2(char *name, int base, int irq, int dma, int dma2);


int sound_read_sw (int dev, struct fileinfo *file, char *buf, int count);
int sound_write_sw (int dev, struct fileinfo *file, const char *buf, int count);
int sound_open_sw (int dev, struct fileinfo *file);
void sound_release_sw (int dev, struct fileinfo *file);
int sound_ioctl_sw (int dev, struct fileinfo *file,
      unsigned int cmd, __caddr_t arg);


int opl3_detect (int ioaddr, int *osp);
void opl3_init(int ioaddr, int *osp);


void attach_sb_card(struct address_info *hw_config);
int probe_sb(struct address_info *hw_config);


void sb_dsp_disable_midi(int port);
void sb_dsp_disable_recording(int port);
void attach_sbmpu (struct address_info *hw_config);
int probe_sbmpu (struct address_info *hw_config);
void unload_sbmpu (struct address_info *hw_config);


void attach_uart401 (struct address_info *hw_config);
int probe_uart401 (struct address_info *hw_config);
void unload_uart401 (struct address_info *hw_config);
void uart401intr (int irq, void *dev_id, struct pt_regs * dummy);


void attach_adlib_card(struct address_info *hw_config);
int probe_adlib(struct address_info *hw_config);


void attach_pas_card(struct address_info *hw_config);
int probe_pas(struct address_info *hw_config);
int pas_set_intr(int mask);
int pas_remove_intr(int mask);
unsigned char pas_read(int ioaddr);
void pas_write(unsigned char data, int ioaddr);


void pas_pcm_interrupt(unsigned char status, int cause);
void pas_pcm_init(struct address_info *hw_config);


int pas_init_mixer(void);


void pas_midi_init(void);
void pas_midi_interrupt(void);


void attach_gus_card(struct address_info * hw_config);
int probe_gus(struct address_info *hw_config);
int gus_set_midi_irq(int num);
void gusintr(int irq, void *dev_id, struct pt_regs * dummy);
void attach_gus_db16(struct address_info * hw_config);
int probe_gus_db16(struct address_info *hw_config);


int gus_wave_detect(int baseaddr);
void gus_wave_init(struct address_info *hw_config);
void gus_wave_unload (void);
void gus_voice_irq(void);
unsigned char gus_read8 (int reg);
void gus_write8(int reg, unsigned int data);
void guswave_dma_irq(void);
void gus_delay(void);
int gus_default_mixer_ioctl (int dev, unsigned int cmd, __caddr_t arg);
void gus_timer_command (unsigned int addr, unsigned int val);


void gus_midi_init(void);
void gus_midi_interrupt(int dummy);


void attach_mpu401(struct address_info * hw_config);
int probe_mpu401(struct address_info *hw_config);
void mpuintr(int irq, void *dev_id, struct pt_regs * dummy);


void attach_uart6850(struct address_info * hw_config);
int probe_uart6850(struct address_info *hw_config);


void enable_opl3_mode(int left, int right, int both);


int pmgr_open(int dev);
void pmgr_release(int dev);
int pmgr_read (int dev, struct fileinfo *file, char * buf, int count);
int pmgr_write (int dev, struct fileinfo *file, const char * buf, int count);
int pmgr_access(int dev, struct patmgr_info *rec);
int pmgr_inform(int dev, int event, unsigned long parm1, unsigned long parm2,
        unsigned long parm3, unsigned long parm4);


void ics2101_mixer_init(void);


void sound_timer_interrupt(void);
void sound_timer_syncinterval(unsigned int new_usecs);


void ad1848_init (char *name, int io_base, int irq, int dma_playback, int dma_capture, int share_dma, int *osp);
void ad1848_unload (int io_base, int irq, int dma_playback, int dma_capture, int share_dma);

int ad1848_detect (int io_base, int *flags, int *osp);



void ad1848_interrupt (int irq, void *dev_id, struct pt_regs * dummy);
void attach_ms_sound(struct address_info * hw_config);
int probe_ms_sound(struct address_info *hw_config);
void attach_pnp_ad1848(struct address_info * hw_config);
int probe_pnp_ad1848(struct address_info *hw_config);
void unload_pnp_ad1848(struct address_info *hw_info);


int probe_pss (struct address_info *hw_config);
void attach_pss (struct address_info *hw_config);
int probe_pss_mpu (struct address_info *hw_config);
void attach_pss_mpu (struct address_info *hw_config);
int probe_pss_mss (struct address_info *hw_config);
void attach_pss_mss (struct address_info *hw_config);


int probe_sscape (struct address_info *hw_config);
void attach_sscape (struct address_info *hw_config);
int probe_ss_ms_sound (struct address_info *hw_config);
void attach_ss_ms_sound(struct address_info * hw_config);

int pss_read (int dev, struct fileinfo *file, char *buf, int count);
int pss_write (int dev, struct fileinfo *file, char *buf, int count);
int pss_open (int dev, struct fileinfo *file);
void pss_release (int dev, struct fileinfo *file);
int pss_ioctl (int dev, struct fileinfo *file,
    unsigned int cmd, __caddr_t arg);
int pss_lseek (int dev, struct fileinfo *file, __off_t offset, int orig);
void pss_init(void);


int InitAEDSP16_SBPRO(struct address_info *hw_config);
int InitAEDSP16_MSS(struct address_info *hw_config);
int InitAEDSP16_MPU401(struct address_info *hw_config);


void do_midi_msg (int synthno, unsigned char *msg, int mlen);


void attach_trix_wss (struct address_info *hw_config);
int probe_trix_wss (struct address_info *hw_config);
void attach_trix_sb (struct address_info *hw_config);
int probe_trix_sb (struct address_info *hw_config);
void attach_trix_mpu (struct address_info *hw_config);
int probe_trix_mpu (struct address_info *hw_config);


void attach_mad16 (struct address_info *hw_config);
int probe_mad16 (struct address_info *hw_config);
void attach_mad16_mpu (struct address_info *hw_config);
int probe_mad16_mpu (struct address_info *hw_config);
int mad16_sb_dsp_detect (struct address_info *hw_config);


void unload_pss(struct address_info *hw_info);
void unload_pss_mpu(struct address_info *hw_info);
void unload_pss_mss(struct address_info *hw_info);
void unload_mad16(struct address_info *hw_info);
void unload_mad16_mpu(struct address_info *hw_info);
void unload_adlib(struct address_info *hw_info);
void unload_pas(struct address_info *hw_info);
void unload_mpu401(struct address_info *hw_info);
void unload_maui(struct address_info *hw_info);
void unload_uart6850(struct address_info *hw_info);
void unload_sb(struct address_info *hw_info);
void unload_sb16(struct address_info *hw_info);
void unload_sb16midi(struct address_info *hw_info);
void unload_gus_db16(struct address_info *hw_info);
void unload_ms_sound(struct address_info *hw_info);
void unload_gus(struct address_info *hw_info);
void unload_sscape(struct address_info *hw_info);
void unload_ss_ms_sound(struct address_info *hw_info);
void unload_trix_wss(struct address_info *hw_info);
void unload_trix_sb(struct address_info *hw_info);
void unload_trix_mpu(struct address_info *hw_info);
void unload_cs4232(struct address_info *hw_info);
void unload_cs4232_mpu(struct address_info *hw_info);



int probe_cs4232 (struct address_info *hw_config);
void attach_cs4232 (struct address_info *hw_config);
int probe_cs4232_mpu (struct address_info *hw_config);
void attach_cs4232_mpu (struct address_info *hw_config);


void attach_maui(struct address_info * hw_config);
int probe_maui(struct address_info *hw_config);


void sound_pnp_init(int *osp);
void sound_pnp_disconnect(void);
extern int sound_started;

struct driver_info {
 char *driver_id;
 int card_subtype;
 int card_type;
 char *name;
 void (*attach) (struct address_info *hw_config);
 int (*probe) (struct address_info *hw_config);
 void (*unload) (struct address_info *hw_config);
};

struct card_info {
 int card_type;
 struct address_info config;
 int enabled;
 void *for_driver_use;
};

typedef struct pnp_sounddev
{
 int id;
 void (*setup)(void *dev);
 char *driver_name;
}pnp_sounddev;
struct dma_buffparms {
 int dma_mode;
 int closing;





   char *raw_buf;
     unsigned long raw_buf_phys;





 unsigned long flags;
 int open_mode;




        int qlen;
        int qhead;
        int qtail;
 int cfrag;

 int nbufs;
 int counts[(32*4)];
 int subdivision;

 int fragment_size;
 int max_fragments;

 int bytes_in_use;

 int underrun_count;
 int byte_counter;

 int mapping_flags;

 char neutral_byte;



};





typedef struct coproc_operations {
  char name[32];
  int (*open) (void *devc, int sub_device);
  void (*close) (void *devc, int sub_device);
  int (*ioctl) (void *devc, unsigned int cmd, __caddr_t arg, int local);
  void (*reset) (void *devc);

  void *devc;
 } coproc_operations;

struct audio_driver {
 int (*open) (int dev, int mode);
 void (*close) (int dev);
 void (*output_block) (int dev, unsigned long buf,
         int count, int intrflag, int dma_restart);
 void (*start_input) (int dev, unsigned long buf,
        int count, int intrflag, int dma_restart);
 int (*ioctl) (int dev, unsigned int cmd, __caddr_t arg, int local);
 int (*prepare_for_input) (int dev, int bufsize, int nbufs);
 int (*prepare_for_output) (int dev, int bufsize, int nbufs);
 void (*reset) (int dev);
 void (*halt_xfer) (int dev);
 int (*local_qlen)(int dev);
        void (*copy_from_user)(int dev, char *localbuf, int localoffs,
                               const char *userbuf, int useroffs, int len);
 void (*halt_input) (int dev);
 void (*halt_output) (int dev);
 void (*trigger) (int dev, int bits);
 int (*set_speed)(int dev, int speed);
 unsigned int (*set_bits)(int dev, unsigned int bits);
 short (*set_channels)(int dev, short channels);
};

struct audio_operations {
        char name[32];
 int flags;






 int format_mask;
 void *devc;
 struct audio_driver *d;
 long buffsize;
 int dmachan1, dmachan2;
 struct dma_buffparms *dmap_in, *dmap_out;
 struct coproc_operations *coproc;
 int mixer_dev;
 int enable_bits;
  int open_mode;
 int go;
 int min_fragment;
};

struct mixer_operations {
 char id[16];
 char name[32];
 int (*ioctl) (int dev, unsigned int cmd, __caddr_t arg);

 void *devc;
};

struct synth_operations {
 struct synth_info *info;
 int midi_dev;
 int synth_type;
 int synth_subtype;

 int (*open) (int dev, int mode);
 void (*close) (int dev);
 int (*ioctl) (int dev, unsigned int cmd, __caddr_t arg);
 int (*kill_note) (int dev, int voice, int note, int velocity);
 int (*start_note) (int dev, int voice, int note, int velocity);
 int (*set_instr) (int dev, int voice, int instr);
 void (*reset) (int dev);
 void (*hw_control) (int dev, unsigned char *event);
 int (*load_patch) (int dev, int format, const char *addr,
      int offs, int count, int pmgr_flag);
 void (*aftertouch) (int dev, int voice, int pressure);
 void (*controller) (int dev, int voice, int ctrl_num, int value);
 void (*panning) (int dev, int voice, int value);
 void (*volume_method) (int dev, int mode);
 int (*pmgr_interface) (int dev, struct patmgr_info *info);
 void (*bender) (int dev, int chn, int value);
 int (*alloc_voice) (int dev, int chn, int note, struct voice_alloc_info *alloc);
 void (*setup_voice) (int dev, int voice, int chn);
 int (*send_sysex)(int dev, unsigned char *bytes, int len);

  struct voice_alloc_info alloc;
  struct channel_info chn_info[16];
};

struct midi_input_info {

      int m_busy;
      unsigned char m_buf[10];
  unsigned char m_prev_status;
      int m_ptr;



      int m_state;
      int m_left;
 };

struct midi_operations {
 struct midi_info info;
 struct synth_operations *converter;
 struct midi_input_info in_info;
 int (*open) (int dev, int mode,
  void (*inputintr)(int dev, unsigned char data),
  void (*outputintr)(int dev)
  );
 void (*close) (int dev);
 int (*ioctl) (int dev, unsigned int cmd, __caddr_t arg);
 int (*putc) (int dev, unsigned char data);
 int (*start_read) (int dev);
 int (*end_read) (int dev);
 void (*kick)(int dev);
 int (*command) (int dev, unsigned char *data);
 int (*buffer_status) (int dev);
 int (*prefix_cmd) (int dev, unsigned char status);
 struct coproc_operations *coproc;
 void *devc;
};

struct sound_lowlev_timer {
  int dev;
  unsigned int (*tmr_start)(int dev, unsigned int usecs);
  void (*tmr_disable)(int dev);
  void (*tmr_restart)(int dev);
 };

struct sound_timer_operations {
 struct sound_timer_info info;
 int priority;
 int devlink;
 int (*open)(int dev, int mode);
 void (*close)(int dev);
 int (*event)(int dev, unsigned char *ev);
 unsigned long (*get_time)(int dev);
 int (*ioctl) (int dev, unsigned int cmd, __caddr_t arg);
 void (*arm_timer)(int dev, long time);
};
 extern struct audio_operations * audio_devs[5]; extern int num_audiodevs;
 extern struct mixer_operations * mixer_devs[5]; extern int num_mixers;
 extern struct synth_operations * synth_devs[3 +6]; extern int num_synths;
 extern struct midi_operations * midi_devs[6]; extern int num_midis;
 extern struct sound_timer_operations * sound_timer_devs[3]; extern int num_sound_timers;

 extern struct driver_info sound_drivers[];
 extern int num_sound_drivers;
 extern int max_sound_drivers;
 extern struct card_info snd_installed_cards[];
 extern int num_sound_cards;
 extern int max_sound_cards;

 extern int trace_init;


void sndtable_init(void);
int sndtable_get_cardcount (void);
struct address_info *sound_getconf(int card_type);
void sound_chconf(int card_type, int ioaddr, int irq, int dma);
int snd_find_driver(int type);
void sound_unload_drivers(void);
void sound_unload_driver(int type);
int sndtable_identify_card(char *name);
void sound_setup (char *str, int *ints);

int sound_alloc_dmap (int dev, struct dma_buffparms *dmap, int chan);
void sound_free_dmap (int dev, struct dma_buffparms *dmap);
extern int soud_map_buffer (int dev, struct dma_buffparms *dmap, buffmem_desc *info);
void install_pnp_sounddrv(struct pnp_sounddev *drv);
int sndtable_probe (int unit, struct address_info *hw_config);
int sndtable_init_card (int unit, struct address_info *hw_config);
int sndtable_start_card (int unit, struct address_info *hw_config);
void sound_timer_init (struct sound_lowlev_timer *t, char *name);
int sound_start_dma ( int dev, struct dma_buffparms *dmap, int chan,
   unsigned long physaddr,
   int count, int dma_mode, int autoinit);
void sound_dma_intr (int dev, struct dma_buffparms *dmap, int chan);



int sound_install_audiodrv(int vers,
      char *name,
      struct audio_driver *driver,
      int driver_size,
      int flags,
            unsigned int format_mask,
      void *devc,
      int dma1,
      int dma2);
int sound_install_mixer(int vers,
   char *name,
   struct mixer_operations *driver,
   int driver_size,
   void *devc);
