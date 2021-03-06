package monopolycards.impl;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import monopolycards.card.Card;
import monopolycards.card.CardDefaults;
import monopolycards.card.Cash;
import monopolycards.card.Property;
import monopolycards.card.PropertyColor;
import monopolycards.card.PropertyColumn;
import monopolycards.card.ResponseType;

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

	private final ReadOnlyIntegerProperty cashAmount;
	private final CardDefaults defs;
	private final String name;
	private final ObservableList<Cash> bank;
	private int cashCache = -1;
	private int setCache = -1;
	private Board game = null;
	// this is all the cards a player has in his/her hand.
	private final ObservableList<Card> hand;
	private int moves = 0;
	private final ObservableList<PropertyColumn> propertyColumn;
	private final ObservableList<CardAction> playerHistory;
	private final ReadOnlyIntegerProperty propertySets;

	public Player(CardDefaults defs, String name) {
		this.name = name;
		this.defs = defs;
		bank = FXCollections.observableArrayList();
		propertyColumn = FXCollections.observableArrayList();
		hand = FXCollections.observableArrayList();
		playerHistory = FXCollections.observableArrayList();
		cashAmount = new ReadOnlyIntegerPropertyBase() {
			{
				bank.addListener((ListChangeListener<Cash>) event -> {
					while (event.next())
					{
						if (event.wasPermutated()) {
							// no change needed to fire.
							return;
						} else if (event.wasUpdated()) {
							cashCache = -1;
						} else {
							cashCache += event.getAddedSubList()
									.parallelStream()
									.mapToInt(Cash::getValue)
									.sum();
							cashCache -= event.getRemoved()
									.parallelStream()
									.mapToInt(Cash::getValue)
									.sum();
						}
					}
					this.fireValueChangedEvent();
				});
			}

			@Override
			public int get() {

				if (cashCache == -1) { // invalidated
					return cashCache = bank.parallelStream()
							.mapToInt(Cash::getValue)
							.sum();
				}
				return cashCache;
			}

			@Override
			public Object getBean() {
				return this;
			}

			@Override
			public String getName() {
				return "cashAmount";
			}

		};

		propertySets = new ReadOnlyIntegerPropertyBase() {
			{
				InvalidationListener columnList = event -> {
					setCache = -1;
					this.fireValueChangedEvent();
				};
				propertyColumn.addListener((ListChangeListener<PropertyColumn>) event -> {
					while (event.next())
					{
						if (event.wasAdded()) {
							event.getAddedSubList()
							.parallelStream()
							.forEach(column -> column.addListener(columnList));
						}

						setCache = -1;
					}
					this.fireValueChangedEvent();
				});
			}

			@Override
			public int get() {
				if (setCache == -1) { // invalidated
					return setCache = (int) propertyColumn.parallelStream()
							.filter(PropertyColumn::isFullSet)
							.count();
				}
				return setCache;
			}

			@Override
			public Object getBean() {
				return this;
			}

			@Override
			public String getName() {
				return "propertySets";
			}

		};
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

	public Stream<Cash> bankStream() {
		return bank.stream();
	}

	public ReadOnlyIntegerProperty cashAmountProperty() {
		return cashAmount;
	}

	public boolean checkWin() {
		int fullSets = propertyColumn.stream().parallel()
				.reduce(0, (count, column) -> count + (column.isFullSet() ? 1 : 0), (count1, count2) -> count1 + count2);
		return fullSets >= 3;
	}

	public Stream<PropertyColumn> columnStream() {
		return propertyColumn.stream();
	}

	// starts a player's turn with drawing cards.
	@SuppressWarnings("FinalMethod")
	public final void drawCards() {
		int draw = 2;
		if (hand.isEmpty())
			draw = 5;
		
		Card[] cards = game.getCenterPlay()
				.drawCards(draw);
		hand.addAll(Arrays.asList(cards));
	}

	public void endTurn()
	{
		moves = 4;
	}

	public ObservableList<Cash> getBankAccount() {
		return FXCollections.unmodifiableObservableList(bank);
	}

	public Card getBankCard(int index) {
		return bank.get(index);
	}

	public int getBankCount() {
		return bank.size();
	}

	public int getCashAmount() {
		return cashAmount.get();
	}

	public CardDefaults getDefaults() {
		return defs;
	}

	public ObservableList<Card> getFullHand() {
		return FXCollections.unmodifiableObservableList(hand);
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

	public ObservableList<CardAction> getPlayerHistory() {
		return FXCollections.unmodifiableObservableList(playerHistory);
	}

	public ObservableList<PropertyColumn> getPropColumns() {
		return FXCollections.unmodifiableObservableList(propertyColumn);
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

	public int getPropertySets() {
		return propertySets.get();
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

	/**
	 * Determines whether if player has any valued assets on the field.
	 * @return true if there are no assets, false otherwise.
	 */
	public final boolean isBankrupt()
	{
		if (getCashAmount() > 0)
			return false;
		for (PropertyColumn column : propertyColumn)
		{
			for (Card c : column)
			{
				if (c.getSellValue() > 0)
					return false;
				if (c instanceof Property && ((Property)c).getValue() > 0)
					return false;
			}
		}
		return true;
	}
	
	public final boolean isLastTurn() {
		return moves == 2;
	}

	public final boolean isTurnDone() {
		return moves >= 3;
	}

	public final boolean isTurnEnd() {
		return moves >= 4;
	}

	// player makes one move.
	@SuppressWarnings("FinalMethod")
	public final boolean playAction(CardAction move) {
		Card played = move.getPlayed();

		if (isTurnDone()) {
			return false;
		}

		// TODO: checkReference(current, played);
		if (move.getActionType().getAction().apply(played, this)) {
			pushTurn(move);
			hand.remove(played);
		}
		return true;
	}
	
	public final boolean playActionNoTurn(CardAction move) {
		Card played = move.getPlayed();

		if (isTurnDone()) {
			return false;
		}

		// TODO: checkReference(current, played);
		move.getActionType().getAction().apply(played, this);
		playerHistory.add(move);
		hand.remove(played);

		return true;
	}

	public ReadOnlyIntegerProperty propertySetsProperty() {
		return propertySets;
	}

	public void pushTurn(CardAction move) {
		moves++;
		playerHistory.add(move);
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
	 * Prompts the player to end the turn.
	 */
	public abstract void selectEndTurn();

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
	 * This method must acknowledge whether if the card has any valid supported actions
	 * by calling {@link Card#getSupportedTypes(Player)}.
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
	public abstract CardAction selectHand(String prompt, Predicate<Card> filter, Predicate<CardActionType> actionFilter);

	/**
	 * This prompts the player of a payment that must be made.
	 * <p>
	 *
	 * @param amount the debt amount to pay (if any) that is currently due.
	 * @param prevProposal the previous proposal this player has made, or null 
	 * 		no previous ones have been made, or if player rejected the payment
	 * @param response response of the other player if any, or null if other player has not made a response.
	 * @return any counter proposal of the player if the player objects to the payment.
	 */
	public abstract CounterProposal selectPayment(Payment amount, Payment prevProposal, ResponseType response);

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
	 * This method allows for a pause before player starts a turn... ie if player needs to be prompted to start turn and draw cards.
	 */
	public abstract void selectTurn();

	@Override
	public String toString()
	{
		return getName();
	}
}
