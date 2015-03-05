import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

    public class Program
    {
        static class Person
        {
            public int Rank;
            public String Name;
            public String Citizenship;
            public int Age;
            public double Worth;
            public String Source;

            public String toString()
            {
                return ""+Rank + " " + Name + " " + Citizenship + " " + Age + " " + Worth + " " + Source;

            }
        }
        
        static class CountryBalance
        {
            public String Name;
            public int Billionare;
            public double Worth;

            public String toString()
            {
            	DecimalFormat df = new DecimalFormat("#.00");
                return Name + " " + Billionare + " " + df.format(Worth);

            }
        }
        public static void main(String[] args) throws FileNotFoundException
        {
            Scanner streamReader = new Scanner(new FileInputStream("WorldWealthList.txt"));
            String record;
            int size = 2;
            List<Person> data = new ArrayList<Person>();
            while (streamReader.hasNextLine() && size > 0)
            {
                record = streamReader.nextLine();
                --size;
            }
            while (streamReader.hasNextLine())
            {
                record = streamReader.nextLine();
                Person person2 = new Person();
                if (SetPerson(record, person2))
                    data.add(person2);
            }
            streamReader.close();
            System.out.println("loaded Records =" + data.size());
            System.out.print("Balance over all = ");
            double balance = 0.0;
            for (int count = 0; count < data.size(); ++count)
            {
                if (data.get(count).Worth > 0) balance += data.get(count).Worth;
            }
            System.out.print(balance);
            System.out.println(" billions US$\n");

            System.out.print("average age over all = ");
            double avgAge = 0.0;
            for (int count = 0; count < data.size(); ++count)
            {
                if (data.get(count).Age > 0) avgAge += data.get(count).Age;
            }
            avgAge = avgAge / data.size();
            System.out.println(avgAge);

            System.out.print("average balance over all = ");
            double avgBalance = 0.0;
            for (int count = 0; count < data.size(); ++count)
            {
                if (data.get(count).Worth > 0) avgBalance += data.get(count).Worth;
            }
            avgBalance = avgBalance / data.size();
            System.out.print(avgBalance);
            System.out.println(" billions US$\n");

            System.out.println("**** Top 10 ****\n");
            List<Person> data2 = new ArrayList<Person>();
            for (int count = 0; count < data.size(); ++count)
            {
                int count2 = 0;
                for (; count2 < data2.size(); ++count2)
                {
                    if (data.get(count).Worth > data2.get(count2).Worth)
                    {
                        break;
                    }
                }
                data2.add(count2, data.get(count));
            }
            for (int count = 0; count < 10; ++count)
            {
                if (count < data2.size())
                    System.out.println(data2.get(count).toString());
            }

            System.out.println("****************");

            System.out.println("*** country balance ***");
            List<CountryBalance> data3 = new ArrayList<CountryBalance>();
            for (int count = 0; count < data.size(); ++count)
            {
                Person p1 = data.get(count);
                CountryBalance found = null;
                for (int count2 = 0; count2 < data3.size(); ++count2)
                {
                    if (p1.Citizenship.equals( data3.get(count2).Name))
                    {
                        found = data3.get(count2);
                        break;
                    }
                }
                if (found == null)
                {

                    found = new CountryBalance();
                    found.Name = p1.Citizenship;
                    data3.add(found);
                }
                found.Billionare += 1;
                found.Worth += p1.Worth;
            }

            List<CountryBalance> data4 = new ArrayList<CountryBalance>();
            for (int count = 0; count < data3.size(); ++count)
            {
                int count2 = 0;
                for (; count2 < data4.size(); ++count2)
                {
                    if (data3.get(count).Worth > data4.get(count2).Worth)
                    {
                        break;
                    }
                }
                data4.add(count2, data3.get(count));
            }

            for (int count = 0; count < data4.size(); ++count)
            {
                System.out.println(data4.get(count).toString());
            }

        }

        static List<String> splitString(String x, char y)
        {
            int i = 0, j = 0;
            List<String> split = new ArrayList<String>();
            char[] ca = x.toCharArray();
            for (char c : ca)
            {
                if (y == c)
                {
                    split.add(x.substring(j, j+ i - j));
                    ++i;
                    j = i;
                    continue;
                }
                ++i;
                if (i == x.length())
                    split.add(x.substring(j, j + i - j));
            }
            return split;
        }

        static boolean SetPerson(String listRecord, Person person)
        {
            List<String> split = splitString(listRecord, '\t');
            if (split.size() < 6)
                return false;
            setPartsToPerson(split, person);
            return true;
        }
        
        static void setPartsToPerson(List<String> split, Person person)
        {
            try
            {
                person.Rank = Integer.parseInt(split.get(0));
            }
            catch (Exception e)
            {
            }

            person.Name = split.get(1);
            person.Citizenship = split.get(2);
            try
            {
                person.Age = Integer.parseInt(split.get(3));
            }
            catch (Exception e)
            {
            }

            try
            {
                person.Worth = Float.parseFloat(split.get(4));
            }
            catch (Exception e)
            {
            }
            person.Source = split.get(5);
        }

    }
