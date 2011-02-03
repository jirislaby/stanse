#ifndef COMMON_H
#define COMMON_H

/* typedefs suck and always did */
typedef void *my_jobject;

extern int parse(const char *file, void *priv, my_jobject *AST);

extern my_jobject newTranslationUnit(void *priv);
extern my_jobject newBinaryOperation(void *priv, const char *op);

#endif
