package monopolycards.ui.virtual;

import java.util.ArrayList;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.WritableDoubleValue;
import javafx.util.Duration;

public class MovementTimeline
{
	private static class KeyFrames {
		private final WritableDoubleValue key;
		private final ArrayList<Double> frames;
		
		public KeyFrames(WritableDoubleValue key)
		{
			this.key = key;
			frames = new ArrayList<>();
		}
		
		public boolean shouldIgnore()
		{
			for (Double frame : frames)
				if (frame != null)
					return false;
			return true;
		}
	}
	
	private static final int MOVEMENT_KEYS = 6;
	
	private final ArrayList<Duration> timeframes;
	private final ArrayList<Interpolator> interps;
	private final KeyFrames[] movements;
	private final VrtNode node;
	
	public MovementTimeline(VrtNode node)
	{
		timeframes = new ArrayList<>();
		interps = new ArrayList<>();
		movements = new KeyFrames[MOVEMENT_KEYS];
		
		DoubleProperty values[] = {
				node.translateXProperty(), node.translateYProperty(), node.translateZProperty(),
				node.rotateXProperty(), node.rotateYProperty(), node.rotateZProperty()
		};
		for (int i = 0; i < values.length; i++)
			movements[i] = new KeyFrames(values[i]);
		
		this.node = node;
	}
	
	public MovementTimeline addFrame(Duration delay, MovementFrame frame)
	{
		if (delay.isIndefinite() || delay.isUnknown() || delay.toMillis() <= 0)
			throw new IllegalArgumentException("Delay must be a positive definite number");
		
		timeframes.add(delay);
		movements[0].frames.add(frame.isSetTranslateX() ? frame.getTranslateX() : null);
		movements[1].frames.add(frame.isSetTranslateY() ? frame.getTranslateY() : null);
		movements[2].frames.add(frame.isSetTranslateZ() ? frame.getTranslateZ() : null);
		movements[3].frames.add(frame.isSetRotateX() ? frame.getRotateX() : null);
		movements[4].frames.add(frame.isSetRotateY() ? frame.getRotateY() : null);
		movements[5].frames.add(frame.isSetRotateZ() ? frame.getRotateZ() : null);
		interps.add(frame.getInterpolator());
		
		return this;
	}

	//null to ignore value
	public MovementTimeline addTranslate(Duration delay, Double tx, Double ty, Double tz)
	{
		return addTranslate(delay, Interpolator.EASE_BOTH, tx, ty, tz);
	}
	
	public MovementTimeline addTranslate(Duration delay, Interpolator interp, Double tx, Double ty, Double tz)
	{
		if (delay.isIndefinite() || delay.isUnknown() || delay.toMillis() <= 0)
			throw new IllegalArgumentException("Delay must be a positive definite number");
		
		timeframes.add(delay);
		movements[0].frames.add(tx);
		movements[1].frames.add(ty);
		movements[2].frames.add(tz);
		movements[3].frames.add(null);
		movements[4].frames.add(null);
		movements[5].frames.add(null);
		interps.add(interp);
		
		return this;
	}
	
	//null to ignore value
	public MovementTimeline addRotate(Duration delay, Double rx, Double ry, Double rz)
	{
		return addRotate(delay, Interpolator.EASE_BOTH, rx,  ry, rz);
	}
	
	public MovementTimeline addRotate(Duration delay, Interpolator interp, Double rx, Double ry, Double rz)
	{
		if (delay.isIndefinite() || delay.isUnknown() || delay.toMillis() <= 0)
			throw new IllegalArgumentException("Delay must be a positive definite number");
		
		timeframes.add(delay);
		movements[0].frames.add(null);
		movements[1].frames.add(null);
		movements[2].frames.add(null);
		movements[3].frames.add(rx);
		movements[4].frames.add(ry);
		movements[5].frames.add(rz);
		interps.add(interp);
		
		return this;
	}
	
	public Timeline generateAnimation()
	{
		Timeline animate = new Timeline();
		
		ArrayList<KeyFrames> moves = new ArrayList<>();
		int[] compactMap = new int[MOVEMENT_KEYS];
		int ind = 0;
		for (KeyFrames move : movements)
		{
			if (!move.shouldIgnore())
			{
				compactMap[moves.size()] = ind;
				moves.add(move);
			}
			ind++;
		}
		
		if (moves.isEmpty())
			return animate;
		
		Double prevValues[] = {
			node.getTranslateX(), node.getTranslateY(), node.getTranslateZ(),
			node.getRotateX(), node.getRotateY(), node.getRotateZ()
		};
		Duration time = Duration.ZERO;
		
		for (int pos = -1; pos < timeframes.size(); pos++)
		{
			boolean init = pos == -1;
			
			KeyValue[] values = new KeyValue[moves.size()];
			for (int mtype = 0; mtype < moves.size(); mtype++)
			{
				Double value = init ? null : moves.get(mtype).frames.get(pos);
				if (value == null)
					value = prevValues[compactMap[mtype]];
				else
					prevValues[compactMap[mtype]] = value;
				if (init)
					values[mtype] = new KeyValue(moves.get(mtype).key, value);
				else
					values[mtype] = new KeyValue(moves.get(mtype).key, value, interps.get(pos));
			}
			if (!init)
				time = time.add(timeframes.get(pos));
			animate.getKeyFrames().add(new KeyFrame(time, values));
		}
		return animate;
	}
}
