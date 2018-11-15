package edu.smith.cs.csc212.p8;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CheckSpelling { 
	/**
	 * Read all lines from the UNIX dictionary.
	 * @return a list of words!
	 */
	public static List<String> loadDictionary() {
		long start = System.nanoTime();
		List<String> words;
		try {
			words = Files.readAllLines(new File("src/main/resources/words").toPath());
		} catch (IOException e) {
			throw new RuntimeException("Couldn't find dictionary.", e);
		}
		long end = System.nanoTime();
		
		//convert all words to lower case
		for(String str:words) {
			str=str.toLowerCase();
		}
		
		double time = (end - start) / 1e9;
		System.out.println("Loaded " + words.size() + " entries in " + time +" seconds.");
		return words;
	}
	
	
	/**
	 * This method looks for all the words in a dictionary.
	 * @param words - the "queries"
	 * @param dictionary - the data structure.
	 */
	public static void timeLookup(List<String> words, Collection<String> dictionary) {
		long startLookup = System.nanoTime();
		
		int found = 0;
		for (String w : words) {
			if (dictionary.contains(w)) {
				found++;
			}
		}
		
		long endLookup = System.nanoTime();
		
		double fractionFound = found / (double) words.size();
		double timeSpentPerItem = (endLookup - startLookup) / ((double) words.size());
		int nsPerItem = (int) timeSpentPerItem;
		System.out.println(dictionary.getClass().getSimpleName()+
				": Lookup of items found="+fractionFound+" time="+nsPerItem+" ns/item");
	}
	
	/**
	 * 
	 * @param yesWords - words from the original dictionary
	 * @param numSamples ~=10000
	 * @param fractionYes - (0.0-1.0) percentage of yesWords 
	 * @return numSample words in an output list, where fractionYes of them are from the original dictionary (yesWords)
	 */
	public static List<String> createMixedDataset(List<String> yesWords, int numSamples, double fractionYes){
		int numYes = (int)(numSamples*fractionYes);
		int numFake = numSamples-numYes;
		
		List<String> output = new ArrayList<String>();
		
		Random rand = new Random();
		//pick a random word from yesWords
		int randI = rand.nextInt(yesWords.size());
		
		//add nonFakeWords 	
		for(int i=0;i<numYes;i++) {
			output.add(yesWords.get(randI));
		}
		//add fakeWords
		for(int i=0;i<numFake;i++) {
			output.add("zzz");
		}
		
		return output;
	}
	
	public static final String bookPath = "src/main/resources/The_Dunwich_Horror.txt";
	/**
	 * @param bookPath - the book from which we load all its words for testing 
	 * @return - a List of all words from the book 
	 */
	public static List<String> loadBook(String bookPath){
		List<String> loadedBookWords = new ArrayList<>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(bookPath));
			String line = reader.readLine();
			while(line!=null) {
				List<String> words = new ArrayList<>();
				words = WordSplitter.splitTextToWords(line);
				for(String str:words) {
					if(!loadedBookWords.contains(str)) {
						loadedBookWords.add(str);
					}
				}
				line=reader.readLine();
			}
			reader.close();
		}catch(FileNotFoundException ex){
			System.out.println("cannot find the file");
		}catch(IOException ex) {
			System.out.println("file cannot be read");
		}
		return loadedBookWords;
	}
	
	
	/**
	 * 
	 * @param loadedWords
	 * @param dictionary
	 * @return a list of words that cannot be found in the given dictionary 
	 */
	public static List<String> findMisspelled(List<String> loadedWords, Collection<String> dictionary){
		List<String> misspelled = new ArrayList<>();
		for(String str:loadedWords) {
			if(!dictionary.contains(str)) {
				misspelled.add(str);
			}
		}
		return misspelled;
	}
	
	
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = loadDictionary();
		
		// --- Create a bunch of data structures for testing:
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		CharTrie trie = new CharTrie(); 
		for (String w : listOfWords) {
			trie.insert(w);
		}
		LLHash hm100k = new LLHash(100000);
		/*
		 * lookup of items found in LLHash with different numBuckets:
		 * default numBuckets = 100000; lookup time ~=140ns/item
		 * numBuckets = defaultValue/100; lookup time drastically increased - lookup time ~=4000ns/item
		 * numBuckets = defaultValue*100; lookup time doesn't change much - lookup time ~=170ns/item
		 * numBuckets = decaultValue*10000; lookup time - OutOfMemory 
		 */
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		
		// --- Make sure that every word in the dictionary is in the dictionary:
		timeLookup(listOfWords, treeOfWords);
		timeLookup(listOfWords, hashOfWords);
		timeLookup(listOfWords, bsl);
		timeLookup(listOfWords, trie);
		timeLookup(listOfWords, hm100k);
		
		
		System.out.println("\n\nloop up time of dataset with percentage of hits and misses:");
		// --- Create a dataset of mixed hits and misses:
		List<String> hitsAndMisses = createMixedDataset(listOfWords,10000,0.5);	
		// TODO, do this.
		/*
		 *  TODO, make a line plot as the percentage of hits goes form 0.0 to 1.0 in steps of 0.1
		 *  where each line is a different data structure
		 */
		timeLookup(hitsAndMisses, treeOfWords);
		timeLookup(hitsAndMisses, hashOfWords);
		timeLookup(hitsAndMisses, bsl);
		timeLookup(hitsAndMisses, trie);
		timeLookup(hitsAndMisses, hm100k);

		
		/*
		 * TODO, spell-check a project Gutenberg book (The Dunwich Horror)
		 */
		//load all words of the book to a list
		List<String> bookWords = loadBook(bookPath);
		//do the spell-checking 
		System.out.println("\n\nCheck the spelling of a project Gutenberg book:");
		timeLookup(bookWords, treeOfWords);
		timeLookup(bookWords, hashOfWords);
		timeLookup(bookWords, bsl);
		timeLookup(bookWords, trie);
		timeLookup(bookWords, hm100k);
		/*
		 * now print all the misspelled words
		 */
		System.out.println("\nNow print all misspelled words: ");
		List<String> misspelled = findMisspelled(bookWords, hashOfWords);
		for(String str:misspelled) {
			System.out.println(str);
		}
		System.out.println("So, there are "+misspelled.size()+" misspelled words");
		
		
		// --- linear list timing:
		// Looking up in a list is so slow, we need to sample:
		System.out.println("\n\nStart of list: ");
		timeLookup(listOfWords.subList(0, 1000), listOfWords);
		System.out.println("End of list: ");
		timeLookup(listOfWords.subList(listOfWords.size()-100, listOfWords.size()), listOfWords);
		
	
		// --- print statistics about the data structures:
		System.out.println("Count-Nodes: "+trie.countNodes());
		System.out.println("Count-Items: "+hm100k.size());

		System.out.println("Count-Collisions[100k]: "+hm100k.countCollisions());
		System.out.println("Count-Used-Buckets[100k]: "+hm100k.countUsedBuckets());
		System.out.println("Load-Factor[100k]: "+hm100k.countUsedBuckets() / 100000.0);

		
		System.out.println("log_2 of listOfWords.size(): "+listOfWords.size());
		
		System.out.println("Done!");
	}
}
