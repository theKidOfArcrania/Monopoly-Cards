/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card.standard;

import java.util.ArrayList;

import monopolycards.card.PropertyColumn;
import monopolycards.impl.CardAction;
import monopolycards.impl.CardActionType.Likeness;
import monopolycards.impl.CenterPlay;
import monopolycards.impl.Player;

/**
 *
 * @author Henry
 */
public class DoubleTheRentCard extends ActionCard {
	/**
	 *
	 */
	private static final long serialVersionUID = -5163764459341688966L;

	@Override
	public boolean actionPlayed(Player self) {
		ArrayList<DoubleTheRentCard> extraDoubles = new ArrayList<>(10);
		CardAction played;
		CenterPlay centerPlay = self.getGame()
				.getCenterPlay();
		int multiplier = 2;

		do {
			played = self
					.selectHand("Select a rent card to rent others with.",
							card -> ((!self.isLastTurn() && card instanceof DoubleTheRentCard)
									|| (card instanceof RentCard && card.isEnabled(self, Likeness.Action))),
							cardAction -> cardAction.getInternalType()
									.equals("move.action"));

			if (played == null) {
				return false;
			}
			if (played.getPlayed() instanceof DoubleTheRentCard) {
				extraDoubles.add((DoubleTheRentCard) played.getPlayed());
				multiplier *= 2;
			}
		} while (played.getPlayed() instanceof DoubleTheRentCard);
		RentCard rentCard = (RentCard) played.getPlayed();

		int rent = self.columnStream()
				.parallel()
				.filter(rentCard::isValidRent)
				.mapToInt(PropertyColumn::getRent)
				.max()
				.orElse(0);
		if (rent == 0) {
			return false;
		}

		if (payRequest(self, rentCard.isGlobal(), rent * multiplier, "rent")) {
			extraDoubles.forEach((doubling) -> {
				self.pushTurn(new CardAction(doubling, getSupportedTypes().getActionType(Likeness.Action)));
				centerPlay.discard(doubling);
			});
			centerPlay.discard(rentCard);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getInternalType() {
		return "action.double";
	}

	@Override
	public boolean isEnabled(Player self, Likeness action) {
		if (action == Likeness.Action) {
			if (self.isLastTurn()) {
				return false;
			}

			return self.handStream()
					.parallel()
					.filter(card -> card instanceof RentCard)
					.anyMatch(rent -> rent.isEnabled(self, Likeness.Action));
		}
		return true;
	}
}
