package monopolycards.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.BiFunction;

import monopolycards.ResourceDefaults;
import monopolycards.card.Card;

public class CardActionType {

	public static final CardActionType TYPE_DISCARD = new CardActionType("TYPE_DISCARD", (card, player) -> {
		player.getGame()
				.getCenterPlay()
				.discard(card);
		return true;
	} , "move.discard");

	public static final CardActionType TYPE_ACTION = new CardActionType("Play action", (card, player) -> {
		boolean action = card.actionPlayed(player);
		if (action) {
			TYPE_DISCARD.getAction()
					.apply(card, player);
			return true;
		} else {
			return false;
		}
	} , "move.action");

	private final String name;
	private final BiFunction<? super Card, ? super Player, ? extends Boolean> action;
	private final String internalType;
	private final ResourceDefaults defs;

	public CardActionType(String name, BiFunction<? super Card, ? super Player, ? extends Boolean> action, String internalType) {

		this(name, action, internalType, ResourceDefaults.getDefaults());
	}

	public CardActionType(String name, BiFunction<? super Card, ? super Player, ? extends Boolean> action, String internalType, ResourceDefaults defs) {
		this.name = name;
		this.action = action;
		this.internalType = internalType;
		this.defs = defs;
	}

	public CardActionType(String name, CardActionType model, String internalType) {
		this(name, model.getAction(), internalType, ResourceDefaults.getDefaults());
	}

	public CardActionType(String name, CardActionType model, String internalType, ResourceDefaults def) {
		this(name, model.getAction(), internalType, def);
	}

	public BiFunction<? super Card, ? super Player, ? extends Boolean> getAction() {
		return action;
	}

	public BufferedImage getIcon() throws IOException {
		return defs.getImageProperty(getInternalType() + ".icon");
	}

	public String getInternalType() {
		return internalType;
	}

	public String getName() {
		return name;
	}
}