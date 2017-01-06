package monopolycards.card;

import monopolycards.impl.CardActionType;
import monopolycards.impl.Player;
import monopolycards.impl.SupportedActions;

public interface Cash extends Valuable {

	static String moneyString(int amount, boolean shortString) {
		double figure;
		String unit;

		if (amount >= 1000000000) { // billions
			figure = amount / 1000000000.0;
			unit = shortString ? "B" : " billion dollars";
		} else if (amount >= 1000000) { // millions
			figure = amount / 1000000.0;
			unit = shortString ? "M" : " million dollars";
		} else if (amount >= 1000) { // thousands
			figure = amount / 1000.0;
			unit = shortString ? "K" : " thousand dollars";
		} else {
			figure = amount;
			unit = " dollars";
		}

		if (shortString)
			return String.format("$%.0f%s", figure, unit);
		else
			return String.format("%.0f%s", figure, unit);
	}

	@Override
	default boolean actionPlayed(Player self) {
		self.addBill(this);
		return true;
	}

	@Override
	default SupportedActions getSupportedTypes(Player self) {
		SupportedActions actions = new SupportedActions();
		actions.addAction(new CardActionType("Add to bank", "move.cash", getDefaults()));
		actions.addAction(new CardActionType("Discard", "move.discard", getDefaults()));
		filterSupportedTypes(self, actions);
		return actions;
	}

}