package monopolycards.ui.virtual;

import java.util.ArrayList;

import javafx.animation.Interpolator;
import javafx.util.Duration;

public class VrtDeck extends VrtGroup
{

	private ArrayList<VrtCard> deck;
	
	public VrtDeck()
	{
		deck = new ArrayList<>();
	}

	public void pushCard(VrtCard card)
	{
		getChildren().add(card);
	}
	
	public VrtCard popCard()
	{
		for (int i = deck.size(); i >= 0; i--)
		{
			VrtNode node = getChildren().get(i);
			if (node instanceof VrtCard)
				return (VrtCard)node;
		}
		return null;
	}
	
	@Override
	protected void removeCard(VrtCard c)
	{
		deck.remove(c);
		
		int ind = 0;
		for (VrtNode node : getChildren())
		{
			if (node instanceof VrtCard)
			{
				MovementFrame frame = new MovementFrame(Interpolator.EASE_IN);
				frame.setTranslateY(getTranslateY() - (2 * ind));
				new MovementTimeline(node).addFrame(Duration.seconds(.3), frame).generateAnimation().playFromStart();
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
		frame.setRotateY(getRotateY());
		frame.setRotateZ(getRotateZ());
		frame.setTranslateX(getTranslateX());
		frame.setTranslateY(getTranslateY() - 2 * (deck.size() - 1));
		frame.setTranslateZ(getTranslateZ());
		
		new MovementTimeline(c).addFrame(Duration.seconds(2), frame).generateAnimation().playFromStart();
	}
}
