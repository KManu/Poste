
package com.MIGH.Poste.DataModels;

public class FeedPosterModel extends PosterModel {
	String PosterTitle;

	public FeedPosterModel(
			String posterID, 
			String posterName,
			String posterContactNumber, 
			String posterContactLocation, 
			String posterTitle) {
		super(posterID, posterName, posterContactNumber, posterContactLocation);
		
		this.PosterTitle= posterTitle;
	}
	
	public String getPosterTitle(){
		return this.PosterTitle;
	}
}
