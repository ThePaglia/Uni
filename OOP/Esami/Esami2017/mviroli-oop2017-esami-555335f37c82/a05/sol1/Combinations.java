package a05.sol1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combinations {
	
	public static void main(String[] args) {
		// lista degli incontri di 4 squadre, denominate 1,2,3,4
		System.out.println(combine(Arrays.asList(1,2,3,4)));
		// [[1, 2], [3, 4], [1, 3], [2, 4], [1, 4], [2, 3], [2, 1], [4, 3], [3, 1], [4, 2], [3, 2], [4, 1]]
		// ossia:
		// prima giornata, giocano [1,2] e [3,4]
		// seconda giornata, giocano [1,3] e [2,4]
		// terza giornata, giocano [1,4] e [2,3]
		// quarta giornata, giocano [2,1] e [4,3]
		// quinta giornata, giocano [3,1] e [4,2]
		// sesta giornata, giocano [3,2] e [4,1]		
	}

	// genera una lista di coppie, combinazione dei team in ingresso
	public static <X> List<List<X>> combine(List<X> list){
		// struttura dati in uscita
		List<List<X>> outlist = new ArrayList<>();
		// il numero di giornate è (N-1)*2
		for (int i=0;i<(list.size()-1)*2;i++) {
			// team non ancora assegnati
			List<X> viableteams = new ArrayList<>(list);
			// doppio ciclo, per generare tutte le coppie ordinate (1,1),(1,2),(1,3),(1,4),(2,1),(2,2),..
			for (X s1: list) {
				for (X s2: list) {
					// potenziale combinazione da verificare
					List<X> newCombination = Arrays.asList(s1,s2);
					if (!s1.equals(s2) && // una squadra non gioca con se stessa 
							viableteams.contains(s1) && // s1 non già assegnata in questa giornata
							viableteams.contains(s2) && // s2 non già assegnata in questa giornata
							!outlist.contains(newCombination)) { // s1,s2 non già calendarizzata in precedenza) 
						outlist.add(newCombination); // aggiungo la combinazione
						viableteams.remove(s1); // aggiorno viableteams
						viableteams.remove(s2); // aggiorno viableteams
					}
				}
			}
		}
		return outlist;
	}

}
