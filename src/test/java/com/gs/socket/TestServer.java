package com.gs.socket;

import java.io.IOException;

import org.junit.Test;

import com.google.gson.JsonSyntaxException;
import com.gs.socket.server.Server;

import junit.framework.TestCase;

public class TestServer extends TestCase {
	@Test
	public void testServer() throws JsonSyntaxException, IOException, Exception{
		Server s = new Server();
		s.start();
	}
}
