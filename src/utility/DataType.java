package utility;

public class DataType {
	private String name;
	private int arriving;
	private int time;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getArriving() {
		return arriving;
	}
	public void setArriving(int arriving) {
		this.arriving = arriving;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public DataType(String name, int arriving, int time) {
		this.name = name;
		this.arriving = arriving;
		this.time = time;
	}
	
	public DataType() {
	}
}
