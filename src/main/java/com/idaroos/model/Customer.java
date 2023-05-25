package com.idaroos;

public class Customer {
    private int id;
    private String ss_number;
    private String password;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String address;

    public Customer(int id, String ss_number, String password, String fname, String lname, String email, String phone, String address){
this.id = id;
this.ss_number = ss_number;
this.password = password;
this.fname = fname;
this.lname = lname;
this.email = email;
this.phone = phone;
this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSs_number() {
        return ss_number;
    }

    public void setSs_number(String ss_number) {
        this.ss_number = ss_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
