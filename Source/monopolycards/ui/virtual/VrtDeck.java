package monopolycards.ui.virtual;

import java.util.ArrayList;

import javafx.util.Duration;

public class VrtDeck extends VrtGroup
{

	private ArrayList<VrtCard> deck;
	
	public VrtDeck()
	{
		deck = new ArrayList<>();
	}

	@Override
	protected void removeCard(VrtCard c)
	{
		deck.remove(c);
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
