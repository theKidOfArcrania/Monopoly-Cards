package monopolycards.ui.test;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopolycards.ui.virtual.VrtCard;

public class CardTest extends Application
{
	private PerspectiveCamera camera = new PerspectiveCamera();
	private Group root = new Group();
	private Transition rotate;
	
	public void init() throws Exception
	{
		Image cardBack = new Image(CardTest.class.getResourceAsStream("Card back.jpg"));
		Image dealBreakCard = new Image(CardTest.class.getResourceAsStream("Dealbreaker.jpg"));
		
		VrtCard card = new VrtCard();
		card.setBackImage(cardBack);
		card.setFrontImage(dealBreakCard);
		//card.setRotateZ(45);
		
		rotate = new Transition()
		{
			{
				setCycleDuration(Duration.seconds(3));
			}
			@Override
			protected void interpolate(double frac)
			{
				card.setRotateX(-frac * 360.0);
			}
		};
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(-1);
		
		PointLight light = new PointLight();
		PointLight light2 = new PointLight();
		PointLight light3 = new PointLight();
		light.setTranslateX(500);
		light.setTranslateY(500);
		light.setTranslateZ(-10000);
		light2.setTranslateX(10000);
		
		//camera.setTranslateX(-10);
		//camera.setTranslateY(-10);
		//camera.setTranslateZ(-1000);
		
		root.getChildren().addAll(card.getNode(), camera, light);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(root, 1000, 1000, true, SceneAntialiasing.BALANCED);
		scene.setCamera(camera);
		primaryStage.setScene(scene);
		primaryStage.show();
		rotate.playFromStart();
	}
	

	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
