package ex2014.a01b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class AddressFormStrategy implements FormStrategy<String>{
	
	private static enum AddressField implements FormField {
		NOME("Nome",15,x->x.length()>1), 
		COGNOME("Cognome",15,x->x.length()>2), 
		CF("Codice Fiscale",16,x->x.length()==16), 
		ANNO("Anno di nascita",4,x->{
			Optional<Integer> res=scanToInt(x);
			return res.isPresent() && res.get()>=1900 &&  res.get()<2016;
		}),
		VIA("Via",30,x->x.length()>7),
		CAP("CAP",5,x-> x.length()==5 && scanToInt(x).isPresent()),
		CITTA("Città",15,x->x.length()>3),
		PROVINCIA("Provincia",2,x->x.length()==2);
		
		private String nome;
		private int length;
		private Predicate<String> pred;
		
		private AddressField(String nome, int length, Predicate<String> pred) {
			this.nome = nome;
			this.length = length;
			this.pred = pred;
		}
		
		public String getNome() {
			return nome;
		}

		public int getLength() {
			return length;
		}

		public Predicate<String> getPred() {
			return pred;
		}
		
		private static Optional<Integer> scanToInt(String s){
			try{
				return Optional.of(new Integer(s));
			} catch (NumberFormatException e){
				return Optional.empty();
			}
		}
		
	}
	
	@Override
	public List<FormField> fields() {
		return Arrays.asList(AddressField.values());
	}

	@Override
	public String result(List<String> values) {
		return Stream.of(AddressField.values())
				     .map(f->f.nome+"="+values.get(f.ordinal()))
				     .collect(Collectors.joining(";", "[", "]"));
	}

}
