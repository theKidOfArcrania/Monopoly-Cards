package monopolycards.card;

import monopolycards.impl.CardActionType;
import monopolycards.impl.SupportedActions;

public interface Action extends Card {

	Cash convertToCash();

	String getActionInternalType();

	String getActionName();

	@Override
	default SupportedActions getSupportedTypes() {
		SupportedActions actions = new SupportedActions();
		actions.addAction(new CardActionType(getActionName(), getActionInternalType()));
		actions.addAction(new CardActionType("Discard", "move.discard"));
		return actions;
	}
}