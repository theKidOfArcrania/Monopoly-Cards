package monopolycards.card;

import monopolycards.impl.CardActionType;
import monopolycards.impl.Player;
import monopolycards.impl.SupportedActions;

public interface Action extends Card {

	Cash convertToCash();

	String getActionInternalType();

	String getActionName();

	@Override
	default SupportedActions getSupportedTypes(Player self) {
		SupportedActions actions = new SupportedActions();
		String actionType = getActionInternalType();
		if (!actionType.isEmpty())
			actions.addAction(new CardActionType(getActionName(), actionType, getDefaults()));
		actions.addAction(new CardActionType("Discard", "move.discard", getDefaults()));
		actions.addAction(new CardActionType("Cash-in", "move.cashin", getDefaults()));
		filterSupportedTypes(self, actions);
		return actions;
	}
}