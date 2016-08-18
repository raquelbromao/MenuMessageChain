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
		lblTexto.setBounds(25, 10, 394, 15);
		lblTexto.setText("New Label");
		
		results = new Text(shell, SWT.BORDER);
		results.setBounds(25, 38, 425, 369);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setBounds(456, 36, 75, 25);
		btnNewButton.setText("Apply");
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setBounds(456, 64, 75, 25);
		btnNewButton_1.setText("Cancel");
		
		Button btnNewButton_2 = new Button(shell, SWT.NONE);
		btnNewButton_2.setBounds(456, 93, 75, 25);
		btnNewButton_2.setText("Clear");

	}
}
