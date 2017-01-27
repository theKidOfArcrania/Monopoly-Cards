package monopolycards.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainUI extends StackPane
{
	public static class Main extends Application
	{
		private MainUI root;
		
		@Override
		public void init()
		{
			root = new MainUI();
		}
		
		@Override
		public void start(Stage primaryStage)
		{
			Scene s = new Scene(root);
			primaryStage.setScene(s);
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			primaryStage.show();
		}
		
		public static void main(String[] args)
		{
			Application.launch(args);
		}
	}
	
	private Group tableRoot;
	
	public MainUI()
	{
		tableRoot = initTable();
		
		SubScene tableScene = new SubScene(tableRoot, 0, 0, true, SceneAntialiasing.BALANCED);
		tableScene.widthProperty().bind(widthProperty());
		tableScene.heightProperty().bind(heightProperty());
		tableScene.setFill(Color.ALICEBLUE);		
		
		getChildren().add(tableScene);
	}
	
	private Group initTable()
	{
		Group root = new Group();
		return root;
	}
}
