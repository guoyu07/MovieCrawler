package com.gs.DAO;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDAO {

	@Test
	public void test() {
		UserDAO dao = new UserDAO();
		System.out.println(dao.checkUserWithCpuid("BFE1BFF000206A7"));
		UserDAO dao1 = new UserDAO();
		System.out.println(dao1.checkUserWithCpuid("BFEBFBFF000206A7"));
		UserDAO dao2 = new UserDAO();
		System.out.println(dao2.checkUserWithCpuid("111"));
	}

}
