import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is designed to output a random fact about the word that is input.
 * 
 * @author Brandon Dalla Rosa
 *
 */
public class FunFact {
	/**
	 * This method generates the fun fact given the input string. 
	 * @param input
	 * @return
	 */
	public String giveFact(String input) {
		Queue<String> valid = new LinkedList<String>();
		ArrayList<String> possibilities = new ArrayList<String>();
		try {
			URL website = new URL("https://en.wikipedia.org/w/index.php?title="+input+"&action=edit");
			InputStream inst = website.openStream();
			InputStreamReader red = new InputStreamReader(inst);
			BufferedReader buf = new BufferedReader(red);
			String current = "";
			while((current = buf.readLine())!=null) {
				if(infoSearch(current)) {
					valid.add(current);
				}
			}
			inst.close();
			red.close();
			buf.close();
		}
		catch (MalformedURLException error) {
			System.out.println("URL error.");
		}
		catch (IOException error) {
			System.out.println("IO error.");
		}
		String temporary= "";
		for(int i=0;i<valid.size();i++) {
			temporary = temporary+valid.poll();
		}
		String sub = "";
		for(int i=0;i<temporary.length();i++) {
			if(!(temporary.charAt(i)==']')&&!(temporary.charAt(i)=='[')) {
				sub = sub+temporary.charAt(i);
			}
			if(temporary.charAt(i)=='.') {
				possibilities.add(sub);
				sub = "";
			}
		}
		String output = "None Found.";
		if(possibilities.size()>0) {
			int rand = ThreadLocalRandom.current().nextInt(0, possibilities.size());
			output = possibilities.get(rand);
		}
		return output;
	}
	/** This method checks to see if the string contains viable data
	 * 
	 * @param input
	 * @return
	 */
	public boolean infoSearch(String input) {
		boolean output = false;
		if(!input.contains("<") && !input.contains(">") && !input.contains("/")&& !input.contains("%")&& !input.contains(":")) {
			output = true;
		}
		return output;
	}
}
