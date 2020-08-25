package com.example.muscinfinder.modal;

public class InstituteModal {
    private String _id;
    private String name;
    private String image;
    private String cost;
    private String description;
    private String location;
    private String phone;
    private String latitude;
    private String longitude;

    public InstituteModal(String _id, String name, String image, String cost, String description,
                          String location, String phone, String latitude, String longitude) {
        this._id = _id;
        this.name = name;
        this.image = image;
        this.cost = cost;
        this.description = description;
        this.location = location;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}