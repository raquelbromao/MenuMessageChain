package messagechainexe2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class Results extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Results frame = new Results();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Results() {
		setTitle("Message Chain Plugin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Message Chain Plugin");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(21, 11, 188, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select the class:");
		lblNewLabel_1.setBounds(21, 58, 86, 14);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboClasses = new JComboBox();
		comboClasses.setBounds(106, 55, 311, 20);
		contentPane.add(comboClasses);
		
		JButton btnApplyClass = new JButton("Apply");
		btnApplyClass.setBounds(424, 54, 69, 23);
		contentPane.add(btnApplyClass);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(424, 96, 69, 23);
		contentPane.add(btnClear);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(424, 123, 69, 23);
		contentPane.add(btnCancel);
		
		JTextArea results = new JTextArea();
		results.setBounds(21, 96, 396, 343);
		contentPane.add(results);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, lblNewLabel, lblNewLabel_1, comboClasses, btnApplyClass, btnClear, btnCancel, results}));
	}
}
