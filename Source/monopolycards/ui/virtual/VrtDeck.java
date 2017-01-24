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
		frame.setRotateX(-90);
		frame.setRotateY(0);
		frame.setRotateZ(0);
		frame.setTranslateX(getTranslateX());
		frame.setTranslateY(getTranslateY() - 2 * (deck.size() - 1));
		frame.setTranslateZ(getTranslateZ());
		
		new MovementTimeline(c).addFrame(Duration.seconds(2), frame).generateAnimation().playFromStart();
	}
	
//	7 - 7 * 4 + 8
//	
//	7 7 4 * - 8 +
//	+ - 7 * 7 4 8
//	
//	((A-B)/(X+Y)) ^Q
//	
//	^/-AB+XYQ
//	AB-XY+/Q^
	
	

}