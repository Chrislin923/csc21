package model;

// By Shih Hsuan Lin
public class DiceTray {
	private int row;
	private int col;
	private char[][] Tray;
	private char[][] LetterTrack;

	/**
	 * Construct a tray of dice using a hard coded 2D array of chars. Use this for
	 * testing
	 * 
	 * @param newBoard The 2D array of characters used in testing
	 */
	public DiceTray(char[][] newBoard) {
		// TODO Implement this constructor
		this.row = newBoard.length;
		this.col = newBoard[0].length;
		this.Tray = new char[this.row][this.col];
		this.LetterTrack = new char[this.row][this.col];
		for (int r = 0; r < this.row; r++) {
			for (int c = 0; c < this.col; c++) {
				this.Tray[r][c] = newBoard[r][c];
			}
		}

	}

	private void initLetterTrack() {
		for (int r = 0; r < this.row; r++) {
			for (int c = 0; c < this.col; c++) {
				this.LetterTrack[r][c] = ' ';
			}
		}
	}

	/**
	 * Return true if search is word that can found on the board following the rules
	 * of Boggle
	 * 
	 * @param str A word that may be in the board by connecting consecutive letters
	 * @return True if search is found
	 */
	public boolean found(String attempt) {
		// TODO: Implement this method
		attempt = attempt.toUpperCase();
		boolean result = false;
		for (int r = 0; r < this.row; r++) {
			for (int c = 0; c < this.col; c++) {
				if (this.Tray[r][c] == attempt.charAt(0)) {
					initLetterTrack();
					if (search(r, c, attempt)) {
						return true;
					}
				}
			}
		}
		return result;
	}

	private boolean search(int r, int c, String word) {
		boolean result = false;
		if (WithinBound(r, c) && this.LetterTrack[r][c] == ' ') {
			if (word.equals(""))
				result = true;
			else if (this.Tray[r][c] == word.charAt(0)) {
				this.LetterTrack[r][c] = '.';
				if (word.length() == 1)
					result = true;
				if (!result)
					result = search(r - 1, c, word.substring(1));
				if (!result) {
					result = search(r + 1, c, word.substring(1));
				}
				if (!result) {
					result = search(r, c - 1, word.substring(1));
				}
				if (!result) {
					result = search(r, c + 1, word.substring(1));
				}
				if (!result) {
					result = search(r - 1, c - 1, word.substring(1));
				}
				if (!result) {
					result = search(r - 1, c + 1, word.substring(1));
				}
				if (!result) {
					result = search(r + 1, c - 1, word.substring(1));
				}
				if (!result) {
					result = search(r + 1, c + 1, word.substring(1));
				}
				if (!result) {
					this.LetterTrack[r][c] = ' ';
					return false;
				}
			}
		}
		return result;
	}

	private boolean WithinBound(int r, int c) {
		return r >= 0 && r < this.row && c >= 0 && c < this.col;
	}
}
