/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card.standard;

import common.util.Lambdas;
import monopolycards.card.Action;
import monopolycards.card.Cash;
import monopolycards.impl.CardActionType;
import monopolycards.impl.Player;

/**
 * This card contains all the default impl. for any action card.
 * <p>
 *
 * @author HW
 */
public abstract class ActionCard extends StandardCard implements Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8876027487042225439L;

	public static final CardActionType TYPE_CASH_IN = new CardActionType("Cash-in", Lambdas.convertType(ActionCard.class)
			.andThen(ActionCard::convertToCash)
			.filterReturn(Cash::actionPlayed), "move.cashin");

	final CardActionType typeAction = new CardActionType(getActionName(), CardActionType.TYPE_ACTION, "move.action");

	ActionCard() {
	}

	@SuppressWarnings("unused")
	@Override
	public boolean actionPlayed(Player self) {
		// default implementation is nothing.
		return true;
	}

	@Override
	public CashCard convertToCash() {
		return new CashCard(this);
	}

	@Override
	public String getActionName() {
		return this.getInternalProperty("actionName", getCardName());
	}

	@Override
	public String getCardName() {
		return this.getInternalProperty("name");
	}

	@Override
	public String getDescription() {
		return getInternalProperty("description", "");
	}

	@Override
	public int getSellValue() {
		return getInternalIntProperty("sellValue", 0);
	}

	@Override
	public CardActionType[] getSupportedTypes() {
		return new CardActionType[] { TYPE_CASH_IN, typeAction, CardActionType.TYPE_DISCARD };
	}

}
