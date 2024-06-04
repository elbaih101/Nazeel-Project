package alia.nazeel.pojos;

public class Vendor {
    private String name;
    private String phone;
    private String email;
    private String vat;
    private String description;
    private String status;
    private String crNo;
    private String postalCode;
    private String address;

    public String getName() {
        return name;
    }

    public Vendor(String name, String phone, String email, String vat, String description, String status, String crNo, String postalCode, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.vat = vat;
        this.description = description;
        this.status = status;
        this.crNo = crNo;
        this.postalCode = postalCode;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCrNo() {
        return crNo;
    }

    public void setCrNo(String crNo) {
        this.crNo = crNo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
