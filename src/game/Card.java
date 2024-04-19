package game;

public class Card {
    private Suit suit;
    private Rank rank;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(Rank rank, Suit suit, boolean isManilha) {
        this.rank = rank;
        this.suit = suit;
    }

    public boolean isStrongerThan(Card other, Card vira) {
        Rank manilha = vira.getNextRank();

        if (this.getRank().equals(manilha)) {
            this.rank.setForce(manilha.getStrongestForce() + 1);
        } else if (other.getRank().equals(manilha)) {
            other.rank.setForce(manilha.getStrongestForce() + 1);
        }

        if (this.rank.getForce() == other.rank.getForce()) {
            return this.suit.ordinal() > other.suit.ordinal();
        }
        return this.rank.getForce() > other.rank.getForce();
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Rank getNextRank() {
        int nextValue = rank.getForce() + 1;

        if (nextValue > Rank.TRES.getForce()) {
            return Rank.QUATRO;
        }
        return Rank.getRankByForce(nextValue);
    }

    @Override
    public String toString() {
        return capitalizeFirstLetter(rank.toString()) + " de " + capitalizeFirstLetter(suit.toString());
    }

    private String capitalizeFirstLetter(String text) {
        var lowerCaseText = text.toLowerCase();
        return lowerCaseText.substring(0, 1).toUpperCase() + lowerCaseText.substring(1);
    }
}