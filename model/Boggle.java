package model;
//By Shih Hsuan Lin
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Boggle {

  // TODO: Complete a Boggle game that will be used with two views
  //  1. A console based game with standard IO  (Boggle Two)
  //  2. An event-driven program with a graphical user interface (Boggle Three)
	private int score;
	private ArrayList<String> wordList;
	private ArrayList<String> incorrectList;
	private int otherCounts;
	private String otherW;

	public Boggle() {
		score = 0;
	}

	public String play(char[][] a) {
		/**
		 * print out the game intro and the gird
		 */
		String output = "";
		for (int r = 0; r < a.length; r++) {
			output += "\n\t";
			for (int c = 0; c < a[0].length; c++) {
				if (a[r][c] == 'Q')
					output += "Qu" + "\t";
				else
					output += a[r][c] + "\t";
			}
		}
		output+= "\n";
		return output;
	}

	public void enterWord(DiceTray Tray, Scanner input) {
		/**
		 * this method decide user input words are found or not
		 */
		this.wordList = new ArrayList<String>();
		this.incorrectList = new ArrayList<String>();
		String word = "";
		while (input.hasNext()) {
			word = input.next();
			word = word.toLowerCase();
			if (word.equals("zz"))
				break;
			if (inList(word))
				continue;
			if (Tray.found(word) && isInDic(word) && !word.equals("zz")) {
				this.wordList.add(word);
			} else {
				if (!word.equals("zz"))
					this.incorrectList.add(word.toLowerCase());
			}
		}
		input.close();
	}

	public int scoreCount() {
		/**
		 * Count the score from the words user get
		 */
		for (int i = 0; i < this.wordList.size(); i++) {
			String word = this.wordList.get(i);
			if (word.length() == 3)
				this.score++;
			else if (word.length() == 4)
				this.score++;
			else if (word.length() == 5)
				this.score = this.score + 2;
			else if (word.length() == 6)
				this.score = this.score + 3;
			else if (word.length() == 7)
				this.score = this.score + 4;
			else
				this.score = this.score + 11;
		}
		return this.score;
	}

	public String getSortedFoundList() {
		/**
		 * print a list of found words in sorted way
		 */
		String list = "";
		Collections.sort(this.wordList);
		for (int i = 0; i < this.wordList.size(); i++) {
			String word = this.wordList.get(i);
			if (i + 1 == this.wordList.size())
				list += word;
			else
				list += word + " ";
		}
		list += "\n";
		return list;
	}

	public String getSortedIncorrectList() {
		/**
		 * get the incorrect words sorted
		 */
		String list = "";
		Collections.sort(this.incorrectList);
		for (int i = 0; i < this.incorrectList.size(); i++) {
			String word = this.incorrectList.get(i);
			if (i + 1 == this.incorrectList.size())
				list += word;
			else
				list += word + " ";
		}
		list += "\n";
		return list;
	}

	private boolean isInDic(String word) {
		/**
		 * test if a word is in the dictionary
		 */
		try {
			File inFile = new File("BoggleWords.txt");
			Scanner dic = new Scanner(inFile);
			while (dic.hasNext()) {
				if (word.equals(dic.next()))
					return true;
			}
			dic.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean inList(String str) {
		/**
		 * Test if a word is already in the lists
		 */
		for (int i = 0; i < this.wordList.size(); i++) {
			String word = this.wordList.get(i);
			if (str.equals(word))
				return true;
		}
		for (int i = 0; i < this.incorrectList.size(); i++) {
			String word = this.incorrectList.get(i);
			if (str.equals(word))
				return true;
		}
		return false;
	}

	public void otherWords(DiceTray Tray) {
		/**
		 * get what other words can be found in the matrix
		 */
		try {
			File inFile = new File("BoggleWords.txt");
			Scanner dic = new Scanner(inFile);
			this.otherCounts = 0;
			this.otherW = "";
			while (dic.hasNext()) {
				String w = dic.next();
				if (Tray.found(w) && !inList(w)) {
					this.otherW += w + " ";
					this.otherCounts++;
					if (this.otherCounts % 20 == 0)
						this.otherW += "\n";
				}
			}
			dic.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getOtherCounts() {
		/**
		 * get the count of other words
		 */
		return this.otherCounts;
	}

	public String getOtherW() {
		/**
		 * other word that can found from the matrix
		 */
		return this.otherW;
	}
	
	

}