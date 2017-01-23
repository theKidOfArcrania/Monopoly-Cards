package monopolycards.ui.virtual;

import java.util.Objects;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public abstract class VrtGroup extends VrtNode
{
	private final ObservableList<VrtNode> children = new VetoableObservableList<>(this::checkChildren);
	
	public static Timeline createAnimation(VrtNode obj, Duration length, double finalX, double finalY, double finalZ)
	{
		return createAnimation(obj, length, Interpolator.EASE_BOTH, finalX, finalY, finalZ, obj.getRotateX(), 
				obj.getRotateY(), obj.getRotateZ());
	}
	public static Timeline createAnimation(VrtNode obj, Duration length, double finalX, double finalY, double finalZ, 
			double angleX, double angleY, double angleZ)
	{
		return createAnimation(obj, length, Interpolator.EASE_BOTH, finalX, finalY, finalZ, angleX, angleY, angleZ);
	}
	public static Timeline createAnimation(VrtNode obj, Duration length, Interpolator intrp, double finalX, double finalY, 
			double finalZ, double angleX, double angleY, double angleZ)
	{
		Timeline animate = new Timeline();
		KeyFrame before = new KeyFrame(Duration.ZERO, new KeyValue(obj.translateXProperty(), obj.getTranslateX(), intrp),
				new KeyValue(obj.translateYProperty(), obj.getTranslateY(), intrp),
				new KeyValue(obj.translateZProperty(), obj.getTranslateZ(), intrp),
				new KeyValue(obj.rotateXProperty(), obj.getRotateX(), intrp),
				new KeyValue(obj.rotateYProperty(), obj.getRotateY(), intrp),
				new KeyValue(obj.rotateZProperty(), obj.getRotateZ(), intrp));
		KeyFrame after = new KeyFrame(length, new KeyValue(obj.translateXProperty(), finalX, intrp),
				new KeyValue(obj.translateYProperty(), finalY, intrp),
				new KeyValue(obj.translateZProperty(), finalZ, intrp),
				new KeyValue(obj.rotateXProperty(), angleX, intrp),
				new KeyValue(obj.rotateYProperty(), angleY, intrp),
				new KeyValue(obj.rotateZProperty(), angleZ, intrp));
		animate.getKeyFrames().addAll(before, after);
		return animate;
	}
	
	private void checkChildren(VrtNode node)
	{
		if (node == null)
			throw new NullPointerException();
		if (node.getParent() != null)
			throw new VetoedException("Node already has a parent.");
		if (Objects.equals(node,this))
			throw new VetoedException("Cannot add node to itself.");
		
		VrtGroup tmp = this;
		while (tmp != null)
		{
			if (Objects.equals(node,tmp))
				throw new VetoedException("Parent node cannot be added to a child node.");
			tmp = tmp.getParent();
		}
	}
	
	/**
	 * Transfers a card into this UIComponent in its position and orientation
	 * @param c the card to move.
	 */
	public abstract void transferCard(VrtCard c);
	
	public final ObservableList<VrtNode> getChildren()
	{
		return children;
	}
	
	
}
