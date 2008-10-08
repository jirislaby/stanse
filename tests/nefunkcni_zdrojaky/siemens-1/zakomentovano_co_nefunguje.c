





struct sched_param {
 int sched_priority;
};











typedef struct {
 unsigned long fds_bits [(1024/(8 * sizeof(unsigned long)))];
} __kernel_fd_set;


typedef void (*__kernel_sighandler_t)(int);


typedef int __kernel_key_t;
typedef int __kernel_mqd_t;

typedef unsigned long __kernel_ino_t;
typedef unsigned short __kernel_mode_t;
typedef unsigned short __kernel_nlink_t;
typedef long __kernel_off_t;
typedef int __kernel_pid_t;
typedef unsigned short __kernel_ipc_pid_t;
typedef unsigned short __kernel_uid_t;
typedef unsigned short __kernel_gid_t;
typedef unsigned int __kernel_size_t;
typedef int __kernel_ssize_t;
typedef int __kernel_ptrdiff_t;
typedef long __kernel_time_t;
typedef long __kernel_suseconds_t;
typedef long __kernel_clock_t;
typedef int __kernel_timer_t;
typedef int __kernel_clockid_t;
typedef int __kernel_daddr_t;
typedef char * __kernel_caddr_t;
typedef unsigned short __kernel_uid16_t;
typedef unsigned short __kernel_gid16_t;
typedef unsigned int __kernel_uid32_t;
typedef unsigned int __kernel_gid32_t;

typedef unsigned short __kernel_old_uid_t;
typedef unsigned short __kernel_old_gid_t;
typedef unsigned short __kernel_old_dev_t;


typedef long long __kernel_loff_t;


typedef struct {

 int val[2];



} __kernel_fsid_t;





typedef unsigned short umode_t;






typedef __signed__ char __s8;
typedef unsigned char __u8;

typedef __signed__ short __s16;
typedef unsigned short __u16;

typedef __signed__ int __s32;
typedef unsigned int __u32;


typedef __signed__ long long __s64;
typedef unsigned long long __u64;
typedef signed char s8;
typedef unsigned char u8;

typedef signed short s16;
typedef unsigned short u16;

typedef signed int s32;
typedef unsigned int u32;

typedef signed long long s64;
typedef unsigned long long u64;






typedef u32 dma_addr_t;

typedef u64 dma64_addr_t;



typedef __u32 __kernel_dev_t;

typedef __kernel_fd_set fd_set;
typedef __kernel_dev_t dev_t;
typedef __kernel_ino_t ino_t;
typedef __kernel_mode_t mode_t;
typedef __kernel_nlink_t nlink_t;
typedef __kernel_off_t off_t;
typedef __kernel_pid_t pid_t;
typedef __kernel_daddr_t daddr_t;
typedef __kernel_key_t key_t;
typedef __kernel_suseconds_t suseconds_t;
typedef __kernel_timer_t timer_t;
typedef __kernel_clockid_t clockid_t;
typedef __kernel_mqd_t mqd_t;


typedef __kernel_uid32_t uid_t;
typedef __kernel_gid32_t gid_t;
typedef __kernel_uid16_t uid16_t;
typedef __kernel_gid16_t gid16_t;
typedef __kernel_loff_t loff_t;
typedef __kernel_size_t size_t;




typedef __kernel_ssize_t ssize_t;




typedef __kernel_ptrdiff_t ptrdiff_t;




typedef __kernel_time_t time_t;




typedef __kernel_clock_t clock_t;




typedef __kernel_caddr_t caddr_t;



typedef unsigned char u_char;
typedef unsigned short u_short;
typedef unsigned int u_int;
typedef unsigned long u_long;


typedef unsigned char unchar;
typedef unsigned short ushort;
typedef unsigned int uint;
typedef unsigned long ulong;




typedef __u8 u_int8_t;
typedef __s8 int8_t;
typedef __u16 u_int16_t;
typedef __s16 int16_t;
typedef __u32 u_int32_t;
typedef __s32 int32_t;



typedef __u8 uint8_t;
typedef __u16 uint16_t;
typedef __u32 uint32_t;


typedef __u64 uint64_t;
typedef __u64 u_int64_t;
typedef __s64 int64_t;
typedef unsigned long sector_t;



typedef unsigned long blkcnt_t;
typedef __u16 __le16;
typedef __u16 __be16;
typedef __u32 __le32;
typedef __u32 __be32;

typedef __u64 __le64;
typedef __u64 __be64;



typedef unsigned gfp_t;




typedef u32 resource_size_t;




struct ustat {
 __kernel_daddr_t f_tfree;
 __kernel_ino_t f_tinode;
 char f_fname[6];
 char f_fpack[6];
};
typedef struct __user_cap_header_struct {
 __u32 version;
 int pid;
} *cap_user_header_t;

typedef struct __user_cap_data_struct {
        __u32 effective;
        __u32 permitted;
        __u32 inheritable;
} *cap_user_data_t;



struct restart_block {
 long (*fn)(struct restart_block *);
 unsigned long arg0, arg1, arg2, arg3;
};

extern long do_no_restart_syscall(struct restart_block *parm);

struct alt_instr {
 u8 *instr;
 u8 *replacement;
 u8 cpuid;
 u8 instrlen;
 u8 replacementlen;
 u8 pad;
};

extern void apply_alternatives(struct alt_instr *start, struct alt_instr *end);

struct module;







static inline /*__attribute__((always_inline))*/ void alternatives_smp_module_add(struct module *mod, char *name,
     void *locks, void *locks_end,
     void *text, void *text_end) {}
static inline /*__attribute__((always_inline))*/ void alternatives_smp_module_del(struct module *mod) {}
static inline /*__attribute__((always_inline))*/ void alternatives_smp_module_del(struct module *mod) {}
static inline /*__attribute__((always_inline))*/ void alternatives_smp_switch(int smp) {}
static inline /*__attribute__((always_inline))*/ void set_bit(int nr, volatile unsigned long * addr)
{
 __asm__ __volatile__( ""
  "btsl %1,%0"
  :"+m" ((*(volatile long *) addr))
  :"Ir" (nr));
}
static inline /*__attribute__((always_inline))*/ void __set_bit(int nr, volatile unsigned long * addr)
{
 __asm__(
  "btsl %1,%0"
  :"+m" ((*(volatile long *) addr))
  :"Ir" (nr));
}
static inline /*__attribute__((always_inline))*/ void clear_bit(int nr, volatile unsigned long * addr)
{
 __asm__ __volatile__( ""
  "btrl %1,%0"
  :"+m" ((*(volatile long *) addr))
  :"Ir" (nr));
}

static inline /*__attribute__((always_inline))*/ void __clear_bit(int nr, volatile unsigned long * addr)
{
 __asm__ __volatile__(
  "btrl %1,%0"
  :"+m" ((*(volatile long *) addr))
  :"Ir" (nr));
}
static inline /*__attribute__((always_inline))*/ void __change_bit(int nr, volatile unsigned long * addr)
{
 __asm__ __volatile__(
  "btcl %1,%0"
  :"+m" ((*(volatile long *) addr))
  :"Ir" (nr));
}
static inline /*__attribute__((always_inline))*/ void change_bit(int nr, volatile unsigned long * addr)
{
 __asm__ __volatile__( ""
  "btcl %1,%0"
  :"+m" ((*(volatile long *) addr))
  :"Ir" (nr));
}
static inline /*__attribute__((always_inline))*/ int test_and_set_bit(int nr, volatile unsigned long * addr)
{
 int oldbit;

 __asm__ __volatile__( ""
  "btsl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"+m" ((*(volatile long *) addr))
  :"Ir" (nr) : "memory");
 return oldbit;
}
static inline /*__attribute__((always_inline))*/ int __test_and_set_bit(int nr, volatile unsigned long * addr)
{
 int oldbit;

 __asm__(
  "btsl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"+m" ((*(volatile long *) addr))
  :"Ir" (nr));
 return oldbit;
}
static inline /*__attribute__((always_inline))*/ int test_and_clear_bit(int nr, volatile unsigned long * addr)
{
 int oldbit;

 __asm__ __volatile__( ""
  "btrl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"+m" ((*(volatile long *) addr))
  :"Ir" (nr) : "memory");
 return oldbit;
}
static inline /*__attribute__((always_inline))*/ int __test_and_clear_bit(int nr, volatile unsigned long *addr)
{
 int oldbit;

 __asm__(
  "btrl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"+m" ((*(volatile long *) addr))
  :"Ir" (nr));
 return oldbit;
}


static inline /*__attribute__((always_inline))*/ int __test_and_change_bit(int nr, volatile unsigned long *addr)
{
 int oldbit;

 __asm__ __volatile__(
  "btcl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"+m" ((*(volatile long *) addr))
  :"Ir" (nr) : "memory");
 return oldbit;
}
static inline /*__attribute__((always_inline))*/ int test_and_change_bit(int nr, volatile unsigned long* addr)
{
 int oldbit;

 __asm__ __volatile__( ""
  "btcl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit),"+m" ((*(volatile long *) addr))
  :"Ir" (nr) : "memory");
 return oldbit;
}
static inline /*__attribute__((always_inline))*/ /*__attribute__((always_inline))*/ int constant_test_bit(int nr, const volatile unsigned long *addr)
{
 return ((1UL << (nr & 31)) & (addr[nr >> 5])) != 0;
}

static inline /*__attribute__((always_inline))*/ int variable_test_bit(int nr, const volatile unsigned long * addr)
{
 int oldbit;

 __asm__ __volatile__(
  "btl %2,%1\n\tsbbl %0,%0"
  :"=r" (oldbit)
  :"m" ((*(volatile long *) addr)),"Ir" (nr));
 return oldbit;
}
static inline /*__attribute__((always_inline))*/ int find_first_zero_bit(const unsigned long *addr, unsigned size)
{
 int d0, d1, d2;
 int res;

 if (!size)
  return 0;

 __asm__ __volatile__(
  "movl $-1,%%eax\n\t"
  "xorl %%edx,%%edx\n\t"
  "repe; scasl\n\t"
  "je 1f\n\t"
  "xorl -4(%%edi),%%eax\n\t"
  "subl $4,%%edi\n\t"
  "bsfl %%eax,%%edx\n"
  "1:\tsubl %%ebx,%%edi\n\t"
  "shll $3,%%edi\n\t"
  "addl %%edi,%%edx"
  :"=d" (res), "=&c" (d0), "=&D" (d1), "=&a" (d2)
  :"1" ((size + 31) >> 5), "2" (addr), "b" (addr) : "memory");
 return res;
}







int find_next_zero_bit(const unsigned long *addr, int size, int offset);







static inline /*__attribute__((always_inline))*/ unsigned long __ffs(unsigned long word)
{
 __asm__("bsfl %1,%0"
  :"=r" (word)
  :"rm" (word));
 return word;
}
static inline /*__attribute__((always_inline))*/ unsigned find_first_bit(const unsigned long *addr, unsigned size)
{
 unsigned x = 0;

 while (x < size) {
  unsigned long val = *addr++;
  if (val)
   return __ffs(val) + x;
  x += (sizeof(*addr)<<3);
 }
 return x;
}







int find_next_bit(const unsigned long *addr, int size, int offset);







static inline /*__attribute__((always_inline))*/ unsigned long ffz(unsigned long word)
{
 __asm__("bsfl %1,%0"
  :"=r" (word)
  :"r" (~word));
 return word;
}



static inline /*__attribute__((always_inline))*/ int sched_find_first_bit(const unsigned long *b)
{







 if (__builtin_expect(!!(b[0]), 0))
  return __ffs(b[0]);
 if (__builtin_expect(!!(b[1]), 0))
  return __ffs(b[1]) + 32;
 if (__builtin_expect(!!(b[2]), 0))
  return __ffs(b[2]) + 64;
 if (b[3])
  return __ffs(b[3]) + 96;
 return __ffs(b[4]) + 128;



}
static inline /*__attribute__((always_inline))*/ int ffs(int x)
{
 int r;

 __asm__("bsfl %1,%0\n\t"
  "jnz 1f\n\t"
  "movl $-1,%0\n"
  "1:" : "=r" (r) : "rm" (x));
 return r+1;
}







static inline /*__attribute__((always_inline))*/ int fls(int x)
{
 int r;

 __asm__("bsrl %1,%0\n\t"
  "jnz 1f\n\t"
  "movl $-1,%0\n"
  "1:" : "=r" (r) : "rm" (x));
 return r+1;
}






extern unsigned int hweight32(unsigned int w);
extern unsigned int hweight16(unsigned int w);
extern unsigned int hweight8(unsigned int w);
extern unsigned long hweight64(__u64 w);








static inline /*__attribute__((always_inline))*/ int fls64(__u64 x)
{
 __u32 h = x >> 32;
 if (h)
  return fls(h) + 32;
 return fls(x);
}










static __inline__ /*__attribute__((always_inline))*/ /*__attribute__((__const__))*/ __u32 ___arch__swab32(__u32 x)
{



 __asm__("xchgb %b0,%h0\n\t"
  "rorl $16,%0\n\t"
  "xchgb %b0,%h0"
  :"=q" (x)
  : "0" (x));

 return x;
}

static __inline__ /*__attribute__((always_inline))*/ /*__attribute__((__const__))*/ __u64 ___arch__swab64(__u64 val)
{
 union {
  struct { __u32 a,b; } s;
  __u64 u;
 } v;
 v.u = val;





   v.s.a = ___arch__swab32(v.s.a);
 v.s.b = ___arch__swab32(v.s.b);
 asm("xchgl %0,%1" : "=r" (v.s.a), "=r" (v.s.b) : "0" (v.s.a), "1" (v.s.b));

 return v.u;
}
static __inline__ /*__attribute__((always_inline))*/ /*__attribute__((__const__))*/ __u16 __fswab16(__u16 x)
{
 return ({ __u16 __tmp = (x) ; ({ __u16 __x = (__tmp); ((__u16)( (((__u16)(__x) & (__u16)0x00ffU) << 8) | (((__u16)(__x) & (__u16)0xff00U) >> 8) )); }); });
}
static __inline__ /*__attribute__((always_inline))*/ __u16 __swab16p(const __u16 *x)
{
 return ({ __u16 __tmp = (*(x)) ; ({ __u16 __x = (__tmp); ((__u16)( (((__u16)(__x) & (__u16)0x00ffU) << 8) | (((__u16)(__x) & (__u16)0xff00U) >> 8) )); }); });
}
static __inline__ /*__attribute__((always_inline))*/ void __swab16s(__u16 *addr)
{
 do { *(addr) = ({ __u16 __tmp = (*((addr))) ; ({ __u16 __x = (__tmp); ((__u16)( (((__u16)(__x) & (__u16)0x00ffU) << 8) | (((__u16)(__x) & (__u16)0xff00U) >> 8) )); }); }); } while (0);
}

static __inline__ /*__attribute__((always_inline))*/ /*__attribute__((__const__))*/ __u32 __fswab32(__u32 x)
{
 return ___arch__swab32(x);
}
static __inline__ /*__attribute__((always_inline))*/ __u32 __swab32p(const __u32 *x)
{
 return ___arch__swab32(*(x));
}
static __inline__ /*__attribute__((always_inline))*/ void __swab32s(__u32 *addr)
{
 do { *(addr) = ___arch__swab32(*((addr))); } while (0);
}


static __inline__ /*__attribute__((always_inline))*/ /*__attribute__((__const__))*/ __u64 __fswab64(__u64 x)
{





 return ___arch__swab64(x);

}
static __inline__ /*__attribute__((always_inline))*/ __u64 __swab64p(const __u64 *x)
{
 return ___arch__swab64(*(x));
}
static __inline__ /*__attribute__((always_inline))*/ void __swab64s(__u64 *addr)
{
 do { *(addr) = ___arch__swab64(*((addr))); } while (0);
}
static inline /*__attribute__((always_inline))*/ __le64 __cpu_to_le64p(const __u64 *p)
{
 return ( __le64)*p;
}
static inline /*__attribute__((always_inline))*/ __u64 __le64_to_cpup(const __le64 *p)
{
 return ( __u64)*p;
}
static inline /*__attribute__((always_inline))*/ __le32 __cpu_to_le32p(const __u32 *p)
{
 return ( __le32)*p;
}
static inline /*__attribute__((always_inline))*/ __u32 __le32_to_cpup(const __le32 *p)
{
 return ( __u32)*p;
}
static inline /*__attribute__((always_inline))*/ __le16 __cpu_to_le16p(const __u16 *p)
{
 return ( __le16)*p;
}
static inline /*__attribute__((always_inline))*/ __u16 __le16_to_cpup(const __le16 *p)
{
 return ( __u16)*p;
}
static inline /*__attribute__((always_inline))*/ __be64 __cpu_to_be64p(const __u64 *p)
{
 return ( __be64)__swab64p(p);
}
static inline /*__attribute__((always_inline))*/ __u64 __be64_to_cpup(const __be64 *p)
{
 return __swab64p((__u64 *)p);
}
static inline /*__attribute__((always_inline))*/ __be32 __cpu_to_be32p(const __u32 *p)
{
 return ( __be32)__swab32p(p);
}
static inline /*__attribute__((always_inline))*/ __u32 __be32_to_cpup(const __be32 *p)
{
 return __swab32p((__u32 *)p);
}
static inline /*__attribute__((always_inline))*/ __be16 __cpu_to_be16p(const __u16 *p)
{
 return ( __be16)__swab16p(p);
}
static inline /*__attribute__((always_inline))*/ __u16 __be16_to_cpup(const __be16 *p)
{
 return __swab16p((__u16 *)p);
}
extern __u32 ntohl(__be32);
extern __be32 htonl(__u32);
extern __u16 ntohs(__be16);
extern __be16 htons(__u16);







static __inline__ /*__attribute__((always_inline))*/ int get_bitmask_order(unsigned int count)
{
 int order;

 order = fls(count);
 return order;
}

static __inline__ /*__attribute__((always_inline))*/ int get_count_order(unsigned int count)
{
 int order;

 order = fls(count) - 1;
 if (count & (count - 1))
  order++;
 return order;
}

static inline /*__attribute__((always_inline))*/ unsigned long hweight_long(unsigned long w)
{
 return sizeof(w) == 4 ? hweight32(w) : hweight64(w);
}







static inline /*__attribute__((always_inline))*/ __u32 rol32(__u32 word, unsigned int shift)
{
 return (word << shift) | (word >> (32 - shift));
}







static inline /*__attribute__((always_inline))*/ __u32 ror32(__u32 word, unsigned int shift)
{
 return (word >> shift) | (word << (32 - shift));
}

static inline /*__attribute__((always_inline))*/ unsigned fls_long(unsigned long l)
{
 if (sizeof(l) == 4)
  return fls(l);
 return fls64(l);
}
extern int nx_enabled;
typedef struct { unsigned long pte_low; } pte_t;
typedef struct { unsigned long pgd; } pgd_t;
typedef struct { unsigned long pgprot; } pgprot_t;
struct vm_area_struct;





extern unsigned int __VMALLOC_RESERVE;

extern int sysctl_legacy_va_layout;

extern int page_is_ram(unsigned long pagenr);
static __inline__ /*__attribute__((always_inline))*/ /*__attribute__((__const__))*/ int get_order(unsigned long size)
{
 int order;

 size = (size - 1) >> (12 - 1);
 order = -1;
 do {
  size >>= 1;
  order++;
 } while (size);
 return order;
}


struct vm86_regs {



 long ebx;
 long ecx;
 long edx;
 long esi;
 long edi;
 long ebp;
 long eax;
 long __null_ds;
 long __null_es;
 long __null_fs;
 long __null_gs;
 long orig_eax;
 long eip;
 unsigned short cs, __csh;
 long eflags;
 long esp;
 unsigned short ss, __ssh;



 unsigned short es, __esh;
 unsigned short ds, __dsh;
 unsigned short fs, __fsh;
 unsigned short gs, __gsh;
};

struct revectored_struct {
 unsigned long __map[8];
};

struct vm86_struct {
 struct vm86_regs regs;
 unsigned long flags;
 unsigned long screen_bitmap;
 unsigned long cpu_type;
 struct revectored_struct int_revectored;
 struct revectored_struct int21_revectored;
};






struct vm86plus_info_struct {
 unsigned long force_return_for_pic:1;
 unsigned long vm86dbg_active:1;
 unsigned long vm86dbg_TFpendig:1;
 unsigned long unused:28;
 unsigned long is_vm86pus:1;
 unsigned char vm86dbg_intxxtab[32];
};

struct vm86plus_struct {
 struct vm86_regs regs;
 unsigned long flags;
 unsigned long screen_bitmap;
 unsigned long cpu_type;
 struct revectored_struct int_revectored;
 struct revectored_struct int21_revectored;
 struct vm86plus_info_struct vm86plus;
};
struct kernel_vm86_regs {



 long ebx;
 long ecx;
 long edx;
 long esi;
 long edi;
 long ebp;
 long eax;
 long __null_ds;
 long __null_es;
 long orig_eax;
 long eip;
 unsigned short cs, __csh;
 long eflags;
 long esp;
 unsigned short ss, __ssh;



 unsigned short es, __esh;
 unsigned short ds, __dsh;
 unsigned short fs, __fsh;
 unsigned short gs, __gsh;
};

struct kernel_vm86_struct {
 struct kernel_vm86_regs regs;
 unsigned long flags;
 unsigned long screen_bitmap;
 unsigned long cpu_type;
 struct revectored_struct int_revectored;
 struct revectored_struct int21_revectored;
 struct vm86plus_info_struct vm86plus;
 struct pt_regs *regs32;
};
static inline /*__attribute__((always_inline))*/ int handle_vm86_trap(struct kernel_vm86_regs *a, long b, int c) {
 return 0;
}



struct _fpreg {
 unsigned short significand[4];
 unsigned short exponent;
};

struct _fpxreg {
 unsigned short significand[4];
 unsigned short exponent;
 unsigned short padding[3];
};

struct _xmmreg {
 unsigned long element[4];
};

struct _fpstate {

 unsigned long cw;
 unsigned long sw;
 unsigned long tag;
 unsigned long ipoff;
 unsigned long cssel;
 unsigned long dataoff;
 unsigned long datasel;
 struct _fpreg _st[8];
 unsigned short status;
 unsigned short magic;


 unsigned long _fxsr_env[6];
 unsigned long mxcsr;
 unsigned long reserved;
 struct _fpxreg _fxsr_st[8];
 struct _xmmreg _xmm[8];
 unsigned long padding[56];
};



struct sigcontext {
 unsigned short gs, __gsh;
 unsigned short fs, __fsh;
 unsigned short es, __esh;
 unsigned short ds, __dsh;
 unsigned long edi;
 unsigned long esi;
 unsigned long ebp;
 unsigned long esp;
 unsigned long ebx;
 unsigned long edx;
 unsigned long ecx;
 unsigned long eax;
 unsigned long trapno;
 unsigned long err;
 unsigned long eip;
 unsigned short cs, __csh;
 unsigned long eflags;
 unsigned long esp_at_signal;
 unsigned short ss, __ssh;
 struct _fpstate * fpstate;
 unsigned long oldmask;
 unsigned long cr2;
};

int restore_i387_soft(void *s387, struct _fpstate *buf);
int save_i387_soft(void *s387, struct _fpstate *buf);





struct info {
 long ___orig_eip;
 long ___ebx;
 long ___ecx;
 long ___edx;
 long ___esi;
 long ___edi;
 long ___ebp;
 long ___eax;
 long ___ds;
 long ___es;
 long ___orig_eax;
 long ___eip;
 long ___cs;
 long ___eflags;
 long ___esp;
 long ___ss;
 long ___vm86_es;
 long ___vm86_ds;
 long ___vm86_fs;
 long ___vm86_gs;
};



static inline /*__attribute__((always_inline))*/ void wrmsrl (unsigned long msr, unsigned long long val)
{
 unsigned long lo, hi;
 lo = (unsigned long) val;
 hi = val >> 32;
 __asm__ __volatile__("wrmsr" : : "c" (msr), "a" (lo), "d" (hi));
}



typedef /*__builtin_va_list*/int/**/ __gnuc_va_list;
typedef __gnuc_va_list va_list;









extern const char linux_banner[];
extern int console_printk[];






struct completion;
struct pt_regs;
struct user;
extern struct atomic_notifier_head panic_notifier_list;
extern long (*panic_blink)(long time);
 void panic(const char * fmt, ...)
 /*__attribute__ ((noreturn, format (printf, 1, 2)))*/;
extern void oops_enter(void);
extern void oops_exit(void);
extern int oops_may_print(void);
/*__attribute__((regparm(3)))*/ void do_exit(long error_code)
 /*__attribute__((noreturn))*/;
 void complete_and_exit(struct completion *, long)
 /*__attribute__((noreturn))*/;
extern unsigned long simple_strtoul(const char *,char **,unsigned int);
extern long simple_strtol(const char *,char **,unsigned int);
extern unsigned long long simple_strtoull(const char *,char **,unsigned int);
extern long long simple_strtoll(const char *,char **,unsigned int);
extern int sprintf(char * buf, const char * fmt, ...)
 __attribute__ ((format (printf, 2, 3)));
extern int vsprintf(char *buf, const char *, va_list)
 __attribute__ ((format (printf, 2, 0)));
extern int snprintf(char * buf, size_t size, const char * fmt, ...)
 __attribute__ ((format (printf, 3, 4)));
extern int vsnprintf(char *buf, size_t size, const char *fmt, va_list args)
 __attribute__ ((format (printf, 3, 0)));
extern int scnprintf(char * buf, size_t size, const char * fmt, ...)
 __attribute__ ((format (printf, 3, 4)));
extern int vscnprintf(char *buf, size_t size, const char *fmt, va_list args)
 __attribute__ ((format (printf, 3, 0)));
extern char *kasprintf(gfp_t gfp, const char *fmt, ...)
 __attribute__ ((format (printf, 2, 3)));

extern int sscanf(const char *, const char *, ...)
 __attribute__ ((format (scanf, 2, 3)));
extern int vsscanf(const char *, const char *, va_list)
 __attribute__ ((format (scanf, 2, 0)));

extern int get_option(char **str, int *pint);
extern char *get_options(const char *str, int nints, int *ints);
extern unsigned long long memparse(char *ptr, char **retptr);

extern int core_kernel_text(unsigned long addr);
extern int __kernel_text_address(unsigned long addr);
extern int kernel_text_address(unsigned long addr);
extern int session_of_pgrp(int pgrp);

extern void dump_thread(struct pt_regs *regs, struct user *dump);







static inline /*__attribute__((always_inline))*/ int vprintk(const char *s, va_list args)
 __attribute__ ((format (printf, 1, 0)));
static inline /*__attribute__((always_inline))*/ int vprintk(const char *s, va_list args) { return 0; }
static inline /*__attribute__((always_inline))*/ int printk(const char *s, ...)
 __attribute__ ((format (printf, 1, 2)));
static inline /*__attribute__((always_inline))*/ int printk(const char *s, ...) { return 0; }


unsigned long int_sqrt(unsigned long);

static inline /*__attribute__((always_inline))*/ int /*__attribute__((pure))*/ long_log2(unsigned long x)
{
 int r = 0;
 for (x >>= 1; x > 0; x >>= 1)
  r++;
 return r;
}

static inline /*__attribute__((always_inline))*/ unsigned long
/*__attribute__((__const__))*/ roundup_pow_of_two(unsigned long x)
{
 return 1UL << fls_long(x - 1);
}

extern int printk_ratelimit(void);
extern int __printk_ratelimit(int ratelimit_jiffies, int ratelimit_burst);

static inline /*__attribute__((always_inline))*/ void console_silent(void)
{
 (console_printk[0]) = 0;
}

static inline /*__attribute__((always_inline))*/ void console_verbose(void)
{
 if ((console_printk[0]))
  (console_printk[0]) = 15;
}

extern void bust_spinlocks(int yes);
extern int oops_in_progress;
extern int panic_timeout;
extern int panic_on_oops;
extern int tainted;
extern const char *print_tainted(void);
extern void add_taint(unsigned);


extern enum system_states {
 SYSTEM_BOOTING,
 SYSTEM_RUNNING,
 SYSTEM_HALT,
 SYSTEM_POWER_OFF,
 SYSTEM_RESTART,
 SYSTEM_SUSPEND_DISK,
} system_state;
extern void dump_stack(void);
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
 unsigned short pad;
 unsigned long totalhigh;
 unsigned long freehigh;
 unsigned int mem_unit;
 char _f[20-2*sizeof(long)-sizeof(int)];
};






struct task_struct;
extern struct task_struct * __switch_to(struct task_struct *prev, struct task_struct *next) /*__attribute__((regparm(3)))*/;
static inline /*__attribute__((always_inline))*/ unsigned long get_limit(unsigned long segment)
{
 unsigned long __limit;
 __asm__("lsll %1,%0"
  :"=r" (__limit):"r" (segment));
 return __limit+1;
}







struct __xchg_dummy { unsigned long a[100]; };
static inline /*__attribute__((always_inline))*/ unsigned long __xchg(unsigned long x, volatile void * ptr, int size)
{
 switch (size) {
  case 1:
   __asm__ __volatile__("xchgb %b0,%1"
    :"=q" (x)
    :"m" (*((struct __xchg_dummy *)(ptr))), "0" (x)
    :"memory");
   break;
  case 2:
   __asm__ __volatile__("xchgw %w0,%1"
    :"=r" (x)
    :"m" (*((struct __xchg_dummy *)(ptr))), "0" (x)
    :"memory");
   break;
  case 4:
   __asm__ __volatile__("xchgl %0,%1"
    :"=r" (x)
    :"m" (*((struct __xchg_dummy *)(ptr))), "0" (x)
    :"memory");
   break;
 }
 return x;
}
static inline /*__attribute__((always_inline))*/ unsigned long __cmpxchg(volatile void *ptr, unsigned long old,
          unsigned long new, int size)
{
 unsigned long prev;
 switch (size) {
 case 1:
  __asm__ __volatile__("" "cmpxchgb %b1,%2"
         : "=a"(prev)
         : "q"(new), "m"(*((struct __xchg_dummy *)(ptr))), "0"(old)
         : "memory");
  return prev;
 case 2:
  __asm__ __volatile__("" "cmpxchgw %w1,%2"
         : "=a"(prev)
         : "r"(new), "m"(*((struct __xchg_dummy *)(ptr))), "0"(old)
         : "memory");
  return prev;
 case 4:
  __asm__ __volatile__("" "cmpxchgl %1,%2"
         : "=a"(prev)
         : "r"(new), "m"(*((struct __xchg_dummy *)(ptr))), "0"(old)
         : "memory");
  return prev;
 }
 return old;
}
extern unsigned long cmpxchg_386_u8(volatile void *, u8, u8);
extern unsigned long cmpxchg_386_u16(volatile void *, u16, u16);
extern unsigned long cmpxchg_386_u32(volatile void *, u32, u32);

static inline /*__attribute__((always_inline))*/ unsigned long cmpxchg_386(volatile void *ptr, unsigned long old,
          unsigned long new, int size)
{
 switch (size) {
 case 1:
  return cmpxchg_386_u8(ptr, old, new);
 case 2:
  return cmpxchg_386_u16(ptr, old, new);
 case 4:
  return cmpxchg_386_u32(ptr, old, new);
 }
 return old;
}





void disable_hlt(void);
void enable_hlt(void);

extern int es7000_plat;
void cpu_idle_wait(void);





static inline /*__attribute__((always_inline))*/ void sched_cacheflush(void)
{
 __asm__ __volatile__ ("wbinvd": : :"memory");
}

extern unsigned long arch_align_stack(unsigned long sp);
extern void free_init_pages(char *what, unsigned long begin, unsigned long end);

void default_idle(void);














extern char *strndup_user(const char *, long);




static inline /*__attribute__((always_inline))*/ char * strcpy(char * dest,const char *src)
{
int d0, d1, d2;
__asm__ __volatile__(
 "1:\tlodsb\n\t"
 "stosb\n\t"
 "testb %%al,%%al\n\t"
 "jne 1b"
 : "=&S" (d0), "=&D" (d1), "=&a" (d2)
 :"0" (src),"1" (dest) : "memory");
return dest;
}


static inline /*__attribute__((always_inline))*/ char * strncpy(char * dest,const char *src,size_t count)
{
int d0, d1, d2, d3;
__asm__ __volatile__(
 "1:\tdecl %2\n\t"
 "js 2f\n\t"
 "lodsb\n\t"
 "stosb\n\t"
 "testb %%al,%%al\n\t"
 "jne 1b\n\t"
 "rep\n\t"
 "stosb\n"
 "2:"
 : "=&S" (d0), "=&D" (d1), "=&c" (d2), "=&a" (d3)
 :"0" (src),"1" (dest),"2" (count) : "memory");
return dest;
}


static inline /*__attribute__((always_inline))*/ char * strcat(char * dest,const char * src)
{
int d0, d1, d2, d3;
__asm__ __volatile__(
 "repne\n\t"
 "scasb\n\t"
 "decl %1\n"
 "1:\tlodsb\n\t"
 "stosb\n\t"
 "testb %%al,%%al\n\t"
 "jne 1b"
 : "=&S" (d0), "=&D" (d1), "=&a" (d2), "=&c" (d3)
 : "0" (src), "1" (dest), "2" (0), "3" (0xffffffffu):"memory");
return dest;
}


static inline /*__attribute__((always_inline))*/ char * strncat(char * dest,const char * src,size_t count)
{
int d0, d1, d2, d3;
__asm__ __volatile__(
 "repne\n\t"
 "scasb\n\t"
 "decl %1\n\t"
 "movl %8,%3\n"
 "1:\tdecl %3\n\t"
 "js 2f\n\t"
 "lodsb\n\t"
 "stosb\n\t"
 "testb %%al,%%al\n\t"
 "jne 1b\n"
 "2:\txorl %2,%2\n\t"
 "stosb"
 : "=&S" (d0), "=&D" (d1), "=&a" (d2), "=&c" (d3)
 : "0" (src),"1" (dest),"2" (0),"3" (0xffffffffu), "g" (count)
 : "memory");
return dest;
}


static inline /*__attribute__((always_inline))*/ int strcmp(const char * cs,const char * ct)
{
int d0, d1;
register int __res;
__asm__ __volatile__(
 "1:\tlodsb\n\t"
 "scasb\n\t"
 "jne 2f\n\t"
 "testb %%al,%%al\n\t"
 "jne 1b\n\t"
 "xorl %%eax,%%eax\n\t"
 "jmp 3f\n"
 "2:\tsbbl %%eax,%%eax\n\t"
 "orb $1,%%al\n"
 "3:"
 :"=a" (__res), "=&S" (d0), "=&D" (d1)
 :"1" (cs),"2" (ct)
 :"memory");
return __res;
}


