package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.DataType;
import utility.Helper;
import utility.Status;

public class RRAlgorithm implements InterfaceAlgorithm {
	private long duration;
	private Map<String, Integer[]> solution;
	private List<DataType> data;
	private boolean found;
	private int max;
	private int quantum;
	private List<Status> available;
	private List<Status> blackList = new ArrayList<Status>();
	
	public RRAlgorithm(int qtm) {
		solution = new HashMap<String, Integer[]>();
		found=false;
		max=0;
		this.quantum=qtm;
	}

	@Override
	public void solve() {
		int lastOne = 0;
		duration = System.currentTimeMillis();
		available = Helper.getAvailable(data, lastOne,blackList,available);
		while(!available.isEmpty()||blackList.size()!=data.size())
		{
			for(int k=0;k<available.size();k++)
			{
				Status j = available.get(k);
				int cmp=0;
				while(j.getRemaining()>0 && cmp<this.quantum)
				{
					lastOne++;
					solution.put(j.getName(), Helper.addElement(solution.get(j.getName()), lastOne));
					cmp++;
					j.used();
				}
				if(j.getRemaining()<=0)
				{
					available.remove(k);
					if(!Helper.checkBlackedV2(blackList, j))
						blackList.add(j);
					k--;
				}
				available = Helper.getAvailable(data, lastOne,blackList,available);
			}
			if(available.isEmpty()&&blackList.size()!=data.size())
			{
				lastOne++;
				available = Helper.getAvailable(data, lastOne,blackList,available);
			}
		}
		this.max=lastOne;
		found=true;
		duration = System.currentTimeMillis()-duration;
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
