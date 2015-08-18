package monopolycards.ui.test;

import java.awt.Dimension;
import java.awt.DisplayMode;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

public class PlayerInfoTest extends Application {

	private static final double ANCHOR = 10.0;

	public static void main(String[] args) {
		launch(args);
	}

	private final PerspectiveCamera cameraView = new PerspectiveCamera();
	private final DisplayMode defaultMode = getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode();
	private final Dimension displayRes = new Dimension(defaultMode.getWidth(), defaultMode.getHeight());
	private final Rotate cameraRX = new Rotate(-20, displayRes.getWidth() / 2, displayRes.getHeight() / 2, 0, Rotate.X_AXIS);
	private final Rotate cameraRY = new Rotate(0, displayRes.getWidth() / 2, displayRes.getHeight() / 2, 0, Rotate.Y_AXIS);
	public final double dispWidth = displayRes.getWidth();
	public final double dispHeight = displayRes.getHeight();
	public PlayerInfoTest() {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
	}

	private void init(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		AnchorPane root = new AnchorPane();
		
		Text txt = new Text("Player Name");
		txt.setFill(Color.BLACK);
		AnchorPane.setTopAnchor(txt, 10.0);
		
		txt.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 40));
		
		root.getChildren().add(txt);
		Scene scene = new Scene(root, dispWidth/5, dispHeight/4);
		scene.setFill(Color.color(.95, .95, .95, .8));
		txt.setWrappingWidth(scene.getWidth());
		txt.setTextAlignment(TextAlignment.CENTER);
		primaryStage.setX(dispWidth/2-scene.getWidth()/2);
		primaryStage.setY(0);
		
		
		
		
		
		primaryStage.setScene(scene);
		scene.setCamera(cameraView);
		primaryStage.show();
		// enlarge.setAutoReverse(true);
	}
}
