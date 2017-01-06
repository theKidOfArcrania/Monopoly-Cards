package monopolycards.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.BiFunction;

import common.util.Lambdas;
import monopolycards.ResourceDefaults;
import monopolycards.card.Card;
import monopolycards.card.Cash;
import monopolycards.card.standard.ActionCard;

public class CardActionType {

	public enum Likeness {
		Action(ACTION_ACTION), FieldPlace(Card::actionPlayed), Cashin(ACTION_CASHIN), Discard(ACTION_DISCARD);

		private BiFunction<? super Card, ? super Player, ? extends Boolean> action;

		Likeness(BiFunction<? super Card, ? super Player, ? extends Boolean> action) {
			this.action = action;
		}

		public BiFunction<? super Card, ? super Player, ? extends Boolean> getAction() {
			return action;
		}
	}

	// TO DO: make more standard.
	public static final BiFunction<? super Card, ? super Player, ? extends Boolean> ACTION_DISCARD = (card, player) -> {
		player.getGame().getCenterPlay().discard(card);
		//player.playAction(null)
		return true;
	};
	public static final BiFunction<? super Card, ? super Player, ? extends Boolean> ACTION_CASHIN = Lambdas.convertType(ActionCard.class)
			.andThen(ActionCard::convertToCash)
			.filterReturn(Cash::actionPlayed);
	public static final BiFunction<? super Card, ? super Player, ? extends Boolean> ACTION_ACTION = (card, player) -> {
		boolean action = card.actionPlayed(player);
		if (action) {
			ACTION_DISCARD.apply(card, player);
			return true;
		} else {
			return false;
		}
	};

	private final String name;
	private final BiFunction<? super Card, ? super Player, ? extends Boolean> action;
	private final String internalType;
	private final ResourceDefaults defs;
	private final Likeness likeness;

	public CardActionType(String name, String internalType, ResourceDefaults defs) {
		this(name, internalType, defs, null);
	}

	public CardActionType(String name, String internalType, ResourceDefaults defs, BiFunction<? super Card, ? super Player, ? extends Boolean> actionOverride) {
		this.name = name;
		this.internalType = internalType;
		this.defs = defs;
		this.likeness = defs.getEnumProperty(internalType + ".like", Likeness.class, null);

		if (likeness == null) {
			System.out.println(internalType);
			throw new IllegalArgumentException("Invalid action type for internal type");
		}
		if (actionOverride == null) {
			this.action = this.likeness.getAction();
		} else {
			this.action = actionOverride;
		}
	}

	public BiFunction<? super Card, ? super Player, ? extends Boolean> getAction() {
		return action;
	}

	public BufferedImage getIcon() throws IOException {
		return defs.getImageProperty(internalType + ".icon");
	}

	public String getInternalType() {
		return internalType;
	}

	public Likeness getLikeness() {
		return likeness;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}