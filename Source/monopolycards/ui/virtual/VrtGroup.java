package monopolycards.ui.virtual;

import java.util.Objects;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public abstract class VrtGroup extends VrtNode
{
	private final ObservableList<VrtNode> children = new VetoableObservableList<>(this::checkChildren);
	
	public static Timeline createAnimation(VrtNode obj)
	{
		Timeline animate = new Timeline();
		KeyFrame before = new KeyFrame(Duration.ZERO, new KeyValue(obj.translateXProperty(), obj.getTranslateX()),
				new KeyValue(obj.translateYProperty(), obj.getTranslateY()),
				new KeyValue(obj.translateZProperty(), obj.getTranslateZ()),
				new KeyValue(obj.rotateXProperty(), obj.getRotateX()),
				new KeyValue(obj.rotateYProperty(), obj.getRotateY()),
				new KeyValue(obj.rotateZProperty(), obj.getRotateZ()));
		animate.getKeyFrames().add(before);
		return animate;
	}
	
	public static void toTranslate(VrtNode obj, Timeline animate, Duration time, double tx, double ty, double tz)
	{
		toTranslate(obj, animate, animate.getCycleDuration(), time, tx, ty, tz);
	}
	
	public static void toTranslate(VrtNode obj, Timeline animate, Duration start, Duration time, double tx, double ty, double tz)
	{
		KeyFrame fra = new KeyFrame(start.add(time), new KeyValue(obj.translateXProperty(), tx),
				new KeyValue(obj.translateYProperty(), ty),
				new KeyValue(obj.translateZProperty(), tz));
		animate.getKeyFrames().add(fra);
	}
	
	public static void toRotate(VrtNode obj, Timeline animate, Duration time, double rx, double ry, double rz)
	{
		toRotate(obj, animate, animate.getCycleDuration(), time, rx, ry, rz);
	}
	
	public static void toRotate(VrtNode obj, Timeline animate, Duration start, Duration time, double rx, double ry, double rz)
	{
		KeyFrame fra = new KeyFrame(start.add(time), new KeyValue(obj.rotateXProperty(), rx),
				new KeyValue(obj.rotateYProperty(), ry),
				new KeyValue(obj.rotateZProperty(), rz));
		animate.getKeyFrames().add(fra);
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
