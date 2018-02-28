import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class Driver extends Application{
	private static final Paint BACK_COLOR = Color.LIGHTBLUE;
	private static final int SCENE_WIDTH = 600;
	private static final int SCENE_HEIGHT = 500;
	private int currentState;
	private ComboBox<Integer> minLength;
	private ComboBox<Boolean> allWords;
	private boolean usable1;
	private boolean usable2;
	/**
	 * This method is called to initialize the stage and scene.
	 */
	public void start(Stage stage) {
		Pane pane = setObjects();
		Scene scene = new Scene(pane,SCENE_WIDTH,SCENE_HEIGHT,BACK_COLOR);
		stage.setScene(scene);
		stage.setTitle("Word Fun");
		stage.setMinHeight(SCENE_HEIGHT);
		stage.setMinWidth(SCENE_WIDTH);
		pane.setBackground(new Background(new BackgroundFill(BACK_COLOR,null,null)));
		stage.show();
	}
	/**
	 * This method creates and aligns the GUI objects for the program.
	 * 
	 * @return
	 */
	private Pane setObjects() {
		usable1 = false;
		usable2 = false;
		currentState = 0;
		HBox center = new HBox(20);
		center.setAlignment(Pos.CENTER);
		VBox order = new VBox(50);
		order.setAlignment(Pos.CENTER);
		FunFact funFact = new FunFact();
		Anagram anagram = new Anagram();
		center.getChildren().add(order);
		HBox inputBox = new HBox(10);
		
		allWords = new ComboBox<Boolean>();
		allWords.setPrefSize(100, 40);
		allWords.setPromptText("false");
		allWords.getItems().add(true);
		allWords.getItems().add(false);
		allWords.setOnAction(click->{usable1=true;});
		minLength = new ComboBox<Integer>();
		minLength.setPrefSize(100, 40);
		minLength.setPromptText("4");
		minLength.setOnAction(click->{usable2=true;});
		for(int i=0;i<16;i++) {
			minLength.getItems().add((Integer)i);
		}
		
		
		ComboBox<String> wordChoice = new ComboBox<String>();
		wordChoice.setPrefSize(200, 40);
		wordChoice.setPromptText("Fun Fact");
		wordChoice.getItems().add("Fun Fact");
		wordChoice.getItems().add("Anagram");
		wordChoice.setOnAction(click->{handleBox(wordChoice,inputBox);});
		order.getChildren().add(wordChoice);
		
		TextArea outputField = new TextArea();
		outputField.setEditable(false);
		outputField.setWrapText(true);
		outputField.setPrefSize(300, 200);
		
		TextField inputField = new TextField();
		inputField.setPromptText("Type the word here...");
		inputField.setPrefSize(300,40);
		inputField.setOnAction(click->{
			if(currentState==0) {
				outputField.setText(funFact.giveFact(inputField.getText()));
			}
			if(currentState==1) {
				boolean awords = false;
				int mlength = 4;
				if(usable1) {
					awords = allWords.getValue();
				}
				if(usable2) {
					mlength = minLength.getValue();
				}
				outputField.setText(anagram.phrase(inputField.getText(), mlength, awords));
			}
			inputField.setText("");
		});
		inputBox.getChildren().add(inputField);
		order.getChildren().add(inputBox);
		order.getChildren().add(outputField);
		return center;
	}
	/**
	 * This method is called to handle the values within the pull-down
	 * menu in the program.
	 * 
	 * @param box
	 */
	private void handleBox(ComboBox<String> box, HBox input) {
		String current = box.getValue();
		if(current.equals("Fun Fact")){
			currentState = 0;
			if(input.getChildren().contains(minLength)) {
				input.getChildren().remove(minLength);
				input.getChildren().remove(allWords);
			}
		}
		else if(current.equals("Anagram")) {
			currentState = 1;
			if(!input.getChildren().contains(minLength)) {
				input.getChildren().add(minLength);
				input.getChildren().add(allWords);
			}
		}
	}
	/**
	 * This is the main method to launch the program.
	 * 
	 * @param args
	 */
	public static void main (String[] args) {
		launch(args);
	}

}
