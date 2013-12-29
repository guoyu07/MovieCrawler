package com.gs.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.JLabel;

import com.gs.socket.request.SearchRequest;
import com.gs.socket.response.Response;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private static Login dialog = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new Login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			textField.setBounds(150, 39, 66, 21);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			textField_1 = new JTextField();
			textField_1.setBounds(150, 113, 66, 21);
			contentPanel.add(textField_1);
			textField_1.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("服务器IP");
			lblNewLabel.setBounds(63, 42, 54, 15);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("用户名");
			lblNewLabel_1.setBounds(63, 116, 54, 15);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								JTextArea textArea = new JTextArea();
								com.gs.socket.client.Client c = new com.gs.socket.client.Client(
										textField.getText());
								c.setTextArea(textArea);
								Response resp = null;
								try {
									resp = c.post(new SearchRequest("Login"),
											textField_1.getText());
								} catch (IOException e) {
									e.printStackTrace();
								}
								if (resp.getStatusCode() == 200) {
									Thread t = new Thread(new Runnable() {
										@Override
										public void run() {
											com.gs.window.Client
													.main(new String[] {
															textField.getText(),
															textField_1
																	.getText() });
										}
									});
									t.start();

									dialog.setVisible(false);
								} else {
									JOptionPane.showMessageDialog(null,
											"无法通过身份验证,请重试");
									System.exit(ERROR);
								}

							}
						});
						thread.start();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(ABORT);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton button = new JButton("注册");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						com.gs.window.Regist.main(null);
					}
				});
				buttonPane.add(button);
			}
		}
	}

}
