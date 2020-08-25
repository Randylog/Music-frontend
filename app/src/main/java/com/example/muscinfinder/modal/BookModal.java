package com.example.muscinfinder.modal;

public class BookModal {

    private String productid;
    private String userid;
    private String dates;
    private String times;
    private String phone;
//
    public BookModal(String productid, String userid, String dates, String times, String phone) {
        this.productid = productid;
        this.userid = userid;
        this.dates = dates;
        this.times = times;
        this.phone = phone;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
