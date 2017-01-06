package monopolycards.card.standard;

import monopolycards.card.Response;
import monopolycards.card.ResponseType;
import monopolycards.impl.CardActionType;
import monopolycards.impl.CardActionType.Likeness;
import monopolycards.impl.Player;
import monopolycards.impl.SupportedActions;

public class JustSayNoCard extends ActionCard implements Response {
	private static final long serialVersionUID = -1356787074424595137L;
	public static final ResponseType JUST_SAY_NO = (prevProposal, current) -> prevProposal;
	
	@Override
	public String getInternalType() {
		return "action.justSayNo";
	}

	@Override
	public ResponseType getResponseType() {
		return JUST_SAY_NO;
	}

	@Override
	public SupportedActions getSupportedTypes(Player self) {
		SupportedActions actions = super.getSupportedTypes(self);
		actions.removeAction(CardActionType.Likeness.Action);
		return actions;
	}

	@Override
	public boolean isEnabled(Player self, Likeness action) {
		return true;
	}

}
