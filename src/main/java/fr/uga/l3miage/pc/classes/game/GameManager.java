package fr.uga.l3miage.pc.classes.game;

import fr.uga.l3miage.pc.classes.strategies.StrategyFactory;
import lombok.Getter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

@Getter
public class GameManager {
    private static GameManager instance;

    private final StrategyFactory strategyFactory = new StrategyFactory();

    private Random random = new SecureRandom();

    private Game activeGame;

    private final ArrayList<Game> finishedGames = new ArrayList<>();

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startNewGameRandomStrategy(int turns) throws IllegalArgumentException {
        if (turns <= 0) {
            throw new IllegalArgumentException("A game cannot last 0 turns");
        }
        activeGame = new Game(turns);
        joinGame(new Tribe(), activeGame);
    }

    public void startNewGameSetStrategy(int turns, String strategyName) throws IllegalArgumentException {
        if (turns <= 0) {
            throw new IllegalArgumentException("A game cannot last 0 turns");
        }
        activeGame = new Game(turns, strategyName);
        Tribe tribe = new Tribe();
        joinGame(tribe, activeGame);
    }

    public void joinGame(Tribe tribe, Game game) {
        try {
            game.addTribe(tribe);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void endGame() {
        finishedGames.add(activeGame);
        activeGame = null;
    }

    public void changeRandom(Random random) {
        this.random = random;
    }
}
