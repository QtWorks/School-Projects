package model;

public class UserRecord {

	private String login;
	private String name;
	private String password;
	private int addressId;
	private AddressRecord address;
	private String phone;
	private boolean isAdmin;
	
	public UserRecord() {		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public AddressRecord getAddress() {
		return address;
	}

	public void setAddress(AddressRecord address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean equals(UserRecord other){
		return other.getLogin().equals(login);
	}

	public String SqlString() {
		return "login from User where login like " + this.login;
	}

}
