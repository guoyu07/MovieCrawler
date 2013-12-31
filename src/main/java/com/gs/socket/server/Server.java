package com.gs.socket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gs.DAO.MovieDAO;
import com.gs.DAO.UserDAO;
import com.gs.luceneComponent.Searcher;
import com.gs.model.Movie;
import com.gs.socket.request.RegistRequest;
import com.gs.socket.request.RequestDTO;
import com.gs.socket.request.RequestDTOProcesser;
import com.gs.socket.request.SearchRequest;
import com.gs.socket.request.TestRequest;
import com.gs.socket.response.Response;

public class Server {
	private Logger logger = Logger.getLogger(this.getClass());
	private JTextArea textArea;
	private static final Searcher searcher = new Searcher();
	private ServerSocket ss = null;
	private MovieDAO dao = new MovieDAO();
	private UserDAO userDAO = new UserDAO();

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public void start() throws JsonSyntaxException, IOException, Exception {
		Socket socket = null;
		try {
			ss = new ServerSocket(8888);
			while (true) {
				// 服务器接收到客户端的数据后，创建与此客户端对话的Socket
				try {
					socket = ss.accept();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// 用于向客户端发送数据的输出流
				textArea.append(socket.getInetAddress().getHostAddress()
						+ "已连接\n");
				DataOutputStream dos = new DataOutputStream(
						socket.getOutputStream());
				// 用于接收客户端发来的数据的输入流
				DataInputStream dis = new DataInputStream(
						socket.getInputStream());
				String json = dis.readUTF();// 接收客户端传过来的Json格式的RequestDTO
				RequestDTO dto = null;
				SearchRequest searchreq = null;
				TestRequest testreq = null;
				RegistRequest registreq = null;
				try {
					dto = new Gson().fromJson(json, RequestDTO.class);// 从Json格式转换成一个response实例
					textArea.append("CPUID: " + dto.getProperty().getCpuid()
							+ "  Username: " + dto.getProperty().getUsername()
							+ "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (dto.getJsonClassName().equals(
						"com.gs.socket.request.SearchRequest")) {
					try {
						searchreq = (SearchRequest) RequestDTOProcesser
								.unpack(dto);
						textArea.append(searchreq.toString()+"\n");
					} catch (Exception e) {
						String err = e.getMessage() + " 用户名:"
								+ dto.getProperty().getUsername() + " IP:"
								+ socket.getInetAddress().getHostAddress()
								+ " Port:" + socket.getPort() + "\n";
						logger.error(err);
						textArea.append(err);
						dos.writeUTF(new Gson().toJson(new Response("Forbiden",
								403)));
						continue;
					}
					LinkedList<Movie> list = new LinkedList<Movie>();
					for (Movie m : searcher.search(searchreq.getQueryString(),
							searchreq.getMax())) {
						list.add(dao.getMovie(m.getUrl()));
					}
					dos.writeUTF(new Gson().toJson(new Response(new Gson()
							.toJson(list), 200)));
				} else if (dto.getJsonClassName().equals(
						"com.gs.socket.request.RegistRequest")) {
					try {
						registreq = (RegistRequest) RequestDTOProcesser
								.unpack(dto);
					} catch (Exception e) {
						e.printStackTrace();
					}
					textArea.append("注册申请  用户名:"
							+ dto.getProperty().getUsername() + " IP:"
							+ socket.getInetAddress().getHostAddress()
							+ " Port:" + socket.getPort() + "\n");
					dos.writeUTF(new Gson().toJson(new Response("请等待服务器通过注册申请",
							403)));
				}else if(dto.getJsonClassName().equals(
						"com.gs.socket.request.RecommendRequest")){
					textArea.append(dto.getProperty().getUsername()+"每日推荐");
					dos.writeUTF(new Gson().toJson(new Response(new Gson()
					.toJson(dao.getRadomMovies()), 200)));
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (JsonSyntaxException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			socket.close();
			ss.close();
		}
	}
	
	public void stop(){
		try {
			ss.close();
		} catch (IOException e) {
		}
	}
}
