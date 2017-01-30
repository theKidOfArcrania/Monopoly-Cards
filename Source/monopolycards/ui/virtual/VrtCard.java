package monopolycards.ui.virtual;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class VrtCard extends VrtNode
{
	
	public static final int CARD_WIDTH_FACTOR = 11;
	public static final int CARD_HEIGHT_FACTOR = 17;
	public static final double CARD_DEPTH = .5;
	
	private static final double PADDING = 5;
	private static final double DELTA = .01;
	
	private ObjectProperty<Image> backImage = new SimpleObjectProperty<>(this, "backImage", null);
	private ObjectProperty<Image> frontImage = new SimpleObjectProperty<>(this, "frontImage", null);
	
	
	public VrtCard()
	{
		super(new Group());

		setWidth(CARD_WIDTH_FACTOR * 15);
		setHeight(CARD_HEIGHT_FACTOR * 15);
		
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
		front.setDepth(CARD_DEPTH / 2);
		front.setTranslateZ(CARD_DEPTH / 2);
		front.setMaterial(frontMat);
		
		Box back = new Box();
		back.widthProperty().bind(width);
		back.heightProperty().bind(height);
		back.setDepth(CARD_DEPTH / 2);
		back.setTranslateZ(-CARD_DEPTH / 2);
		back.setMaterial(backMat);
		
		PhongMaterial white = new PhongMaterial(Color.WHITE);
		Box rim = new Box();
		rim.widthProperty().bind(width.add(PADDING));
		rim.heightProperty().bind(height.add(PADDING));
		rim.setDepth(CARD_DEPTH - DELTA);
		rim.setMaterial(white);
		
		((Group)getNode()).getChildren().addAll(rim, front, back);
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
	

	public final Image getFrontImage()
	{
		return this.frontImageProperty().get();
	}
	

	public final void setFrontImage(final Image frontImage)
	{
		this.frontImageProperty().set(frontImage);
	}
}
