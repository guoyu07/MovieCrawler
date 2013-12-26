package com.gs.socket;


import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import com.gs.socket.client.Client;
import com.gs.socket.request.SearchRequset;

public class TestClient {

	@Test
	public void testID() throws IOException{
		Process process = Runtime.getRuntime().exec(
				new String[] { "wmic", "cpu", "get", "ProcessorId" });
		process.getOutputStream().close();
		Scanner sc = new Scanner(process.getInputStream());
		String property = sc.next();
		String serial = sc.next();
		System.out.println(property + ": " + serial);
	}
	
	@Test
	public void test1() throws IOException{
		Client c = new Client();
		c.post(new SearchRequset("111"), "gashen");
	}

}
