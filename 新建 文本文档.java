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
//һ������ Java Ԫ�أ��Ϳ���ʹ�� JDT API �������Ͳ�ѯģ�͡������Բ�ѯ������ Java Ԫ���ڵķ� Java ��Դ��   
         private void createJavaElementsFrom(IProject myProject, IFolder myFolder, IFile myFile) {
        ...
        // get the non Java resources contained in my project.
        Object[] nonJavaChildren = myJavaProject.getNonJavaResources();����ͨ���� AST ʹ�ù�����������ͷ���� CompilationUnit����Щ�������� new... ��ͷ�������Ǵ��� HelloWorld ���һ��ʾ����
��һ������������ɵ������ 
	package example;
   import java.util.*;	
   public class HelloWorld {
      public static void main(String[] args) {
			System.out.println("Hello" + " world");
		}
	}
���д�����������������Ӧ���롣 
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

