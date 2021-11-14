package utility;

public class Status {
	private String name;
	private int remaining;
	private int total;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRemaining() {
		return remaining;
	}
	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
		this.setRemaining(total);
	}
	
	public void used()
	{
		this.setRemaining(this.getRemaining()-1);
	}
}
