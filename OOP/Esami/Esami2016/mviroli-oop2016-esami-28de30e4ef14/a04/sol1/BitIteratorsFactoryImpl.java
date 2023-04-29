package ex2016.a04.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BitIteratorsFactoryImpl implements BitIteratorsFactory {

    @Override
    public Iterator<Bit> empty() {
        //return java.util.stream.Stream.<Bit>empty().iterator();
        return new Iterator<Bit>(){

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Bit next() {
                throw new NoSuchElementException();
            }};
    }
    
    private Iterator<Bit> of(Bit b){
        //return java.util.stream.Stream.<Bit>generate(()->b).iterator();
        return new Iterator<Bit>(){

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Bit next() {
                return b;
            }  
        };
    }

    @Override
    public Iterator<Bit> zeros() {
        return of(Bit.ZERO);
    }

    @Override
    public Iterator<Bit> ones() {
        return of(Bit.ONE);
    }
    
    private Bit bitFromBoolean(boolean b){
        return b ? Bit.ONE : Bit.ZERO;
    }

    @Override
    public Iterator<Bit> fromByteStartingWithLSB(int b) {
        return new Iterator<Bit>(){

            private int val = b;
            private int count = 0;
            
            @Override
            public boolean hasNext() {
                return count < 8;
            }

            @Override
            public Bit next() {
                if (!this.hasNext()){
                    throw new NoSuchElementException();
                }
                int old = val;
                count++;
                val = (byte) (val / 2);
                return bitFromBoolean(old%2 == 1);
            }
            
        };
    }

    @Override
    public Iterator<Bit> fromBitList(List<Bit> list) {
        return list.iterator();
    }

    @Override
    public Iterator<Bit> fromBooleanList(List<Boolean> list) {
        return list.stream().map(this::bitFromBoolean).iterator();
    }

}
