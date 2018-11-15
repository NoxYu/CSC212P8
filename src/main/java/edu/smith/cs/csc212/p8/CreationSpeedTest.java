package edu.smith.cs.csc212.p8;

import java.util.*;
public class CreationSpeedTest {

	public static void main(String[] args) {
		List<String> data = new ArrayList<>();
		data.add("aa");
		data.add("ab");
		data.add("ac");
		data.add("ad");
		data.add("ae");
				
		/*
		 * compare: 
		 * timing difference b2w constructing a HashSet and a TreeSet
		 * with their input data/calling add method in a for loop
		 */
		long fillHashSetStart = System.nanoTime();
		Set<String> hashSet1 = new HashSet<>(5);
		for(String str:data) {
			hashSet1.add(str);
		}
		long fillHashSet = System.nanoTime()-fillHashSetStart;

		
		long fillTreeStart = System.nanoTime();
		Set<String> treeSet1 = new TreeSet<>();
		for(String str:data) {
			treeSet1.add(str);
		}
		long fillTree = System.nanoTime()-fillTreeStart;
		

		long createWithInputHashStart = System.nanoTime(); 
		Set<String> hashSet2 = new HashSet<>(data);
		long createWithInputHash = System.nanoTime()-createWithInputHashStart;

		long createWithInputTreeStart = System.nanoTime();
		Set<String> treeSet2 = new TreeSet<>(data);
		long createWithInputTree = System.nanoTime()-createWithInputTreeStart;
		
		
		long fillSortedLStart = System.nanoTime();
		Set<String> sortedStrLSet = new SortedStringListSet(data);
		long fillSortedL = System.nanoTime()-fillSortedLStart;
		
		
		long fillCharTrieStart = System.nanoTime();
		CharTrie charTrie = new CharTrie();
		for(String str:data) {
			charTrie.insert(str);
		}
		long fillCharTrie =System.nanoTime()-fillCharTrieStart;

		
		long fillLLHashStart = System.nanoTime();
		LLHash llHash = new LLHash(5);
		for(String str:data) {
			llHash.add(str);
		}
		long fillLLHash = System.nanoTime()-fillLLHashStart;
		
		/*
		 * print time needed to fill each data structure:
		 */
		System.out.println("Time needed to: "
				+"\n\tfill a HashSet: "+fillHashSet/1000 +"μs"
				+"\n\tfill a TreeSet: "+fillTree/1000 +"μs"
				+"\n\tfill a SortedStringListSet: "+fillSortedL/1000 +"μs"
				+"\n\tfill a CharTrie: "+fillCharTrie/1000 +"μs"
				+"\n\tfill a LLHash: "+fillLLHash/1000+"μs");
		System.out.println("Time needed to: "
				+"\n\tconstruct a HashSet with its input data: "+createWithInputHash/1000+"μs"
				+"\n\tconstruct a HashSet by calling add in a for loop: "+ fillHashSet/1000+"μs"
				+"\n\n\tconstruct a TreeSet with its input data: "+ createWithInputTree/1000+"μs"
				+"\n\tconstruct a TreeSet by calling add in a for loop: "+fillTree/1000+"μs");
		
		/*
		 * find insertion time per element for each of these data structures：
		 */
		String str = "bb";
		List<String> dataSingleE = new ArrayList<>();
		dataSingleE.add(str);
		
		long addHashStart = System.nanoTime();
		hashSet1.add(str);
		long addHash = System.nanoTime()-addHashStart;
		
		long addTreeStart = System.nanoTime();
		treeSet1.add(str);
		long addTree = System.nanoTime()-addTreeStart;
		
		long addCharTrieStart = System.nanoTime();
		charTrie.insert(str);
		long addCharTrie = System.nanoTime()-addCharTrieStart;
		
		long addllHashStart = System.nanoTime();
		llHash.add(str);
		long addllHash = System.nanoTime()-addllHashStart;

		long addSSLStart = System.nanoTime();
		Set<String> ssls = new SortedStringListSet(dataSingleE);
		long addSSL = System.nanoTime()-addSSLStart;

		System.out.println("Insertion time per element for: "
				+"\n\tHashSet: "+addHash/1000+"μs"
				+"\n\tTreeSet: "+addTree/1000+"μs"
				+"\n\tCharTrie: "+addCharTrie/1000+"μs"
				+"\n\tLLHash: "+addllHash/1000+"μ2"
				+"\n\tSortedStringListSet: "+addSSL/1000+"μs");
	}
}
