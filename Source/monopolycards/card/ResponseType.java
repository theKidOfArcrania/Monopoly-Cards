package monopolycards.card;

import monopolycards.impl.Payment;

public interface ResponseType {
	/**
	 * Creates a new proposal played in response to this current payment against the player.
	 * @param prevProposal the previous proposal this player has made, or null 
	 * 		no previous ones have been made, or if player rejected payment
	 * @param current the current payment forced against player
	 * @return a new proposed payment, or null if player wishes to refuse payment.
	 */
	Payment propose(Payment prevProposal, Payment current);
}