/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card.standard;

import monopolycards.card.DualColor;
import monopolycards.card.PropertyColor;
import monopolycards.card.PropertyColumn;
import monopolycards.impl.CardActionType;
import monopolycards.impl.Player;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Henry
 */
public class RentCard extends ActionCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9126449081012411183L;
	private final DualColor propertyColors;

	/**
	 * Constructs an all-color wild rent (except gold, if applicable).
	 */
	public RentCard() {
		propertyColors = new DualColor();
	}

	/**
	 * Constructs a one-color rent card.
	 *
	 * @param propertyColor
	 *            What color this rent card represents.
	 */
	public RentCard(PropertyColor propertyColor) {
		// all parameters MUST be non-null.
		requireNonNull(propertyColor);
		propertyColors = new DualColor(propertyColor);
	}

	/**
	 * Constructs a bi-color wild card rent card.
	 * <p>
	 *
	 * @param propertyColor1
	 *            The first color of this wild card.
	 * @param propertyColor2
	 *            The second color of this wild card.
	 */
	public RentCard(PropertyColor propertyColor1, PropertyColor propertyColor2) {
		// all parameters MUST be non-null;
		requireNonNull(propertyColor1);
		requireNonNull(propertyColor2);
		propertyColors = new DualColor(propertyColor1, propertyColor2);
	}

	/**
	 * Constructs a Rent Card with the internal typing. This is the constructor called by card creator.
	 *
	 * @param internalType
	 *            the internal type of the rent-card color
	 */
	public RentCard(String internalType) {
		propertyColors = new DualColor(internalType);
	}

	@Override
	public boolean actionPlayed(Player self) {
		int rent = self.columnStream()
				.parallel()
				.filter(this::isValidRent)
				.mapToInt(PropertyColumn::getRent)
				.max()
				.orElse(0);
		if (rent == 0) {
			return false;
		}
		return payRequest(self, isGlobal(), rent, "rent");
	}

	@Override
	public String getCardName() {
		return propertyColors.getColorName() + " Rent";
	}

	@Override
	public String getInternalType() {
		return "action.rent." + propertyColors.getInternalType();
	}

	@Override
	public boolean isEnabled(Player self, CardActionType action) {
		if (action.getInternalType()
				.equals("move.action")) {
			return self.columnStream()
					.parallel()
					.anyMatch(this::isValidRent);
		}
		return true;
	}

	public boolean isGlobal() {
		return this.getInternalIntProperty("global", 0) != 0;
	}

	public boolean isValidRent(PropertyColumn column) {
		return propertyColors.compatibleWith(column.getPropertyColor()) && column.getRent() > 0;
	}

}