static inline /*__attribute__((always_inline))*/ int strncmp(const char * cs,const char * ct,size_t count)
{
register int __res;
int d0, d1, d2;
__asm__ __volatile__(
 "1:\tdecl %3\n\t"
 "js 2f\n\t"
 "lodsb\n\t"
 "scasb\n\t"
 "jne 3f\n\t"
 "testb %%al,%%al\n\t"
 "jne 1b\n"
 "2:\txorl %%eax,%%eax\n\t"
 "jmp 4f\n"
 "3:\tsbbl %%eax,%%eax\n\t"
 "orb $1,%%al\n"
 "4:"
 :"=a" (__res), "=&S" (d0), "=&D" (d1), "=&c" (d2)
 :"1" (cs),"2" (ct),"3" (count)
 :"memory");
return __res;
}


static inline /*__attribute__((always_inline))*/ char * strchr(const char * s, int c)
{
int d0;
register char * __res;
__asm__ __volatile__(
 "movb %%al,%%ah\n"
 "1:\tlodsb\n\t"
 "cmpb %%ah,%%al\n\t"
 "je 2f\n\t"
 "testb %%al,%%al\n\t"
 "jne 1b\n\t"
 "movl $1,%1\n"
 "2:\tmovl %1,%0\n\t"
 "decl %0"
 :"=a" (__res), "=&S" (d0)
 :"1" (s),"0" (c)
 :"memory");
return __res;
}


static inline /*__attribute__((always_inline))*/ char * strrchr(const char * s, int c)
{
int d0, d1;
register char * __res;
__asm__ __volatile__(
 "movb %%al,%%ah\n"
 "1:\tlodsb\n\t"
 "cmpb %%ah,%%al\n\t"
 "jne 2f\n\t"
 "leal -1(%%esi),%0\n"
 "2:\ttestb %%al,%%al\n\t"
 "jne 1b"
 :"=g" (__res), "=&S" (d0), "=&a" (d1)
 :"0" (0),"1" (s),"2" (c)
 :"memory");
return __res;
}


static inline /*__attribute__((always_inline))*/ size_t strlen(const char * s)
{
int d0;
register int __res;
__asm__ __volatile__(
 "repne\n\t"
 "scasb\n\t"
 "notl %0\n\t"
 "decl %0"
 :"=c" (__res), "=&D" (d0)
 :"1" (s),"a" (0), "0" (0xffffffffu)
 :"memory");
return __res;
}

static inline /*__attribute__((always_inline))*/ /*__attribute__((always_inline))*/ void * __memcpy(void * to, const void * from, size_t n)
{
int d0, d1, d2;
__asm__ __volatile__(
 "rep ; movsl\n\t"
 "movl %4,%%ecx\n\t"
 "andl $3,%%ecx\n\t"

 "jz 1f\n\t"

 "rep ; movsb\n\t"
 "1:"
 : "=&c" (d0), "=&D" (d1), "=&S" (d2)
 : "0" (n/4), "g" (n), "1" ((long) to), "2" ((long) from)
 : "memory");
return (to);
}





static inline /*__attribute__((always_inline))*/ /*__attribute__((always_inline))*/ void * __constant_memcpy(void * to, const void * from, size_t n)
{
 long esi, edi;
 if (!n) return to;

 switch (n) {
  case 1: *(char*)to = *(char*)from; return to;
  case 2: *(short*)to = *(short*)from; return to;
  case 4: *(int*)to = *(int*)from; return to;

  case 3: *(short*)to = *(short*)from;
   *((char*)to+2) = *((char*)from+2); return to;
  case 5: *(int*)to = *(int*)from;
   *((char*)to+4) = *((char*)from+4); return to;
  case 6: *(int*)to = *(int*)from;
   *((short*)to+2) = *((short*)from+2); return to;
  case 8: *(int*)to = *(int*)from;
   *((int*)to+1) = *((int*)from+1); return to;

 }

 esi = (long) from;
 edi = (long) to;
 if (n >= 5*4) {

  int ecx;
  __asm__ __volatile__(
   "rep ; movsl"
   : "=&c" (ecx), "=&D" (edi), "=&S" (esi)
   : "0" (n/4), "1" (edi),"2" (esi)
   : "memory"
  );
 } else {

  if (n >= 4*4) __asm__ __volatile__("movsl"
   :"=&D"(edi),"=&S"(esi):"0"(edi),"1"(esi):"memory");
  if (n >= 3*4) __asm__ __volatile__("movsl"
   :"=&D"(edi),"=&S"(esi):"0"(edi),"1"(esi):"memory");
  if (n >= 2*4) __asm__ __volatile__("movsl"
   :"=&D"(edi),"=&S"(esi):"0"(edi),"1"(esi):"memory");
  if (n >= 1*4) __asm__ __volatile__("movsl"
   :"=&D"(edi),"=&S"(esi):"0"(edi),"1"(esi):"memory");
 }
 switch (n % 4) {

  case 0: return to;
  case 1: __asm__ __volatile__("movsb"
   :"=&D"(edi),"=&S"(esi):"0"(edi),"1"(esi):"memory");
   return to;
  case 2: __asm__ __volatile__("movsw"
   :"=&D"(edi),"=&S"(esi):"0"(edi),"1"(esi):"memory");
   return to;
  default: __asm__ __volatile__("movsw\n\tmovsb"
   :"=&D"(edi),"=&S"(esi):"0"(edi),"1"(esi):"memory");
   return to;
 }
}
void *memmove(void * dest,const void * src, size_t n);




static inline /*__attribute__((always_inline))*/ void * memchr(const void * cs,int c,size_t count)
{
int d0;
register void * __res;
if (!count)
 return ((void *)0);
__asm__ __volatile__(
 "repne\n\t"
 "scasb\n\t"
 "je 1f\n\t"
 "movl $1,%0\n"
 "1:\tdecl %0"
 :"=D" (__res), "=&c" (d0)
 :"a" (c),"0" (cs),"1" (count)
 :"memory");
return __res;
}

static inline /*__attribute__((always_inline))*/ void * __memset_generic(void * s, char c,size_t count)
{
int d0, d1;
__asm__ __volatile__(
 "rep\n\t"
 "stosb"
 : "=&c" (d0), "=&D" (d1)
 :"a" (c),"1" (s),"0" (count)
 :"memory");
return s;
}
static inline /*__attribute__((always_inline))*/ /*__attribute__((always_inline))*/ void * __constant_c_memset(void * s, unsigned long c, size_t count)
{
int d0, d1;
__asm__ __volatile__(
 "rep ; stosl\n\t"
 "testb $2,%b3\n\t"
 "je 1f\n\t"
 "stosw\n"
 "1:\ttestb $1,%b3\n\t"
 "je 2f\n\t"
 "stosb\n"
 "2:"
 :"=&c" (d0), "=&D" (d1)
 :"a" (c), "q" (count), "0" (count/4), "1" ((long) s)
 :"memory");
return (s);
}



static inline /*__attribute__((always_inline))*/ size_t strnlen(const char * s, size_t count)
{
int d0;
register int __res;
__asm__ __volatile__(
 "movl %2,%0\n\t"
 "jmp 2f\n"
 "1:\tcmpb $0,(%0)\n\t"
 "je 3f\n\t"
 "incl %0\n"
 "2:\tdecl %1\n\t"
 "cmpl $-1,%1\n\t"
 "jne 1b\n"
 "3:\tsubl %2,%0"
 :"=a" (__res), "=&d" (d0)
 :"c" (s),"1" (count)
 :"memory");
return __res;
}




extern char *strstr(const char *cs, const char *ct);





static inline /*__attribute__((always_inline))*/ /*__attribute__((always_inline))*/ void * __constant_c_and_count_memset(void * s, unsigned long pattern, size_t count)
{
 switch (count) {
  case 0:
   return s;
  case 1:
   *(unsigned char *)s = pattern;
   return s;
  case 2:
   *(unsigned short *)s = pattern;
   return s;
  case 3:
   *(unsigned short *)s = pattern;
   *(2+(unsigned char *)s) = pattern;
   return s;
  case 4:
   *(unsigned long *)s = pattern;
   return s;
 }







{
 int d0, d1;
 switch (count % 4) {
  case 0: __asm__ __volatile__( "rep ; stosl" "" : "=&c" (d0), "=&D" (d1) : "a" (pattern),"0" (count/4),"1" ((long) s) : "memory"); return s;
  case 1: __asm__ __volatile__( "rep ; stosl" "\n\tstosb" : "=&c" (d0), "=&D" (d1) : "a" (pattern),"0" (count/4),"1" ((long) s) : "memory"); return s;
  case 2: __asm__ __volatile__( "rep ; stosl" "\n\tstosw" : "=&c" (d0), "=&D" (d1) : "a" (pattern),"0" (count/4),"1" ((long) s) : "memory"); return s;
  default: __asm__ __volatile__( "rep ; stosl" "\n\tstosw\n\tstosb" : "=&c" (d0), "=&D" (d1) : "a" (pattern),"0" (count/4),"1" ((long) s) : "memory"); return s;
 }
}


}
static inline /*__attribute__((always_inline))*/ void * memscan(void * addr, int c, size_t size)
{
 if (!size)
  return addr;
 __asm__("repnz; scasb\n\t"
  "jnz 1f\n\t"
  "dec %%edi\n"
  "1:"
  : "=D" (addr), "=c" (size)
  : "0" (addr), "1" (size), "a" (c)
  : "memory");
 return addr;
}
size_t strlcpy(char *, const char *, size_t);
extern size_t strlcat(char *, const char *, __kernel_size_t);
extern int strnicmp(const char *, const char *, __kernel_size_t);





extern char * strnchr(const char *, size_t, int);




extern char * strstrip(char *);
extern char * strpbrk(const char *,const char *);


extern char * strsep(char **,const char *);


extern __kernel_size_t strspn(const char *,const char *);


extern __kernel_size_t strcspn(const char *,const char *);
extern int __builtin_memcmp(const void *,const void *,__kernel_size_t);





extern char *kstrdup(const char *s, gfp_t gfp);
extern int __bitmap_empty(const unsigned long *bitmap, int bits);
extern int __bitmap_full(const unsigned long *bitmap, int bits);
extern int __bitmap_equal(const unsigned long *bitmap1,
                 const unsigned long *bitmap2, int bits);
extern void __bitmap_complement(unsigned long *dst, const unsigned long *src,
   int bits);
extern void __bitmap_shift_right(unsigned long *dst,
                        const unsigned long *src, int shift, int bits);
extern void __bitmap_shift_left(unsigned long *dst,
                        const unsigned long *src, int shift, int bits);
extern void __bitmap_and(unsigned long *dst, const unsigned long *bitmap1,
   const unsigned long *bitmap2, int bits);
extern void __bitmap_or(unsigned long *dst, const unsigned long *bitmap1,
   const unsigned long *bitmap2, int bits);
extern void __bitmap_xor(unsigned long *dst, const unsigned long *bitmap1,
   const unsigned long *bitmap2, int bits);
extern void __bitmap_andnot(unsigned long *dst, const unsigned long *bitmap1,
   const unsigned long *bitmap2, int bits);
extern int __bitmap_intersects(const unsigned long *bitmap1,
   const unsigned long *bitmap2, int bits);
extern int __bitmap_subset(const unsigned long *bitmap1,
   const unsigned long *bitmap2, int bits);
extern int __bitmap_weight(const unsigned long *bitmap, int bits);

extern int bitmap_scnprintf(char *buf, unsigned int len,
   const unsigned long *src, int nbits);
extern int bitmap_parse(const char *ubuf, unsigned int ulen,
   unsigned long *dst, int nbits);
extern int bitmap_scnlistprintf(char *buf, unsigned int len,
   const unsigned long *src, int nbits);
extern int bitmap_parselist(const char *buf, unsigned long *maskp,
   int nmaskbits);
extern void bitmap_remap(unsigned long *dst, const unsigned long *src,
  const unsigned long *old, const unsigned long *new, int bits);
extern int bitmap_bitremap(int oldbit,
  const unsigned long *old, const unsigned long *new, int bits);
extern int bitmap_find_free_region(unsigned long *bitmap, int bits, int order);
extern void bitmap_release_region(unsigned long *bitmap, int pos, int order);
extern int bitmap_allocate_region(unsigned long *bitmap, int pos, int order);







static inline /*__attribute__((always_inline))*/ void bitmap_zero(unsigned long *dst, int nbits)
{
 if (nbits <= 32)
  *dst = 0UL;
 else {
  int len = (((nbits)+32 -1)/32) * sizeof(unsigned long);
  (__builtin_constant_p(0) ? (__builtin_constant_p((len)) ? __constant_c_and_count_memset(((dst)),((0x01010101UL*(unsigned char)(0))),((len))) : __constant_c_memset(((dst)),((0x01010101UL*(unsigned char)(0))),((len)))) : (__builtin_constant_p((len)) ? __memset_generic((((dst))),(((0))),(((len)))) : __memset_generic(((dst)),((0)),((len)))));
 }
}

static inline /*__attribute__((always_inline))*/ void bitmap_fill(unsigned long *dst, int nbits)
{
 size_t nlongs = (((nbits)+32 -1)/32);
 if (nlongs > 1) {
  int len = (nlongs - 1) * sizeof(unsigned long);
  (__builtin_constant_p(0xff) ? (__builtin_constant_p((len)) ? __constant_c_and_count_memset(((dst)),((0x01010101UL*(unsigned char)(0xff))),((len))) : __constant_c_memset(((dst)),((0x01010101UL*(unsigned char)(0xff))),((len)))) : (__builtin_constant_p((len)) ? __memset_generic((((dst))),(((0xff))),(((len)))) : __memset_generic(((dst)),((0xff)),((len)))));
 }
 dst[nlongs - 1] = ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL );
}

static inline /*__attribute__((always_inline))*/ void bitmap_copy(unsigned long *dst, const unsigned long *src,
   int nbits)
{
 if (nbits <= 32)
  *dst = *src;
 else {
  int len = (((nbits)+32 -1)/32) * sizeof(unsigned long);
  (__builtin_constant_p(len) ? __constant_memcpy((dst),(src),(len)) : __memcpy((dst),(src),(len)));
 }
}

static inline /*__attribute__((always_inline))*/ void bitmap_and(unsigned long *dst, const unsigned long *src1,
   const unsigned long *src2, int nbits)
{
 if (nbits <= 32)
  *dst = *src1 & *src2;
 else
  __bitmap_and(dst, src1, src2, nbits);
}

static inline /*__attribute__((always_inline))*/ void bitmap_or(unsigned long *dst, const unsigned long *src1,
   const unsigned long *src2, int nbits)
{
 if (nbits <= 32)
  *dst = *src1 | *src2;
 else
  __bitmap_or(dst, src1, src2, nbits);
}

static inline /*__attribute__((always_inline))*/ void bitmap_xor(unsigned long *dst, const unsigned long *src1,
   const unsigned long *src2, int nbits)
{
 if (nbits <= 32)
  *dst = *src1 ^ *src2;
 else
  __bitmap_xor(dst, src1, src2, nbits);
}

static inline /*__attribute__((always_inline))*/ void bitmap_andnot(unsigned long *dst, const unsigned long *src1,
   const unsigned long *src2, int nbits)
{
 if (nbits <= 32)
  *dst = *src1 & ~(*src2);
 else
  __bitmap_andnot(dst, src1, src2, nbits);
}

static inline /*__attribute__((always_inline))*/ void bitmap_complement(unsigned long *dst, const unsigned long *src,
   int nbits)
{
 if (nbits <= 32)
  *dst = ~(*src) & ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL );
 else
  __bitmap_complement(dst, src, nbits);
}

static inline /*__attribute__((always_inline))*/ int bitmap_equal(const unsigned long *src1,
   const unsigned long *src2, int nbits)
{
 if (nbits <= 32)
  return ! ((*src1 ^ *src2) & ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL ));
 else
  return __bitmap_equal(src1, src2, nbits);
}

static inline /*__attribute__((always_inline))*/ int bitmap_intersects(const unsigned long *src1,
   const unsigned long *src2, int nbits)
{
 if (nbits <= 32)
  return ((*src1 & *src2) & ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL )) != 0;
 else
  return __bitmap_intersects(src1, src2, nbits);
}

static inline /*__attribute__((always_inline))*/ int bitmap_subset(const unsigned long *src1,
   const unsigned long *src2, int nbits)
{
 if (nbits <= 32)
  return ! ((*src1 & ~(*src2)) & ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL ));
 else
  return __bitmap_subset(src1, src2, nbits);
}

static inline /*__attribute__((always_inline))*/ int bitmap_empty(const unsigned long *src, int nbits)
{
 if (nbits <= 32)
  return ! (*src & ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL ));
 else
  return __bitmap_empty(src, nbits);
}

static inline /*__attribute__((always_inline))*/ int bitmap_full(const unsigned long *src, int nbits)
{
 if (nbits <= 32)
  return ! (~(*src) & ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL ));
 else
  return __bitmap_full(src, nbits);
}

static inline /*__attribute__((always_inline))*/ int bitmap_weight(const unsigned long *src, int nbits)
{
 if (nbits <= 32)
  return hweight_long(*src & ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL ));
 return __bitmap_weight(src, nbits);
}

static inline /*__attribute__((always_inline))*/ void bitmap_shift_right(unsigned long *dst,
   const unsigned long *src, int n, int nbits)
{
 if (nbits <= 32)
  *dst = *src >> n;
 else
  __bitmap_shift_right(dst, src, n, nbits);
}

static inline /*__attribute__((always_inline))*/ void bitmap_shift_left(unsigned long *dst,
   const unsigned long *src, int n, int nbits)
{
 if (nbits <= 32)
  *dst = (*src << n) & ( ((nbits) % 32) ? (1UL<<((nbits) % 32))-1 : ~0UL );
 else
  __bitmap_shift_left(dst, src, n, nbits);
}

typedef struct { unsigned long bits[(((1)+32 -1)/32)]; } cpumask_t;
extern cpumask_t _unused_cpumask_arg_;


static inline /*__attribute__((always_inline))*/ void __cpu_set(int cpu, volatile cpumask_t *dstp)
{
 set_bit(cpu, dstp->bits);
}


static inline /*__attribute__((always_inline))*/ void __cpu_clear(int cpu, volatile cpumask_t *dstp)
{
 clear_bit(cpu, dstp->bits);
}


