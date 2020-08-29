package com.example.muscinfinder.required_classes;

//for getting server response
public class SignUpResponse {
    private String status;
    private String token;
    private String _id;
    private String fullname;

    public SignUpResponse(String status, String token, String _id, String fullname) {
        this.status = status;
        this.token = token;
        this._id = _id;
        this.fullname = fullname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}