package com.example.assignment07;

public class MemberModel {

    private String CardNo;
    private String MemberName;
    private String MemberAddress;
    private String MemberPhone;
    private String Unpaiddues;

    // creating getter and setter methods
    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String MemberName) {
        this.MemberName= MemberName;
    }

    public String getMemberAddress() {
        return MemberAddress;
    }

    public void setMemberAddress(String MemberAddress) {
        this.MemberAddress = MemberAddress;
    }

    public String getMemberPhone() {
        return MemberPhone;
    }

    public void setMemberPhone(String MemberPhone) {
        this.MemberPhone = MemberPhone;
    }

    public String getUnpaiddues() {
        return Unpaiddues;
    }

    public void setUnpaiddues(String Unpaiddues) {
        this.Unpaiddues = Unpaiddues;
    }

    // constructor
    public MemberModel(String CardNo, String MemberName, String MemberAddress, String MemberPhone) {
        this.CardNo= CardNo;
        this.MemberName = MemberName;
        this.MemberAddress = MemberAddress;
        this.MemberPhone = MemberPhone;
        this.Unpaiddues= Unpaiddues;
    }
}

