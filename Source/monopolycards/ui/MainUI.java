package monopolycards.ui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopolycards.card.Card;
import monopolycards.card.Cash;
import monopolycards.card.Property;
import monopolycards.card.PropertyColor;
import monopolycards.ui.test.CardTest;
import monopolycards.ui.virtual.VrtCard;
import monopolycards.ui.virtual.VrtDeck;
import monopolycards.ui.virtual.VrtHand;
import monopolycards.ui.virtual.VrtNode;

public class MainUI extends StackPane
{
	public static final Rectangle2D BOUNDS = Screen.getPrimary().getBounds();
	
	public static final double SCALE_X = BOUNDS.getWidth() / 1920.0;
	public static final double SCALE_Y = BOUNDS.getHeight() / 1080.0;
	public static final double SCALE_MIN = Math.min(SCALE_X, SCALE_Y);
	
	public static final double TABLE_SIZE = BOUNDS.getWidth() * 3 / 4;
	public static final double TABLE_DEFLECT = 60.0; 
	public static final double TABLE_DEPTH = 50;
	
	public static final double SIN_DEFLECT = Math.sin(Math.toRadians(TABLE_DEFLECT));
	public static final double COS_DEFLECT = Math.cos(Math.toRadians(TABLE_DEFLECT));
	
	public static final double CARD_FACTOR = 9;
	public static final double CARD_WIDTH = VrtCard.CARD_WIDTH_FACTOR * CARD_FACTOR * SCALE_X;
	public static final double CARD_HEIGHT = VrtCard.CARD_HEIGHT_FACTOR * CARD_FACTOR * SCALE_X;
	
	public static final int MAX_PLAYERS = 4;
	
	public static final double DECK_SPACING = 10;

	private static final Image DEFAULT_PROFILE_IMAGE = Tools.createFXImage("DefaultProfile.png");
	private static final Image GEAR_IMAGE = Tools.createFXImage("gear.png");
	private static final Image GEAR_PRESSED_IMAGE = Tools.createFXImage("gear_pressed.png");

	
	
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
			
