
public class Player
{
	
	
	private Card hand[] = new Card[50]; //hands can hold up to 50 cards
	private int numberOfCards; //keeps track of how many cards the player has in his hand
	//because the rest of the hand will be set to null -1 instead of blue 9 or something like that.
	
	public Player()
	{
		numberOfCards = 0; //start the player with 0 cards.
	}
	
	public Card getThisCardFromHand(int c)
	{
		return hand[c]; //return a specific card from hand (THIS DOES NOT PLAY THE CARD)
	}
	
	public void takeCard(Card c) //receives a card and puts it into the hand after all the cards.
	{
		hand[numberOfCards] = c;
		numberOfCards++;
	}
	
	public int getNumCardsInHand() //returns the number of cards in the players hand.
	{
		return numberOfCards;
	}
	
	public Card playCard(int c) //returns a selected card from the players hand, and also deletes the card from the hand.
	{
		Card cardPlayed = hand[c];
		for(int i = c; i < numberOfCards; i++)
		{
			hand[i] = hand[i+1]; //moves all the cards down to fill the void of the played card.
		}
		hand[numberOfCards] = new Card(-1, "null");
		
		numberOfCards--; //correct the number of cards the player has left.
		
		return cardPlayed; 
	}
}
