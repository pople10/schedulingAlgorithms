package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.DataType;
import utility.Helper;
import utility.Status;

public class SJF_NOPAlgorithm implements InterfaceAlgorithm {
	private long duration;
	private Map<String, Integer[]> solution;
	private List<DataType> data;
	private boolean found;
	private int max;
	private List<Status> available;
	private List<Status> blackList = new ArrayList<Status>();

	public SJF_NOPAlgorithm() {
		solution = new HashMap<String, Integer[]>();
		found=false;
		max=0;
	}

	@Override
	public void solve() {
		int lastOne = 0;
		duration = System.currentTimeMillis();
		available = Helper.getAvailable(data, lastOne,blackList,available);
		available = Helper.sortByTotal(available);
		while(!available.isEmpty()||blackList.size()!=data.size())
		{
			if(!available.isEmpty())
			{
				Status j = available.get(0);
				while(j.getRemaining()>0)
				{
					lastOne++;
					solution.put(j.getName(), Helper.addElement(solution.get(j.getName()), lastOne));
					j.used();
				}
				if(j.getRemaining()<=0)
				{
					available.remove(0);
					if(!Helper.checkBlackedV2(blackList, j))
						blackList.add(j);
				}
			}
			available = Helper.getAvailable(data, lastOne,blackList,available);
			available = Helper.sortByTotal(available);
			if(available.isEmpty()&&blackList.size()!=data.size())
			{
				lastOne++;
				available = Helper.getAvailable(data, lastOne,blackList,available);
				available = Helper.sortByTotal(available);
			}
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
		return duration;
	}

	@Override
	public void setData(List<DataType> data) {
		this.data=data;
	}

	@Override
	public int getMax() {
		return max;
	}

}
