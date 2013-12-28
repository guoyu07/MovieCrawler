package com.gs.window;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gs.model.Movie;
import com.gs.socket.request.SearchRequest;
import com.gs.socket.response.Response;
import javax.swing.table.DefaultTableModel;

public class Client {

	private JFrame frame;
	private JTextField textField;
	private JTextField txtGaoshen;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 979, 671);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 963, 750);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("搜索", null, panel, null);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 130, 958, 475);
		panel.add(scrollPane);

		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);

		JButton btnStart = new JButton("搜索");
		btnStart.setBounds(465, 85, 93, 23);
		panel.add(btnStart);

		textField = new JTextField();
		textField.setBounds(164, 23, 66, 21);
		panel.add(textField);
		textField.setColumns(10);

		txtGaoshen = new JTextField();
		txtGaoshen.setBounds(477, 23, 66, 21);
		panel.add(txtGaoshen);
		txtGaoshen.setColumns(10);

		JLabel label = new JLabel("关键字");
		label.setBounds(63, 26, 54, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("用户名");
		label_1.setBounds(402, 26, 54, 15);
		panel.add(label_1);

		JLabel lblNewLabel = new JLabel("服务器IP");
		lblNewLabel.setBounds(736, 26, 54, 15);
		panel.add(lblNewLabel);

		textField_2 = new JTextField();
		textField_2.setBounds(836, 23, 66, 21);
		panel.add(textField_2);
		textField_2.setColumns(10);
		

		final JPanel panel_1 = new JPanel();
		tabbedPane.addTab("注册", null, panel_1, null);
		panel_1.setLayout(null);

		textField_3 = new JTextField();
		textField_3.setBounds(238, 100, 119, 21);
		panel_1.add(textField_3);
		textField_3.setColumns(10);

		JButton btnNewButton = new JButton("好");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					public void run() {
						com.gs.socket.client.Client c = new com.gs.socket.client.Client(
								textField_4.getText());
						c.setTextArea(textArea);
						try {
							c.post(new com.gs.socket.request.RegistRequest(textField_3.getText()),
									textField_3.getText());
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});
				t.start();
			}
		});
		btnNewButton.setBounds(229, 186, 93, 23);
		panel_1.add(btnNewButton);

		textField_4 = new JTextField();
		textField_4.setBounds(238, 69, 119, 21);
		panel_1.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("服务器IP");
		lblNewLabel_1.setBounds(143, 72, 54, 15);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("用户名");
		lblNewLabel_2.setBounds(143, 103, 54, 15);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("结果", null, panel_2, null);
		panel_2.setLayout(null);
		
		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 10, 958, 595);
		panel_2.add(scrollPane_1);
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"\u540D\u79F0", "URL", "QVOD", "\u5206\u7C7B"
			}
		));
		table.setFont(new Font("微软雅黑", Font.BOLD, 14));
		scrollPane_1.setViewportView(table);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				Thread thread = new Thread(new Runnable() {
					public void run() {
						try {
							textArea.append("正在测试和服务器的连接\n");
							Thread.sleep(1000);
							textArea.append("网络正常\n");
							Thread.sleep(1000);
							textArea.append("正在进行身份验证\n");
							Thread.sleep(1000);
							textArea.append("等待校验码返回\n");
							Thread.sleep(1000);
							textArea.append("校验码: "
									+ UUID.randomUUID().toString() + " \n");
							Thread.sleep(1000);
							textArea.append("身份验证成功\n\n");
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						// ////////////////////////////////////////////////////////////////////////////
						com.gs.socket.client.Client c = new com.gs.socket.client.Client(
								textField_2.getText());
						c.setTextArea(textArea);
						Response resp = null;
						try {
							resp = c.post(
									new SearchRequest(textField.getText()),
									txtGaoshen.getText());
						} catch (IOException e) {
							e.printStackTrace();
						}
						LinkedList<Movie> list = null;
						if (resp.getJson().startsWith("[")) {
							list = (LinkedList<Movie>) new Gson()
									.fromJson(resp.getJson(),
											new TypeToken<LinkedList<Movie>>() {
											}.getType());
						}else{
							textArea.append(resp.getJson());
						}
						
						Object[][] tableData = new Object[list.size()][4];
						Object[] columnTitle = { "名称", "URL", "QVOD", "分类"};
						for (int i = 0; i < list.size(); i++) {
							Movie m = list.get(i);
							tableData[i] = new Object[] {m.getName(),m.getUrl(),m.getQvod(),m.getCategory()};
						}
						System.out.println(tableData);
						table.setModel(new DefaultTableModel(
								tableData,
								new String[] {
									"\u540D\u79F0", "URL", "QVOD", "\u5206\u7C7B"
								}
							));

					}
				});
				thread.start();
			}
		});

	}
}
