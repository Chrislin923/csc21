package view_controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import model.DiceTray;

public class BoggleConsole {
	public static void main(String[] args) {

		// Use this for testing
		char[][] a = { { 'A', 'B', 'S', 'E' }, { 'I', 'M', 'T', 'N' }, { 'N', 'D', 'E', 'D' },
				{ 'S', 'S', 'E', 'N' }, };

		DiceTray tray = new DiceTray(a);

		BoggleConsole boggle = new BoggleConsole(tray);
		boggle.play(a);
		boggle.enterWord(tray);
		boggle.scoreCount();
		boggle.getSortedFoundList();
		boggle.getSortedIncorrectList();
		boggle.otherWords(tray);
		System.out.println("You could have found these " + boggle.getOtherCounts() + " more words:");
		System.out.println(boggle.getOtherW().trim());
		

		// TODO: Complete a console game
	}
	private int score;
	private ArrayList<String> wordList;
	private ArrayList<String> incorrectList;
	private int otherCounts;
	private String otherW;
	public BoggleConsole(DiceTray Tray){
		score = 0;
	}

	private void play(char[][] a) {
		/**
		 * print out the game intro and the gird
		 */
		System.out.println("Play one game of Boggle:");
		for (int r = 0; r < a.length; r++) {
			System.out.print("\n");
			for (int c = 0; c < a[0].length; c++) {
				System.out.print(a[r][c] + " ");
			}
		}
		System.out.println("\n");
	}

	private void enterWord(DiceTray Tray) {
		/**
		 * this method decide user input words are found or not
		 */
		this.wordList = new ArrayList<String>();
		this.incorrectList = new ArrayList<String>();
		System.out.println("Enter words or ZZ to quite:");
		String word = "";
		Scanner input = null;
		while (!word.equals("zz")) {
			input = new Scanner(System.in);
			word = input.next();
			word = word.toLowerCase();
			if (inList(word))
				continue;
			if (Tray.found(word) 
					&& isInDic(word) 
					&& !word.equals("zz")) {
				this.wordList.add(word);
			}
			else {
				if (!word.equals("zz"))
					this.incorrectList.add(word.toLowerCase());
			}
		}
		input.close();
	}
	
	private void scoreCount() {
		/**
		 * Count the score from the words user get
		 */
		for (int i = 0; i < this.wordList.size(); i ++) {
			String word = this.wordList.get(i);
			if(word.length() == 3)
				this.score++;
			else if(word.length() == 4)
				this.score++;
			else if(word.length() == 5)
				this.score = this.score + 2;
			else if (word.length() == 6)
				this.score = this.score + 3;
			else if (word.length() == 7)
				this.score = this.score + 4;
			else
				this.score = this.score + 11;
		}
		System.out.print("Score: " + this.score);
		System.out.println("\n");
	}
	
	private void getSortedFoundList(){
		/**
		 * print a list of found words in sorted way
		 */
		Collections.sort(this.wordList);
		System.out.println("Words you found:");
		for (int i = 0; i < this.wordList.size(); i ++) {
			String word = this.wordList.get(i);
			if (i + 1 == this.wordList.size())
				System.out.print(word);
			else
				System.out.print(word + " ");
		}
		System.out.println("\n");
	}
	
	private void getSortedIncorrectList() {
		/**
		 * get the incoorect words sorted
		 */
		Collections.sort(this.incorrectList);
		System.out.println("Incorrect Words:");
		for (int i = 0; i < this.incorrectList.size(); i ++) {
			String word = this.incorrectList.get(i);
			if (i + 1 == this.incorrectList.size())
				System.out.print(word);
			else
				System.out.print(word + " ");
		}
		System.out.println("\n");
	}
	

	
	private boolean isInDic(String word) {
		/**
		 * test if a word is in the dictionary
		 */
		try {
			File inFile = new File("BoggleWords.txt");
			Scanner dic = new Scanner(inFile);
			while (dic.hasNext()) {
				if(word.equals(dic.next()))
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
		 *  Test if a word is already in the lists
		 */
		for (int i = 0; i < this.wordList.size(); i ++) {
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
	
	private void otherWords(DiceTray Tray) {
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
				if(Tray.found(w) && !inList(w)) {
					this.otherW += w +" ";
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
	
	private int getOtherCounts() {
		/**
		 * get the count of other words
		 */
		return this.otherCounts;
	}
	
	private String getOtherW() {
		/**
		 * other word that can found from the matrix
		 */
		return this.otherW;
	}

}
