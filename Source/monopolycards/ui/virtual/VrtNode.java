package monopolycards.ui.virtual;

import java.util.Arrays;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public abstract class VrtNode
{
	private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
	private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
	private final Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
	
	private final DoubleProperty width = new SimpleDoubleProperty(this, "width");
	private final DoubleProperty height = new SimpleDoubleProperty(this, "height");
	private final Node ui;
	private final List<Transform> transforms; 
	
	protected VrtNode()
	{
		this(null);
	}
	
	protected VrtNode(Node ui)
	{
		this.ui = ui;
		if (ui != null)
		{
			ui.getTransforms().addAll(rotateX, rotateY, rotateZ);
			transforms = null;
		}
		else
			transforms = Arrays.asList(rotateX, rotateY, rotateZ);
		
		rotateZ.angleProperty().addListener(val -> recalcAxis());
		rotateY.angleProperty().addListener(val -> recalcAxis());
		
		rotateX.pivotXProperty().bind(width.divide(2));
		rotateX.pivotYProperty().bind(height.divide(2));
		rotateY.pivotXProperty().bind(width.divide(2));
		rotateY.pivotYProperty().bind(height.divide(2));
		rotateZ.pivotXProperty().bind(width.divide(2));
		rotateZ.pivotYProperty().bind(height.divide(2));
	}
	
	protected final List<Transform> getTransforms()
	{
		return transforms;
	}
	
	public final Node getNode()
	{
		return ui;
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
	
	public final DoubleProperty rotateXProperty() {
		return rotateX.angleProperty();
	}
	
	public final double getRotateX()
	{
		return rotateX.getAngle();
	}
	
	public final void setRotateX(double ang)
	{
		rotateX.setAngle(ang);
	}
	
	public final DoubleProperty rotateYProperty() {
		return rotateY.angleProperty();
	}
	
	public final double getRotateY()
	{
		return rotateY.getAngle();
	}
	
	public final void setRotateY(double ang)
	{
		rotateY.setAngle(ang);
	}
	
	public final DoubleProperty rotateZProperty() {
		return rotateZ.angleProperty();
	}
	
	public final double getRotateZ()
	{
		return rotateZ.getAngle();
	}
	
	public final void setRotateZ(double ang)
	{
		rotateZ.setAngle(ang);
	}
	
	public final void setTranslateX(double value)
	{
		ui.setTranslateX(value);
	}

	public final double getTranslateX()
	{
		return ui.getTranslateX();
	}

	public final DoubleProperty translateXProperty()
	{
		return ui.translateXProperty();
	}

	public final void setTranslateY(double value)
	{
		ui.setTranslateY(value);
	}

	public final double getTranslateY()
	{
		return ui.getTranslateY();
	}

	public final DoubleProperty translateYProperty()
	{
		return ui.translateYProperty();
	}

	public final void setTranslateZ(double value)
	{
		ui.setTranslateZ(value);
	}

	public final double getTranslateZ()
	{
		return ui.getTranslateZ();
	}

	public final DoubleProperty translateZProperty()
	{
		return ui.translateZProperty();
	}

	private void recalcAxis()
	{
		rotateY.setAxis(rotateZ.transform(Rotate.Y_AXIS));
		rotateX.setAxis(rotateY.transform(rotateZ.transform(Rotate.X_AXIS)));
	}
}
