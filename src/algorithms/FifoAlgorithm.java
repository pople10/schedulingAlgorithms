package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.DataType;

import utility.Helper;

public class FifoAlgorithm implements InterfaceAlgorithm {
	
	private long duration;
	private Map<String, Integer[]> solution;
	private List<DataType> data;
	private boolean found;
	private int max;

	public FifoAlgorithm() {
		solution = new HashMap<String, Integer[]>();
		found=false;
		max=0;
	}

	@Override
	public void solve() {
		duration = System.currentTimeMillis();
		List<DataType> sorted = Helper.sortByArriving(data);
		int lastOne = 0;
		for(DataType i : sorted)
		{
			int starting=lastOne;
			if(lastOne<i.getArriving())
			{
				starting=i.getArriving();
			}
			lastOne = i.getTime()+starting;
			List<Integer> list = new ArrayList<Integer>();
			for(int j=starting+1;j<=lastOne;j++)
			{
				list.add(j);
			}
			solution.put(i.getName(),  Helper.fromListToArray(list));
		}
		duration = System.currentTimeMillis()-duration;
		this.max = lastOne;
		found=true;
	}

	@Override
	public boolean isSolutionFound() {
		return found;
	}

	@Override
	public Map<String, Integer[]> getSolution() {
		return solution;
	}

	@Override
	public long getDuration() {
		return this.duration;
	}

	@Override
	public void setData(List<DataType> data) {
		this.data = data;
	}

	@Override
	public int getMax() {
		return max;
	}

}
