package com.gs.window;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.gs.model.Movie;
import com.gs.socket.request.RecommendRequest;
import com.gs.socket.request.SearchRequest;
import com.gs.socket.response.Response;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSlider;

public class Client implements ChangeListener{

	private JFrame frame;
	private JTextField textField;
	private JTable table;
	private static String serverIP;
	private static String username;
	private JTextArea textArea;
	private JTable table_1;
	private JSlider slider;
	private JLabel lblNewLabel_5;

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
		new SplashWindow3("欢迎"+username, frame, 3000); 
		frame = new JFrame();
		frame.setBounds(100, 100, 1186, 721);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setAlwaysOnTop(true);
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
		btnStart.setFont(new Font("微软雅黑", Font.BOLD, 12));
		btnStart.setBounds(449, 40, 147, 23);
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
									new SearchRequest(textField.getText(),slider.getValue()),
									username);
						} catch (IOException e) {
							textArea.append(e.getMessage());
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
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {// 仅当鼠标单击时响应
				// 得到选中的行列的索引值
				int r = table.getSelectedRow();
				int c = table.getSelectedColumn();
				// 得到选中的单元格的值，表格中都是字符串
				Object value = table.getValueAt(r, c);
				StringSelection ss = new StringSelection(value.toString());
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(ss, null);
				javax.swing.JOptionPane.showMessageDialog(null,
						value.toString() + "已复制到剪贴板");
				if (value.toString().startsWith("www")) {
					runBroswer(value.toString());
				}
			}
		});
		table.setColumnSelectionAllowed(true);
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null }, }, new String[] { "\u540D\u79F0", "URL", "QVOD",
				"\u5206\u7C7B" }));
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
		try {
			recommend = null;
			com.gs.socket.client.Client cc = new com.gs.socket.client.Client(
					serverIP);
			cc.setTextArea(new JTextArea());
			recommend = (Set<Movie>) new Gson().fromJson(
					cc.post(new RecommendRequest(), username).getJson(),
					new TypeToken<Set<Movie>>() {
					}.getType());
		} catch (JsonSyntaxException e) {
			textArea.append(e.getMessage());
			e.printStackTrace();
		}catch(ConnectException e){
			JOptionPane.showMessageDialog(null, "未开启服务器");
			textArea.append(e.getMessage());
		}catch (IOException e) {
			textArea.append(e.getMessage());
			e.printStackTrace();
		}
		Object[][] recommendData = new Object[recommend.size()][4];
		int i = 0;
		for (Movie m : recommend) {
			recommendData[i++] = new Object[] { m.getName(), m.getUrl(),
					m.getQvod(), m.getCategory() };
		}
		table_1.setModel(new DefaultTableModel(recommendData, new String[] {
				"\u540D\u79F0", "URL", "QVOD", "\u5206\u7C7B" }));
		table_1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {// 仅当鼠标单击时响应
				// 得到选中的行列的索引值
				int r = table_1.getSelectedRow();
				int c = table_1.getSelectedColumn();
				// 得到选中的单元格的值，表格中都是字符串
				Object value = table_1.getValueAt(r, c);
				StringSelection ss = new StringSelection(value.toString());
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(ss, null);
				javax.swing.JOptionPane.showMessageDialog(null,
						value.toString() + "已复制到剪贴板");
				if (value.toString().startsWith("www")) {
					runBroswer(value.toString());
				}
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
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(787, Short.MAX_VALUE))
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
		
		JLabel lblTips = new JLabel("Tips:点击单元格即可复制");
		lblTips.setFont(new Font("微软雅黑", Font.BOLD, 14));
		lblTips.setBounds(969, 43, 191, 15);
		panel_1.add(lblTips);
		
		JLabel lblNewLabel = new JLabel("用户: "+username);
		lblNewLabel.setBounds(10, 0, 139, 15);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("服务器: "+serverIP);
		lblNewLabel_1.setBounds(10, 15, 126, 15);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("操作系统: 基于"+System.getProperty("os.arch", "")+"架构的"+System.getProperty("os.name", "")+"系统 版本号"+System.getProperty("os.version", ""));
		lblNewLabel_2.setBounds(10, 30, 306, 15);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("当前操作系统用户为: "+System.getProperty("user.name", ""));
		lblNewLabel_3.setBounds(10, 43, 178, 15);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("本次登录校验码:"+UUID.randomUUID().toString());
		lblNewLabel_4.setBounds(702, 10, 334, 15);
		panel_1.add(lblNewLabel_4);
		
		slider = new JSlider();
		slider.setMinimum (0);//设置最小值
		slider.setMaximum (100);//设置最大值
		slider.setPaintLabels (true);//Default:false显示标签
		slider.setPaintTicks (true);//Default:false显示标号
		slider.setBounds(604, 37, 179, 23);
		slider.addChangeListener(this);
		panel_1.add(slider);
		
		lblNewLabel_5 = new JLabel("最大个数为:"+String.valueOf(slider.getValue()));
		lblNewLabel_5.setBounds(807, 30, 152, 15);
		panel_1.add(lblNewLabel_5);
		frame.getContentPane().setLayout(groupLayout);

	}
	
	public static void runBroswer(String webSite) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = new URI(webSite);
                desktop.browse(uri);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

	@Override
	public void stateChanged(ChangeEvent arg0) {
		lblNewLabel_5.setText("最大个数为:"+String.valueOf(slider.getValue()));
		
	}
}
