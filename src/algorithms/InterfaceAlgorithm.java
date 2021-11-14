package algorithms;

import java.util.List;
import java.util.Map;

import utility.DataType;

public interface InterfaceAlgorithm {
	public void solve();
	public boolean isSolutionFound();
	public Map<String,Integer[]> getSolution();
	public long getDuration();
	public void setData(List<DataType> data);
	public int getMax();
}
