using System;
using System.Collections.Generic;
using System.IO;

namespace Application
{

    class Person
    {
        public int Rank;
        public string Name;
        public string Citizenship;
        public int Age;
        public double Worth;
        public string Source;

        public override string ToString()
        {
            return Rank.ToString() + " " + Name + " " + Citizenship + " " + Age + " " + Worth + " " + Source;

        }
    };

    class CountryBalance
    {
        public string Name;
        public int Billionare;
        public double Worth;

        public override string ToString()
        {
            return Name.ToString() + " " + Billionare + " " + Math.Round(Worth,2);

        }
    };

    class Program
    {
        private static void Main(string[] args)
        {

            var streamReader = new StreamReader("WorldWealthList.txt");
            string record;
            int size = 2;
            var data = new List<Person>();
            while (!streamReader.EndOfStream && size > 0)
            {
                record = streamReader.ReadLine();
                --size;
            }
            while (!streamReader.EndOfStream)
            {
                record = streamReader.ReadLine();
                var person2 = new Person();
                if (SetPerson(record, person2))
                    data.Add(person2);
            }
            Console.WriteLine("loaded Records =" + data.Count);
            Console.Write("Balance over all = ");
            double balance = 0.0;
            for (int count = 0; count < data.Count; ++count)
            {
                if (data[count].Worth > 0) balance += data[count].Worth;
            }
            Console.Write(balance);
            Console.WriteLine(" billions US$\n");

            Console.Write("average age over all = ");
            double avgAge = 0.0;
            for (int count = 0; count < data.Count; ++count)
            {
                if (data[count].Age > 0) avgAge += data[count].Age;
            }
            avgAge = avgAge / data.Count;
            Console.WriteLine(avgAge);

            Console.Write("average balance over all = ");
            double avgBalance = 0.0;
            for (int count = 0; count < data.Count; ++count)
            {
                if (data[count].Worth > 0) avgBalance += data[count].Worth;
            }
            avgBalance = avgBalance / data.Count;
            Console.Write(avgBalance);
            Console.WriteLine(" billions US$\n");

            Console.WriteLine("**** Top 10 ****\n");
            var data2 = new List<Person>();
            for (int count = 0; count < data.Count; ++count)
            {
                int count2 = 0;
                for (; count2 < data2.Count; ++count2)
                {
                    if (data[count].Worth > data2[count2].Worth)
                    {
                        break;
                    }
                }
                data2.Insert(count2, data[count]);
            }
            for (int count = 0; count < 10; ++count)
            {
                if (count < data2.Count)
                    Console.WriteLine(data2[count].ToString());
            }

            Console.WriteLine("****************");

            Console.WriteLine("*** country balance ***");
            List<CountryBalance> data3 = new List<CountryBalance>();
            for (int count = 0; count < data.Count; ++count)
            {
                var p1 = data[count];
                CountryBalance found = null;
                for (int count2 = 0; count2 < data3.Count; ++count2)
                {
                    if (p1.Citizenship == data3[count2].Name)
                    {
                        found = data3[count2];
                        break;
                    }
                }
                if (found == null)
                {

                    found = new CountryBalance();
                    found.Name = p1.Citizenship;
                    data3.Add(found);
                }
                found.Billionare += 1;
                found.Worth += p1.Worth;
            }

            var data4 = new List<CountryBalance>();
            for (int count = 0; count < data3.Count; ++count)
            {
                int count2 = 0;
                for (; count2 < data4.Count; ++count2)
                {
                    if (data3[count].Worth > data4[count2].Worth)
                    {
                        break;
                    }
                }
                data4.Insert(count2, data3[count]);
            }

            for (int count = 0; count < data4.Count; ++count)
            {
                Console.WriteLine(data4[count].ToString());
            }

        }

        static List<string> splitString(string x, char y)
        {
            int i = 0, j = 0;
            var split = new List<string>();
            foreach (char c in x)
            {
                if (y == c)
                {
                    split.Add(x.Substring(j, i - j));
                    ++i;
                    j = i;
                    continue;
                }
                ++i;
                if (i == x.Length)
                    split.Add(x.Substring(j, i - j));
            }
            return split;
        }

        static bool SetPerson(string listRecord, Person person)
        {
            var split = splitString(listRecord, '\t');
            if (split.Count < 6)
                return false;
            setPartsToPerson(split, person);
            return true;
        }
        static void setPartsToPerson(List<string> split, Person person)
        {
            try
            {
                person.Rank = int.Parse(split[0]);
            }
            catch (Exception e)
            {
            }

            person.Name = split[1];
            person.Citizenship = split[2];
            try
            {
                person.Age = int.Parse(split[3]);
            }
            catch (Exception e)
            {
            }

            try
            {
                person.Worth = float.Parse(split[4], System.Globalization.CultureInfo.InvariantCulture);
            }
            catch (Exception e)
            {
            }
            person.Source = split[5];
        }

    }
}
