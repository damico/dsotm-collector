package org.jdamico.dsotm.dataobjects;

public class Visit {
	
	
	private int placeId = 0;
	private String timestamp;
	private boolean isBookmarked =  false;
	private int timesUsed = 0;

	public Visit(Integer placeId, String timestamp, boolean isBookmarked, int timesUsed) {
		setPlaceId(placeId);
		setTimestamp(timestamp);
		setBookmarked(isBookmarked);
		setTimesUsed(timesUsed);
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isBookmarked() {
		return isBookmarked;
	}

	public void setBookmarked(boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
	}

	public int getTimesUsed() {
		return timesUsed;
	}

	public void setTimesUsed(int timesUsed) {
		this.timesUsed = timesUsed;
	}
	
	
	
	
}