static inline /*__attribute__((always_inline))*/ void __cpus_setall(cpumask_t *dstp, int nbits)
{
 bitmap_fill(dstp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ void __cpus_clear(cpumask_t *dstp, int nbits)
{
 bitmap_zero(dstp->bits, nbits);
}





static inline /*__attribute__((always_inline))*/ int __cpu_test_and_set(int cpu, cpumask_t *addr)
{
 return test_and_set_bit(cpu, addr->bits);
}


static inline /*__attribute__((always_inline))*/ void __cpus_and(cpumask_t *dstp, const cpumask_t *src1p,
     const cpumask_t *src2p, int nbits)
{
 bitmap_and(dstp->bits, src1p->bits, src2p->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ void __cpus_or(cpumask_t *dstp, const cpumask_t *src1p,
     const cpumask_t *src2p, int nbits)
{
 bitmap_or(dstp->bits, src1p->bits, src2p->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ void __cpus_xor(cpumask_t *dstp, const cpumask_t *src1p,
     const cpumask_t *src2p, int nbits)
{
 bitmap_xor(dstp->bits, src1p->bits, src2p->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __cpus_andnot(cpumask_t *dstp, const cpumask_t *src1p,
     const cpumask_t *src2p, int nbits)
{
 bitmap_andnot(dstp->bits, src1p->bits, src2p->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ void __cpus_complement(cpumask_t *dstp,
     const cpumask_t *srcp, int nbits)
{
 bitmap_complement(dstp->bits, srcp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __cpus_equal(const cpumask_t *src1p,
     const cpumask_t *src2p, int nbits)
{
 return bitmap_equal(src1p->bits, src2p->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __cpus_intersects(const cpumask_t *src1p,
     const cpumask_t *src2p, int nbits)
{
 return bitmap_intersects(src1p->bits, src2p->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __cpus_subset(const cpumask_t *src1p,
     const cpumask_t *src2p, int nbits)
{
 return bitmap_subset(src1p->bits, src2p->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __cpus_empty(const cpumask_t *srcp, int nbits)
{
 return bitmap_empty(srcp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __cpus_full(const cpumask_t *srcp, int nbits)
{
 return bitmap_full(srcp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __cpus_weight(const cpumask_t *srcp, int nbits)
{
 return bitmap_weight(srcp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __cpus_shift_right(cpumask_t *dstp,
     const cpumask_t *srcp, int n, int nbits)
{
 bitmap_shift_right(dstp->bits, srcp->bits, n, nbits);
}



static inline /*__attribute__((always_inline))*/ void __cpus_shift_left(cpumask_t *dstp,
     const cpumask_t *srcp, int n, int nbits)
{
 bitmap_shift_left(dstp->bits, srcp->bits, n, nbits);
}
static inline /*__attribute__((always_inline))*/ int __cpumask_scnprintf(char *buf, int len,
     const cpumask_t *srcp, int nbits)
{
 return bitmap_scnprintf(buf, len, srcp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __cpumask_parse(const char *buf, int len,
     cpumask_t *dstp, int nbits)
{
 return bitmap_parse(buf, len, dstp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __cpulist_scnprintf(char *buf, int len,
     const cpumask_t *srcp, int nbits)
{
 return bitmap_scnlistprintf(buf, len, srcp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __cpulist_parse(const char *buf, cpumask_t *dstp, int nbits)
{
 return bitmap_parselist(buf, dstp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __cpu_remap(int oldbit,
  const cpumask_t *oldp, const cpumask_t *newp, int nbits)
{
 return bitmap_bitremap(oldbit, oldp->bits, newp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __cpus_remap(cpumask_t *dstp, const cpumask_t *srcp,
  const cpumask_t *oldp, const cpumask_t *newp, int nbits)
{
 bitmap_remap(dstp->bits, srcp->bits, oldp->bits, newp->bits, nbits);
}
extern cpumask_t cpu_possible_map;
extern cpumask_t cpu_online_map;
extern cpumask_t cpu_present_map;


extern int tsc_disable;

struct desc_struct {
 unsigned long a,b;
};
struct cpuinfo_x86 {
 __u8 x86;
 __u8 x86_vendor;
 __u8 x86_model;
 __u8 x86_mask;
 char wp_works_ok;
 char hlt_works_ok;
 char hard_math;
 char rfu;
        int cpuid_level;
 unsigned long x86_capability[7];
 char x86_vendor_id[16];
 char x86_model_id[64];
 int x86_cache_size;

 int x86_cache_alignment;
 char fdiv_bug;
 char f00f_bug;
 char coma_bug;
 char pad0;
 int x86_power;
 unsigned long loops_per_jiffy;



 unsigned char x86_max_cores;
 unsigned char apicid;





} /*__attribute__((__aligned__((1 << (CONFIG_X86_L1_CACHE_SHIFT)))))*/;
extern struct cpuinfo_x86 boot_cpu_data;
extern struct cpuinfo_x86 new_cpu_data;
extern struct tss_struct doublefault_tss;
extern __typeof__(struct tss_struct) per_cpu__init_tss;
extern int cpu_llc_id[1];
extern char ignore_fpu_irq;

extern void identify_cpu(struct cpuinfo_x86 *);
extern void print_cpu_info(struct cpuinfo_x86 *);
extern unsigned int init_intel_cacheinfo(struct cpuinfo_x86 *c);
extern unsigned short num_cache_leaves;




static inline /*__attribute__((always_inline))*/ void detect_ht(struct cpuinfo_x86 *c) {}
static inline /*__attribute__((always_inline))*/ void cpuid(unsigned int op, unsigned int *eax, unsigned int *ebx, unsigned int *ecx, unsigned int *edx)
{
 __asm__("cpuid"
  : "=a" (*eax),
    "=b" (*ebx),
    "=c" (*ecx),
    "=d" (*edx)
  : "0" (op), "c"(0));
}


static inline /*__attribute__((always_inline))*/ void cpuid_count(int op, int count, int *eax, int *ebx, int *ecx,
         int *edx)
{
 __asm__("cpuid"
  : "=a" (*eax),
    "=b" (*ebx),
    "=c" (*ecx),
    "=d" (*edx)
  : "0" (op), "c" (count));
}




static inline /*__attribute__((always_inline))*/ unsigned int cpuid_eax(unsigned int op)
{
 unsigned int eax;

 __asm__("cpuid"
  : "=a" (eax)
  : "0" (op)
  : "bx", "cx", "dx");
 return eax;
}
static inline /*__attribute__((always_inline))*/ unsigned int cpuid_ebx(unsigned int op)
{
 unsigned int eax, ebx;

 __asm__("cpuid"
  : "=a" (eax), "=b" (ebx)
  : "0" (op)
  : "cx", "dx" );
 return ebx;
}
static inline /*__attribute__((always_inline))*/ unsigned int cpuid_ecx(unsigned int op)
{
 unsigned int eax, ecx;

 __asm__("cpuid"
  : "=a" (eax), "=c" (ecx)
  : "0" (op)
  : "bx", "dx" );
 return ecx;
}
static inline /*__attribute__((always_inline))*/ unsigned int cpuid_edx(unsigned int op)
{
 unsigned int eax, edx;

 __asm__("cpuid"
  : "=a" (eax), "=d" (edx)
  : "0" (op)
  : "bx", "cx");
 return edx;
}
extern unsigned long mmu_cr4_features;

static inline /*__attribute__((always_inline))*/ void set_in_cr4 (unsigned long mask)
{
 unsigned cr4;
 mmu_cr4_features |= mask;
 cr4 = ({ unsigned int __dummy; __asm__( "movl %%cr4,%0\n\t" :"=r" (__dummy)); __dummy; });
 cr4 |= mask;
 __asm__ __volatile__("movl %0,%%cr4": :"r" (cr4));
}

static inline /*__attribute__((always_inline))*/ void clear_in_cr4 (unsigned long mask)
{
 unsigned cr4;
 mmu_cr4_features &= ~mask;
 cr4 = ({ unsigned int __dummy; __asm__( "movl %%cr4,%0\n\t" :"=r" (__dummy)); __dummy; });
 cr4 &= ~mask;
 __asm__ __volatile__("movl %0,%%cr4": :"r" (cr4));
}
static inline /*__attribute__((always_inline))*/ void sync_core(void)
{
 int tmp;
 asm volatile("cpuid" : "=a" (tmp) : "0" (1) : "ebx","ecx","edx","memory");
}

static inline /*__attribute__((always_inline))*/ void __monitor(const void *eax, unsigned long ecx,
  unsigned long edx)
{

 asm volatile(
  ".byte 0x0f,0x01,0xc8;"
  : :"a" (eax), "c" (ecx), "d"(edx));
}

static inline /*__attribute__((always_inline))*/ void __mwait(unsigned long eax, unsigned long ecx)
{

 asm volatile(
  ".byte 0x0f,0x01,0xc9;"
  : :"a" (eax), "c" (ecx));
}



extern unsigned int machine_id;
extern unsigned int machine_submodel_id;
extern unsigned int BIOS_revision;
extern unsigned int mca_pentium_flag;


extern int bootloader_type;
struct i387_fsave_struct {
 long cwd;
 long swd;
 long twd;
 long fip;
 long fcs;
 long foo;
 long fos;
 long st_space[20];
 long status;
};

struct i387_fxsave_struct {
 unsigned short cwd;
 unsigned short swd;
 unsigned short twd;
 unsigned short fop;
 long fip;
 long fcs;
 long foo;
 long fos;
 long mxcsr;
 long mxcsr_mask;
 long st_space[32];
 long xmm_space[32];
 long padding[56];
} __attribute__ ((aligned (16)));

struct i387_soft_struct {
 long cwd;
 long swd;
 long twd;
 long fip;
 long fcs;
 long foo;
 long fos;
 long st_space[20];
 unsigned char ftop, changed, lookahead, no_update, rm, alimit;
 struct info *info;
 unsigned long entry_eip;
};

union i387_union {
 struct i387_fsave_struct fsave;
 struct i387_fxsave_struct fxsave;
 struct i387_soft_struct soft;
};

typedef struct {
 unsigned long seg;
} mm_segment_t;

struct thread_struct;

struct tss_struct {
 unsigned short back_link,__blh;
 unsigned long esp0;
 unsigned short ss0,__ss0h;
 unsigned long esp1;
 unsigned short ss1,__ss1h;
 unsigned long esp2;
 unsigned short ss2,__ss2h;
 unsigned long __cr3;
 unsigned long eip;
 unsigned long eflags;
 unsigned long eax,ecx,edx,ebx;
 unsigned long esp;
 unsigned long ebp;
 unsigned long esi;
 unsigned long edi;
 unsigned short es, __esh;
 unsigned short cs, __csh;
 unsigned short ss, __ssh;
 unsigned short ds, __dsh;
 unsigned short fs, __fsh;
 unsigned short gs, __gsh;
 unsigned short ldt, __ldth;
 unsigned short trace, io_bitmap_base;






 unsigned long io_bitmap[((65536/8)/sizeof(long)) + 1];



 unsigned long io_bitmap_max;
 struct thread_struct *io_bitmap_owner;



 unsigned long __cacheline_filler[35];



 unsigned long stack[64];
} __attribute__((packed));



struct thread_struct {

 struct desc_struct tls_array[3];
 unsigned long esp0;
 unsigned long sysenter_cs;
 unsigned long eip;
 unsigned long esp;
 unsigned long fs;
 unsigned long gs;

 unsigned long debugreg[8];

 unsigned long cr2, trap_no, error_code;

 union i387_union i387;

 struct vm86_struct * vm86_info;
 unsigned long screen_bitmap;
 unsigned long v86flags, v86mask, saved_esp0;
 unsigned int saved_fs, saved_gs;

 unsigned long *io_bitmap_ptr;
  unsigned long iopl;

 unsigned long io_bitmap_max;
};
static inline /*__attribute__((always_inline))*/ void load_esp0(struct tss_struct *tss, struct thread_struct *thread)
{
 tss->esp0 = thread->esp0;

 if (__builtin_expect(!!(tss->ss1 != thread->sysenter_cs), 0)) {
  tss->ss1 = thread->sysenter_cs;
  __asm__ __volatile__("wrmsr" : : "c" (0x174), "a" (thread->sysenter_cs), "d" (0));
 }
}
static inline /*__attribute__((always_inline))*/ void set_iopl_mask(unsigned mask)
{
 unsigned int reg;
 __asm__ __volatile__ ("pushfl;"
         "popl %0;"
         "andl %1, %0;"
         "orl %2, %0;"
         "pushl %0;"
         "popfl"
    : "=&r" (reg)
    : "i" (~0x00003000), "r" (mask));
}


struct task_struct;
struct mm_struct;


extern void release_thread(struct task_struct *);


extern void prepare_to_copy(struct task_struct *tsk);




extern int kernel_thread(int (*fn)(void *), void * arg, unsigned long flags);

extern unsigned long thread_saved_pc(struct task_struct *tsk);
void show_trace(struct task_struct *task, struct pt_regs *regs, unsigned long *stack);

unsigned long get_wchan(struct task_struct *p);
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


static inline /*__attribute__((always_inline))*/ void rep_nop(void)
{
 __asm__ __volatile__("rep;nop": : :"memory");
}
static inline /*__attribute__((always_inline))*/ void prefetch(const void *x)
{
 asm volatile ("661:\n\t" ".byte 0x8d,0x74,0x26,0x00\n" "\n662:\n" ".section .altinstructions,\"a\"\n" "  .align 4\n" "  .long 661b\n" "  .long 663f\n" "  .byte %c0\n" "  .byte 662b-661b\n" "  .byte 664f-663f\n" ".previous\n" ".section .altinstr_replacement,\"ax\"\n" "663:\n\t" "prefetchnta (%1)" "\n664:\n" ".previous" :: "i" ((0*32+25)), "r" (x));



}







static inline /*__attribute__((always_inline))*/ void prefetchw(const void *x)
{
 asm volatile ("661:\n\t" ".byte 0x8d,0x74,0x26,0x00\n" "\n662:\n" ".section .altinstructions,\"a\"\n" "  .align 4\n" "  .long 661b\n" "  .long 663f\n" "  .byte %c0\n" "  .byte 662b-661b\n" "  .byte 664f-663f\n" ".previous\n" ".section .altinstr_replacement,\"ax\"\n" "663:\n\t" "prefetchw (%1)" "\n664:\n" ".previous" :: "i" ((1*32+31)), "r" (x));



}


extern void select_idle_routine(const struct cpuinfo_x86 *c);



extern unsigned long boot_option_idle_override;
extern void enable_sep_cpu(void);
extern int sysenter_setup(void);
struct thread_info {
 struct task_struct *task;
 struct exec_domain *exec_domain;
 unsigned long flags;
 unsigned long status;
 __u32 cpu;
 int preempt_count;


 mm_segment_t addr_limit;



 void *sysenter_return;
 struct restart_block restart_block;

 unsigned long previous_esp;


 __u8 supervisor_stack[0];
};
register unsigned long current_stack_pointer asm("esp") __attribute__((__used__));


static inline /*__attribute__((always_inline))*/ struct thread_info *current_thread_info(void)
{
 return (struct thread_info *)(current_stack_pointer & ~((8192) - 1));
}
static inline /*__attribute__((always_inline))*/ void set_ti_thread_flag(struct thread_info *ti, int flag)
{
 set_bit(flag,&ti->flags);
}

static inline /*__attribute__((always_inline))*/ void clear_ti_thread_flag(struct thread_info *ti, int flag)
{
 clear_bit(flag,&ti->flags);
}

static inline /*__attribute__((always_inline))*/ int test_and_set_ti_thread_flag(struct thread_info *ti, int flag)
{
 return test_and_set_bit(flag,&ti->flags);
}

static inline /*__attribute__((always_inline))*/ int test_and_clear_ti_thread_flag(struct thread_info *ti, int flag)
{
 return test_and_clear_bit(flag,&ti->flags);
}

static inline /*__attribute__((always_inline))*/ int test_ti_thread_flag(struct thread_info *ti, int flag)
{
 return (__builtin_constant_p(flag) ? constant_test_bit((flag),(&ti->flags)) : variable_test_bit((flag),(&ti->flags)));
}










static inline /*__attribute__((always_inline))*/ void prefetch_range(void *addr, size_t len)
{

 char *cp;
 char *end = addr + len;

 for (cp = addr; cp < end; cp += (4*(1 << (CONFIG_X86_L1_CACHE_SHIFT))))
  prefetch(cp);

}
struct list_head {
 struct list_head *next, *prev;
};






static inline /*__attribute__((always_inline))*/ void INIT_LIST_HEAD(struct list_head *list)
{
 list->next = list;
 list->prev = list;
}







static inline /*__attribute__((always_inline))*/ void __list_add(struct list_head *new,
         struct list_head *prev,
         struct list_head *next)
{
 next->prev = new;
 new->next = next;
 new->prev = prev;
 prev->next = new;
}
static inline /*__attribute__((always_inline))*/ void list_add(struct list_head *new, struct list_head *head)
{
 __list_add(new, head, head->next);
}
static inline /*__attribute__((always_inline))*/ void list_add_tail(struct list_head *new, struct list_head *head)
{
 __list_add(new, head->prev, head);
}







static inline /*__attribute__((always_inline))*/ void __list_add_rcu(struct list_head * new,
  struct list_head * prev, struct list_head * next)
{
 new->next = next;
 new->prev = prev;
 __asm__ __volatile__("": : :"memory");
 next->prev = new;
 prev->next = new;
}
static inline /*__attribute__((always_inline))*/ void list_add_rcu(struct list_head *new, struct list_head *head)
{
 __list_add_rcu(new, head, head->next);
}
static inline /*__attribute__((always_inline))*/ void list_add_tail_rcu(struct list_head *new,
     struct list_head *head)
{
 __list_add_rcu(new, head->prev, head);
}
static inline /*__attribute__((always_inline))*/ void __list_del(struct list_head * prev, struct list_head * next)
{
 next->prev = prev;
 prev->next = next;
}







static inline /*__attribute__((always_inline))*/ void list_del(struct list_head *entry)
{
 __list_del(entry->prev, entry->next);
 entry->next = ((void *) 0x00100100);
 entry->prev = ((void *) 0x00200200);
}
static inline /*__attribute__((always_inline))*/ void list_del_rcu(struct list_head *entry)
{
 __list_del(entry->prev, entry->next);
 entry->prev = ((void *) 0x00200200);
}







static inline /*__attribute__((always_inline))*/ void list_replace(struct list_head *old,
    struct list_head *new)
{
 new->next = old->next;
 new->next->prev = new;
 new->prev = old->prev;
 new->prev->next = new;
}

static inline /*__attribute__((always_inline))*/ void list_replace_init(struct list_head *old,
     struct list_head *new)
{
 list_replace(old, new);
 INIT_LIST_HEAD(old);
}
static inline /*__attribute__((always_inline))*/ void list_replace_rcu(struct list_head *old,
    struct list_head *new)
{
 new->next = old->next;
 new->prev = old->prev;
 __asm__ __volatile__("": : :"memory");
 new->next->prev = new;
 new->prev->next = new;
 old->prev = ((void *) 0x00200200);
}





static inline /*__attribute__((always_inline))*/ void list_del_init(struct list_head *entry)
{
 __list_del(entry->prev, entry->next);
 INIT_LIST_HEAD(entry);
}






static inline /*__attribute__((always_inline))*/ void list_move(struct list_head *list, struct list_head *head)
{
        __list_del(list->prev, list->next);
        list_add(list, head);
}






static inline /*__attribute__((always_inline))*/ void list_move_tail(struct list_head *list,
      struct list_head *head)
{
        __list_del(list->prev, list->next);
        list_add_tail(list, head);
}






static inline /*__attribute__((always_inline))*/ int list_is_last(const struct list_head *list,
    const struct list_head *head)
{
 return list->next == head;
}





static inline /*__attribute__((always_inline))*/ int list_empty(const struct list_head *head)
{
 return head->next == head;
}
static inline /*__attribute__((always_inline))*/ int list_empty_careful(const struct list_head *head)
{
 struct list_head *next = head->next;
 return (next == head) && (next == head->prev);
}

static inline /*__attribute__((always_inline))*/ void __list_splice(struct list_head *list,
     struct list_head *head)
{
 struct list_head *first = list->next;
 struct list_head *last = list->prev;
 struct list_head *at = head->next;

 first->prev = head;
 head->next = first;

 last->next = at;
 at->prev = last;
}






static inline /*__attribute__((always_inline))*/ void list_splice(struct list_head *list, struct list_head *head)
{
 if (!list_empty(list))
  __list_splice(list, head);
}
static inline /*__attribute__((always_inline))*/ void list_splice_init(struct list_head *list,
        struct list_head *head)
{
 if (!list_empty(list)) {
  __list_splice(list, head);
  INIT_LIST_HEAD(list);
 }
}
struct hlist_head {
 struct hlist_node *first;
};

struct hlist_node {
 struct hlist_node *next, **pprev;
};




static inline /*__attribute__((always_inline))*/ void INIT_HLIST_NODE(struct hlist_node *h)
{
 h->next = ((void *)0);
 h->pprev = ((void *)0);
}

static inline /*__attribute__((always_inline))*/ int hlist_unhashed(const struct hlist_node *h)
{
 return !h->pprev;
}

static inline /*__attribute__((always_inline))*/ int hlist_empty(const struct hlist_head *h)
{
 return !h->first;
}

static inline /*__attribute__((always_inline))*/ void __hlist_del(struct hlist_node *n)
{
 struct hlist_node *next = n->next;
 struct hlist_node **pprev = n->pprev;
 *pprev = next;
 if (next)
  next->pprev = pprev;
}

static inline /*__attribute__((always_inline))*/ void hlist_del(struct hlist_node *n)
{
 __hlist_del(n);
 n->next = ((void *) 0x00100100);
 n->pprev = ((void *) 0x00200200);
}
static inline /*__attribute__((always_inline))*/ void hlist_del_rcu(struct hlist_node *n)
{
 __hlist_del(n);
 n->pprev = ((void *) 0x00200200);
}

static inline /*__attribute__((always_inline))*/ void hlist_del_init(struct hlist_node *n)
{
 if (!hlist_unhashed(n)) {
  __hlist_del(n);
  INIT_HLIST_NODE(n);
 }
}
static inline /*__attribute__((always_inline))*/ void hlist_replace_rcu(struct hlist_node *old,
     struct hlist_node *new)
{
 struct hlist_node *next = old->next;

 new->next = next;
 new->pprev = old->pprev;
 __asm__ __volatile__("": : :"memory");
 if (next)
  new->next->pprev = &new->next;
 *new->pprev = new;
 old->pprev = ((void *) 0x00200200);
}

static inline /*__attribute__((always_inline))*/ void hlist_add_head(struct hlist_node *n, struct hlist_head *h)
{
 struct hlist_node *first = h->first;
 n->next = first;
 if (first)
  first->pprev = &n->next;
 h->first = n;
 n->pprev = &h->first;
}
static inline /*__attribute__((always_inline))*/ void hlist_add_head_rcu(struct hlist_node *n,
     struct hlist_head *h)
{
 struct hlist_node *first = h->first;
 n->next = first;
 n->pprev = &h->first;
 __asm__ __volatile__("": : :"memory");
 if (first)
  first->pprev = &n->next;
 h->first = n;
}


static inline /*__attribute__((always_inline))*/ void hlist_add_before(struct hlist_node *n,
     struct hlist_node *next)
{
 n->pprev = next->pprev;
 n->next = next;
 next->pprev = &n->next;
 *(n->pprev) = n;
}

static inline /*__attribute__((always_inline))*/ void hlist_add_after(struct hlist_node *n,
     struct hlist_node *next)
{
 next->next = n->next;
 n->next = next;
 next->pprev = &n->next;

 if(next->next)
  next->next->pprev = &next->next;
}
static inline /*__attribute__((always_inline))*/ void hlist_add_before_rcu(struct hlist_node *n,
     struct hlist_node *next)
{
 n->pprev = next->pprev;
 n->next = next;
 __asm__ __volatile__("": : :"memory");
 next->pprev = &n->next;
 *(n->pprev) = n;
}
static inline /*__attribute__((always_inline))*/ void hlist_add_after_rcu(struct hlist_node *prev,
           struct hlist_node *n)
{
 n->next = prev->next;
 n->pprev = &prev->next;
 __asm__ __volatile__("": : :"memory");
 prev->next = n;
 if (n->next)
  n->next->pprev = &n->next;
}



struct task_struct;

extern int debug_locks;
extern int debug_locks_silent;




extern int debug_locks_off(void);
static inline /*__attribute__((always_inline))*/ void debug_show_all_locks(void)
{
}

static inline /*__attribute__((always_inline))*/ void debug_show_held_locks(struct task_struct *task)
{
}

static inline /*__attribute__((always_inline))*/ void
debug_check_no_locks_freed(const void *from, unsigned long len)
{
}

static inline /*__attribute__((always_inline))*/ void
debug_check_no_locks_held(struct task_struct *task)
{
}
static inline /*__attribute__((always_inline))*/ void lockdep_off(void)
{
}

static inline /*__attribute__((always_inline))*/ void lockdep_on(void)
{
}

static inline /*__attribute__((always_inline))*/ int lockdep_internal(void)
{
 return 0;
}
struct lock_class_key { };




typedef struct { } raw_spinlock_t;





typedef struct {




} raw_rwlock_t;


typedef struct {
 raw_spinlock_t raw_lock;
} spinlock_t;



typedef struct {
 raw_rwlock_t raw_lock;
} rwlock_t;

extern int /*__attribute__((regparm(3)))*/ /*__attribute__((section(".spinlock.text")))*/ generic__raw_read_trylock(raw_rwlock_t *lock);







typedef struct { volatile int counter; } atomic_t;
static __inline__ /*__attribute__((always_inline))*/ void atomic_add(int i, atomic_t *v)
{
 __asm__ __volatile__(
  "" "addl %1,%0"
  :"+m" (v->counter)
  :"ir" (i));
}
static __inline__ /*__attribute__((always_inline))*/ void atomic_sub(int i, atomic_t *v)
{
 __asm__ __volatile__(
  "" "subl %1,%0"
  :"+m" (v->counter)
  :"ir" (i));
}
static __inline__ /*__attribute__((always_inline))*/ int atomic_sub_and_test(int i, atomic_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "" "subl %2,%0; sete %1"
  :"+m" (v->counter), "=qm" (c)
  :"ir" (i) : "memory");
 return c;
}







static __inline__ /*__attribute__((always_inline))*/ void atomic_inc(atomic_t *v)
{
 __asm__ __volatile__(
  "" "incl %0"
  :"+m" (v->counter));
}







static __inline__ /*__attribute__((always_inline))*/ void atomic_dec(atomic_t *v)
{
 __asm__ __volatile__(
  "" "decl %0"
  :"+m" (v->counter));
}
static __inline__ /*__attribute__((always_inline))*/ int atomic_dec_and_test(atomic_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "" "decl %0; sete %1"
  :"+m" (v->counter), "=qm" (c)
  : : "memory");
 return c != 0;
}
static __inline__ /*__attribute__((always_inline))*/ int atomic_inc_and_test(atomic_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "" "incl %0; sete %1"
  :"+m" (v->counter), "=qm" (c)
  : : "memory");
 return c != 0;
}
static __inline__ /*__attribute__((always_inline))*/ int atomic_add_negative(int i, atomic_t *v)
{
 unsigned char c;

 __asm__ __volatile__(
  "" "addl %2,%0; sets %1"
  :"+m" (v->counter), "=qm" (c)
  :"ir" (i) : "memory");
 return c;
}
static __inline__ /*__attribute__((always_inline))*/ int atomic_add_return(int i, atomic_t *v)
{
 int __i;






 __i = i;
 __asm__ __volatile__(
  "" "xaddl %0, %1;"
  :"=r"(i)
  :"m"(v->counter), "0"(i));
 return i + __i;
}

static __inline__ /*__attribute__((always_inline))*/ int atomic_sub_return(int i, atomic_t *v)
{
 return atomic_add_return(-i,v);
}
typedef atomic_t atomic_long_t;


static inline /*__attribute__((always_inline))*/ long atomic_long_read(atomic_long_t *l)
{
 atomic_t *v = (atomic_t *)l;

 return (long)((v)->counter);
}

static inline /*__attribute__((always_inline))*/ void atomic_long_set(atomic_long_t *l, long i)
{
 atomic_t *v = (atomic_t *)l;

 (((v)->counter) = (i));
}

static inline /*__attribute__((always_inline))*/ void atomic_long_inc(atomic_long_t *l)
{
 atomic_t *v = (atomic_t *)l;

 atomic_inc(v);
}

static inline /*__attribute__((always_inline))*/ void atomic_long_dec(atomic_long_t *l)
{
 atomic_t *v = (atomic_t *)l;

 atomic_dec(v);
}

static inline /*__attribute__((always_inline))*/ void atomic_long_add(long i, atomic_long_t *l)
{
 atomic_t *v = (atomic_t *)l;

 atomic_add(i, v);
}

static inline /*__attribute__((always_inline))*/ void atomic_long_sub(long i, atomic_long_t *l)
{
 atomic_t *v = (atomic_t *)l;

 atomic_sub(i, v);
}





extern int _atomic_dec_and_lock(atomic_t *atomic, spinlock_t *lock);





struct task_struct;

static inline /*__attribute__((always_inline))*/ /*__attribute__((always_inline))*/ struct task_struct * get_current(void)
{
 return current_thread_info()->task;
}
typedef __u32 kernel_cap_t;
extern kernel_cap_t cap_bset;
static inline /*__attribute__((always_inline))*/ kernel_cap_t cap_combine(kernel_cap_t a, kernel_cap_t b)
{
     kernel_cap_t dest;
     (dest) = (a) | (b);
     return dest;
}

static inline /*__attribute__((always_inline))*/ kernel_cap_t cap_intersect(kernel_cap_t a, kernel_cap_t b)
{
     kernel_cap_t dest;
     (dest) = (a) & (b);
     return dest;
}

static inline /*__attribute__((always_inline))*/ kernel_cap_t cap_drop(kernel_cap_t a, kernel_cap_t drop)
{
     kernel_cap_t dest;
     (dest) = (a) & ~(drop);
     return dest;
}

static inline /*__attribute__((always_inline))*/ kernel_cap_t cap_invert(kernel_cap_t c)
{
     kernel_cap_t dest;
     (dest) = ~(c);
     return dest;
}
int capable(int cap);
int __capable(struct task_struct *t, int cap);









typedef struct {
 unsigned sequence;
 spinlock_t lock;
} seqlock_t;
static inline /*__attribute__((always_inline))*/ void write_seqlock(seqlock_t *sl)
{
 do { do { } while (0); (void)0; (void)(&sl->lock); } while (0);
 ++sl->sequence;
 __asm__ __volatile__("": : :"memory");
}

static inline /*__attribute__((always_inline))*/ void write_sequnlock(seqlock_t *sl)
{
 __asm__ __volatile__("": : :"memory");
 sl->sequence++;
 do { do { } while (0); (void)0; (void)(&sl->lock); } while (0);
}

static inline /*__attribute__((always_inline))*/ int write_tryseqlock(seqlock_t *sl)
{
 int ret = (({ do { do { } while (0); (void)0; (void)(&sl->lock); } while (0); 1; }));

 if (ret) {
  ++sl->sequence;
  __asm__ __volatile__("": : :"memory");
 }
 return ret;
}


static inline /*__attribute__((always_inline))*/ /*__attribute__((always_inline))*/ unsigned read_seqbegin(const seqlock_t *sl)
{
 unsigned ret = sl->sequence;
 __asm__ __volatile__("": : :"memory");
 return ret;
}
static inline /*__attribute__((always_inline))*/ /*__attribute__((always_inline))*/ int read_seqretry(const seqlock_t *sl, unsigned iv)
{
 __asm__ __volatile__("": : :"memory");
 return (iv & 1) | (sl->sequence ^ iv);
}
typedef struct seqcount {
 unsigned sequence;
} seqcount_t;





static inline /*__attribute__((always_inline))*/ unsigned read_seqcount_begin(const seqcount_t *s)
{
 unsigned ret = s->sequence;
 __asm__ __volatile__("": : :"memory");
 return ret;
}






static inline /*__attribute__((always_inline))*/ int read_seqcount_retry(const seqcount_t *s, unsigned iv)
{
 __asm__ __volatile__("": : :"memory");
 return (iv & 1) | (s->sequence ^ iv);
}






static inline /*__attribute__((always_inline))*/ void write_seqcount_begin(seqcount_t *s)
{
 s->sequence++;
 __asm__ __volatile__("": : :"memory");
}

static inline /*__attribute__((always_inline))*/ void write_seqcount_end(seqcount_t *s)
{
 __asm__ __volatile__("": : :"memory");
 s->sequence++;
}




struct timespec {
 time_t tv_sec;
 long tv_nsec;
};


struct timeval {
 time_t tv_sec;
 suseconds_t tv_usec;
};

struct timezone {
 int tz_minuteswest;
 int tz_dsttime;
};
static inline /*__attribute__((always_inline))*/ int timespec_equal(struct timespec *a, struct timespec *b)
{
 return (a->tv_sec == b->tv_sec) && (a->tv_nsec == b->tv_nsec);
}






static inline /*__attribute__((always_inline))*/ int timespec_compare(struct timespec *lhs, struct timespec *rhs)
{
 if (lhs->tv_sec < rhs->tv_sec)
  return -1;
 if (lhs->tv_sec > rhs->tv_sec)
  return 1;
 return lhs->tv_nsec - rhs->tv_nsec;
}

static inline /*__attribute__((always_inline))*/ int timeval_compare(struct timeval *lhs, struct timeval *rhs)
{
 if (lhs->tv_sec < rhs->tv_sec)
  return -1;
 if (lhs->tv_sec > rhs->tv_sec)
  return 1;
 return lhs->tv_usec - rhs->tv_usec;
}

extern unsigned long mktime(const unsigned int year, const unsigned int mon,
       const unsigned int day, const unsigned int hour,
       const unsigned int min, const unsigned int sec);

extern void set_normalized_timespec(struct timespec *ts, time_t sec, long nsec);




static inline /*__attribute__((always_inline))*/ struct timespec timespec_sub(struct timespec lhs,
      struct timespec rhs)
{
 struct timespec ts_delta;
 set_normalized_timespec(&ts_delta, lhs.tv_sec - rhs.tv_sec,
    lhs.tv_nsec - rhs.tv_nsec);
 return ts_delta;
}







extern struct timespec xtime;
extern struct timespec wall_to_monotonic;
extern seqlock_t xtime_lock;

void timekeeping_init(void);

static inline /*__attribute__((always_inline))*/ unsigned long get_seconds(void)
{
 return xtime.tv_sec;
}

struct timespec current_kernel_time(void);




extern void do_gettimeofday(struct timeval *tv);
extern int do_settimeofday(struct timespec *tv);
extern int do_sys_settimeofday(struct timespec *tv, struct timezone *tz);

extern long do_utimes(int dfd, char *filename, struct timeval *times);
struct itimerval;
extern int do_setitimer(int which, struct itimerval *value,
   struct itimerval *ovalue);
extern unsigned int alarm_setitimer(unsigned int seconds);
extern int do_getitimer(int which, struct itimerval *value);
extern void getnstimeofday(struct timespec *tv);

extern struct timespec timespec_trunc(struct timespec t, unsigned gran);
extern int timekeeping_is_continuous(void);
static inline /*__attribute__((always_inline))*/ s64 timespec_to_ns(const struct timespec *ts)
{
 return ((s64) ts->tv_sec * 1000000000L) + ts->tv_nsec;
}
static inline /*__attribute__((always_inline))*/ s64 timeval_to_ns(const struct timeval *tv)
{
 return ((s64) tv->tv_sec * 1000000000L) +
  tv->tv_usec * 1000L;
}







extern struct timespec ns_to_timespec(const s64 nsec);







extern struct timeval ns_to_timeval(const s64 nsec);






static inline /*__attribute__((always_inline))*/ void timespec_add_ns(struct timespec *a, u64 ns)
{
 ns += a->tv_nsec;
 while(__builtin_expect(!!(ns >= 1000000000L), 0)) {
  ns -= 1000000000L;
  a->tv_sec++;
 }
 a->tv_nsec = ns;
}
struct itimerspec {
 struct timespec it_interval;
 struct timespec it_value;
};

struct itimerval {
 struct timeval it_interval;
 struct timeval it_value;
};
struct timex {
 unsigned int modes;
 long offset;
 long freq;
 long maxerror;
 long esterror;
 int status;
 long constant;
 long precision;
 long tolerance;


 struct timeval time;
 long tick;

 long ppsfreq;
 long jitter;
 int shift;
 long stabil;
 long jitcnt;
 long calcnt;
 long errcnt;
 long stbcnt;

 int :32; int :32; int :32; int :32;
 int :32; int :32; int :32; int :32;
 int :32; int :32; int :32; int :32;
};





typedef unsigned long long cycles_t;

extern unsigned int cpu_khz;
extern unsigned int tsc_khz;

static inline /*__attribute__((always_inline))*/ cycles_t get_cycles(void)
{
 unsigned long long ret = 0;







 __asm__ __volatile__("rdtsc" : "=A" (ret));

 return ret;
}

extern void tsc_init(void);
extern void mark_tsc_unstable(void);
extern int read_current_timer(unsigned long *timer_value);






extern unsigned long tick_usec;
extern unsigned long tick_nsec;
extern int tickadj;




extern int time_state;
extern int time_status;
extern long time_offset;
extern long time_constant;
extern long time_tolerance;
extern long time_precision;
extern long time_maxerror;
extern long time_esterror;

extern long time_freq;
extern long time_reftime;

extern long time_adjust;
extern long time_next_adjust;






static inline /*__attribute__((always_inline))*/ void ntp_clear(void)
{
 time_adjust = 0;
 time_status |= 0x0040;
 time_maxerror = (512000L << 5);
 time_esterror = (512000L << 5);
}





static inline /*__attribute__((always_inline))*/ int ntp_synced(void)
{
 return !(time_status & 0x0040);
}
static inline /*__attribute__((always_inline))*/ void
time_interpolator_reset(void)
{
}






extern u64 current_tick_length(void);

extern int do_adjtimex(struct timex *);







static inline /*__attribute__((always_inline))*/ long
div_ll_X_l_rem(long long divs, long div, long *rem)
{
 long dum2;
      __asm__("divl %2":"=a"(dum2), "=d"(*rem)
      : "rm"(div), "A"(divs));

 return dum2;

}
static inline /*__attribute__((always_inline))*/ long div_long_long_rem_signed(const long long dividend,
         const long divisor, long *remainder)
{
 long res;

 if (__builtin_expect(!!(dividend < 0), 0)) {
  res = -div_ll_X_l_rem(-dividend,divisor,remainder);
  *remainder = -(*remainder);
 } else
  res = div_ll_X_l_rem(dividend,divisor,remainder);

 return res;
}
extern u64 /*__attribute__((section(".data")))*/ jiffies_64;
extern unsigned long volatile /*__attribute__((section(".data")))*/ jiffies;


u64 get_jiffies_64(void);
static inline /*__attribute__((always_inline))*/ unsigned int jiffies_to_msecs(const unsigned long j)
{

 return (1000L / 100) * j;





}

static inline /*__attribute__((always_inline))*/ unsigned int jiffies_to_usecs(const unsigned long j)
{

 return (1000000L / 100) * j;





}

static inline /*__attribute__((always_inline))*/ unsigned long msecs_to_jiffies(const unsigned int m)
{
 if (m > jiffies_to_msecs(((~0UL >> 1)-1)))
  return ((~0UL >> 1)-1);

 return (m + (1000L / 100) - 1) / (1000L / 100);





}

static inline /*__attribute__((always_inline))*/ unsigned long usecs_to_jiffies(const unsigned int u)
{
 if (u > jiffies_to_usecs(((~0UL >> 1)-1)))
  return ((~0UL >> 1)-1);

 return (u + (1000000L / 100) - 1) / (1000000L / 100);





}
static __inline__ /*__attribute__((always_inline))*/ unsigned long
timespec_to_jiffies(const struct timespec *value)
{
 unsigned long sec = value->tv_sec;
 long nsec = value->tv_nsec + (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))))) - 1;

 if (sec >= (long)((u64)((u64)((~0UL >> 1)-1) * (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))))) / 1000000000L)){
  sec = (long)((u64)((u64)((~0UL >> 1)-1) * (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))))) / 1000000000L);
  nsec = 0;
 }
 return (((u64)sec * ((unsigned long)((((u64)1000000000L << (32 - 7)) + (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))))) -1) / (u64)(( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))))))) +
  (((u64)nsec * ((unsigned long)((((u64)1 << ((32 - 7) + 29)) + (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))))) -1) / (u64)(( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))))))) >>
   (((32 - 7) + 29) - (32 - 7)))) >> (32 - 7);

}

static __inline__ /*__attribute__((always_inline))*/ void
jiffies_to_timespec(const unsigned long jiffies, struct timespec *value)
{




 u64 nsec = (u64)jiffies * (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))));
 value->tv_sec = div_ll_X_l_rem(nsec,1000000000L,&value->tv_nsec);
}
static __inline__ /*__attribute__((always_inline))*/ unsigned long
timeval_to_jiffies(const struct timeval *value)
{
 unsigned long sec = value->tv_sec;
 long usec = value->tv_usec;

 if (sec >= (long)((u64)((u64)((~0UL >> 1)-1) * (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))))) / 1000000000L)){
  sec = (long)((u64)((u64)((~0UL >> 1)-1) * (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))))) / 1000000000L);
  usec = 0;
 }
 return (((u64)sec * ((unsigned long)((((u64)1000000000L << (32 - 7)) + (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))))) -1) / (u64)(( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))))))) +
  (((u64)usec * ((unsigned long)((((u64)1000L << ((32 - 7) + 19)) + (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))))) -1) / (u64)(( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))))))) + (u64)(((u64)1 << ((32 - 7) + 19)) - 1)) >>
   (((32 - 7) + 19) - (32 - 7)))) >> (32 - 7);
}

static __inline__ /*__attribute__((always_inline))*/ void
jiffies_to_timeval(const unsigned long jiffies, struct timeval *value)
{




 u64 nsec = (u64)jiffies * (( (((1000000UL * 1000) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((((1000000UL * 1000) % ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))) << (8)) + ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100))))) / 2) / ((( (((1193182) / (((1193182 + 100/2) / 100))) << (8)) + ((((1193182) % (((1193182 + 100/2) / 100))) << (8)) + (((1193182 + 100/2) / 100)) / 2) / (((1193182 + 100/2) / 100)))))));
 long tv_usec;

 value->tv_sec = div_ll_X_l_rem(nsec,1000000000L,&tv_usec);
 tv_usec /= 1000L;
 value->tv_usec = tv_usec;
}




static inline /*__attribute__((always_inline))*/ clock_t jiffies_to_clock_t(long x)
{

 return x / (100 / 100);





}

static inline /*__attribute__((always_inline))*/ unsigned long clock_t_to_jiffies(unsigned long x)
{

 if (x >= ~0UL / (100 / 100))
  return ~0UL;
 return x * (100 / 100);
}

static inline /*__attribute__((always_inline))*/ u64 jiffies_64_to_clock_t(u64 x)
{

 ({ unsigned long __upper, __low, __high, __mod, __base; __base = (100 / 100); asm("":"=a" (__low), "=d" (__high):"A" (x)); __upper = __high; if (__high) { __upper = __high % (__base); __high = __high / (__base); } asm("divl %2":"=a" (__low), "=d" (__mod):"rm" (__base), "0" (__low), "1" (__upper)); asm("":"=A" (x):"a" (__low),"d" (__high)); __mod; });
 return x;
}

static inline /*__attribute__((always_inline))*/ u64 nsec_to_clock_t(u64 x)
{

 ({ unsigned long __upper, __low, __high, __mod, __base; __base = ((1000000000L / 100)); asm("":"=a" (__low), "=d" (__high):"A" (x)); __upper = __high; if (__high) { __upper = __high % (__base); __high = __high / (__base); } asm("divl %2":"=a" (__low), "=d" (__mod):"rm" (__base), "0" (__low), "1" (__upper)); asm("":"=A" (x):"a" (__low),"d" (__high)); __mod; });
 return x;
}
struct rb_node
{
 unsigned long rb_parent_color;


 struct rb_node *rb_right;
 struct rb_node *rb_left;
} __attribute__((aligned(sizeof(long))));


struct rb_root
{
 struct rb_node *rb_node;
};
static inline /*__attribute__((always_inline))*/ void rb_set_parent(struct rb_node *rb, struct rb_node *p)
{
 rb->rb_parent_color = (rb->rb_parent_color & 3) | (unsigned long)p;
}
static inline /*__attribute__((always_inline))*/ void rb_set_color(struct rb_node *rb, int color)
{
 rb->rb_parent_color = (rb->rb_parent_color & ~1) | color;
}
extern void rb_insert_color(struct rb_node *, struct rb_root *);
extern void rb_erase(struct rb_node *, struct rb_root *);


extern struct rb_node *rb_next(struct rb_node *);
extern struct rb_node *rb_prev(struct rb_node *);
extern struct rb_node *rb_first(struct rb_root *);
extern struct rb_node *rb_last(struct rb_root *);


extern void rb_replace_node(struct rb_node *victim, struct rb_node *new,
       struct rb_root *root);

static inline /*__attribute__((always_inline))*/ void rb_link_node(struct rb_node * node, struct rb_node * parent,
    struct rb_node ** rb_link)
{
 node->rb_parent_color = (unsigned long )parent;
 node->rb_left = node->rb_right = ((void *)0);

 *rb_link = node;
}












typedef struct { unsigned long bits[((((1 << 0))+32 -1)/32)]; } nodemask_t;
extern nodemask_t _unused_nodemask_arg_;


static inline /*__attribute__((always_inline))*/ void __node_set(int node, volatile nodemask_t *dstp)
{
 set_bit(node, dstp->bits);
}


static inline /*__attribute__((always_inline))*/ void __node_clear(int node, volatile nodemask_t *dstp)
{
 clear_bit(node, dstp->bits);
}


