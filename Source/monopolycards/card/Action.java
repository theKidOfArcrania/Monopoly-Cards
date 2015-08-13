package monopolycards.card;

public interface Action extends Card {

	Cash convertToCash();

	String getActionName();

}