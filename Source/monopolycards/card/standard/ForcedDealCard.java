/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card.standard;

import monopolycards.card.PropertyColumn;
import monopolycards.impl.CardActionType;
import monopolycards.impl.Payment;
import monopolycards.impl.Player;

/**
 *
 * @author Henry
 */
public class ForcedDealCard extends ActionCard {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7696673622785225864L;

	@Override
	public boolean actionPlayed(Player self) {
		Player target = self.selectPlayer("Please select another player to take a property set from.", Player::hasIncompleteSet);

		if (target == null) {
			return false;
		}

		PropertyCard take = (PropertyCard) self.selectProperty("Please select a property to take.", (card) -> {
			// Has to not be part of the full set.
			PropertyColumn column = target.getPropertyColumn(card);
			column.sort();
			return !column.isFullSet() || column.indexOf(card) >= column.getFullSet();
		} , target);

		if (take == null) {
			return false;
		}

		PropertyCard give = (PropertyCard) self.selectProperty("Please select a property to give", (card) -> true);

		Payment forceDeal = new Payment(self, target);
		forceDeal.requestProperty(take);
		forceDeal.giveProperty(give);
		forceDeal.finishRequest();
		return true;
	}

	@Override
	public String getInternalType() {
		return "action.forced";
	}

	@Override
	public boolean isEnabled(Player self, CardActionType action) {
		if (action.getInternalType()
				.equals("move.action")) {
			self.getGame()
					.playerStream()
					.parallel()
					.anyMatch(Player::hasIncompleteSet);
			return !self.columnStream()
					.allMatch(PropertyColumn::isEmpty);
		}
		return true;
	}
}
