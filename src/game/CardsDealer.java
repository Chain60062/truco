package game;

import java.util.List;
import java.util.Random;

public class CardsDealer {

    private Random random = new Random();

    public int[] dealCards(Deck deck, List<Player> players) {

        if(deck.isEmpty()) throw new RuntimeException("Deck está vazio, gere um novo deck.");

        int[] cardsDealt = new int[40];
        int arrayIndex = 0;

        for (Player player : players) {
            for (int j = 0; j < 3;) {
                int cardIndex = random.nextInt(40);
                boolean repeated = false;

                // checa se carta ja foi distribuida
                for (int k = 0; k < cardsDealt.length; k++) {
                    if (cardIndex == cardsDealt[k]) {
                        repeated = true;
                        break;
                    }
                }

                /*
                 * nao incrementa j e vai para a proxima iteracao para gerar uma carta
                 * que ja nao tenha saido
                 */
                if (repeated)
                    continue;

                /*
                 * caso nao tenho repetido, incrementa index depois de atribuir index da carta
                 * ao array
                 * de cartas ja distribuidas
                 */
                cardsDealt[arrayIndex++] = cardIndex;
                player.addCard(deck.getCardAtIndex(cardIndex));
                j++;
            }
        }
        return cardsDealt;
    }

    public Card getManilha(Deck deck, int[] cardsDealt) {
        int cardIndex;
        boolean isUnique;
        do {
            isUnique = true;
            cardIndex = random.nextInt(40);
            // checa se carta ja foi distribuida
            for (int i = 0; i < cardsDealt.length; i++) {
                if (cardIndex == cardsDealt[i]) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);

        return deck.getCardAtIndex(cardIndex);
    }
}
