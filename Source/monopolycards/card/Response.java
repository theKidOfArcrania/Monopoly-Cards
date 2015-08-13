package monopolycards.card;

public interface Response extends Action {

	public enum ResponseType {
		JustSayNo;
	}

	ResponseType getResponseType();

}