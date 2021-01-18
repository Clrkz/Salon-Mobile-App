package com.example.albatross.final_salon_app;
public class Users {

    private String u_information_id_PK;
    private String u_firstname;
    private String u_lastname;
    private String u_address;
    private String u_picture;
    private String u_dateadded;
    private String u_gender;
    private String u_fb_name;
    private String u_gmail;
    private String u_password;
    private String u_phone_number;
    private String u_availability;
    private String u_status;
    private String u_role;

    public Users(
            String u_information_id_PK,
            String u_firstname,
            String u_lastname,
            String u_address,
            String u_picture,
            String u_dateadded,
            String u_gender,
            String u_fb_name,
            String u_gmail,
            String u_password,
            String u_phone_number,
            String u_availability,
            String u_status,
            String u_role) {
        this.u_information_id_PK = u_information_id_PK;
        this.u_firstname =  u_firstname;
        this.u_lastname = u_lastname;
        this.u_address = u_address;
        this.u_picture = u_picture;
        this.u_dateadded = u_dateadded;
        this.u_gender = u_gender;
        this.u_fb_name = u_fb_name;
        this.u_gmail = u_gmail;
        this.u_password = u_password;
        this.u_phone_number = u_phone_number;
        this.u_availability = u_availability;
        this.u_status = u_status;
        this.u_role = u_role;
    }


    public String getu_information_id_PK() {
        return u_information_id_PK;
    }
    public void setu_information_id_PK(String u_information_id_PK) { this.u_information_id_PK = u_information_id_PK;  }

    public String getu_firstname() {
        return u_firstname;
    }
    public void setu_firstname(String u_firstname) { this.u_firstname = u_firstname;  }

    public String getu_lastname() {
        return u_lastname;
    }
    public void setu_lastname(String u_lastname) { this.u_lastname = u_lastname;  }

    public String getu_address() {
        return u_address;
    }
    public void setu_address(String service_price) { this.u_address = u_address;  }

    public String getu_picture() {
        return u_picture;
    }
    public void setu_picture(String u_picture) { this.u_picture = u_picture;  }


    public String getu_dateadded() {
        return u_dateadded;
    }
    public void setu_dateadded(String u_dateadded) { this.u_dateadded = u_dateadded;  }


    public String getu_gender() {
        return u_gender;
    }
    public void setu_gender(String u_gender) { this.u_gender = u_gender;  }


    public String getu_fb_name() {
        return u_fb_name;
    }
    public void setu_fb_name(String u_fb_name) { this.u_fb_name = u_fb_name;  }


    public String getu_gmail() {
        return u_gmail;
    }
    public void setu_gmail(String u_gmail) { this.u_gmail = u_gmail;  }


    public String getu_password() {
        return u_password;
    }
    public void setu_password(String u_password) { this.u_password = u_password;  }



    public String getu_phone_number() {
        return u_phone_number;
    }
    public void setu_phone_number(String u_phone_number) { this.u_phone_number = u_phone_number;  }


    public String getu_availability() {
        return u_availability;
    }
    public void setu_availability(String u_availability) { this.u_availability = u_availability;  }


    public String getu_status() {
        return u_status;
    }
    public void setu_status(String u_status) { this.u_status = u_status;  }


    public String getu_role() {
        return u_role;
    }
    public void setu_role(String u_role) { this.u_role = u_role;  }




}