import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;


public class AboutDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setBounds(100, 100, 271, 147);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JLabel lblMadeByXingzhou = new JLabel("Made by Xingzhou He and Linan Qiu");
			lblMadeByXingzhou.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblMadeByXingzhou);
		}
		
		JLabel lblWithHelpFrom = new JLabel("With Help from Swapneel and Morris");
		lblWithHelpFrom.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblWithHelpFrom);
		
		JLabel lblSsolbot = new JLabel("SSOLBot");
		lblSsolbot.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblSsolbot);
	}

}
