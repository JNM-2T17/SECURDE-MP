package model;

import java.util.Date;

public class User {
	public static final String SEARCH_PRODUCT = "searchProduct";
	public static final String PURCHASE_PRODUCT = "purchaseProduct";
	public static final String REVIEW_PRODUCT = "reviewProduct";
	public static final String ADD_PRODUCT = "addProduct";
	public static final String EDIT_PRODUCT = "editProduct";
	public static final String DELETE_PRODUCT = "deleteProduct";
	public static final String VIEW_RECORDS = "viewRecords";
	public static final String CREATE_ACCOUNT = "createAccount";
	private int id;
	private int role;
	private String username;
	private String fName;
	private String mi;
	private String lName;
	private String emailAddress;
	private String billHouseNo;
	private String billStreet;
	private String billSubd;
	private String billCity;
	private String billPostCode;
	private String billCountry;
	private String shipHouseNo;
	private String shipStreet;
	private String shipSubd;
	private String shipCity;
	private String shipPostCode;
	private String shipCountry;
	private boolean searchProduct;
	private boolean purchaseProduct;
	private boolean reviewProduct;
	private boolean addProduct;
	private boolean editProduct;
	private boolean deleteProduct;
	private boolean viewRecords;
	private boolean createAccount;
	
	public User() {
		super();
	}

	public User(int id, String username, String fName, String mi, String lName,
			String emailAddress, String billHouseNo, String billStreet,
			String billSubd, String billCity, String billPostCode,
			String billCountry, String shipHouseNo, String shipStreet,
			String shipSubd, String shipCity, String shipPostCode,
			String shipCountry) {
		super();
		this.id = id;
		this.username = username;
		this.fName = fName;
		this.mi = mi;
		this.lName = lName;
		this.emailAddress = emailAddress;
		this.billHouseNo = billHouseNo;
		this.billStreet = billStreet;
		this.billSubd = billSubd;
		this.billCity = billCity;
		this.billPostCode = billPostCode;
		this.billCountry = billCountry;
		this.shipHouseNo = shipHouseNo;
		this.shipStreet = shipStreet;
		this.shipSubd = shipSubd;
		this.shipCity = shipCity;
		this.shipPostCode = shipPostCode;
		this.shipCountry = shipCountry;
	}

	public User(int id, int role, String username, String fName, String mi,
			String lName, String emailAddress, String billHouseNo,
			String billStreet, String billSubd, String billCity,
			String billPostCode, String billCountry, String shipHouseNo,
			String shipStreet, String shipSubd, String shipCity,
			String shipPostCode, String shipCountry, boolean searchProduct,
			boolean purchaseProduct, boolean reviewProduct, boolean addProduct,
			boolean editProduct, boolean deleteProduct, boolean viewRecords,
			boolean createAccount) {
		super();
		this.id = id;
		this.role = role;
		this.username = username;
		this.fName = fName;
		this.mi = mi;
		this.lName = lName;
		this.emailAddress = emailAddress;
		this.billHouseNo = billHouseNo;
		this.billStreet = billStreet;
		this.billSubd = billSubd;
		this.billCity = billCity;
		this.billPostCode = billPostCode;
		this.billCountry = billCountry;
		this.shipHouseNo = shipHouseNo;
		this.shipStreet = shipStreet;
		this.shipSubd = shipSubd;
		this.shipCity = shipCity;
		this.shipPostCode = shipPostCode;
		this.shipCountry = shipCountry;
		this.searchProduct = searchProduct;
		this.purchaseProduct = purchaseProduct;
		this.reviewProduct = reviewProduct;
		this.addProduct = addProduct;
		this.editProduct = editProduct;
		this.deleteProduct = deleteProduct;
		this.viewRecords = viewRecords;
		this.createAccount = createAccount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getBillHouseNo() {
		return billHouseNo;
	}

	public void setBillHouseNo(String billHouseNo) {
		this.billHouseNo = billHouseNo;
	}

	public String getBillStreet() {
		return billStreet;
	}

	public void setBillStreet(String billStreet) {
		this.billStreet = billStreet;
	}

	public String getBillSubd() {
		return billSubd;
	}

	public void setBillSubd(String billSubd) {
		this.billSubd = billSubd;
	}

	public String getBillCity() {
		return billCity;
	}

	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}

	public String getBillPostCode() {
		return billPostCode;
	}

	public void setBillPostCode(String billPostCode) {
		this.billPostCode = billPostCode;
	}

	public String getBillCountry() {
		return billCountry;
	}

	public void setBillCountry(String billCountry) {
		this.billCountry = billCountry;
	}

	public String getShipHouseNo() {
		return shipHouseNo;
	}

	public void setShipHouseNo(String shipHouseNo) {
		this.shipHouseNo = shipHouseNo;
	}

	public String getShipStreet() {
		return shipStreet;
	}

	public void setShipStreet(String shipStreet) {
		this.shipStreet = shipStreet;
	}

	public String getShipSubd() {
		return shipSubd;
	}

	public void setShipSubd(String shipSubd) {
		this.shipSubd = shipSubd;
	}

	public String getShipCity() {
		return shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipPostCode() {
		return shipPostCode;
	}

	public void setShipPostCode(String shipPostCode) {
		this.shipPostCode = shipPostCode;
	}

	public String getShipCountry() {
		return shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	public boolean isSearchProduct() {
		return searchProduct;
	}

	public void setSearchProduct(boolean searchProduct) {
		this.searchProduct = searchProduct;
	}

	public boolean isPurchaseProduct() {
		return purchaseProduct;
	}

	public void setPurchaseProduct(boolean purchaseProduct) {
		this.purchaseProduct = purchaseProduct;
	}

	public boolean isReviewProduct() {
		return reviewProduct;
	}

	public void setReviewProduct(boolean reviewProduct) {
		this.reviewProduct = reviewProduct;
	}

	public boolean isAddProduct() {
		return addProduct;
	}

	public void setAddProduct(boolean addProduct) {
		this.addProduct = addProduct;
	}

	public boolean isEditProduct() {
		return editProduct;
	}

	public void setEditProduct(boolean editProduct) {
		this.editProduct = editProduct;
	}

	public boolean isDeleteProduct() {
		return deleteProduct;
	}

	public void setDeleteProduct(boolean deleteProduct) {
		this.deleteProduct = deleteProduct;
	}

	public boolean isViewRecords() {
		return viewRecords;
	}

	public void setViewRecords(boolean viewRecords) {
		this.viewRecords = viewRecords;
	}

	public boolean isCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(boolean createAccount) {
		this.createAccount = createAccount;
	}
	
	public boolean isAuth(String privilege) {
		switch(privilege) {
			case User.SEARCH_PRODUCT:
				return searchProduct;
			case User.PURCHASE_PRODUCT:
				return purchaseProduct;
			case User.REVIEW_PRODUCT:
				return reviewProduct;
			case User.ADD_PRODUCT:
				return addProduct;
			case User.EDIT_PRODUCT:
				return editProduct;
			case User.DELETE_PRODUCT:
				return deleteProduct;
			case User.VIEW_RECORDS:
				return viewRecords;
			case User.CREATE_ACCOUNT:
				return createAccount;
			default:
				return false;
		}
	}
}
