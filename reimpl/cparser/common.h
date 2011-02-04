#ifndef COMMON_H
#define COMMON_H

/* typedefs suck and always did */
typedef void *my_jobject;

extern int parse(const char *file, void *priv, my_jobject *AST);

extern void addChild(void *priv, my_jobject parent, my_jobject child);
extern void setColumn(void *priv, my_jobject obj, int column);
extern void setEndLine(void *priv, my_jobject obj, int line);
extern void setLine(void *priv, my_jobject obj, int line);

extern my_jobject newBinaryOperation(void *priv, const char *op);
extern my_jobject newCompoundStatement(void *priv);
extern my_jobject newDeclaration(void *priv);
extern my_jobject newDeclarationSpecifiers(void *priv);
extern my_jobject newDeclarator(void *priv);
extern my_jobject newExternalDeclaration(void *priv);
extern my_jobject newFunctionDefinition(void *priv);
extern my_jobject newTranslationUnit(void *priv);

#endif
