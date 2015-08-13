package monopolycards.card.standard;

import monopolycards.card.Response;
import monopolycards.impl.CardActionType;
import monopolycards.impl.Player;

public class JustSayNoCard extends ActionCard implements Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1356787074424595137L;

	@Override
	public String getInternalType() {
		return "action.justSayNo";
	}

	@Override
	public ResponseType getResponseType() {
		return ResponseType.JustSayNo;
	}

	@Override
	public CardActionType[] getSupportedTypes() {
		return new CardActionType[] { ActionCard.TYPE_CASH_IN, CardActionType.TYPE_DISCARD };
	}

	@Override
	public boolean isEnabled(Player self, CardActionType action) {
		return true;
	}

}
