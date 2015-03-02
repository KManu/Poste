package com.MIGH.Poste.DataModels;

public abstract class PosterModel {
	String PosterID;
	String PosterName;
	String PosterContactNumber;
	String PosterContactLocation;

	// Constructor
	/**
	 * @param posterID
	 * @param posterName
	 * @param posterContactNumber
	 * @param posterContactLocation
	 */
	public PosterModel(String posterID, String posterName,
			String posterContactNumber, String posterContactLocation) {
		super();
		PosterID = posterID;
		PosterName = posterName;
		PosterContactNumber = posterContactNumber;
		PosterContactLocation = posterContactLocation;
	}

	public String getPosterID() {
		return PosterID;
	}

	public void setPosterID(String posterID) {
		PosterID = posterID;
	}

	public String getPosterName() {
		return PosterName;
	}

	public void setPosterName(String posterName) {
		PosterName = posterName;
	}

	public String getPosterContactNumber() {
		return PosterContactNumber;
	}

	public void setPosterContactNumber(String posterContactNumber) {
		PosterContactNumber = posterContactNumber;
	}

	public String getPosterContactLocation() {
		return PosterContactLocation;
	}

	public void setPosterContactLocation(String posterContactLocation) {
		PosterContactLocation = posterContactLocation;
	}
	
	//Other Methods

}
