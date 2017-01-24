package monopolycards.ui.virtual;

import java.util.Objects;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public abstract class VrtGroup extends VrtNode
{
	private final ObservableList<VrtNode> children;
	
	protected VrtGroup()
	{
		children = new VetoableObservableList<>(this::checkChildren);
		children.addListener((ListChangeListener<VrtNode>)chg -> {
			while (chg.next())
			{
				if (chg.wasRemoved())
				{
					for (VrtNode node : chg.getRemoved())
					{
						if (node instanceof VrtCard)
							removeCard((VrtCard)node);
					}
				}
				if (chg.wasAdded())
				{
					for (VrtNode node : chg.getAddedSubList())
					{
						if (node instanceof VrtCard)
							addCard((VrtCard)node);
					}
				}
			}
		});
	}
	
	public final ObservableList<VrtNode> getChildren()
	{
		return children;
	}
	
	/**
	 * Removes any card references to this group
	 * @param c the card to remove.
	 */
	protected abstract void removeCard(VrtCard c);
	
	/**
	 * Adds a card into this UIComponent in its position and orientation
	 * @param c the card to add.
	 */
	protected abstract void addCard(VrtCard c);
	
	private void checkChildren(VrtNode node)
	{
		if (node == null)
			throw new NullPointerException();
		if (node.getParent() != null)
			throw new VetoedException("Node already has a parent.");
		if (Objects.equals(node,this))
			throw new VetoedException("Cannot add node to itself.");
		
		VrtGroup tmp = this;
		while (tmp != null)
		{
			if (Objects.equals(node,tmp))
				throw new VetoedException("Parent node cannot be added to a child node.");
			tmp = tmp.getParent();
		}
	}
	
}
