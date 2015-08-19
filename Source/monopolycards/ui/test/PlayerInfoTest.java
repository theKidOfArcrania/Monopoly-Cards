package monopolycards.ui.test;

import java.awt.Dimension;
import java.awt.DisplayMode;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
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
		
		Text name = new Text("Player Name");
		name.textProperty().bind(new SimpleStringProperty("Player Name"));
		name.setFill(Color.BLACK);
		name.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 32));
		AnchorPane.setTopAnchor(name, 10.0);
	
		Text money = new Text("Money Amount:");
		money.setFill(Color.BLACK);
		money.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 13));
		AnchorPane.setTopAnchor(money, 55.0);
		AnchorPane.setLeftAnchor(money, 10.0);
		
		Text properties = new Text("Full Sets:");
		properties.setFill(Color.BLACK);
		properties.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 13));
		AnchorPane.setTopAnchor(properties, 110.0);
		AnchorPane.setLeftAnchor(properties, 10.0);
		
		
	
		
		
		root.getChildren().addAll(name, properties, money);
		Scene scene = new Scene(root, dispWidth/6, dispHeight/5);
		scene.setFill(Color.color(.95, .95, .95, .8));
		
		name.setWrappingWidth(scene.getWidth());
		name.setTextAlignment(TextAlignment.CENTER);
		
		primaryStage.setX(dispWidth/2-scene.getWidth()/2);
		primaryStage.setY(0);
		
		
		
		
		
		primaryStage.setScene(scene);
		scene.setCamera(cameraView);
		primaryStage.show();
		// enlarge.setAutoReverse(true);
	}
}
