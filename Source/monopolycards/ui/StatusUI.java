package monopolycards.ui;

import java.io.IOException;
import java.net.URL;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StatusUI extends VBox
{

    @FXML
    private Label lblMoves;

    @FXML
    private Label lblTurn;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblDescription;

    @FXML
    private StackPane pnlButton;
    
    private ButtonUI button = new ButtonUI();
	
    private final SimpleIntegerProperty moves = new SimpleIntegerProperty(this, "moves") {
    	protected void invalidated() {
    		lblMoves.setText("" + getValue());
    	}
    };
    
    private final SimpleIntegerProperty turnNum = new SimpleIntegerProperty(this, "turnNum") {
    	protected void invalidated() {
    		lblTurn.setText("" + getValue());
    	}
    };
    
    private final SimpleStringProperty title = new SimpleStringProperty(this, "title") {
    	protected void invalidated() {
    		lblTitle.setText(getValue());
    	}
    };
    
    private final SimpleStringProperty description = new SimpleStringProperty(this, "description") {
    	protected void invalidated() {
    		lblDescription.setText(getValue());
    	}
    };
    
    private final SimpleObjectProperty<Image> statusImage = new SimpleObjectProperty<Image>(this, "statusImage") {
    	protected void invalidated() {
    		button.setImage(getValue());
    	}
    };
    
    private final SimpleObjectProperty<Image> statusPressedImage = new SimpleObjectProperty<Image>(this, "statusPressedImage") {
    	protected void invalidated() {
    		button.setImage(getValue());
    	}
    };
    
    //TODO: handler for when status button is clicked.
	public StatusUI()
	{
		URL path = PlayerUI.class.getResource("StatusUI.fxml");
		if (path == null)
			throw new RuntimeException("Unable to find Status UI");
		try
		{
			FXMLLoader loader = new FXMLLoader(path);
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		}
		catch (IOException e)
		{
			throw new RuntimeException("Unable to load Status UI", e);
		}
	}
	
	public void initialize()
	{
		pnlButton.getChildren().add(button);
	}
	
	public final SimpleIntegerProperty movesProperty()
	{
		return this.moves;
	}
	

	public final int getMoves()
	{
		return this.movesProperty().get();
	}
	

	public final void setMoves(final int moves)
	{
		this.movesProperty().set(moves);
	}
	

	public final SimpleIntegerProperty turnNumProperty()
	{
		return this.turnNum;
	}
	

	public final int getTurnNum()
	{
		return this.turnNumProperty().get();
	}
	

	public final void setTurnNum(final int turnNum)
	{
		this.turnNumProperty().set(turnNum);
	}
	

	public final SimpleStringProperty titleProperty()
	{
		return this.title;
	}
	

	public final String getTitle()
	{
		return this.titleProperty().get();
	}
	

	public final void setTitle(final String title)
	{
		this.titleProperty().set(title);
	}
	

	public final SimpleStringProperty descriptionProperty()
	{
		return this.description;
	}
	

	public final String getDescription()
	{
		return this.descriptionProperty().get();
	}
	

	public final void setDescription(final String description)
	{
		this.descriptionProperty().set(description);
	}

	public final SimpleObjectProperty<Image> statusImageProperty()
	{
		return this.statusImage;
	}
	

	public final Image getStatusImage()
	{
		return this.statusImageProperty().get();
	}
	

	public final void setStatusImage(final Image statusImage)
	{
		this.statusImageProperty().set(statusImage);
	}

	public final SimpleObjectProperty<Image> statusPressedImageProperty()
	{
		return this.statusPressedImage;
	}
	

	public final Image getStatusPressedImage()
	{
		return this.statusPressedImageProperty().get();
	}
	

	public final void setStatusPressedImage(final Image statusPressedImage)
	{
		this.statusPressedImageProperty().set(statusPressedImage);
	}
	
	
	
}
