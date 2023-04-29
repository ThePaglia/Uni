package ex2014.a03b.sol1;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 */

public class DiceStatisticsImpl implements DiceStatistics{
	
	private Optional<String> fileName = Optional.empty();

	@Override
	public void setFileName(String fileName) {
		this.fileName = Optional.of(fileName);
	}

	@Override
	public void storeSeries(long size,int diceNumber) throws IOException {
		Random r = new Random();
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName.get())));
		dos.writeLong(size);
		for (long l=0;l<size;l++){
			dos.writeInt(IntStream.range(0,diceNumber).map(i->r.nextInt(6)+1).sum());
		}
		dos.close();
	}
	
	@Override
	public long loadToLuckyCount() throws IOException {
		return loadToMap().get((loadToMap().size()-1)*6/5);
	}
	
	@Override
	public Map<Integer, Long> loadToMap() throws IOException {
		return genericLoad(
				new HashMap<Integer,Long>(), 
				(map,i) ->{
					map.merge(i, 0L, (v,v2)->v+1);
					return map;
				}
		);
	}
	
	@Override
	public long loadToOddCount() throws IOException {
		return genericLoad(0L,(l,i)->l+i%2);
	}
		
	private <D> D genericLoad(D data, BiFunction<D,Integer,D> acc) throws IOException {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName.get())));
		long size = dis.readLong();
		for (long l=0;l<size;l++){
			data = acc.apply(data,dis.readInt());
		}
		dis.close();
		return data;
	}
	
}
