package monopolycards.ui;

import javafx.collections.SetChangeListener;
import javafx.scene.Group;
import monopolycards.card.Card;
import monopolycards.card.PropertyColumn;

public class BoardColumnUI extends UIComponent
{
	private final PropertyColumn col;
	private final Group root;
	
	public BoardColumnUI(Group root, PropertyColumn col)
	{
		this.col = col;
		this.root = root;
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

	@Override
	public void transferCard(CardUI c)
	{
		
	}
	
	
}
