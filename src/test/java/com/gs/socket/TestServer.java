package com.gs.socket;

import org.junit.Test;

import com.gs.socket.server.Server;

import junit.framework.TestCase;

public class TestServer extends TestCase {
	@Test
	public void testServer(){
		Server s = new Server();
		s.start();
	}
}
