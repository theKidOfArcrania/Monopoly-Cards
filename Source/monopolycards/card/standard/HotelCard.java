/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card.standard;

public class HotelCard extends HouseCard {
	/**
	 * 
	 */
	private static final long serialVersionUID = -239602064457327661L;

	@Override
	public String getInternalType() {
		return "action.hotel";
	}
	
	//TODO: disallow building hotels if there are no full sets.
}
