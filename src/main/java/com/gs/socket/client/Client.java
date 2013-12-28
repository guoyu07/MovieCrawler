package com.gs.socket.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.gs.socket.request.Request;
import com.gs.socket.request.RequestDTOProcesser;
import com.gs.socket.request.RequestProperty;
import com.gs.socket.response.Response;

public class Client {
	private Logger logger = Logger.getLogger(this.getClass());
	private JTextArea textArea;
	private String serverIP = "localhost";
	public Client(String serverIP) {
		this.serverIP = serverIP;
	}
	
	public Client() {
	}

	public Response post(Request req,String username) throws IOException {
		Socket socket = null;
		Response resp = null;
		try {
			socket = new Socket(serverIP, 8888);
			// 获取输出流，用于客户端向服务器端发送数据
			textArea.append("正在与服务器建立通信,请稍候\n");
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			// 获取输入流，用于接收服务器端发送来的数据
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			// 客户端向服务器端发送数据
			textArea.append("正在向服务器发送请求\n");
			dos.writeUTF(new Gson().toJson(RequestDTOProcesser.pack(req, new RequestProperty(getCPUID()+getMotherboardSN(), username))));
			// 打印出从服务器端接收到的数据
			textArea.append("准备接受数据\n");
			resp = new Gson().fromJson(dis.readUTF(), Response.class);
			// 不需要继续使用此连接时，记得关闭哦
			socket.close();
			textArea.append("关闭与服务器的通信\n");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return resp;
	}
	
	public String getCPUID(){
		Process process;
		Scanner sc = null;
		try {
			process = Runtime.getRuntime().exec(
					new String[] { "wmic", "cpu", "get", "ProcessorId" });
			process.getOutputStream().close();
			sc = new Scanner(process.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		sc.next();
		String serial = sc.next();
		sc.close();
		return serial;
	}
	
	public static String getMotherboardSN() {
		  String result = "";
		    try {
		      File file = File.createTempFile("realhowto",".vbs");
		      file.deleteOnExit();
		      FileWriter fw = new java.io.FileWriter(file);
		      String vbs =
		         "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
		        + "Set colItems = objWMIService.ExecQuery _ \n"
		        + "   (\"Select * from Win32_BaseBoard\") \n"
		        + "For Each objItem in colItems \n"
		        + "    Wscript.Echo objItem.SerialNumber \n"
		        + "    exit for  ' do the first cpu only! \n"
		        + "Next \n";
		      fw.write(vbs);
		      fw.close();
		      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
		      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		      String line;
		      while ((line = input.readLine()) != null) {
		         result += line;
		      }
		      input.close();
		    }
		    catch(Exception e){
		        e.printStackTrace();
		    }
		    return result.trim();
		  }

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	
}
