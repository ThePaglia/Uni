using System;
using System.Collections.Generic;

namespace Aula
{
   class Program
   {
      static void Main(string[] args)
      {
         var l = new List<int>(new int[]{-20,10,20,-50,30,40});
         var lo = Filter(l, i => i > 0);
         foreach(var i in lo) Console.Write(i+" ");
      }
      
      static IList<int> Filter(IList<int> list, Predicate<int> predicate)
      {
         var l = new List<int>();
         foreach (var t in list) if (predicate(t)) l.Add(t);
         return l;
      }
   }
}