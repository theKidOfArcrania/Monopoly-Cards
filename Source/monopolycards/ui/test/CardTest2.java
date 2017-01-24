package monopolycards.ui.test;

import java.util.ArrayList;

import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopolycards.ui.virtual.VrtCard;
import monopolycards.ui.virtual.VrtDeck;

public class CardTest2 extends Application
{
	private PerspectiveCamera camera = new PerspectiveCamera();
	private Group root = new Group();
	private VrtDeck deck;
	private ArrayList<VrtCard> cards;
	
	@Override
	public void init()
	{
		Image cardBack = new Image(CardTest.class.getResourceAsStream("Card back.jpg"));
		Image dealBreakCard = new Image(CardTest.class.getResourceAsStream("Dealbreaker.jpg"));
		
		cards = new ArrayList<>();
		for (int i = 0; i < 50; i++)
		{
			VrtCard card = new VrtCard();
			card.setBackImage(cardBack);
			card.setFrontImage(dealBreakCard);
			card.setTranslateZ(i * 2);
			root.getChildren().add(card.getNode());
			cards.add(card);
		}
		
		deck = new VrtDeck();
		deck.setTranslateY(600);
		deck.setTranslateX(350);
		deck.setRotateX(30); //Angle it upward
		
		PointLight light = new PointLight();
		light.setTranslateX(500);
		light.setTranslateY(600);
		light.setTranslateZ(-10000);
		
		root.getChildren().addAll(camera, light);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(root, 1000, 1000, true, SceneAntialiasing.BALANCED);
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
		t.setCycleCount(-1);
		t.playFromStart();
		//deck.getChildren().addAll(cards);
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
