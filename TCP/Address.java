package TCP;

import java.io.Serializable;




public class Address implements Serializable{
    private int id;
    private String code;
    private String addressLine;
    private String city;
    private String postalCode;
    
    private static final long serialVersionUID = 20180801L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + id + ", code=" + code + ", addressLine=" + addressLine + ", city=" + city + ", postalCode=" + postalCode + '}';
    }
    
    
}
