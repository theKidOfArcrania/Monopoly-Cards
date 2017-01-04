/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card.standard;

import static java.util.Objects.requireNonNull;

import monopolycards.card.DualColor;
import monopolycards.card.Property;
import monopolycards.card.PropertyColor;
import monopolycards.impl.CardActionType.Likeness;
import monopolycards.impl.Player;

/**
 * This class encapsulates a property card, used for winning, and also for renting from. This also has implementation for wild cards, based on the two properties that determine the color type of a property: <code>propertyColors</code> and <code>propertyColor2</code>. In a regular property card, you
 * would have just <code>propertyColors</code> filled as the color of this property. In a bi-color wild card, both of these properties will be filled as to which colors it can rotate to. In an all-color wild card, both of these properties are left to be NULL. Note: when determining rents, don't use
 * these two properties directly, rather, use the helper functions {@link #compatibleWith(monopolycards.card.Property)} and {@link #canStandAlone()} to determine whether if this can
 * <p>
 *
 * @author HW
 */
@SuppressWarnings("FinalClass")
public final class PropertyCard extends StandardCard implements Property {

	/**
	 *
	 */
	private static final long serialVersionUID = -4847169645075457537L;
	private int propNumber = 0;
	private final DualColor propertyColors;

	/**
	 * Constructs an all-color wild card (except gold, if applicable).
	 */
	public PropertyCard() {
		propertyColors = new DualColor();
	}

	/**
	 * Constructs a regular property card.
	 * <p>
	 *
	 * @param propNumber
	 *            The number of the property (ie 1st Blue Property or 3rd Green Property)
	 * @param propertyColor
	 *            What color this property card represents.
	 */
	public PropertyCard(int propNumber, PropertyColor propertyColor) {
		// all parameters MUST be non-null.
		requireNonNull(propNumber);
		requireNonNull(propertyColor);
		this.propNumber = propNumber;
		this.propertyColors = new DualColor(propertyColor);
	}

	/**
	 * Constructs a bi-color wild card property card.
	 * <p>
	 *
	 * @param propertyColor1
	 *            The first color of this wild card.
	 * @param propertyColor2
	 *            The second color of this wild card.
	 */
	public PropertyCard(PropertyColor propertyColor1, PropertyColor propertyColor2) {
		// all parameters MUST be non-null;
		requireNonNull(propertyColor1);
		requireNonNull(propertyColor2);
		propertyColors = new DualColor(propertyColor1, propertyColor2);
	}

	/**
	 * Constructs a Property Card with the internal typing. This is the constructor called by card creator.
	 *
	 * @param internalType
	 *            the internal type of the property-card color
	 */
	public PropertyCard(String internalType) {
		if (internalType.contains("$")) {
			int propNumIndex = internalType.indexOf("$");
			propertyColors = new DualColor(internalType.substring(0, propNumIndex));
			propNumber = Integer.parseInt(internalType.substring(propNumIndex + 1));
		} else {
			propertyColors = new DualColor(internalType);
			if (!propertyColors.isWildCard()) {
				throw new IllegalArgumentException("Single property card must have '$' and prop number as a suffix.");
			}
		}
	}

	@Override
	public boolean canStandAlone() {
		return !isAllWildCard();
	}

	@Override
	public boolean compatibleWith(Property other) {

		return propertyColors.compatibleWith(other.getDualColors());
	}

	@Override
	public String getCardName() {
		return getPropertyName();
	}

	@Override
	public DualColor getDualColors() {
		return propertyColors;
	}

	@Override
	public String getInternalType() {
		return "props." + propertyColors.getInternalType() + (isWildCard() ? "" : "." + propNumber);
	}

	@Override
	public String getPropertyName() {
		if (isWildCard()) {
			return propertyColors.getColorName() + " Wild";
		}
		return getInternalProperty("name"); //+ "#" + propNumber;
	}

	@Override
	public int getSellValue() {
		return 0;
	}

	@Override
	public int getValue() {
		return getInternalIntProperty("value");
	}

	@Override
	public boolean isAllWildCard() {
		return propertyColors.isAllWildCard();
	}

	@Override
	public boolean isEnabled(Player self, Likeness action) {
		return true;
	}

	@Override
	public boolean isWildCard() {
		return propertyColors.isWildCard();
	}

}
