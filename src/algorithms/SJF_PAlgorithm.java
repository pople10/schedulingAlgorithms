package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.DataType;
import utility.Helper;
import utility.Status;

public class SJF_PAlgorithm implements InterfaceAlgorithm {
	private long duration;
	private Map<String, Integer[]> solution;
	private List<DataType> data;
	private boolean found;
	private int max;
	private List<Status> available;
	private List<Status> blackList = new ArrayList<Status>();

	public SJF_PAlgorithm() {
		solution = new HashMap<String, Integer[]>();
		found=false;
		max=0;
	}

	@Override
	public void solve() {
		int lastOne = 0;
		duration = System.currentTimeMillis();
		refresh(lastOne);
		while(!available.isEmpty()||blackList.size()!=data.size())
		{
			if(!available.isEmpty())
			{
				Status j = available.get(0);
				boolean flag=true;
				while(j.getRemaining()>0&&flag)
				{
					lastOne++;
					solution.put(j.getName(), Helper.addElement(solution.get(j.getName()), lastOne));
					j.used();
					if(Helper.isThereAnyPremption(data, lastOne))
					{
						flag=false;
						refresh(lastOne);
					}
				}
				if(j.getRemaining()<=0)
				{
					available.remove(0);
					if(!Helper.checkBlackedV2(blackList, j))
						blackList.add(j);
				}
			}
			refresh(lastOne);
			if(available.isEmpty()&&blackList.size()!=data.size())
			{
				lastOne++;
				refresh(lastOne);
			}
		}
		duration = System.currentTimeMillis()-duration;
		this.max = lastOne;
		found=true;
	}
	
	public void refresh(int lastOne)
	{
		available = Helper.getAvailable(data, lastOne,blackList,available);
		available = Helper.sortByTotal(available);
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
