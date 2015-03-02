package com.MIGH.Poste.DataModels;


public class EventNoticeModel extends PostModel {
			String EventName;
			String EventType;
			String EventTime;
			String EventVenue;
			String EventOrganisers;
			String EventEventDetails;
			String EventDetails;
			String EventContact;
			String EventGPS;
			String EventImage;
	

	/**
	 * @param InfoID
	 * @param PosterID
	 * @param PostTime
	 * @param PostValidity
	 * @param eventName
	 * @param eventType
	 * @param eventTime
	 * @param eventVenue
	 * @param eventOrganisers
	 * @param eventDetails
	 * @param eventContact
	 * @param eventGPS
	 * @param eventImage
	 */
			
	// Constructor
	public EventNoticeModel(
			String InfoID,
			String PosterID,
			String FeedID,
			String PostTime,
			String PostValidity,
			String eventName,
			String eventType,
			String eventTime,
			String eventVenue,
			String eventOrganisers,
			String eventDetails,
			String eventContact,
			String eventGPS,
			String eventImage) {
		super(InfoID, PosterID, FeedID, PostTime, PostValidity);
		EventName = eventName;
		EventType = eventType;
		EventTime = eventTime;
		EventVenue = eventVenue;
		EventOrganisers = eventOrganisers;
		EventDetails = eventDetails;
		EventContact = eventContact;
		EventGPS = eventGPS;
		EventImage = eventImage;
	}

	// Getters and setters
	public String getEventName() {
		return EventName;
	}

	public void setEventName(String eventName) {
		EventName = eventName;
	}

	public String getEventType() {
		return EventType;
	}

	public void setEventType(String eventType) {
		EventType = eventType;
	}

	public String getEventTime() {
		return EventTime;
	}

	public void setEventTime(String eventTime) {
		EventTime = eventTime;
	}

	public String getEventVenue() {
		return EventVenue;
	}

	public void setEventVenue(String eventVenue) {
		EventVenue = eventVenue;
	}

	public String getEventOrganisers() {
		return EventOrganisers;
	}

	public void setEventOrganisers(String eventOrganisers) {
		EventOrganisers = eventOrganisers;
	}

	public String getEventDetails() {
		return EventDetails;
	}

	public void setEventDetails(String eventDetails) {
		EventDetails = eventDetails;
	}

	public String getEventContact() {
		return EventContact;
	}

	public void setEventContact(String eventContact) {
		EventContact = eventContact;
	}

	public String getEventGPS() {
		return EventGPS;
	}

	public void setEventGPS(String eventGPS) {
		EventGPS = eventGPS;
	}

	public String getEventImage() {
		return EventImage;
	}

	public void setEventImage(String eventImage) {
		EventImage = eventImage;
	}

}
