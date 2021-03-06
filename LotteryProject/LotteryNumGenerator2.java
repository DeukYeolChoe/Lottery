import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class LotteryNumGenerator2 {
	public static int[] highFreqNums;
	public static int[] avgEachCols;
	public static Map<String,Integer> map = new TreeMap<String,Integer>();
	public static int count;
	public static int TOTAL = 500;
	public static int howmany;
	
	public static void MakeLotteryNumbers(String line)
	{
		  String[] splitNums = line.split(",");
		  
		  int temp[] = new int[7];
		  int index = 0;
		  for (String s : splitNums)
		  {
			  int count = 0;
			  if (map.containsKey(s))
				  count = map.get(s);
			  map.put(s, ++count);
			  if (s.equals(""))
				  continue;
			  temp[index] = Integer.parseInt(s);
			  index++;
		  }
		  int gapArr[] = gapCalculator(temp).clone();
		  
		  for (int i = 0; i < gapArr.length; i++)
		  {
			  avgEachCols[i] += gapArr[i];
		  }		  
	}
	
	public static int[] gapCalculator(int[] arr)
	{
		int result[] = new int[6];
		for (int i = 0; i < arr.length-1; i++)
		{
			result[i] = Math.abs(arr[i] - arr[i+1]);
		}
		return result;
	}
	
	public static void printNumbers()
	{
		int n = 0;
		boolean close = false;
		Map<String, Integer> sortByMap = sortByValues(map);
		for (Map.Entry<String,Integer> entry : sortByMap.entrySet()){
			String key = entry.getKey();
			
			if (n == 10){
				break;
			}	
			if (key.equals(""))
				continue;
			if (Integer.parseInt(key) <= 10)
			{
				highFreqNums[n] = Integer.parseInt(key);
				n++;	
			}
		}
		
		for (int i = 0; i < highFreqNums.length; i++)
		{
			int sum = 0;
			System.out.print(highFreqNums[i] + " ");
			sum = highFreqNums[i];
			for (int j = 0; j < avgEachCols.length; j++)
			{
				sum += avgEachCols[j];
				if (sum <= 45)
				{
					System.out.print(sum + " ");
				}
			}
			System.out.println();
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
		howmany = 0;	
		avgEachCols = new int[6];
		
		try {	
			FileReader reader = new FileReader(fileName);
			BufferedReader buffer = new BufferedReader(reader);

			while((line = buffer.readLine()) != null){
				MakeLotteryNumbers(line);
			}	
			
			for (int i = 0; i < 6; i++)
			{
				avgEachCols[i] /= TOTAL;
			}
			highFreqNums = new int[10];
			printNumbers();
			buffer.close();
		}
		catch(FileNotFoundException ex){
			System.out.println("Failed to read");
		}
		catch(IOException ex){
			System.out.println("Failed to read");
		}
	}
}
