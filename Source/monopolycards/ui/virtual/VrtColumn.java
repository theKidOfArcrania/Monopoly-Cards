package monopolycards.ui.virtual;

import javafx.collections.SetChangeListener;
import javafx.scene.Group;
import monopolycards.card.Card;
import monopolycards.card.PropertyColumn;

public class VrtColumn extends VrtGroup
{
	private final PropertyColumn col;
	private final Group root;
	
	public VrtColumn(Group root, PropertyColumn col)
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
	public void transferCard(VrtCard c)
	{
		
	}
	
	
}
