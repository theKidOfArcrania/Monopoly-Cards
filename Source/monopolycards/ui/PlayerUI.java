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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PlayerUI extends GridPane
{

    @FXML
    private ImageView imgProfile;

    @FXML
    private Label lblPlayer;

    @FXML
    private Label lblCash;

    @FXML
    private Label lblSets;

    @FXML
    private ImageView imgCash;

    @FXML
    private ImageView imgSets;
	
    private final SimpleStringProperty playerName = new SimpleStringProperty(this, "playerName") {
    	protected void invalidated() {
    		lblPlayer.setText(getValue());
    	}
    };
    
    private final SimpleIntegerProperty cash = new SimpleIntegerProperty(this, "cash") {
    	protected void invalidated() {
    		lblCash.setText(String.format("$%,d", getValue()));
    	}
    };
    
    private final SimpleIntegerProperty propSets = new SimpleIntegerProperty(this, "propSets") {
    	protected void invalidated() {
    		lblSets.setText(String.format("%d set(s)", getValue()));
    	}
    };
    
    private final SimpleObjectProperty<Image> profileImage = new SimpleObjectProperty<Image>(this, "profileImage") {
    	protected void invalidated() {
    		imgProfile.setImage(getValue());
    	}
    };
    
	public PlayerUI()
	{
		URL path = PlayerUI.class.getResource("PlayerUI.fxml");
		if (path == null)
			throw new RuntimeException("Unable to find Player UI");
		try
		{
			FXMLLoader loader = new FXMLLoader(path);
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		}
		catch (IOException e)
		{
			throw new RuntimeException("Unable to load Player UI", e);
		}
	}

	public final SimpleStringProperty playerNameProperty()
	{
		return this.playerName;
	}
	

	public final String getPlayerName()
	{
		return this.playerNameProperty().get();
	}
	

	public final void setPlayerName(final String playerName)
	{
		this.playerNameProperty().set(playerName);
	}
	

	public final SimpleIntegerProperty cashProperty()
	{
		return this.cash;
	}
	

	public final int getCash()
	{
		return this.cashProperty().get();
	}
	

	public final void setCash(final int cash)
	{
		this.cashProperty().set(cash);
	}
	

	public final SimpleIntegerProperty propSetsProperty()
	{
		return this.propSets;
	}
	

	public final int getPropSets()
	{
		return this.propSetsProperty().get();
	}
	

	public final void setPropSets(final int propSets)
	{
		this.propSetsProperty().set(propSets);
	}

	public final SimpleObjectProperty<Image> profileImageProperty()
	{
		return this.profileImage;
	}
	

	public final Image getProfileImage()
	{
		return this.profileImageProperty().get();
	}
	

	public final void setProfileImage(final Image profileImage)
	{
		this.profileImageProperty().set(profileImage);
	}
	
}
