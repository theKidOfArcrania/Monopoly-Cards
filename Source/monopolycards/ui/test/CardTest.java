package monopolycards.ui.test;

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

public class CardTest extends Application
{
	private PerspectiveCamera camera = new PerspectiveCamera();
	private Group root = new Group();
	private Transition rotate;
	private VrtCard card = new VrtCard();
	private void resetAnimation()
	{
		card.setRotateY(0);
		card.setRotateX(-90);
		card.setTranslateY(600);
		card.setTranslateX(350);
		card.setTranslateZ(0);
		rotate.playFromStart();
	}
	
	@Override
	public void init() throws Exception
	{
		Image cardBack = new Image(CardTest.class.getResourceAsStream("Card back.jpg"));
		Image dealBreakCard = new Image(CardTest.class.getResourceAsStream("Dealbreaker.jpg"));
		
		
		card.setBackImage(cardBack);
		card.setFrontImage(dealBreakCard);
		
		rotate = new Transition()
		{
			{
				setCycleDuration(Duration.seconds(3));
			}
			@Override
			protected void interpolate(double frac)
			{
				frac *= 2;
				if (frac >= 1)
				{
					frac--;
					card.setRotateY(-frac * 180.0);
					card.setTranslateZ(-frac * 1000.0);	
				}
				else
				{
					card.setRotateX(-90 + frac * 90.0);
					card.setTranslateY(600 - frac * 300.0);
				}
			}
		};
		//rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setDelay(Duration.seconds(.5));
		rotate.setOnFinished(evt -> resetAnimation());
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
		
		resetAnimation();
	}
	

	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
