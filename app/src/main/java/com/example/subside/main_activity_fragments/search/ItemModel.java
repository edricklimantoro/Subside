package com.example.subside.main_activity_fragments.search;

public class ItemModel {
    String name;
    String major;
    String id;
    String ig;
    String email;
    String phone;
    String linkedin;
    int image;


    public ItemModel(String name, String major, int image, String id, String ig, String email, String phone, String linkedin) {
        this.name=name;
        this.major =major;
        this.image=image;
        this.id=id;
        this.ig=ig;
        this.email=email;
        this.phone=phone;
        this.linkedin=linkedin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIg() {
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}
