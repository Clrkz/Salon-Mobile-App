package com.example.albatross.final_salon_app;
public class Customers {

    private String guest_id_PK;
    private String g_firstname;
    private String g_lastname;
    private String g_picture;
    private String g_date_time_added;
    private String g_contact_number;
    private String g_fb_name;
    private String g_gmail;
    private String g_password;
    private String g_status;
    private String g_gender;

    public Customers(
            String guest_id_PK,
            String g_firstname,
            String g_lastname,
            String g_picture,
            String g_date_time_added,
            String g_contact_number,
            String g_fb_name,
            String g_gmail,
            String g_password,
            String g_status,
            String g_gender) {
        this.guest_id_PK = guest_id_PK;
        this.g_firstname =  g_firstname;
        this.g_lastname = g_lastname;
        this.g_picture = g_picture;
        this.g_date_time_added = g_date_time_added;
        this.g_contact_number = g_contact_number;
        this.g_fb_name = g_fb_name;
        this.g_gmail = g_gmail;
        this.g_password = g_password;
        this.g_status = g_status;
        this.g_gender = g_gender;
    }


    public String getguest_id_PK() {
        return guest_id_PK;
    }
    public void setguest_id_PK(String guest_id_PK) { this.guest_id_PK = guest_id_PK;  }

    public String getg_firstname() {
        return g_firstname;
    }
    public void setg_firstname(String g_firstname) { this.g_firstname = g_firstname;  }

    public String getg_lastname() {
        return g_lastname;
    }
    public void set_lastname(String g_lastname) { this.g_lastname = g_lastname;  }

    public String getg_picture() {
        return g_picture;
    }
    public void setg_picture(String g_picture) { this.g_picture = g_picture;  }

    public String getg_date_time_added() {
        return g_date_time_added;
    }
    public void setg_date_time_added(String g_date_time_added) { this.g_date_time_added = g_date_time_added;  }


    public String getg_contact_number() {
        return g_contact_number;
    }
    public void setg_contact_number(String g_contact_number) { this.g_contact_number = g_contact_number;  }


    public String getg_fb_name() {
        return g_fb_name;
    }
    public void setg_fb_name(String g_fb_name) { this.g_fb_name = g_fb_name;  }


    public String getg_gmail() {
        return g_gmail;
    }
    public void setg_gmail(String g_gmail) { this.g_gmail = g_gmail;  }


    public String getg_password() {
        return g_password;
    }
    public void setg_password(String g_password) { this.g_password = g_password;  }


    public String getg_status() {
        return g_status;
    }
    public void setg_status(String g_status) { this.g_status = g_status;  }

    public String getg_gender() {
        return g_gender;
    }
    public void setg_gender(String g_gender) { this.g_gender = g_gender;  }



}