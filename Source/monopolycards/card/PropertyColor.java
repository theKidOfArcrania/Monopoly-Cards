/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolycards.card;

/**
 *
 * @author HW
 */
public enum PropertyColor {

	Beige, Black, Blue, Brown, Gold, Green, LightBlue("Light Blue"), Megenta, Orange, Red, Yellow;
	private final String colorName;

	PropertyColor() {
		this.colorName = name();
	}

	PropertyColor(String colorName) {
		this.colorName = colorName;
	}

	public String getColorName() {
		return colorName;
	}

	@Override
	public String toString() {
		return colorName;
	}

}
