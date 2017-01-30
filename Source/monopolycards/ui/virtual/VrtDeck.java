package monopolycards.ui.virtual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class VrtDeck extends VrtGroup
{
	private static final double UNEVEN = 10.0;
	
	private ArrayList<VrtCard> deck;
	private HashMap<VrtCard, Animation> prevRunning;
	private final boolean faceUp;
	private final EventHandler<MouseEvent> mouseClick;
	private final ObjectProperty<Runnable> onDrawDeck;
	
	private BooleanProperty uneven = new SimpleBooleanProperty(this, "uneven");
	
	public VrtDeck()
	{
		this(false);
	}
	public VrtDeck(boolean faceUp)
	{
		deck = new ArrayList<>();
		prevRunning = new HashMap<>();
		onDrawDeck = new SimpleObjectProperty<>(this, "onDrawDeck");
		this.faceUp = faceUp;
		
		mouseClick = evt -> {
			if (onDrawDeck.get() != null)
				onDrawDeck.get().run();
		};
	}

	public boolean isEmpty()
	{
		return deck.isEmpty();
	}
	
	public final Runnable getOnDrawDeck()
	{
		return onDrawDeck.get();
	}
	
	public final void setOnDrawDeck(Runnable p)
	{
		onDrawDeck.set(p);
	}
	
	public final ObjectProperty<Runnable> onDrawDeckProperty()
	{
		return onDrawDeck;
	}
	
	public final BooleanProperty unevenProperty()
	{
		return this.uneven;
	}
	
	public final boolean isUneven()
	{
		return this.unevenProperty().get();
	}
	
	public final void setUneven(final boolean uneven)
	{
		this.unevenProperty().set(uneven);
	}
	
	public void pushCard(VrtCard card)
	{
		getChildren().add(card);
	}
	
	public void shuffle()
	{
		VrtDeck tmp = new VrtDeck(faceUp);
		
		double maxWidth = 0;
		for (VrtNode n : getChildren())
			maxWidth = Math.max(maxWidth, n.getWidth());
		tmp.setTranslateX(getTranslateX() + maxWidth + 10);
		tmp.setTranslateY(getTranslateY());
		tmp.setTranslateZ(getTranslateZ());
		tmp.setRotateX(getRotateX());
		tmp.setRotateY(getRotateY());
		tmp.setRotateZ(getRotateZ());
		
		Iterator<VrtNode> itr = getChildren().iterator();
		while (itr.hasNext())
		{
			VrtNode n = itr.next();
			if (n instanceof VrtCard && Math.random() < .5)
			{
				itr.remove();
				tmp.pushCard((VrtCard)n);
			}
		}
		
		tmp.animatingProperty().addListener(new InvalidationListener()
		{
			
			@Override
			public void invalidated(Observable observable)
			{
				if (!tmp.isAnimating())
				{
					tmp.animatingProperty().removeListener(this);
					while (!tmp.isEmpty())
					{
						int rand = (int)(Math.random() * (deck.size() + 1));
						getChildren().add(rand, tmp.popCard());
					}
					repositionCards(null);					
				}
				
			}
		});
	}
	
	public VrtCard popCard()
	{
		for (int i = deck.size() - 1; i >= 0; i--)
		{
			VrtNode node = getChildren().get(i);
			if (node instanceof VrtCard)
			{
				getChildren().remove(node);
				return (VrtCard)node;
			}
		}
		
		return null;
	}
	
	public void updatePosition()
	{
		repositionCards(null);
	}
	
	@Override
	protected void removeCard(VrtCard c)
	{
		deck.remove(c);
		c.getNode().removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);
		repositionCards(null);
	}

	@Override
	protected void addCard(VrtCard c)
	{
		double offset = uneven.get() ? Math.random() * UNEVEN * 2 - UNEVEN : 0;
		double offset2 = uneven.get() ? Math.random() * UNEVEN * 2 - UNEVEN : 0;
		
		int ind = 0;
		for (VrtNode n : getChildren())
		{
			if (n instanceof VrtCard)
			{
				if (n == c)
					break;
				ind++;
			}
		}
		deck.add(ind, c);
		
		MovementFrame frame = new MovementFrame();
		frame.setRotateX(getRotateX() - 90);
		if (faceUp)
			frame.setRotateX(-frame.getRotateX());
		frame.setRotateY(getRotateY() + (faceUp ? 180 : 0));
		frame.setRotateZ(getRotateZ());
		frame.setTranslateX(getTranslateX() + offset);
		frame.setTranslateY(getTranslateY() - ind * VrtCard.CARD_DEPTH);
		frame.setTranslateZ(getTranslateZ() + offset2);
		
		Timeline animate = new MovementTimeline(c).addFrame(MEDIUM_TRANS, frame).generateAnimation();
		animate.setOnFinished(evt -> {
			c.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);
		});
		animate.play();
		
		if (!isAnimating()) //Already reposition in progress.
			repositionCards(c);
		
		runningAnimation(animate);
	}
	
	private void repositionCards(VrtCard ignore)
	{
		int ind = 0;
		for (VrtNode node : getChildren())
		{
			if (node instanceof VrtCard)
			{
				if (ignore != node)
				{
					Animation prevAnimate = prevRunning.get(node);
					if (prevAnimate != null && prevAnimate.getStatus() != Status.STOPPED)
						prevAnimate.stop();
					
					MovementFrame frame = new MovementFrame(Interpolator.EASE_IN);
					frame.setTranslateY(getTranslateY() - ind * VrtCard.CARD_DEPTH);
					Timeline shift = new MovementTimeline(node).addFrame(MEDIUM_TRANS, frame).generateAnimation();
					shift.play();
					prevRunning.put((VrtCard)node, shift);
					runningAnimation(shift);
				}
				ind++;
			}
		}
	}
}
