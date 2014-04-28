import java.io.*;
import java.util.*;

public class CardPile
{
	private Card drawPile[] = new Card[108]; //standard uno deck has 108 cards
	private int numberOfCards; //keeps track of how many cards are still in the draw pile
	private Card discardPile[] = new Card[108]; //discardPile can hold up to 108 cards.
	private int numberDiscarded; //keeps track of how many have been discarded
	
	public CardPile() throws FileNotFoundException
	{
		// Generate a deck of cards
		private i = 0;  // Current card number
		private String[] colors = {"yellow", "green", "blue", "red"};
		for (String color: colors) {
			// Only 1 '0' valued card per color
			drawPile[i++] = new Card(0, color);
			// All other values from '1' to '12' get two cards
			for (int v = 1; v <= 12; v++) {
				drawPile[i++] = new Card(v, color);
				drawPile[i++] = new Card(v, color);
			}
		}
		
		// Generate 4 "wild" (13) and 4 "wild draw 4" (14) cards
		for (int v = 1; v <= 4; v++) {
			drawPile[i++] = new Card(13, "black");
			drawPile[i++] = new Card(14, "black");
		}
		
		numberOfCards = i; //set the number of cards to the number generated
	}
	
	public Card nextCard()
	{
		Card nextCard = drawPile[0]; //holds the next card to be returned at the end of function.
		
		for(int i = 0; i < numberOfCards - 2; i++)
		{
			//moves all the cards down in the array to fill in the empty "first slot"
			drawPile[i] = drawPile[i+1];
		}
		drawPile[numberOfCards - 1] = new Card(-1, "null");
		
		numberOfCards--; //correct the number of cards in the draw pile
		
		if(numberOfCards == 0)
		{
			//if the deck is now empty:
			for(int i = 0; i < numberDiscarded - 1; i++)
			{
				//place all of the discard pile into the draw pile except for the "top" card to be played on.
				drawPile[i] = discardPile[i];
				numberOfCards++; //update the number of cards as we go along (its not going to be all 108)
				
			}
			numberDiscarded = 0; //update discarded number
			shuffle(); //shuffle the draw pile
		}
		return nextCard;
	}
	
	public void shuffle()
	{    
		//this shuffle algorithm is actually lazy, and there's a more accurate way to do it, but whatever
		for (int i = numberOfCards - 1; i > 0; i--)
		{
			//for each card in the deck, swap it with a random other card.
			int j = (int)(new Random().nextInt(numberOfCards));
			Card t = drawPile[j];
			drawPile[j] = drawPile[i];
			drawPile[i] = t;
		}
	}
	
	public void discard(Card discard) //adds the Card argument to the top of the discard pile
	{
		discardPile[numberDiscarded] = discard;
		numberDiscarded++;
	}
	
	public Card getTopCard() //gets the top card of the discard pile (the one that you have to play a card on)
	{
		return discardPile[numberDiscarded-1];
	}
	
	public void wildSetColor(String color) //allows you to change the color of the top card (only gets called when a wild is played)
	{
		discardPile[numberDiscarded - 1].color = color;
	}
	
	
}
