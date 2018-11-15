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
}
