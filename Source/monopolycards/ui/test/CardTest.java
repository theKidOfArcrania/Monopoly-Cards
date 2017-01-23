package monopolycards.ui.test;

import javafx.animation.Timeline;
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
import monopolycards.ui.virtual.VrtGroup;

public class CardTest extends Application
{
	private PerspectiveCamera camera = new PerspectiveCamera();
	private Group root = new Group();
	private Transition rotate;
	
	private Timeline cardMovement;
	
	private VrtCard card = new VrtCard();
	
	@Override
	public void init() throws Exception
	{
		Image cardBack = new Image(CardTest.class.getResourceAsStream("Card back.jpg"));
		Image dealBreakCard = new Image(CardTest.class.getResourceAsStream("Dealbreaker.jpg"));
		
		
		card.setBackImage(cardBack);
		card.setFrontImage(dealBreakCard);
		
		card.setRotateX(-90);
		card.setTranslateY(600);
		card.setTranslateX(350);
		
		cardMovement = VrtGroup.createAnimation(card);
		VrtGroup.toRotate(card, cardMovement, Duration.seconds(2), 0, 0, 0);
		VrtGroup.toTranslate(card, cardMovement, Duration.ZERO, Duration.seconds(2), 350, 300, 0);
		
		VrtGroup.toRotate(card, cardMovement, Duration.seconds(1), 0, 180, 0);
		VrtGroup.toTranslate(card, cardMovement, Duration.seconds(2), Duration.seconds(1), 350, 300, -1000);
		
		VrtGroup.toRotate(card, cardMovement, Duration.seconds(1), 0, 180, 0);
		
		//rotate.setInterpolator(Interpolator.LINEAR);
		cardMovement.setDelay(Duration.seconds(.5));
		cardMovement.setCycleCount(-1);
		//rotate.setCycleCount(-1);
		
		PointLight light = new PointLight();
		light.setTranslateX(500);
		light.setTranslateY(500);
		light.setTranslateZ(-10000);
		
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
		
		cardMovement.playFromStart();
	}
	

	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
