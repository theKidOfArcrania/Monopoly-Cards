package monopolycards.ui.virtual;

import java.util.Objects;

import javafx.collections.ObservableList;

public abstract class VrtGroup extends VrtNode
{
	private final ObservableList<VrtNode> children = new VetoableObservableList<>(this::checkChildren);
	
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
	
	/**
	 * Transfers a card into this UIComponent in its position and orientation
	 * @param c the card to move.
	 */
	public abstract void transferCard(VrtCard c);
	
	public final ObservableList<VrtNode> getChildren()
	{
		return children;
	}
	
	
}
