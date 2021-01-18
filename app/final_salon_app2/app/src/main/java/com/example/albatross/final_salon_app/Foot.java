package com.example.albatross.final_salon_app;
public class Foot {

    private String salon_service_id;
    private String service_type;
    private String service_name;
    private String service_price;
    private String service_duration;

    public Foot(
                    String salon_service_id,
                    String service_type,
                    String service_name,
                    String service_price,
                    String service_duration) {
        this.salon_service_id = salon_service_id;
        this.service_type =  service_type;
        this.service_name = service_name;
        this.service_price = service_price;
        this.service_duration = service_duration;
    }


    public String getsalon_service_id() {
        return salon_service_id;
    }
    public void setsalon_service_id(String salon_service_id) { this.salon_service_id = salon_service_id;  }

    public String getservice_type() {
        return service_type;
    }
    public void setcservice_type(String service_type) { this.service_type = service_type;  }

    public String getservice_name() {
        return service_name;
    }
    public void setservice_name(String service_name) { this.service_name = service_name;  }

    public String getservice_price() {
        return service_price;
    }
    public void setservice_price(String service_price) { this.service_price = service_price;  }

    public String getservice_duration() {
        return service_duration;
    }
    public void setservice_duration(String service_duration) { this.service_duration = service_duration;  }




}