static inline /*__attribute__((always_inline))*/ void __nodes_setall(nodemask_t *dstp, int nbits)
{
 bitmap_fill(dstp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ void __nodes_clear(nodemask_t *dstp, int nbits)
{
 bitmap_zero(dstp->bits, nbits);
}






static inline /*__attribute__((always_inline))*/ int __node_test_and_set(int node, nodemask_t *addr)
{
 return test_and_set_bit(node, addr->bits);
}



static inline /*__attribute__((always_inline))*/ void __nodes_and(nodemask_t *dstp, const nodemask_t *src1p,
     const nodemask_t *src2p, int nbits)
{
 bitmap_and(dstp->bits, src1p->bits, src2p->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __nodes_or(nodemask_t *dstp, const nodemask_t *src1p,
     const nodemask_t *src2p, int nbits)
{
 bitmap_or(dstp->bits, src1p->bits, src2p->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __nodes_xor(nodemask_t *dstp, const nodemask_t *src1p,
     const nodemask_t *src2p, int nbits)
{
 bitmap_xor(dstp->bits, src1p->bits, src2p->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __nodes_andnot(nodemask_t *dstp, const nodemask_t *src1p,
     const nodemask_t *src2p, int nbits)
{
 bitmap_andnot(dstp->bits, src1p->bits, src2p->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __nodes_complement(nodemask_t *dstp,
     const nodemask_t *srcp, int nbits)
{
 bitmap_complement(dstp->bits, srcp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __nodes_equal(const nodemask_t *src1p,
     const nodemask_t *src2p, int nbits)
{
 return bitmap_equal(src1p->bits, src2p->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __nodes_intersects(const nodemask_t *src1p,
     const nodemask_t *src2p, int nbits)
{
 return bitmap_intersects(src1p->bits, src2p->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __nodes_subset(const nodemask_t *src1p,
     const nodemask_t *src2p, int nbits)
{
 return bitmap_subset(src1p->bits, src2p->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __nodes_empty(const nodemask_t *srcp, int nbits)
{
 return bitmap_empty(srcp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __nodes_full(const nodemask_t *srcp, int nbits)
{
 return bitmap_full(srcp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __nodes_weight(const nodemask_t *srcp, int nbits)
{
 return bitmap_weight(srcp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __nodes_shift_right(nodemask_t *dstp,
     const nodemask_t *srcp, int n, int nbits)
{
 bitmap_shift_right(dstp->bits, srcp->bits, n, nbits);
}



static inline /*__attribute__((always_inline))*/ void __nodes_shift_left(nodemask_t *dstp,
     const nodemask_t *srcp, int n, int nbits)
{
 bitmap_shift_left(dstp->bits, srcp->bits, n, nbits);
}





static inline /*__attribute__((always_inline))*/ int __first_node(const nodemask_t *srcp)
{
 return ({ int __x = ((1 << 0)); int __y = (find_first_bit(srcp->bits, (1 << 0))); __x < __y ? __x: __y; });
}


static inline /*__attribute__((always_inline))*/ int __next_node(int n, const nodemask_t *srcp)
{
 return ({ int __x = ((1 << 0)); int __y = (find_next_bit(srcp->bits, (1 << 0), n+1)); __x < __y ? __x: __y; });
}
static inline /*__attribute__((always_inline))*/ int __first_unset_node(const nodemask_t *maskp)
{
 return ({ int __x = ((1 << 0)); int __y = (find_first_zero_bit(maskp->bits, (1 << 0))); __x < __y ? __x: __y; });

}
static inline /*__attribute__((always_inline))*/ int __nodemask_scnprintf(char *buf, int len,
     const nodemask_t *srcp, int nbits)
{
 return bitmap_scnprintf(buf, len, srcp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __nodemask_parse(const char *buf, int len,
     nodemask_t *dstp, int nbits)
{
 return bitmap_parse(buf, len, dstp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __nodelist_scnprintf(char *buf, int len,
     const nodemask_t *srcp, int nbits)
{
 return bitmap_scnlistprintf(buf, len, srcp->bits, nbits);
}


static inline /*__attribute__((always_inline))*/ int __nodelist_parse(const char *buf, nodemask_t *dstp, int nbits)
{
 return bitmap_parselist(buf, dstp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ int __node_remap(int oldbit,
  const nodemask_t *oldp, const nodemask_t *newp, int nbits)
{
 return bitmap_bitremap(oldbit, oldp->bits, newp->bits, nbits);
}



static inline /*__attribute__((always_inline))*/ void __nodes_remap(nodemask_t *dstp, const nodemask_t *srcp,
  const nodemask_t *oldp, const nodemask_t *newp, int nbits)
{
 bitmap_remap(dstp->bits, srcp->bits, oldp->bits, newp->bits, nbits);
}
extern nodemask_t node_online_map;
extern nodemask_t node_possible_map;


typedef struct __wait_queue wait_queue_t;
typedef int (*wait_queue_func_t)(wait_queue_t *wait, unsigned mode, int sync, void *key);
int default_wake_function(wait_queue_t *wait, unsigned mode, int sync, void *key);

struct __wait_queue {
 unsigned int flags;

 void *private;
 wait_queue_func_t func;
 struct list_head task_list;
};

struct wait_bit_key {
 void *flags;
 int bit_nr;
};

struct wait_bit_queue {
 struct wait_bit_key key;
 wait_queue_t wait;
};

struct __wait_queue_head {
 spinlock_t lock;
 struct list_head task_list;
};
typedef struct __wait_queue_head wait_queue_head_t;

struct task_struct;
extern void init_waitqueue_head(wait_queue_head_t *q);

static inline /*__attribute__((always_inline))*/ void init_waitqueue_entry(wait_queue_t *q, struct task_struct *p)
{
 q->flags = 0;
 q->private = p;
 q->func = default_wake_function;
}

static inline /*__attribute__((always_inline))*/ void init_waitqueue_func_entry(wait_queue_t *q,
     wait_queue_func_t func)
{
 q->flags = 0;
 q->private = ((void *)0);
 q->func = func;
}

static inline /*__attribute__((always_inline))*/ int waitqueue_active(wait_queue_head_t *q)
{
 return !list_empty(&q->task_list);
}
extern void add_wait_queue(wait_queue_head_t *q, wait_queue_t * wait) __attribute__((regparm(3)));
extern void add_wait_queue_exclusive(wait_queue_head_t *q, wait_queue_t * wait) __attribute__((regparm(3)));
extern void remove_wait_queue(wait_queue_head_t *q, wait_queue_t * wait) __attribute__((regparm(3)));

static inline /*__attribute__((always_inline))*/ void __add_wait_queue(wait_queue_head_t *head, wait_queue_t *new)
{
 list_add(&new->task_list, &head->task_list);
}




static inline /*__attribute__((always_inline))*/ void __add_wait_queue_tail(wait_queue_head_t *head,
      wait_queue_t *new)
{
 list_add_tail(&new->task_list, &head->task_list);
}

static inline /*__attribute__((always_inline))*/ void __remove_wait_queue(wait_queue_head_t *head,
       wait_queue_t *old)
{
 list_del(&old->task_list);
}

void __wake_up(wait_queue_head_t *q, unsigned int mode, int nr, void *key) /*__attribute__((regparm(3)))*/;
extern void __wake_up_locked(wait_queue_head_t *q, unsigned int mode) __attribute__((regparm(3)));
extern void __wake_up_sync(wait_queue_head_t *q, unsigned int mode, int nr) /*__attribute__((regparm(3)))*/;
void __wake_up_bit(wait_queue_head_t *, void *, int) /*__attribute__((regparm(3)))*/;
int __wait_on_bit(wait_queue_head_t *, struct wait_bit_queue *, int (*)(void *), unsigned) /*__attribute__((regparm(3)))*/;
int __wait_on_bit_lock(wait_queue_head_t *, struct wait_bit_queue *, int (*)(void *), unsigned) /*__attribute__((regparm(3)))*/;
void wake_up_bit(void *, int) /*__attribute__((regparm(3)))*/;
int out_of_line_wait_on_bit(void *, int, int (*)(void *), unsigned) /*__attribute__((regparm(3)))*/;
int out_of_line_wait_on_bit_lock(void *, int, int (*)(void *), unsigned) /*__attribute__((regparm(3)))*/;
wait_queue_head_t *bit_waitqueue(void *, int) /*__attribute__((regparm(3)))*/;
static inline /*__attribute__((always_inline))*/ void add_wait_queue_exclusive_locked(wait_queue_head_t *q,
         wait_queue_t * wait)
{
 wait->flags |= 0x01;
 __add_wait_queue_tail(q, wait);
}




static inline /*__attribute__((always_inline))*/ void remove_wait_queue_locked(wait_queue_head_t *q,
         wait_queue_t * wait)
{
 __remove_wait_queue(q, wait);
}






extern void sleep_on(wait_queue_head_t *q) /*__attribute__((regparm(3)))*/;
extern long sleep_on_timeout(wait_queue_head_t *q, signed long timeout) /*__attribute__((regparm(3)))*/;

extern void interruptible_sleep_on(wait_queue_head_t *q) /*__attribute__((regparm(3)))*/;
extern long interruptible_sleep_on_timeout(wait_queue_head_t *q, signed long timeout) /*__attribute__((regparm(3)))*/;





void prepare_to_wait(wait_queue_head_t *q, wait_queue_t *wait, int state) /*__attribute__((regparm(3)))*/;

void prepare_to_wait_exclusive(wait_queue_head_t *q, wait_queue_t *wait, int state) /*__attribute__((regparm(3)))*/;

void finish_wait(wait_queue_head_t *q, wait_queue_t *wait) /*__attribute__((regparm(3)))*/;
int autoremove_wake_function(wait_queue_t *wait, unsigned mode, int sync, void *key);
int wake_bit_function(wait_queue_t *wait, unsigned mode, int sync, void *key);
static inline /*__attribute__((always_inline))*/ int wait_on_bit(void *word, int bit,
    int (*action)(void *), unsigned mode)
{
 if (!(__builtin_constant_p(bit) ? constant_test_bit((bit),(word)) : variable_test_bit((bit),(word))))
  return 0;
 return out_of_line_wait_on_bit(word, bit, action, mode);
}
static inline /*__attribute__((always_inline))*/ int wait_on_bit_lock(void *word, int bit,
    int (*action)(void *), unsigned mode)
{
 if (!test_and_set_bit(bit, word))
  return 0;
 return out_of_line_wait_on_bit_lock(word, bit, action, mode);
}
struct rw_semaphore;




struct rwsem_waiter;

extern struct rw_semaphore *rwsem_down_read_failed(struct rw_semaphore *sem) /*__attribute__((regparm(3)))*/;
extern struct rw_semaphore *rwsem_down_write_failed(struct rw_semaphore *sem) /*__attribute__((regparm(3)))*/;
extern struct rw_semaphore *rwsem_wake(struct rw_semaphore *) /*__attribute__((regparm(3)))*/;
extern struct rw_semaphore *rwsem_downgrade_wake(struct rw_semaphore *sem) /*__attribute__((regparm(3)))*/;




struct rw_semaphore {
 signed long count;






 spinlock_t wait_lock;
 struct list_head wait_list;



};
extern void __init_rwsem(struct rw_semaphore *sem, const char *name,
    struct lock_class_key *key);
static inline /*__attribute__((always_inline))*/ void __down_read(struct rw_semaphore *sem)
{
 __asm__ __volatile__(
  "# beginning down_read\n\t"
"" "  incl      (%%eax)\n\t"
  "  js        2f\n\t"
  "1:\n\t"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\n\t"
  "  pushl     %%ecx\n\t"
  "  pushl     %%edx\n\t"
  "  call      rwsem_down_read_failed\n\t"
  "  popl      %%edx\n\t"
  "  popl      %%ecx\n\t"
  "  jmp       1b\n"
  ".previous\n\t"
  "# ending down_read\n\t"
  : "+m" (sem->count)
  : "a" (sem)
  : "memory", "cc");
}




static inline /*__attribute__((always_inline))*/ int __down_read_trylock(struct rw_semaphore *sem)
{
 __s32 result, tmp;
 __asm__ __volatile__(
  "# beginning __down_read_trylock\n\t"
  "  movl      %0,%1\n\t"
  "1:\n\t"
  "  movl	     %1,%2\n\t"
  "  addl      %3,%2\n\t"
  "  jle	     2f\n\t"
"" "  cmpxchgl  %2,%0\n\t"
  "  jnz	     1b\n\t"
  "2:\n\t"
  "# ending __down_read_trylock\n\t"
  : "+m" (sem->count), "=&a" (result), "=&r" (tmp)
  : "i" (0x00000001)
  : "memory", "cc");
 return result>=0 ? 1 : 0;
}




static inline /*__attribute__((always_inline))*/ void __down_write_nested(struct rw_semaphore *sem, int subclass)
{
 int tmp;

 tmp = ((-0x00010000) + 0x00000001);
 __asm__ __volatile__(
  "# beginning down_write\n\t"
"" "  xadd      %%edx,(%%eax)\n\t"
  "  testl     %%edx,%%edx\n\t"
  "  jnz       2f\n\t"
  "1:\n\t"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\n\t"
  "  pushl     %%ecx\n\t"
  "  call      rwsem_down_write_failed\n\t"
  "  popl      %%ecx\n\t"
  "  jmp       1b\n"
  ".previous\n\t"
  "# ending down_write"
  : "+m" (sem->count), "=d" (tmp)
  : "a" (sem), "1" (tmp)
  : "memory", "cc");
}

static inline /*__attribute__((always_inline))*/ void __down_write(struct rw_semaphore *sem)
{
 __down_write_nested(sem, 0);
}




static inline /*__attribute__((always_inline))*/ int __down_write_trylock(struct rw_semaphore *sem)
{
 signed long ret = ({ __typeof__(*(&sem->count)) __ret; if (__builtin_expect(!!(boot_cpu_data.x86 > 3), 1)) __ret = __cmpxchg((&sem->count), (unsigned long)(0x00000000), (unsigned long)(((-0x00010000) + 0x00000001)), sizeof(*(&sem->count))); else __ret = cmpxchg_386((&sem->count), (unsigned long)(0x00000000), (unsigned long)(((-0x00010000) + 0x00000001)), sizeof(*(&sem->count))); __ret; });


 if (ret == 0x00000000)
  return 1;
 return 0;
}




static inline /*__attribute__((always_inline))*/ void __up_read(struct rw_semaphore *sem)
{
 __s32 tmp = -0x00000001;
 __asm__ __volatile__(
  "# beginning __up_read\n\t"
"" "  xadd      %%edx,(%%eax)\n\t"
  "  js        2f\n\t"
  "1:\n\t"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\n\t"
  "  decw      %%dx\n\t"
  "  jnz       1b\n\t"
  "  pushl     %%ecx\n\t"
  "  call      rwsem_wake\n\t"
  "  popl      %%ecx\n\t"
  "  jmp       1b\n"
  ".previous\n\t"
  "# ending __up_read\n"
  : "+m" (sem->count), "=d" (tmp)
  : "a" (sem), "1" (tmp)
  : "memory", "cc");
}




static inline /*__attribute__((always_inline))*/ void __up_write(struct rw_semaphore *sem)
{
 __asm__ __volatile__(
  "# beginning __up_write\n\t"
  "  movl      %2,%%edx\n\t"
"" "  xaddl     %%edx,(%%eax)\n\t"
  "  jnz       2f\n\t"
  "1:\n\t"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\n\t"
  "  decw      %%dx\n\t"
  "  jnz       1b\n\t"
  "  pushl     %%ecx\n\t"
  "  call      rwsem_wake\n\t"
  "  popl      %%ecx\n\t"
  "  jmp       1b\n"
  ".previous\n\t"
  "# ending __up_write\n"
  : "+m" (sem->count)
  : "a" (sem), "i" (-((-0x00010000) + 0x00000001))
  : "memory", "cc", "edx");
}




static inline /*__attribute__((always_inline))*/ void __downgrade_write(struct rw_semaphore *sem)
{
 __asm__ __volatile__(
  "# beginning __downgrade_write\n\t"
"" "  addl      %2,(%%eax)\n\t"
  "  js        2f\n\t"
  "1:\n\t"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\n\t"
  "  pushl     %%ecx\n\t"
  "  pushl     %%edx\n\t"
  "  call      rwsem_downgrade_wake\n\t"
  "  popl      %%edx\n\t"
  "  popl      %%ecx\n\t"
  "  jmp       1b\n"
  ".previous\n\t"
  "# ending __downgrade_write\n"
  : "+m" (sem->count)
  : "a" (sem), "i" (-(-0x00010000))
  : "memory", "cc");
}




static inline /*__attribute__((always_inline))*/ void rwsem_atomic_add(int delta, struct rw_semaphore *sem)
{
 __asm__ __volatile__(
"" "addl %1,%0"
  : "+m" (sem->count)
  : "ir" (delta));
}




static inline /*__attribute__((always_inline))*/ int rwsem_atomic_update(int delta, struct rw_semaphore *sem)
{
 int tmp = delta;

 __asm__ __volatile__(
"" "xadd %0,%1"
  : "+r" (tmp), "+m" (sem->count)
  : : "memory");

 return tmp+delta;
}

static inline /*__attribute__((always_inline))*/ int rwsem_is_locked(struct rw_semaphore *sem)
{
 return (sem->count != 0);
}





extern void down_read(struct rw_semaphore *sem);




extern int down_read_trylock(struct rw_semaphore *sem);




extern void down_write(struct rw_semaphore *sem);




extern int down_write_trylock(struct rw_semaphore *sem);




extern void up_read(struct rw_semaphore *sem);




extern void up_write(struct rw_semaphore *sem);




extern void downgrade_write(struct rw_semaphore *sem);

struct semaphore {
 atomic_t count;
 int sleepers;
 wait_queue_head_t wait;
};
static inline /*__attribute__((always_inline))*/ void sema_init (struct semaphore *sem, int val)
{






 (((&sem->count)->counter) = (val));
 sem->sleepers = 0;
 init_waitqueue_head(&sem->wait);
}

static inline /*__attribute__((always_inline))*/ void init_MUTEX (struct semaphore *sem)
{
 sema_init(sem, 1);
}

static inline /*__attribute__((always_inline))*/ void init_MUTEX_LOCKED (struct semaphore *sem)
{
 sema_init(sem, 0);
}

/*__attribute__((regparm(3)))*/ void __down_failed(void );
/*__attribute__((regparm(3)))*/ int __down_failed_interruptible(void );
/*__attribute__((regparm(3)))*/ int __down_failed_trylock(void );
/*__attribute__((regparm(3)))*/ void __up_wakeup(void );






static inline /*__attribute__((always_inline))*/ void down(struct semaphore * sem)
{
 do { do { } while (0); } while (0);
 __asm__ __volatile__(
  "# atomic down operation\n\t"
  "" "decl %0\n\t"
  "js 2f\n"
  "1:\n"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\tlea %0,%%eax\n\t"
  "call __down_failed\n\t"
  "jmp 1b\n"
  ".previous\n\t"
  :"+m" (sem->count)
  :
  :"memory","ax");
}





static inline /*__attribute__((always_inline))*/ int down_interruptible(struct semaphore * sem)
{
 int result;

 do { do { } while (0); } while (0);
 __asm__ __volatile__(
  "# atomic interruptible down operation\n\t"
  "" "decl %1\n\t"
  "js 2f\n\t"
  "xorl %0,%0\n"
  "1:\n"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\tlea %1,%%eax\n\t"
  "call __down_failed_interruptible\n\t"
  "jmp 1b\n"
  ".previous\n\t"
  :"=a" (result), "+m" (sem->count)
  :
  :"memory");
 return result;
}





static inline /*__attribute__((always_inline))*/ int down_trylock(struct semaphore * sem)
{
 int result;

 __asm__ __volatile__(
  "# atomic interruptible down operation\n\t"
  "" "decl %1\n\t"
  "js 2f\n\t"
  "xorl %0,%0\n"
  "1:\n"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\tlea %1,%%eax\n\t"
  "call __down_failed_trylock\n\t"
  "jmp 1b\n"
  ".previous\n\t"
  :"=a" (result), "+m" (sem->count)
  :
  :"memory");
 return result;
}







static inline /*__attribute__((always_inline))*/ void up(struct semaphore * sem)
{
 __asm__ __volatile__(
  "# atomic up operation\n\t"
  "" "incl %0\n\t"
  "jle 2f\n"
  "1:\n"
  ".subsection 1\n\t" "" ".ifndef " ".text.lock."/*KBUILD_BASENAME*/ "\n\t" ".text.lock."/*KBUILD_BASENAME*/ ":\n\t" ".endif\n"
  "2:\tlea %0,%%eax\n\t"
  "call __up_wakeup\n\t"
  "jmp 1b\n"
  ".previous\n\t"
  ".subsection 0\n"
  :"+m" (sem->count)
  :
  :"memory","ax");
}

struct pt_regs {
 long ebx;
 long ecx;
 long edx;
 long esi;
 long edi;
 long ebp;
 long eax;
 int xds;
 int xes;
 long orig_eax;
 long eip;
 int xcs;
 long eflags;
 long esp;
 int xss;
};
struct task_struct;
extern void send_sigtrap(struct task_struct *tsk, struct pt_regs *regs, int error_code);
static inline /*__attribute__((always_inline))*/ int user_mode(struct pt_regs *regs)
{
 return (regs->xcs & 3) != 0;
}
static inline /*__attribute__((always_inline))*/ int user_mode_vm(struct pt_regs *regs)
{
 return ((regs->xcs & 3) | (regs->eflags & 0)) != 0;
}
typedef struct {
 int size;
 struct semaphore sem;
 void *ldt;
 void *vdso;
} mm_context_t;









typedef unsigned long cputime_t;
typedef u64 cputime64_t;

extern void cpu_idle(void);
static inline /*__attribute__((always_inline))*/ int up_smp_call_function(void)
{
 return 0;
}
static inline /*__attribute__((always_inline))*/ void smp_send_reschedule(int cpu) { }
void smp_setup_processor_id(void);



struct ipc_perm
{
 __kernel_key_t key;
 __kernel_uid_t uid;
 __kernel_gid_t gid;
 __kernel_uid_t cuid;
 __kernel_gid_t cgid;
 __kernel_mode_t mode;
 unsigned short seq;
};


struct ipc64_perm
{
 __kernel_key_t key;
 __kernel_uid32_t uid;
 __kernel_gid32_t gid;
 __kernel_uid32_t cuid;
 __kernel_gid32_t cgid;
 __kernel_mode_t mode;
 unsigned short __pad1;
 unsigned short seq;
 unsigned short __pad2;
 unsigned long __unused1;
 unsigned long __unused2;
};
struct kern_ipc_perm
{
 spinlock_t lock;
 int deleted;
 key_t key;
 uid_t uid;
 gid_t gid;
 uid_t cuid;
 gid_t cgid;
 mode_t mode;
 unsigned long seq;
 void *security;
};
struct semid_ds {
 struct ipc_perm sem_perm;
 __kernel_time_t sem_otime;
 __kernel_time_t sem_ctime;
 struct sem *sem_base;
 struct sem_queue *sem_pending;
 struct sem_queue **sem_pending_last;
 struct sem_undo *undo;
 unsigned short sem_nsems;
};


struct semid64_ds {
 struct ipc64_perm sem_perm;
 __kernel_time_t sem_otime;
 unsigned long __unused1;
 __kernel_time_t sem_ctime;
 unsigned long __unused2;
 unsigned long sem_nsems;
 unsigned long __unused3;
 unsigned long __unused4;
};


struct sembuf {
 unsigned short sem_num;
 short sem_op;
 short sem_flg;
};


union semun {
 int val;
 struct semid_ds *buf;
 unsigned short *array;
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
struct task_struct;


struct sem {
 int semval;
 int sempid;
};


struct sem_array {
 struct kern_ipc_perm sem_perm;
 int sem_id;
 time_t sem_otime;
 time_t sem_ctime;
 struct sem *sem_base;
 struct sem_queue *sem_pending;
 struct sem_queue **sem_pending_last;
 struct sem_undo *undo;
 unsigned long sem_nsems;
};


struct sem_queue {
 struct sem_queue * next;
 struct sem_queue ** prev;
 struct task_struct* sleeper;
 struct sem_undo * undo;
 int pid;
 int status;
 struct sem_array * sma;
 int id;
 struct sembuf * sops;
 int nsops;
 int alter;
};




struct sem_undo {
 struct sem_undo * proc_next;
 struct sem_undo * id_next;
 int semid;
 short * semadj;
};




struct sem_undo_list {
 atomic_t refcnt;
 spinlock_t lock;
 struct sem_undo *proc_list;
};

struct sysv_sem {
 struct sem_undo_list *undo_list;
};



extern int copy_semundo(unsigned long clone_flags, struct task_struct *tsk);
extern void exit_sem(struct task_struct *tsk);



struct siginfo;
typedef unsigned long old_sigset_t;

typedef struct {
 unsigned long sig[(64 / 32)];
} sigset_t;
typedef void __signalfn_t(int);
typedef __signalfn_t *__sighandler_t;

typedef void __restorefn_t(void);
typedef __restorefn_t *__sigrestore_t;


struct old_sigaction {
 __sighandler_t sa_handler;
 old_sigset_t sa_mask;
 unsigned long sa_flags;
 __sigrestore_t sa_restorer;
};

struct sigaction {
 __sighandler_t sa_handler;
 unsigned long sa_flags;
 __sigrestore_t sa_restorer;
 sigset_t sa_mask;
};

struct k_sigaction {
 struct sigaction sa;
};
typedef struct sigaltstack {
 void *ss_sp;
 int ss_flags;
 size_t ss_size;
} stack_t;
static __inline__ /*__attribute__((always_inline))*/ void __gen_sigaddset(sigset_t *set, int _sig)
{
 __asm__("btsl %1,%0" : "+m"(*set) : "Ir"(_sig - 1) : "cc");
}

static __inline__ /*__attribute__((always_inline))*/ void __const_sigaddset(sigset_t *set, int _sig)
{
 unsigned long sig = _sig - 1;
 set->sig[sig / 32] |= 1 << (sig % 32);
}







static __inline__ /*__attribute__((always_inline))*/ void __gen_sigdelset(sigset_t *set, int _sig)
{
 __asm__("btrl %1,%0" : "+m"(*set) : "Ir"(_sig - 1) : "cc");
}

static __inline__ /*__attribute__((always_inline))*/ void __const_sigdelset(sigset_t *set, int _sig)
{
 unsigned long sig = _sig - 1;
 set->sig[sig / 32] &= ~(1 << (sig % 32));
}

static __inline__ /*__attribute__((always_inline))*/ int __const_sigismember(sigset_t *set, int _sig)
{
 unsigned long sig = _sig - 1;
 return 1 & (set->sig[sig / 32] >> (sig % 32));
}

static __inline__ /*__attribute__((always_inline))*/ int __gen_sigismember(sigset_t *set, int _sig)
{
 int ret;
 __asm__("btl %2,%1\n\tsbbl %0,%0"
  : "=r"(ret) : "m"(*set), "Ir"(_sig-1) : "cc");
 return ret;
}






static __inline__ /*__attribute__((always_inline))*/ int sigfindinword(unsigned long word)
{
 __asm__("bsfl %1,%0" : "=r"(word) : "rm"(word) : "cc");
 return word;
}

struct pt_regs;









typedef union sigval {
 int sival_int;
 void *sival_ptr;
} sigval_t;
typedef struct siginfo {
 int si_signo;
 int si_errno;
 int si_code;

 union {
  int _pad[((128 - (3 * sizeof(int))) / sizeof(int))];


  struct {
   pid_t _pid;
   uid_t _uid;
  } _kill;


  struct {
   timer_t _tid;
   int _overrun;
   char _pad[sizeof( uid_t) - sizeof(int)];
   sigval_t _sigval;
   int _sys_private;
  } _timer;


  struct {
   pid_t _pid;
   uid_t _uid;
   sigval_t _sigval;
  } _rt;


  struct {
   pid_t _pid;
   uid_t _uid;
   int _status;
   clock_t _utime;
   clock_t _stime;
  } _sigchld;


  struct {
   void *_addr;



  } _sigfault;


  struct {
   long _band;
   int _fd;
  } _sigpoll;
 } _sifields;
} siginfo_t;
typedef struct sigevent {
 sigval_t sigev_value;
 int sigev_signo;
 int sigev_notify;
 union {
  int _pad[((64 - (sizeof(int) * 2 + sizeof(sigval_t))) / sizeof(int))];
   int _tid;

  struct {
   void (*_function)(sigval_t);
   void *_attribute;
  } _sigev_thread;
 } _sigev_un;
} sigevent_t;







struct siginfo;
void do_schedule_next_timer(struct siginfo *info);





static inline /*__attribute__((always_inline))*/ void copy_siginfo(struct siginfo *to, struct siginfo *from)
{
 if (from->si_code < 0)
  (__builtin_constant_p(sizeof(*to)) ? __constant_memcpy((to),(from),(sizeof(*to))) : __memcpy((to),(from),(sizeof(*to))));
 else

  (__builtin_constant_p((3 * sizeof(int)) + sizeof(from->_sifields._sigchld)) ? __constant_memcpy((to),(from),((3 * sizeof(int)) + sizeof(from->_sifields._sigchld))) : __memcpy((to),(from),((3 * sizeof(int)) + sizeof(from->_sifields._sigchld))));
}



extern int copy_siginfo_to_user(struct siginfo *to, struct siginfo *from);
struct sigqueue {
 struct list_head list;
 int flags;
 siginfo_t info;
 struct user_struct *user;
};




struct sigpending {
 struct list_head list;
 sigset_t signal;
};
static inline /*__attribute__((always_inline))*/ int sigisemptyset(sigset_t *set)
{
 extern void _NSIG_WORDS_is_unsupported_size(void);
 switch ((64 / 32)) {
 case 4:
  return (set->sig[3] | set->sig[2] |
   set->sig[1] | set->sig[0]) == 0;
 case 2:
  return (set->sig[1] | set->sig[0]) == 0;
 case 1:
  return set->sig[0] == 0;
 default:
  _NSIG_WORDS_is_unsupported_size();
  return 0;
 }
}
static inline /*__attribute__((always_inline))*/ void sigorsets(sigset_t *r, const sigset_t *a, const sigset_t *b) { extern void _NSIG_WORDS_is_unsupported_size(void); unsigned long a0, a1, a2, a3, b0, b1, b2, b3; switch ((64 / 32)) { case 4: a3 = a->sig[3]; a2 = a->sig[2]; b3 = b->sig[3]; b2 = b->sig[2]; r->sig[3] = ((a3) | (b3)); r->sig[2] = ((a2) | (b2)); case 2: a1 = a->sig[1]; b1 = b->sig[1]; r->sig[1] = ((a1) | (b1)); case 1: a0 = a->sig[0]; b0 = b->sig[0]; r->sig[0] = ((a0) | (b0)); break; default: _NSIG_WORDS_is_unsupported_size(); } }


static inline /*__attribute__((always_inline))*/ void sigandsets(sigset_t *r, const sigset_t *a, const sigset_t *b) { extern void _NSIG_WORDS_is_unsupported_size(void); unsigned long a0, a1, a2, a3, b0, b1, b2, b3; switch ((64 / 32)) { case 4: a3 = a->sig[3]; a2 = a->sig[2]; b3 = b->sig[3]; b2 = b->sig[2]; r->sig[3] = ((a3) & (b3)); r->sig[2] = ((a2) & (b2)); case 2: a1 = a->sig[1]; b1 = b->sig[1]; r->sig[1] = ((a1) & (b1)); case 1: a0 = a->sig[0]; b0 = b->sig[0]; r->sig[0] = ((a0) & (b0)); break; default: _NSIG_WORDS_is_unsupported_size(); } }


static inline /*__attribute__((always_inline))*/ void signandsets(sigset_t *r, const sigset_t *a, const sigset_t *b) { extern void _NSIG_WORDS_is_unsupported_size(void); unsigned long a0, a1, a2, a3, b0, b1, b2, b3; switch ((64 / 32)) { case 4: a3 = a->sig[3]; a2 = a->sig[2]; b3 = b->sig[3]; b2 = b->sig[2]; r->sig[3] = ((a3) & ~(b3)); r->sig[2] = ((a2) & ~(b2)); case 2: a1 = a->sig[1]; b1 = b->sig[1]; r->sig[1] = ((a1) & ~(b1)); case 1: a0 = a->sig[0]; b0 = b->sig[0]; r->sig[0] = ((a0) & ~(b0)); break; default: _NSIG_WORDS_is_unsupported_size(); } }
static inline /*__attribute__((always_inline))*/ void signotset(sigset_t *set) { extern void _NSIG_WORDS_is_unsupported_size(void); switch ((64 / 32)) { case 4: set->sig[3] = (~(set->sig[3])); set->sig[2] = (~(set->sig[2])); case 2: set->sig[1] = (~(set->sig[1])); case 1: set->sig[0] = (~(set->sig[0])); break; default: _NSIG_WORDS_is_unsupported_size(); } }




static inline /*__attribute__((always_inline))*/ void sigemptyset(sigset_t *set)
{
 switch ((64 / 32)) {
 default:
  (__builtin_constant_p(0) ? (__builtin_constant_p((sizeof(sigset_t))) ? __constant_c_and_count_memset(((set)),((0x01010101UL*(unsigned char)(0))),((sizeof(sigset_t)))) : __constant_c_memset(((set)),((0x01010101UL*(unsigned char)(0))),((sizeof(sigset_t))))) : (__builtin_constant_p((sizeof(sigset_t))) ? __memset_generic((((set))),(((0))),(((sizeof(sigset_t))))) : __memset_generic(((set)),((0)),((sizeof(sigset_t))))));
  break;
 case 2: set->sig[1] = 0;
 case 1: set->sig[0] = 0;
  break;
 }
}

static inline /*__attribute__((always_inline))*/ void sigfillset(sigset_t *set)
{
 switch ((64 / 32)) {
 default:
  (__builtin_constant_p(-1) ? (__builtin_constant_p((sizeof(sigset_t))) ? __constant_c_and_count_memset(((set)),((0x01010101UL*(unsigned char)(-1))),((sizeof(sigset_t)))) : __constant_c_memset(((set)),((0x01010101UL*(unsigned char)(-1))),((sizeof(sigset_t))))) : (__builtin_constant_p((sizeof(sigset_t))) ? __memset_generic((((set))),(((-1))),(((sizeof(sigset_t))))) : __memset_generic(((set)),((-1)),((sizeof(sigset_t))))));
  break;
 case 2: set->sig[1] = -1;
 case 1: set->sig[0] = -1;
  break;
 }
}



static inline /*__attribute__((always_inline))*/ void sigaddsetmask(sigset_t *set, unsigned long mask)
{
 set->sig[0] |= mask;
}

static inline /*__attribute__((always_inline))*/ void sigdelsetmask(sigset_t *set, unsigned long mask)
{
 set->sig[0] &= ~mask;
}

static inline /*__attribute__((always_inline))*/ int sigtestsetmask(sigset_t *set, unsigned long mask)
{
 return (set->sig[0] & mask) != 0;
}

static inline /*__attribute__((always_inline))*/ void siginitset(sigset_t *set, unsigned long mask)
{
 set->sig[0] = mask;
 switch ((64 / 32)) {
 default:
  (__builtin_constant_p(0) ? (__builtin_constant_p((sizeof(long)*((64 / 32)-1))) ? __constant_c_and_count_memset(((&set->sig[1])),((0x01010101UL*(unsigned char)(0))),((sizeof(long)*((64 / 32)-1)))) : __constant_c_memset(((&set->sig[1])),((0x01010101UL*(unsigned char)(0))),((sizeof(long)*((64 / 32)-1))))) : (__builtin_constant_p((sizeof(long)*((64 / 32)-1))) ? __memset_generic((((&set->sig[1]))),(((0))),(((sizeof(long)*((64 / 32)-1))))) : __memset_generic(((&set->sig[1])),((0)),((sizeof(long)*((64 / 32)-1))))));
  break;
 case 2: set->sig[1] = 0;
 case 1: ;
 }
}

static inline /*__attribute__((always_inline))*/ void siginitsetinv(sigset_t *set, unsigned long mask)
{
 set->sig[0] = ~mask;
 switch ((64 / 32)) {
 default:
  (__builtin_constant_p(-1) ? (__builtin_constant_p((sizeof(long)*((64 / 32)-1))) ? __constant_c_and_count_memset(((&set->sig[1])),((0x01010101UL*(unsigned char)(-1))),((sizeof(long)*((64 / 32)-1)))) : __constant_c_memset(((&set->sig[1])),((0x01010101UL*(unsigned char)(-1))),((sizeof(long)*((64 / 32)-1))))) : (__builtin_constant_p((sizeof(long)*((64 / 32)-1))) ? __memset_generic((((&set->sig[1]))),(((-1))),(((sizeof(long)*((64 / 32)-1))))) : __memset_generic(((&set->sig[1])),((-1)),((sizeof(long)*((64 / 32)-1))))));
  break;
 case 2: set->sig[1] = -1;
 case 1: ;
 }
}



static inline /*__attribute__((always_inline))*/ void init_sigpending(struct sigpending *sig)
{
 sigemptyset(&sig->signal);
 INIT_LIST_HEAD(&sig->list);
}

extern void flush_sigqueue(struct sigpending *queue);


static inline /*__attribute__((always_inline))*/ int valid_signal(unsigned long sig)
{
 return sig <= 64 ? 1 : 0;
}

extern int group_send_sig_info(int sig, struct siginfo *info, struct task_struct *p);
extern int __group_send_sig_info(int, struct siginfo *, struct task_struct *);
extern long do_sigpending(void *, unsigned long);
extern int sigprocmask(int, sigset_t *, sigset_t *);

struct pt_regs;
extern int get_signal_to_deliver(siginfo_t *info, struct k_sigaction *return_ka, struct pt_regs *regs, void *cookie);





extern unsigned securebits;



struct dentry;
struct vfsmount;

struct fs_struct {
 atomic_t count;
 rwlock_t lock;
 int umask;
 struct dentry * root, * pwd, * altroot;
 struct vfsmount * rootmnt, * pwdmnt, * altrootmnt;
};







extern void exit_fs(struct task_struct *);
extern void set_fs_altroot(void);
extern void set_fs_root(struct fs_struct *, struct vfsmount *, struct dentry *);
extern void set_fs_pwd(struct fs_struct *, struct vfsmount *, struct dentry *);
extern struct fs_struct *copy_fs_struct(struct fs_struct *);
extern void put_fs_struct(struct fs_struct *);

struct completion {
 unsigned int done;
 wait_queue_head_t wait;
};
static inline /*__attribute__((always_inline))*/ void init_completion(struct completion *x)
{
 x->done = 0;
 init_waitqueue_head(&x->wait);
}

extern void wait_for_completion(struct completion *) /*__attribute__((regparm(3)))*/;
extern int wait_for_completion_interruptible(struct completion *x) /*__attribute__((regparm(3)))*/;
extern unsigned long wait_for_completion_timeout(struct completion *x, unsigned long timeout) /*__attribute__((regparm(3)))*/;

extern unsigned long wait_for_completion_interruptible_timeout( struct completion *x, unsigned long timeout) /*__attribute__((regparm(3)))*/;


extern void complete(struct completion *) /*__attribute__((regparm(3)))*/;
extern void complete_all(struct completion *) /*__attribute__((regparm(3)))*/;






typedef struct kmem_cache kmem_cache_t;




typedef int (*initcall_t)(void);
typedef void (*exitcall_t)(void);

extern initcall_t __con_initcall_start[], __con_initcall_end[];
extern initcall_t __security_initcall_start[], __security_initcall_end[];


extern char saved_command_line[];


extern void setup_arch(char **);
struct obs_kernel_param {
 const char *str;
 int (*setup_func)(char *);
 int early;
};
void /*__attribute__ ((__section__ (".init.text")))*/ parse_early_param(void);
struct free_area {
 struct list_head free_list;
 unsigned long nr_free;
};

struct pglist_data;
enum zone_stat_item {
 NR_ANON_PAGES,
 NR_FILE_MAPPED,

 NR_FILE_PAGES,
 NR_SLAB,
 NR_PAGETABLE,
 NR_FILE_DIRTY,
 NR_WRITEBACK,
 NR_UNSTABLE_NFS,
 NR_BOUNCE,
 NR_VM_ZONE_STAT_ITEMS };

struct per_cpu_pages {
 int count;
 int high;
 int batch;
 struct list_head list;
};

struct per_cpu_pageset {
 struct per_cpu_pages pcp[2];




} ;
struct zone {

 unsigned long free_pages;
 unsigned long pages_min, pages_low, pages_high;
 unsigned long lowmem_reserve[4];
 struct per_cpu_pageset pageset[1];




 spinlock_t lock;




 struct free_area free_area[11];





 spinlock_t lru_lock;
 struct list_head active_list;
 struct list_head inactive_list;
 unsigned long nr_scan_active;
 unsigned long nr_scan_inactive;
 unsigned long nr_active;
 unsigned long nr_inactive;
 unsigned long pages_scanned;
 int all_unreclaimable;


 atomic_t reclaim_in_progress;


 atomic_long_t vm_stat[NR_VM_ZONE_STAT_ITEMS];
 int temp_priority;
 int prev_priority;



 wait_queue_head_t * wait_table;
 unsigned long wait_table_hash_nr_entries;
 unsigned long wait_table_bits;




 struct pglist_data *zone_pgdat;

 unsigned long zone_start_pfn;
 unsigned long spanned_pages;
 unsigned long present_pages;




 char *name;
} ;
struct zonelist {
 struct zone *zones[(1 << 0) * 4 + 1];
};
struct bootmem_data;
typedef struct pglist_data {
 struct zone node_zones[4];
 struct zonelist node_zonelists[((0x07 + 1) / 2 + 1)];
 int nr_zones;

 struct page *node_mem_map;

 struct bootmem_data *bdata;
 unsigned long node_start_pfn;
 unsigned long node_present_pages;
 unsigned long node_spanned_pages;

 int node_id;
 wait_queue_head_t kswapd_wait;
 struct task_struct *kswapd;
 int kswapd_max_order;
} pg_data_t;





struct mutex {

 atomic_t count;
 spinlock_t wait_lock;
 struct list_head wait_list;
};





struct mutex_waiter {
 struct list_head list;
 struct task_struct *task;




};
extern void __mutex_init(struct mutex *lock, const char *name,
    struct lock_class_key *key);







static inline /*__attribute__((always_inline))*/ int /*__attribute__((regparm(3)))*/ mutex_is_locked(struct mutex *lock)
{
 return ((&lock->count)->counter) != 1;
}





extern void /*__attribute__((regparm(3)))*/ mutex_lock(struct mutex *lock);
extern int /*__attribute__((regparm(3)))*/ mutex_lock_interruptible(struct mutex *lock);
extern int /*__attribute__((regparm(3)))*/ mutex_trylock(struct mutex *lock);
extern void /*__attribute__((regparm(3)))*/ mutex_unlock(struct mutex *lock);
struct notifier_block {
 int (*notifier_call)(struct notifier_block *, unsigned long, void *);
 struct notifier_block *next;
 int priority;
};

struct atomic_notifier_head {
 spinlock_t lock;
 struct notifier_block *head;
};

struct blocking_notifier_head {
 struct rw_semaphore rwsem;
 struct notifier_block *head;
};

struct raw_notifier_head {
 struct notifier_block *head;
};
extern int atomic_notifier_chain_register(struct atomic_notifier_head *,
  struct notifier_block *);
extern int blocking_notifier_chain_register(struct blocking_notifier_head *,
  struct notifier_block *);
extern int raw_notifier_chain_register(struct raw_notifier_head *,
  struct notifier_block *);

extern int atomic_notifier_chain_unregister(struct atomic_notifier_head *,
  struct notifier_block *);
extern int blocking_notifier_chain_unregister(struct blocking_notifier_head *,
  struct notifier_block *);
extern int raw_notifier_chain_unregister(struct raw_notifier_head *,
  struct notifier_block *);

extern int atomic_notifier_call_chain(struct atomic_notifier_head *,
  unsigned long val, void *v);
extern int blocking_notifier_call_chain(struct blocking_notifier_head *,
  unsigned long val, void *v);
extern int raw_notifier_call_chain(struct raw_notifier_head *,
  unsigned long val, void *v);

struct page;
struct zone;
struct pglist_data;
static inline /*__attribute__((always_inline))*/ void pgdat_resize_lock(struct pglist_data *p, unsigned long *f) {}
static inline /*__attribute__((always_inline))*/ void pgdat_resize_unlock(struct pglist_data *p, unsigned long *f) {}
static inline /*__attribute__((always_inline))*/ void pgdat_resize_init(struct pglist_data *pgdat) {}

static inline /*__attribute__((always_inline))*/ unsigned zone_span_seqbegin(struct zone *zone)
{
 return 0;
}
static inline /*__attribute__((always_inline))*/ int zone_span_seqretry(struct zone *zone, unsigned iv)
{
 return 0;
}
static inline /*__attribute__((always_inline))*/ void zone_span_writelock(struct zone *zone) {}
static inline /*__attribute__((always_inline))*/ void zone_span_writeunlock(struct zone *zone) {}
static inline /*__attribute__((always_inline))*/ void zone_seqlock_init(struct zone *zone) {}

static inline /*__attribute__((always_inline))*/ int mhp_notimplemented(const char *func)
{
 printk("<4>" "%s() called, with CONFIG_MEMORY_HOTPLUG disabled\n", func);
 dump_stack();
 return -38;
}


static inline /*__attribute__((always_inline))*/ int __remove_pages(struct zone *zone, unsigned long start_pfn,
 unsigned long nr_pages)
{
 printk("<4>" "%s() called, not yet supported\n", (__func__));
 dump_stack();
 return -38;
}

extern int add_memory(int nid, u64 start, u64 size);
extern int arch_add_memory(int nid, u64 start, u64 size);
extern int remove_memory(u64 start, u64 size);

void __get_zone_counts(unsigned long *active, unsigned long *inactive,
   unsigned long *free, struct pglist_data *pgdat);
void get_zone_counts(unsigned long *active, unsigned long *inactive,
   unsigned long *free);
void build_all_zonelists(void);
void wakeup_kswapd(struct zone *zone, int order);
int zone_watermark_ok(struct zone *z, int order, unsigned long mark,
  int classzone_idx, int alloc_flags);

extern int init_currently_empty_zone(struct zone *zone, unsigned long start_pfn,
         unsigned long size);




static inline /*__attribute__((always_inline))*/ void memory_present(int nid, unsigned long start, unsigned long end) {}
static inline /*__attribute__((always_inline))*/ int populated_zone(struct zone *zone)
{
 return (!!zone->present_pages);
}

static inline /*__attribute__((always_inline))*/ int is_highmem_idx(int idx)
{
 return (idx == 3);
}

static inline /*__attribute__((always_inline))*/ int is_normal_idx(int idx)
{
 return (idx == 2);
}







static inline /*__attribute__((always_inline))*/ int is_highmem(struct zone *zone)
{
 return zone == zone->zone_pgdat->node_zones + 3;
}

static inline /*__attribute__((always_inline))*/ int is_normal(struct zone *zone)
{
 return zone == zone->zone_pgdat->node_zones + 2;
}

static inline /*__attribute__((always_inline))*/ int is_dma32(struct zone *zone)
{
 return zone == zone->zone_pgdat->node_zones + 1;
}

static inline /*__attribute__((always_inline))*/ int is_dma(struct zone *zone)
{
 return zone == zone->zone_pgdat->node_zones + 0;
}


struct ctl_table;
struct file;
int min_free_kbytes_sysctl_handler(struct ctl_table *, int, struct file *,
     void *, size_t *, loff_t *);
extern int sysctl_lowmem_reserve_ratio[4 -1];
int lowmem_reserve_ratio_sysctl_handler(struct ctl_table *, int, struct file *,
     void *, size_t *, loff_t *);
int percpu_pagelist_fraction_sysctl_handler(struct ctl_table *, int, struct file *,
     void *, size_t *, loff_t *);
int sysctl_min_unmapped_ratio_sysctl_handler(struct ctl_table *, int,
   struct file *, void *, size_t *, loff_t *);




extern cpumask_t cpu_coregroup_map(int cpu);







extern struct pglist_data contig_page_data;
extern struct pglist_data *first_online_pgdat(void);
extern struct pglist_data *next_online_pgdat(struct pglist_data *pgdat);
extern struct zone *next_zone(struct zone *zone);
void memory_present(int nid, unsigned long start, unsigned long end);
unsigned long /*__attribute__ ((__section__ (".init.text")))*/ node_memmap_size_bytes(int, unsigned long, unsigned long);



struct vm_area_struct;
static inline /*__attribute__((always_inline))*/ int gfp_zone(gfp_t gfp)
{
 int zone = 0x07 & ( int) gfp;
 do { if (zone >= ((0x07 + 1) / 2 + 1)) ; } while(0);
 return zone;
}
static inline /*__attribute__((always_inline))*/ void arch_free_page(struct page *page, int order) { }


extern struct page *
__alloc_pages(gfp_t, unsigned int, struct zonelist *) /*__attribute__((regparm(3)))*/;

static inline /*__attribute__((always_inline))*/ struct page *alloc_pages_node(int nid, gfp_t gfp_mask,
      unsigned int order)
{
 if (__builtin_expect(!!(order >= 11), 0))
  return ((void *)0);


 if (nid < 0)
  nid = ((0));

 return __alloc_pages(gfp_mask, order,
  (&contig_page_data)->node_zonelists + gfp_zone(gfp_mask));
}
extern unsigned long __get_free_pages(gfp_t gfp_mask, unsigned int order) /*__attribute__((regparm(3)))*/;
extern unsigned long get_zeroed_page(gfp_t gfp_mask) /*__attribute__((regparm(3)))*/;







extern void __free_pages(struct page *page, unsigned int order) /*__attribute__((regparm(3)))*/;
extern void free_pages(unsigned long addr, unsigned int order) /*__attribute__((regparm(3)))*/;
extern void free_hot_page(struct page *page) /*__attribute__((regparm(3)))*/;
extern void free_cold_page(struct page *page) /*__attribute__((regparm(3)))*/;




void page_alloc_init(void);



static inline /*__attribute__((always_inline))*/ void drain_node_pages(int node) { };
extern void /*__attribute__ ((__section__ (".init.text")))*/ kmem_cache_init(void);

extern kmem_cache_t *kmem_cache_create(const char *, size_t, size_t, unsigned long,
           void (*)(void *, kmem_cache_t *, unsigned long),
           void (*)(void *, kmem_cache_t *, unsigned long));
extern int kmem_cache_destroy(kmem_cache_t *);
extern int kmem_cache_shrink(kmem_cache_t *);
extern void *kmem_cache_alloc(kmem_cache_t *, gfp_t);
extern void *kmem_cache_zalloc(struct kmem_cache *, gfp_t);
extern void kmem_cache_free(kmem_cache_t *, void *);
extern unsigned int kmem_cache_size(kmem_cache_t *);
extern const char *kmem_cache_name(kmem_cache_t *);
extern kmem_cache_t *kmem_find_general_cachep(size_t size, gfp_t gfpflags);


struct cache_sizes {
 size_t cs_size;
 kmem_cache_t *cs_cachep;
 kmem_cache_t *cs_dmacachep;
};
extern struct cache_sizes malloc_sizes[];

extern void *__kmalloc(size_t, gfp_t);
static inline /*__attribute__((always_inline))*/ void *kmalloc(size_t size, gfp_t flags)
{
 if (__builtin_constant_p(size)) {
  int i = 0;






 if (size <= 32) goto found; else i++;

 if (size <= 64) goto found; else i++;



 if (size <= 128) goto found; else i++;

 if (size <= 192) goto found; else i++;

 if (size <= 256) goto found; else i++;
 if (size <= 512) goto found; else i++;
 if (size <= 1024) goto found; else i++;
 if (size <= 2048) goto found; else i++;
 if (size <= 4096) goto found; else i++;
 if (size <= 8192) goto found; else i++;
 if (size <= 16384) goto found; else i++;
 if (size <= 32768) goto found; else i++;
 if (size <= 65536) goto found; else i++;
 if (size <= 131072) goto found; else i++;

  {
   extern void __you_cannot_kmalloc_that_much(void);
   __you_cannot_kmalloc_that_much();
  }
found:
  return kmem_cache_alloc((flags & (( gfp_t)0x01u)) ?
   malloc_sizes[i].cs_dmacachep :
   malloc_sizes[i].cs_cachep, flags);
 }
 return __kmalloc(size, flags);
}

extern void *__kzalloc(size_t, gfp_t);






static inline /*__attribute__((always_inline))*/ void *kzalloc(size_t size, gfp_t flags)
{
 if (__builtin_constant_p(size)) {
  int i = 0;






 if (size <= 32) goto found; else i++;

 if (size <= 64) goto found; else i++;



 if (size <= 128) goto found; else i++;

 if (size <= 192) goto found; else i++;

 if (size <= 256) goto found; else i++;
 if (size <= 512) goto found; else i++;
 if (size <= 1024) goto found; else i++;
 if (size <= 2048) goto found; else i++;
 if (size <= 4096) goto found; else i++;
 if (size <= 8192) goto found; else i++;
 if (size <= 16384) goto found; else i++;
 if (size <= 32768) goto found; else i++;
 if (size <= 65536) goto found; else i++;
 if (size <= 131072) goto found; else i++;

  {
   extern void __you_cannot_kzalloc_that_much(void);
   __you_cannot_kzalloc_that_much();
  }
found:
  return kmem_cache_zalloc((flags & (( gfp_t)0x01u)) ?
   malloc_sizes[i].cs_dmacachep :
   malloc_sizes[i].cs_cachep, flags);
 }
 return __kzalloc(size, flags);
}







static inline /*__attribute__((always_inline))*/ void *kcalloc(size_t n, size_t size, gfp_t flags)
{
 if (n != 0 && size > (~0UL) / n)
  return ((void *)0);
 return kzalloc(n * size, flags);
}

extern void kfree(const void *);
extern unsigned int ksize(const void *);
extern int slab_is_available(void);





static inline /*__attribute__((always_inline))*/ void *kmem_cache_alloc_node(kmem_cache_t *cachep, gfp_t flags, int node)
{
 return kmem_cache_alloc(cachep, flags);
}
static inline /*__attribute__((always_inline))*/ void *kmalloc_node(size_t size, gfp_t flags, int node)
{
 return kmalloc(size, flags);
}


extern int kmem_cache_reap(int) /*__attribute__((regparm(3)))*/;
extern int kmem_ptr_validate(kmem_cache_t *cachep, void *ptr) /*__attribute__((regparm(3)))*/;
extern kmem_cache_t *vm_area_cachep;
extern kmem_cache_t *names_cachep;
extern kmem_cache_t *files_cachep;
extern kmem_cache_t *filp_cachep;
extern kmem_cache_t *fs_cachep;
extern kmem_cache_t *sighand_cachep;
extern kmem_cache_t *bio_cachep;

extern atomic_t slab_reclaim_pages;
static inline /*__attribute__((always_inline))*/ void *__alloc_percpu(size_t size)
{
 void *ret = kmalloc(size, ((( gfp_t)0x10u) | (( gfp_t)0x40u) | (( gfp_t)0x80u)));
 if (ret)
  (__builtin_constant_p(0) ? (__builtin_constant_p((size)) ? __constant_c_and_count_memset(((ret)),((0x01010101UL*(unsigned char)(0))),((size))) : __constant_c_memset(((ret)),((0x01010101UL*(unsigned char)(0))),((size)))) : (__builtin_constant_p((size)) ? __memset_generic((((ret))),(((0))),(((size)))) : __memset_generic(((ret)),((0)),((size)))));
 return ret;
}
static inline /*__attribute__((always_inline))*/ void free_percpu(const void *ptr)
{
 kfree(ptr);
}
struct rcu_head {
 struct rcu_head *next;
 void (*func)(struct rcu_head *head);
};
struct rcu_ctrlblk {
 long cur;
 long completed;
 int next_pending;

 spinlock_t lock ;
 cpumask_t cpumask;

} ;


static inline /*__attribute__((always_inline))*/ int rcu_batch_before(long a, long b)
{
        return (a - b) < 0;
}


static inline /*__attribute__((always_inline))*/ int rcu_batch_after(long a, long b)
{
        return (a - b) > 0;
}






struct rcu_data {

 long quiescbatch;
 int passed_quiesc;
 int qs_pending;


 long batch;
 struct rcu_head *nxtlist;
 struct rcu_head **nxttail;
 long qlen;
 struct rcu_head *curlist;
 struct rcu_head **curtail;
 struct rcu_head *donelist;
 struct rcu_head **donetail;
 long blimit;
 int cpu;
 struct rcu_head barrier;



};

extern __typeof__(struct rcu_data) per_cpu__rcu_data;
extern __typeof__(struct rcu_data) per_cpu__rcu_bh_data;







static inline /*__attribute__((always_inline))*/ void rcu_qsctr_inc(int cpu)
{
 struct rcu_data *rdp = &(*((void)(cpu), &per_cpu__rcu_data));
 rdp->passed_quiesc = 1;
}
static inline /*__attribute__((always_inline))*/ void rcu_bh_qsctr_inc(int cpu)
{
 struct rcu_data *rdp = &(*((void)(cpu), &per_cpu__rcu_bh_data));
 rdp->passed_quiesc = 1;
}

extern int rcu_pending(int cpu);
extern int rcu_needs_cpu(int cpu);
extern void rcu_init(void);
extern void rcu_check_callbacks(int cpu, int user);
extern void rcu_restart_cpu(int cpu);
extern long rcu_batches_completed(void);
extern long rcu_batches_completed_bh(void);


extern void call_rcu(struct rcu_head *head, void (*func)(struct rcu_head *head)) /*__attribute__((regparm(3)))*/;

extern void call_rcu_bh(struct rcu_head *head, void (*func)(struct rcu_head *head)) /*__attribute__((regparm(3)))*/;

extern void synchronize_rcu(void);
void synchronize_idle(void);
extern void rcu_barrier(void);

enum pid_type
{
 PIDTYPE_PID,
 PIDTYPE_PGID,
 PIDTYPE_SID,
 PIDTYPE_MAX
};
struct pid
{
 atomic_t count;

 int nr;
 struct hlist_node pid_chain;

 struct hlist_head tasks[PIDTYPE_MAX];
 struct rcu_head rcu;
};

struct pid_link
{
 struct hlist_node node;
 struct pid *pid;
};

static inline /*__attribute__((always_inline))*/ struct pid *get_pid(struct pid *pid)
{
 if (pid)
  atomic_inc(&pid->count);
 return pid;
}

extern void put_pid(struct pid *pid) /*__attribute__((regparm(3)))*/;
extern struct task_struct *pid_task(struct pid *pid, enum pid_type) /*__attribute__((regparm(3)))*/;
extern struct task_struct *get_pid_task(struct pid *pid, enum pid_type) /*__attribute__((regparm(3)))*/;






extern int attach_pid(struct task_struct *task, enum pid_type type, int nr) /*__attribute__((regparm(3)))*/;


extern void detach_pid(struct task_struct *task, enum pid_type) /*__attribute__((regparm(3)))*/;





extern struct pid *find_pid(int nr) /*__attribute__((regparm(3)))*/;




extern struct pid *find_get_pid(int nr);

extern struct pid *alloc_pid(void);
extern void free_pid(struct pid *pid) /*__attribute__((regparm(3)))*/;


typedef struct { } seccomp_t;



static inline /*__attribute__((always_inline))*/ int has_secure_computing(struct thread_info *ti)
{
 return 0;
}




struct robust_list {
 struct robust_list *next;
};
struct robust_list_head {



 struct robust_list list;







 long futex_offset;
 struct robust_list *list_op_pending;
};
long do_futex(u32 *uaddr, int op, u32 val, unsigned long timeout,
       u32 *uaddr2, u32 val2, u32 val3);

extern int
handle_futex_death(u32 *uaddr, struct task_struct *curr, int pi);


extern void exit_robust_list(struct task_struct *curr);
extern void exit_pi_state_list(struct task_struct *curr);
struct plist_head {
 struct list_head prio_list;
 struct list_head node_list;



};

struct plist_node {
 int prio;
 struct plist_head plist;
};
static inline /*__attribute__((always_inline))*/ void
plist_head_init(struct plist_head *head, spinlock_t *lock)
{
 INIT_LIST_HEAD(&head->prio_list);
 INIT_LIST_HEAD(&head->node_list);



}







static inline /*__attribute__((always_inline))*/ void plist_node_init(struct plist_node *node, int prio)
{
 node->prio = prio;
 plist_head_init(&node->plist, ((void *)0));
}

extern void plist_add(struct plist_node *node, struct plist_head *head);
extern void plist_del(struct plist_node *node, struct plist_head *head);
static inline /*__attribute__((always_inline))*/ int plist_head_empty(const struct plist_head *head)
{
 return list_empty(&head->node_list);
}






static inline /*__attribute__((always_inline))*/ int plist_node_empty(const struct plist_node *node)
{
 return plist_head_empty(&node->plist);
}
static inline /*__attribute__((always_inline))*/ struct plist_node* plist_first(const struct plist_head *head)
{
 return ({ const typeof( ((struct plist_node *)0)->plist.node_list ) *__mptr = (head->node_list.next); (struct plist_node *)( (char *)__mptr - __builtin_offsetof(/*struct plist_node,*/plist.node_list) );});

}
struct rt_mutex {
 spinlock_t wait_lock;
 struct plist_head wait_list;
 struct task_struct *owner;






};

struct rt_mutex_waiter;
struct hrtimer_sleeper;






 static inline /*__attribute__((always_inline))*/ int rt_mutex_debug_check_no_locks_freed(const void *from,
             unsigned long len)
 {
 return 0;
 }
static inline /*__attribute__((always_inline))*/ int rt_mutex_is_locked(struct rt_mutex *lock)
{
 return lock->owner != ((void *)0);
}

extern void __rt_mutex_init(struct rt_mutex *lock, const char *name);
extern void rt_mutex_destroy(struct rt_mutex *lock);

extern void rt_mutex_lock(struct rt_mutex *lock);
extern int rt_mutex_lock_interruptible(struct rt_mutex *lock,
      int detect_deadlock);
extern int rt_mutex_timed_lock(struct rt_mutex *lock,
     struct hrtimer_sleeper *timeout,
     int detect_deadlock);

extern int rt_mutex_trylock(struct rt_mutex *lock);

extern void rt_mutex_unlock(struct rt_mutex *lock);







struct task_struct;
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
 unsigned long rlim_cur;
 unsigned long rlim_max;
};




int getrusage(struct task_struct *p, int who, struct rusage *ru);







struct tvec_t_base_s;

struct timer_list {
 struct list_head entry;
 unsigned long expires;

 void (*function)(unsigned long);
 unsigned long data;

 struct tvec_t_base_s *base;
};

extern struct tvec_t_base_s boot_tvec_bases;
void /*__attribute__((regparm(3)))*/ init_timer(struct timer_list * timer);

static inline /*__attribute__((always_inline))*/ void setup_timer(struct timer_list * timer,
    void (*function)(unsigned long),
    unsigned long data)
{
 timer->function = function;
 timer->data = data;
 init_timer(timer);
}
static inline /*__attribute__((always_inline))*/ int timer_pending(const struct timer_list * timer)
{
 return timer->entry.next != ((void *)0);
}

extern void add_timer_on(struct timer_list *timer, int cpu);
extern int del_timer(struct timer_list * timer);
extern int __mod_timer(struct timer_list *timer, unsigned long expires);
extern int mod_timer(struct timer_list *timer, unsigned long expires);

extern unsigned long next_timer_interrupt(void);
static inline /*__attribute__((always_inline))*/ void add_timer(struct timer_list *timer)
{
 do { if (timer_pending(timer)) ; } while(0);
 __mod_timer(timer, timer->expires);
}
extern void init_timers(void);
extern void run_local_timers(void);
struct hrtimer;
extern int it_real_fn(struct hrtimer *);
typedef union {
 s64 tv64;
} ktime_t;
static inline /*__attribute__((always_inline))*/ ktime_t ktime_set(const long secs, const unsigned long nsecs)
{




 return (ktime_t) { .tv64 = (s64)secs * 1000000000L + (s64)nsecs };
}
static inline /*__attribute__((always_inline))*/ ktime_t timespec_to_ktime(struct timespec ts)
{
 return ktime_set(ts.tv_sec, ts.tv_nsec);
}


static inline /*__attribute__((always_inline))*/ ktime_t timeval_to_ktime(struct timeval tv)
{
 return ktime_set(tv.tv_sec, tv.tv_usec * 1000L);
}
extern void ktime_get_ts(struct timespec *ts);







enum hrtimer_mode {
 HRTIMER_ABS,
 HRTIMER_REL,
};

enum hrtimer_restart {
 HRTIMER_NORESTART,
 HRTIMER_RESTART,
};



struct hrtimer_base;
struct hrtimer {
 struct rb_node node;
 ktime_t expires;
 int (*function)(struct hrtimer *);
 struct hrtimer_base *base;
};
struct hrtimer_sleeper {
 struct hrtimer timer;
 struct task_struct *task;
};
struct hrtimer_base {
 clockid_t index;
 spinlock_t lock;
 struct rb_root active;
 struct rb_node *first;
 ktime_t resolution;
 ktime_t (*get_time)(void);
 ktime_t (*get_softirq_time)(void);
 struct hrtimer *curr_timer;
 ktime_t softirq_time;
 struct lock_class_key lock_key;
};
extern void hrtimer_init(struct hrtimer *timer, clockid_t which_clock,
    enum hrtimer_mode mode);


extern int hrtimer_start(struct hrtimer *timer, ktime_t tim,
    const enum hrtimer_mode mode);
extern int hrtimer_cancel(struct hrtimer *timer);
extern int hrtimer_try_to_cancel(struct hrtimer *timer);




extern ktime_t hrtimer_get_remaining(const struct hrtimer *timer);
extern int hrtimer_get_res(const clockid_t which_clock, struct timespec *tp);





static inline /*__attribute__((always_inline))*/ int hrtimer_active(const struct hrtimer *timer)
{
 return ((struct rb_node *)((&timer->node)->rb_parent_color & ~3)) != &timer->node;
}


extern unsigned long
hrtimer_forward(struct hrtimer *timer, ktime_t now, ktime_t interval);


extern long hrtimer_nanosleep(struct timespec *rqtp,
         struct timespec *rmtp,
         const enum hrtimer_mode mode,
         const clockid_t clockid);

extern void hrtimer_init_sleeper(struct hrtimer_sleeper *sl,
     struct task_struct *tsk);


extern void hrtimer_run_queues(void);


extern void /*__attribute__ ((__section__ (".init.text")))*/ hrtimers_init(void);



struct exec_domain;
struct futex_pi_state;
extern unsigned long avenrun[];
extern unsigned long total_forks;
extern int nr_threads;
extern int last_pid;
extern __typeof__(unsigned long) per_cpu__process_counts;
extern int nr_processes(void);
extern unsigned long nr_running(void);
extern unsigned long nr_uninterruptible(void);
extern unsigned long nr_active(void);
extern unsigned long nr_iowait(void);
extern unsigned long weighted_cpuload(const int cpu);
extern rwlock_t tasklist_lock;
extern spinlock_t mmlist_lock;

struct task_struct;

extern void sched_init(void);
extern void sched_init_smp(void);
extern void init_idle(struct task_struct *idle, int cpu);

extern cpumask_t nohz_cpu_mask;

extern void show_state(void);
extern void show_regs(struct pt_regs *);






extern void show_stack(struct task_struct *task, unsigned long *sp);

void io_schedule(void);
long io_schedule_timeout(long timeout);

extern void cpu_init (void);
extern void trap_init(void);
extern void update_process_times(int user);
extern void scheduler_tick(void);






static inline /*__attribute__((always_inline))*/ void softlockup_tick(void)
{
}
static inline /*__attribute__((always_inline))*/ void spawn_softlockup_task(void)
{
}
static inline /*__attribute__((always_inline))*/ void touch_softlockup_watchdog(void)
{
}






extern int in_sched_functions(unsigned long addr);


extern signed long schedule_timeout(signed long timeout) /*__attribute__((regparm(3)))*/;
extern signed long schedule_timeout_interruptible(signed long timeout);
extern signed long schedule_timeout_uninterruptible(signed long timeout);
 /*__attribute__((regparm(0)))*/ void schedule(void);

struct namespace;




extern int sysctl_max_map_count;





struct workqueue_struct;

struct work_struct {
 unsigned long pending;
 struct list_head entry;
 void (*func)(void *);
 void *data;
 void *wq_data;
 struct timer_list timer;
};

struct execute_work {
 struct work_struct work;
};
extern struct workqueue_struct *__create_workqueue(const char *name,
          int singlethread);



extern void destroy_workqueue(struct workqueue_struct *wq);

extern int queue_work(struct workqueue_struct *wq, struct work_struct *work) /*__attribute__((regparm(3)))*/;
extern int queue_delayed_work(struct workqueue_struct *wq, struct work_struct *work, unsigned long delay) /*__attribute__((regparm(3)))*/;
extern int queue_delayed_work_on(int cpu, struct workqueue_struct *wq,
 struct work_struct *work, unsigned long delay);
extern void flush_workqueue(struct workqueue_struct *wq) /*__attribute__((regparm(3)))*/;

extern int schedule_work(struct work_struct *work) /*__attribute__((regparm(3)))*/;
extern int schedule_delayed_work(struct work_struct *work, unsigned long delay) /*__attribute__((regparm(3)))*/;

extern int schedule_delayed_work_on(int cpu, struct work_struct *work, unsigned long delay);
extern int schedule_on_each_cpu(void (*func)(void *info), void *info);
extern void flush_scheduled_work(void);
extern int current_is_keventd(void);
extern int keventd_up(void);

extern void init_workqueues(void);
void cancel_rearming_delayed_work(struct work_struct *work);
void cancel_rearming_delayed_workqueue(struct workqueue_struct *,
           struct work_struct *);
int execute_in_process_context(void (*fn)(void *), void *,
          struct execute_work *);






static inline /*__attribute__((always_inline))*/ int cancel_delayed_work(struct work_struct *work)
{
 int ret;

 ret = del_timer(&work->timer);
 if (ret)
  clear_bit(0, &work->pending);
 return ret;
}
typedef unsigned long aio_context_t;

enum {
 IOCB_CMD_PREAD = 0,
 IOCB_CMD_PWRITE = 1,
 IOCB_CMD_FSYNC = 2,
 IOCB_CMD_FDSYNC = 3,




 IOCB_CMD_NOOP = 6,
};


struct io_event {
 __u64 data;
 __u64 obj;
 __s64 res;
 __s64 res2;
};
struct iocb {

 __u64 aio_data;
 __u32 aio_key, aio_reserved1;



 __u16 aio_lio_opcode;
 __s16 aio_reqprio;
 __u32 aio_fildes;

 __u64 aio_buf;
 __u64 aio_nbytes;
 __s64 aio_offset;


 __u64 aio_reserved2;
 __u64 aio_reserved3;
};






struct kioctx;
struct kiocb {
 struct list_head ki_run_list;
 long ki_flags;
 int ki_users;
 unsigned ki_key;

 struct file *ki_filp;
 struct kioctx *ki_ctx;
 int (*ki_cancel)(struct kiocb *, struct io_event *);
 ssize_t (*ki_retry)(struct kiocb *);
 void (*ki_dtor)(struct kiocb *);

 union {
  void *user;
  struct task_struct *tsk;
 } ki_obj;

 __u64 ki_user_data;
 wait_queue_t ki_wait;
 loff_t ki_pos;

 void *private;

 unsigned short ki_opcode;
 size_t ki_nbytes;
 char *ki_buf;
 size_t ki_left;
 long ki_retried;
 long ki_kicked;
 long ki_queued;

 struct list_head ki_list;

};
struct aio_ring {
 unsigned id;
 unsigned nr;
 unsigned head;
 unsigned tail;

 unsigned magic;
 unsigned compat_features;
 unsigned incompat_features;
 unsigned header_length;


 struct io_event io_events[0];
};




struct aio_ring_info {
 unsigned long mmap_base;
 unsigned long mmap_size;

 struct page **ring_pages;
 spinlock_t ring_lock;
 long nr_pages;

 unsigned nr, tail;

 struct page *internal_pages[8];
};

struct kioctx {
 atomic_t users;
 int dead;
 struct mm_struct *mm;


 unsigned long user_id;
 struct kioctx *next;

 wait_queue_head_t wait;

 spinlock_t ctx_lock;

 int reqs_active;
 struct list_head active_reqs;
 struct list_head run_list;


 unsigned max_reqs;

 struct aio_ring_info ring_info;

 struct work_struct wq;
};


extern unsigned aio_max_size;

extern ssize_t wait_on_sync_kiocb(struct kiocb *iocb) /*__attribute__((regparm(3)))*/;
extern int aio_put_req(struct kiocb *iocb) /*__attribute__((regparm(3)))*/;
extern void kick_iocb(struct kiocb *iocb) /*__attribute__((regparm(3)))*/;
extern int aio_complete(struct kiocb *iocb, long res, long res2) /*__attribute__((regparm(3)))*/;
extern void __put_ioctx(struct kioctx *ctx) /*__attribute__((regparm(3)))*/;
struct mm_struct;
extern void exit_aio(struct mm_struct *mm) /*__attribute__((regparm(3)))*/;
extern struct kioctx *lookup_ioctx(unsigned long ctx_id);
extern int io_submit_one(struct kioctx *ctx, struct iocb *user_iocb, struct iocb *iocb) /*__attribute__((regparm(3)))*/;



struct kioctx *lookup_ioctx(unsigned long ctx_id);
int io_submit_one(struct kioctx *ctx, struct iocb *user_iocb, struct iocb *iocb) /*__attribute__((regparm(3)))*/;
static inline /*__attribute__((always_inline))*/ struct kiocb *list_kiocb(struct list_head *h)
{
 return ({ const typeof( ((struct kiocb *)0)->ki_list ) *__mptr = (h); (struct kiocb *)( (char *)__mptr - __builtin_offsetof(/*struct kiocb,*/ki_list) );});
}


extern unsigned long aio_nr;
extern unsigned long aio_max_nr;

extern unsigned long
arch_get_unmapped_area(struct file *, unsigned long, unsigned long,
         unsigned long, unsigned long);
extern unsigned long
arch_get_unmapped_area_topdown(struct file *filp, unsigned long addr,
     unsigned long len, unsigned long pgoff,
     unsigned long flags);
extern void arch_unmap_area(struct mm_struct *, unsigned long);
extern void arch_unmap_area_topdown(struct mm_struct *, unsigned long);
typedef unsigned long mm_counter_t;
struct mm_struct {
 struct vm_area_struct * mmap;
 struct rb_root mm_rb;
 struct vm_area_struct * mmap_cache;
 unsigned long (*get_unmapped_area) (struct file *filp,
    unsigned long addr, unsigned long len,
    unsigned long pgoff, unsigned long flags);
 void (*unmap_area) (struct mm_struct *mm, unsigned long addr);
 unsigned long mmap_base;
 unsigned long task_size;
 unsigned long cached_hole_size;
 unsigned long free_area_cache;
 pgd_t * pgd;
 atomic_t mm_users;
 atomic_t mm_count;
 int map_count;
 struct rw_semaphore mmap_sem;
 spinlock_t page_table_lock;

 struct list_head mmlist;







 mm_counter_t _file_rss;
 mm_counter_t _anon_rss;

 unsigned long hiwater_rss;
 unsigned long hiwater_vm;

 unsigned long total_vm, locked_vm, shared_vm, exec_vm;
 unsigned long stack_vm, reserved_vm, def_flags, nr_ptes;
 unsigned long start_code, end_code, start_data, end_data;
 unsigned long start_brk, brk, start_stack;
 unsigned long arg_start, arg_end, env_start, env_end;

 unsigned long saved_auxv[44];

 unsigned dumpable:2;
 cpumask_t cpu_vm_mask;


 mm_context_t context;


 unsigned long swap_token_time;
 char recent_pagein;


 int core_waiters;
 struct completion *core_startup_done, core_done;


 rwlock_t ioctx_list_lock;
 struct kioctx *ioctx_list;
};

struct sighand_struct {
 atomic_t count;
 struct k_sigaction action[64];
 spinlock_t siglock;
};

struct pacct_struct {
 int ac_flag;
 long ac_exitcode;
 unsigned long ac_mem;
 cputime_t ac_utime, ac_stime;
 unsigned long ac_minflt, ac_majflt;
};
struct signal_struct {
 atomic_t count;
 atomic_t live;

 wait_queue_head_t wait_chldexit;


 struct task_struct *curr_target;


 struct sigpending shared_pending;


 int group_exit_code;





 struct task_struct *group_exit_task;
 int notify_count;


 int group_stop_count;
 unsigned int flags;


 struct list_head posix_timers;


 struct hrtimer real_timer;
 struct task_struct *tsk;
 ktime_t it_real_incr;


 cputime_t it_prof_expires, it_virt_expires;
 cputime_t it_prof_incr, it_virt_incr;


 pid_t pgrp;
 pid_t tty_old_pgrp;
 pid_t session;

 int leader;

 struct tty_struct *tty;







 cputime_t utime, stime, cutime, cstime;
 unsigned long nvcsw, nivcsw, cnvcsw, cnivcsw;
 unsigned long min_flt, maj_flt, cmin_flt, cmaj_flt;







 unsigned long long sched_time;
 struct rlimit rlim[15];

 struct list_head cpu_timers[3];
};
struct user_struct {
 atomic_t __count;
 atomic_t processes;
 atomic_t files;
 atomic_t sigpending;





 unsigned long mq_bytes;
 unsigned long locked_shm;







 struct list_head uidhash_list;
 uid_t uid;
};

extern struct user_struct *find_user(uid_t);

extern struct user_struct root_user;


struct backing_dev_info;
struct reclaim_state;
static inline /*__attribute__((always_inline))*/ int sched_info_on(void)
{






 return 0;

}

enum idle_type
{
 SCHED_IDLE,
 NOT_IDLE,
 NEWLY_IDLE,
 MAX_IDLE_TYPES
};
struct io_context;
void exit_io_context(void);
struct cpuset;



struct group_info {
 int ngroups;
 atomic_t usage;
 gid_t small_block[32];
 int nblocks;
 gid_t *blocks[0];
};
extern struct group_info *groups_alloc(int gidsetsize);
extern void groups_free(struct group_info *group_info);
extern int set_current_groups(struct group_info *group_info);
extern int groups_search(struct group_info *group_info, gid_t grp);







static inline /*__attribute__((always_inline))*/ void prefetch_stack(struct task_struct *t) { }


struct audit_context;
struct mempolicy;
struct pipe_inode_info;

enum sleep_type {
 SLEEP_NORMAL,
 SLEEP_NONINTERACTIVE,
 SLEEP_INTERACTIVE,
 SLEEP_INTERRUPTED,
};

struct prio_array;

struct task_struct {
 volatile long state;
 struct thread_info *thread_info;
 atomic_t usage;
 unsigned long flags;
 unsigned long ptrace;

 int lock_depth;






 int load_weight;
 int prio, static_prio, normal_prio;
 struct list_head run_list;
 struct prio_array *array;

 unsigned short ioprio;
 unsigned int btrace_seq;

 unsigned long sleep_avg;
 unsigned long long timestamp, last_ran;
 unsigned long long sched_time;
 enum sleep_type sleep_type;

 unsigned long policy;
 cpumask_t cpus_allowed;
 unsigned int time_slice, first_time_slice;





 struct list_head tasks;




 struct list_head ptrace_children;
 struct list_head ptrace_list;

 struct mm_struct *mm, *active_mm;


 struct linux_binfmt *binfmt;
 long exit_state;
 int exit_code, exit_signal;
 int pdeath_signal;

 unsigned long personality;
 unsigned did_exec:1;
 pid_t pid;
 pid_t tgid;





 struct task_struct *real_parent;
 struct task_struct *parent;




 struct list_head children;
 struct list_head sibling;
 struct task_struct *group_leader;


 struct pid_link pids[PIDTYPE_MAX];
 struct list_head thread_group;

 struct completion *vfork_done;
 int *set_child_tid;
 int *clear_child_tid;

 unsigned long rt_priority;
 cputime_t utime, stime;
 unsigned long nvcsw, nivcsw;
 struct timespec start_time;

 unsigned long min_flt, maj_flt;

   cputime_t it_prof_expires, it_virt_expires;
 unsigned long long it_sched_expires;
 struct list_head cpu_timers[3];


 uid_t uid,euid,suid,fsuid;
 gid_t gid,egid,sgid,fsgid;
 struct group_info *group_info;
 kernel_cap_t cap_effective, cap_inheritable, cap_permitted;
 unsigned keep_capabilities:1;
 struct user_struct *user;





 int oomkilladj;
 char comm[16];




 int link_count, total_link_count;

 struct sysv_sem sysvsem;

 struct thread_struct thread;

 struct fs_struct *fs;

 struct files_struct *files;

 struct namespace *namespace;

 struct signal_struct *signal;
 struct sighand_struct *sighand;

 sigset_t blocked, real_blocked;
 sigset_t saved_sigmask;
 struct sigpending pending;

 unsigned long sas_ss_sp;
 size_t sas_ss_size;
 int (*notifier)(void *priv);
 void *notifier_data;
 sigset_t *notifier_mask;

 void *security;
 struct audit_context *audit_context;
 seccomp_t seccomp;


    u32 parent_exec_id;
    u32 self_exec_id;

 spinlock_t alloc_lock;


 spinlock_t pi_lock;



 struct plist_head pi_waiters;

 struct rt_mutex_waiter *pi_blocked_on;
 void *journal_info;


 struct reclaim_state *reclaim_state;

 struct backing_dev_info *backing_dev_info;

 struct io_context *io_context;

 unsigned long ptrace_message;
 siginfo_t *last_siginfo;






 wait_queue_t *io_wait;

 u64 rchar, wchar, syscr, syscw;
 struct robust_list_head *robust_list;



 struct list_head pi_state_list;
 struct futex_pi_state *pi_state_cache;

 atomic_t fs_excl;
 struct rcu_head rcu;




 struct pipe_inode_info *splice_pipe;



};

static inline /*__attribute__((always_inline))*/ pid_t process_group(struct task_struct *tsk)
{
 return tsk->signal->pgrp;
}
static inline /*__attribute__((always_inline))*/ int pid_alive(struct task_struct *p)
{
 return p->pids[PIDTYPE_PID].pid != ((void *)0);
}

extern void free_task(struct task_struct *tsk);


extern void __put_task_struct(struct task_struct *t);

static inline /*__attribute__((always_inline))*/ void put_task_struct(struct task_struct *t)
{
 if (atomic_dec_and_test(&t->usage))
  __put_task_struct(t);
}
static inline /*__attribute__((always_inline))*/ int set_cpus_allowed(struct task_struct *p, cpumask_t new_mask)
{
 if (!(__builtin_constant_p((0)) ? constant_test_bit(((0)),((new_mask).bits)) : variable_test_bit(((0)),((new_mask).bits))))
  return -22;
 return 0;
}


extern unsigned long long sched_clock(void);
extern unsigned long long
current_sched_time(const struct task_struct *current_task);
static inline /*__attribute__((always_inline))*/ void idle_task_exit(void) {}


extern void sched_idle_next(void);


extern int rt_mutex_getprio(struct task_struct *p);
extern void rt_mutex_setprio(struct task_struct *p, int prio);
extern void rt_mutex_adjust_pi(struct task_struct *p);
extern void set_user_nice(struct task_struct *p, long nice);
extern int task_prio(const struct task_struct *p);
extern int task_nice(const struct task_struct *p);
extern int can_nice(const struct task_struct *p, const int nice);
extern int task_curr(const struct task_struct *p);
extern int idle_cpu(int cpu);
extern int sched_setscheduler(struct task_struct *, int, struct sched_param *);
extern struct task_struct *idle_task(int cpu);
extern struct task_struct *curr_task(int cpu);
extern void set_curr_task(int cpu, struct task_struct *p);

void yield(void);




extern struct exec_domain default_exec_domain;

union thread_union {
 struct thread_info thread_info;
 unsigned long stack[(8192)/sizeof(long)];
};


static inline /*__attribute__((always_inline))*/ int kstack_end(void *addr)
{



 return !(((unsigned long)addr+sizeof(void*)-1) & ((8192)-sizeof(void*)));
}


extern union thread_union init_thread_union;
extern struct task_struct init_task;

extern struct mm_struct init_mm;


extern struct task_struct *find_task_by_pid_type(int type, int pid);
extern void set_special_pids(pid_t session, pid_t pgrp);
extern void __set_special_pids(pid_t session, pid_t pgrp);


extern struct user_struct * alloc_uid(uid_t);
static inline /*__attribute__((always_inline))*/ struct user_struct *get_uid(struct user_struct *u)
{
 atomic_inc(&u->__count);
 return u;
}
extern void free_uid(struct user_struct *);
extern void switch_uid(struct user_struct *);



extern void do_timer(struct pt_regs *);

extern int wake_up_state(struct task_struct * tsk, unsigned int state) /*__attribute__((regparm(3)))*/;
extern int wake_up_process(struct task_struct * tsk) /*__attribute__((regparm(3)))*/;
extern void wake_up_new_task(struct task_struct * tsk, unsigned long clone_flags) /*__attribute__((regparm(3)))*/;




 static inline /*__attribute__((always_inline))*/ void kick_process(struct task_struct *tsk) { }

extern void sched_fork(struct task_struct * p, int clone_flags) /*__attribute__((regparm(3)))*/;
extern void sched_exit(struct task_struct * p) /*__attribute__((regparm(3)))*/;

extern int in_group_p(gid_t);
extern int in_egroup_p(gid_t);

extern void proc_caches_init(void);
extern void flush_signals(struct task_struct *);
extern void flush_signal_handlers(struct task_struct *, int force_default);
extern int dequeue_signal(struct task_struct *tsk, sigset_t *mask, siginfo_t *info);

static inline /*__attribute__((always_inline))*/ int dequeue_signal_lock(struct task_struct *tsk, sigset_t *mask, siginfo_t *info)
{
 unsigned long flags;
 int ret;

 do { local_irq_save(flags); do { do { } while (0); (void)0; (void)(&tsk->sighand->siglock); } while (0); } while (0);
 ret = dequeue_signal(tsk, mask, info);
 do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&tsk->sighand->siglock); } while (0); } while (0);

 return ret;
}

extern void block_all_signals(int (*notifier)(void *priv), void *priv,
         sigset_t *mask);
extern void unblock_all_signals(void);
extern void release_task(struct task_struct * p);
extern int send_sig_info(int, struct siginfo *, struct task_struct *);
extern int send_group_sig_info(int, struct siginfo *, struct task_struct *);
extern int force_sigsegv(int, struct task_struct *);
extern int force_sig_info(int, struct siginfo *, struct task_struct *);
extern int __kill_pg_info(int sig, struct siginfo *info, pid_t pgrp);
extern int kill_pg_info(int, struct siginfo *, pid_t);
extern int kill_proc_info(int, struct siginfo *, pid_t);
extern int kill_proc_info_as_uid(int, struct siginfo *, pid_t, uid_t, uid_t, u32);
extern void do_notify_parent(struct task_struct *, int);
extern void force_sig(int, struct task_struct *);
extern void force_sig_specific(int, struct task_struct *);
extern int send_sig(int, struct task_struct *, int);
extern void zap_other_threads(struct task_struct *p);
extern int kill_pg(pid_t, int, int);
extern int kill_proc(pid_t, int, int);
extern struct sigqueue *sigqueue_alloc(void);
extern void sigqueue_free(struct sigqueue *);
extern int send_sigqueue(int, struct sigqueue *, struct task_struct *);
extern int send_group_sigqueue(int, struct sigqueue *, struct task_struct *);
extern int do_sigaction(int, struct k_sigaction *, struct k_sigaction *);
extern int do_sigaltstack(const stack_t *, stack_t *, unsigned long);






static inline /*__attribute__((always_inline))*/ int is_si_special(const struct siginfo *info)
{
 return info <= ((struct siginfo *) 2);
}



static inline /*__attribute__((always_inline))*/ int on_sig_stack(unsigned long sp)
{
 return (sp - get_current()->sas_ss_sp < get_current()->sas_ss_size);
}

static inline /*__attribute__((always_inline))*/ int sas_ss_flags(unsigned long sp)
{
 return (get_current()->sas_ss_size == 0 ? 2
  : on_sig_stack(sp) ? 1 : 0);
}




extern struct mm_struct * mm_alloc(void);


extern void __mmdrop(struct mm_struct *) /*__attribute__((regparm(3)))*/;
static inline /*__attribute__((always_inline))*/ void mmdrop(struct mm_struct * mm)
{
 if (atomic_dec_and_test(&mm->mm_count))
  __mmdrop(mm);
}


extern void mmput(struct mm_struct *);

extern struct mm_struct *get_task_mm(struct task_struct *task);

extern void mm_release(struct task_struct *, struct mm_struct *);

extern int copy_thread(int, unsigned long, unsigned long, unsigned long, struct task_struct *, struct pt_regs *);
extern void flush_thread(void);
extern void exit_thread(void);

extern void exit_files(struct task_struct *);
extern void __cleanup_signal(struct signal_struct *);
extern void __cleanup_sighand(struct sighand_struct *);
extern void exit_itimers(struct signal_struct *);

extern void do_group_exit(int);

extern void daemonize(const char *, ...);
extern int allow_signal(int);
extern int disallow_signal(int);
extern struct task_struct *child_reaper;

extern int do_execve(char *, char * *, char * *, struct pt_regs *);
extern long do_fork(unsigned long, unsigned long, struct pt_regs *, unsigned long, int *, int *);
struct task_struct *fork_idle(int);

extern void set_task_comm(struct task_struct *tsk, char *from);
extern void get_task_comm(char *to, struct task_struct *tsk);
static inline /*__attribute__((always_inline))*/ struct task_struct *next_thread(const struct task_struct *p)
{
 return ({ const typeof( ((struct task_struct *)0)->thread_group ) *__mptr = (({ typeof(p->thread_group.next) _________p1 = p->thread_group.next; do { } while(0); (_________p1); })); (struct task_struct *)( (char *)__mptr - __builtin_offsetof(/*struct task_struct,*/thread_group) );});

}

static inline /*__attribute__((always_inline))*/ int thread_group_empty(struct task_struct *p)
{
 return list_empty(&p->thread_group);
}
static inline /*__attribute__((always_inline))*/ void task_lock(struct task_struct *p)
{
 do { do { } while (0); (void)0; (void)(&p->alloc_lock); } while (0);
}

static inline /*__attribute__((always_inline))*/ void task_unlock(struct task_struct *p)
{
 do { do { } while (0); (void)0; (void)(&p->alloc_lock); } while (0);
}

extern struct sighand_struct *lock_task_sighand(struct task_struct *tsk,
       unsigned long *flags);

static inline /*__attribute__((always_inline))*/ void unlock_task_sighand(struct task_struct *tsk,
      unsigned long *flags)
{
 do { local_irq_restore(*flags); do { do { } while (0); (void)0; (void)(&tsk->sighand->siglock); } while (0); } while (0);
}






static inline /*__attribute__((always_inline))*/ void setup_thread_stack(struct task_struct *p, struct task_struct *org)
{
 *(p)->thread_info = *(org)->thread_info;
 (p)->thread_info->task = p;
}

static inline /*__attribute__((always_inline))*/ unsigned long *end_of_stack(struct task_struct *p)
{
 return (unsigned long *)(p->thread_info + 1);
}






static inline /*__attribute__((always_inline))*/ void set_tsk_thread_flag(struct task_struct *tsk, int flag)
{
 set_ti_thread_flag((tsk)->thread_info, flag);
}

static inline /*__attribute__((always_inline))*/ void clear_tsk_thread_flag(struct task_struct *tsk, int flag)
{
 clear_ti_thread_flag((tsk)->thread_info, flag);
}

static inline /*__attribute__((always_inline))*/ int test_and_set_tsk_thread_flag(struct task_struct *tsk, int flag)
{
 return test_and_set_ti_thread_flag((tsk)->thread_info, flag);
}

static inline /*__attribute__((always_inline))*/ int test_and_clear_tsk_thread_flag(struct task_struct *tsk, int flag)
{
 return test_and_clear_ti_thread_flag((tsk)->thread_info, flag);
}

static inline /*__attribute__((always_inline))*/ int test_tsk_thread_flag(struct task_struct *tsk, int flag)
{
 return test_ti_thread_flag((tsk)->thread_info, flag);
}

static inline /*__attribute__((always_inline))*/ void set_tsk_need_resched(struct task_struct *tsk)
{
 set_tsk_thread_flag(tsk,3);
}

static inline /*__attribute__((always_inline))*/ void clear_tsk_need_resched(struct task_struct *tsk)
{
 clear_tsk_thread_flag(tsk,3);
}

static inline /*__attribute__((always_inline))*/ int signal_pending(struct task_struct *p)
{
 return __builtin_expect(!!(test_tsk_thread_flag(p,2)), 0);
}

static inline /*__attribute__((always_inline))*/ int need_resched(void)
{
 return __builtin_expect(!!(test_ti_thread_flag(current_thread_info(), 3)), 0);
}
extern int cond_resched(void);
extern int cond_resched_lock(spinlock_t * lock);
extern int cond_resched_softirq(void);
static inline /*__attribute__((always_inline))*/ int lock_need_resched(spinlock_t *lock)
{
 if (0 || need_resched())
  return 1;
 return 0;
}





extern void recalc_sigpending_tsk(struct task_struct *t) /*__attribute__((regparm(3)))*/;
extern void recalc_sigpending(void);

extern void signal_wake_up(struct task_struct *t, int resume_stopped);
static inline /*__attribute__((always_inline))*/ unsigned int task_cpu(const struct task_struct *p)
{
 return 0;
}

static inline /*__attribute__((always_inline))*/ void set_task_cpu(struct task_struct *p, unsigned int cpu)
{
}




extern void arch_pick_mmap_layout(struct mm_struct *mm);
extern long sched_setaffinity(pid_t pid, cpumask_t new_mask);
extern long sched_getaffinity(pid_t pid, cpumask_t *mask);

struct kobject;
struct module;

struct attribute {
 const char * name;
 struct module * owner;
 mode_t mode;
};

struct attribute_group {
 const char * name;
 struct attribute ** attrs;
};
struct vm_area_struct;

struct bin_attribute {
 struct attribute attr;
 size_t size;
 void *private;
 ssize_t (*read)(struct kobject *, char *, loff_t, size_t);
 ssize_t (*write)(struct kobject *, char *, loff_t, size_t);
 int (*mmap)(struct kobject *, struct bin_attribute *attr,
      struct vm_area_struct *vma);
};

struct sysfs_ops {
 ssize_t (*show)(struct kobject *, struct attribute *,char *);
 ssize_t (*store)(struct kobject *,struct attribute *,const char *, size_t);
};

struct sysfs_dirent {
 atomic_t s_count;
 struct list_head s_sibling;
 struct list_head s_children;
 void * s_element;
 int s_type;
 umode_t s_mode;
 struct dentry * s_dentry;
 struct iattr * s_iattr;
 atomic_t s_event;
};
extern int
sysfs_create_dir(struct kobject *);

extern void
sysfs_remove_dir(struct kobject *);

extern int
sysfs_rename_dir(struct kobject *, const char *new_name);

extern int
sysfs_create_file(struct kobject *, const struct attribute *);

extern int
sysfs_update_file(struct kobject *, const struct attribute *);

extern int
sysfs_chmod_file(struct kobject *kobj, struct attribute *attr, mode_t mode);

extern void
sysfs_remove_file(struct kobject *, const struct attribute *);

extern int
sysfs_create_link(struct kobject * kobj, struct kobject * target, const char * name);

extern void
sysfs_remove_link(struct kobject *, const char * name);

int sysfs_create_bin_file(struct kobject * kobj, struct bin_attribute * attr);
int sysfs_remove_bin_file(struct kobject * kobj, struct bin_attribute * attr);

int sysfs_create_group(struct kobject *, const struct attribute_group *);
void sysfs_remove_group(struct kobject *, const struct attribute_group *);
void sysfs_notify(struct kobject * k, char *dir, char *attr);


struct kref {
 atomic_t refcount;
};

void kref_init(struct kref *kref);
void kref_get(struct kref *kref);
int kref_put(struct kref *kref, void (*release) (struct kref *kref));
extern char uevent_helper[];


extern u64 uevent_seqnum;


typedef int kobject_action_t;
enum kobject_action {
 KOBJ_ADD = ( kobject_action_t) 0x01,
 KOBJ_REMOVE = ( kobject_action_t) 0x02,
 KOBJ_CHANGE = ( kobject_action_t) 0x03,
 KOBJ_MOUNT = ( kobject_action_t) 0x04,
 KOBJ_UMOUNT = ( kobject_action_t) 0x05,
 KOBJ_OFFLINE = ( kobject_action_t) 0x06,
 KOBJ_ONLINE = ( kobject_action_t) 0x07,
};

struct kobject {
 const char * k_name;
 char name[20];
 struct kref kref;
 struct list_head entry;
 struct kobject * parent;
 struct kset * kset;
 struct kobj_type * ktype;
 struct dentry * dentry;
 wait_queue_head_t poll;
};

extern int kobject_set_name(struct kobject *, const char *, ...)
 __attribute__((format(printf,2,3)));

static inline /*__attribute__((always_inline))*/ const char * kobject_name(const struct kobject * kobj)
{
 return kobj->k_name;
}

extern void kobject_init(struct kobject *);
extern void kobject_cleanup(struct kobject *);

extern int kobject_add(struct kobject *);
extern void kobject_del(struct kobject *);

extern int kobject_rename(struct kobject *, const char *new_name);

extern int kobject_register(struct kobject *);
extern void kobject_unregister(struct kobject *);

extern struct kobject * kobject_get(struct kobject *);
extern void kobject_put(struct kobject *);

extern struct kobject *kobject_add_dir(struct kobject *, const char *);

extern char * kobject_get_path(struct kobject *, gfp_t);

struct kobj_type {
 void (*release)(struct kobject *);
 struct sysfs_ops * sysfs_ops;
 struct attribute ** default_attrs;
};
struct kset_uevent_ops {
 int (*filter)(struct kset *kset, struct kobject *kobj);
 const char *(*name)(struct kset *kset, struct kobject *kobj);
 int (*uevent)(struct kset *kset, struct kobject *kobj, char **envp,
   int num_envp, char *buffer, int buffer_size);
};

struct kset {
 struct subsystem * subsys;
 struct kobj_type * ktype;
 struct list_head list;
 spinlock_t list_lock;
 struct kobject kobj;
 struct kset_uevent_ops * uevent_ops;
};


extern void kset_init(struct kset * k);
extern int kset_add(struct kset * k);
extern int kset_register(struct kset * k);
extern void kset_unregister(struct kset * k);

static inline /*__attribute__((always_inline))*/ struct kset * to_kset(struct kobject * kobj)
{
 return kobj ? ({ const typeof( ((struct kset *)0)->kobj ) *__mptr = (kobj); (struct kset *)( (char *)__mptr - __builtin_offsetof(/*struct kset,*/kobj) );}) : ((void *)0);
}

static inline /*__attribute__((always_inline))*/ struct kset * kset_get(struct kset * k)
{
 return k ? to_kset(kobject_get(&k->kobj)) : ((void *)0);
}

static inline /*__attribute__((always_inline))*/ void kset_put(struct kset * k)
{
 kobject_put(&k->kobj);
}

static inline /*__attribute__((always_inline))*/ struct kobj_type * get_ktype(struct kobject * k)
{
 if (k->kset && k->kset->ktype)
  return k->kset->ktype;
 else
  return k->ktype;
}

extern struct kobject * kset_find_obj(struct kset *, const char *);
struct subsystem {
 struct kset kset;
 struct rw_semaphore rwsem;
};
extern struct subsystem kernel_subsys;

extern struct subsystem hypervisor_subsys;
extern void subsystem_init(struct subsystem *);
extern int subsystem_register(struct subsystem *);
extern void subsystem_unregister(struct subsystem *);

static inline /*__attribute__((always_inline))*/ struct subsystem * subsys_get(struct subsystem * s)
{
 return s ? ({ const typeof( ((struct subsystem *)0)->kset ) *__mptr = (kset_get(&s->kset)); (struct subsystem *)( (char *)__mptr - __builtin_offsetof(/*struct subsystem,*/kset) );}) : ((void *)0);
}

static inline /*__attribute__((always_inline))*/ void subsys_put(struct subsystem * s)
{
 kset_put(&s->kset);
}

struct subsys_attribute {
 struct attribute attr;
 ssize_t (*show)(struct subsystem *, char *);
 ssize_t (*store)(struct subsystem *, const char *, size_t);
};

extern int subsys_create_file(struct subsystem * , struct subsys_attribute *);


void kobject_uevent(struct kobject *kobj, enum kobject_action action);

int add_uevent_var(char **envp, int num_envp, int *cur_index,
   char *buffer, int buffer_size, int *cur_len,
   const char *format, ...)
 __attribute__((format (printf, 7, 8)));
typedef int pm_request_t;
typedef int pm_dev_t;
enum
{
 PM_SYS_UNKNOWN = 0x00000000,
 PM_SYS_KBC = 0x41d00303,
 PM_SYS_COM = 0x41d00500,
 PM_SYS_IRDA = 0x41d00510,
 PM_SYS_FDC = 0x41d00700,
 PM_SYS_VGA = 0x41d00900,
 PM_SYS_PCMCIA = 0x41d00e00,
};
struct pm_dev;

typedef int (*pm_callback)(struct pm_dev *dev, pm_request_t rqst, void *data);




struct pm_dev
{
 pm_dev_t type;
 unsigned long id;
 pm_callback callback;
 void *data;

 unsigned long flags;
 unsigned long state;
 unsigned long prev_state;

 struct list_head entry;
};







extern void (*pm_idle)(void);
extern void (*pm_power_off)(void);

typedef int suspend_state_t;







typedef int suspend_disk_method_t;







struct pm_ops {
 suspend_disk_method_t pm_disk_mode;
 int (*valid)(suspend_state_t state);
 int (*prepare)(suspend_state_t state);
 int (*enter)(suspend_state_t state);
 int (*finish)(suspend_state_t state);
};

extern void pm_set_ops(struct pm_ops *);
extern struct pm_ops *pm_ops;
extern int pm_suspend(suspend_state_t state);






struct device;

typedef struct pm_message {
 int event;
} pm_message_t;
struct dev_pm_info {
 pm_message_t power_state;
 unsigned can_wakeup:1;

 unsigned should_wakeup:1;
 pm_message_t prev_state;
 void * saved_state;
 struct device * pm_parent;
 struct list_head entry;

};

extern void device_pm_set_parent(struct device * dev, struct device * parent);

extern int device_power_down(pm_message_t state);
extern void device_power_up(void);
extern void device_resume(void);


extern suspend_disk_method_t pm_disk_mode;

extern int device_suspend(pm_message_t state);






extern int dpm_runtime_suspend(struct device *, pm_message_t);
extern void dpm_runtime_resume(struct device *);
extern void __suspend_report_result(const char *function, void *fn, int ret);


struct sys_device;

struct sysdev_class {
 struct list_head drivers;


 int (*shutdown)(struct sys_device *);
 int (*suspend)(struct sys_device *, pm_message_t state);
 int (*resume)(struct sys_device *);
 struct kset kset;
};

struct sysdev_class_attribute {
 struct attribute attr;
 ssize_t (*show)(struct sysdev_class *, char *);
 ssize_t (*store)(struct sysdev_class *, const char *, size_t);
};
extern int sysdev_class_register(struct sysdev_class *);
extern void sysdev_class_unregister(struct sysdev_class *);

extern int sysdev_class_create_file(struct sysdev_class *,
 struct sysdev_class_attribute *);
extern void sysdev_class_remove_file(struct sysdev_class *,
 struct sysdev_class_attribute *);




struct sysdev_driver {
 struct list_head entry;
 int (*add)(struct sys_device *);
 int (*remove)(struct sys_device *);
 int (*shutdown)(struct sys_device *);
 int (*suspend)(struct sys_device *, pm_message_t state);
 int (*resume)(struct sys_device *);
};


extern int sysdev_driver_register(struct sysdev_class *, struct sysdev_driver *);
extern void sysdev_driver_unregister(struct sysdev_class *, struct sysdev_driver *);







struct sys_device {
 u32 id;
 struct sysdev_class * cls;
 struct kobject kobj;
};

extern int sysdev_register(struct sys_device *);
extern void sysdev_unregister(struct sys_device *);


struct sysdev_attribute {
 struct attribute attr;
 ssize_t (*show)(struct sys_device *, char *);
 ssize_t (*store)(struct sys_device *, const char *, size_t);
};
extern int sysdev_create_file(struct sys_device *, struct sysdev_attribute *);
extern void sysdev_remove_file(struct sys_device *, struct sysdev_attribute *);
extern int sched_mc_power_savings, sched_smt_power_savings;
extern struct sysdev_attribute attr_sched_mc_power_savings, attr_sched_smt_power_savings;
extern int sched_create_sysfs_power_savings_entries(struct sysdev_class *cls);

extern void normalize_rt_tasks(void);





static inline /*__attribute__((always_inline))*/ int frozen(struct task_struct *p)
{
 return p->flags & 0x00010000;
}




static inline /*__attribute__((always_inline))*/ int freezing(struct task_struct *p)
{
 return p->flags & 0x00004000;
}





static inline /*__attribute__((always_inline))*/ void freeze(struct task_struct *p)
{
 p->flags |= 0x00004000;
}




static inline /*__attribute__((always_inline))*/ void do_not_freeze(struct task_struct *p)
{
 p->flags &= ~0x00004000;
}




static inline /*__attribute__((always_inline))*/ int thaw_process(struct task_struct *p)
{
 if (frozen(p)) {
  p->flags &= ~0x00010000;
  wake_up_process(p);
  return 1;
 }
 return 0;
}




static inline /*__attribute__((always_inline))*/ void frozen_process(struct task_struct *p)
{
 p->flags = (p->flags & ~0x00004000) | 0x00010000;
}

extern void refrigerator(void);
extern int freeze_processes(void);
extern void thaw_processes(void);

static inline /*__attribute__((always_inline))*/ int try_to_freeze(void)
{
 if (freezing(get_current())) {
  refrigerator();
  return 1;
 } else
  return 0;
}










struct __old_kernel_stat {
 unsigned short st_dev;
 unsigned short st_ino;
 unsigned short st_mode;
 unsigned short st_nlink;
 unsigned short st_uid;
 unsigned short st_gid;
 unsigned short st_rdev;
 unsigned long st_size;
 unsigned long st_atime;
 unsigned long st_mtime;
 unsigned long st_ctime;
};

struct stat {
 unsigned long st_dev;
 unsigned long st_ino;
 unsigned short st_mode;
 unsigned short st_nlink;
 unsigned short st_uid;
 unsigned short st_gid;
 unsigned long st_rdev;
 unsigned long st_size;
 unsigned long st_blksize;
 unsigned long st_blocks;
 unsigned long st_atime;
 unsigned long st_atime_nsec;
 unsigned long st_mtime;
 unsigned long st_mtime_nsec;
 unsigned long st_ctime;
 unsigned long st_ctime_nsec;
 unsigned long __unused4;
 unsigned long __unused5;
};




struct stat64 {
 unsigned long long st_dev;
 unsigned char __pad0[4];


 unsigned long __st_ino;

 unsigned int st_mode;
 unsigned int st_nlink;

 unsigned long st_uid;
 unsigned long st_gid;

 unsigned long long st_rdev;
 unsigned char __pad3[4];

 long long st_size;
 unsigned long st_blksize;

 unsigned long long st_blocks;

 unsigned long st_atime;
 unsigned long st_atime_nsec;

 unsigned long st_mtime;
 unsigned int st_mtime_nsec;

 unsigned long st_ctime;
 unsigned long st_ctime_nsec;

 unsigned long long st_ino;
};
struct kstat {
 unsigned long ino;
 dev_t dev;
 umode_t mode;
 unsigned int nlink;
 uid_t uid;
 gid_t gid;
 dev_t rdev;
 loff_t size;
 struct timespec atime;
 struct timespec mtime;
 struct timespec ctime;
 unsigned long blksize;
 unsigned long long blocks;
};


static inline /*__attribute__((always_inline))*/ int request_module(const char * name, ...) { return -38; }




struct key;
extern int call_usermodehelper_keys(char *path, char *argv[], char *envp[],
        struct key *session_keyring, int wait);

static inline /*__attribute__((always_inline))*/ int
call_usermodehelper(char *path, char **argv, char **envp, int wait)
{
 return call_usermodehelper_keys(path, argv, envp, ((void *)0), wait);
}

extern void usermodehelper_init(void);





struct user_i387_struct {
 long cwd;
 long swd;
 long twd;
 long fip;
 long fcs;
 long foo;
 long fos;
 long st_space[20];
};

struct user_fxsr_struct {
 unsigned short cwd;
 unsigned short swd;
 unsigned short twd;
 unsigned short fop;
 long fip;
 long fcs;
 long foo;
 long fos;
 long mxcsr;
 long reserved;
 long st_space[32];
 long xmm_space[32];
 long padding[56];
};







struct user_regs_struct {
 long ebx, ecx, edx, esi, edi, ebp, eax;
 unsigned short ds, __ds, es, __es;
 unsigned short fs, __fs, gs, __gs;
 long orig_eax, eip;
 unsigned short cs, __cs;
 long eflags, esp;
 unsigned short ss, __ss;
};




struct user{


  struct user_regs_struct regs;

  int u_fpvalid;

  struct user_i387_struct i387;

  unsigned long int u_tsize;
  unsigned long int u_dsize;
  unsigned long int u_ssize;
  unsigned long start_code;
  unsigned long start_stack;



  long int signal;
  int reserved;
  struct user_pt_regs * u_ar0;

  struct user_i387_struct* u_fpstate;
  unsigned long magic;
  char u_comm[32];
  int u_debugreg[8];
};







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

extern struct rw_semaphore uts_sem;
typedef unsigned long elf_greg_t;


typedef elf_greg_t elf_gregset_t[(sizeof (struct user_regs_struct) / sizeof(elf_greg_t))];

typedef struct user_i387_struct elf_fpregset_t;
typedef struct user_fxsr_struct elf_fpxregset_t;



struct user_desc {
 unsigned int entry_number;
 unsigned long base_addr;
 unsigned int limit;
 unsigned int seg_32bit:1;
 unsigned int contents:2;
 unsigned int read_exec_only:1;
 unsigned int limit_in_pages:1;
 unsigned int seg_not_present:1;
 unsigned int useable:1;
};
extern struct desc_struct cpu_gdt_table[32];

extern __typeof__(unsigned char) per_cpu__cpu_16bit_stack[1024];

struct Xgt_desc_struct {
 unsigned short size;
 unsigned long address __attribute__((packed));
 unsigned short pad;
} __attribute__ ((packed));

extern struct Xgt_desc_struct idt_descr;
extern __typeof__(struct Xgt_desc_struct) per_cpu__cpu_gdt_descr;


static inline /*__attribute__((always_inline))*/ struct desc_struct *get_cpu_gdt_table(unsigned int cpu)
{
 return (struct desc_struct *)(*((void)(cpu), &per_cpu__cpu_gdt_descr)).address;
}
extern struct desc_struct default_ldt[];
extern void set_intr_gate(unsigned int irq, void * addr);
static inline /*__attribute__((always_inline))*/ void __set_tss_desc(unsigned int cpu, unsigned int entry, void *addr)
{
 __asm__ __volatile__ ("movw %w3,0(%2)\n\t" "movw %w1,2(%2)\n\t" "rorl $16,%1\n\t" "movb %b1,4(%2)\n\t" "movb %4,5(%2)\n\t" "movb $0,6(%2)\n\t" "movb %h1,7(%2)\n\t" "rorl $16,%1" : "=m"(*(&get_cpu_gdt_table(cpu)[entry])) : "q" ((int)addr), "r"(&get_cpu_gdt_table(cpu)[entry]), "ir"(__builtin_offsetof(/*struct tss_struct,*/__cacheline_filler) - 1), "i"(0x89));

}



static inline /*__attribute__((always_inline))*/ void set_ldt_desc(unsigned int cpu, void *addr, unsigned int size)
{
 __asm__ __volatile__ ("movw %w3,0(%2)\n\t" "movw %w1,2(%2)\n\t" "rorl $16,%1\n\t" "movb %b1,4(%2)\n\t" "movb %4,5(%2)\n\t" "movb $0,6(%2)\n\t" "movb %h1,7(%2)\n\t" "rorl $16,%1" : "=m"(*(&get_cpu_gdt_table(cpu)[(12 + 5)])) : "q" ((int)addr), "r"(&get_cpu_gdt_table(cpu)[(12 + 5)]), "ir"(((size << 3)-1)), "i"(0x82));
}
static inline /*__attribute__((always_inline))*/ void write_ldt_entry(void *ldt, int entry, __u32 entry_a, __u32 entry_b)
{
 __u32 *lp = (__u32 *)((char *)ldt + entry*8);
 *lp = entry_a;
 *(lp+1) = entry_b;
}





static inline /*__attribute__((always_inline))*/ void load_TLS(struct thread_struct *t, unsigned int cpu)
{

 get_cpu_gdt_table(cpu)[6 + 0] = t->tls_array[0]; get_cpu_gdt_table(cpu)[6 + 1] = t->tls_array[1]; get_cpu_gdt_table(cpu)[6 + 2] = t->tls_array[2];

}

static inline /*__attribute__((always_inline))*/ void clear_LDT(void)
{
 int cpu = ({ do { } while (0); 0; });

 set_ldt_desc(cpu, &default_ldt[0], 5);
 __asm__ __volatile__("lldt %w0"::"q" ((12 + 5)*8));
 do { } while (0);
}




static inline /*__attribute__((always_inline))*/ void load_LDT_nolock(mm_context_t *pc, int cpu)
{
 void *segments = pc->ldt;
 int count = pc->size;

 if (__builtin_expect(!!(!count), 1)) {
  segments = &default_ldt[0];
  count = 5;
 }

 set_ldt_desc(cpu, segments, count);
 __asm__ __volatile__("lldt %w0"::"q" ((12 + 5)*8));
}

static inline /*__attribute__((always_inline))*/ void load_LDT(mm_context_t *pc)
{
 int cpu = ({ do { } while (0); 0; });
 load_LDT_nolock(pc, cpu);
 do { } while (0);
}

static inline /*__attribute__((always_inline))*/ unsigned long get_desc_base(unsigned long *desc)
{
 unsigned long base;
 base = ((desc[0] >> 16) & 0x0000ffff) |
  ((desc[1] << 16) & 0x00ff0000) |
  (desc[1] & 0xff000000);
 return base;
}
struct task_struct;

extern int dump_task_regs (struct task_struct *, elf_gregset_t *);
extern int dump_task_fpu (struct task_struct *, elf_fpregset_t *);
extern int dump_task_extended_fpu (struct task_struct *, struct user_fxsr_struct *);
extern void __kernel_vsyscall;




struct linux_binprm;
extern int arch_setup_additional_pages(struct linux_binprm *bprm,
                                       int executable_stack);

extern unsigned int vdso_enabled;
typedef __u32 Elf32_Addr;
typedef __u16 Elf32_Half;
typedef __u32 Elf32_Off;
typedef __s32 Elf32_Sword;
typedef __u32 Elf32_Word;


typedef __u64 Elf64_Addr;
typedef __u16 Elf64_Half;
typedef __s16 Elf64_SHalf;
typedef __u64 Elf64_Off;
typedef __s32 Elf64_Sword;
typedef __u32 Elf64_Word;
typedef __u64 Elf64_Xword;
typedef __s64 Elf64_Sxword;
typedef struct dynamic{
  Elf32_Sword d_tag;
  union{
    Elf32_Sword d_val;
    Elf32_Addr d_ptr;
  } d_un;
} Elf32_Dyn;

typedef struct {
  Elf64_Sxword d_tag;
  union {
    Elf64_Xword d_val;
    Elf64_Addr d_ptr;
  } d_un;
} Elf64_Dyn;
typedef struct elf32_rel {
  Elf32_Addr r_offset;
  Elf32_Word r_info;
} Elf32_Rel;

typedef struct elf64_rel {
  Elf64_Addr r_offset;
  Elf64_Xword r_info;
} Elf64_Rel;

typedef struct elf32_rela{
  Elf32_Addr r_offset;
  Elf32_Word r_info;
  Elf32_Sword r_addend;
} Elf32_Rela;

typedef struct elf64_rela {
  Elf64_Addr r_offset;
  Elf64_Xword r_info;
  Elf64_Sxword r_addend;
} Elf64_Rela;

typedef struct elf32_sym{
  Elf32_Word st_name;
  Elf32_Addr st_value;
  Elf32_Word st_size;
  unsigned char st_info;
  unsigned char st_other;
  Elf32_Half st_shndx;
} Elf32_Sym;

typedef struct elf64_sym {
  Elf64_Word st_name;
  unsigned char st_info;
  unsigned char st_other;
  Elf64_Half st_shndx;
  Elf64_Addr st_value;
  Elf64_Xword st_size;
} Elf64_Sym;




typedef struct elf32_hdr{
  unsigned char e_ident[16];
  Elf32_Half e_type;
  Elf32_Half e_machine;
  Elf32_Word e_version;
  Elf32_Addr e_entry;
  Elf32_Off e_phoff;
  Elf32_Off e_shoff;
  Elf32_Word e_flags;
  Elf32_Half e_ehsize;
  Elf32_Half e_phentsize;
  Elf32_Half e_phnum;
  Elf32_Half e_shentsize;
  Elf32_Half e_shnum;
  Elf32_Half e_shstrndx;
} Elf32_Ehdr;

typedef struct elf64_hdr {
  unsigned char e_ident[16];
  Elf64_Half e_type;
  Elf64_Half e_machine;
  Elf64_Word e_version;
  Elf64_Addr e_entry;
  Elf64_Off e_phoff;
  Elf64_Off e_shoff;
  Elf64_Word e_flags;
  Elf64_Half e_ehsize;
  Elf64_Half e_phentsize;
  Elf64_Half e_phnum;
  Elf64_Half e_shentsize;
  Elf64_Half e_shnum;
  Elf64_Half e_shstrndx;
} Elf64_Ehdr;







typedef struct elf32_phdr{
  Elf32_Word p_type;
  Elf32_Off p_offset;
  Elf32_Addr p_vaddr;
  Elf32_Addr p_paddr;
  Elf32_Word p_filesz;
  Elf32_Word p_memsz;
  Elf32_Word p_flags;
  Elf32_Word p_align;
} Elf32_Phdr;

typedef struct elf64_phdr {
  Elf64_Word p_type;
  Elf64_Word p_flags;
  Elf64_Off p_offset;
  Elf64_Addr p_vaddr;
  Elf64_Addr p_paddr;
  Elf64_Xword p_filesz;
  Elf64_Xword p_memsz;
  Elf64_Xword p_align;
} Elf64_Phdr;
typedef struct {
  Elf32_Word sh_name;
  Elf32_Word sh_type;
  Elf32_Word sh_flags;
  Elf32_Addr sh_addr;
  Elf32_Off sh_offset;
  Elf32_Word sh_size;
  Elf32_Word sh_link;
  Elf32_Word sh_info;
  Elf32_Word sh_addralign;
  Elf32_Word sh_entsize;
} Elf32_Shdr;

typedef struct elf64_shdr {
  Elf64_Word sh_name;
  Elf64_Word sh_type;
  Elf64_Xword sh_flags;
  Elf64_Addr sh_addr;
  Elf64_Off sh_offset;
  Elf64_Xword sh_size;
  Elf64_Word sh_link;
  Elf64_Word sh_info;
  Elf64_Xword sh_addralign;
  Elf64_Xword sh_entsize;
} Elf64_Shdr;
typedef struct elf32_note {
  Elf32_Word n_namesz;
  Elf32_Word n_descsz;
  Elf32_Word n_type;
} Elf32_Nhdr;


typedef struct elf64_note {
  Elf64_Word n_namesz;
  Elf64_Word n_descsz;
  Elf64_Word n_type;
} Elf64_Nhdr;



extern Elf32_Dyn _DYNAMIC [];


struct kernel_param;


typedef int (*param_set_fn)(const char *val, struct kernel_param *kp);

typedef int (*param_get_fn)(char *buffer, struct kernel_param *kp);

struct kernel_param {
 const char *name;
 unsigned int perm;
 param_set_fn set;
 param_get_fn get;
 void *arg;
};


struct kparam_string {
 unsigned int maxlen;
 char *string;
};


struct kparam_array
{
 unsigned int max;
 unsigned int *num;
 param_set_fn set;
 param_get_fn get;
 unsigned int elemsize;
 void *elem;
};
extern int parse_args(const char *name,
        char *args,
        struct kernel_param *params,
        unsigned num,
        int (*unknown)(char *param, char *val));







extern int param_set_byte(const char *val, struct kernel_param *kp);
extern int param_get_byte(char *buffer, struct kernel_param *kp);


extern int param_set_short(const char *val, struct kernel_param *kp);
extern int param_get_short(char *buffer, struct kernel_param *kp);


extern int param_set_ushort(const char *val, struct kernel_param *kp);
extern int param_get_ushort(char *buffer, struct kernel_param *kp);


extern int param_set_int(const char *val, struct kernel_param *kp);
extern int param_get_int(char *buffer, struct kernel_param *kp);


extern int param_set_uint(const char *val, struct kernel_param *kp);
extern int param_get_uint(char *buffer, struct kernel_param *kp);


extern int param_set_long(const char *val, struct kernel_param *kp);
extern int param_get_long(char *buffer, struct kernel_param *kp);


extern int param_set_ulong(const char *val, struct kernel_param *kp);
extern int param_get_ulong(char *buffer, struct kernel_param *kp);


extern int param_set_charp(const char *val, struct kernel_param *kp);
extern int param_get_charp(char *buffer, struct kernel_param *kp);


extern int param_set_bool(const char *val, struct kernel_param *kp);
extern int param_get_bool(char *buffer, struct kernel_param *kp);


extern int param_set_invbool(const char *val, struct kernel_param *kp);
extern int param_get_invbool(char *buffer, struct kernel_param *kp);
extern int param_array_set(const char *val, struct kernel_param *kp);
extern int param_array_get(char *buffer, struct kernel_param *kp);

extern int param_set_copystring(const char *val, struct kernel_param *kp);
extern int param_get_string(char *buffer, struct kernel_param *kp);



struct module;

extern int module_param_sysfs_setup(struct module *mod,
        struct kernel_param *kparam,
        unsigned int num_params);

extern void module_param_sysfs_remove(struct module *mod);





typedef struct
{
 volatile long counter;
} local_t;






static __inline__ /*__attribute__((always_inline))*/ void local_inc(local_t *v)
{
 __asm__ __volatile__(
  "incl %0"
  :"+m" (v->counter));
}

static __inline__ /*__attribute__((always_inline))*/ void local_dec(local_t *v)
{
 __asm__ __volatile__(
  "decl %0"
  :"+m" (v->counter));
}

static __inline__ /*__attribute__((always_inline))*/ void local_add(long i, local_t *v)
{
 __asm__ __volatile__(
  "addl %1,%0"
  :"+m" (v->counter)
  :"ir" (i));
}

static __inline__ /*__attribute__((always_inline))*/ void local_sub(long i, local_t *v)
{
 __asm__ __volatile__(
  "subl %1,%0"
  :"+m" (v->counter)
  :"ir" (i));
}





struct mod_arch_specific
{
};
struct kernel_symbol
{
 unsigned long value;
 const char *name;
};

struct modversion_info
{
 unsigned long crc;
 char name[(64 - sizeof(unsigned long))];
};

struct module;

struct module_attribute {
        struct attribute attr;
        ssize_t (*show)(struct module_attribute *, struct module *, char *);
        ssize_t (*store)(struct module_attribute *, struct module *,
    const char *, size_t count);
 void (*setup)(struct module *, const char *);
 int (*test)(struct module *);
 void (*free)(struct module *);
};

struct module_kobject
{
 struct kobject kobj;
 struct module *mod;
};


extern int init_module(void);
extern void cleanup_module(void);


struct exception_table_entry;

const struct exception_table_entry *
search_extable(const struct exception_table_entry *first,
        const struct exception_table_entry *last,
        unsigned long value);
void sort_extable(struct exception_table_entry *start,
    struct exception_table_entry *finish);
void sort_main_extable(void);

extern struct subsystem module_subsys;
const struct exception_table_entry *search_exception_tables(unsigned long add);

struct notifier_block;




void *__symbol_get(const char *symbol);
void *__symbol_get_gpl(const char *symbol);
struct module_ref
{
 local_t count;
} __attribute__((__aligned__((1 << (6)))));

enum module_state
{
 MODULE_STATE_LIVE,
 MODULE_STATE_COMING,
 MODULE_STATE_GOING,
};



struct module_sect_attr
{
 struct module_attribute mattr;
 char name[32];
 unsigned long address;
};

struct module_sect_attrs
{
 struct attribute_group grp;
 struct module_sect_attr attrs[0];
};

struct module_param_attrs;

struct module
{
 enum module_state state;


 struct list_head list;


 char name[(64 - sizeof(unsigned long))];


 struct module_kobject mkobj;
 struct module_param_attrs *param_attrs;
 struct module_attribute *modinfo_attrs;
 const char *version;
 const char *srcversion;


 const struct kernel_symbol *syms;
 unsigned int num_syms;
 const unsigned long *crcs;


 const struct kernel_symbol *gpl_syms;
 unsigned int num_gpl_syms;
 const unsigned long *gpl_crcs;


 const struct kernel_symbol *unused_syms;
 unsigned int num_unused_syms;
 const unsigned long *unused_crcs;

 const struct kernel_symbol *unused_gpl_syms;
 unsigned int num_unused_gpl_syms;
 const unsigned long *unused_gpl_crcs;


 const struct kernel_symbol *gpl_future_syms;
 unsigned int num_gpl_future_syms;
 const unsigned long *gpl_future_crcs;


 unsigned int num_exentries;
 const struct exception_table_entry *extable;


 int (*init)(void);


 void *module_init;


 void *module_core;


 unsigned long init_size, core_size;


 unsigned long init_text_size, core_text_size;


 void *unwind_info;


 struct mod_arch_specific arch;


 int unsafe;


 int license_gplok;



 struct module_ref ref[1];


 struct list_head modules_which_use_me;


 struct task_struct *waiter;


 void (*exit)(void);




 Elf32_Sym *symtab;
 unsigned long num_symtab;
 char *strtab;


 struct module_sect_attrs *sect_attrs;



 void *percpu;



 char *args;
};




static inline /*__attribute__((always_inline))*/ int module_is_live(struct module *mod)
{
 return mod->state != MODULE_STATE_GOING;
}


struct module *module_text_address(unsigned long addr);
struct module *__module_text_address(unsigned long addr);
int is_module_address(unsigned long addr);



struct module *module_get_kallsym(unsigned int symnum, unsigned long *value,
    char *type, char *name, size_t namelen);


unsigned long module_kallsyms_lookup_name(const char *name);

int is_exported(const char *name, const struct module *mod);

extern void __module_put_and_exit(struct module *mod, long code)
 __attribute__((noreturn));



unsigned int module_refcount(struct module *mod);
void __symbol_put(const char *symbol);

void symbol_put_addr(void *addr);



static inline /*__attribute__((always_inline))*/ void __module_get(struct module *module)
{
 if (module) {
  do { if (module_refcount(module) == 0) ; } while(0);
  local_inc(&module->ref[({ do { } while (0); 0; })].count);
  do { } while (0);
 }
}

static inline /*__attribute__((always_inline))*/ int try_module_get(struct module *module)
{
 int ret = 1;

 if (module) {
  unsigned int cpu = ({ do { } while (0); 0; });
  if (__builtin_expect(!!(module_is_live(module)), 1))
   local_inc(&module->ref[cpu].count);
  else
   ret = 0;
  do { } while (0);
 }
 return ret;
}

static inline /*__attribute__((always_inline))*/ void module_put(struct module *module)
{
 if (module) {
  unsigned int cpu = ({ do { } while (0); 0; });
  local_dec(&module->ref[cpu].count);

  if (__builtin_expect(!!(!module_is_live(module)), 0))
   wake_up_process(module->waiter);
  do { } while (0);
 }
}
const char *module_address_lookup(unsigned long addr,
      unsigned long *symbolsize,
      unsigned long *offset,
      char **modname);


const struct exception_table_entry *search_module_extables(unsigned long addr);

int register_module_notifier(struct notifier_block * nb);
int unregister_module_notifier(struct notifier_block * nb);

extern void print_modules(void);

struct device_driver;
void module_add_driver(struct module *, struct device_driver *);
void module_remove_driver(struct device_driver *);










typedef enum
{
 SCKPATCH_OK = 0,
 SCKPATCH_ERROR,
 SCKPATCH_NOTALLOWED,
 SCKPATCH_MULTI,
 SCKPATCH_NOFOUND
} scpatch_return_codes_t;
typedef struct SC_StackFrameExt {
 s32 id;
 s32 ebp;
 unsigned long int retAddr;
} SC_StackFrameExt_t;
typedef void (*SC_TraceStartFun_t) (
 s32 id,
 SC_StackFrameExt_t *stack,
 s32 write_trace_f
);






typedef void (*SC_TraceStartFun_ctxswitch_t) (struct task_struct *prev, struct task_struct *new);
typedef void (*SC_TraceEndFun_t) (
 s32 id,
 SC_StackFrameExt_t *stack,
 s32 write_trace_f
);
void sckpatch_set_debug_level(int in_debug_level);
scpatch_return_codes_t sckpatch_init(unsigned long int in_kernel_text_address_addr, unsigned long int in_switch_to_addr, unsigned long int in_module_init, unsigned long int in_module_init_text_size, unsigned long int in_module_core, unsigned long int in_module_core_text_size);






void sckpatch_exit(void);
scpatch_return_codes_t sckpatch_patch_fn_addr ( void * in_fn_p, char * in_fn_name_str, s32 * out_id_p, SC_TraceStartFun_t in_trace_func_start_p, SC_TraceEndFun_t in_trace_func_end_p, s32 write_trace_f );
scpatch_return_codes_t sckpatch_unpatch_fn ( s32 in_id );
scpatch_return_codes_t sckpatch_patch_irqfn_addr ( void * in_fn_p, char * in_fn_name_str, s32 * out_id_p, SC_TraceStartFun_t in_trace_func_start_p, SC_TraceEndFun_t in_trace_func_end_p, s32 write_trace_f );
scpatch_return_codes_t sckpatch_unpatch_irqfn ( s32 in_id );
scpatch_return_codes_t sckpatch_patch_ctxswitch ( SC_TraceStartFun_ctxswitch_t in_trace_func_ctxswitch_p );
scpatch_return_codes_t sckpatch_unpatch_ctxswitch ( void );
typedef int (*FUNCPTR) (void);


typedef s32 SC_STATUS;


typedef int (*KERNEL_TEXT_ADDRESS_P_T)(unsigned long int addr);



typedef enum
{
 SC_KM_PATCH_BLOCK_FUNCTION,
 SC_KM_PATCH_BLOCK_RANGE
} patch_blocked_location_type_t;





typedef struct patch_blocked_location_struct
{
 patch_blocked_location_type_t type;
 unsigned long int address_from;
 unsigned long int address_to;
 u32 blocked_count;
 struct patch_blocked_location_struct * prev;
 struct patch_blocked_location_struct * next;
} patch_blocked_location_struct_t;
extern spinlock_t mutex_patch_unpatch_lock;


extern KERNEL_TEXT_ADDRESS_P_T kernel_text_address_p;


extern unsigned long switch_to_addr;






extern int switch_to_patched_f;
extern int sc_km_patch_debug_level;
extern int patch_block_check(unsigned long int in_address);
extern int patch_block_add_function(unsigned long int in_address);
extern int patch_block_add_range(unsigned long int in_address_from, unsigned long int in_address_to);
extern int patch_block_remove_function(unsigned long int in_address);
extern int patch_block_remove_range(unsigned long int in_address_from, unsigned long int in_address_to);






extern int patch_block_remove_all(void);
extern int check_mem_kernel_ok(unsigned long int in_address, unsigned long int in_length);
typedef int irqreturn_t;












static __inline__ /*__attribute__((always_inline))*/ int irq_canonicalize(int irq)
{
 return ((irq == 2) ? 9 : irq);
}
struct proc_dir_entry;
struct irq_chip {
 const char *name;
 unsigned int (*startup)(unsigned int irq);
 void (*shutdown)(unsigned int irq);
 void (*enable)(unsigned int irq);
 void (*disable)(unsigned int irq);

 void (*ack)(unsigned int irq);
 void (*mask)(unsigned int irq);
 void (*mask_ack)(unsigned int irq);
 void (*unmask)(unsigned int irq);
 void (*eoi)(unsigned int irq);

 void (*end)(unsigned int irq);
 void (*set_affinity)(unsigned int irq, cpumask_t dest);
 int (*retrigger)(unsigned int irq);
 int (*set_type)(unsigned int irq, unsigned int flow_type);
 int (*set_wake)(unsigned int irq, unsigned int on);
 const char *typename;
};
struct irq_desc {
 void /*__attribute__((regparm(3)))*/ (*handle_irq)(unsigned int irq,
           struct irq_desc *desc,
           struct pt_regs *regs);
 struct irq_chip *chip;
 void *handler_data;
 void *chip_data;
 struct irqaction *action;
 unsigned int status;

 unsigned int depth;
 unsigned int wake_depth;
 unsigned int irq_count;
 unsigned int irqs_unhandled;
 spinlock_t lock;
 struct proc_dir_entry *dir;

} __attribute__((__aligned__((1 << (6)))));

extern struct irq_desc irq_desc[224];





typedef struct irq_chip hw_irq_controller;

typedef struct irq_desc irq_desc_t;




struct proc_dir_entry;
struct pt_regs;
struct notifier_block;


void /*__attribute__ ((__section__ (".init.text")))*/ profile_init(void);
void profile_tick(int, struct pt_regs *);
void profile_hit(int, void *);

void create_prof_cpu_mask(struct proc_dir_entry *);




enum profile_type {
 PROFILE_TASK_EXIT,
 PROFILE_MUNMAP
};
static inline /*__attribute__((always_inline))*/ int task_handoff_register(struct notifier_block * n)
{
 return -38;
}

static inline /*__attribute__((always_inline))*/ int task_handoff_unregister(struct notifier_block * n)
{
 return -38;
}

static inline /*__attribute__((always_inline))*/ int profile_event_register(enum profile_type t, struct notifier_block * n)
{
 return -38;
}

static inline /*__attribute__((always_inline))*/ int profile_event_unregister(enum profile_type t, struct notifier_block * n)
{
 return -38;
}





static inline /*__attribute__((always_inline))*/ int register_timer_hook(int (*hook)(struct pt_regs *))
{
 return -38;
}

static inline /*__attribute__((always_inline))*/ void unregister_timer_hook(int (*hook)(struct pt_regs *))
{
 return;
}











extern char _text[], _stext[], _etext[];
extern char _data[], _sdata[], _edata[];
extern char __bss_start[], __bss_stop[];
extern char __init_begin[], __init_end[];
extern char _sinittext[], _einittext[];
extern char _sextratext[] __attribute__((weak));
extern char _eextratext[] __attribute__((weak));
extern char _end[];
extern char __per_cpu_start[], __per_cpu_end[];
extern char __kprobes_text_start[], __kprobes_text_end[];
extern char __initdata_begin[], __initdata_end[];
extern char __start_rodata[], __end_rodata[];

struct irq_chip;
extern u8 irq_vector[224];



extern void (*interrupt[224])(void);
/*__attribute__((regparm(3)))*/ void apic_timer_interrupt(void);
/*__attribute__((regparm(3)))*/ void error_interrupt(void);
/*__attribute__((regparm(3)))*/ void spurious_interrupt(void);
/*__attribute__((regparm(3)))*/ void thermal_interrupt(struct pt_regs *);



void disable_8259A_irq(unsigned int irq);
void enable_8259A_irq(unsigned int irq);
int i8259A_irq_pending(unsigned int irq);
void make_8259A_irq(unsigned int irq);
void init_8259A(int aeoi);
void send_IPI_self(int vector) /*__attribute__((regparm(3)))*/;
void init_VISWS_APIC_irqs(void);
void setup_IO_APIC(void);
void disable_IO_APIC(void);
void print_IO_APIC(void);
int IO_APIC_get_PCI_irq_vector(int bus, int slot, int fn);
void send_IPI(int dest, int vector);
void setup_ioapic_dest(void);

extern unsigned long io_apic_irqs;

extern atomic_t irq_err_count;
extern atomic_t irq_mis_count;

extern int setup_irq(unsigned int irq, struct irqaction *new);
static inline /*__attribute__((always_inline))*/ void set_native_irq_info(int irq, cpumask_t mask)
{
}
static inline /*__attribute__((always_inline))*/ void set_balance_irq_affinity(unsigned int irq, cpumask_t mask)
{
}





static inline /*__attribute__((always_inline))*/ int select_smp_affinity(unsigned int irq)
{
 return 1;
}


extern int no_irq_affinity;


extern int handle_IRQ_event(unsigned int irq, struct pt_regs *regs,
       struct irqaction *action);





extern void /*__attribute__((regparm(3)))*/
handle_level_irq(unsigned int irq, struct irq_desc *desc, struct pt_regs *regs);
extern void /*__attribute__((regparm(3)))*/
handle_fasteoi_irq(unsigned int irq, struct irq_desc *desc,
    struct pt_regs *regs);
extern void /*__attribute__((regparm(3)))*/
handle_edge_irq(unsigned int irq, struct irq_desc *desc, struct pt_regs *regs);
extern void /*__attribute__((regparm(3)))*/
handle_simple_irq(unsigned int irq, struct irq_desc *desc,
    struct pt_regs *regs);
extern void /*__attribute__((regparm(3)))*/
handle_percpu_irq(unsigned int irq, struct irq_desc *desc,
    struct pt_regs *regs);
extern void /*__attribute__((regparm(3)))*/
handle_bad_irq(unsigned int irq, struct irq_desc *desc, struct pt_regs *regs);





extern const char *
handle_irq_name(void /*__attribute__((regparm(3)))*/ (*handle)(unsigned int, struct irq_desc *,
     struct pt_regs *));





extern /*__attribute__((regparm(3)))*/ unsigned int __do_IRQ(unsigned int irq, struct pt_regs *regs);







static inline /*__attribute__((always_inline))*/ void generic_handle_irq(unsigned int irq, struct pt_regs *regs)
{
 struct irq_desc *desc = irq_desc + irq;

 if (__builtin_expect(!!(desc->handle_irq), 1))
  desc->handle_irq(irq, desc, regs);
 else
  __do_IRQ(irq, regs);
}


extern void note_interrupt(unsigned int irq, struct irq_desc *desc,
      int action_ret, struct pt_regs *regs);


void check_irq_resend(struct irq_desc *desc, unsigned int irq);


extern void init_irq_proc(void);


extern int noirqdebug_setup(char *str);


extern int can_request_irq(unsigned int irq, unsigned long irqflags);


extern struct irq_chip no_irq_chip;
extern struct irq_chip dummy_irq_chip;

extern void
set_irq_chip_and_handler(unsigned int irq, struct irq_chip *chip,
    void /*__attribute__((regparm(3)))*/ (*handle)(unsigned int,
       struct irq_desc *,
       struct pt_regs *));
extern void
__set_irq_handler(unsigned int irq,
    void /*__attribute__((regparm(3)))*/ (*handle)(unsigned int, struct irq_desc *,
       struct pt_regs *),
    int is_chained);




static inline /*__attribute__((always_inline))*/ void
set_irq_handler(unsigned int irq,
  void /*__attribute__((regparm(3)))*/ (*handle)(unsigned int, struct irq_desc *,
     struct pt_regs *))
{
 __set_irq_handler(irq, handle, 0);
}






static inline /*__attribute__((always_inline))*/ void
set_irq_chained_handler(unsigned int irq,
   void /*__attribute__((regparm(3)))*/ (*handle)(unsigned int, struct irq_desc *,
      struct pt_regs *))
{
 __set_irq_handler(irq, handle, 1);
}



extern int set_irq_chip(unsigned int irq, struct irq_chip *chip);
extern int set_irq_data(unsigned int irq, void *data);
extern int set_irq_chip_data(unsigned int irq, void *data);
extern int set_irq_type(unsigned int irq, unsigned int type);

typedef struct {
 unsigned int __softirq_pending;
 unsigned long idle_timestamp;
 unsigned int __nmi_count;
 unsigned int apic_timer_irqs;
} __attribute__((__aligned__((1 << (6))))) irq_cpustat_t;

extern __typeof__(irq_cpustat_t) per_cpu__irq_stat;
extern irq_cpustat_t irq_stat[];




void ack_bad_irq(unsigned int irq);
struct task_struct;


static inline /*__attribute__((always_inline))*/ void account_system_vtime(struct task_struct *tsk)
{
}
extern void irq_exit(void);
struct irqaction {
 irqreturn_t (*handler)(int, void *, struct pt_regs *);
 unsigned long flags;
 cpumask_t mask;
 const char *name;
 void *dev_id;
 struct irqaction *next;
 int irq;
 struct proc_dir_entry *dir;
};

extern irqreturn_t no_action(int cpl, void *dev_id, struct pt_regs *regs);
extern int request_irq(unsigned int,
         irqreturn_t (*handler)(int, void *, struct pt_regs *),
         unsigned long, const char *, void *);
extern void free_irq(unsigned int, void *);
extern void disable_irq_nosync(unsigned int irq);
extern void disable_irq(unsigned int irq);
extern void enable_irq(unsigned int irq);
static inline /*__attribute__((always_inline))*/ void disable_irq_nosync_lockdep(unsigned int irq)
{
 disable_irq_nosync(irq);



}

static inline /*__attribute__((always_inline))*/ void disable_irq_lockdep(unsigned int irq)
{
 disable_irq(irq);



}

static inline /*__attribute__((always_inline))*/ void enable_irq_lockdep(unsigned int irq)
{



 enable_irq(irq);
}


extern int set_irq_wake(unsigned int irq, unsigned int on);

static inline /*__attribute__((always_inline))*/ int enable_irq_wake(unsigned int irq)
{
 return set_irq_wake(irq, 1);
}

static inline /*__attribute__((always_inline))*/ int disable_irq_wake(unsigned int irq)
{
 return set_irq_wake(irq, 0);
}
static inline /*__attribute__((always_inline))*/ void /*__attribute__((deprecated))*/ cli(void)
{
 local_irq_disable();
}
static inline /*__attribute__((always_inline))*/ void /*__attribute__((deprecated))*/ sti(void)
{
 local_irq_enable();
}
static inline /*__attribute__((always_inline))*/ void /*__attribute__((deprecated))*/ save_flags(unsigned long *x)
{
 local_save_flags(*x);
}

static inline /*__attribute__((always_inline))*/ void /*__attribute__((deprecated))*/ restore_flags(unsigned long x)
{
 local_irq_restore(x);
}

static inline /*__attribute__((always_inline))*/ void /*__attribute__((deprecated))*/ save_and_cli(unsigned long *x)
{
 local_irq_save(*x);
}



extern void local_bh_disable(void);
extern void __local_bh_enable(void);
extern void _local_bh_enable(void);
extern void local_bh_enable(void);
extern void local_bh_enable_ip(unsigned long ip);







enum
{
 HI_SOFTIRQ=0,
 TIMER_SOFTIRQ,
 NET_TX_SOFTIRQ,
 NET_RX_SOFTIRQ,
 BLOCK_SOFTIRQ,
 TASKLET_SOFTIRQ
};





struct softirq_action
{
 void (*action)(struct softirq_action *);
 void *data;
};

 /*__attribute__ ((__section__ (".init.text")))*/ void do_softirq(void);
extern void open_softirq(int nr, void (*action)(struct softirq_action*), void *data);
extern void softirq_init(void);

extern void raise_softirq_irqoff(unsigned int nr) /*__attribute__((regparm(3)))*/;
extern void raise_softirq(unsigned int nr) /*__attribute__((regparm(3)))*/;
struct tasklet_struct
{
 struct tasklet_struct *next;
 unsigned long state;
 atomic_t count;
 void (*func)(unsigned long);
 unsigned long data;
};
enum
{
 TASKLET_STATE_SCHED,
 TASKLET_STATE_RUN
};
extern void __tasklet_schedule(struct tasklet_struct *t) /*__attribute__((regparm(3)))*/;

static inline /*__attribute__((always_inline))*/ void tasklet_schedule(struct tasklet_struct *t)
{
 if (!test_and_set_bit(TASKLET_STATE_SCHED, &t->state))
  __tasklet_schedule(t);
}

extern void __tasklet_hi_schedule(struct tasklet_struct *t) /*__attribute__((regparm(3)))*/;

static inline /*__attribute__((always_inline))*/ void tasklet_hi_schedule(struct tasklet_struct *t)
{
 if (!test_and_set_bit(TASKLET_STATE_SCHED, &t->state))
  __tasklet_hi_schedule(t);
}


static inline /*__attribute__((always_inline))*/ void tasklet_disable_nosync(struct tasklet_struct *t)
{
 atomic_inc(&t->count);
 __asm__ __volatile__("": : :"memory");
}

static inline /*__attribute__((always_inline))*/ void tasklet_disable(struct tasklet_struct *t)
{
 tasklet_disable_nosync(t);
 do { } while (0);
 __asm__ __volatile__("": : :"memory");
}

static inline /*__attribute__((always_inline))*/ void tasklet_enable(struct tasklet_struct *t)
{
 __asm__ __volatile__("": : :"memory");
 atomic_dec(&t->count);
}

static inline /*__attribute__((always_inline))*/ void tasklet_hi_enable(struct tasklet_struct *t)
{
 __asm__ __volatile__("": : :"memory");
 atomic_dec(&t->count);
}

extern void tasklet_kill(struct tasklet_struct *t);
extern void tasklet_kill_immediate(struct tasklet_struct *t, unsigned int cpu);
extern void tasklet_init(struct tasklet_struct *t,
    void (*func)(unsigned long), unsigned long data);
extern unsigned long probe_irq_on(void);
extern int probe_irq_off(unsigned long);
extern unsigned int probe_irq_mask(unsigned long);
typedef struct _SC_StackFrameRegs_
{
 u32 edi;
 u32 esi;
 u32 ebp;
 u32 old_esp;
 u32 ebx;
 u32 edx;
 u32 ecx;
 u32 eax;
} SC_StackFrameRegs_t;







typedef struct _SC_patchStack_
{
 u32 flags;
 SC_StackFrameRegs_t regs;
 u32 addrOfMainStr;
 SC_StackFrameExt_t s;



} SC_patchStack_t;



typedef struct SC_patchEachFunc {
 u8 trampoline_bytes[32];
} SC_patchEachFunc_t;







typedef struct _SC_patchStubBranch_ {
 u32 copied_bytes;
 unsigned long int backAddr;
 u32 patchFlags;

 FUNCPTR func;
 SC_TraceStartFun_t startFunc;
 SC_TraceEndFun_t endFunc;
} SC_patchStubBranch_t;
typedef enum
{
 I_TYPE_UNKNOWN,
 I_TYPE_Jcc,
 I_TYPE_JMP,
 I_TYPE_CALL,
 I_TYPE_LOOP
} inst_type_enum_t;
typedef enum
{
 I_OP_TYPE_NONE,
 I_OP_TYPE_REG,
 I_OP_TYPE_a,
 I_OP_TYPE_b,
 I_OP_TYPE_c,
 I_OP_TYPE_d,
 I_OP_TYPE_dq,
 I_OP_TYPE_p,
 I_OP_TYPE_pi,
 I_OP_TYPE_pd,
 I_OP_TYPE_ps,
 I_OP_TYPE_q,
 I_OP_TYPE_s,
 I_OP_TYPE_sd,
 I_OP_TYPE_ss,
 I_OP_TYPE_si,
 I_OP_TYPE_v,
 I_OP_TYPE_w,
 I_OP_TYPE_w_b
} inst_operand_type_enum_t;





typedef enum
{
 I_P_GRP1_NONE,
 I_P_GRP1_LOCK,
 I_P_GRP1_REPN,
 I_P_GRP1_REP
} inst_prefix_grp1_enum_t;





typedef enum
{
 I_P_GRP2_NONE,
 I_P_GRP2_SEG_CS_OR_BR_NOTTAKEN,
 I_P_GRP2_SEG_SS,
 I_P_GRP2_SEG_DS_OR_BR_TAKEN,
 I_P_GRP2_SEG_ES,
 I_P_GRP2_SEG_FS,
 I_P_GRP2_SEG_GS
} inst_prefix_grp2_enum_t;





typedef enum
{
 I_P_GRP3_NONE,
 I_P_GRP3_SET
} inst_prefix_grp3_enum_t;





typedef enum
{
 I_P_GRP4_NONE,
 I_P_GRP4_SET,
} inst_prefix_grp4_enum_t;


typedef enum
{
 I_ADDR_MODE_16,
 I_ADDR_MODE_32
} inst_addressing_mode_enum_t;


typedef struct
{
 u8 * inst_start_p;
 u8 curr_offset;
 inst_type_enum_t inst_type_e;
 inst_addressing_mode_enum_t addressing_mode_e;
 inst_addressing_mode_enum_t addressing_mode_operand_e;
 inst_addressing_mode_enum_t addressing_mode_address_e;

 u32 opcode;
 u8 opcode_extended_f;
 u8 opcode_extension;

 u8 length;
 u8 length_prefixes;
 u8 length_opcode;
 u8 length_displ;
 u8 length_immdata;
 u8 length_immdata_2;

 u8 offset_opcode;
 u8 offset_modrm;
 u8 offset_sib;
 u8 offset_displ;
 u8 offset_immdata;
 u8 offset_immdata_2;

 inst_prefix_grp1_enum_t prefix_grp1_e;
 inst_prefix_grp2_enum_t prefix_grp2_e;
 inst_prefix_grp3_enum_t prefix_grp3_e;
 inst_prefix_grp4_enum_t prefix_grp4_e;
} inst_info_struct_t;
extern spinlock_t mutex_gPatchFunc_lock;



extern u32 gPatchedFuncCount;




extern SC_patchStubBranch_t * gPatchFunc;
extern int sc_parse_instruction(
 inst_info_struct_t * out_inst_info_struct_p,
 unsigned long int in_inst_start_p
);
extern SC_STATUS SC_AsmCheckAddrForBeginFunction(
 inst_info_struct_t in_inst_info_struct_a[],
 int in_inst_num,
 s32 in_id
);
extern SC_STATUS SC_AsmCheckAddrForNoOtherBeginFunction(
 inst_info_struct_t in_inst_info_struct_a[],
 int in_inst_num
);
extern FUNCPTR SC_AsmFindAddrForBeginFunction(
 unsigned long int addrStart,
 unsigned long int addrEnd
);
extern SC_STATUS SC_AsmCheckForNoBranch(
 inst_info_struct_t in_inst_info_struct_a[],
 int in_inst_num
);
extern int sc_i80x86_write_inst_jmp_near_relative_32(u8 * out_address_p, u32 in_jump_address);
extern int SC_doPatchFunction(
 FUNCPTR func,
 SC_TraceStartFun_t startFunc,
 SC_TraceEndFun_t endFunc,
 int in_irq_f,
 char * in_func_name,
 s32 write_trace_f
);
extern int SC_doPatchSwitch_to(
 FUNCPTR in_switch_to_p,
 SC_TraceStartFun_ctxswitch_t in_startFunc
);
extern SC_STATUS SC_doUnPatchSwitch_to(FUNCPTR in_switch_to_p);
extern int SC_doUnPatchFunction(FUNCPTR func);
extern int SC_doUnPatchFunctionId(s32 id);



extern void SC_doUnPatchFunctionAll(void);
extern void SC_patchedCodeSwitch_to(void) /*__attribute__((regparm(3)))*/;

extern void SC_trampoline_switch_to(void);
extern unsigned int SC_patchedCode_do_IRQ(unsigned int irq, struct pt_regs *regs) /*__attribute__((regparm(3)))*/;
extern unsigned int SC_trampoline_do_IRQ(unsigned int irq, struct pt_regs *regs) /*__attribute__((regparm(3)))*/;
KERNEL_TEXT_ADDRESS_P_T kernel_text_address_p;

spinlock_t mutex_patch_unpatch_lock = (spinlock_t) { .raw_lock = { }, };

unsigned long switch_to_addr = 0;

int switch_to_patched_f = 0;

int sc_km_patch_debug_level = 1;
static int sckpatch_initialized_f = 0;



static patch_blocked_location_struct_t * patch_blocked_locations_head_p = ((void *)0);







spinlock_t mutex_blocked_locations_lock = (spinlock_t) { .raw_lock = { }, };







int check_mem_kernel_ok(unsigned long int in_address, unsigned long int in_length)
{
 unsigned long int address_to_check;

 address_to_check = (unsigned long int) in_address;

 if ( 0 == kernel_text_address_p ) {
  return (1);
 }
 else {
  if ( (! (*kernel_text_address_p)(address_to_check)) || (! (*kernel_text_address_p)(address_to_check + in_length)) ) {
   return (0);
  } else {
   return (1);
  }
 }
}

int patch_block_check(unsigned long int in_address)
{
 patch_blocked_location_struct_t * tmp_block_loc_p = ((void *)0);
 unsigned long flags;

 do { local_irq_save(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

  tmp_block_loc_p = patch_blocked_locations_head_p;
  while ( ((void *)0) != tmp_block_loc_p )
  {
   if ( tmp_block_loc_p->address_from > in_address )
   {

    do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
    return (0);
   }
   else
   {
    switch (tmp_block_loc_p->type)
    {
     case SC_KM_PATCH_BLOCK_FUNCTION:
      if ( tmp_block_loc_p->address_from == in_address )
      {

       do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
       return (1);
      }
      else
      {
       break;
      }
      break;

     case SC_KM_PATCH_BLOCK_RANGE:
      if ( tmp_block_loc_p->address_to >= in_address )
      {

       do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
       return (1);
      }
      else
      {
       break;
      }
      break;

     default:
      break;
    }

    tmp_block_loc_p = tmp_block_loc_p->next;
   }
  }

 do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);


 return (0);
}

int patch_block_add_function(unsigned long int in_address)
{
 patch_blocked_location_struct_t * tmp_block_loc_p = ((void *)0);
 patch_blocked_location_struct_t * tmp_block_loc_prev_p = ((void *)0);
 patch_blocked_location_struct_t * new_block_loc_p = ((void *)0);
 unsigned long flags;

 do { local_irq_save(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

  tmp_block_loc_p = patch_blocked_locations_head_p;
  while ( ((void *)0) != tmp_block_loc_p )
  {
   if ( tmp_block_loc_p->address_from > in_address )
   {
    break;
   }
   else
   {
    if ( (tmp_block_loc_p->address_from == in_address) && (tmp_block_loc_p->type == SC_KM_PATCH_BLOCK_FUNCTION) )
    {

     tmp_block_loc_p->blocked_count += 1;
     do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
     { do { if ( sc_km_patch_debug_level >= 3 ) { printk("<7>" "SC/CSDA KPatch: DEBUG: " "%s: Adding address (at: 0x%lX).\n", __PRETTY_FUNCTION__, in_address); } } while (0); };
     return (0);
    }
    else
    {
     tmp_block_loc_prev_p = tmp_block_loc_p;
     tmp_block_loc_p = tmp_block_loc_prev_p->next;
    }
   }
  }

  new_block_loc_p = (patch_blocked_location_struct_t *) kmalloc(sizeof(patch_blocked_location_struct_t), ((( gfp_t)0x10u) | (( gfp_t)0x40u) | (( gfp_t)0x80u)));
  if ( ((void *)0) == new_block_loc_p )
  {
   printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: Not enough memory!\n", __PRETTY_FUNCTION__);
   do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
   return (-1);
  }
  else
  {
   new_block_loc_p->type = SC_KM_PATCH_BLOCK_FUNCTION;
   new_block_loc_p->address_from = in_address;
   new_block_loc_p->address_to = 0;
   new_block_loc_p->blocked_count = 1;
  }

  if ( ((void *)0) == tmp_block_loc_p )
  {
   new_block_loc_p->next = ((void *)0);
   new_block_loc_p->prev = tmp_block_loc_prev_p;

   if ( ((void *)0) == tmp_block_loc_prev_p )
   {

    patch_blocked_locations_head_p = new_block_loc_p;
   }
   else
   {

    tmp_block_loc_prev_p->next = new_block_loc_p;
   }
  }
  else
  {
   new_block_loc_p->prev = tmp_block_loc_p->prev;
   new_block_loc_p->next = tmp_block_loc_p;


   if ( ((void *)0) == tmp_block_loc_prev_p )
   {

    patch_blocked_locations_head_p = new_block_loc_p;
   }
   else
   {
    tmp_block_loc_prev_p->next = new_block_loc_p;
   }

   tmp_block_loc_p->prev = new_block_loc_p;
  }

 do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

 { do { if ( sc_km_patch_debug_level >= 3 ) { printk("<7>" "SC/CSDA KPatch: DEBUG: " "%s: Adding address (at: 0x%lX).\n", __PRETTY_FUNCTION__, in_address); } } while (0); };
 return (0);
}

int patch_block_add_range(unsigned long int in_address_from, unsigned long int in_address_to)
{
 patch_blocked_location_struct_t * tmp_block_loc_p = ((void *)0);
 patch_blocked_location_struct_t * tmp_block_loc_prev_p = ((void *)0);
 patch_blocked_location_struct_t * new_block_loc_p = ((void *)0);
 unsigned long flags;

 if ( in_address_to <= in_address_from )
 {
  { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Address range invalid (from: 0x%lX, to: 0x%lX)!\n", __PRETTY_FUNCTION__, in_address_from, in_address_to); } } while (0); };
  return (-1);
 }

 do { local_irq_save(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

  tmp_block_loc_p = patch_blocked_locations_head_p;
  while ( ((void *)0) != tmp_block_loc_p )
  {
   if ( tmp_block_loc_p->address_from > in_address_from )
   {
    break;
   }
   else
   {
    if ( (tmp_block_loc_p->address_from == in_address_from) && (tmp_block_loc_p->type == SC_KM_PATCH_BLOCK_RANGE) && (tmp_block_loc_p->address_to == in_address_to) )
    {

     tmp_block_loc_p->blocked_count += 1;
     do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
     { do { if ( sc_km_patch_debug_level >= 3 ) { printk("<7>" "SC/CSDA KPatch: DEBUG: " "%s: Adding address range (from: 0x%lX, to: 0x%lX).\n", __PRETTY_FUNCTION__, in_address_from, in_address_to); } } while (0); };
     return (0);
    }
    else
    {
     tmp_block_loc_prev_p = tmp_block_loc_p;
     tmp_block_loc_p = tmp_block_loc_prev_p->next;
    }
   }
  }

  new_block_loc_p = (patch_blocked_location_struct_t *) kmalloc(sizeof(patch_blocked_location_struct_t), ((( gfp_t)0x10u) | (( gfp_t)0x40u) | (( gfp_t)0x80u)));
  if ( ((void *)0) == new_block_loc_p )
  {
   printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: Not enough memory!\n", __PRETTY_FUNCTION__);
   do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
   return (-1);
  }
  else
  {
   new_block_loc_p->type = SC_KM_PATCH_BLOCK_RANGE;
   new_block_loc_p->address_from = in_address_from;
   new_block_loc_p->address_to = in_address_to;
   new_block_loc_p->blocked_count = 1;
  }

  if ( ((void *)0) == tmp_block_loc_p )
  {
   new_block_loc_p->next = ((void *)0);
   new_block_loc_p->prev = tmp_block_loc_prev_p;

   if ( ((void *)0) == tmp_block_loc_prev_p )
   {

    patch_blocked_locations_head_p = new_block_loc_p;
   }
   else
   {

    tmp_block_loc_prev_p->next = new_block_loc_p;
   }
  }
  else
  {
   new_block_loc_p->prev = tmp_block_loc_p->prev;
   new_block_loc_p->next = tmp_block_loc_p;


   if ( ((void *)0) == tmp_block_loc_prev_p )
   {

    patch_blocked_locations_head_p = new_block_loc_p;
   }
   else
   {
    tmp_block_loc_prev_p->next = new_block_loc_p;
   }

   tmp_block_loc_p->prev = new_block_loc_p;
  }

 do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

 { do { if ( sc_km_patch_debug_level >= 3 ) { printk("<7>" "SC/CSDA KPatch: DEBUG: " "%s: Adding address range (from: 0x%lX, to: 0x%lX).\n", __PRETTY_FUNCTION__, in_address_from, in_address_to); } } while (0); };
 return (0);
}

int patch_block_remove_function(unsigned long int in_address)
{
 patch_blocked_location_struct_t * tmp_block_loc_p = ((void *)0);
 unsigned long flags;

 do { local_irq_save(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

  tmp_block_loc_p = patch_blocked_locations_head_p;
  while ( ((void *)0) != tmp_block_loc_p )
  {
   if ( tmp_block_loc_p->address_from > in_address )
   {
    { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Function at 0x%lX is not blocked!\n", __PRETTY_FUNCTION__, in_address); } } while (0); };
    do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
    return (0);
   }
   else
   {
    if ( (tmp_block_loc_p->address_from == in_address) && (tmp_block_loc_p->type == SC_KM_PATCH_BLOCK_FUNCTION) )
    {

     tmp_block_loc_p->blocked_count -= 1;


     if ( 0 == (tmp_block_loc_p->blocked_count) )
     {
      if ( ((void *)0) != tmp_block_loc_p->prev )
      {
       (tmp_block_loc_p->prev)->next = tmp_block_loc_p->next;
      }
      else
      {
       patch_blocked_locations_head_p = tmp_block_loc_p->next;
      }

      if ( ((void *)0) != tmp_block_loc_p->next )
      {
       (tmp_block_loc_p->next)->prev = tmp_block_loc_p->prev;
      }

      kfree (tmp_block_loc_p);
     }

     do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
     { do { if ( sc_km_patch_debug_level >= 3 ) { printk("<7>" "SC/CSDA KPatch: DEBUG: " "%s: Removing address (at 0x%lX).\n", __PRETTY_FUNCTION__, in_address); } } while (0); };
     return (0);
    }
    else
    {
     tmp_block_loc_p = tmp_block_loc_p->next;
    }
   }
  }

 do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

 { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Function at 0x%lX is not blocked!\n", __PRETTY_FUNCTION__, in_address); } } while (0); };
 return (0);
}

int patch_block_remove_range(unsigned long int in_address_from, unsigned long int in_address_to)
{
 patch_blocked_location_struct_t * tmp_block_loc_p = ((void *)0);
 unsigned long flags;

 if ( in_address_to <= in_address_from )
 {
  { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Address range invalid (from: 0x%lX, to: 0x%lX)!\n", __PRETTY_FUNCTION__, in_address_from, in_address_to); } } while (0); };
  return (-1);
 }

 do { local_irq_save(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

  tmp_block_loc_p = patch_blocked_locations_head_p;
  while ( ((void *)0) != tmp_block_loc_p )
  {
   if ( tmp_block_loc_p->address_from > in_address_from )
   {
    { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Memory range from 0x%lX to 0x%lX is not blocked!\n", __PRETTY_FUNCTION__, in_address_from, in_address_to); } } while (0); };
    do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
    return (0);
   }
   else
   {
    if ( (tmp_block_loc_p->address_from == in_address_from) && (tmp_block_loc_p->type == SC_KM_PATCH_BLOCK_RANGE) && (tmp_block_loc_p->address_to == in_address_to) )
    {

     tmp_block_loc_p->blocked_count -= 1;


     if ( 0 == (tmp_block_loc_p->blocked_count) )
     {
      if ( ((void *)0) != tmp_block_loc_p->prev )
      {
       (tmp_block_loc_p->prev)->next = tmp_block_loc_p->next;
      }
      else
      {
       patch_blocked_locations_head_p = tmp_block_loc_p->next;
      }

      if ( ((void *)0) != tmp_block_loc_p->next )
      {
       (tmp_block_loc_p->next)->prev = tmp_block_loc_p->prev;
      }

      kfree (tmp_block_loc_p);
     }

     do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
     { do { if ( sc_km_patch_debug_level >= 3 ) { printk("<7>" "SC/CSDA KPatch: DEBUG: " "%s: Removing address range (from: 0x%lX, to: 0x%lX).\n", __PRETTY_FUNCTION__, in_address_from, in_address_to); } } while (0); };
     return (0);
    }
    else
    {
     tmp_block_loc_p = tmp_block_loc_p->next;
    }
   }
  }

 do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

 { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Memory range from 0x%lX to 0x%lX is not blocked!\n", __PRETTY_FUNCTION__, in_address_from, in_address_to); } } while (0); };
 return (0);
}

int patch_block_remove_all(void)
{
 patch_blocked_location_struct_t * tmp_block_loc_p = ((void *)0);
 patch_blocked_location_struct_t * to_delete_block_loc_p = ((void *)0);
 unsigned long flags;

 do { local_irq_save(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);

  tmp_block_loc_p = patch_blocked_locations_head_p;
  while ( ((void *)0) != tmp_block_loc_p )
  {
   to_delete_block_loc_p = tmp_block_loc_p;
   tmp_block_loc_p = to_delete_block_loc_p->next;
   kfree (to_delete_block_loc_p);
  }

  patch_blocked_locations_head_p = ((void *)0);

 do { local_irq_restore(flags); do { do { } while (0); (void)0; (void)(&mutex_blocked_locations_lock); } while (0); } while (0);
 { do { if ( sc_km_patch_debug_level >= 3 ) { printk("<7>" "SC/CSDA KPatch: DEBUG: " "%s: Removing all blocked addresses.\n", __PRETTY_FUNCTION__); } } while (0); };

 return (0);
}

void sckpatch_set_debug_level(int in_debug_level)
{
 if ( (0 <= in_debug_level) && (in_debug_level <= 3) )
 {
  sc_km_patch_debug_level = in_debug_level;
  { do { if ( sc_km_patch_debug_level >= 2 ) { printk("<6>" "SC/CSDA KPatch: INFO: " "%s: Setting debug level to %d.\n", __PRETTY_FUNCTION__, sc_km_patch_debug_level); } } while (0); };
  return;
 }
 else
 {
  printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: Debug level must be an integer from the range 0 - 3!\n", __PRETTY_FUNCTION__);
  return;
 }
}



scpatch_return_codes_t sckpatch_patch_fn_addr ( void * in_fn_p, char * in_fn_name_str, s32 * out_id_p, SC_TraceStartFun_t in_trace_func_start_p, SC_TraceEndFun_t in_trace_func_end_p, s32 write_trace_f )
{
 s32 retval;

 if ( 0 == sckpatch_initialized_f )
 {
  { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Kernel Patch subsystem not initialized!\n", __PRETTY_FUNCTION__); } } while (0); };
  return (SCKPATCH_ERROR);
 }





 retval = SC_doPatchFunction((FUNCPTR) in_fn_p, in_trace_func_start_p, in_trace_func_end_p, 0, in_fn_name_str, write_trace_f);


 if ( 0 <= retval )
 {

  *out_id_p = retval;
  return ( SCKPATCH_OK );
 }
 else
 {

  *out_id_p = -1;

  switch (retval)
  {

   case -1:
    return ( SCKPATCH_NOTALLOWED );
    break;


   case -2:
    return ( SCKPATCH_MULTI );
    break;


   default:
    return ( SCKPATCH_ERROR );
    break;
  }
 }
}

scpatch_return_codes_t sckpatch_unpatch_fn ( s32 in_id )
{
 if ( 0 == sckpatch_initialized_f )
 {
  { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Kernel Patch subsystem not initialized!\n", __PRETTY_FUNCTION__); } } while (0); };
  return (SCKPATCH_ERROR);
 }

 if ( (in_id < 8) || ((20 - 1) < in_id) )
 {

  printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: Wrong function ID (%d)! Valid normal function IDs are from range %d - %d\n", __PRETTY_FUNCTION__, in_id, 8, (20 - 1));
  return ( SCKPATCH_ERROR );
 }
 else
 {
  if ( ((SC_STATUS) -1) == SC_doUnPatchFunctionId(in_id) )
  {
   return ( SCKPATCH_NOFOUND );
  }
  else
  {
   return ( SCKPATCH_OK );
  }
 }
}

scpatch_return_codes_t sckpatch_patch_irqfn_addr ( void * in_fn_p, char * in_fn_name_str, s32 * out_id_p, SC_TraceStartFun_t in_trace_func_start_p, SC_TraceEndFun_t in_trace_func_end_p, s32 write_trace_f )
{
 s32 retval;

 if ( 0 == sckpatch_initialized_f )
 {
  { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Kernel Patch subsystem not initialized!\n", __PRETTY_FUNCTION__); } } while (0); };
  return (SCKPATCH_ERROR);
 }




 retval = SC_doPatchFunction((FUNCPTR) in_fn_p, in_trace_func_start_p, in_trace_func_end_p, 1, in_fn_name_str, write_trace_f);


 if ( 0 <= retval )
 {

  *out_id_p = retval;
  return ( SCKPATCH_OK );
 }
 else
 {

  *out_id_p = -1;

  switch (retval)
  {

   case -1:
    return ( SCKPATCH_NOTALLOWED );
    break;


   case -2:
    return ( SCKPATCH_MULTI );
    break;


   default:
    return ( SCKPATCH_ERROR );
    break;
  }
 }
}

scpatch_return_codes_t sckpatch_unpatch_irqfn ( s32 in_id )
{
 if ( 0 == sckpatch_initialized_f )
 {
  { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Kernel Patch subsystem not initialized!\n", __PRETTY_FUNCTION__); } } while (0); };
  return (SCKPATCH_ERROR);
 }

 if ( (in_id < 1) || (7 < in_id) )
 {
  printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: Wrong IRQ function ID (%d)! Valid IRQ function IDs are from range %d - %d\n", __PRETTY_FUNCTION__, in_id, 1, 7);

  return ( SCKPATCH_ERROR );
 }
 else
 {
  if ( ((SC_STATUS) -1) == SC_doUnPatchFunctionId(in_id) )
  {
   return ( SCKPATCH_NOFOUND );
  }
  else
  {
   return ( SCKPATCH_OK );
  }
 }
}

scpatch_return_codes_t sckpatch_patch_ctxswitch ( SC_TraceStartFun_ctxswitch_t in_trace_func_ctxswitch_p )
{
 s32 retval;

 if ( 0 == sckpatch_initialized_f )
 {
  { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: Kernel Patch subsystem not initialized!\n", __PRETTY_FUNCTION__); } } while (0); };
  return (SCKPATCH_ERROR);
 }

 retval = SC_doPatchSwitch_to((FUNCPTR) switch_to_addr, in_trace_func_ctxswitch_p);

 if ( 0 <= retval )
 {

  return ( SCKPATCH_OK );
 }
 else
 {


  switch (retval)
  {

   case -1:
    return ( SCKPATCH_NOTALLOWED );
    break;


   case -2:
    return ( SCKPATCH_MULTI );
    break;


   default:
    return ( SCKPATCH_ERROR );
    break;
  }
 }
}

scpatch_return_codes_t sckpatch_unpatch_ctxswitch ( void )
{
 if ( 0 == sckpatch_initialized_f )
 {
  { do { if ( sc_km_patch_debug_level >= 2 ) { printk("<6>" "SC/CSDA KPatch: INFO: " "%s: Kernel Patch subsystem not initialized!\n", __PRETTY_FUNCTION__); } } while (0); };
  return (SCKPATCH_ERROR);
 }

 if ( ((SC_STATUS) -1) == SC_doUnPatchSwitch_to((FUNCPTR) switch_to_addr) )
 {
  return ( SCKPATCH_NOFOUND );
 }
 else
 {
  return ( SCKPATCH_OK );
 }
}

scpatch_return_codes_t sckpatch_init(unsigned long int in_kernel_text_address_addr, unsigned long int in_switch_to_addr, unsigned long int in_module_init, unsigned long int in_module_init_text_size, unsigned long int in_module_core, unsigned long int in_module_core_text_size)
{
 s32 id;

 if ( 0 != sckpatch_initialized_f )
 {
  { do { if ( sc_km_patch_debug_level >= 2 ) { printk("<6>" "SC/CSDA KPatch: INFO: " "%s: Kernel Patch subsystem already initialized!\n", __PRETTY_FUNCTION__); } } while (0); };
  return (SCKPATCH_OK);
 }

 printk("<6>" "SC/CSDA Kernel Patch subsystem initializing...\n");

 if ( 0 == in_switch_to_addr )
 {
  printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: required address of context switch is not valid - exiting!\n", __PRETTY_FUNCTION__);
  return (SCKPATCH_ERROR);
 }
 else
 {
  switch_to_addr = in_switch_to_addr;
 }

 if ( 0 == in_kernel_text_address_addr )
 {
  { do { if ( sc_km_patch_debug_level >= 1 ) { printk("<4>" "SC/CSDA KPatch: WARNING: " "%s: address of kernel_text_address is not valid - accesses will not be checked.\n", __PRETTY_FUNCTION__); } } while (0); };
 }
 kernel_text_address_p = (KERNEL_TEXT_ADDRESS_P_T) in_kernel_text_address_addr;
 gPatchFunc = (SC_patchStubBranch_t *) kmalloc(20 * sizeof(SC_patchStubBranch_t), ((( gfp_t)0x10u) | (( gfp_t)0x40u) | (( gfp_t)0x80u)));
 if ( ((void *)0) == gPatchFunc )
 {
  printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: Not enough memory!\n", __PRETTY_FUNCTION__);
  return (SCKPATCH_ERROR);
 }


 do { do { } while (0); (void)0; (void)(&mutex_gPatchFunc_lock); } while (0);
  for(id = 0; id < 20; id++)
  {
   gPatchFunc[id].func = ((void *)0);
   gPatchFunc[id].startFunc = ((void *)0);
   gPatchFunc[id].endFunc = ((void *)0);
   { do { (*&(gPatchFunc[id].patchFlags)) = 0; } while (0); };
   gPatchFunc[id].backAddr = 0;
  }
 do { do { } while (0); (void)0; (void)(&mutex_gPatchFunc_lock); } while (0);


 if ( (0 != in_module_init) && (0 != in_module_init_text_size) && (0 != in_module_core) && (0 != in_module_core_text_size) )
 {
  if ( 0 != patch_block_add_range((unsigned long int) in_module_init, (unsigned long int) in_module_init + (unsigned long int) in_module_init_text_size) )
  {
   printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: Can't block the addresses within the module!\n", __PRETTY_FUNCTION__);
   return (SCKPATCH_ERROR);
  }
  if ( 0 != patch_block_add_range((unsigned long int) in_module_core, (unsigned long int) in_module_core + (unsigned long int) in_module_core_text_size) )
  {
   printk("<3>" "SC/CSDA KPatch: SC_ERROR: " "%s: Can't block the addresses within the module!\n", __PRETTY_FUNCTION__);
   return (SCKPATCH_ERROR);
  }
 }
 sckpatch_initialized_f = 1;

 printk("<6>" "SC/CSDA Kernel Patch subsystem initialized (debug level = %d)\n", sc_km_patch_debug_level);

 return (0);
}

void sckpatch_exit(void)
{
 if ( 0 == sckpatch_initialized_f )
 {
  { do { if ( sc_km_patch_debug_level >= 3 ) { printk("<7>" "SC/CSDA KPatch: DEBUG: " "%s: Kernel Patch subsystem not initialized!\n", __PRETTY_FUNCTION__); } } while (0); };
  return;
 }


 SC_doUnPatchFunctionAll();
 if ( 0 != switch_to_patched_f )
 {
  SC_doUnPatchSwitch_to((FUNCPTR) switch_to_addr);
 }
 kfree (gPatchFunc);
 gPatchFunc = ((void *)0);






 patch_block_remove_all();

 sckpatch_initialized_f = 0;

 printk("<6>" "SC/CSDA Kernel Patch subsystem: Uninitialized.\n");
}
