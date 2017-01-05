package monopolycards.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import monopolycards.impl.CardActionType.Likeness;

public class SupportedActions implements Iterable<CardActionType> {
	private final HashMap<Likeness, CardActionType> supported = new HashMap<>();

	public void addAction(CardActionType type) {
		supported.put(type.getLikeness(), type);
	}

	public boolean containsLikeness(Likeness likeness) {
		return supported.containsKey(likeness);
	}

	public CardActionType getActionType(Likeness likeness) {
		return supported.get(likeness);
	}

	public boolean isEmpty()
	{
		return supported.isEmpty();
	}
	
	@Override
	public Iterator<CardActionType> iterator() {
		return supported.values()
				.iterator();
	}

	public Stream<CardActionType> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
	
	public Stream<CardActionType> parallelStream() {
		return StreamSupport.stream(spliterator(), true);
	}
	
	public boolean removeAction(CardActionType type) {
		return supported.remove(type.getLikeness(), type);
	}

	public CardActionType removeAction(Likeness likeness) {
		return supported.remove(likeness);
	}

	public CardActionType replaceAction(CardActionType type) {
		if (!supported.containsKey(type.getLikeness())) {
			throw new IllegalArgumentException("Likeness does not already exist");
		}

		return supported.put(type.getLikeness(), type);
	}
}
