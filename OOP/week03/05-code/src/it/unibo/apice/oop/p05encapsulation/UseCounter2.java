package it.unibo.apice.oop.p05encapsulation;

public class UseCounter2 {

   /* Conto su un array, con Contatore creato internamente */
   static int countElements(int[] array, int elem) {
      final Counter c = new Counter();
      for (final int i : array) { // schema "for-each"
         if (i == elem) {
            c.increment();
         }
      }
      return c.getValue();
   }

   /* Conto su un array, con Contatore passato in input */
   static void countInArray(int[] array, int elem, Counter c) {
      for (final int i : array) {
         if (i == elem) {
            c.increment();
         }
      }
   }

   /* Conto su una matrice, riusando appieno la countInArray */
   static void countInMatrix(int[][] matrix, int elem, Counter c) {
      for (final int[] array : matrix) {
         countInArray(array, elem, c);
      }
   }
}
