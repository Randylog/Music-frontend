package com.example.muscinfinder.modal;

public class UpdateModal {
    private String _id;
    private String fullname;
    private String phone;

    public UpdateModal(String _id, String fullname, String phone) {
        this._id = _id;
        this.fullname = fullname;
        this.phone = phone;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}