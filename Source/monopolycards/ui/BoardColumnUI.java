package monopolycards.ui;

import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import monopolycards.card.Card;
import monopolycards.card.PropertyColumn;

public class BoardColumnUI extends Group
{
	private PropertyColumn col;
	
	public BoardColumnUI(PropertyColumn col)
	{
		this.col = col;
		col.addListener((ListChangeListener<Card>)(c ->
		{
			while (c.next())
			{
				c.
			}
		}));
	}
	
	
}
