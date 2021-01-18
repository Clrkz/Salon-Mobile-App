package com.example.albatross.final_salon_app;
public class History {



    private String id;
    private String dateadded;
    private String name;
    private String member;
    private String service;
    private String price;
    private String duration;
    private String datetime;
    private String code;
    private String image;
    private String uname;
    private String accepted;

    public History(
                    String id,
                    String dateadded,
                    String name,
                    String member,
                    String service,
                    String price,
                    String duration,
                    String datetime,
                    String code,
                    String image,
                    String uname,
                    String accepted) {
        this.id = id;
        this.dateadded =  dateadded;
        this.name = name;
        this.member = member;
        this.service = service;
        this.price = price;
        this.duration = duration;
        this.datetime = datetime;
        this.code = code;
        this.image = image;
        this.uname = uname;
        this.accepted = accepted;
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

    public String getmember() {
        return member;
    }
    public void setmember(String member) { this.member = member;  }

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

    public String getdatetime() {
        return datetime;
    }
    public void setdatetime(String datetime) { this.datetime = datetime;  }


    public String getcode() {
        return code;
    }
    public void setcode(String code) { this.code = code;  }


    public String getimage() {
        return image;
    }
    public void setimage(String image) { this.image = image;  }



    public String getuname() {
        return uname;
    }
    public void setuname(String uname) { this.uname = uname;  }


    public String getaccepted() {
        return accepted;
    }
    public void setaccepted(String accepted) { this.accepted = accepted;  }


}