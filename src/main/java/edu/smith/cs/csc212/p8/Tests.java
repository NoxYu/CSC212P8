package edu.smith.cs.csc212.p8;

import org.junit.*;

public class Tests {

	@Test
	public void testCharTrieNodeCount() {
		CharTrie ct = new CharTrie();
		ct.insert("xyz");
		Assert.assertEquals(3, ct.countNodes());
		ct.insert("ab");
		Assert.assertEquals(5,ct.countNodes());
		ct.insert("vm");
		Assert.assertEquals(7,ct.countNodes());	
	}
	
	@Test
	public void testCharTrieNodeCount2() {
		CharTrie ct = new CharTrie();
		ct.insert("apple");
		Assert.assertEquals(5, ct.countNodes());
		ct.insert("apps");
		Assert.assertEquals(6, ct.countNodes());
		ct.insert("please");
		Assert.assertEquals(12, ct.countNodes());		
	}
}
