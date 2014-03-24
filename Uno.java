/**
 * TEXT ONLY UNO
 *
 * @author Alec Pickup
 * @version 0.9
 */

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.*;

public class Uno 
{
	final static int SKIP = 10; //so I don't have to remember the number for these things.
	final static int REVERSE = 11;
	final static int DRAW2 = 12;
	final static int WILD = 13;
	final static int WILDDRAW4 = 14;
	
	public static void main(String[] args) throws FileNotFoundException
	{
		boolean gameOver = false;
		
		CardPile draw = new CardPile(); //create card pile
		draw.shuffle(); //initial shuffle
		
		Scanner input = new Scanner(System.in); //input scanner
		System.out.println("Welcome to Text-Only UNO");
		System.out.println("How many players? "); //asks how many players
		int numPlayers = input.nextInt(); //input number of players
		Player[] players = new Player[numPlayers]; //creates a list of players
		for(int i = 0; i < numPlayers ; i++)
		{
		    players[i] = new Player(); //construct each new player in the array
		}
		
		for(int i = 0; i < 7; i++) //deal out a card 7 times...
		{
			for(int j = 0; j < numPlayers; j++) //...to each player
			{
				players[j].takeCard(draw.nextCard());
			}
		}
		
		int t = 0; 					//indicates the player of the current turn
		int i;						//used for loops and stuff
		int choice;					//used for input
		boolean clockwise = true;	//indicates direction of play
		int nextPlayerDraw = 0;		//this could be a 2 or 4, depending on what the next player has to draw after a draw 2 or 4 has been played.
		boolean nextPlayerSkip = false;
		
		do
		{
			draw.discard(draw.nextCard());  //lays down cards until the first card to play on is a regular number card.
		}
		while((draw.getTopCard().getColor().equalsIgnoreCase("black")) || (draw.getTopCard().getValue() > 9));
		
		while(!gameOver)
		{
			System.out.println("------------------------------------");
			System.out.println("Player " + (t + 1) + "'s turn.");
			System.out.println();
			System.out.println("The top card is: " + draw.getTopCard()); 
			System.out.println();
			System.out.println("Select a card to play:");
			
			for(i = 0; i < players[t].getNumCardsInHand(); i++) 
			{
				//outputs a list of cards in the players hand, with corresponding numbers to input to select the card.
				System.out.println(i + ") " + players[t].getThisCardFromHand(i) + " ");
			}
			
			System.out.println(i + ") draw a card"); //gives the option of drawing a card, rather than playing
			choice = input.nextInt();
			if(choice == i)
			{
				players[t].takeCard(draw.nextCard()); //if they select "draw a card"
			}
			if((players[t].getThisCardFromHand(choice).getColor().equalsIgnoreCase("black")))
			{
				//if the card they choose to play is a wild card:
				draw.discard(players[t].playCard(choice)); //automatically play it, since wilds can be played whenever
				
				while(draw.getTopCard().getColor().equalsIgnoreCase("black"))
				{
					//while they haven't selected a valid color:
					System.out.println();
					System.out.println("Select a color");
					System.out.println("1) red");
					System.out.println("2) blue");
					System.out.println("3) green");
					System.out.println("4) yellow");
					int wildColor = input.nextInt();
					switch(wildColor)
					{
					case 1:
						draw.wildSetColor("red"); //these call a function that sets the top card (the wild card) to whatever color they chose
						break;
					case 2:
						draw.wildSetColor("blue");
						break;
					case 3:
						draw.wildSetColor("green");
						break;
					case 4:
						draw.wildSetColor("yellow");
						break;
					default:
						draw.wildSetColor("black"); //invalid selection: leave as black so the loop will repeat.
					}
				}
				
				if(draw.getTopCard().getValue() == WILDDRAW4) //and if its a draw 4, set the next player's draw amount to 4 and skip them.
				{
					nextPlayerDraw = 4;
					nextPlayerSkip = true;
						
				}
			}
			else if((players[t].getThisCardFromHand(choice).getColor().equalsIgnoreCase(draw.getTopCard().getColor())) || (players[t].getThisCardFromHand(choice).getValue() == draw.getTopCard().getValue()))
			{
				//if the player plays a card that matches either the color or the value:
				System.out.println("You played a " + players[t].getThisCardFromHand(choice)); //output the card being played.
				draw.discard(players[t].playCard(choice)); //discard the card from the hand.
				
				if(draw.getTopCard().getValue() == SKIP) //pretty obvious what these do.
				{
					nextPlayerSkip = true;	
				}
				if(draw.getTopCard().getValue() == REVERSE) 
				{
					clockwise = !clockwise;
				}
				if(draw.getTopCard().getValue() == DRAW2) 
				{
					nextPlayerDraw = 2;
					nextPlayerSkip = true;
				}
				
			}
			
			if(players[t].getNumCardsInHand() == 0) //if the player just played his last card:
			{
				System.out.println("********************************");
				System.out.println("*        Player " + (t + 1) + " wins!        *");
				System.out.println("********************************");
				gameOver = true; //will exit the loop.
			}
			
			if(clockwise) //based on the direction of play, change the iterator to the next player
			{
				t++;
			}
			else
			{
				t--;
			}
			if(t == numPlayers) //if the iterator is out of bounds, loop around to the other side.
			{
				t = 0;
			}
			if(t == -1)
			{
				t = numPlayers - 1;
			}
			//next player's turn starts here.
			
			//draw 2 and draw 4 take effect here.
			for(i = 0; i < nextPlayerDraw; i++) //draws the "nextPlayerDraw" amount of cards
			{
				players[t].takeCard(draw.nextCard());
				System.out.println("Player " + (t + 1) + " drew a card");
			}
			System.out.println();
			
			nextPlayerDraw = 0; //after they draw, set back to zero
			
			if(nextPlayerSkip) //if nextPlayerSkip was true, change iterator to next player
			{
				if(clockwise)
				{
					System.out.println("Player " + (t + 1) + " was skipped"); //displays who was skipped.
					t = t + 1;
				}
				else
				{
					System.out.println("Player " + (t + 1) + " was skipped");
					t = t - 1;
				}
			}
			System.out.println();
			
			nextPlayerSkip = false; //reset skip
			
			if(t == numPlayers) //again, check to make sure the iterator isn't out of bounds.
			{
				t = 0;
			}
			if(t == -1)
			{
				t = numPlayers - 1;
			}
		}
	}
}
