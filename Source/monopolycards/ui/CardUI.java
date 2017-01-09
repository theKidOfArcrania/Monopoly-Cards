package monopolycards.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Box;
import monopolycards.card.Card;

public class CardUI extends Box
{
	private ObjectProperty<Card> card = new SimpleObjectProperty<>(this, "card");
	
	public CardUI(int width, int height)
	{
		this(null, width, height);
	}
	
	public CardUI(Card c, int width, int height)
	{
		setWidth(width);
		setHeight(height);
		setDepth(2);
		translateXProperty().bind(widthProperty().divide(2));
		translateYProperty().bind(heightProperty().divide(2));
		
		setCard(c);
	}
	
	public Card getCard()
	{
		return card.get();
	}
	
	public void setCard(Card c)
	{
		card.set(c);
	}
	
	public ObjectProperty<Card> cardProperty()
	{
		return card;
	}
}
