package com.MIGH.Poste.DataModels;

public class FeedsModel {
public String FeedID;
public String PosterID;
public String FeedName;
public String FeedDescription;

	public FeedsModel(
			String FeedID,
			String PosterID,
			String FeedName,
			String FeedDescription
			) {
		//No super. This is god himself. Well not really. But kinda. 
		this.FeedID = FeedID;
		this.PosterID = PosterID;
		this.FeedDescription = FeedDescription;
		this.FeedName = FeedName;
		
	}

}
// tbh getters and setters aren't necessary. so screw em. just make everything public :)