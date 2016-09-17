using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PriorityQueue
{
    class PQ
    {
        private HashSet<int>[] items;
        private Dictionary<int, int> weights;

        public PQ(int m)
        {
            items = new HashSet<int>[m+1];
            for(int i = 0; i < m+1; i++)
            {
                items[i] = new HashSet<int>();
            }
            weights = new Dictionary<int, int>();
        }

        //Notes with Fred:
        //items[weight].Add(item);
        //weights.Add(item,weight);
        /*
            It should change the weight of "item" to "weight" 
            (if "item" is already in the priority queue), or
            it should insert "item" with a weight of "weight" 
            (otherwise).  You may assume that 0 \le≤weight\le≤m. 
            It should run in O(1) time. 
        */
        public void insertOrUpdate(int item, int weight)
        {
            //If item is not in items, add it
            if (!weights.ContainsKey(item))
            {
                weights.Add(item, weight);
                items[weight].Add(item);
            }
            //Update weight associated with value
            else
            {
                int old_weight = weights[item];
                items[old_weight].Remove(item);
                weights[item] = weight;
                items[weight].Add(item);
            }
        }
        /// <summary>
        /// It should remove and return the item in the priority 
        /// queue with the smallest weight unless the priority 
        /// queue is empty, in which case is should throw an Exception.
        /// (If more than one item has the smallest weight, remove and
        /// return any one of them.)  It should run in O(m) time.
        /// </summary>
        /// <returns></returns>
        public int deleteMin()
        {
            for(int i = 0; i < items.Length; i++)
            {
                if(items[i].Count > 0)
                {
                    int item = items[i].First<int>();
                    int weight = i;
                    items[i].Remove(item);
                    weights.Remove(item);
                    return item;
                }
            }
            throw new Exception("Empty PQ");
        }
        static void Main(string[] args)
        {
            PQ pq = new PQ(600);
            pq.insertOrUpdate(5, 500);
            pq.insertOrUpdate(4, 3);
            pq.insertOrUpdate(6, 600);
            pq.insertOrUpdate(2, 2);
            pq.insertOrUpdate(3, 1);
            pq.insertOrUpdate(7, 70);
            pq.insertOrUpdate(10, 0);
            if(pq.deleteMin() != 10)
            {
                Console.WriteLine("Wrong 10");
            }
            if (pq.deleteMin() != 3)
            {
                Console.WriteLine("Wrong 3");
            }
            if (pq.deleteMin() != 2)
            {
                Console.WriteLine("Wrong 2");
            }
            if (pq.deleteMin() != 4)
            {
                Console.WriteLine("Wrong 4");
            }
            Console.WriteLine("Done");
            Console.ReadLine();
        }
    }
}
