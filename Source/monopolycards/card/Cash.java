package monopolycards.card;

import monopolycards.impl.CardActionType;
import monopolycards.impl.Player;

public interface Cash extends Valuable {
	public static final CardActionType TYPE_BANK = new CardActionType("Add to bank", Card::actionPlayed, "move.cash");

	static String moneyString(int amount, boolean shortString) {
		double figure;
		String unit;

		if (amount > 1000000000) { // billions
			figure = amount / 1000000000.0;
			unit = shortString ? "B" : " billion dollars";
		} else if (amount > 1000000) { // millions
			figure = amount / 1000000.0;
			unit = shortString ? "M" : " million dollars";
		} else if (amount > 1000) { // thousands
			figure = amount / 1000000.0;
			unit = shortString ? "K" : " thousand dollars";
		} else {
			figure = amount;
			unit = "";
		}

		return String.format("%f1%s", figure, unit);
	}

	@Override
	default boolean actionPlayed(Player self) {
		self.addBill(this);
		return true;
	}

	@Override
	default CardActionType[] getSupportedTypes() {
		return new CardActionType[] { TYPE_BANK, CardActionType.TYPE_DISCARD };
	}

	// This is a marker interface that indicates a cash interface.

}