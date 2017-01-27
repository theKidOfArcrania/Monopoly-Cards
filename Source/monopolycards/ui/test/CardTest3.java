package monopolycards.ui.test;

import java.util.ArrayList;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopolycards.ui.virtual.*;

public class CardTest3 extends Application
{
	private static final Rectangle2D BOUNDS = Screen.getPrimary().getBounds();
	
	private PerspectiveCamera camera = new PerspectiveCamera();
	private Group root = new Group();
	private VrtHand hand;
	private VrtDeck drawDeck;
	private VrtDeck discardDeck;
	private ArrayList<VrtCard> cards;
	private Timeline running;
	
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
		
		drawDeck = new VrtDeck();
		drawDeck.setTranslateY(600);
		drawDeck.setTranslateX(600);
		drawDeck.setTranslateZ(2000);
		drawDeck.setRotateX(30); //Angle it upward
		
		discardDeck = new VrtDeck(true);
		discardDeck.setTranslateY(600);
		discardDeck.setTranslateX(800);
		discardDeck.setTranslateZ(2000);
		discardDeck.setRotateX(30); //Angle it upward
		
		hand = new VrtHand();
		hand.setTranslateY(BOUNDS.getHeight() - cards.get(0).getHeight());
		hand.setWidth(BOUNDS.getWidth());
		hand.setOnSelectHand(card -> {
			if (isAnimating())
				return;
			
			hand.getChildren().remove(card);
			
			MovementFrame frame = new MovementFrame();
			frame.setRotateX(0);
			frame.setRotateY(180);
			frame.setRotateZ(0);
			frame.setTranslateX((BOUNDS.getWidth() - card.getWidth()) / 2);
			frame.setTranslateY((BOUNDS.getHeight() - card.getHeight()) / 2);
			frame.setTranslateZ(-500);
			
			Timeline enlarge = new MovementTimeline(card).addFrame(VrtGroup.FAST_TRANS, frame).generateAnimation();
			enlarge.getKeyFrames().add(new KeyFrame(VrtGroup.MEDIUM_TRANS));
			enlarge.setOnFinished(evt -> {
				discardDeck.pushCard(card);
			});
			enlarge.play();
			running = enlarge;
		});
		
		root.getChildren().addAll(camera);
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
					int ind = discardDeck.getChildren().size();
					if (cards.size() == ind)
					{
						stop();
						return;
					}
					discardDeck.getChildren().add(cards.get(ind));
				}
			}
		};
		fillingDeck.setCycleCount(-1);
		fillingDeck.playFromStart();
		
		ArrayList<VrtCard> drawing = new ArrayList<>();
		hand.animatingProperty().addListener((val, before, after) -> {
			if (!after)
			{
				while (!drawing.isEmpty())
					hand.flipCard(drawing.remove(drawing.size() - 1));
			}
		});
		drawDeck.setOnDrawDeck(() -> {
			if (isAnimating())
				return;
			
			VrtCard removed = drawDeck.popCard();
			hand.getChildren().add(removed);
			drawing.add(removed);
		});
		discardDeck.setOnDrawDeck(() -> {
			if (isAnimating())
				return;
			while (!discardDeck.isEmpty())
				drawDeck.pushCard(discardDeck.popCard());
			drawDeck.shuffle();
		});
	}
	
	private boolean isAnimating()
	{
		return hand.isAnimating() || discardDeck.isAnimating() || drawDeck.isAnimating() 
				|| running != null && running.getStatus() == Status.RUNNING;
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
