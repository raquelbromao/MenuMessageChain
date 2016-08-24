package plugin.raquel.examples.helloworld;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;

import org.eclipse.swt.SWT;
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
	private Text text;
	IPackageFragment[] packagesSelection;
	
	public void recebeDados(IPackageFragment[] p) {
		packagesSelection = p;
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
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		Combo combo = new Combo(container, SWT.NONE);
		combo.setBounds(107, 7, 387, 23);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 91, 15);
		lblNewLabel.setText("Select the class:");
		
		Button btnApplyClass = new Button(container, SWT.NONE);
		btnApplyClass.setBounds(500, 7, 75, 25);
		btnApplyClass.setText("Apply");
		
		text = new Text(container, SWT.BORDER);
		text.setBounds(10, 49, 484, 334);
		
		Button btnCancel = new Button(container, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setBounds(500, 80, 75, 25);
		
		Button btnClear = new Button(container, SWT.NONE);
		btnClear.setText("Clear");
		btnClear.setBounds(500, 49, 75, 25);

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
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Results window = new Results();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
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
