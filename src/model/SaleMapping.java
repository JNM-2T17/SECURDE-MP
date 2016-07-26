package model;

public class SaleMapping {
	private String label;
	private double total;
	
	public SaleMapping() {
		super();
	}
	public SaleMapping(String label, double total) {
		super();
		this.label = label;
		this.total = total;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
