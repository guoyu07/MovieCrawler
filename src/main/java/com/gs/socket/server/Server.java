package com.gs.socket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gs.searcher.Hit;
import com.gs.socket.Request;
import com.gs.socket.RequestProcesser;
import com.gs.socket.Response;

public class Server {
	private Logger logger = Logger.getLogger(this.getClass());
	public void start() throws JsonSyntaxException,IOException,Exception {
		ServerSocket ss = null;
		Socket socket = null;
		try {
			ss = new ServerSocket(8888);
			while (true) {
				// 服务器接收到客户端的数据后，创建与此客户端对话的Socket
				socket = ss.accept();
				// 用于向客户端发送数据的输出流
				DataOutputStream dos = new DataOutputStream(
						socket.getOutputStream());
				// 用于接收客户端发来的数据的输入流
				DataInputStream dis = new DataInputStream(
						socket.getInputStream());
				String json = dis.readUTF();
				logger.debug(json);
				System.out.println(json);
				try {
					System.out.println(RequestProcesser.getQueryString(new Gson().fromJson(json, Request.class)));
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
				// 服务器向客户端发送连接成功确认信息
				dos.writeUTF(new Gson().toJson(new Response(new HashSet<Hit>(),200)));
				// 不需要继续使用此连接时，关闭连接
				socket.close();
				// ss.close();
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
		}finally{
			socket.close();
			ss.close();
		}
	}
}
