package monopolycards.ui.test;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.util.ArrayList;

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

public class MoveCounter extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	private final PerspectiveCamera cameraView = new PerspectiveCamera();
	private final DisplayMode defaultMode = getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode();
	private final Dimension displayRes = new Dimension(defaultMode.getWidth(), defaultMode.getHeight());
	
	public final double dispWidth = displayRes.getWidth();
	public final double dispHeight = displayRes.getHeight();
	
	private static final double ANCHOR = 10.0;
	private SimpleIntegerProperty moveCount;
	private SimpleDoubleProperty alph;

	private final Timeline solid = new Timeline();
	
	public MoveCounter() {
		alph = new SimpleDoubleProperty(1.0);
		moveCount = new SimpleIntegerProperty(3);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
	}

	private void init(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		
		AnchorPane root = new AnchorPane();
		
		//shades
		InnerShadow smallShade = new InnerShadow();
		smallShade.setRadius(2.0);
		
		InnerShadow mediumShade = new InnerShadow();
		mediumShade.setRadius(3.0);
		
		InnerShadow largeShade = new InnerShadow();
		mediumShade.setRadius(5.0);
		
		DropShadow out = new DropShadow();
		out.setRadius(2.0);

		
		//lightholder
		Rectangle panel = new Rectangle(dispWidth/6-50, dispHeight/4-85);
		panel.setArcHeight(60.0);
		panel.setArcWidth(60.0);
		AnchorPane.setBottomAnchor(panel, 25.0);
		AnchorPane.setLeftAnchor(panel, 22.0);
		panel.setFill(Color.DARKGRAY.darker().darker());
		panel.setEffect(out);
		
		//label
		Text name = new Text("Moves Left");
		name.setFill(Color.GRAY);
		name.setFont(Font.loadFont(PlayerInfoTest.class.getResource("KabaleMedium-Normal.ttf").toExternalForm(), 30));
		name.setWrappingWidth(dispWidth/6);
		name.setTextAlignment(TextAlignment.CENTER);
		AnchorPane.setTopAnchor(name, 10.0);
		name.setEffect(smallShade);
		
		Scene scene = new Scene(root, dispWidth/6, dispHeight/4);
		//first light
		
		ArrayList<ImageView> lightList = new ArrayList<ImageView>();
		
		Image turn1 = new Image((PlayerInfoTest.class.getResource("Greenlight.png").toExternalForm()));
		ImageView light1 = new ImageView();
		light1.setImage(turn1);
		light1.setFitHeight(dispHeight/16);
		light1.setFitWidth(dispWidth/16);
		light1.setPreserveRatio(true);
        light1.setSmooth(true);
        light1.setCache(true);
        light1.setEffect(mediumShade);
        AnchorPane.setBottomAnchor(light1, panel.getHeight()/2);
        AnchorPane.setLeftAnchor(light1, panel.getWidth()/6);
        light1.setOnMouseClicked(e->genAnimation(light1));
        lightList.add(light1);
        
        Image turn2 = new Image((PlayerInfoTest.class.getResource("Greenlight.png").toExternalForm()));
		ImageView light2 = new ImageView();
		light2.setImage(turn2);
		light2.setFitHeight(dispHeight/16);
		light2.setFitWidth(dispWidth/16);
		light2.setPreserveRatio(true);
        light2.setSmooth(true);
        light2.setCache(true);
        light2.setEffect(mediumShade);
        AnchorPane.setBottomAnchor(light2, panel.getHeight()/2);
        AnchorPane.setLeftAnchor(light2, scene.getWidth()/2-light2.getFitHeight()/2-3);
        light2.setOnMouseClicked(e->genAnimation(light2));
        lightList.add(light2);
        
        Image turn3 = new Image((PlayerInfoTest.class.getResource("Greenlight.png").toExternalForm()));
		ImageView light3 = new ImageView();
		light3.setImage(turn3);
		light3.setFitHeight(dispHeight/16);
		light3.setFitWidth(dispWidth/16);
		light3.setPreserveRatio(true);
        light3.setSmooth(true);
        light3.setCache(true);
        light3.setEffect(mediumShade);
        AnchorPane.setBottomAnchor(light3, panel.getHeight()/2);
        AnchorPane.setRightAnchor(light3, panel.getWidth()/6);
        light3.setOnMouseClicked(e->genAnimation(light3));
        lightList.add(light3);
        
        Image turn1off = new Image((PlayerInfoTest.class.getResource("lightoff.png").toExternalForm()));
		ImageView light1off = new ImageView();
		light1off.setImage(turn1off);
		light1off.setFitHeight(dispHeight/16);
		light1off.setFitWidth(dispWidth/16);
		light1off.setPreserveRatio(true);
        light1off.setSmooth(true);
        light1off.setCache(true);
        light1off.setEffect(largeShade);
        AnchorPane.setBottomAnchor(light1off, panel.getHeight()/2);
        AnchorPane.setLeftAnchor(light1off, panel.getWidth()/6);
        
        Image turn2off = new Image((PlayerInfoTest.class.getResource("lightoff.png").toExternalForm()));
		ImageView light2off = new ImageView();
		light2off.setImage(turn1off);
		light2off.setFitHeight(dispHeight/16);
		light2off.setFitWidth(dispWidth/16);
		light2off.setPreserveRatio(true);
        light2off.setSmooth(true);
        light2off.setCache(true);
        light2off.setEffect(largeShade);
        AnchorPane.setBottomAnchor(light2off, panel.getHeight()/2);
        AnchorPane.setLeftAnchor(light2off, scene.getWidth()/2-light2.getFitHeight()/2-3);
        
        Image turn3off = new Image((PlayerInfoTest.class.getResource("lightoff.png").toExternalForm()));
		ImageView light3off = new ImageView();
		light3off.setImage(turn1off);
		light3off.setFitHeight(dispHeight/16);
		light3off.setFitWidth(dispWidth/16);
		light3off.setPreserveRatio(true);
        light3off.setSmooth(true);
        light3off.setCache(true);
        light3off.setEffect(largeShade);
        AnchorPane.setBottomAnchor(light3off, panel.getHeight()/2);
        AnchorPane.setRightAnchor(light3off, panel.getWidth()/6);
        
		root.getChildren().addAll(name, panel, light1off, light2off, light3off, light1, light2, light3);
		
   
		
		root.setId("ROOTNODE");
		scene.getStylesheets().add("/monopolycards/ui/test/border.css");
		primaryStage.setX(0);
		primaryStage.setY(dispHeight - scene.getHeight());
		
		primaryStage.setScene(scene);
		scene.setCamera(cameraView);
		primaryStage.show();

	}
	
	public void genAnimation(ImageView light)
	{
		
		Timeline anim = new Timeline();
		anim.getKeyFrames()
		.add(new KeyFrame(Duration.ZERO, new KeyValue(light.opacityProperty(), 1.0)));
		
		anim.getKeyFrames()
		.add(new KeyFrame(Duration.seconds(.05), new KeyValue(light.opacityProperty(), .75)));
		
		anim.getKeyFrames()
		.add(new KeyFrame(Duration.seconds(.1), new KeyValue(light.opacityProperty(), .5)));
		
		anim.getKeyFrames()
		.add(new KeyFrame(Duration.seconds(.15), new KeyValue(light.opacityProperty(), .25)));
		
		anim.getKeyFrames()
		.add(new KeyFrame(Duration.seconds(.2), new KeyValue(light.opacityProperty(), 0)));
		
		anim.playFromStart();
		
	}
	
	public void resetMoves()
	{
		moveCount = new SimpleIntegerProperty(3);
	}

}