package com.example.muscinfinder.modal;

public class FavouriteModal {

    private String productid;
    private String userid;

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

    public FavouriteModal(String productid, String userid) {
        this.productid = productid;
        this.userid = userid;
    }
}
