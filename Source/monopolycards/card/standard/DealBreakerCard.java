/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card.standard;

import monopolycards.card.PropertyColumn;
import monopolycards.impl.CardActionType.Likeness;
import monopolycards.impl.Payment;
import monopolycards.impl.Player;

/**
 *
 * @author Henry
 */
public class DealBreakerCard extends ActionCard {
	/**
	 *
	 */
	private static final long serialVersionUID = 4190593954097839138L;

	@Override
	public boolean actionPlayed(Player self) {
		Player target = self.selectPlayer("Please select another player to take a property set from.", Player::hasIncompleteSet);

		if (target == null) {
			return false;
		}

		PropertyColumn takeSet = self.selectPropertyColumn("Please select a property set to take.", PropertyColumn::isFullSet, target);

		if (takeSet == null) {
			return false;
		}

		Payment dealBreaker = new Payment(self, target);
		dealBreaker.requestPropertySet(takeSet.getPropertyColor());
		dealBreaker.finishRequest();
		return true;
	}

	@Override
	public String getInternalType() {
		return "action.breaker";
	}

	@Override
	public boolean isEnabled(Player self, Likeness action) {
		if (action == Likeness.Action) {
			return self.getGame()
					.playerStream()
					.parallel()
					.anyMatch(Player::hasCompleteSet);
		}
		return true;
	}
}
