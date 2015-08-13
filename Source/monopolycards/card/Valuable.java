/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card;

/**
 * Interface that specifies if a card has some value.
 * <p>
 *
 * @author HW
 */
public interface Valuable extends Card {

	/**
	 * Retrives the dollar value that this card is ACTUALLY worth.
	 * <p>
	 *
	 * @return the value of this card.
	 */
	public int getValue();
}
