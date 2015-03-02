package com.MIGH.Poste.DataModels;

public class NoticeModel extends PostModel {
public	String NoticeType;
public	String NoticeTitle;
public	String NoticeSubject;
public	String NoticeMessage;
public	String NoticeImage;
public	String NoticeDoc;

	//COnstructor
	public NoticeModel(
			//Super
			String InfoID,
			String PosterID,
			String FeedID,
			String PostTime,
			String PostValidity,
			//Sub
			String NoticeType,
			String NoticeTitle,
			String NoticeSubject,
			String NoticeMessage,
			String NoticeImage,
			String NoticeDoc) {
		
		super(InfoID, PosterID,FeedID, PostTime, PostValidity);
		setNoticeType(NoticeType);
        setNoticeTitle(NoticeTitle);
		setNoticeSubject(NoticeSubject);
		setNoticeMessage(NoticeMessage);
		setNoticeImage(NoticeImage);
		setNoticeDoc(NoticeDoc);
		
		
	}

	public String getNoticeType() {
		return NoticeType;
	}

	public void setNoticeType(String noticeType) {
		NoticeType = noticeType;
	}

	public String getNoticeTitle() {
		return NoticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		NoticeTitle = noticeTitle;
	}

	public String getNoticeSubject() {
		return NoticeSubject;
	}

	public void setNoticeSubject(String noticeSubject) {
		NoticeSubject = noticeSubject;
	}

	public String getNoticeMessage() {
		return NoticeMessage;
	}

	public void setNoticeMessage(String noticeMessage) {
		NoticeMessage = noticeMessage;
	}

	public String getNoticeImage() {
		return NoticeImage;
	}

	public void setNoticeImage(String noticeImage) {
		NoticeImage = noticeImage;
	}

	public String getNoticeDoc() {
		return NoticeDoc;
	}

	public void setNoticeDoc(String noticeDoc) {
		NoticeDoc = noticeDoc;
	}

}
