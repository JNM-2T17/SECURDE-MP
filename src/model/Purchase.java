package model;

public class Purchase {
	private Item item;
	private int quantity;
	
	public Purchase() {
		super();
	}
	public Purchase(Item item, int quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
