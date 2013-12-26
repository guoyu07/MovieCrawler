package com.gs.luceneComponent;

import org.junit.Test;

public class TestIndexer {
	
	@Test
	public void test(){
		Indexer i = new Indexer();
		i.index("D://Test//MovieIndex");
	}
}
