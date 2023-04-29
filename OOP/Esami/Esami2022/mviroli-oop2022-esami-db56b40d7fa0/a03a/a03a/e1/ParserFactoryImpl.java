package a03a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class ParserFactoryImpl implements ParserFactory{

    private <X> Parser<X> general(){
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                return false;
            }
            
        };
    }

    @Override
    public <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                return acceptedSequences.contains(new ArrayList<>(){{
                                                    while(iterator.hasNext()){
                                                        add(iterator.next());
                                                    }}});
            }
            
        };
    }

    @Override
    public <X> Parser<X> fromGraph(X x0, Set<Pair<X, X>> transitions, Set<X> acceptanceInputs) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> l = new ArrayList<>(){{
                    while(iterator.hasNext())
                        add(iterator.next());
                }};
                for(int i = 0; i < l.size(); i++){
                    if(i < l.size()-1){
                        Pair<X,X> p = new Pair<X,X>(l.get(i), l.get(i+1));
                        if(!transitions.contains(p)){
                            return false;
                        }
                    } else {
                        if(!acceptanceInputs.contains(l.get(i))){
                            return false;
                        }
                    }
                }
                return l.isEmpty() ? false : true;
            }
            
        };
    }

    @Override
    public <X> Parser<X> fromIteration(X x0, Function<X, Optional<X>> next) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> l = new ArrayList<>(){{
                    while(iterator.hasNext())
                        add(iterator.next());
                }};
                for(int i = 0; i < l.size(); i++){
                    if(next.apply(l.get(i)).isEmpty()){
                        return i == l.size()-1 ? true : false;
                    }
                }
                return false;
            }
            
        };
    }

    @Override
    public <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> l = new ArrayList<>(){{
                    while(iterator.hasNext())
                        add(iterator.next());
                }};
                if (l.isEmpty()){
                    return false;
                }
                return nextParser.apply(l.get(0)).get().accept(l.stream().dropWhile(i -> i == l.get(0)).iterator());
            }
            
        };
    }

    @Override
    public <X> Parser<X> fromParserWithInitial(X x, Parser<X> parser) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> l = new ArrayList<>(){{
                    while(iterator.hasNext())
                        add(iterator.next());
                }};
                if(l.isEmpty() || l.get(0) != x){
                    return false;
                }
                l.remove(l.get(0));
                return parser.accept(l.iterator());
            }
            
        };
    }
    
}
