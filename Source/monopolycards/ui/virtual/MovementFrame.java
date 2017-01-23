package monopolycards.ui.virtual;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

public class MovementFrame
{
	private double translateX;
	private boolean translateXSet;
	private double translateY;
	private boolean translateYSet;
	private double translateZ;
	private boolean translateZSet;
	
	private double rotateX;
	private boolean rotateXSet;
	private double rotateY;
	private boolean rotateYSet;
	private double rotateZ;
	private boolean rotateZSet;
	
	public double getTranslateX()
	{
		return translateX;
	}
	
	public boolean isSetTranslateX()
	{
		return translateXSet;
	}
	
	public void setTranslateX(double translateX)
	{
		translateXSet = true;
		this.translateX = translateX;
	}
		
	public void unsetTranslateX()
	{
		translateX = 0;
		translateXSet = false;
	}
	
	public double getTranslateY()
	{
		return translateY;
	}
	
	public boolean isSetTranslateY()
	{
		return translateYSet;
	}
	
	public void setTranslateY(double translateY)
	{
		translateYSet = true;
		this.translateY = translateY;
	}
	
	public void unsetTranslateY()
	{
		translateY = 0;
		translateYSet = false;
	}
	
	public double getTranslateZ()
	{
		return translateZ;
	}
	
	public boolean isSetTranslateZ()
	{
		return translateZSet;
	}
	
	public void setTranslateZ(double translateZ)
	{
		translateZSet = true;
		this.translateZ = translateZ;
	}
	
	public void unsetTranslateZ()
	{
		translateZ = 0;
		translateZSet = false;
	}
	
	public double getRotateX()
	{
		return rotateX;
	}
	
	public boolean isSetRotateX()
	{
		return rotateXSet;
	}
	
	public void setRotateX(double rotateX)
	{
		rotateXSet = true;
		this.rotateX = rotateX;
	}
	
	public void unsetRotateX()
	{
		rotateX = 0;
		rotateXSet = false;
	}
	
	public double getRotateY()
	{
		return rotateY;
	}
	
	public boolean isSetRotateY()
	{
		return rotateYSet;
	}
	
	public void setRotateY(double rotateY)
	{
		rotateYSet = true;
		this.rotateY = rotateY;
	}
	
	public void unsetRotateY()
	{
		rotateY = 0;
		rotateYSet = false;
	}
	
	public double getRotateZ()
	{
		return rotateZ;
	}
	
	public boolean isSetRotateZ()
	{
		return rotateZSet;
	}
	
	public void setRotateZ(double rotateZ)
	{
		rotateZSet = true;
		this.rotateZ = rotateZ;
	}
	
	public void unsetRotateZ()
	{
		rotateZ = 0;
		rotateZSet = false;
	}
	
	public KeyFrame generateKeyFrame(VrtNode node, Duration time)
	{
		ArrayList<KeyValue> vals = new ArrayList<>();
		if (rotateXSet)
			vals.add(new KeyValue(node.rotateXProperty(), rotateX));
		if (rotateYSet)
			vals.add(new KeyValue(node.rotateYProperty(), rotateY));
		if (rotateZSet)
			vals.add(new KeyValue(node.rotateZProperty(), rotateZ));
		if (translateXSet)
			vals.add(new KeyValue(node.translateXProperty(), translateX));
		if (translateYSet)
			vals.add(new KeyValue(node.translateYProperty(), translateY));
		if (translateZSet)
			vals.add(new KeyValue(node.translateZProperty(), translateZ));
		
		return new KeyFrame(time, vals.toArray(new KeyValue[0]));
	}
}
