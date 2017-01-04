/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import monopolycards.ResourceDefaults;

//action.[actionName].* --> this set of properties describes an action of that name.
//action.rent.[color] --> this set of properties describes an rent action of that color type.
//cash.[value].* --> this set of properties describe a cash card of a specific value.
//deck.length --> determines the number of card entries.
//deck.[index].class --> determines the class of the card at that index.
//deck.[index].subType --> determines the internal sub type of the card at that index.
//deck.[index].count --> determines how many of these cards are in the deck. (optional)
//props.[color].fullset --> this is the number of properties in a full set of that color.
//props.[color].rent.[num] --> this is the rent of the properties of that number of properties.
//props.[color].[num].* --> this set of properties describes a property card of the specific color of the specific number.
//props.wild.[colorCombo].* --> this set of properties describes a property card wild of the specific colors
//scale --> this property sets the multiplier on all number properties starting with a $.
//
//Card Prop-set:
//[card].image --> this is the image filepath of the card.
//
//Action Card Prop-set (extends from Card):
//[card].sellValue --> this defines the sell value.
//[card].description --> this specifies a detailed description of the card.
//[card].name --> name of card
//[card].actionName --> action tooltip text (optional)
//
//Property Card Prop-set (extends from Card):
//[card].name --> this defines the property names.
//[card].value --> this defines the value of the property.

/**
 * This class determines some basic property values such as rents, number of properties in full set, etc.
 * <p>
 *
 * @author HW
 */
public abstract class CardDefaults extends ResourceDefaults {

	private static final long serialVersionUID = -7651695787655927167L;

	private static String colorString(PropertyColor propertyType) {
		String propName = propertyType.name();
		return propName.substring(0, 1)
				.toLowerCase() + propName.substring(1);
	}

	protected CardDefaults(String propFile) throws IOException {
		load(ClassLoader.getSystemResourceAsStream(propFile));
	}

	public Deck generateDeck() throws DeckInitializationFailureException {
		int length = getIntProperty("deck.length", 0);
		Deck generated = new Deck();
		try {
			for (int i = 0; i < length; i++) {
				String className = getProperty("deck." + i + ".class");
				String subType = getProperty("deck." + i + ".subType");

				if (className == null) {
					throw new DeckInitializationFailureException("Class name is omitted.");
				}
				
				Class<?> cardCls = Class.forName(className);
				if (!AbstractCard.class.isAssignableFrom(cardCls))
					throw new DeckInitializationFailureException(className + " is not an instance of AbstractCard");
				
				if (subType == null) {
					generated.add((AbstractCard)cardCls.newInstance());
				} else {
					generated.add((AbstractCard)cardCls.getConstructor(String.class).newInstance(subType));
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new DeckInitializationFailureException(e);
		}
		generated.finalizeDeck();
		return generated;
	}

	public int getPropertyFullSet(PropertyColor propertyType) {
		String propName = colorString(propertyType);
		return getIntProperty("props." + propName + ".fullset", 0);
	}

	public int getRent(PropertyColor propertyType, int propertyCount) {
		if (propertyCount < 0) {
			throw new IllegalArgumentException("property count must not be negative.");
		}
		int maxConciousCount = Math.max(propertyCount, getPropertyFullSet(propertyType));

		if (maxConciousCount == 0) {
			return 0;
		}

		String propName = colorString(propertyType);
		return getIntProperty("props." + propName + ".rent." + maxConciousCount, 0);
	}

}
