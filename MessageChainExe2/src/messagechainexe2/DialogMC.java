package messagechainexe2;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import messagechainexe2.GetInfo;

public class DialogMC implements IWorkbenchWindowActionDelegate {
	/**
	 * vARIÁVEIS DA CLASSE
	 */
	private IWorkbenchWindow window;
	public Shell shlmessagechain;
	private static final String nol_ACTION = "plugin.splmetrics.actions.numberOfLines";

	/**
	 * O construtor da classe
	 * O comentário abaixo não pode ser retirado, pois indica o ponto de entrada do WBP
	 * WBP -> Windows Builder Plugin
	 * @wbp.parser.entryPoint
	 */
	public DialogMC() {
	}
	
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
	
	public void showSelectProjectDialog() {
		shlmessagechain = new Shell(getParent(), getStyle());
		shlmessagechain.setSize(451, 348);
		// NOME DA JANELA
		shlmessagechain.setText("Message Chain");

		// CAIXA DE TEXTO COM OS RESULTADOS
		results = new Text(shlmessagechain, SWT.BORDER);
		results.setBounds(37, 10, 370, 245);

		// BUTTON APPLY
		Button btnApply = new Button(shlmessagechain, SWT.NONE);
		btnApply.setBounds(37, 261, 75, 25);
		btnApply.setText("Apply");
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final GetInfo a = new GetInfo();
			}
		});

		// BUTTON CANCEL
		Button btnCancel = new Button(shlmessagechain, SWT.NONE);
		btnCancel.setBounds(118, 261, 75, 25);
		btnCancel.setText("Cancel");
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlmessagechain.close();
			}
		});		
	}
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void run(IAction action) {
		
		splMetricsAction spl = new splMetricsAction();
		spl.showSelectProjectDialog();
	
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 * @wbp.parser.entryPoint
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 * @wbp.parser.entryPoint
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}


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

}