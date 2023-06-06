package a03a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class ParserFactoryImpl implements ParserFactory {

    @Override
    public <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> tmp = new ArrayList<>();
                iterator.forEachRemaining(elem -> tmp.add(elem));
                return acceptedSequences.contains(tmp);
            }
        };
    }

    @Override
    public <X> Parser<X> fromGraph(X x0, Set<Pair<X, X>> transitions, Set<X> acceptanceInputs) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> list = new ArrayList<>();
                iterator.forEachRemaining(elem -> list.add(elem));
                for (int i = 0; i < list.size(); i++) {
                    if (i == list.size() - 1) {
                        if (!acceptanceInputs.contains(list.get(i))) {
                            return false;
                        }
                    } else if (!transitions.contains(new Pair<>(list.get(i), list.get(i + 1)))) {
                        return false;
                    }
                }
                return !list.isEmpty();
            }
        };
    }

    @Override
    public <X> Parser<X> fromIteration(X x0, Function<X, Optional<X>> next) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                X tmp = x0;
                while (iterator.hasNext()) {
                    if (next.apply(tmp).isEmpty()) {
                        return false;
                    }
                    tmp = iterator.next();
                }
                return next.apply(tmp).isEmpty();
            }
        };
    }

    @Override
    public <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                if (!iterator.hasNext()) {
                    return false;
                }
                X nxt = iterator.next();
                List<X> list = new ArrayList<>();
                iterator.forEachRemaining(elem -> list.add(elem));
                if (nextParser.apply(nxt).get().accept(list.iterator())) {
                    return true;
                }
                return false;
            }
        };

    }

    @Override
    public <X> Parser<X> fromParserWithInitial(X x, Parser<X> parser) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> list = new ArrayList<>();
                if (iterator.hasNext() && iterator.next() != x) {
                    return false;
                }
                iterator.forEachRemaining(elem -> list.add(elem));
                return parser.accept(list.iterator());
            }
        };
    }

}
