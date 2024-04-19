package game;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<>();

    public Deck() {
        generateCards();
    }

    public void generateCards() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCardAtIndex(int index) {
        return cards.get(index);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
