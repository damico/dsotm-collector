package org.jdamico.dsotm.dataobjects;

public class SearchItem {
	private String firstUsed; 
	private String lastUsed;
	private int timesUsed = 0;
	private String fieldName;
	
	public SearchItem(String firstUsed, String lastUsed, int timesUsed,	String fieldName) {
		super();
		this.firstUsed = firstUsed;
		this.lastUsed = lastUsed;
		this.timesUsed = timesUsed;
		this.fieldName = fieldName;
	}
	
	public String getFirstUsed() {
		return firstUsed;
	}
	public void setFirstUsed(String firstUsed) {
		this.firstUsed = firstUsed;
	}
	public String getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(String lastUsed) {
		this.lastUsed = lastUsed;
	}
	public int getTimesUsed() {
		return timesUsed;
	}
	public void setTimesUsed(int timesUsed) {
		this.timesUsed = timesUsed;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
}
