package monopolycards.ui.test;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopolycards.ui.CardUI;

public class CardTest extends Application
{
	PerspectiveCamera camera = new PerspectiveCamera();
	Group root = new Group();
	RotateTransition rotate = new RotateTransition(Duration.seconds(3));
	public void init() throws Exception
	{
		Image cardBack = new Image(CardTest.class.getResourceAsStream("Card back.jpg"));
		Image dealBreakCard = new Image(CardTest.class.getResourceAsStream("Dealbreaker.jpg"));
		
		CardUI card = new CardUI();
		card.setBackImage(cardBack);
		card.setFrontImage(dealBreakCard);
		
		rotate.setAxis(Rotate.Y_AXIS);
		rotate.setNode(card);
		rotate.setFromAngle(0);
		rotate.setToAngle(360);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(-1);
		
		PointLight light = new PointLight();
		PointLight light2 = new PointLight();
		PointLight light3 = new PointLight();
		light.setTranslateX(500);
		light.setTranslateY(500);
		light.setTranslateZ(-10000);
		light2.setTranslateX(10000);
		
		camera.setTranslateX(100);
		camera.setTranslateY(100);
		camera.setTranslateZ(-1000);
		
		root.getChildren().addAll(card, light, camera, light2, light3);
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
