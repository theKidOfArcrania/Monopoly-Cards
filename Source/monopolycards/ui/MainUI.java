package monopolycards.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainUI extends StackPane
{
	private static final Image DEFAULT_PROFILE_IMAGE = Tools.createFXImage("DefaultProfile.png");
	private static final Image GEAR_IMAGE = Tools.createFXImage("gear.png");
	private static final Image GEAR_PRESSED_IMAGE = Tools.createFXImage("gear_pressed.png");
	private static final Rectangle2D BOUNDS = Screen.getPrimary().getBounds();
	
	public static final double SCALE_X = BOUNDS.getWidth() / 1920.0;
	public static final double SCALE_Y = BOUNDS.getHeight() / 1080.0;
	public static final double SCALE_MIN = Math.min(SCALE_X, SCALE_Y);
	
	public static class Main extends Application
	{
		private MainUI root;
		
		@Override
		public void init()
		{
			Font.loadFont(Main.class.getResourceAsStream("KabaleMedium-Normal.ttf"), 1);
			root = new MainUI();
		}
		
		@Override
		public void start(Stage primaryStage)
		{
			Scene s = new Scene(root);
			s.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
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
	
	private final Group tableRoot;
	private final Pane floatingUI;
	
	private final PlayerUI[] players;
	private final StatusUI status;
	
	public MainUI()
	{
		//Initializing some UI
		players = new PlayerUI[4]; //TODO: change number of players.
		status = new StatusUI();
		
		//Floating UI elements
		floatingUI = initFloatingUI();
		
		//3D scene
		tableRoot = initTable();
		PerspectiveCamera camera = new PerspectiveCamera();
		tableRoot.getChildren().add(camera);
		
		SubScene tableScene = new SubScene(tableRoot, 0, 0, true, SceneAntialiasing.BALANCED);
		tableScene.widthProperty().bind(widthProperty());
		tableScene.heightProperty().bind(heightProperty());
		tableScene.setFill(Color.ALICEBLUE);
		tableScene.setCamera(camera);
		
		getChildren().addAll(tableScene, floatingUI);
	}
	
	private Pane initFloatingUI()
	{
		AnchorPane root = new AnchorPane();
		
		/*****************
		 * Player Pane
		 *****************/
		HBox playerPane = new HBox();
		playerPane.setPickOnBounds(false);
		for (int i = 0; i < players.length; i++)
		{
			StackPane playerStats = new StackPane();
			
			//Highlight used when user mouseovers.
			Pane highlight = new Pane();
			highlight.setBackground(new Background(new BackgroundFill(Color.gray(1, .3), null, null)));
			highlight.setVisible(false);
			
			Pane shadow = new Pane();
			shadow.setBackground(new Background(new BackgroundFill(Color.gray(.5, .3), null, null)));
			shadow.setVisible(false);
			
			//Contains all the player's quick stats display
			PlayerUI display = players[i] = new PlayerUI();
			display.setPlayerName("Mr. Nice-Guy-The-Player");
			display.setCash(15000000);
			display.setPropSets(2);
			display.setProfileImage(DEFAULT_PROFILE_IMAGE);
			
			//Handlers for when the user mouseovers (stats enlarge and highlight)
			Scale statsScale = new Scale(1, 1);
			statsScale.setPivotY(0);
			statsScale.pivotXProperty().bind(playerStats.widthProperty().divide(2));
			playerStats.getTransforms().add(statsScale);
			
			playerStats.hoverProperty().addListener(val -> {
				if (playerStats.isHover())
				{
					double scale = playerStats.isPressed() ? .96 : 1.04;
					statsScale.setX(scale);
					statsScale.setY(scale);
					highlight.setVisible(!playerStats.isPressed());
				}
				else
				{
					statsScale.setX(1);
					statsScale.setY(1);
					highlight.setVisible(false);
				}
			});
			
			playerStats.pressedProperty().addListener(val -> {
				if (playerStats.isPressed())
				{
					statsScale.setX(.96);
					statsScale.setY(.96);
					highlight.setVisible(false);
					shadow.setVisible(true);
				}
				else
				{
					double scale = playerStats.isHover() ? 1.04 : 1;
					statsScale.setX(scale);
					statsScale.setY(scale);
					highlight.setVisible(playerStats.isHover());
					shadow.setVisible(false);
				}
			});
			
			//TODO: quickly flash card twice and then show extended stats when clicked.
			
			playerStats.getChildren().addAll(display, highlight, shadow);
			playerPane.getChildren().add(playerStats);
			
			//Create 20px gaps between player stat cards
			if (i > 0)
				HBox.setMargin(playerStats, new Insets(0, 0, 0, 20));
			
			//Rounded stuff.
			Tools.roundCorners(playerStats);
			playerStats.setTranslateY(-Tools.CORNER_RADIUS - 1); //Shove the top rounds "under the rug"
		}
		ScalerParent scaledPlayerPane = scaleToScreen(playerPane);
		AnchorPane.setRightAnchor(scaledPlayerPane, 150.0 * SCALE_X);
		
		/*****************
		 * Status description pane
		 *****************/
		status.setTitle("It's Your Turn!");
		status.setDescription("A sample description: Click on the card icon or the deck of cards to draw some cards " +
				"and start your turn!");
		status.setTurnNum(1);
		status.setMoves(3);
		
		//Rounded stuff.
		Tools.roundCorners(status);
		status.setTranslateY(-Tools.CORNER_RADIUS - 1); //Shove the top rounds "under the rug"
		status.setTranslateX(-Tools.CORNER_RADIUS - 1);
		
		/*****************
		 * Settings gear button
		 *****************/
		ButtonUI gearButton = new ButtonUI();
		gearButton.setMinSize(50, 50);
		gearButton.setPrefSize(50, 50);
		gearButton.setMaxSize(50, 50);
		gearButton.setImage(GEAR_IMAGE);
		gearButton.setPressedImage(GEAR_PRESSED_IMAGE);
		
		ScalerParent scaledButton = scaleToScreen(gearButton);
		AnchorPane.setRightAnchor(scaledButton, 0.0 /*10.0 * SCALE_MIN*/);
		AnchorPane.setTopAnchor(scaledButton, 0.0 /*10.0 * SCALE_MIN*/);
		
		/*****************
		 * Putting 'em together
		 *****************/
		root.getChildren().addAll(scaledPlayerPane, scaleToScreen(status), scaledButton);
		root.setPickOnBounds(false);
		return root;
	}
	
	private Group initTable()
	{
		Group root = new Group();
		
		Image imgWood = Tools.createFXImage("WoodTexture.jpg");
		PhongMaterial wood = new PhongMaterial();
		wood.setDiffuseMap(imgWood);
		
		final double DEFLECT = 60.0; 
		final double DEPTH = 50;
		double cos = Math.cos(Math.toRadians(DEFLECT));
		double sin = Math.sin(Math.toRadians(DEFLECT));
		double size = Math.max(BOUNDS.getHeight(), BOUNDS.getWidth());
		Box table = new Box(size, size, DEPTH);
		table.setMaterial(wood);
		table.setTranslateX(size / 2);
		table.setTranslateY(BOUNDS.getHeight() - size / 2 * cos);
		table.setTranslateZ(size / 2 * sin);
		table.setRotate(-DEFLECT);
		table.setRotationAxis(Rotate.X_AXIS);
		
		root.getChildren().add(table);
		return root;
	}
	
	private ScalerParent scaleToScreen(Node node)
	{
		ScalerParent scale = new ScalerParent(node);
		scale.setPreserveRatio(true);
		scale.setScalingX(SCALE_X);
		scale.setScalingY(SCALE_Y);
		return scale;
	}
}
