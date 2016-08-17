package model;

import java.util.ArrayList;

public class Cart {
	public ArrayList<Purchase> purchases;
	
	public Cart() {
		super();
		purchases = new ArrayList<Purchase>();
	}
	
	public int size() {
		return purchases.size();
	}

	public Purchase[] getPurchases() {
		return purchases.toArray(new Purchase[0]);
	}

	public void setPurchases(ArrayList<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	public void deleteItem(int itemId) {
		for(Purchase p : purchases) {
			if( p.getItem().getId() == itemId) {
				this.purchases.remove(p);
				break;
			}
		}
	}
	
	public void addPurchase(Item item, int quantity) throws Exception {
		if( quantity > 0 ) {
			boolean added = false;
			for(Purchase p : purchases) {
				if( p.getItem().getId() == item.getId()) {
					if( p.getQuantity() + quantity <= 2000) {
						p.setQuantity(p.getQuantity() + quantity);
					} else {
						throw new Exception("Overflowing quantity");
					}
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
