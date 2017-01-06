package monopolycards.impl;

import monopolycards.card.Response;

public class CounterProposal
{

	private final Payment original;
	private final Payment proposal;
	private final Response card;
	
	public CounterProposal(Response card, Payment original, Payment proposal)
	{
		this.original = original;
		this.proposal = proposal;
		this.card = card;
	}

	public Payment getOriginal()
	{
		return original;
	}

	public Payment getProposal()
	{
		return proposal;
	}

	public Response getCard()
	{
		return card;
	}
	
	

}
