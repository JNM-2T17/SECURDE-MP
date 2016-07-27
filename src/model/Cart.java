package model;

import java.util.ArrayList;

public class Cart {
	public ArrayList<Purchase> purchases;
	
	public Cart() {
		super();
		purchases = new ArrayList<Purchase>();
	}

	public ArrayList<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(ArrayList<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	public void addPurchase(Item item, int quantity) {
		if( quantity > 0 ) {
			boolean added = false;
			for(Purchase p : purchases) {
				if( p.getItem().getId() == item.getId()) {
					p.setQuantity(p.getQuantity() + quantity);
					added = true;
					break;
				}
			}
			if( !added ) {
				purchases.add(new Purchase(item,quantity));
			}
		}
	}
	
	public double getTotal() {
		double total = 0;
		for(Purchase p : purchases) {
			total += p.getTotal();
		}
		return total;
	}
	
	public void clear() {
		purchases.clear();
	}
}
