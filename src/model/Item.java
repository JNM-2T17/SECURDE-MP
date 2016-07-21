package model;

public class Item {
	private int id;
	private String name;
	private int typeId;
	private String itemtype;
	private String description;
	private double price;
	
	public Item() {
		super();
	}
	
	public Item(int id, String name, int typeId, String itemtype,
			String description, double price) {
		super();
		this.id = id;
		this.name = name;
		this.typeId = typeId;
		this.itemtype = itemtype;
		this.description = description;
		this.price = price;
	}


	public Item(int id, String name, String itemtype, String description, double price) {
		super();
		this.id = id;
		this.name = name;
		this.itemtype = itemtype;
		this.description = description;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
