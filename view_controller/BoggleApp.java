package view_controller;
//By Shih Hsuan Lin
import model.Boggle;
import model.DiceTray;

import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class BoggleApp extends Application {
	//create a GUI to play game of boggle
  public static void main(String[] args) {
    launch(args);
  }
  private TextArea input = new TextArea();
  private TextArea diceTrayArea = new TextArea();
  private Button NewGame = new Button("Game Start");
  private Button EndGame = new Button("Game End");
  private Label prompt = new Label("Enter the words below: ");
  private char[][] tray;
  private DiceTray dTray;
  private Boggle game;

  public void start(Stage stage) {
    // Set up window
	  input.setWrapText(true);
	  VBox vbox = new VBox(5);
	  HBox buttonSec = new HBox(50);
	  addLayout(vbox, buttonSec);
	  addDiceTray();
	  addInput();
	  Scene scene = new Scene(vbox, 350, 550);
	  stage.setScene(scene);
	  stage.show();
  }
  
  private void addLayout(VBox vbox, HBox buttonSec) {
	  //add all the components to the window
	  this.diceTrayArea.setMaxSize(300, 230);
	  this.diceTrayArea.setEditable(false);
	  this.diceTrayArea.setFont(new Font("Courier", 24));
	  this.input.setMaxSize(300, 1000);
	  buttonLayout(buttonSec);
	  vbox.getChildren().add(this.diceTrayArea);
	  vbox.getChildren().add(buttonSec);
	  vbox.getChildren().add(this.prompt);
	  vbox.getChildren().add(this.input);
	  vbox.setAlignment(Pos.CENTER);
  }
  
  private void buttonLayout(HBox buttonSec) {
	  //The button is display side to side, add hbox for button display
	  buttonSec.getChildren().add(this.NewGame);
	  buttonSec.getChildren().add(this.EndGame);
	  buttonSec.setAlignment(Pos.CENTER);
  }
  
  private void getDice() {
	  //Generate random dice to the DiceTray as the preparation for the game
	  this.tray = new char[4][4];
	  char[][] dice = {{'L', 'R', 'Y', 'T', 'T', 'E'},
				{'A', 'N', 'A', 'E', 'E', 'G'},
				{'A', 'F', 'P', 'K', 'F', 'S'},
				{'Y', 'L', 'D', 'E', 'V', 'R'},
				{'V', 'T', 'H', 'R', 'W', 'E'},
				{'I', 'D', 'S', 'Y', 'T', 'T'},
				{'X', 'L', 'D', 'E', 'R', 'I'},
				{'Z', 'N', 'R', 'N', 'H', 'L'},
				{'E', 'G', 'H', 'W', 'N', 'E'},
				{'O', 'A', 'T', 'T', 'O', 'W'},
				{'H', 'C', 'P', 'O', 'A', 'S'},
				{'N', 'M', 'I', 'H', 'U', 'Q'},
				{'S', 'E', 'O', 'T', 'I', 'S'},
				{'M', 'T', 'O', 'I', 'C', 'U'},
				{'E', 'N', 'S', 'I', 'E', 'U'},
				{'O', 'B', 'B', 'A', 'O', 'J'},};
	  char[]temp = new char[16];
	  for (int i = 0; i < dice.length; i++) {
		  Random num = new Random();
		  int side = num.nextInt(6);
		  temp[i] = dice[i][side];
	  }
	  for (int i = 0; i < this.tray.length; i++) {
		  for (int j = 0; j< this.tray.length; j++) {
			  this.tray[i][j] = temp[(j*this.tray.length)+ i];
		  }
	  }
	  game = new Boggle();
	  this.dTray = new DiceTray(this.tray);
  }
  
  private void addDiceTray() {
	  //add event when the game button is pressed
	  EventHandler<ActionEvent> addTray = new TrayHandler();
	  this.NewGame.setOnAction(addTray);
  }
  
  private class TrayHandler implements EventHandler<ActionEvent>{
	  
	  public void handle(ActionEvent event) {
		  getDice();
		  diceTrayArea.setText(game.play(tray));
		  
	  }
	  
  }
  
  private void addInput() {
	  // create event when end button is pressed
	  EventHandler<ActionEvent> input = new textInput();
	  this.EndGame.setOnAction(input);
  }
  
  private class textInput implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		String words = input.getText();
		Scanner userInput = new Scanner(words);
		setAlert(userInput);
		input.setText("");
		
	}
	  
  }
  private void setAlert(Scanner userInput) {
	  //create information window when end button is hit
	  Alert alert = new Alert(AlertType.INFORMATION);
	  alert.setTitle("Results");
	  alert.setHeaderText("Here is your score");  
	  alert.setContentText(result(userInput));  // message is a big string with all required reults
	  alert.showAndWait();
  }
  
  private String result(Scanner userInput) {
	  //return a string with all the information
	  String output = "";
	  this.game.enterWord(this.dTray, userInput);
	  output += "Score: " + this.game.scoreCount() + "\n\n";
	  output += "Words you found: \n";
	  output += this.game.getSortedFoundList() + "\n";
	  output += "Incorrect Words: \n";
	  output += this.game.getSortedIncorrectList() + "\n";
	  this.game.otherWords(dTray);
	  output += "You could have found these " 
				+ this.game.getOtherCounts() 
				+ " more words: \n";
	  output += this.game.getOtherW().trim();
	  return output;
  }
  
}