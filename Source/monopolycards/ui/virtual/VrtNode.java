package monopolycards.ui.virtual;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class VrtNode
{
	private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
	private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
	private final Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
	private final Translate translate = new Translate();
	
	private final DoubleProperty width = new SimpleDoubleProperty(this, "width");
	private final DoubleProperty height = new SimpleDoubleProperty(this, "height");
	final ReadOnlyObjectWrapper<VrtGroup> parent = new ReadOnlyObjectWrapper<>(this, "parent");
	
	private final Node ui;
	
	protected VrtNode()
	{
		this(null);
	}
	
	public VrtNode(Node ui)
	{
		this.ui = ui;
		if (ui != null)
			ui.getTransforms().addAll(translate, rotateZ, rotateX, rotateY);
		
		rotateX.angleProperty().addListener(val -> recalcAxis());
		rotateY.angleProperty().addListener(val -> recalcAxis());
	}
	
	public double getParentRotateX()
	{
		VrtGroup parent = getParent();
		if (parent == null)
			return 0;
		else
			return parent.getParentRotateX() + parent.getRotateX();
	}
	
	public double getParentRotateY()
	{
		VrtGroup parent = getParent();
		if (parent == null)
			return 0;
		else
			return parent.getParentRotateY() + parent.getRotateY();
	}
	
	public double getParentRotateZ()
	{
		VrtGroup parent = getParent();
		if (parent == null)
			return 0;
		else
			return parent.getParentRotateZ() + parent.getRotateZ();
	}
	
	public double getParentTranslateX()
	{
		VrtGroup parent = getParent();
		if (parent == null)
			return 0;
		else
			return parent.getParentTranslateX() + parent.getTranslateX();
	}
	
	public double getParentTranslateY()
	{
		VrtGroup parent = getParent();
		if (parent == null)
			return 0;
		else
			return parent.getParentTranslateY() + parent.getTranslateY();
	}
	
	public double getParentTranslateZ()
	{
		VrtGroup parent = getParent();
		if (parent == null)
			return 0;
		else
			return parent.getParentTranslateZ() + parent.getTranslateZ();
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
		translate.setX(value);
	}

	public final double getTranslateX()
	{
		return translate.getX();
	}

	public final DoubleProperty translateXProperty()
	{
		return translate.xProperty();
	}

	public final void setTranslateY(double value)
	{
		translate.setY(value);
	}

	public final double getTranslateY()
	{
		return translate.getY();
	}

	public final DoubleProperty translateYProperty()
	{
		return translate.yProperty();
	}

	public final void setTranslateZ(double value)
	{
		translate.setZ(value);
	}

	public final double getTranslateZ()
	{
		return translate.getZ();
	}

	public final DoubleProperty translateZProperty()
	{
		return translate.zProperty();
	}

	private void recalcAxis()
	{
		//TODO: allow user to change rotation axis order
		rotateX.setAxis(rotateY.transform(Rotate.X_AXIS));
		rotateZ.setAxis(rotateX.transform(rotateY.transform(Rotate.Z_AXIS)));
	}

	public final ReadOnlyObjectProperty<VrtGroup> parentProperty()
	{
		return this.parent.getReadOnlyProperty();
	}
	

	public final VrtGroup getParent()
	{
		return this.parentProperty().get();
	}
	
}
