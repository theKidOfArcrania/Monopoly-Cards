package monopolycards.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import monopolycards.card.Card;
import monopolycards.card.CardDefaults;
import monopolycards.card.Cash;
import monopolycards.card.Property;
import monopolycards.card.PropertyColor;
import monopolycards.card.PropertyColumn;
import monopolycards.card.Response;

/**
 * This class describes the cards that a player has at any point.
 * <p>
 *
 * @author HW, Alex
 */
public abstract class Player {

	private static boolean hasPropertyInColumn(Property card, PropertyColumn column) {
		return column.stream()
				.parallel()
				.anyMatch((prop) -> prop == card);
	}

	private final String name;
	private final ArrayList<Card> bank;
	private Board game = null;
	// this is all the cards a player has in his/her hand.
	private final ArrayList<Card> hand;
	private int moves = 0;
	private final ArrayList<PropertyColumn> propertyColumn;

	private final CardDefaults defs;

	public Player(CardDefaults defs, String name) {
		this.name = name;
		this.defs = defs;
		bank = new ArrayList<>(20);
		propertyColumn = new ArrayList<>(8);
		// a player will not exceed 13 cards at any one point, so use that as the array list capacity.
		hand = new ArrayList<>(13);
	}

	public void addBill(Cash card) {
		bank.add(card);
		// TO DO: add ref.
	}

	/**
	 * This alerts the player of a message.
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest to tell the player.
	 */
	public abstract void alert(String prompt);

	public int bankIndexOf(Card c) {
		return bank.indexOf(c);
	}

	public Stream<Card> bankStream() {
		return bank.stream();
	}

	public boolean checkWin() {
		int fullSets = propertyColumn.stream()
				.parallel()
				.reduce(0, (count, column) -> count + (column.isFullSet() ? 1 : 0), (count1, count2) -> count1 + count2);
		return fullSets >= 3;
	}

	public Stream<PropertyColumn> columnStream() {
		return propertyColumn.stream();
	}

	// starts a player's turn with drawing cards.
	@SuppressWarnings("FinalMethod")
	public final void drawCards() {
		Card[] cards = game.getCenterPlay()
				.drawCards(2);
		hand.addAll(Arrays.asList(cards));
	}

	public Card getBankCard(int index) {
		return bank.get(index);
	}

	public int getBankCount() {
		return bank.size();
	}

	public int getCash() {
		// TO DO: this method.
		return 0;
	}

	public CardDefaults getDefaults() {
		return defs;
	}

	public Board getGame() {
		return game;
	}

	public Card getHand(int index) {
		return hand.get(index);
	}

	public int getHandCount() {
		return hand.size();
	}

	public int getMove() {
		return moves;
	}

	public String getName() {
		return name;
	}

	public PropertyColumn getPropertyColumn(int index) {
		return propertyColumn.get(index);
	}

	public PropertyColumn getPropertyColumn(Property card) {
		return propertyColumn.parallelStream()
				.filter(column -> hasPropertyInColumn(card, column))
				.findAny()
				.orElse(null);
	}

	public PropertyColumn getPropertyColumn(PropertyColor color) {
		return propertyColumn.parallelStream()
				.filter(column -> column.getPropertyColor() == color)
				.findAny()
				.orElseGet(() -> {
					PropertyColumn newColumn = new PropertyColumn(defs, color);
					propertyColumn.add(newColumn);
					return newColumn;
				});
	}

	public int getPropertyColumnCount() {
		return propertyColumn.size();
	}

	public Stream<Card> handStream() {
		return hand.stream();
	}

	public boolean hasCompleteSet() {
		return propertyColumn.parallelStream()
				.anyMatch(PropertyColumn::isFullSet);
	}

	public boolean hasIncompleteSet() {
		return propertyColumn.parallelStream()
				.anyMatch(PropertyColumn::hasIncompleteSet);
	}

	public final boolean isLastTurn() {
		return moves == 2;
	}

	public final boolean isTurnDone() {
		return moves >= 3;
	}

	public void moved() {
		moves++;
	}

	// player makes one move.
	@SuppressWarnings("FinalMethod")
	public final boolean playAction(CardAction move) {
		Card played = move.getPlayed();

		if (isTurnDone()) {
			return false;
		}

		hand.remove(played);

		// TO DO: checkReference(current, played);
		if (move.getActionType()
				.getAction()
				.apply(played, this)) {
			moves++;
		}
		return true;
	}

	public void registerGame(Board game) {
		if (this.game != null && this.game.isStarted()) {
			throw new IllegalStateException("This player is already registered to a started game.");
		}
		this.game = game;
	}

	public void removeBill(Cash card) {
		bank.remove(card);
		// TO DO: remove ref.
	}

	public void resetTurn() {
		moves = 0;
	}

