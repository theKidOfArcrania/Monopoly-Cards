package monopolycards.card;

import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import monopolycards.impl.CardActionType;
import monopolycards.impl.CardActionType.Likeness;
import monopolycards.impl.Player;
import monopolycards.impl.SupportedActions;

public interface Card extends Serializable {

	/**
	 * This method is called by the Board class whenever this card is played. Any special actions that would happen will be placed in here.
	 * <p>
	 *
	 * @param self
	 *            the player playing this card.
	 * @return true if this action is done, false if this action was canceled.
	 */
	boolean actionPlayed(Player self);

	int getCardId();

	/**
	 * Getter for the name of this card.
	 * <p>
	 *
	 * @return the name of this card
	 */
	String getCardName();

	String getDescription();

	/**
	 * This retrieves the image of the card when face up. Default implementations will look to the carddefs.
	 * <p>
	 *
	 * @return the image of this card.
	 * @exception IOException
	 *                when the image cannot be read
	 */
	Image getImage() throws IOException;

	/**
	 * Gets the internal type of this card. For example, Parker Place (first blue property card) would have an internal type of "props.blue.1"
	 * <p>
	 *
	 * @return the internal type of this card.
	 */
	String getInternalType();

	/**
	 * Getter for the card sell value. This value is used if a player decides to use it as money.
	 * <p>
	 *
	 * @return the monetary value of this card, or 0 if it cannot be sold.
	 */
	int getSellValue();

	/**
	 * This retrieves the supported actions that this card can do.
	 * <p>
	 *
	 * @return The supported actions in an array.
	 */
	default SupportedActions getSupportedTypes(Player self) {
		SupportedActions actions = new SupportedActions();
		actions.addAction(new CardActionType("Play " + getCardName(), "move.action", getDefaults()));
		actions.addAction(new CardActionType("Discard", "move.discard", getDefaults()));
		filterSupportedTypes(self, actions);
		return actions;
	}

	default void filterSupportedTypes(Player self, SupportedActions actions)
	{
		Iterator<CardActionType> itr = actions.iterator();
		while (itr.hasNext())
		{
			if (!isEnabled(self, itr.next().getLikeness()))
				itr.remove();
		}
	}
	
	boolean isEnabled(Player self, Likeness action);
	
	CardDefaults getDefaults();
}