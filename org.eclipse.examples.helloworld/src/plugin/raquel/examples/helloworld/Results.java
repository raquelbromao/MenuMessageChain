package plugin.raquel.examples.helloworld;

import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
//import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class Results extends ApplicationWindow {
	private static Text results;
	static IPackageFragment[] packagesSelection;

	public static void Inicializa(IPackageFragment[] p) {
		packagesSelection = p;
	}
	
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
		}

		results.append("_______________________________________________________\n");
	}

	public static void verificaMessageChain(String s) {
		// verifica se a expressão coletada é igual ao regex criado
		// não foi usado [;] no final do regex pq o compilador nem lê se não
		// houver ele no final
		if (s.matches("[\\w]+([\\.]+[\\w]+[(]+[)]){2,}")) { // "[\\w]+([\\.]+[\\w]+[(]+[?\\w]+[)]){2,}")
			results.append("\nMessage Chain: " + s + "\n");
			splitMessageChain(s);
		} else {
			results.append("\nNão é Message Chain: " + s + "\n_______________________________________________________\n");
		}
	}

	private void analyseClass(ICompilationUnit classe) throws JavaModelException {
		// ICompilationUnit unit = classe;
		// now create the AST for the ICompilationUnits
		CompilationUnit parse = parse(classe);
		ExpressionInvoke visitor = new ExpressionInvoke();
		parse.accept(visitor);

		// Imprime na tela o nome do método e o tipo de retorno
		for (ExpressionStatement method : visitor.getMethods()) {
			String t = null;
			t = method.getExpression().toString();

			verificaMessageChain(t);
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


	/**
	 * Create the application window,
	 */
	public Results() {
		super(null);
		createActions();
		addCoolBar(SWT.FLAT);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		Combo comboClasses = new Combo(container, SWT.NONE);
		comboClasses.setBounds(107, 7, 387, 23);

		// Gera a lista de todas as classes do projeto selecionado
		// com o tipo IPackageFragment que obtenho todas as classes de um
		// projeto
		// IProject -> IPackageFragment -> ICompilationUnit -> arq.java
		try {
			for (IPackageFragment mypackage : packagesSelection) {
				for (final ICompilationUnit compilationUnit : mypackage.getCompilationUnits()) {
					comboClasses.add(compilationUnit.getElementName());
					// results.append("## PACKAGE NAME: " +
					// mypackage.getElementName() + "\n");
					// results.append("## [CLASSE] COMPILATION UNIT NAME: " +
					// compilationUnit.getElementName() + "\n");
					// classSelection = compilationUnit;
					// analyseClass(compilationUnit);
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// deixa a primeira classe encontrada visível por default no combo
		comboClasses.select(0);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 91, 15);
		lblNewLabel.setText("Select the class:");

		Button btnApplyClass = new Button(container, SWT.NONE);
		btnApplyClass.setBounds(500, 7, 75, 25);
		btnApplyClass.setText("Apply");

		results = new Text(container, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		results.setBounds(10, 49, 484, 334);

		Button btnCancel = new Button(container, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setBounds(500, 80, 75, 25);

		Button btnClear = new Button(container, SWT.NONE);
		btnClear.setText("Clear");
		btnClear.setBounds(500, 49, 75, 25);
		
		btnApplyClass.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					// LIMPA A JANELA DOS RESULTADOS QUANDO SELECIONADO UMA NOVA CLASSE
					results.setText("");
					String nameClass = comboClasses.getItem(comboClasses.getSelectionIndex());

					for (IPackageFragment mypackage : packagesSelection) {
						for (final ICompilationUnit compilationUnit : mypackage.getCompilationUnits()) {
							String aux = compilationUnit.getElementName();
							if (aux.equals(nameClass)) {
								analyseClass(compilationUnit);
							}
						}
					}

				} catch (JavaModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		btnCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				close();
			}
		});

		btnClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				results.setText("");
			}
		});

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[], IPackageFragment[] p) {
		try {
			Results window = new Results();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Inicializa(p);
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Message Chain Plugin");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(597, 436);
	}
}
