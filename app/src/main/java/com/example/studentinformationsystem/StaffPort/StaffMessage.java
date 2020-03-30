package com.example.studentinformationsystem.StaffPort;

public class StaffMessage {

    private String ttRegNum,ttDate,ttMsgFor,ttTitle,ttMessage;

    public StaffMessage() {
    }

    public StaffMessage(String ttRegNum, String ttDate, String ttMsgFor, String ttTitle, String ttMessage) {
        this.ttRegNum = ttRegNum;
        this.ttDate = ttDate;
        this.ttMsgFor = ttMsgFor;
        this.ttTitle = ttTitle;
        this.ttMessage = ttMessage;
    }

    public String getTtRegNum() {
        return ttRegNum;
    }

    public void setTtRegNum(String ttRegNum) {
        this.ttRegNum = ttRegNum;
    }

    public String getTtDate() {
        return ttDate;
    }

    public void setTtDate(String ttDate) {
        this.ttDate = ttDate;
    }

    public String getTtMsgFor() {
        return ttMsgFor;
    }

    public void setTtMsgFor(String ttMsgFor) {
        this.ttMsgFor = ttMsgFor;
    }

    public String getTtTitle() {
        return ttTitle;
    }

    public void setTtTitle(String ttTitle) {
        this.ttTitle = ttTitle;
    }

    public String getTtMessage() {
        return ttMessage;
    }

    public void setTtMessage(String ttMessage) {
        this.ttMessage = ttMessage;
    }

    public String display(){
        return this.ttDate+"\n Message: \t"+ttMessage;
    }
}
