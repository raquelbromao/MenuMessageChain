package messagechainexe2;

import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;

/**
 * 
 * @author Romao
 * @date 29/07/16
 * @time 15:04
 * 
 *       Pega as informações dos métodos de TODAS as classes do projeto
 *       principal aberto na workspace Pega o nome do método e o seu tipo de
 *       retorno
 *
 */

public class GetInfo {
	
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
	// private StructuralPropertyDescriptor property;

	public static void splitMessageChain(String s) {
		// retira o ";" do final da string
		//String[][] aux;
		s = s.replace(";", " ");

		// Quebra a variável quando acha . e armazena a sobra numa posição do
		// array aux
		// a().b() -> . é descartado e a() fica em aux[0] e b() em aux[1]
		String[] aux = s.split(Pattern.quote("."));

		// Pega o tamanho da string aux
		// Imprime a variável aux na tela
		System.out.println("Objeto: " + aux[0]);
		for (int i = 1; i < aux.length; i++) {
			System.out.println("Método[" + i + "]: " + aux[i]);
		}
		
		System.out.println("________________________________________________________________________________");
	}

	public static void verificaMessageChain(String s) {
		// verifica se a expressão coletada é igual ao regex criado
		// não foi usado [;] no final do regex pq o compilador nem lê se não houver ele no final
		if (s.matches("[\\w]+([\\.]+[\\w]+[(]+[)]){2,}")) {
			System.out.println("\nMessage Chain: " + s + "\n");
			splitMessageChain(s);
		} else {
			System.out.println("Não é Message Chain: " + s + "\n");
		}
	}

	public void execute() {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();

		// Pega todos os projetos na workspace
		IProject[] projects = root.getProjects();

		// Faz um loop sobre todos os projetos e executa o método analyseMethods
		for (IProject project : projects) {
			try {
				if (project.isNatureEnabled(JDT_NATURE)) {
					analyseMethods(project);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	/*
	 * private void analyseClass (IProject project) { //IProject class = root. }
	 * 
	 */
	private void analyseMethods(IProject project) throws JavaModelException {
		IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();
		// parse(JavaCore.create(project));
		for (IPackageFragment mypackage : packages) {
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				// System.out.println("####### INFORMAÇÕES DO METHOD DECLARATION
				// DO PROJETO " + mypackage.getElementName());
				// createASTmethod(mypackage);
				if (mypackage.getElementName() != null) {
					System.out.println("####### INFORMAÇÕES DO EXPRESSION STATEMENT DO PROJETO "
							+ mypackage.getElementName() + " ########\n");
				}
				createASTInvocation(mypackage);
			}
		}
	}


	private void createASTInvocation(IPackageFragment mypackage) throws JavaModelException {
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			// now create the AST for the ICompilationUnits
			CompilationUnit parse = parse(unit);
			ExpressionInvoke visitor = new ExpressionInvoke();
			parse.accept(visitor);

			// Imprime na tela o nome do método e o tipo de retorno
			for (ExpressionStatement method : visitor.getMethods()) {
				//System.out.println("\n\nNAME: " + method.getExpression());
				//System.out.println("PARENT: " + method.getParent());
				System.out.println("method.getRoot(): " + method.getRoot());

				// Converter o method.getParent() em string e avalia se é Message Chain
				String t = null;
				t = method.getExpression().toString();

				verificaMessageChain(t);
			}
		}
	}

	/**
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the
	 * Java source file
	 *
	 * @param unit
	 * @return
	 */
	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}
}