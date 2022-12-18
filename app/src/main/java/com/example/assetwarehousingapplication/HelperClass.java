package com.example.assets_warehousing_app;

public class HelperClass {

    String account_number,bank_name,account_type,ifsc_code;
    int balance;
    String name,email,username,password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperClass(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public HelperClass(String bank_name, String account_number, String ifsc_code, int balance,String account_type) {
        this.bank_name = bank_name;
        this.account_number = account_number;
        this.ifsc_code = ifsc_code;
        this.balance = balance;
        this.account_type=account_type;
    }


    public HelperClass() {
    }
}
