package model;

public class KeywordRecord {
	
	String keyword, language;

	public KeywordRecord() {
		
	}

	public KeywordRecord(String language, String keyword) {
		this.keyword = keyword;
		this.language = language;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
