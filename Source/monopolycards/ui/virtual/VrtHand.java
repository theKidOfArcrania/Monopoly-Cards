package monopolycards.ui.virtual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class VrtHand extends VrtGroup
{

	private static final int PADDING = 10;
	private final ArrayList<VrtCard> hand;
	private final HashSet<VrtCard> shown;
	private final HashMap<VrtCard, Animation> animating;
	private final EventHandler<MouseEvent> mouseEnter;
	private final EventHandler<MouseEvent> mouseExit;
	
	//TODO: the cards are centered.
	public VrtHand()
	{
		hand = new ArrayList<>();
		shown = new HashSet<>();
		animating = new HashMap<>();
		
		mouseEnter = evt -> {
			for (int i = 0; i < hand.size(); i++)
			{
				VrtCard c = hand.get(i);
				if (c.getNode() == evt.getSource())
				{
					Animation prev = animating.get(c);
					if (prev != null && prev.getStatus() == Status.RUNNING)
						prev.stop();
					
					MovementFrame frame = new MovementFrame();
					frame.setTranslateZ(getTranslateZ() - 200);
					frame.setTranslateY(getTranslateY() - 80);
					Timeline animate = new MovementTimeline(c).addFrame(Duration.seconds(.2), frame).generateAnimation();
					animating.put(c, animate);
					animate.play();
				}
			}
		};
		mouseExit = evt -> {
			for (int i = 0; i < hand.size(); i++)
			{
				VrtCard c = hand.get(i);
				if (c.getNode() == evt.getSource())
				{
					Animation prev = animating.get(c);
					if (prev != null && prev.getStatus() == Status.RUNNING)
						prev.stop();
					MovementFrame frame = new MovementFrame();
					frame.setTranslateZ(getTranslateZ());
					frame.setTranslateY(getTranslateY());
					Timeline animate = new MovementTimeline(c).addFrame(Duration.seconds(.2), frame).generateAnimation();
					animating.put(c, animate);
					animate.play();
				}
			}
		};
	}
	

	public boolean isShown(VrtCard c)
	{
		return shown.contains(c);
	}
	
	public void flipAll()
	{
		for (VrtCard c : hand)
			flipCard(c);
			
	}
	
	public void flipCard(VrtCard c)
	{
		int flipSide = 180;
		if (shown.remove(c))
			flipSide = 0; //Already shown, hide it.
		else
			shown.add(c);
		
		MovementFrame frame = new MovementFrame();
		frame.setRotateY(getRotateY() + flipSide);
		new MovementTimeline(c).addFrame(MEDIUM_TRANS, frame).generateAnimation().playFromStart();
	}
	
	@Override
	protected void removeCard(VrtCard c)
	{
		hand.remove(c);
		c.getNode().removeEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnter);
		c.getNode().removeEventHandler(MouseEvent.MOUSE_EXITED, mouseExit);
		
		repositionCards();
	}

	@Override
	protected void addCard(VrtCard c)
	{
		hand.add(c);
		
		MovementFrame frame = new MovementFrame();
		frame.setRotateX(getRotateX());
		frame.setRotateY(getRotateY());
		frame.setRotateZ(getRotateZ());
		frame.setTranslateY(getTranslateY());
		frame.setTranslateZ(getTranslateZ());
		
		Timeline addToHand = new MovementTimeline(c).addFrame(MEDIUM_TRANS, frame).generateAnimation();
		addToHand.setOnFinished(evt -> {
			c.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, mouseExit);
			c.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnter);
		});
		addToHand.play();
		
		repositionCards();
	}
	
	private void repositionCards()
	{
		repositionCards(0, hand.size());
	}
	
	private void repositionCards(int start, int end)
	{
		double totalWidth = 0;
		for (VrtCard c : hand)
			totalWidth += c.getWidth();
		totalWidth += PADDING * (hand.size() - 1);
		
		double tx = getTranslateX() + (getWidth() - totalWidth) / 2;
		
		for (int i = 0; i < end; i++)
		{
			VrtCard c = hand.get(i);
			
			if (i >= start)
			{
				MovementFrame frame = new MovementFrame();
				frame.setTranslateX(tx);
				new MovementTimeline(c).addFrame(MEDIUM_TRANS, frame).generateAnimation().play();
			}
			
			tx += c.getWidth() + PADDING;
		}
	}
}
