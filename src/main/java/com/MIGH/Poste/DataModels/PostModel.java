package com.MIGH.Poste.DataModels;

public abstract class PostModel {
	/**
	 * Might as well be an interface. sigh
	 * 
	 */
	public String InfoID;
	public String PosterID;
	public String PostTime;
	public String PostValidity;
	public String FeedID;
	// Constructor
	public PostModel(String InfoID, String PosterID, String FeedID, String PostTime, String PostValidity) {
		super();
		this.FeedID = FeedID;
		setInfoID(InfoID);
		setPosterID(PosterID);
		setPostTime(PostTime);
		setPostValidity(PostValidity);
	}
	
	// Getters and setters
	public String getInfoID() {
		return InfoID;
	}
	public void setInfoID(String infoID) {
		InfoID = infoID;
	}
	public String getPosterID() {
		return PosterID;
	}
	public void setPosterID(String posterID) {
		PosterID = posterID;
	}
	public String getPostTime() {
		return PostTime;
	}
	public void setPostTime(String postTime) {
		PostTime = postTime;
	}
	public String getPostValidity() {
		return PostValidity;
	}
	public void setPostValidity(String postValidity) {
		PostValidity = postValidity;
	}

	// Other methods
	
	
}


