using System;

namespace InitialExamples
{
   public class Initialisation
   {
      public static void Code()
      {
         String s = new String("aaa");
         string s2 = "bbb";  // string and String are aliases
         s2 = "ccc"; // reassignment
         s2 = s; // s2 will contain a reference to the object of "aaa"

         String ss; // Define name ss, without initialisation
         // String s3 = ss; // this would not compile!
         ss = "init"; // now assign ss

         int i = 5 + 2; // int and Int32 are aliases
         int j = i; // j and i both contain bits representing 7

         Object o = null; // null is a special reference
         object o2 = o; // object and Object are aliases

         var x = 5; // by type inference, equivalent to int x = 5;
      }
   }
}