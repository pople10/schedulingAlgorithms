package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import exceptions.InvalidDataException;

public class Helper {

	public static void displayVector(Integer[] is) {
		for(Integer i : is)
		{
			System.out.print(i+" ");
		}
		System.out.println("");
	}
	
	public static String secondsToString(float f)
	{
		if(f<=1.0f)
			return f+" second";
		return f+" seconds";
	}
	
	public static int[] stringList_TO_intList(char[] s)
	{
		int size = s.length;
		int[] n = new int[size]; 
		for(int i=0; i<size; ++i) {
			n[i] = Integer.parseInt(s[i]+"");
		}
		return n;
	}
	
	public static void DisplayStringInList(char[] s)
	{
		for(int i=0; i<s.length; ++i) {
			System.out.print(s[i]);
		}
		System.out.println("");
	}
	
	public static void smallSeparator()
	{
		System.out.println("-----------------");
	}
	
	public static void bigSeparator()
	{
		System.out.println("*************************************************");
	}
	
	public static void displayMatrixWithSeparators(int[][] output)
	{
		for(int []i : output)
		{
			for(int j : i)
				System.out.print(j+" ");
			System.out.println("");
			smallSeparator();
		}
	}
	
	public static List<DataType> sortByArriving(List<DataType> res)
	{
		List<DataType> data = new ArrayList<DataType>(res);
		int time=-1;
		int size = data.size();
		for(int i=0;i<size;i++)
		{
			time=data.get(i).getArriving();
			for(int j=i+1;j<size;j++)
			{
				DataType curr = data.get(j);
				if(time>curr.getArriving())
				{
					DataType tmp = data.get(i);
					data.set(i, curr);
					data.set(j,tmp);
				}
			}
		}
		return data;
	}
	
	public static Integer[] fromListToArray(List<Integer> list)
	{
		int size = list.size();
		Integer[] output = new Integer[size];
		for(int i=0;i<size;i++)
		{
			output[i]=list.get(i);
		}
		return output;
	}
	
	public static void displayListDataType(List<DataType> list)
	{
		for(DataType i : list)
		{
			System.out.println("name : " + i.getName() + " ,arriving : "+ 
					i.getArriving()+" ,time :"+i.getTime());
		}
	}
	
	public static void displayListDataStatus(List<Status> list)
	{
		for(Status i : list)
		{
			System.out.println("name : " + i.getName() + " ,remaining : "+ 
					i.getRemaining()+" ,total :"+i.getTotal());
		}
	}
	
	public static boolean valueExist(Integer[] list,int toFind)
	{
		for(Integer i : list)
		{
			if(i==toFind)
				return true;
		}
		return false;
	}
	
	public static int getStartFromName(List<DataType> list,String name)
	{
		for(DataType i : list)
		{
			if(name.equals(i.getName()))
				return i.getArriving();
		}
		return 0;
	}
	
	public static Integer[] addElement(Integer[] arr,int i)
	{
		int size;
		if(arr==null)
		{
			size=1;
			arr = new Integer[size];
		}
		else
		{
			size=arr.length+1;
		}
		Integer[] res = new Integer[size];
		res=Arrays.copyOf(arr, size);
		res[size-1]=i;
		return res;
	}
	
	public static List<Status> getAvailable(List<DataType> data,int lastOne,List<Status> blacked,List<Status> avail)
	{
		List<Status> available = new ArrayList<Status>();
		if(avail!=null)
			available = avail;
		for(DataType i : data)
		{
			if(i.getArriving()<=lastOne && !checkBlacked(blacked,i) && checkAvailable(avail,i)==null)
			{
				Status tmp = new Status();
				tmp.setName(i.getName());
				tmp.setTotal(i.getTime());
				available.add(tmp);
			}
		}
		return available;
	}
	
	public static boolean checkBlacked(List<Status> list,DataType i)
	{
		if(list==null)
			return false;
		for(Status j : list)
		{
			if(i.getName().equals(j.getName()))
				return true;
		}
		return false;
	}
	
	public static boolean checkBlackedV2(List<Status> list,Status i)
	{
		if(list==null)
			return false;
		for(Status j : list)
		{
			if(i.getName().equals(j.getName()))
				return true;
		}
		return false;
	}
	
	public static Status checkAvailable(List<Status> list,DataType i)
	{
		if(list==null)
			return null;
		for(Status j : list)
		{
			if(i.getName().equals(j.getName()))
				return j;
		}
		return null;
	}
	
	
	public static void displaySolution(Map<String, Integer[]> result)
	{

		for(Map.Entry<String, Integer[]> entry : result.entrySet())
		{
			System.out.println("name : "+entry.getKey()+ " : ");
			for(Integer i : entry.getValue())
			{
				System.out.print(i+" ");
			}
			System.out.println(" ");
		}
	}
	
	public static List<DataType> settingData(List<DataType> data)
	{
		List<DataType> res = new ArrayList<DataType>();
		for(DataType i : data)
		{
			DataType tmp = new DataType();
			tmp.setArriving(i.getArriving());
			tmp.setName(i.getName());
			tmp.setTime(i.getTime());
			res.add(tmp);
		}
		return res;
	}
	
	public static List<Status> sortByTotal(List<Status> res)
	{
		List<Status> data =  new ArrayList<Status>(res);
		Collections.sort(data, new Comparator<Status>() {
		    @Override
		    public int compare(Status lhs, Status rhs) {
		        return lhs.getTotal()-rhs.getTotal();
		    }
		});
		/*int size = data.size();
		for(int i=0;i<size;i++)
		{
			Status curr = data.get(i);
			for(int j=i+1;j<size;j++)
			{
				Status next = data.get(j);
				if(curr.getTotal()==next.getTotal())
				{
					if(curr.getName().ind)
				}
			}
		}*/
		return data;
	}
	
	public static List<Status> sortByRemaining(List<Status> res)
	{
		List<Status> data =  new ArrayList<Status>(res);
		Collections.sort(data, new Comparator<Status>() {
		    @Override
		    public int compare(Status lhs, Status rhs) {
		        return lhs.getRemaining()-rhs.getRemaining();
		    }
		});
		return data;
	}
	
	public static boolean isThereAnyPremption(List<DataType> list,int lastOne)
	{
		for(DataType i : list)
		{
			if(i.getArriving()==lastOne)
				return true;
		}
		return false;
	}
	
	public static List<DataType> readDataFromFile(File f) throws IOException,InvalidDataException
	{
		List<DataType> res = new ArrayList<DataType>();
		BufferedReader br = new BufferedReader(new FileReader(f));
        String line = "";
        boolean flag=false;
		while ((line = br.readLine()) != null) {
		   if(!flag)
		   {
			   flag=true;
			   continue;
		   }
		   String[] comps = line.split(";");
		   if(comps.length!=3)
		   {
			   throw new InvalidDataException("Few or too much argument for one line");
		   }
		   DataType tmp = new DataType();
		   tmp.setName(comps[0]);
		   if(!Validator.isInteger(comps[1])||!Validator.isInteger(comps[2]))
		   {
			   throw new InvalidDataException("Non-numeric values");
		   }
		   tmp.setArriving(Integer.parseInt(comps[1]));
		   tmp.setTime(Integer.parseInt(comps[2]));
		   res.add(tmp);
		}
		return res;
	}
}
