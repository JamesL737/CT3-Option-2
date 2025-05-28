package com.module3CTOption2.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.time.LocalDateTime;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author James L.
 * Program to create a gui, print date using a button, update
 * background color when button is pressed, and output current date to text file
 */

public class JavaFXGUI extends Application {

	//create panes for top bar and main menu
	GridPane topBar = null;
	GridPane mainScreen = null;
	
	//initialize variables
	Scene scene = null;
	GridPane fullPane = null;
	Label dateLabel = null;
	TextField dateField = null;
	Random rNum = new Random();
	
	@Override
	public void start(Stage applicationStage) throws Exception {
		
		//create top bar and main screen
		topBar = new GridPane();
		mainScreen = new GridPane();
		
		//create scene and pan to contain top bar and main menu
		fullPane = new GridPane();
		scene = new Scene(fullPane);
		
		//label for date
		dateLabel = new Label("Current date: ");
		
		//field to display date
		dateField = new TextField();
		dateField.setEditable(false);
		dateField.setPrefColumnCount(20);
		
		//create menu items
		MenuItem getDate = new MenuItem("Get Date");
		MenuItem dateToFile = new MenuItem("Print Date to File");
		MenuItem changeColor = new MenuItem("Background Color: #ffffff");
		MenuItem exit = new MenuItem("Exit");
		
		//event handler for setting date field
		getDate.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				LocalDateTime now = LocalDateTime.now();
				dateField.setText(now.toString());
			}
		});
		
		//event handler for writing date to file log.txt
		dateToFile.setOnAction(event -> {
			
			FileOutputStream fStream = null;
			PrintWriter outFS = null;
			
			try {
				fStream = new FileOutputStream("log.txt");
				outFS = new PrintWriter(fStream);
				outFS.println(dateField.getText());
				outFS.close();
			}
			catch(IOException excpt) {
				Alert alert = new Alert(AlertType.ERROR, "Caught IO Error: " + excpt.getLocalizedMessage());
				alert.showAndWait();
			}
			finally {
				try {
					if(fStream != null) {
						fStream.close();
					}
				}
				catch(IOException excpt) {
					Alert alert = new Alert(AlertType.ERROR, "Caught IO Error: " + excpt.getLocalizedMessage());
					alert.showAndWait();
				}
			}
		});
		
		//event handler to change main screen color to a shade of orange
		changeColor.setOnAction(event -> {
			
			//use random number to pick one of 5 shades
			//updates main menu background and button text with hex number
			int randNum = rNum.nextInt(5);
			
			switch(randNum) {
			
			case 0:
				mainScreen.setStyle("-fx-background-color: #ff9966");
				changeColor.setText("Background color: #ff9966");
				break;
			case 1:
				mainScreen.setStyle("-fx-background-color: #ff6600");
				changeColor.setText("Background color: #ff6600");
				break;
			case 2:
				mainScreen.setStyle("-fx-background-color: #ff9933");
				changeColor.setText("Background color: #ff9933");
				break;
			case 3:
				mainScreen.setStyle("-fx-background-color: #ffcc00");
				changeColor.setText("Background color: #ffcc00");
				break;
			case 4:
				mainScreen.setStyle("-fx-background-color: #ff9900");
				changeColor.setText("Background color: #ff9900");
				break;
			default:
				break;
			}
			
		});
		
		//exit application 
		exit.setOnAction(event -> {
			Platform.exit();
		});
		
		//create menu with buttons
		MenuButton menuButton = new MenuButton("Options", null, getDate, dateToFile, changeColor, exit);
		
		//add menu to top bar
		topBar.add(menuButton, 0, 0);
		
		//add date label and field to main menu
		mainScreen.setPadding(new Insets (30, 20, 30, 20));
		mainScreen.add(dateLabel, 0, 0);
		mainScreen.add(dateField, 1, 0);
		
		//add top bar, then main menu
		fullPane.setPadding(new Insets(2, 0, 0, 0));
		fullPane.setVgap(2);
		fullPane.add(topBar, 0, 0);
		fullPane.add(mainScreen, 0, 1);
		
		//set scene and show
		applicationStage.setScene(scene);
		applicationStage.setTitle("Menu Functions");
		applicationStage.show();
		
	}
	
	public static void main(String [] args) {
		launch(args);
	}
}
