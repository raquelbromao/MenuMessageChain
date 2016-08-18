package plugin.raquel.examples.helloworld;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.wb.swt.SWTResourceManager;

import java.util.regex.Pattern;

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

	public static final String[] testeErro = {"objeto", ".objeto", "objeto.", "objeto;",
			"objeto.function", "objeto.function;", ";objeto.function", "objeto.;function", "objeto;.function",
			"objeto()", ".objeto()", "objeto.()", "objeto().",
			"objeto().function", "objeto.()function","()objeto.function",
			"objeto.function()","objeto.function().", 
			"a.somadiferente().subdiferente().multdiferente().raizdiferente()",
			"objeto.function().function2", "objeto.function().function2.", "objeto.function().function2.;"};
	
	public static final String[] testeValido = {"objeto.function().function2();",
			"objeto.function().function2().function3();",
			"objeto.function().function2().function3().function4();", 
			"objeto.function().function2().function3().function4().function5();",
			"a.somadiferente().subdiferente().multdiferente().raizdiferente();"};
	
	public static void splitMessageChain (String s) {
		// retira o ";" do final da string		
		s = s.replace(";", " ");
		
		// Quebra a variável quando acha . e armazena a sobra numa posição do array aux
		// a().b() -> . é descartando e a() fica em aux[0] e b() em aux[1]
		String[] aux = s.split(Pattern.quote("."));		

		// Pega o tamanho da string aux
		// Imprime a variável aux na tela
		results.append("\nObjeto: " + aux[0]+"\n");	
		for (int i = 1; i < aux.length; i++) {
			results.append("Método[" + i + "]: " + aux[i]+"\n");
		}		
	}
	
	public static void verificaMessageChain (String s) {		
		if (s!=null && s.matches("[\\w]+([\\.]+[\\w]+[(]+[)]){2,}+[;]")) {
			results.append("\nÉ Message Chain para "+s+"\n");
			splitMessageChain(s);
		} else {
			results.append("\nNão é Message Chain para "+s+"\n");
		}
	}
	
	public static void testaStrings (String[] s) {
		for (int i = 0; i<s.length; i++) {
			verificaMessageChain(s[i]);
		}
	}

	public static void execute() {
		testaStrings(testeErro);		
		results.append("\n#####################################################\n");
		testaStrings(testeValido);
	}
	
	/** Run the action. Display the Hello World message
	 */		
	public void run(IAction proxyAction) {
		// proxyAction has UI information from manifest file (ignored)
		Shell shell = activeWindow.getShell();
		shlMessageChain = new Shell();
		shlMessageChain.setSize(547, 446);
		shlMessageChain.setText("Message Chain");
		shlMessageChain.setLayout(null);
		
		Label lblPleaseSelectThe = new Label(shlMessageChain, SWT.NONE);
		lblPleaseSelectThe.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblPleaseSelectThe.setBounds(25, 10, 394, 15);
		lblPleaseSelectThe.setText("Message Chain: all methods in workspace!");
		
		results = new Text(shlMessageChain, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		results.setBounds(25, 38, 394, 156);
		
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
				execute();
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