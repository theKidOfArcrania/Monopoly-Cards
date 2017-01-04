package monopolycards.card.standard;

import static monopolycards.card.standard.StandardCardDefaults.getCardDefaults;

import java.awt.Image;
import java.io.IOException;

import monopolycards.card.AbstractCard;
import monopolycards.card.CardDefaults;

/**
 * This is the top abstract class of cards for all monopoly cards. (Classic and Millionare)
 * <p>
 *
 * @author HW
 */
public abstract class StandardCard extends AbstractCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5207918406669141417L;

	protected StandardCard() {
	}

	@Override
	public CardDefaults getDefaults() {
		return getCardDefaults();
	}

	@Override
	public String getDescription() {
		return getInternalProperty("description");
	}

	@Override
	public Image getImage() throws IOException {
		return getCardDefaults().getImageProperty(getInternalType() + ".image");
	}

	@Override
	protected int getInternalIntProperty(String subKey) {
		return getCardDefaults().getIntProperty(getInternalType() + "." + subKey, 0);
	}

	@Override
	protected int getInternalIntProperty(String subKey, int defValue) {
		return getCardDefaults().getIntProperty(getInternalType() + "." + subKey, defValue);
	}

	@Override
	protected String getInternalProperty(String subKey) {
		return getCardDefaults().getProperty(getInternalType() + "." + subKey);
	}

	@Override
	protected String getInternalProperty(String subKey, String defValue) {
		return getCardDefaults().getProperty(getInternalType() + "." + subKey, defValue);
	}

}
