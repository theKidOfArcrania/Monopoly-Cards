package monopolycards.ui.test;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import monopolycards.card.Card;
import monopolycards.card.CardDefaults;
import monopolycards.card.Deck;
import monopolycards.card.Property;
import monopolycards.card.PropertyColumn;
import monopolycards.card.Response;
import monopolycards.card.standard.StandardCardDefaults;
import monopolycards.impl.Board;
import monopolycards.impl.CardAction;
import monopolycards.impl.CardActionType;
import monopolycards.impl.CenterPlay;
import monopolycards.impl.Payment;
import monopolycards.impl.Player;

public class GameTest
{
	public static class PlayerImpl extends Player {
		private Scanner in;
		public PlayerImpl(CardDefaults defs, String name)
		{
			super(defs, name);
			in = new Scanner(System.in);
		}

		@Override
		public void alert(String prompt)
		{
			System.out.println(getName() + "> " + prompt);
		}

		@Override
		public CardAction selectHand(String prompt, Predicate<Card> filter,
				Predicate<CardActionType> actionFilter)
		{
			System.out.println(getName() + "> " + prompt);
			CardAction action = null;
			
			while (action == null)
			{
				System.out.println("Cards: ");
				List<Card> cards = filter != null ? getFullHand().filtered(filter) : getFullHand();
				for (int i = 0; i < cards.size(); i++)
					System.out.println("  " + i + " -- " + cards.get(i));
				System.out.println();
				
				int cardInd = readNumber("Choose a card: ", 0, cards.size() - 1);
				Card selected = cards.get(cardInd);
				
				CardActionType[] actions = selected.getSupportedTypes().parallelStream()
						.filter(actionFilter).toArray(CardActionType[]::new);
				System.out.println();
				System.out.println("Available actions: ");
				System.out.println("  0 -- Go back");
				for (int i = 0; i < actions.length; i++)
					System.out.println("  " + (i + 1) + " -- " + actions[i]);
				
				int actionInd = readNumber("Choose an action for " + selected + ": ", 0, actions.length);
				if (actionInd != 0)
					action = new CardAction(selected, actions[actionInd - 1]);
			}
			return action;
		}
		
		@Override
		public void selectPayment(Payment amount)
		{
			System.out.println("TODO: Payment");
			System.exit(1);
		}

		@Override
		public Player selectPlayer(String prompt, Predicate<Player> filter)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Property selectProperty(String prompt,
				Predicate<Property> filter, Player context)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PropertyColumn selectPropertyColumn(String prompt,
				Predicate<PropertyColumn> filter, Player context)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean selectRequest(String prompt)
		{
			return readResponse(prompt);
		}

		@Override
		public Response selectResponse(String prompt)
		{
			System.out.println(prompt);
			System.out.println("TODO: select Just-say-no?");
			return null;
		}

		@Override
		public void selectTurn()
		{
			System.out.println(getName() + "'s turn!");
		}
		
		private boolean readResponse(String prompt)
		{
			System.out.println(prompt);
			while (true)
			{
				System.out.println("(Y or N) ");
				String resp = in.nextLine().toUpperCase();
				if (resp.charAt(0) == 'Y')
					return true;
				else if (resp.charAt(0) == 'N')
					return false;
			}
		}
		
		private int readNumber(String prompt, int min, int max)
		{
			while (true)
			{
				System.out.print(prompt);
				if (!in.hasNextInt())
					System.out.println("Invalid number. ");
				else
				{
					int num = in.nextInt();
					if (num < min || num > max)
						System.out.println("Number must be between " + min + " and " + max);
					else
						return num;
				}
				in.nextLine(); //Flush buffer.
			}
		}
	}
	public static void main(String[] args) throws Exception
	{
		CardDefaults defs = StandardCardDefaults.getCardDefaults();
		Deck cards = defs.generateDeck();
		cards.shuffle();
		cards.shuffle();
		
		CenterPlay play = new CenterPlay(cards);
		Board b = new Board(defs, play);
		
		for (int i = 1; i <= 4; i++)
			b.addPlayer(new PlayerImpl(defs, "Player " + i));
		
		System.out.println("Starting game....");
		System.out.println("-------------------------------");
		b.start();
		System.out.println("We have a winner!");
		System.out.print("Congratulations ");
		Player[] winners = b.checkWin();
		for (int i = 0; i < winners.length; i++)
		{
			if (i == 0)
				System.out.print(winners[i]);
			else
				System.out.printf(", %s", winners[i]);
		}
	}
}
