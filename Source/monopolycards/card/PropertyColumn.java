/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

/**
 *
 * @author HW
 */
public class PropertyColumn implements Iterable<Card>, Serializable, Observable {

	private static final long serialVersionUID = 5264125689357215996L;

	private static int getValue(Card card) {
		if (card instanceof Property) {
			return ((Property) card).getValue();
		}
		return card.getSellValue();
	}

	//TODO: configure so that the user can change the full-set.
	//TODO: allow user to pay with houses/hotels
	private final ObservableSet<Property> primarySet = FXCollections.observableSet();
	private final ObservableSet<Card> properties = FXCollections.observableSet();

	private final CardDefaults defs;
	private final PropertyColor propertyColor;

	public PropertyColumn(CardDefaults defs, PropertyColor propertyColor) {
		Objects.requireNonNull(defs);
		this.defs = defs;
		this.propertyColor = propertyColor;
	}

	public boolean add(Card prop) {
		requireNonNull(prop);
		checkCard(prop);
		boolean fullSet = isFullSet();
		if (!fullSet && !(prop instanceof Property))
			throw new IllegalArgumentException("Cannot add houses/hotels before obtaining full set");
		if (!fullSet && prop instanceof Property)
			primarySet.add((Property)prop);
		// TODO: check ref.
		return properties.add(prop);
	}

	public void addAll(Collection<Card> prop) {
		for (Card c : prop)
			add(c);
	}

	public void addAll(PropertyColumn prop) {
		for (Card c : prop)
			add(c);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		properties.addListener(listener);
	}

	public void addListener(SetChangeListener<? super Card> listener) {
		properties.addListener(listener);
	}

	public void clear() {
		properties.clear();
	}

	public boolean contains(Card o) {
		return properties.contains(o);
	}

	/**
	 * This removes any houses and hotels, cashing them into money.
	 *
	 * @return an array of cash that was the former houses/ hotels.
	 */
	public Cash[] downgrade() {
		if (!isFullSet()) {
			return new Cash[0];
		}
		Action[] houses = properties.parallelStream()
				.filter(card -> card instanceof Action)
				.toArray(length -> new Action[length]);
		properties.removeAll(Arrays.asList(houses));
		return Arrays.stream(houses)
				.parallel()
				.map(Action::convertToCash)
				.toArray(length -> new Cash[length]);
	}

	public int getFullSetCount() {
		return defs.getPropertyFullSet(propertyColor);
	}

	public PropertyColor getPropertyColor() {
		return propertyColor;
	}

	/**
	 * Calculates the sum of the property cards.
	 * <p>
	 *
	 * @return the number of property cards in this column.
	 */
	public int getPropertyCount() {
		return properties.parallelStream()
				.mapToInt((card) -> (card instanceof Property) ? 1 : 0)
				.sum();
	}

	public int getRent() {
		if (propertyColor == null)
			return 0;
		if (isRentable()) {
			int base = defs.getRent(propertyColor, getPropertyCount());
			int additional = properties.parallelStream()
					.filter(card -> card instanceof PropertyRaise)
					.mapToInt(card -> ((PropertyRaise) card).getRaiseValue())
					.sum();
			return base + additional;
		}
		return 0;
	}

	/**
	 * Describes whether if this column has loose properties.
	 *
	 * @return true if column has loose properties, false otherwise.
	 */
	public boolean hasIncompleteSet() {
		if (propertyColor == null)
			return true;
		return getPropertyCount() != 0 && getPropertyCount() != defs.getPropertyFullSet(propertyColor);
	}

	public boolean isEmpty() {
		return getPropertyCount() == 0;
	}

	/**
	 * Describes whether if this column contains a full set.
	 * <p>
	 *
	 * @return true for full sets, false otherwise.
	 */
	public boolean isFullSet() {
		if (propertyColor == null)
			return false;
		return primarySet.size() >= defs.getPropertyFullSet(propertyColor);
	}

	/**
	 * This describes whether if it contains any stand-alone property cards.
	 *
	 * @return true if it is rentable, false otherwise.
	 */
	public boolean isRentable() {
		if (propertyColor == null)
			return false;
		return properties.parallelStream()
				.filter((card) -> (card instanceof Property))
				.anyMatch(card -> ((Property) card).canStandAlone());
	}

	@Override
	public Iterator<Card> iterator() {
		return properties.iterator();
	}

	public Stream<Card> parallelStream() {
		return properties.parallelStream();
	}

	public boolean remove(Card o) {
		return properties.remove(o);
	}

	public PropertyColumn removeFullSet() {
		if (!isFullSet()) {
			throw new IllegalStateException("Not a full set.");
		}

		PropertyColumn set = new PropertyColumn(defs, propertyColor);
		for (Property prop : primarySet) {
			properties.remove(prop);
			set.add(prop);
		}
		primarySet.clear();

		ArrayList<Card> misc = properties.parallelStream()
				.filter(card -> !(card instanceof Property))
				.collect(Collectors.toCollection(ArrayList::new));
		set.addAll(misc);
		properties.removeAll(misc);
		return set;
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		properties.removeListener(listener);
	}

	public void removeListener(SetChangeListener<? super Card> listener) {
		properties.removeListener(listener);
	}

	public int size() {
		return properties.size();
	}

	public Stream<Card> stream() {
		return properties.stream();
	}

	@Override
	public String toString() {
		return propertyColor.getColorName() + " set";
	}

	private void checkCard(Card card) {
		if (properties.contains(card)) {
			throw new IllegalArgumentException("Property already exists: " + card);
		}
		if (card instanceof Action) {
			if (!(card instanceof PropertyRaise)) {
				throw new IllegalArgumentException("Must be a property card or a house/hotel card");
			}
			return;
		}

		if (!((Property) card).getDualColors()
				.compatibleWith(propertyColor)) {
			throw new IllegalArgumentException("Must be a property card that has the color " + propertyColor);
		}

	}

	public boolean isDowngradable()
	{
		if (!isFullSet())
			return false;
		
		for (Card c : properties)
		{
			if (!(c instanceof Property))
				return true;
		}
		return false;
	}

	public boolean isPrimarySet(Property card)
	{
		return primarySet.contains(card);
	}

}
