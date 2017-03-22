# Fold'em
A Java framework for Texas Hold'em designed with an emphasis on simplicity and ease of use.

## Features
- Hand evaluations
- Equity calculation for both hands and ranges for any number of players
- High level types for working with ranges
- String based notation of cards and hands
- Pretty text format (like in [Deuces](https://github.com/worldveil/deuces))

## Getting Fold'Em
This project is available in the [Maven][mvn] Central Repository.

### Gradle
TODO

### Maven
TODO

## Documentation
A Javadoc for Fold'em is available [here](TODO). Additionally some examples have been provided in this repository [here](/src/main/java/codes/derive/foldem/examples). An effort has been made to keep this library as simple as possible to use, some more basic usage examples have been provided in the section below.

### Basic Usage Guide
Most of this project's functionality is provided in the [codes.derive.foldem](src/main/java/codes/derive/foldem) package. In this package there is a helper class called [Foldem](src/main/java/codes/derive/foldem/Foldem.java) containing static functions for creating and working with the data types this project exposes. (TODO move links to point to Javadoc once its up)

It is recommended that you import this statically to make code less bulky:
```java
import static codes.derive.foldem.Foldem
```

From here you can easily initialize and manipulate the main data types comprising the framework:
```java
// Create a card
Card queenOfHearts = card(Card.QUEEN, Suit.HEARTS); 

// Create another card, this time using shorthand
Card aceOfSpades = card("As");

// Create a hand using the cards
Hand myHand = hand(queenOfHearts, aceOfSpaces);

// Create another hand, this time using shorthand
Hand myOtherHand = hand("KhKd");

// Calculate these hands' equity against eachother
Map<Hand, Equity> equities = equity(myHand, myOtherHand);

// Print their equity against eachother
System.out.println(myHand + " takes pot " + equities.get(myHand).wins() + " of the time");
System.out.println(myOtherHand + " takes pot " + equities.get(myOtherHand).wins() + " of the time");

// Create a board
Board board = Boards.flop(card("Jc"), card("7d"), card("2h")); // TODO shorthand

// Calculate equities again, this time on the board
equities = equity(board, myHand, myOtherHand);

// Print their equity against eachother, this time using pretty printing TODO
System.out.println(myHand + " takes pot " + equities.get(myHand).wins() + " of the time");
System.out.println(myOtherHand + " takes pot " + equities.get(myOtherHand).wins() + " of the time");

// Create a deck and shuffle it
Deck deck = deck().shuffle();

// Deal a hand from the deck.
Hand hand = hand(deck);

// Pretty print the hand. TODO
System.out.println(hand);


```
