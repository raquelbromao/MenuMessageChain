package messagechainexe2;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import messagechainexe2.GetInfo;

public class DialogMC extends Dialog {

	protected Object result;
	protected Shell shlMessageChain;
	private Text results;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DialogMC(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlMessageChain.open();
		shlMessageChain.layout();
		Display display = getParent().getDisplay();
		while (!shlMessageChain.isDisposed()) {
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
		shlMessageChain = new Shell(getParent(), getStyle());
		shlMessageChain.setSize(451, 348);
		// NOME DA JANELA
		shlMessageChain.setText("Message Chain");

		// CAIXA DE TEXTO COM OS RESULTADOS
		results = new Text(shlMessageChain, SWT.BORDER);
		results.setBounds(37, 10, 370, 245);

		// BUTTON APPLY
		Button btnApply = new Button(shlMessageChain, SWT.NONE);
		btnApply.setBounds(37, 261, 75, 25);
		btnApply.setText("Apply");
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final GetInfo a = new GetInfo();
			}
		});

		// BUTTON CANCEL
		Button btnCancel = new Button(shlMessageChain, SWT.NONE);
		btnCancel.setBounds(118, 261, 75, 25);
		btnCancel.setText("Cancel");
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlMessageChain.close();
			}
		});

	}
}