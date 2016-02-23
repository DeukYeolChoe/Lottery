import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeMap;

public class LotteryNumGenerator1 {
	static Map<String,Integer> map = new TreeMap<String,Integer>();
	
	public static void MakeLotteryNumbers(String line)
	{
		  String[] splitNums = line.split(",");
				
		  for (String s : splitNums)
		  {
			  int count = 0;
			  if (map.containsKey(s))
				  count = map.get(s);
			  map.put(s, ++count);
		  }
	}
	
	public static void printNumbers(int howmany)
	{
		int i = 0;
		String[] highFreqNums = new String[20];
		String[] temp = new String[20];
		Map<String, Integer> sortByMap = sortByValues(map);
		for (Map.Entry<String,Integer> entry : sortByMap.entrySet()){
			String key = entry.getKey();

			if (i == 20){
				break;
			}
			highFreqNums[i] = key;
			i++;
		}
		
		Random rand = new Random();
		
		for (int z = 0; z < howmany; z++)
		{
			temp = highFreqNums.clone();		
			int j = 0;
			while(j < 6)
			{
				int n = rand.nextInt(20); // 0 ~ 19
				if (temp[n] == "used")
					continue;
				else{
					temp[n] = "used";
					j++;
				}
			}
			print(temp, highFreqNums);
			System.out.println();
		}
	}
	
	public static void print(String[] arr, String[] ori)
	{
		int[] temp = new int[6];
		int a = 0;
		for (int i = 0; i < 20; i++)
		{
			if (arr[i] == "used"){
				temp[a] = Integer.parseInt(ori[i]);
				a++;
			}
		}
		
		Arrays.sort(temp);
		for (int i = 0; i < 6; i++)
		{
			System.out.print(temp[i] + " ");
		}
	}
	
	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
	    Comparator<K> valueComparator =  new Comparator<K>() {
	        public int compare(K k1, K k2) {
	            int compare = map.get(k2).compareTo(map.get(k1));
	            if (compare == 0) return 1;
	            else return compare;
	        }
	    };
	    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
	}
	
	public static void main(String[] args) {
		String fileName = "LotteryNumber.txt";
		String line = "";		

		try {	
			FileReader reader = new FileReader(fileName);
			BufferedReader buffer = new BufferedReader(reader);
			
		    Scanner in = new Scanner(System.in);
		    System.out.println("How many lottery numbers do you want?");
		    int howmany = in.nextInt();
		    
		    
			while((line = buffer.readLine()) != null){
				MakeLotteryNumbers(line);
			}	
			printNumbers(howmany);
			buffer.close();
			in.close();
		}
		catch(FileNotFoundException ex){
			System.out.println("Failed to read");
		}
		catch(IOException ex){
			System.out.println("Failed to read");
		}
	}

}
