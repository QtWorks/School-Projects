package model;

public class AddressRecord {

	int id;
	String country;
	String state;
	String city;
	int zip;
	String street;
	int number;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public AddressRecord() {
	}
	
	public String toString(){
		return country + ","
			+ state + ","
			+ city + ","
			+ zip + ","
			+ number + " "
			+ street;
	}

	public String SqlString() {
		return "id from Address where id = " + this.id + " LIMIT 1";
	}

}
