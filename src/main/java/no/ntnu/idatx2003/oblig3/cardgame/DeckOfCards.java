package no.ntnu.idatx2003.oblig3.cardgame;

import static java.lang.Math.random;

import java.util.ArrayList;
import java.util.List;

public class DeckOfCards {
  private List<PlayingCard> cards;
  private final char[] suits = {'S', 'H', 'D', 'C'};

  /**
   * Constructs a new deck of cards.
   */
  public DeckOfCards() {
    cards = new ArrayList<>();
    for (char suit : suits) {
      for (int face = 1; face <= 13; face++) {
        cards.add(new PlayingCard(suit, face));
      }
    }
  }

  /**
   * Returns the number of cards in the deck.
   *
   * @return the list of cards in the deck
   */
  public List<PlayingCard> getCards() {
    return new ArrayList<>(cards);

  }

  /**
   * Returns a random card from the deck.
   *
   */

  public List<PlayingCard> dealHand(int n) {
    if (n < 1 || n > cards.size()) {
      throw new IllegalArgumentException("Invalid number of cards to deal");
    }
    List<PlayingCard> hand = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      int index = (int) (random() * cards.size());
      hand.add(cards.remove(index));
    }
    return hand;


  }

}
