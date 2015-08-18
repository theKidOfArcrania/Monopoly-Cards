package monopolycards.ui.test;

import java.awt.Dimension;
import java.awt.DisplayMode;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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

	public PlayerInfoTest() {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
	}

	private void init(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		Group root = new Group();
		Scene scene = new Scene(root, 380, 240);
		scene.setFill(Color.color(.7, .7, .7, .4));
		primaryStage.setScene(scene);
		scene.setCamera(cameraView);
		primaryStage.show();
		// enlarge.setAutoReverse(true);
	}
}
