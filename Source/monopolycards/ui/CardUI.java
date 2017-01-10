package monopolycards.ui;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class CardUI extends UIObject
{
	private DoubleProperty width = new SimpleDoubleProperty(this, "width", 11 * 20);
	private DoubleProperty height = new SimpleDoubleProperty(this, "height", 17 * 20);
	private ObjectProperty<Image> backImage = new SimpleObjectProperty<>(this, "backImage", null);
	private ObjectProperty<Image> frontImage = new SimpleObjectProperty<>(this, "frontImage", null);
	
	public CardUI()
	{
		//TODO: change transform to on CardUI
		PhongMaterial frontMat = new PhongMaterial(Color.WHITE);
		PhongMaterial backMat = new PhongMaterial(Color.WHITE);
		
		frontMat.diffuseMapProperty().bind(frontImage);
		backMat.diffuseMapProperty().bind(backImage);
		
		Box front = new Box();
		front.widthProperty().bind(width);
		front.heightProperty().bind(height);
		front.translateXProperty().bind(width.divide(2));
		front.translateYProperty().bind(height.divide(2));
		front.setDepth(1);
		front.setMaterial(frontMat);
		
		Box back = new Box();
		back.widthProperty().bind(width);
		back.heightProperty().bind(height);
		back.translateXProperty().bind(width.divide(2));
		back.translateYProperty().bind(height.divide(2));
		back.setDepth(1);
		back.setTranslateZ(-1);
		back.setMaterial(backMat);
		
		this.getChildren().addAll(front, back);
	}
	
	public final DoubleProperty widthProperty()
	{
		return this.width;
	}
	
	public final double getWidth()
	{
		return this.widthProperty().get();
	}
	
	public final void setWidth(final double width)
	{
		this.widthProperty().set(width);
	}
	
	public final DoubleProperty heightProperty()
	{
		return this.height;
	}
	
	public final double getHeight()
	{
		return this.heightProperty().get();
	}
	
	public final void setHeight(final double height)
	{
		this.heightProperty().set(height);
	}

	public final ObjectProperty<Image> backImageProperty()
	{
		return this.backImage;
	}
	

	public final javafx.scene.image.Image getBackImage()
	{
		return this.backImageProperty().get();
	}
	

	public final void setBackImage(final javafx.scene.image.Image backImage)
	{
		this.backImageProperty().set(backImage);
	}
	

	public final ObjectProperty<Image> frontImageProperty()
	{
		return this.frontImage;
	}
	

	public final javafx.scene.image.Image getFrontImage()
	{
		return this.frontImageProperty().get();
	}
	

	public final void setFrontImage(final javafx.scene.image.Image frontImage)
	{
		this.frontImageProperty().set(frontImage);
	}
}
