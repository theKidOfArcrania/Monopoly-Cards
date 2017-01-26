package monopolycards.ui.test;

import java.util.ArrayList;

import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopolycards.ui.virtual.VrtCard;
import monopolycards.ui.virtual.VrtDeck;
import monopolycards.ui.virtual.VrtGroup;
import monopolycards.ui.virtual.VrtHand;

public class CardTest3 extends Application
{
	private static final Rectangle2D BOUNDS = Screen.getPrimary().getBounds();
	
	private PerspectiveCamera camera = new PerspectiveCamera();
	private Group root = new Group();
	private VrtHand hand;
	private VrtDeck deck;
	private ArrayList<VrtCard> cards;
	
	@Override
	public void init()
	{
		
		Image cardBack = new Image(CardTest.class.getResourceAsStream("Card back.jpg"));
		Image dealBreakCard = new Image(CardTest.class.getResourceAsStream("Dealbreaker.jpg"));
		
		cards = new ArrayList<>();
		for (int i = 0; i < 100; i++)
		{
			VrtCard card = new VrtCard();
			card.setBackImage(cardBack);
			card.setFrontImage(dealBreakCard);
			card.setTranslateZ(i * 200);
			root.getChildren().add(card.getNode());
			cards.add(card);
		}
		
		deck = new VrtDeck();
		deck.setTranslateY(600);
		deck.setTranslateX(600);
		deck.setTranslateZ(1000);
		deck.setRotateX(30); //Angle it upward
		
		hand = new VrtHand();
		hand.setTranslateY(BOUNDS.getHeight() - cards.get(0).getHeight());
		hand.setWidth(BOUNDS.getWidth());
		
		PointLight light = new PointLight();
		light.setTranslateX(500);
		light.setTranslateY(600);
		light.setTranslateZ(-10000);
		
		root.getChildren().addAll(camera/*, light*/);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(root, 1300, 750, true, SceneAntialiasing.BALANCED);
		scene.setCamera(camera);
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.show();
		
		
		Transition fillDeck = new Transition()
		{
			{
				setCycleDuration(Duration.millis(20));
			}
			@Override
			protected void interpolate(double frac)
			{
				if (frac == 1)
				{
					int ind = deck.getChildren().size();
					if (cards.size() == ind)
					{
						stop();
						return;
					}
					deck.getChildren().add(cards.get(ind));
				}
			}
		};
		fillDeck.setCycleCount(-1);
		fillDeck.playFromStart();
		
		Transition drawDeck = new Transition()
		{
			{
				setCycleDuration(Duration.seconds(3));
			}
			
			@Override
			public void interpolate(double frac)
			{
				if (frac == 1)
				{
					VrtCard removed = deck.popCard();
					hand.getChildren().add(removed);
					Transition wait = new Transition()
					{
						{
							setCycleDuration(VrtGroup.MEDIUM_TRANS);
						}
						@Override
						protected void interpolate(double frac)
						{
							if (frac == 1)
							{
								hand.flipCard(removed);
							}
						}
					};
					wait.play();
				}
			}
		};
		drawDeck.setCycleCount(10);
		drawDeck.setDelay(Duration.seconds(3));
		drawDeck.playFromStart();
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
