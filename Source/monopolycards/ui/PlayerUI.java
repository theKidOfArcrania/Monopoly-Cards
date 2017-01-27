package monopolycards.ui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PlayerUI extends GridPane
{
	public static final double WIDTH = 300;
	public static final double HEIGHT = 150;
	
	public PlayerUI()
	{	
		StackPane profile = new StackPane();
		ImageView profileView = new ImageView();
		profileView.setFitHeight(100);
		profileView.setFitWidth(100);
		profile.getChildren().add(profileView);
		profile.setBackground(new Background(new BackgroundFill(Color.NAVY, null, null)));
		profile.setMaxSize(100, 100);
		
		//TODO: use fxml
		Label money = new Label("15 Million Dollars");
		Label sets = new Label("4 sets");
		Label name = new Label("Player name");
		
		GridPane.setConstraints(profile, 0, 0, 1, 2);
		GridPane.setConstraints(money, 1, 0, 1, 1);
		
		GridPane.setConstraints(sets, 1, 1, 1, 1);
		GridPane.setConstraints(name, 0, 2, 2, 1);
		getChildren().addAll(profile, money, sets, name);
		
		setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, null, null)));
		
		setMinSize(WIDTH, HEIGHT);
		setPrefSize(WIDTH, HEIGHT);
		setMaxSize(WIDTH, HEIGHT);
	}
}
