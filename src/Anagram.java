import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This code attempts to convert a string of random characters into a series of words
 * Randomness is used to allow for the output of different words
 * "False" aims for a complete anagram using all letters, "True" gives all possibilities
 * Therefore, "True" is better for words of longer length.
 * @author Brandon Dalla Rosa
 *
 */
public class Anagram {
	
	//Dictionary to store words globally
	public HashSet<String> dictionary;
	
	//Function to create phrase from input
	public String phrase(String input, Integer minWordLength, Boolean allWords) {
		if(minWordLength==null) {
			minWordLength = 0;
		}
		if(allWords==null) {
			allWords = true;
		}
		setDict();
		
		ArrayList<Character> letters = new ArrayList<Character>();
		for(int i=0;i<input.length();i++) {
			letters.add(input.charAt(i));
		}
		ArrayList<Character> trial = letters;
		int currentLength = input.length();
		String currentTry = "";
		Stack<String> prev = new Stack<String>();
 		HashSet<String> possibilities = new HashSet<String>();
		int count1 = 0;
		int count2 = 0;
		int algoLength = 1000; // This variable determines how long the algorithm will try
		while(currentLength>0 && count2<algoLength) {
			if(trial.size()==0) {
				for(int i=0;i<currentTry.length();i++) {
					trial.add(currentTry.charAt(i));
				}
				currentTry = "";
				count1++;
			}
			if(count1>algoLength) {
				if(prev.isEmpty()) {
					count2++;
					count1=0;
				}
				else {
					String ret = prev.pop();
					for(int i=0;i<ret.length();i++) {
						trial.add(ret.charAt(i));
					}
					currentLength = currentLength+ret.length();
					count1 = 0;
					count2++;
				}
			}
			int rand = ThreadLocalRandom.current().nextInt(0, trial.size());
			currentTry = currentTry+trial.get(rand);
			trial.remove(rand);
			if(dictionary.contains(currentTry) && currentTry.length()>=minWordLength) {
				prev.push(currentTry);
				possibilities.add(currentTry);
				currentLength = currentLength-currentTry.length();
				currentTry = "";
			}
		}
		String output = "";
		if(!allWords) {
			while(!prev.isEmpty()) {
				output = output+prev.pop()+" ";
			}
		}
		if(allWords) {
			Object[] temporary = possibilities.toArray();
			for(int i=0;i<temporary.length;i++) {
				output = output+temporary[i]+" ";
			}
		}
		return input+":\n"+output;
	}
	
	//Sets the words that exist in the English Language
	public void setDict() {
		dictionary = new HashSet<String>();
		String current = "";
		InputStream inStream = Anagram.class.getResourceAsStream("wordlist.txt");
		Scanner scan = new Scanner(inStream);
		while((current = scan.nextLine()) !=null && scan.hasNextLine()) {
			dictionary.add(current);
		}
		scan.close();
	}
	public void setDict(String[] words) {
		dictionary = new HashSet<String>();
		for(int i=0;i<words.length;i++) {
			dictionary.add(words[i]);
		}
	}
}
