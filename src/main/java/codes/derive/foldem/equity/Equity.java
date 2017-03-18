package codes.derive.foldem.equity;

public class Equity {

	private double win = 0.0, lose = 0.0, split = 0.0;
	
	// TODO rename
	protected void applySample(double win, double lose, double split) {
		this.win += win;
		this.lose += lose;
		this.split += split;
	}
	
	public double win() {
		return win;
	}
	
	public double lose() {
		return lose;
	}
	
	public double split() {
		return split;
	}
	
	public String toString() {
		return new StringBuilder().append("[win=").append(win).append(" lose=")
				.append(lose).append(" split=").append(split).append("]")
				.toString();
	}

}
