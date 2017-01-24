package monopolycards.ui.test;

import java.util.ArrayList;

import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopolycards.ui.virtual.VrtCard;
import monopolycards.ui.virtual.VrtHand;

public class CardTest3 extends Application
{
	private static final Rectangle2D BOUNDS = Screen.getPrimary().getBounds();
	
	private PerspectiveCamera camera = new PerspectiveCamera();
	private Group root = new Group();
	private VrtHand hand;
	private ArrayList<VrtCard> cards;
	
	@Override
	public void init()
	{
		
		Image cardBack = new Image(CardTest.class.getResourceAsStream("Card back.jpg"));
		Image dealBreakCard = new Image(CardTest.class.getResourceAsStream("Dealbreaker.jpg"));
		
		cards = new ArrayList<>();
		for (int i = 0; i < 5; i++)
		{
			VrtCard card = new VrtCard();
			card.setBackImage(cardBack);
			card.setFrontImage(dealBreakCard);
			card.setTranslateZ(i * 2);
			root.getChildren().add(card.getNode());
			cards.add(card);
		}
		
		hand = new VrtHand();
		hand.setTranslateY(BOUNDS.getHeight() - cards.get(0).getHeight());
		
		PointLight light = new PointLight();
		light.setTranslateX(500);
		light.setTranslateY(600);
		light.setTranslateZ(-10000);
		
		root.getChildren().addAll(camera, light);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(root, 1300, 750, true, SceneAntialiasing.BALANCED);
		scene.setCamera(camera);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Transition t = new Transition()
		{
			{
				setCycleDuration(Duration.millis(20));
			}
			@Override
			protected void interpolate(double frac)
			{
				if (frac == 1)
				{
					int ind = hand.getChildren().size();
					if (cards.size() == ind)
					{
						stop();
						return;
					}
					hand.getChildren().add(cards.get(ind));
				}
			}
		};
		Transition wait = new Transition()
		{
			{
				setCycleDuration(Duration.seconds(4));
			}
			protected void interpolate(double frac)
			{
				if (frac == 1)
				{
					hand.flipAll();
				}
			}
		};
		wait.setCycleCount(-1);
		wait.play();
		t.setCycleCount(-1);
		t.playFromStart();
		//deck.getChildren().addAll(cards);
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
