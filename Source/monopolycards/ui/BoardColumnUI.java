package monopolycards.ui;

import javafx.collections.SetChangeListener;
import javafx.scene.Group;
import monopolycards.card.Card;
import monopolycards.card.PropertyColumn;

public class BoardColumnUI extends Group
{
	private PropertyColumn col;
	
	public BoardColumnUI(PropertyColumn col)
	{
		this.col = col;
		col.addListener((SetChangeListener<Card>)(c ->
		{
			if (c.wasAdded())
			{
				//TODO: Show card being added.
			}
			else if (c.wasRemoved())
			{
				//TODO: Show card being removed.
			}
		}));
	}
	
	
}
