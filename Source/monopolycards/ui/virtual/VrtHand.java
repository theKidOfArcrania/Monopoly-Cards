package monopolycards.ui.virtual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class VrtHand extends VrtGroup
{

	private static final int PADDING = 10;
	private final ArrayList<VrtCard> hand;
	private final HashSet<VrtCard> shown;
	private final HashMap<VrtCard, Animation> animating;
	private final EventHandler<MouseEvent> mouseEnter;
	private final EventHandler<MouseEvent> mouseExit;
	private final EventHandler<MouseEvent> mouseClick;
	private final ObjectProperty<Consumer<VrtCard>> onSelectHand;
	private final BooleanProperty defaultShow;
	
	public VrtHand()
	{
		hand = new ArrayList<>();
		shown = new HashSet<>();
		animating = new HashMap<>();
		
		onSelectHand = new SimpleObjectProperty<>(this, "onSelectHand");
		defaultShow = new SimpleBooleanProperty(this, "defaultShow");
		
		mouseEnter = evt -> {
			VrtCard c = identifyCardFrom((Node)evt.getSource());
			
			Animation prev = animating.get(c);
			if (prev != null && prev.getStatus() == Status.RUNNING)
				prev.stop();
					
			MovementFrame frame = new MovementFrame();
			frame.setTranslateZ(getTranslateZ() - 200);
			frame.setTranslateY(getTranslateY() - 80);
			Timeline animate = new MovementTimeline(c).addFrame(Duration.seconds(.2), frame).generateAnimation();
			animating.put(c, animate);
			animate.play();
		};
		mouseExit = evt -> {
			VrtCard c = identifyCardFrom((Node)evt.getSource());
	
			Animation prev = animating.get(c);
			if (prev != null && prev.getStatus() == Status.RUNNING)
				prev.stop();
			
			MovementFrame frame = new MovementFrame();
			frame.setTranslateZ(getTranslateZ());
			frame.setTranslateY(getTranslateY());
			Timeline animate = new MovementTimeline(c).addFrame(Duration.seconds(.2), frame).generateAnimation();
			animating.put(c, animate);
			animate.play();
		};
		
		mouseClick = evt -> {
			VrtCard c = identifyCardFrom((Node)evt.getSource());
			Consumer<VrtCard> han = getOnSelectHand();
			if (han != null)
				han.accept(c);
		};
	}

	public final boolean isDefaultShow()
	{
		return defaultShow.get();
	}
	
	public final void setDefaultShow(boolean val)
	{
		defaultShow.set(val);
	}
	
	public final BooleanProperty defaultShowProperty()
	{
		return defaultShow;
	}
	
	public final Consumer<VrtCard> getOnSelectHand()
	{
		return onSelectHand.get();
	}
	
	public final void setOnSelectHand(Consumer<VrtCard> p)
	{
		onSelectHand.set(p);
	}
	
	public final ObjectProperty<Consumer<VrtCard>> onSelectHandProperty()
	{
		return onSelectHand;
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
		Timeline flip = new MovementTimeline(c).addFrame(FAST_TRANS, frame).generateAnimation();
		flip.play();
		runningAnimation(flip);
	}
	
	public void repositionCards()
	{
		repositionCards(0, hand.size());
	}
	
	@Override
	protected void removeCard(VrtCard c)
	{
		hand.remove(c);
		c.getNode().removeEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnter);
		c.getNode().removeEventHandler(MouseEvent.MOUSE_EXITED, mouseExit);
		c.getNode().removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);
		
		repositionCards();
	}

	@Override
	protected void addCard(VrtCard c)
	{
		hand.add(c);
		
		int flip = 0;
		if (defaultShow.get())
		{
			flip = 180;
			shown.add(c);
		}
		
		MovementFrame frame = new MovementFrame();
		frame.setRotateX(getRotateX());
		frame.setRotateY(getRotateY() + flip);
		frame.setRotateZ(getRotateZ());
		
		Timeline addToHand = new MovementTimeline(c).addFrame(MEDIUM_TRANS, frame).generateAnimation();
		addToHand.setOnFinished(evt -> {
			c.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, mouseExit);
			c.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnter);
			c.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);
		});
		addToHand.play();
		runningAnimation(addToHand);
		
		repositionCards();
	}
	
	private VrtCard identifyCardFrom(Node source)
	{
		for (int i = 0; i < hand.size(); i++)
		{
			VrtCard c = hand.get(i);
			if (c.getNode() == source)
				return c;
		}
		return null;
	}
	
	private void repositionCards(int start, int end)
	{
		double totalWidth = 0;
		for (VrtCard c : hand)
			totalWidth += c.getWidth();
		totalWidth += PADDING * (hand.size() - 1);
		
		double tx = (getWidth() - totalWidth) / 2;
		for (int i = 0; i < end; i++)
		{
			VrtCard c = hand.get(i);
			
			if (i >= start)
			{
				Translate trans = calcRotatedTranslate(tx, 0, 0);
				MovementFrame frame = new MovementFrame();
				
				System.out.println(tx);
				
				frame.setTranslateX(getTranslateX() + trans.getX());
				frame.setTranslateY(getTranslateY() + trans.getY());
				frame.setTranslateZ(getTranslateZ() + trans.getZ());
				Timeline repos = new MovementTimeline(c).addFrame(MEDIUM_TRANS, frame).generateAnimation();
				repos.play();
				runningAnimation(repos);
			}
			
			tx += c.getWidth() + PADDING;
		}
	}
	
	private Translate calcRotatedTranslate(double tx, double ty, double tz)
	{
		Translate t = new Translate();
		Transform tAfter = t.createConcatenation(getRotationConcated());
		return new Translate(tAfter.getTx(), tAfter.getTy(), tAfter.getTz());
	}
}
