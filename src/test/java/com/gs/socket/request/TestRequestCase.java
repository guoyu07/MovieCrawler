package com.gs.socket.request;


import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import com.google.gson.JsonSyntaxException;

public class TestRequestCase {

	@Test
	public void test() throws JsonSyntaxException, ClassNotFoundException {
		RequestDTO dto = RequestDTOProcesser.pack(new SearchRequset("HaHa"), new RequestProperty(getCPUID(), "gaoshen"));
		System.out.println(dto);
		try {
			System.out.println(RequestDTOProcesser.unpack(dto));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
