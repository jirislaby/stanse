#ifndef COMMON_H
#define COMMON_H

/* typedefs suck and always did */
typedef void *my_jobject;

extern int parse(const char *file, void *priv, my_jobject *AST);

extern void addChild(void *priv, my_jobject parent, my_jobject child);
extern void setAttr(void *priv, my_jobject parent, const char *name,
		const char *val);
extern void setColumn(void *priv, my_jobject obj, int column);
extern void setEndLine(void *priv, my_jobject obj, int line);
extern void setLine(void *priv, my_jobject obj, int line);

extern my_jobject newAbstractDeclarator(void *priv);
extern my_jobject newArrayDecl(void *priv);
extern my_jobject newBaseType(void *priv, const char *type);
extern my_jobject newBinaryExpression(void *priv, const char *op);
extern my_jobject newCompoundStatement(void *priv);
extern my_jobject newDeclaration(void *priv);
extern my_jobject newDeclarationSpecifiers(void *priv);
extern my_jobject newDeclarator(void *priv);
extern my_jobject newEnum(void *priv);
extern my_jobject newEnumerator(void *priv, const char *name);
extern my_jobject newExternalDeclaration(void *priv);
extern my_jobject newFunctionDecl(void *priv);
extern my_jobject newFunctionDefinition(void *priv);
extern my_jobject newIdentifier(void *priv, const char *name);
extern my_jobject newInitDeclarator(void *priv);
extern my_jobject newParameter(void *priv);
extern my_jobject newStruct(void *priv);
extern my_jobject newStructDeclaration(void *priv);
extern my_jobject newStructDeclarator(void *priv);
extern my_jobject newTranslationUnit(void *priv);
extern my_jobject newTypeName(void *priv);
extern my_jobject newTypeSpecifier(void *priv);
extern my_jobject newUnion(void *priv);

#endif
