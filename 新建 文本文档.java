private void createJavaElementsFrom(IProject myProject, IFolder myFolder, IFile myFile) {
      IJavaProject myJavaProject = JavaCore.create(myProject);
      if (myJavaProject == null)
         // the project is not configured for Java (has no Java nature)
            return;
            
      // get a package fragment or package fragment root
      IJavaElement myPackageFragment = JavaCore.create(myFolder);
        
      // get a .java (compilation unit), .class (class file), or
      // .jar (package fragment root)
      IJavaElement myJavaFile = JavaCore.create(myFile);
    }
//一旦有了 Java 元素，就可以使用 JDT API 来遍历和查询模型。还可以查询包含在 Java 元素内的非 Java 资源。   
         private void createJavaElementsFrom(IProject myProject, IFolder myFolder, IFile myFile) {
        ...
        // get the non Java resources contained in my project.
        Object[] nonJavaChildren = myJavaProject.getNonJavaResources();可以通过对 AST 使用工厂方法来从头创建 CompilationUnit。这些方法名以 new... 开头。以下是创建 HelloWorld 类的一个示例。
第一个代码段是生成的输出： 
	package example;
   import java.util.*;	
   public class HelloWorld {
      public static void main(String[] args) {
			System.out.println("Hello" + " world");
		}
	}
下列代码段是生成输出的相应代码。 
		AST ast = new AST();
		CompilationUnit unit = ast.newCompilationUnit();
		PackageDeclaration packageDeclaration = ast.newPackageDeclaration();
		packageDeclaration.setName(ast.newSimpleName("example"));
		unit.setPackage(packageDeclaration);
		ImportDeclaration importDeclaration = ast.newImportDeclaration();
		QualifiedName name =
			ast.newQualifiedName(
				ast.newSimpleName("java"),
				ast.newSimpleName("util"));
		importDeclaration.setName(name);
		importDeclaration.setOnDemand(true);
		unit.imports().add(importDeclaration);
		TypeDeclaration type = ast.newTypeDeclaration();
		type.setInterface(false);
		type.setModifiers(Modifier.PUBLIC);
		type.setName(ast.newSimpleName("HelloWorld"));
		MethodDeclaration methodDeclaration = ast.newMethodDeclaration();
		methodDeclaration.setConstructor(false);
		methodDeclaration.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		methodDeclaration.setName(ast.newSimpleName("main"));
		methodDeclaration.setReturnType(ast.newPrimitiveType(PrimitiveType.VOID));
		SingleVariableDeclaration variableDeclaration = ast.newSingleVariableDeclaration();
		variableDeclaration.setModifiers(Modifier.NONE);
		variableDeclaration.setType(ast.newArrayType(ast.newSimpleType(ast.newSimpleName("String"))));
		variableDeclaration.setName(ast.newSimpleName("args"));
		methodDeclaration.parameters().add(variableDeclaration);
		org.eclipse.jdt.core.dom.Block block = ast.newBlock();
		MethodInvocation methodInvocation = ast.newMethodInvocation();
		name = 
			ast.newQualifiedName(
				ast.newSimpleName("System"),
				ast.newSimpleName("out"));
		methodInvocation.setExpression(name);
		methodInvocation.setName(ast.newSimpleName("println"));
		InfixExpression infixExpression = ast.newInfixExpression();
		infixExpression.setOperator(InfixExpression.Operator.PLUS);
		StringLiteral literal = ast.newStringLiteral();
		literal.setLiteralValue("Hello");
		infixExpression.setLeftOperand(literal);
		literal = ast.newStringLiteral();
		literal.setLiteralValue(" world");
		infixExpression.setRightOperand(literal);
		methodInvocation.arguments().add(infixExpression);
		ExpressionStatement expressionStatement = ast.newExpressionStatement(methodInvocation);
		block.statements().add(expressionStatement);
		methodDeclaration.setBody(block);
		type.bodyDeclarations().add(methodDeclaration);
		unit.types().add(type);

