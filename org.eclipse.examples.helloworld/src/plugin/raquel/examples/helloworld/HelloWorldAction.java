package plugin.raquel.examples.helloworld;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/** HelloWorldAction is a simple example of using an
 * action set to extend the Eclipse Workbench with a menu 
 * and toolbar action that prints the "Hello World" message.
 */
public class HelloWorldAction implements IWorkbenchWindowActionDelegate {
	IWorkbenchWindow activeWindow = null;
	public Shell shlSplMetricsSelect;
	private Text results;

	/** Run the action. Display the Hello World message
	 */		
	public void run(IAction proxyAction) {
		// proxyAction has UI information from manifest file (ignored)
		Shell shell = activeWindow.getShell();
		shlSplMetricsSelect = new Shell();
		shlSplMetricsSelect.setSize(450, 300);
		shlSplMetricsSelect.setText("Message Chain");
		shlSplMetricsSelect.setLayout(null);
		
		Label lblPleaseSelectThe = new Label(shlSplMetricsSelect, SWT.NONE);
		lblPleaseSelectThe.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblPleaseSelectThe.setBounds(25, 10, 394, 15);
		lblPleaseSelectThe.setText("Message Chain: all methods in workspace!");
		
		results = new Text(shlSplMetricsSelect, SWT.BORDER);
		results.setBounds(25, 38, 394, 156);
		
		Button btnApply = new Button(shlSplMetricsSelect, SWT.NONE);
		btnApply.setSelection(true);
		btnApply.setBounds(25, 214, 75, 25);
		btnApply.setText("Apply");
		
		Button btnCancel = new Button(shlSplMetricsSelect, SWT.NONE);
		btnCancel.setBounds(106, 214, 75, 25);
		btnCancel.setText("Cancel");
		shlSplMetricsSelect.pack();
		shlSplMetricsSelect.open();

		btnApply.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				results.setText("Teste/ OLAR/ BRUSINHA");
				//inserir resposta dos algoritmos
			}
		});

		btnCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shlSplMetricsSelect.close();
				dispose();
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