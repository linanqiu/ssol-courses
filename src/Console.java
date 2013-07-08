import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class Console extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea textAreaConsole;

	/**
	 * Create the dialog.
	 */
	public Console() {
		setTitle("Log Messages");

		// redirectSystemStreams();

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				textAreaConsole = new JTextArea();
				scrollPane.setViewportView(textAreaConsole);
			}
		}

	}

	private void redirectSystemStreams() {
		OutputStream out = new ConsoleOutputStream();

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}

	private class CloseButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Console.this.setVisible(false);
		}

	}

	private class ConsoleOutputStream extends OutputStream {

		private void updateTextArea(String text) {
			SwingUtilities.invokeLater(new updateTextRunnable(text));
		}

		@Override
		public void write(int arg0) throws IOException {
			updateTextArea(String.valueOf((char) arg0));
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			updateTextArea(new String(b, off, len));
		}

		@Override
		public void write(byte[] b) throws IOException {
			write(b, 0, b.length);
		}

		private class updateTextRunnable implements Runnable {

			String text;

			public updateTextRunnable(String text) {
				this.text = text;
			}

			@Override
			public void run() {
				textAreaConsole.append(text);
			}

		}

	}

}
