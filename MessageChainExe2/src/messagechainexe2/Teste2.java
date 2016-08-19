package messagechainexe2;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;

public class Teste2 extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text results;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Teste2(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(557, 500);
		shell.setText(getText());
		shell.setLayout(null);
		
		Label lblTexto = new Label(shell, SWT.NONE);
		lblTexto.setBounds(25, 10, 394, 25);
		lblTexto.setText("New Label");
		
		Combo comboProjects = new Combo(shell, SWT.NONE);
		comboProjects.setBounds(25, 27, 425, 23);
		
		Button applyProjects = new Button(shell, SWT.NONE);
		applyProjects.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		applyProjects.setBounds(456, 25, 75, 25);
		applyProjects.setText("Apply");
		
		Combo comboClasses = new Combo(shell, SWT.NONE);
		comboClasses.setBounds(25, 56, 425, 23);
		
		Button applyClass = new Button(shell, SWT.NONE);
		applyClass.setBounds(456, 56, 75, 25);
		applyClass.setText("Apply");
		
		results = new Text(shell, SWT.BORDER);
		results.setBounds(25, 95, 425, 369);
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setBounds(456, 93, 75, 25);
		btnNewButton_1.setText("Cancel");
		
		Button btnNewButton_2 = new Button(shell, SWT.NONE);
		btnNewButton_2.setBounds(456, 124, 75, 25);
		btnNewButton_2.setText("Clear");

	}
}
