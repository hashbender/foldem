# Fold'em
[![Build Status](https://travis-ci.com/ableiten/foldem.svg?token=BhqqkCqh5epy6HEo9gsq&branch=master)](https://travis-ci.com/ableiten/foldem) TODO:mvncentral

A Java framework for Texas Hold 'em designed with an emphasis on simplicity and ease of use.

![generated](https://cloud.githubusercontent.com/assets/22860251/24421063/72424652-1451-11e7-9f28-fe91c256f4f3.png)
## Features
- Hand evaluator
- Equity calculation for both hands and ranges for any number of players
- High level types for working with ranges
- String based notation of cards and hands
- Pretty text format (like in [Deuces](https://github.com/worldveil/deuces))
- Ranges represented as [images](https://cloud.githubusercontent.com/assets/22860251/24349083/7085e25a-133a-11e7-8649-f0a3ab6bcd58.png)
- Post flop analysis for hand value probabilities

## Getting Fold'em
This project may available in the [Maven](TODO) Central Repository shortly. (TODO)

### Gradle
TODO

### Maven
TODO

## Documentation
A Javadoc for Fold'em is available [here](TODO). Additionally some examples have been provided in this repository [here](/src/main/java/codes/derive/foldem/examples). An effort has been made to keep this library as simple as possible to use. Some more basic usage examples have been provided in the section below.

### Basic Usage Guide
Most of this project's functionality is provided in the `codes.derive.foldem` package. In this package there is a helper class called `Poker` containing static functions for creating and working with the data types this project exposes. (TODO move links to point to Javadoc once its up)

It is recommended that you import this statically to make code less bulky:
```java
import static codes.derive.foldem.Poker
```

**From here you can easily utilize the main data types comprising the framework:**
```java
/* Create a card. */
Card aceOfHearts = card(Card.ACE, Suit.HEARTS);

/* Create another card, this time using shorthand. */
Card aceOfSpades = card("As");

/* Create a hand using the cards. */
Hand aces = hand(aceOfHearts, aceOfSpades);

/* Create another hand, this time using shorthand. */
Hand kings = hand("KhKs");

/* Calculate these hands' equity against each other. */
Map<Hand, Equity> equities = equity(aces, kings);

/* Print their equity against each other. */
System.out.println(aces + ": " + format(equities.get(aces)));
System.out.println(kings + ": " + format(equities.get(kings)));

/*
 * Output:
 * AhAs: Win: 82.5% Lose: 16.95% Split: 0.55%
 * KhKs: Win: 16.95% Lose: 82.5% Split: 0.55%
 */

/* Create a board. */
Board board = board("Kc7d2h");

/* Calculate equities again, this time on the board. */
equities = calculationBuilder().useBoard(board).calculate(aces, kings);

/* 
 * Print their equity against each other, this time throwing in pretty
 * formatting for style points.
 */
System.out.println("-- On board " + format(board));
System.out.println(format(aces) + ": " + format(equities.get(aces)));
System.out.println(format(kings) + ": " + format(equities.get(kings)));

/*
 * Output:
 * 	-- On board K♣, 7♦, 2❤
 * 	A❤,A♠: Win: 8.49% Lose: 91.51% Split: 0.0%
 * 	K❤,K♠: Win: 91.51% Lose: 8.49% Split: 0.0%
 */

/* Create a hand group containing all combinations of aces. */
Collection<Hand> allAces = handGroup("AA");

/* Create a range with aces, and 72 off-suit. */
Range a = range().define(allAces).define(handGroup("72o"));

/* Create a range with kings, and queens with a 70% weight. */
Range b = range().define(handGroup("KK")).define(0.7, handGroup("QQ"));

/* Calculate their equity against each other. */
Map<Range, Equity> rangeEquities = equity(a, b);

/* Print their equities. */
System.out.println("Range A: " + format(rangeEquities.get(a)));
System.out.println("Range B: " + format(rangeEquities.get(b)));

/*
 * Output:
 * Range A: Win: 34.86% Lose: 64.71% Split: 0.43%
 * Range B: Win: 64.71% Lose: 34.86% Split: 0.43%
 */
```

**Some more advanced usage:**
```java
/*
 * Lets find out what kind of hands aces and 72 off-suit are going to
 * make on a flop of 7h7dAc, and how often.
 */

/*
 * First create a codes.derive.foldem.tool.TextureAnalysisBuilder
 * context.
 */
TextureAnalysisBuilder bldr = new TextureAnalysisBuilder();

/* Set it to use the board 7h7dAc. */
bldr.useBoard(board("7h7dAc"));

/*
 * Create a calculation containing hand values mapped to their
 * frequencies for range "a", from earlier in the examples.
 */
Map<HandValue, Double> frequencies = bldr.frequencies(a);

/* Print our frequency information. */
for (HandValue value : frequencies.keySet()) {
	double frequency = frequencies.get(value);
	System.out.println(value + ": " + percent(frequency) + "%");
}

/*
 * Output:
 * 	FLUSH: 0.0%
 * 	STRAIGHT_FLUSH: 0.0%
 * 	FOUR_OF_A_KIND: 0.0%
 * 	PAIR: 0.0%
 * 	THREE_OF_A_KIND: 66.41%
 * 	FULL_HOUSE: 33.59%
 * 	HIGH_CARD: 0.0%
 * 	TWO_PAIR: 0.0%
 * 	NONE: 0.0%
 * 	STRAIGHT: 0.0%
 */

```

### More About
*Fold'em is a GPL licensed module in a larger proprietary project with functionality similar to projects like [CREV](http://gtorangebuilder.com/). Among other things, it includes high-level types for representing and working game trees as well as improvements on this library such as faster implementations of `codes.derive.foldem.eval.Evaluator`.*

*If you are interested in learning more about the expanded version of this library please direct your enquiries to mail@derive.codes.*
