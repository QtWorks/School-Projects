using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestBomb
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(test(2).ToString());
        }

        static int test(int n)
        {
            if (n < 2)
                return n;
            else
                return test(n - 1) + test(n + 1);
        }
    }
}
