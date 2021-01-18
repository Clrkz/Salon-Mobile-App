package com.example.albatross.final_salon_app;
public class Booking {

    private String id;
    private String dateadded;
    private String name;
    private String status;
    private String service;
    private String price;
    private String duration;
    private String time;
    private String date;
    private String code;
    private String image;

    public Booking(
                    String id,
                    String dateadded,
                    String name,
                    String status,
                    String service,
                    String price,
                    String duration,
                    String time,
                    String date,
                    String code,
                    String image) {
        this.id = id;
        this.dateadded =  dateadded;
        this.name = name;
        this.status = status;
        this.service = service;
        this.price = price;
        this.duration = duration;
        this.time = time;
        this.date = date;
        this.code = code;
        this.image = image;
    }


    public String getid() {
        return id;
    }
    public void setid(String id) { this.id = id;  }

    public String getdateadded() {
        return dateadded;
    }
    public void setdateadded(String dateadded) { this.dateadded = dateadded;  }

    public String getname() {
        return name;
    }
    public void setname(String name) { this.name = name;  }

    public String getstatus() {
        return status;
    }
    public void setstatus(String status) { this.status = status;  }

    public String getservice() {
        return service;
    }
    public void setservice(String service) { this.service = service;  }

    public String getprice() {
        return price;
    }
    public void setprice(String price) { this.price = price;  }

    public String getduration() {
        return duration;
    }
    public void setduration(String duration) { this.duration = duration;  }

    public String gettime() {
        return time;
    }
    public void settime(String time) { this.time = time;  }

    public String getdate() {
        return date;
    }
    public void setdate(String date) { this.date = date;  }


    public String getcode() {
        return code;
    }
    public void setcode(String code) { this.code = code;  }


    public String getimage() {
        return image;
    }
    public void setimage(String image) { this.image = image;  }


}