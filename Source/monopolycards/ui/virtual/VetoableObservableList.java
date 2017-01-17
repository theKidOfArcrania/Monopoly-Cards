package monopolycards.ui.virtual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Consumer;

import javafx.collections.ModifiableObservableListBase;

public class VetoableObservableList<E> extends ModifiableObservableListBase<E> implements RandomAccess
{
    private final List<E> source = new ArrayList<>();
    private final Consumer<E> vetoer;
    
    public VetoableObservableList(Consumer<E> vetoer) {
        this.vetoer = vetoer;
    }

	@Override
	public boolean setAll(Collection<? extends E> col)
	{
		for (E ele : col)
			checkElement(ele);
		return super.setAll(col);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		for (E ele : c)
			checkElement(ele);
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		for (E ele : c)
			checkElement(ele);
		return super.addAll(index, c);
	}

	@Override
    public E get(int index) {
        return source.get(index);
    }

    @Override
    public int size() {
        return source.size();
    }

    @Override
    protected void doAdd(int index, E element) {
    	checkElement(element);
        source.add(index, element);
    }

    @Override
    protected E doSet(int index, E element) {
    	checkElement(element);
        return source.set(index, element);
    }

    @Override
    protected E doRemove(int index) {
        return source.remove(index);
    }

    private void checkElement(E element)
    {
    	vetoer.accept(element);
    }
}
