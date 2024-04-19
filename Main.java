import java.util.Arrays;

import game.Game;
import game.Player;

// jogo de truco paulista com manilha variavel em Java
public class Main {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.newGame(Arrays.asList(new Player("rODRIgo"), new Player("BrENo")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
