package monopolycards.ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public abstract class UIObject
{
	private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
	private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
	private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
	
	private DoubleProperty width = new SimpleDoubleProperty(this, "width");
	private DoubleProperty height = new SimpleDoubleProperty(this, "height");
	private Group ui;
	
	protected UIObject(Group ui)
	{
		this.ui = ui;
		ui.getTransforms().addAll(rotateX, rotateY, rotateZ);
		
		rotateZ.angleProperty().addListener(val -> recalcAxis());
		rotateY.angleProperty().addListener(val -> recalcAxis());
		
		rotateX.pivotXProperty().bind(width.divide(2));
		rotateX.pivotYProperty().bind(height.divide(2));
		rotateY.pivotXProperty().bind(width.divide(2));
		rotateY.pivotYProperty().bind(height.divide(2));
		rotateZ.pivotXProperty().bind(width.divide(2));
		rotateZ.pivotYProperty().bind(height.divide(2));
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
	
	public DoubleProperty rotateXProperty() {
		return rotateX.angleProperty();
	}
	
	public double getRotateX()
	{
		return rotateX.getAngle();
	}
	
	public void setRotateX(double ang)
	{
		rotateX.setAngle(ang);
	}
	
	public DoubleProperty rotateYProperty() {
		return rotateY.angleProperty();
	}
	
	public double getRotateY()
	{
		return rotateY.getAngle();
	}
	
	public void setRotateY(double ang)
	{
		rotateY.setAngle(ang);
	}
	
	public DoubleProperty rotateZProperty() {
		return rotateZ.angleProperty();
	}
	
	public double getRotateZ()
	{
		return rotateZ.getAngle();
	}
	
	public void setRotateZ(double ang)
	{
		rotateZ.setAngle(ang);
	}
	
	private void recalcAxis()
	{
		rotateY.setAxis(rotateZ.transform(Rotate.Y_AXIS));
		rotateX.setAxis(rotateY.transform(rotateZ.transform(Rotate.X_AXIS)));
	}
}
