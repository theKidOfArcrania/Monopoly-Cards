<<<<<<< HEAD
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
=======
package monopolycards.ui.test;

import java.awt.Dimension;
import java.awt.DisplayMode;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
	//user variables
	private SimpleIntegerProperty moneyValue;
	private SimpleStringProperty playerName;
	private SimpleDoubleProperty alph;
	private SimpleIntegerProperty setNumber;
	
	
	private final Timeline solid = new Timeline();
	
	public PlayerInfoTest() {
		alph = new SimpleDoubleProperty(.4);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
	}

	private void init(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		
		AnchorPane root = new AnchorPane();
		
		Text name = new Text("Player Name");
		//name.textProperty().bind(playerName);
		name.setFill(Color.BLACK);
		name.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 34));
		AnchorPane.setTopAnchor(name, 30.0);
		AnchorPane.setLeftAnchor(name,90.0);
	
		Text money = new Text("Money:");
		money.setFill(Color.BLACK);
		money.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 20));
		AnchorPane.setTopAnchor(money, 90.0);
		AnchorPane.setLeftAnchor(money, 75.0);
		
		Text properties = new Text("Full Sets:");
		properties.setFill(Color.BLACK);
		properties.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 20));
		AnchorPane.setTopAnchor(properties, 145.0);
		AnchorPane.setLeftAnchor(properties, 75.0);
		
		Text moneyDisplay = new Text("0");
		//moneyDisplay.textProperty().bind(new SimpleStringProperty(moneyValue.getValue()+""));
		moneyDisplay.setFill(Color.BLACK);
		moneyDisplay.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 20));
		AnchorPane.setTopAnchor(moneyDisplay, 90.0);
		AnchorPane.setLeftAnchor(moneyDisplay, 150.0);
		
		Text setDisplay = new Text("0");
		//setDisplay.textProperty().bind(new SimpleStringProperty(setNumber.getValue()+""));
		setDisplay.setFill(Color.BLACK);
		setDisplay.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 20));
		AnchorPane.setTopAnchor(setDisplay, 145.0);
		AnchorPane.setLeftAnchor(setDisplay, 160.0);
		
		Image profileImage = new Image((PlayerInfoTest.class.getResource("blank-profile.jpg").toExternalForm()));
		ImageView profileView = new ImageView();
		profileView.setImage(profileImage);
		profileView.setFitHeight(60);
		profileView.setFitWidth(60);
		profileView.setPreserveRatio(true);
        profileView.setSmooth(true);
        profileView.setCache(true);
        DropShadow shade1 = new DropShadow();
        shade1.setRadius(4.0);
        
        profileView.setEffect(shade1);
        AnchorPane.setTopAnchor(profileView, 10.0);
        AnchorPane.setLeftAnchor(profileView, 15.0);
        
		//moneypic
		Image moneyPic = new Image((PlayerInfoTest.class.getResource("Dollar Sign.png").toExternalForm()));
		ImageView moneyView = new ImageView();
		moneyView.setImage(moneyPic);
		moneyView.setFitHeight(50);
		moneyView.setFitWidth(50);
		moneyView.setPreserveRatio(true);
        moneyView.setSmooth(true);
        moneyView.setCache(true);
        AnchorPane.setTopAnchor(moneyView, 80.0);
        AnchorPane.setLeftAnchor(moneyView, 25.0);
        
        //fullsets pic
        Image cardPic = new Image((PlayerInfoTest.class.getResource("fullset.png").toExternalForm()));
        ImageView cardView = new ImageView();
		cardView.setImage(cardPic);
		cardView.setFitHeight(50);
		cardView.setFitWidth(50);
		cardView.setPreserveRatio(true);
        cardView.setSmooth(true);
        cardView.setCache(true);
        DropShadow shade = new DropShadow();
        shade.setRadius(2.0);
        shade.setOffsetX(-1.0);
        shade.setOffsetY(1.0);
        cardView.setEffect(shade);
        AnchorPane.setTopAnchor(cardView, 135.0);
        AnchorPane.setLeftAnchor(cardView, 15.0);
        
        Scene scene = new Scene(root, dispWidth/5.5, dispHeight/4.5);
		scene.setFill(Color.color(.90, .90, .90,.4));
		
	    
		root.getChildren().addAll(name, properties, money, moneyView, moneyDisplay, setDisplay, cardView, profileView);
		root.opacityProperty().bind(alph);
   
		scene.setOnMouseEntered(e->trans(true));
		scene.setOnMouseExited(e->trans(false));
		root.setId("ROOTNODE");
		
		scene.getStylesheets().add("/monopolycards/ui/test/border.css");
		primaryStage.setX(dispWidth/2-scene.getWidth()/2);
		primaryStage.setY(0);
		
		primaryStage.setScene(scene);
		scene.setCamera(cameraView);
		primaryStage.show();
		//Timeline
		
		//start
		solid.getKeyFrames()
		.add(new KeyFrame(Duration.ZERO, new KeyValue(alph, .4),
				new KeyValue(scene.fillProperty(), Color.color(.90, .90, .90,.4))));
		
		solid.getKeyFrames()
		.add(new KeyFrame(Duration.seconds(.05), new KeyValue(alph, .6), 
				new KeyValue(scene.fillProperty(), Color.color(.90, .90, .90,.6))));
		
		solid.getKeyFrames()
		.add(new KeyFrame(Duration.seconds(.1), new KeyValue(alph, .8),
				new KeyValue(scene.fillProperty(), Color.color(.90, .90, .90,.8))));
		solid.getKeyFrames()
		.add(new KeyFrame(Duration.seconds(.15), new KeyValue(alph, 1.0),
				new KeyValue(scene.fillProperty(), Color.color(.90, .90, .90,1.0))));
	}
	public void trans(boolean status){
		if (solid.getStatus() == Status.RUNNING) {
			return;
		}
		if (status) {
			
			solid.playFromStart();
		} else {
			solid.setRate(-1);
			solid.jumpTo("end");
			solid.play();
		}

	}
}
>>>>>>> origin/master
