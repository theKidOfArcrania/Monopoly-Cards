package monopolycards.ui.virtual;

import java.util.ArrayList;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class VrtDeck extends VrtGroup
{

	private ArrayList<VrtCard> deck;
	private final boolean faceUp;
	private final EventHandler<MouseEvent> mouseClick;
	private final ObjectProperty<Runnable> onDrawDeck;
	
	public VrtDeck()
	{
		this(false);
	}
	public VrtDeck(boolean faceUp)
	{
		deck = new ArrayList<>();
		onDrawDeck = new SimpleObjectProperty<>(this, "onDrawDeck");
		this.faceUp = faceUp;
		
		mouseClick = evt -> {
			if (onDrawDeck.get() != null)
				onDrawDeck.get().run();
		};
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
	
	public void pushCard(VrtCard card)
	{
		getChildren().add(card);
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
	
	@Override
	protected void removeCard(VrtCard c)
	{
		deck.remove(c);
		c.getNode().removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);
		
		int ind = 0;
		for (VrtNode node : getChildren())
		{
			if (node instanceof VrtCard)
			{
				MovementFrame frame = new MovementFrame(Interpolator.EASE_IN);
				frame.setTranslateY(getTranslateY() - (2 * ind));
				new MovementTimeline(node).addFrame(Duration.seconds(.1), frame).generateAnimation().play();
				ind++;
			}
		}
	}

	@Override
	protected void addCard(VrtCard c)
	{
		deck.add(c);
		
		MovementFrame frame = new MovementFrame();
		frame.setRotateX(-90 + getRotateX());
		frame.setRotateY(getRotateY() + (faceUp ? 180 : 0));
		frame.setRotateZ(getRotateZ());
		frame.setTranslateX(getTranslateX());
		frame.setTranslateY(getTranslateY() - 2 * (deck.size() - 1));
		frame.setTranslateZ(getTranslateZ());
		
		Timeline animate = new MovementTimeline(c).addFrame(MEDIUM_TRANS, frame).generateAnimation();
		animate.setOnFinished(evt -> {
			c.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);
		});
		animate.play();
	}
}
