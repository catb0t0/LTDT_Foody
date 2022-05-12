package hcmute.edu.vn.foody_04.Beans;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private Integer id;
    private String name;
    private String address;
    private String time;
    private byte[] image;

    public Restaurant(Integer id, String name, String address, String time, byte[] image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.time = time;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
