package monopolycards.ui.test;

import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopolycards.ui.virtual.MovementFrame;
import monopolycards.ui.virtual.MovementTimeline;
import monopolycards.ui.virtual.VrtCard;

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
		
		MovementTimeline timeline = new MovementTimeline(card);
		MovementFrame frame = new MovementFrame();
		
		frame.setRotateX(0);
		frame.setTranslateY(300);
		timeline.addFrame(Duration.seconds(2), frame);
		
		frame.setRotateY(180);
		frame.setTranslateZ(-1000);
		timeline.addFrame(Duration.seconds(1), frame);
		
		cardMovement = timeline.generateAnimation();
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
