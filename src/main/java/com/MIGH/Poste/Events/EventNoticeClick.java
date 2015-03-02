package com.MIGH.Poste.Events;

import com.MIGH.Poste.DataModels.EventNoticeModel;


/**
 * Created by Kobby on 25-Dec-14.
 */
public class EventNoticeClick {
    private EventNoticeModel eventNoticeToDisp;
    public EventNoticeClick(EventNoticeModel eventNoticeClicked) {
        eventNoticeToDisp = eventNoticeClicked;

    }

    public EventNoticeModel getEventNoticemodel(){
        return eventNoticeToDisp;
    }

    public String getEventID(){
        return eventNoticeToDisp.InfoID;
    }
}
