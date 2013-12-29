package com.gs.window;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;
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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.gs.DAO.MovieDAO;
import com.gs.model.Movie;
import com.gs.socket.request.RecommendRequest;
import com.gs.socket.request.SearchRequest;
import com.gs.socket.response.Response;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Client {

	private JFrame frame;
	private JTextField textField;
	private JTable table;
	private static String serverIP;
	private static String username;
	private JTextArea textArea;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client(args[0], args[1]);
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
	public Client(String serverIP, String username) {
		initialize(serverIP, username);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final String serverIP, final String username) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1186, 721);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);

		JLabel label = new JLabel("关键字");
		label.setBounds(362, 10, 54, 15);
		panel_1.add(label);

		textField = new JTextField();
		textField.setBounds(477, 7, 191, 21);
		panel_1.add(textField);
		textField.setColumns(10);

		JButton btnStart = new JButton("搜索");
		btnStart.setBounds(464, 38, 93, 23);
		panel_1.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				Thread thread = new Thread(new Runnable() {
					public void run() {
						com.gs.socket.client.Client c = new com.gs.socket.client.Client(
								serverIP);
						c.setTextArea(textArea);
						Response resp = null;
						try {
							resp = c.post(
									new SearchRequest(textField.getText()),
									username);
						} catch (IOException e) {
							e.printStackTrace();
						}
						LinkedList<Movie> list = null;
						if (resp.getJson().startsWith("[")) {
							list = (LinkedList<Movie>) new Gson().fromJson(
									resp.getJson(),
									new TypeToken<LinkedList<Movie>>() {
									}.getType());
						} else {
							textArea.append(resp.getJson());
						}

						Object[][] tableData = new Object[list.size()][4];
						Object[] columnTitle = { "名称", "URL", "QVOD", "分类" };
						for (int i = 0; i < list.size(); i++) {
							Movie m = list.get(i);
							tableData[i] = new Object[] { m.getName(),
									m.getUrl(), m.getQvod(), m.getCategory() };
						}
						table.setModel(new DefaultTableModel(tableData,
								new String[] { "\u540D\u79F0", "URL", "QVOD",
										"\u5206\u7C7B" }));
						textArea.append("OK!");

					}
				});
				thread.start();
			}
		});

		final JScrollPane scrollPane_1 = new JScrollPane();
		table = new JTable();
		table.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
               //得到选中的行列的索引值
              int r= table.getSelectedRow();
              int c= table.getSelectedColumn();
              //得到选中的单元格的值，表格中都是字符串
              Object value= table.getValueAt(r, c);
           StringSelection ss = new StringSelection(value.toString());
           Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
           javax.swing.JOptionPane.showMessageDialog(null,value.toString()+"已复制到剪贴板");
            }
        });
		table.setColumnSelectionAllowed(true);
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null },
		}, new String[] { "\u540D\u79F0", "URL", "QVOD", "\u5206\u7C7B" }));
		table.setFont(new Font("微软雅黑", Font.BOLD, 14));
		scrollPane_1.setViewportView(table);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 383, 285);
		panel_3.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		table.setFont(new Font("微软雅黑", Font.BOLD, 14));

		JScrollPane scrollPane_2 = new JScrollPane();

		table_1 = new JTable();
		table_1.setColumnSelectionAllowed(true);
		Set<Movie> recommend = null;
		System.out.println(serverIP+username);
		try {
			recommend = null;
			com.gs.socket.client.Client cc = new com.gs.socket.client.Client(serverIP);
			cc.setTextArea(new JTextArea());
			recommend = (Set<Movie>) new Gson().fromJson(
					cc.post(new RecommendRequest(), username).getJson(),
					new TypeToken<Set<Movie>>() {
					}.getType());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object[][] recommendData = new Object[recommend.size()][4];
		int i = 0;
		for (Movie m : recommend) {
			recommendData[i++] = new Object[] { m.getName(),
					m.getUrl(), m.getQvod(), m.getCategory() };
		}
			table_1.setModel(new DefaultTableModel(recommendData,
			 new String[] { "\u540D\u79F0", "URL", "QVOD", "\u5206\u7C7B" }));
			table_1.addMouseListener(new java.awt.event.MouseAdapter(){
	            public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
	               //得到选中的行列的索引值
	              int r= table_1.getSelectedRow();
	              int c= table_1.getSelectedColumn();
	              //得到选中的单元格的值，表格中都是字符串
	              Object value= table_1.getValueAt(r, c);
	           StringSelection ss = new StringSelection(value.toString());
	           Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	           javax.swing.JOptionPane.showMessageDialog(null,value.toString()+"已复制到剪贴板");
	            }
	        });
		table_1.setFont(new Font("微软雅黑", Font.BOLD, 14));
		scrollPane_2.setViewportView(table_1);

		JLabel label_1 = new JLabel("每日推荐");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 1170, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(382)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
					.addGap(787))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
					.addGap(787))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(159)
					.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
					.addGap(957))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
					.addGap(1)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(307)
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(285)
							.addComponent(label_1))))
		);
		frame.getContentPane().setLayout(groupLayout);

	}
}
