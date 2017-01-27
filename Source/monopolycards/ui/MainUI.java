package monopolycards.ui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainUI extends AnchorPane
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
	
	private final Rectangle2D BOUNDS = Screen.getPrimary().getBounds();
	private Group tableRoot;
	
	public MainUI()
	{
		tableRoot = initTable();
		PerspectiveCamera camera = new PerspectiveCamera();
		tableRoot.getChildren().add(camera);
		
		SubScene tableScene = new SubScene(tableRoot, 0, 0, true, SceneAntialiasing.BALANCED);
		tableScene.widthProperty().bind(widthProperty());
		tableScene.heightProperty().bind(heightProperty());
		tableScene.setFill(Color.ALICEBLUE);
		tableScene.setCamera(camera);
		
		PlayerUI[] players = new PlayerUI[4];
		for (int i = 0; i < players.length; i++)
		{
			PlayerUI player = players[i] = new PlayerUI();
			AnchorPane.setLeftAnchor(player, 100.0 + i * (PlayerUI.WIDTH + 20));
		}
		
		getChildren().addAll(tableScene);
		getChildren().addAll(players);
	}
	
	private Group initTable()
	{
		Group root = new Group();
		
		Image imgWood = new Image(MainUI.class.getResourceAsStream("WoodTexture.jpg"));
		PhongMaterial wood = new PhongMaterial();
		wood.setDiffuseMap(imgWood);
		
		final double DEFLECT = 90.0; 
		double size = Math.max(BOUNDS.getHeight(), BOUNDS.getWidth());
		Box table = new Box(size, size, 50);
		table.setMaterial(wood);
		table.setTranslateX(size / 2);
		table.setTranslateY(BOUNDS.getHeight() - table.getDepth() * 4);
		table.setTranslateZ(size / 2 * Math.cos(Math.toRadians(DEFLECT)));
		table.setRotate(-DEFLECT);
		table.setRotationAxis(Rotate.X_AXIS);
		
		root.getChildren().add(table);
		return root;
	}
}
