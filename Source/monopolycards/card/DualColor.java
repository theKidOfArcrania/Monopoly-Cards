/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card;

import static java.util.Objects.requireNonNull;
import static monopolycards.card.PropertyColor.Beige;
import static monopolycards.card.PropertyColor.Black;
import static monopolycards.card.PropertyColor.Blue;
import static monopolycards.card.PropertyColor.Brown;
import static monopolycards.card.PropertyColor.Green;
import static monopolycards.card.PropertyColor.LightBlue;
import static monopolycards.card.PropertyColor.Megenta;
import static monopolycards.card.PropertyColor.Orange;
import static monopolycards.card.PropertyColor.Red;
import static monopolycards.card.PropertyColor.Yellow;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 *
 * @author HW
 */
public class DualColor implements Serializable {

	private static final long serialVersionUID = -2188904758680736571L;
	private static final Map<String, DualColor> internalTypingColors;

	static {
		DualColor[] availableWildColors = new DualColor[] { new DualColor(), // rainbow
				// wild rent/wild card properties
				new DualColor(Brown, LightBlue), new DualColor(Megenta, Orange), new DualColor(Red, Yellow), 
				new DualColor(Green, Blue),
				new DualColor(Beige, Black),
				// wild-card properties only.
				new DualColor(Black, Green), new DualColor(Black, LightBlue) };
		PropertyColor[] colors = PropertyColor.class.getEnumConstants();
		DualColor[] availableColors = new DualColor[availableWildColors.length + colors.length];
		for (int i = 0; i < colors.length; i++)
			availableColors[i] = new DualColor(colors[i]);
		for (int i = 0; i < availableWildColors.length; i++)
			availableColors[i + colors.length] = availableWildColors[i];
		
		internalTypingColors = Arrays.stream(availableColors)
				.collect(Collectors.toConcurrentMap(DualColor::getInternalType, UnaryOperator.identity()));

	}

	private static String colorString(PropertyColor propertyType) {
		String propName = propertyType.name();
		return propName.substring(0, 1)
				.toLowerCase() + propName.substring(1);
	}

	private final String colorName;
	private final PropertyColor propertyColor;

	// this is only used if this is a wild card.
	private final PropertyColor propertyColor2;

	/**
	 * Constructs an all-color wild card (except gold, if applicable) DualColor.
	 */
	public DualColor() {
		this.colorName = "Rainbow";
		this.propertyColor = null;
		this.propertyColor2 = null;
	}

	/**
	 * Constructs a single color DualColor.
	 * <p>
	 *
	 * @param propertyColor
	 *            What color this property card represents.
	 */
	public DualColor(PropertyColor propertyColor) {
		// all parameters MUST be non-null.
		requireNonNull(propertyColor);
		this.colorName = propertyColor.getColorName();
		this.propertyColor = propertyColor;
		// set it to null because this is a single color property.
		propertyColor2 = null;
	}

	/**
	 * Constructs a bi-color wild card property card. Note that the property name IS "Wild Card"
	 * <p>
	 *
	 * @param propertyColor1
	 *            The first color of this wild card.
	 * @param propertyColor2
	 *            The second color of this wild card.
	 */
	@SuppressWarnings("AssignmentToMethodParameter")
	public DualColor(PropertyColor propertyColor1, PropertyColor propertyColor2) {
		// all parameters MUST be non-null;
		requireNonNull(propertyColor1);
		requireNonNull(propertyColor2);

		if (propertyColor1 == PropertyColor.Gold || propertyColor2 == PropertyColor.Gold) {
			throw new IllegalArgumentException("Gold cannot be included in wild card");
		}

		// always have same ordering, as how the colors were sorted in the first place.
		if (propertyColor1.compareTo(propertyColor2) > 0) {
			this.propertyColor = propertyColor1;
			this.propertyColor2 = propertyColor2;
		} else if (propertyColor1 == propertyColor2) {
			throw new IllegalArgumentException("Must have two DIFFERENT property colors");
		} else {
			this.propertyColor = propertyColor1;
			this.propertyColor2 = propertyColor2;
		}
		this.colorName = propertyColor1 + " -OR- " + propertyColor2;
	}

	public DualColor(String internalType) {
		DualColor colors = internalTypingColors.get(internalType);
		if (colors == null) {
			System.out.println(internalTypingColors);
			System.out.println(internalType);
			throw new IllegalArgumentException();
		}

		this.propertyColor = colors.propertyColor;
		this.propertyColor2 = colors.propertyColor2;
		this.colorName = colors.colorName;
	}

	/**
	 * This determines whether if this property card and the <code>other</code> property card can be put together under one property column.
	 * <p>
	 *
	 * @param other
	 *            the other dual color to compare against.
	 * @return true if this is compatible, false otherwise.
	 */
	public boolean compatibleWith(DualColor other) {
		if (this.isAllWildCard()) {
			return other.isWildCard() || other.getPropertyColor() != PropertyColor.Gold;
		} else if (other.isAllWildCard()) {
			return isWildCard() || getPropertyColor() != PropertyColor.Gold;
		} else {
			boolean compatible = getPropertyColor() == other.getPropertyColor() || getPropertyColor() == other.getPropertyColor2();
			boolean compatible2 = getPropertyColor2() == other.getPropertyColor() || getPropertyColor2() == other.getPropertyColor2();
			return compatible || (getPropertyColor2() != null && compatible2);
		}
	}

	/**
	 * This determines whether if this property card and the <code>other</code> color can be put together under one property column.
	 * <p>
	 *
	 * @param other
	 *            the other color to compare against.
	 * @return true if this is compatible, false otherwise.
	 */
	public boolean compatibleWith(PropertyColor other) {
		if (other == null)
			return false;
		if (this.isAllWildCard()) {
			return other != PropertyColor.Gold;
		} else {
			boolean compatible = getPropertyColor() == other;
			boolean compatible2 = getPropertyColor2() == other;
			return compatible || (getPropertyColor2() != null && compatible2);
		}
	}

	/**
	 * Getter of the property name for this card.
	 * <p>
	 *
	 * @return the value of colorName
	 */
	public String getColorName() {
		return colorName;
	}

	public String getInternalType() {
		if (isWildCard()) {
			if (isAllWildCard()) {
				return "wild.rainbow";
			} else {
				return "wild." + colorString(propertyColor) + propertyColor2.name();
			}
		} else {
			return colorString(propertyColor);
		}
	}

	/**
	 * This retrieves the color of the property color 1. If this is null, then the card is an rainbow wild-card.
	 * <p>
	 *
	 * @return property color 1
	 */
	public PropertyColor getPropertyColor() {
		return propertyColor;
	}

	/**
	 * This retrieves the color of the property color 2. If this is null, then the card is a single-color card if color 1 is not null. If this is non-null, then the card is a dual color wild.
	 * <p>
	 *
	 * @return property color 2
	 */
	public PropertyColor getPropertyColor2() {
		return propertyColor2;
	}

	/**
	 * Describes whether if this DualColor is a all-color wild.
	 * <p>
	 *
	 * @return true if this is all-color wild, false otherwise.
	 */
	@SuppressWarnings("FinalMethod")
	public final boolean isAllWildCard() {
		return propertyColor2 == null && propertyColor == null;
	}

	/**
	 * Describes whether if this DualColor is a wild color.
	 * <p>
	 *
	 * @return true if this is wild, false if this is regular.
	 */
	@SuppressWarnings("FinalMethod")
	public final boolean isWildCard() {
		return propertyColor2 != null || propertyColor == null;
	}

}
