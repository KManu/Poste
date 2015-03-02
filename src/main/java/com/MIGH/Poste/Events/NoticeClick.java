package com.MIGH.Poste.Events;

import com.MIGH.Poste.DataModels.NoticeModel;

public class NoticeClick {
private NoticeModel noticeToDisp;
	public NoticeClick(NoticeModel noticeClicked) {
		noticeToDisp = noticeClicked;
	}

	public NoticeModel getNoticemodel(){
		return noticeToDisp;
	}
}