			root.start();
		}
		
		public static void main(String[] args)
		{
			Application.launch(args);
		}
	}
	
	private class PlayerQuadrant {
		private final VrtDeck moneyDeck;
		private final ArrayList<VrtDeck> propDecks;
		private final HashMap<PropertyColor, Integer> columns;
		private final VrtHand hand;
		
		private int pos;
		
		public PlayerQuadrant(int pos)
		{
			if (pos < 0 || pos >= MAX_PLAYERS)
				throw new IllegalArgumentException("pos must be in the range [0, 3]");
			
			moneyDeck = createDeck(0, 0);
			propDecks = new ArrayList<>();
			columns = new HashMap<>();
			hand = new VrtHand();
			
			this.pos = pos;
			
		}
		
		public void addCash(Cash money)
		{
			moneyDeck.pushCard(cardUI.get(money));
		}
		
		public void addProperty(Property prop)
		{
			addToColumn(prop, prop.getDualColors().getPropertyColor());
		}
		
		public void addToColumn(Card prop, PropertyColor color)
		{
			int columnInd = columns.getOrDefault(color, -1);
			VrtDeck column;
			if (columnInd == -1)
			{
				columnInd = propDecks.size();
				column = createDeck(columnInd % 2, columnInd / 2);
			}
			else
				column = propDecks.get(columnInd);
			
			column.pushCard(cardUI.get(prop));
		}
		
		public void setPos(int pos)
		{
			if (pos < 0 || pos >= MAX_PLAYERS)
				throw new IllegalArgumentException("pos must be in the range [0, 3]");
			this.pos = pos;
			layoutDeck(moneyDeck, 0, 0);
			for (int i = 0; i < propDecks.size(); i++)
				layoutDeck(propDecks.get(i), i % 2, i / 2);
		}
		
		private VrtDeck createDeck(int row, int col)
		{
			VrtDeck deck = new VrtDeck(true);
			deck.setRotateX(90 - TABLE_DEFLECT);
			layoutDeck(deck, row, col);
			return deck;
		}
		
		private void layoutHand()
		{
			int x = 0;
			int y = 0;
			
			switch (pos)
			{
			case 1: 
				x 
			}
			
			if (pos == 0)
			{
				hand.setRotateX(0);
				hand.setRotateY(0);
				hand.setRotateZ(0);
				hand.setTranslateX(0);
				hand.setTranslateY(BOUNDS.getHeight() - CARD_HEIGHT / 2);
				hand.setTranslateZ(0);
				hand.setWidth(TABLE_SIZE);
			}
			else
			{
				
			}
		}
		
		private void layoutDeck(VrtDeck deck, int row, int col)
		{
			double xStart = col * (CARD_WIDTH + DECK_SPACING) + DECK_SPACING + CARD_WIDTH / 2;
			double yStart = -row * (CARD_HEIGHT + DECK_SPACING) - DECK_SPACING + CARD_HEIGHT / 2;
			
			if (col > 0)
				xStart += DECK_SPACING;
			
			switch (pos)
			{
			case 0: 
				yStart += TABLE_SIZE;
				break;
			case 1:
				double tmp = xStart;
				xStart = -yStart;
				yStart = tmp;
				break;
			case 2:
				yStart = -yStart;
				xStart = TABLE_SIZE - xStart;
				break;
			case 3:
				tmp = xStart;
				xStart = yStart + TABLE_SIZE;
				yStart = TABLE_SIZE - tmp;
			}
			deck.setRotateZ(pos * 90);
			if (!deck.isEmpty())
				deck.updatePosition();
		}

		public int getPos()
		{
			return pos;
		}
	}
	
	Image cardBack = new Image(CardTest.class.getResourceAsStream("Card back.jpg"));
	Image dealBreakCard = new Image(CardTest.class.getResourceAsStream("Dealbreaker.jpg"));
	
	private final HashMap<Card, VrtCard> cardUI = new HashMap<>();
	
	private final Group tableRoot;
	private final Pane floatingUI;
	
	private final VrtDeck drawDeck;
	private final VrtDeck discardDeck;
	
	private final PlayerUI[] players;
	private final PlayerQuadrant[] field;
	private final StatusUI status;
	
	public MainUI()
	{
		//Initializing some UI
		players = new PlayerUI[4]; //TODO: change number of players.
		field = new PlayerQuadrant[4];
		status = new StatusUI();
		drawDeck = new VrtDeck();
		discardDeck = new VrtDeck(true);
		
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
	
	public void start()
	{
		double CENTER = TABLE_SIZE / 2;
		Transition fillingDeck = new Transition()
		{
			{
				setCycleDuration(Duration.millis(20));
			}
			@Override
			protected void interpolate(double frac)
			{
				if (frac == 1)
				{
					VrtCard card = createCard();
					positionOnTable(card, CENTER - DECK_SPACING / 2 - CARD_WIDTH / 2, CENTER);
					card.setTranslateY(card.getTranslateY() - 200);
					
					tableRoot.getChildren().add(card.getNode());
					drawDeck.pushCard(card);
				}
			}
		};
		fillingDeck.setDelay(Duration.seconds(.5));
		fillingDeck.setCycleCount(100); //TODO: change this value.
		fillingDeck.playFromStart();
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
		status.setTranslateY(-Tools.CORNER_RADIUS); //Shove the top rounds "under the rug"
		status.setTranslateX(-Tools.CORNER_RADIUS);
		
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
		root.getChildren().addAll(scaledPlayerPane, scaleToScreen(new StackPane(status)), 
				scaledButton);
		root.setPickOnBounds(false);
		return root;
	}
	
	private Group initTable()
	{
		Group root = new Group();
		final double CENTER = TABLE_SIZE / 2;
		
		/*****************
		 * Setup the table
		 *****************/
		Image imgWood = Tools.createFXImage("WoodTexture.jpg");
		PhongMaterial wood = new PhongMaterial();
		wood.setDiffuseMap(imgWood);
		
		Box table = new Box(TABLE_SIZE, TABLE_SIZE, TABLE_DEPTH);
		table.setMaterial(wood);
		table.setTranslateX(CENTER);
		table.setTranslateY(BOUNDS.getHeight() - CENTER * COS_DEFLECT + 7);
		table.setTranslateZ(CENTER * SIN_DEFLECT);
		table.setRotate(-TABLE_DEFLECT);
		table.setRotationAxis(Rotate.X_AXIS);
		
		/*****************
		 * Initialize center decks.
		 *****************/
		double offset = DECK_SPACING / 2 + CARD_WIDTH / 2;  
		positionOnTable(drawDeck, CENTER - offset, CENTER);
		positionOnTable(discardDeck, CENTER + offset, CENTER);
		
		drawDeck.setRotateX(90 - TABLE_DEFLECT);
		discardDeck.setRotateX(90 - TABLE_DEFLECT);
		root.getChildren().addAll(table);
		
		/*****************
		 * Initialize player fields
		 *****************/
		if (field.length == 2)
		{
			field[0] = new PlayerQuadrant(0);
			field[1] = new PlayerQuadrant(2);
		}
		else
		{
			for (int i = 0; i < field.length; i++)
				field[i] = new PlayerQuadrant(i);
		}
			
		
		return root;
	}
	
	private void switchPlayers(int newPlayerIndex)
	{
		int shift = MAX_PLAYERS - field[newPlayerIndex].getPos();
		if (shift == MAX_PLAYERS)
			return;
		for (PlayerQuadrant quad : field)
			quad.setPos((quad.getPos() + shift) % MAX_PLAYERS); 
		//TODO: revealing/ hiding player's hand
	}
	
	private ScalerParent scaleToScreen(Node node)
	{
		ScalerParent scale = new ScalerParent(node);
		scale.setPreserveRatio(true);
		scale.setScalingX(SCALE_X);
		scale.setScalingY(SCALE_Y);
		return scale;
	}
	
	private VrtCard createCard()
	{
		VrtCard card = new VrtCard();
		card.setWidth(CARD_WIDTH);
		card.setHeight(CARD_HEIGHT);
		card.setBackImage(cardBack);
		card.setFrontImage(dealBreakCard); //TODO: this should be a blank card.
		return card;
	}
	
	private void positionOnTable(VrtNode node, double xTable, double zTable)
	{
		node.setTranslateX(xTable);
		node.setTranslateY(BOUNDS.getHeight() - TABLE_DEPTH / 2 * SIN_DEFLECT - zTable * COS_DEFLECT);
		node.setTranslateZ(zTable * SIN_DEFLECT);
		node.setRotateX(-TABLE_DEFLECT);
	}
	
	private Point3D positionOnTable(double xTable, double zTable)
	{
		double y = BOUNDS.getHeight() - TABLE_DEPTH / 2 * SIN_DEFLECT - zTable * COS_DEFLECT;
		double z = zTable * SIN_DEFLECT;
		
		return new Point3D(xTable, y, z);
	}
}
