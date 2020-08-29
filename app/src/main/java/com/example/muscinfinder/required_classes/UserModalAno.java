package com.example.muscinfinder.required_classes;

public class UserModalAno {

    private String _id;
    private String fullname;
    private String email;
    private String phone;
    private String password;

    public UserModalAno(String _id, String fullname, String email, String phone, String password) {
        this._id = _id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}