package monopolycards.ui.virtual;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.collections.ModifiableObservableListBase;

public class VetoableObservableList<E> extends ModifiableObservableListBase<E>
{
    private final List<E> source = new ArrayList<>();
    private final Function<E, String> vetoer;
    
    public VetoableObservableList(Function<E, String> vetoer) {
        this.vetoer = vetoer;
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
    	String err = vetoer.apply(element);
    	if (err != null)
    		throw new IllegalArgumentException(err);
        source.add(index, element);
    }

    @Override
    protected E doSet(int index, E element) {
    	String err = vetoer.apply(element);
    	if (err != null)
    		throw new IllegalArgumentException(err);
        return source.set(index, element);
    }

    @Override
    protected E doRemove(int index) {
        return source.remove(index);
    }

}
