package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class Game {
    private List<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private static final CardsDealer cardsDealer = new CardsDealer();
    private static final Scanner scan = new Scanner(System.in);
    private Card manilha;
    private Card lastThrownCard;
    private Player currentPlayer;
    private Player lastPlayer;
    private Player lastTrucoPlayer;
    private boolean skipTurn;
    private boolean isTrucado;
    private boolean leaveOptionsMenu;
    private boolean restartGame;
    private boolean continueGame;
    private int roundPoints;

    public Game() {
        isTrucado = false;
        restartGame = false;
        continueGame = true;
        roundPoints = 1;
    }

    public void newGame(List<Player> players) throws InterruptedException {
        out.println("--------------- Bem vindo ao Truco ----------------");
        while (continueGame) {
            if (players.isEmpty())
                throw new RuntimeException("Nenhum jogador especificado.");

            if (restartGame)
                restartGameStats();
            // adicionar players do parametro para players interno
            this.players = players;
            // entregar cartas e gerar manilha
            int[] cardsDealt = cardsDealer.dealCards(deck, players);
            manilha = cardsDealer.getManilha(deck, cardsDealt);

            int playerIndex = 0, option = 0, max = players.size();

            out.printf("Manilha da rodada é: %s.\n", manilha);
            out.printf("Todas as cartas de valor: %s são as mais fortes.\n", manilha.getNextRank());
            out.printf("A ordem de grandeza dos naipes é: Ouros < Espadas < Copas < Paus.\n", manilha);

            while (!restartGame) {
                if (skipTurn) {
                    skipTurn = false;
                    continue;
                }
                if (lastThrownCard != null) {
                    out.printf("A última carta foi jogada por %s e é um(a): %s\n", lastPlayer, lastThrownCard);
                }
                currentPlayer = players.get(playerIndex);

                out.println("--------------------------------------");
                out.println("Vez do jogador " + currentPlayer);
                out.println("--------------------------------------");
                printHand(currentPlayer.getHand());

                while (!leaveOptionsMenu) {
                    if (isTrucado) {
                        if (roundPoints >= 12)
                            printOptionsFinal();
                        else
                            printOptionsTrucado(roundPoints);
                    }else{
                        printOptions();
                    }

                    option = scan.nextInt();

                    if (isTrucado) {
                        if (roundPoints >= 12)
                            promptOptionsFinal(option);
                        else
                            promptOptionsTrucado(option);
                    } else
                        promptOptions(option);
                }
                leaveOptionsMenu = false;

                for (var player : players) {
                    if (player.getScore() >= 12) {
                        continueGame = false;
                    }
                }

                playerIndex = (playerIndex + 1) % max;
            }
        }
    }

    public void printHand(List<Card> hand) {
        out.println("------------------- Sua Mão ------------------");
        for (Card card : hand) {
            out.println(card);
        }
        out.println("----------------------------------------------");
    }

    public void printOptions() {
        out.println("(1) Para ver sua mão");
        out.println("(2) Para ver jogar carta");
        out.println("(3) Para trucar");
        out.println("(0) Para sair");
    }

    public void printOptionsTrucado(int roundPoints) {
        out.println("(1) Para ver sua mão");
        out.println("(2) Para aceitar");
        out.println("(3) Para correr");
        out.printf("(4) Para retrucar(pedir %d)\n", roundPoints + 3);
        out.println("(0) Para sair");
    }

    public void printOptionsFinal() {
        out.println("(1) Para ver sua mão");
        out.println("(2) Para aceitar");
        out.println("(3) Para correr");
        out.println("(0) Para sair");
    }

    public void promptOptions(int option) {
        switch (option) {
            case 1:
                printHand(currentPlayer.getHand());
                break;
            case 2:
                throwCard();
                break;
            case 3:
                trucoOption();
                break;
            case 0:
                leaveOptionsMenu = true;
                continueGame = false;
                break;
            default:
                break;
        }
    }

    public void promptOptionsTrucado(int option) {
        switch (option) {
            case 1:
                printHand(currentPlayer.getHand());
                break;
            case 2:
                trucoAcceptedOption();
                break;
            case 3:
                break;
            case 4:
                trucoOption();
                break;
            case 0:
                leaveOptionsMenu = true;
                continueGame = false;
                break;
            default:
                break;
        }
    }

    public void promptOptionsFinal(int option) {
        switch (option) {
            case 1:
                printHand(currentPlayer.getHand());
                break;
            case 2:
                acceptedOption();
                break;
            case 3:
                leaveOptionsMenu = true;
                break;
            case 0:
                leaveOptionsMenu = true;
                continueGame = false;
                break;
            default:
                break;
        }
    }

    private void printSelectableCards() {
        for (int i = 0; i < currentPlayer.getHand().size(); i++) {
            out.printf("(%d) para %s\n", i, currentPlayer.getHand().get(i));
        }
    }

    private void restartGameStats() {
        roundPoints = 1;
        currentPlayer = null;
        lastTrucoPlayer = null;
        lastThrownCard = null;
        restartGame = false;
    }

    private void trucoAcceptedOption() {
        if(lastTrucoPlayer == currentPlayer){
            out.println("Seu truco foi aceito, escolha uma carta: ");
        }
        out.println("Você aceitou o truco, escolha uma carta: ");

        printSelectableCards();
        int chosenCardIndex = scan.nextInt();
        Card chosenCard = currentPlayer.getHand().get(chosenCardIndex);

        compareCards(chosenCard);
        restartGame = true;
        leaveOptionsMenu = true;
    }

    private void trucoOption() {
        out.println("Você trucou, escolha uma carta e espere o inimigo aceitar ou correr. ");
        printSelectableCards();
        lastTrucoPlayer = currentPlayer;
        lastPlayer = currentPlayer;
        isTrucado = true;
        leaveOptionsMenu = true;
        roundPoints = roundPoints == 1 ? 3 : roundPoints + 3;
    }

    private void throwCard() {
        printSelectableCards();
        int chosenCardIndex = scan.nextInt();
        Card chosenCard = currentPlayer.getHand().get(chosenCardIndex);

        if (lastThrownCard != null) {
            compareCards(chosenCard);
        }
        lastPlayer = currentPlayer;
        currentPlayer.removeCard(chosenCard);
        leaveOptionsMenu = true;
        lastThrownCard = chosenCard;
    }

    private void compareCards(Card card) {
        if (card.isStrongerThan(lastThrownCard, manilha)) {
            out.printf("Parabéns, sua carta é mais forte, você ganhou %d ponto(s).\n", roundPoints);
            currentPlayer.increaseScore(roundPoints);
        } else {
            out.printf("Sua carta é mais fraca, %s ganhou %d ponto(s).\n", lastPlayer, roundPoints);
            if(isTrucado){
                lastTrucoPlayer.increaseScore(roundPoints);
            }
            lastPlayer.increaseScore(roundPoints);
        }
    }
}
