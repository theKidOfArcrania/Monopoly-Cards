package monopolycards.card;

import monopolycards.impl.CardActionType;
import monopolycards.impl.Player;
import monopolycards.impl.SupportedActions;

public interface Property extends Valuable {

	//public static final CardActionType Property = new CardActionType("Add Property", "move.property");

	@Override
	default boolean actionPlayed(Player self) {
		PropertyColumn column = self.getPropertyColumn(getDualColors().getPropertyColor());
		column.add(this);
		return true;
	}

	/**
	 * Determines whether if this card can stand alone by itself. Meaning, if you can rent other people with this card.
	 * <p>
	 *
	 * @return true if it can stand alone, false otherwise.
	 */
	boolean canStandAlone();

	/**
	 * This determines whether if this property card and the <code>other</code> property card can be put together under one property set.
	 * <p>
	 *
	 * @param other
	 *            the other property to compare against.
	 * @return true if this is compatible, false otherwise.
	 */
	boolean compatibleWith(Property other);

	DualColor getDualColors();

	/**
	 * Getter of the property name for this card.
	 * <p>
	 *
	 * @return the value of propertyName
	 */
	String getPropertyName();

	@Override
	default SupportedActions getSupportedTypes(Player self) {
		SupportedActions actions = new SupportedActions();
		actions.addAction(new CardActionType("Add property", "move.property", getDefaults()));
		actions.addAction(new CardActionType("Discard", "move.discard", getDefaults()));
		filterSupportedTypes(self, actions);
		return actions;
	}

	/**
	 * Describes whether if this card is a all-color wild card.
	 * <p>
	 *
	 * @return true if this is all-color wild, false otherwise.
	 */
	boolean isAllWildCard();

	/**
	 * Describes whether if this card is a wild card.
	 * <p>
	 *
	 * @return true if this is wild, false if this is regular.
	 */
	boolean isWildCard();

}