package monopolycards.ui;

import javafx.animation.Timeline;

public abstract class UIComponent
{
	public static Timeline animate(UIComponent comp, int finalX, int finalY, int finalZ, int angleX, int angleY, int angleZ)
	{
		
	}
	
	
	/**
	 * Transfers a card into this UIComponent in its position and orientation
	 * @param c the card to move.
	 */
	public abstract void transferCard(CardUI c);
	
	
}