	/**
	 * This prompts the player to select a card to play (convenience method)
	 * <p>
	 *
	 * @return the card the player selected.
	 */
	public CardAction selectHand() {
		return selectHand("Please select a card to play.");
	}

	/**
	 * This prompts the player to select a card to play (convenience method)
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @return the card the player selected.
	 */
	public CardAction selectHand(String prompt) {
		return selectHand(prompt, card -> true);
	}

	/**
	 * This prompts the player to select a card to play
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @param filter
	 *            filter for cards to play.
	 * @return the card the player selected.
	 */
	public CardAction selectHand(String prompt, Predicate<Card> filter) {
		return selectHand(prompt, filter, cardAction -> true);
	}

	/**
	 * This prompts the player to select a card to play
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @param filter
	 *            filter for cards to play.
	 * @param actionFilter
	 *            filter for the actions for the card.
	 * @return the card the player selected.
	 */
	public abstract CardAction selectHand(String prompt, Predicate<Card> filter, Function<CardActionType, Boolean> actionFilter);

	/**
	 * This prompts the player of a payment that must be made.
	 * <p>
	 *
	 * @param amount
	 *            the debt amount.
	 */
	public abstract void selectPayment(Payment amount);

	/**
	 * This prompts the player to select another player
	 * <p>
	 *
	 * @return another player that this player selected
	 */
	public Player selectPlayer() {
		return selectPlayer("Please select your target player.");
	}

	/**
	 * This prompts the player to select another player
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @return another player that this player selected
	 */
	public Player selectPlayer(String prompt) {
		return selectPlayer(prompt, player -> true);
	}

	/**
	 * This prompts the player to select another player
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @param filter
	 *            the filter for the other players
	 * @return another player that this player selected
	 */
	public abstract Player selectPlayer(String prompt, Predicate<Player> filter);

	/**
	 * This prompts the player to select a property column to use (convenience method)
	 * <p>
	 *
	 * @return the property the player selected.
	 */
	public Property selectProperty() {
		return selectProperty("Please select a property column to use.");
	}

	/**
	 * This prompts the player to select a property to use (convenience method)
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @return the property the player selected.
	 */
	public Property selectProperty(String prompt) {
		return selectProperty(prompt, card -> true);
	}

	/**
	 * This prompts the player to select a property to use (convenience method)
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @param filter
	 *            filter for properties
	 * @return the property the player selected.
	 */
	public Property selectProperty(String prompt, Predicate<Property> filter) {
		return selectProperty(prompt, filter, this);
	}

	/**
	 * This prompts the player to select a property to use from a particular player
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @param filter
	 *            filter for properties
	 * @param context
	 *            the property columns that the player will select from.
	 * @return the property the player selected.
	 */
	public abstract Property selectProperty(String prompt, Predicate<Property> filter, Player context);

	/**
	 * This prompts the player to select a property column to use (convenience method)
	 * <p>
	 *
	 * @return the property column the player selected.
	 */
	public PropertyColumn selectPropertyColumn() {
		return selectPropertyColumn("Please select a property column to use.");
	}

	/**
	 * This prompts the player to select a property column to use (convenience method)
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @return the property column the player selected.
	 */
	public PropertyColumn selectPropertyColumn(String prompt) {
		return selectPropertyColumn(prompt, card -> true);
	}

	/**
	 * This prompts the player to select a property column to use (convenience method)
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @param filter
	 *            filter for property columns
	 * @return the property column the player selected.
	 */
	public PropertyColumn selectPropertyColumn(String prompt, Predicate<PropertyColumn> filter) {
		return selectPropertyColumn(prompt, filter, this);
	}

	/**
	 * This prompts the player to select a property column to use from a particular player
	 * <p>
	 *
	 * @param prompt
	 *            the selectRequest that the player will see.
	 * @param filter
	 *            filter for property columns
	 * @param context
	 *            the property columns that the player will select from.
	 * @return the property column the player selected.
	 */
	public abstract PropertyColumn selectPropertyColumn(String prompt, Predicate<PropertyColumn> filter, Player context);

	/**
	 * This prompts the player a yes or no question.
	 * <p>
	 *
	 * @param prompt
	 *            the select request to ask the player
	 * @return true if player responds with a "yes" or false otherwise.
	 */
	public abstract boolean selectRequest(String prompt);

	/**
	 * This prompts the player whether to agree
	 * <p>
	 *
	 * @param prompt
	 *            the prompt that the player will see.
	 * @return null if player accepts, the response card (currently only a just say no) if player rejects.
	 */
	public abstract Response selectResponse(String prompt);

	/**
	 * This method allows for a pause before player starts a turn... ie if player needs to be prompted to start turn and draw cards.
	 */
	public abstract void selectTurn();
}
