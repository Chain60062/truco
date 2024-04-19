package game;

public class Card {
    private Suit suit;
    private Rank rank;
    private boolean isManilha;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(Rank rank, Suit suit, boolean isManilha) {
        this.rank = rank;
        this.suit = suit;
    }

    public boolean isStrongerThan(Card other, Card manilha) {
        Rank nextManilhaRank = manilha.getNextRank();
        nextManilhaRank.setForce(nextManilhaRank.getStrongestForce() + 1);

        if (this.rank.getForce() == nextManilhaRank.getForce()) {
            // se os numeros forem iguais, o naipe decide, comparando pela posicao no enum.
            return this.suit.ordinal() > other.suit.ordinal();
        }
        return this.rank.getForce() > nextManilhaRank.getForce();
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