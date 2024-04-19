package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Rank {
    QUATRO(0),
    CINCO(1),
    SEIS(2),
    SETE(3),
    DAMA(4),
    VALETE(5),
    REI(6),
    AS(7),
    DOIS(8),
    TRES(9);

    private int force;

    private static final Map<Integer, Rank> BY_FORCE = new HashMap<>();

    static {
        for (Rank rank : values()) {
            BY_FORCE.put(rank.force, rank);
        }
    }

    Rank(int force) {
        this.force = force;
    }

    public static Rank getRankByForce(int force) {
        return BY_FORCE.get(force);
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getStrongestForce() {
        return Collections.max(BY_FORCE.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
