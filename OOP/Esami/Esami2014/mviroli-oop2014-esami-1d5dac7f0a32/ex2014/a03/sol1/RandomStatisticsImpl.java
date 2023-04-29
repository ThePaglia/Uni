package ex2014.a03.sol1;

import java.io.*;
import java.util.*;
import java.util.function.*;

/**
 * Questa soluzione usa le lambda per fattorizzare il comportamento
 * comune delle tre load, ma una soluzione equivalente era ottenibile
 * con uno strategy e le classi anonime.
 */

public class RandomStatisticsImpl implements RandomStatistics{
	
	private Optional<String> fileName = Optional.empty();

	@Override
	public void setFileName(String fileName) {
		this.fileName = Optional.of(fileName);
	}

	@Override
	public void storeSeries(long size) throws IOException {
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName.get())));
		dos.writeLong(size);
		for (long l=0;l<size;l++){
			dos.writeDouble(Math.random());
		}
		dos.close();
	}
		
	@Override
	public double loadToMax() throws IOException {
		return genericLoad((double)0,(x,y)->x>y?x:y);
	}
	
	@Override
	public long loadToCountSmaller(double threshold) throws IOException{
		return genericLoad((long)0,(x,d)-> d<=threshold ? x+1 : x);
	}
	
	@Override
	public List<Long> loadToList(int ranges) throws IOException{
		return genericLoad(
				new ArrayList<>(Collections.nCopies(ranges, 0L)), 
				(l,d) ->{
					int slot = (int)Math.floor(d*ranges);
					l.set(slot,l.get(slot)+1);
					return l;
				}
		);
	}
		
	private <D> D genericLoad(D data, BiFunction<D,Double,D> acc) throws IOException {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName.get())));
		long size = dis.readLong();
		for (long l=0;l<size;l++){
			data = acc.apply(data,dis.readDouble());
		}
		dis.close();
		return data;
	}
	
}
