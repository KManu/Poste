package com.MIGH.Poste.DataModels;

public class EventPosterModel extends PosterModel {
	String PosterEnabled;

	/**
	 * @param posterID
	 * @param posterName
	 * @param posterContactNumber
	 * @param posterContactLocation
	 */
	public EventPosterModel(String posterID, String posterName,
			String posterContactNumber, String posterContactLocation, String posterEnabled) {
		super(posterID, posterName, posterContactNumber, posterContactLocation);
		// TODO Auto-generated constructor stub
		PosterEnabled = posterEnabled;
	}

	public String getPosterEnabled() {
		return PosterEnabled;
	}

	public void setPosterEnabled(String posterEnabled) {
		PosterEnabled = posterEnabled;
	}

}
