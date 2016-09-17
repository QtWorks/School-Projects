package model;

import java.util.ArrayList;

public class SearchModel {
	
	ArrayList<String> keywords;
	String country,state,city,category;
	boolean hasPrice;
	int sortedBy;
	double lo, hi;
	
	

	public double getLo() {
		return lo;
	}

	public void setLo(double lo) {
		this.lo = lo;
	}

	public double getHi() {
		return hi;
	}

	public void setHi(double hi) {
		this.hi = hi;
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

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(String words) {
		String[] w = words.split(",");
		for(int i = 0; i < w.length; i++){
			String word = w[i].trim();
			if(word.length() > 0)
				keywords.add(word);
		}
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSortedBy() {
		return sortedBy;
	}

	public void setSortedBy(int sortedBy) {
		this.sortedBy = sortedBy;
	}

	public SearchModel() {
		keywords = new ArrayList<String>();
	}

	public boolean hasKeywords() {
		return keywords.size() > 0;
	}

	public void setPriceRange(String range) {
		if(range.length() > 0){
			String[] s = range.split("-");
			lo = Integer.parseInt(s[0]);
			hi = Integer.parseInt(s[1]);
			hasPrice = true;
		}
		else{
			hasPrice = false;
		}		
	}

	public boolean hasPrice() {
		return hasPrice;
	}

}
