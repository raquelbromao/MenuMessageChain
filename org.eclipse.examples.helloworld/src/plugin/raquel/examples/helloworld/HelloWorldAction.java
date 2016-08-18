package plugin.raquel.examples.helloworld;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.wb.swt.SWTResourceManager;

import plugin.raquel.examples.helloworld.ExpressionInvoke;

import java.util.regex.Pattern;

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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/** HelloWorldAction is a simple example of using an
 * action set to extend the Eclipse Workbench with a menu 
 * and toolbar action that prints the "Hello World" message.
 */
public class HelloWorldAction implements IWorkbenchWindowActionDelegate {
	IWorkbenchWindow activeWindow = null;
	public Shell shlMessageChain;
	private static Text results;

	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
	// private StructuralPropertyDescriptor property;

	public static void splitMessageChain(String s) {
		// retira o ";" do final da string
		s = s.replace(";", " ");

		// Quebra a variável quando acha . e armazena a sobra numa posição do
		// array aux
		// a().b() -> . é descartando e a() fica em aux[0] e b() em aux[1]
		String[] aux = s.split(Pattern.quote("."));

		// Pega o tamanho da string aux
		// Imprime a variável aux na tela
		results.append("Objeto: " + aux[0] + "\n");
		for (int i = 1; i < aux.length; i++) {
			results.append("Método[" + i + "]: " + aux[i] + "\n");
			System.out.println("Método[" + i + "]: " + aux[i] + "\n");
		}
		
		results.append("_______________________________________________________");
	}

	public static void verificaMessageChain(String s) {
		// verifica se a expressão coletada é igual ao regex criado
		// não foi usado [;] no final do regex pq o compilador nem lê se não houver ele no final
		if (s.matches("[\\w]+([\\.]+[\\w]+[(]+[)]){2,}")) {
			results.append("\nMessage Chain: " + s + "\n");
			splitMessageChain(s);
		} else {
			//System.out.println("Não é Message Chain: " + s + "\n");
		}
	}

	/*public static void testaStrings(String[] s) {
		for (int i = 0; i < s.length; i++) {
			verificaMessageChain(s[i]);
		}
	}*/

	public Object execute() throws ExecutionException {

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
		return null;
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
					results.append("####### INFORMAÇÕES DO EXPRESSION STATEMENT DO PROJETO "
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
				//System.out.println("ARGUMENTS: " + method.arguments());

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
	
	/** Run the action. Display the Hello World message
	 */		
	public void run(IAction proxyAction) {
		// proxyAction has UI information from manifest file (ignored)
		//Shell shell = activeWindow.getShell();
		shlMessageChain = new Shell();
		shlMessageChain.setSize(547, 500);
		shlMessageChain.setText("Message Chain");
		shlMessageChain.setLayout(null);
		
		Label lblPleaseSelectThe = new Label(shlMessageChain, SWT.NONE);
		lblPleaseSelectThe.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblPleaseSelectThe.setBounds(25, 10, 394, 15);
		lblPleaseSelectThe.setText("Message Chain: all methods in workspace!");
		
		results = new Text(shlMessageChain, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		results.setBounds(25, 38, 425, 400);
		
		Button btnApply = new Button(shlMessageChain, SWT.NONE);
		btnApply.setSelection(true);
		btnApply.setBounds(456, 36, 75, 25);
		btnApply.setText("Apply");
		
		Button btnCancel = new Button(shlMessageChain, SWT.NONE);
		btnCancel.setBounds(456, 64, 75, 25);
		btnCancel.setText("Cancel");
		shlMessageChain.pack();
		shlMessageChain.open();

		Button btnClear = new Button(shlMessageChain, SWT.NONE);
		btnClear.setBounds(456, 93, 75, 25);
		btnClear.setText("Clear");
		shlMessageChain.pack();
		shlMessageChain.open();
		
		btnApply.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					execute();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shlMessageChain.close();
				dispose();
			}
		});
		
		btnClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				results.setText("");
			}
		});
	}

	// IActionDelegate method
	public void selectionChanged(IAction proxyAction, ISelection selection) {
		// do nothing, action is not dependent on the selection
	}
	
	// IWorkbenchWindowActionDelegate method
	public void init(IWorkbenchWindow window) {
		activeWindow = window;
	}
	
	// IWorkbenchWindowActionDelegate method
	public void dispose() {
		//  nothing to do
	}
}