package monopolycards.ui;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public class UIObject extends Group
{
	private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
	private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
	private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
	
	public UIObject()
	{
		this.getTransforms().addAll(rotateX, rotateY, rotateZ);
		rotateZ.angleProperty().addListener(val -> recalcAxis());
		rotateY.angleProperty().addListener(val -> recalcAxis());
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
