package ex2015.a02a.sol1;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class LeagueImpl implements League {

    private final Map<String, Integer> table = new HashMap<>();
    private boolean started = false;

    public LeagueImpl() {
    }

    private void checkAndRaise(boolean condition, Supplier<RuntimeException> supplier) {
        if (condition) {
            throw supplier.get();
        }
    }

    @Override
    public void addTeam(String teamName) {
        checkAndRaise(this.isStarted(), () -> new IllegalStateException());
        checkAndRaise(this.table.containsKey(Objects.requireNonNull(teamName)), () -> new IllegalArgumentException());
        this.table.put(teamName, 0);
    }

    private boolean isStarted() {
        return this.started;
    }

    @Override
    public void start() {
        checkAndRaise(this.isStarted(), () -> new IllegalStateException());
        this.started = true;
    }

    @Override
    public void storeResults(Map<Pair<String, String>, Pair<Integer, Integer>> results) {
        checkAndRaise(!this.isStarted(), () -> new IllegalStateException("not started yet"));
        checkAndRaise(
                results.keySet().stream()
                        .anyMatch((p) -> !this.table.containsKey(p.getX()) || !this.table.containsKey(p.getY())),
                () -> new IllegalArgumentException("wrong name"));
        checkAndRaise(
                Stream.concat(results.keySet().stream().map(Pair::getX), results.keySet().stream().map(Pair::getY))
                        .distinct().collect(Collectors.counting()) != this.table.size(),
                () -> new IllegalArgumentException("name clash"));
        results.entrySet().forEach(e -> {
            this.updateScore(e.getKey().getX(), e.getValue().getX() - e.getValue().getY());
            this.updateScore(e.getKey().getY(), e.getValue().getY() - e.getValue().getX());
        });
    }

    private void updateScore(String teamName, int delta) {
        this.table.compute(teamName, (k, v) -> v + (delta == 0 ? 1 : delta > 0 ? 3 : 0));
    }

    @Override
    public Map<String, Integer> getTable() {
        checkAndRaise(!this.isStarted(), () -> new IllegalStateException("not started yet"));
        return Collections.unmodifiableMap(this.table);
    }

}
