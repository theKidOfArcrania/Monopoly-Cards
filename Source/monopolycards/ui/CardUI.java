package monopolycards.ui;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class CardUI extends UIObject
{
	
	private ObjectProperty<Image> backImage = new SimpleObjectProperty<>(this, "backImage", null);
	private ObjectProperty<Image> frontImage = new SimpleObjectProperty<>(this, "frontImage", null);
	
	public CardUI()
	{
		setWidth(11 * 20);
		setHeight(17 * 20);
		
		PhongMaterial frontMat = new PhongMaterial();
		PhongMaterial backMat = new PhongMaterial();
		
		frontMat.diffuseMapProperty().bind(frontImage);
		backMat.diffuseMapProperty().bind(backImage);
		frontMat.setSpecularColor(Color.BLACK);
		backMat.setSpecularColor(Color.BLACK);
		
		DoubleProperty width = widthProperty();
		DoubleProperty height = heightProperty();
		
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
		
		PointLight light = new PointLight();
		light.translateXProperty().bind(width.divide(2));
		light.translateYProperty().bind(height.divide(2));
		light.setTranslateZ(200);
		
		PointLight light2 = new PointLight();
		light2.translateXProperty().bind(width.divide(2));
		light2.translateYProperty().bind(height.divide(2));
		light2.setTranslateZ(-200);
		
		this.getChildren().addAll(front, back, light, light2);
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
