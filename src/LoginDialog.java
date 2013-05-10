import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class LoginDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUni;
	private JPasswordField pwdPassword;
	private String password;
	private String uni;
	private JButton loginButton;
	private JLabel lblWrongUniOr;

	/**
	 * Create the dialog.
	 */
	public LoginDialog() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(100, 100, 320, 152);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUni = new JLabel("UNI:");
			GridBagConstraints gbc_lblUni = new GridBagConstraints();
			gbc_lblUni.insets = new Insets(0, 0, 5, 5);
			gbc_lblUni.anchor = GridBagConstraints.EAST;
			gbc_lblUni.gridx = 0;
			gbc_lblUni.gridy = 0;
			contentPanel.add(lblUni, gbc_lblUni);
		}
		{
			txtUni = new JTextField();
			txtUni.setText("abc1234");
			GridBagConstraints gbc_txtUni = new GridBagConstraints();
			gbc_txtUni.insets = new Insets(0, 0, 5, 0);
			gbc_txtUni.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUni.gridx = 1;
			gbc_txtUni.gridy = 0;
			contentPanel.add(txtUni, gbc_txtUni);
			txtUni.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password:");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 1;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			pwdPassword = new JPasswordField();
			pwdPassword.setText("Password");
			GridBagConstraints gbc_pwdPassword = new GridBagConstraints();
			gbc_pwdPassword.fill = GridBagConstraints.HORIZONTAL;
			gbc_pwdPassword.gridx = 1;
			gbc_pwdPassword.gridy = 1;
			contentPanel.add(pwdPassword, gbc_pwdPassword);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				lblWrongUniOr = new JLabel("Wrong UNI or Password. Try Again.");
				buttonPane.add(lblWrongUniOr);
			}
			{
				loginButton = new JButton("Login");
				loginButton.setActionCommand("OK");
				buttonPane.add(loginButton);
				getRootPane().setDefaultButton(loginButton);
			}
		}

		lblWrongUniOr.setVisible(false);

		ButtonListener buttonListener = new ButtonListener();
		loginButton.addActionListener(buttonListener);
	}

	/**
	 * returns uni
	 * 
	 * @return uni the uni the user entered
	 */
	public String getUni() {
		return uni;
	}

	/**
	 * returns the password
	 * 
	 * @return password the password the user entered
	 */
	public String getPassword() {
		return password;
	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == loginButton) {

				LoginDialog.this.uni = txtUni.getText();
				LoginDialog.this.password = String.valueOf(pwdPassword
						.getPassword());
				System.out.println("LoginDialog: Trying combination "
						+ LoginDialog.this.uni + " "
						+ LoginDialog.this.password);

				WebDriver driverDialog = new HtmlUnitDriver(true);
				driverDialog.get("https://ssol.columbia.edu");

				// u_idField is the field for username
				WebElement u_idField = driverDialog.findElement(By
						.cssSelector("input[name=u_id]"));

				// u_pwField is the field for password
				WebElement u_pwField = driverDialog.findElement(By
						.cssSelector("input[name=u_pw]"));

				// sends username and password over and submits username and
				// password
				u_idField.sendKeys(LoginDialog.this.uni);
				u_pwField.sendKeys(LoginDialog.this.password);
				u_pwField.submit();

				try {
					WebElement scheduleLink = driverDialog.findElement(By
							.linkText("Student Schedule"));
					System.out
							.println("LoginDialog: Correct username and password");
					lblWrongUniOr.setVisible(false);
					LoginDialog.this.setVisible(false);
					driverDialog.close();
				} catch (NoSuchElementException e) {
					System.out
							.println("LoginDialog: Wrong username / password");
					lblWrongUniOr.setVisible(true);
				}

			}

		}
	}
}
