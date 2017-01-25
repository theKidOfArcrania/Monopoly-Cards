package monopolycards.ui.virtual;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.util.Duration;

public class VrtHand extends VrtGroup
{

	private static final int PADDING = 10;
	private ArrayList<VrtCard> hand;
	private HashSet<VrtCard> shown; 
	
	public VrtHand()
	{
		hand = new ArrayList<>();
		shown = new HashSet<>();
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
		new MovementTimeline(c).addFrame(Duration.seconds(2), frame).generateAnimation().playFromStart();
	}
}
