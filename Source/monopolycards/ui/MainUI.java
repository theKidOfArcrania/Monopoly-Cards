package monopolycards.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainUI extends Pane
{
	public static class Main extends Application
	{
		private MainUI root;
		
		public void init()
		{
			root = new MainUI();
		}
		
		public void start(Stage primaryStage)
		{
			Scene s = new Scene(root);
			primaryStage.setScene(s);
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			primaryStage.show();
		}
	}
	
	private Group tableRoot;
	
	public MainUI()
	{
		tableRoot = new Group();
		SubScene tableScene = new SubScene(tableRoot, 0, 0, true, SceneAntialiasing.BALANCED);
		
	}
}
