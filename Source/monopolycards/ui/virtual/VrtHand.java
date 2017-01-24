package monopolycards.ui.virtual;

import java.util.ArrayList;

import javafx.util.Duration;

public class VrtHand extends VrtGroup
{

	private static final int PADDING = 10;
	private ArrayList<VrtCard> hand;
	
	public VrtHand()
	{
		hand = new ArrayList<>();
	}

	@Override
	protected void removeCard(VrtCard c)
	{
		hand.remove(c);
	}

	@Override
	protected void addCard(VrtCard c)
	{
		hand.add(c);
		
		MovementFrame frame = new MovementFrame();
		frame.setRotateX(getRotateX());
		frame.setRotateY(getRotateY());
		frame.setRotateZ(getRotateZ());
		frame.setTranslateX(getTranslateX() + (c.getWidth() + PADDING) * (hand.size() - 1));
		frame.setTranslateY(getTranslateY());
		frame.setTranslateZ(getTranslateZ());
		
		new MovementTimeline(c).addFrame(Duration.seconds(2), frame).generateAnimation().playFromStart();
	}
	
	public void flipAll()
	{
		for (VrtCard c : hand)
			flipCard(c);
	}
	
	public void flipCard(VrtCard c)
	{
		MovementFrame frame = new MovementFrame();
		frame.setRotateY(getRotateY() + 180);
		new MovementTimeline(c).addFrame(Duration.seconds(2), frame).generateAnimation().playFromStart();
	}
}
