package monopolycards.ui.virtual;

import java.util.Objects;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public abstract class VrtGroup extends VrtNode
{
	public static final Duration FAST_TRANS = Duration.seconds(1);
	public static final Duration MEDIUM_TRANS = Duration.seconds(2);
	
	private final ObservableList<VrtNode> children;
	
	private final ReadOnlyBooleanWrapper animating;
	private volatile int running = 0;
	
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
		
		animating = new ReadOnlyBooleanWrapper(this, "animating");
	}
	
	public final ReadOnlyBooleanProperty animatingProperty()
	{
		return this.animating.getReadOnlyProperty();
	}

	public final boolean isAnimating()
	{
		return this.animatingProperty().get();
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
	
	protected void runningAnimation(Animation a)
	{
		running++;
		a.statusProperty().addListener(new InvalidationListener()
		{
			@Override
			public void invalidated(Observable observable)
			{
				if (a.getStatus() == Status.STOPPED)
				{
					a.statusProperty().removeListener(this);
					running--;
					animating.set(false);
				}
			}
		});
		animating.set(true);
	}
	
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
