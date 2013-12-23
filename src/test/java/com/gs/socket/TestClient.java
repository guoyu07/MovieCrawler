package com.gs.socket;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import com.gs.socket.client.Client;

public class TestClient {

	@Test
	public void testClient() throws IOException {
		Client c = new Client();
		c.post("HaHa");
	}
	
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

}
