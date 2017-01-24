package monopolycards.ui.virtual;

import javafx.animation.Interpolator;

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
	
	private final Interpolator interp;
	
	public MovementFrame()
	{
		this(Interpolator.EASE_BOTH);
	}
	
	public MovementFrame(Interpolator interp)
	{
		this.interp = interp;
	}

	public Interpolator getInterpolator()
	{
		return interp;
	}

	public double getTranslateX()
	{
		return translateX;
	}
	
	public boolean isSetTranslateX()
	{
		return translateXSet;
	}
	
	public MovementFrame setTranslateX(double translateX)
	{
		translateXSet = true;
		this.translateX = translateX;
		return this;
	}
		
	public MovementFrame unsetTranslateX()
	{
		translateX = 0;
		translateXSet = false;
		return this;
	}
	
	public double getTranslateY()
	{
		return translateY;
	}
	
	public boolean isSetTranslateY()
	{
		return translateYSet;
	}
	
	public MovementFrame setTranslateY(double translateY)
	{
		translateYSet = true;
		this.translateY = translateY;
		return this;
	}
	
	public MovementFrame unsetTranslateY()
	{
		translateY = 0;
		translateYSet = false;
		return this;
	}
	
	public double getTranslateZ()
	{
		return translateZ;
	}
	
	public boolean isSetTranslateZ()
	{
		return translateZSet;
	}
	
	public MovementFrame setTranslateZ(double translateZ)
	{
		translateZSet = true;
		this.translateZ = translateZ;
		return this;
	}
	
	public MovementFrame unsetTranslateZ()
	{
		translateZ = 0;
		translateZSet = false;
		return this;
	}
	
	public double getRotateX()
	{
		return rotateX;
	}
	
	public boolean isSetRotateX()
	{
		return rotateXSet;
	}
	
	public MovementFrame setRotateX(double rotateX)
	{
		rotateXSet = true;
		this.rotateX = rotateX;
		return this;
	}
	
	public MovementFrame unsetRotateX()
	{
		rotateX = 0;
		rotateXSet = false;
		return this;
	}
	
	public double getRotateY()
	{
		return rotateY;
	}
	
	public boolean isSetRotateY()
	{
		return rotateYSet;
	}
	
	public MovementFrame setRotateY(double rotateY)
	{
		rotateYSet = true;
		this.rotateY = rotateY;
		return this;
	}
	
	public MovementFrame unsetRotateY()
	{
		rotateY = 0;
		rotateYSet = false;
		return this;
	}
	
	public double getRotateZ()
	{
		return rotateZ;
	}
	
	public boolean isSetRotateZ()
	{
		return rotateZSet;
	}
	
	public MovementFrame setRotateZ(double rotateZ)
	{
		rotateZSet = true;
		this.rotateZ = rotateZ;
		return this;
	}
	
	public MovementFrame unsetRotateZ()
	{
		rotateZ = 0;
		rotateZSet = false;
		return this;
	}
	
//	public KeyFrame generateKeyFrame(VrtNode node, Duration time)
//	{
//		ArrayList<KeyValue> vals = new ArrayList<>();
//		if (rotateXSet)
//			vals.add(new KeyValue(node.rotateXProperty(), rotateX));
//		if (rotateYSet)
//			vals.add(new KeyValue(node.rotateYProperty(), rotateY));
//		if (rotateZSet)
//			vals.add(new KeyValue(node.rotateZProperty(), rotateZ));
//		if (translateXSet)
//			vals.add(new KeyValue(node.translateXProperty(), translateX));
//		if (translateYSet)
//			vals.add(new KeyValue(node.translateYProperty(), translateY));
//		if (translateZSet)
//			vals.add(new KeyValue(node.translateZProperty(), translateZ));
//		
//		return new KeyFrame(time, vals.toArray(new KeyValue[0]));
//	}
}
