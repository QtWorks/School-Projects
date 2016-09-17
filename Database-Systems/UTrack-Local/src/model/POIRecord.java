package model;

import java.util.ArrayList;

public class POIRecord {
	
	int id;
	String name;
	int addressId;
	AddressRecord address;
	String url;
	String phone;
	int year_est;
	String hours;
	double price;
	String category;
	ArrayList<KeywordRecord> keywords;

	public ArrayList<KeywordRecord> getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String keywordString){
		setKeywords(getKeywords(keywordString));
	}

	public void setKeywords(ArrayList<KeywordRecord> keywords) {
		this.keywords = keywords;
	}
	
	public void addKeyword(KeywordRecord keyword){
		keywords.add(keyword);
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getYear_est() {
		return year_est;
	}

	public void setYear_est(int year_est) {
		this.year_est = year_est;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public POIRecord() {
		keywords = new ArrayList<KeywordRecord>();
	}
	
	public String toStringFull(){
		String addr = "";
		if(address != null)
			addr = address.toString();
		
		return "{" 
				+ "\n\tTitle: " + name
				+ "\n\tAddress: " + addr
				+ "\n\tUrl: " + url
				+ "\n\tYear Est.: " + year_est
				+ "\n\tHours: " + hours
				+ "\n\tPrice: " + price
				+ "\n\tCategory: " + category
				+ "\n\tKeywords: " + getKeywordsString()
			+"\n}";		
	}

	public String SqlString() {
		return "id from POI where id = " + this.id;
	}

	public String getKeywordsString() {
		String words = "";
		if(this.keywords.size() > 0){
			for(KeywordRecord word : keywords){
				words += word.getLanguage() + "/" +word.getKeyword() + ",";
			}
			words = words.substring(0,words.length() - 1);
		}
		return words;
	}
	
	private static ArrayList<KeywordRecord> getKeywords(String line) {
		String[] records = line.split(",");
		ArrayList<KeywordRecord> keywordList = new ArrayList<KeywordRecord>();
		for(int i = 0; i < records.length; i++){
			String r = records[i].trim();
			String[] split = r.split("/");
			KeywordRecord record = new KeywordRecord(split[0].trim(),split[1].trim());
			keywordList.add(record);
		}
		return keywordList;
	}

}
