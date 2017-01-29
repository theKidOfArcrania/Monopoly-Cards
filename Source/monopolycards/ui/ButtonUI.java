package monopolycards.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class ButtonUI extends StackPane
{
	private final Pane pnlHighlight = new Pane();
	private final Pane pnlImage = new Pane();
	
	private final SimpleObjectProperty<Image> image = new SimpleObjectProperty<Image>(this, "image") {
    	protected void invalidated() {
    		updateImage();
    	}
    };
	
    private final SimpleObjectProperty<Image> pressedImage = new SimpleObjectProperty<Image>(this, "pressedImage") {
    	protected void invalidated() {
    		updateImage();
    	}
    };
    
	public ButtonUI()
	{
		getChildren().addAll(pnlImage, pnlHighlight);
		pnlHighlight.getStyleClass().add("ButtonHighlight");
		getStyleClass().add("Button");
		pressedProperty().addListener(val -> updateImage());
	}

	public final SimpleObjectProperty<Image> imageProperty()
	{
		return this.image;
	}
	

	public final Image getImage()
	{
		return this.imageProperty().get();
	}
	

	public final void setImage(final Image image)
	{
		this.imageProperty().set(image);
	}
	
	private void updateImage()
	{
		Image img = image.getValue();
		if (isPressed() && pressedImage.get() != null)
			img = pressedImage.get();
		if (img == null)
		{
			pnlImage.setBackground(null);
			return;
		}
		
		BackgroundImage backImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
				new BackgroundSize(-1, -1, false, false, true, false));
		pnlImage.setBackground(new Background(backImg));
	}

	public final SimpleObjectProperty<Image> pressedImageProperty()
	{
		return this.pressedImage;
	}
	

	public final Image getPressedImage()
	{
		return this.pressedImageProperty().get();
	}
	

	public final void setPressedImage(final Image pressedImage)
	{
		this.pressedImageProperty().set(pressedImage);
	}
	
}
