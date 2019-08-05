package view_controller;
//By Shih Hsuan Lin
import java.util.Scanner;

import model.Boggle;
import model.DiceTray;

public class BoggleConsole {
	public static void main(String[] args) {

		// Use this for testing
		char[][] a = { { 'A', 'B', 'S', 'E' }, 
						{ 'I', 'M', 'T', 'N' }, 
						{ 'N', 'D', 'E', 'D' },
						{ 'S', 'S', 'E', 'N' }, };

		DiceTray tray = new DiceTray(a);

		Boggle boggle = new Boggle();
		System.out.println("Play one game of Boggle:");
		System.out.println(boggle.play(a));
		System.out.println("Enter words or ZZ to quit:");
		Scanner input = new Scanner(System.in);
		boggle.enterWord(tray, input);
		System.out.println("Score: " + boggle.scoreCount() + "\n");
		System.out.println("Words you found:");
		System.out.println(boggle.getSortedFoundList());
		System.out.println("Incorrect Words:");
		System.out.println(boggle.getSortedIncorrectList());
		boggle.otherWords(tray);
		System.out.println("You could have found these " 
				+ boggle.getOtherCounts() 
				+ " more words:");
		System.out.println(boggle.getOtherW().trim());

		// TODO: Complete a console game
	}
}

