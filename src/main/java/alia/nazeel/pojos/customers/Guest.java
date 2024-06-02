package alia.nazeel.pojos.customers;

public class Guest {
    private String name;
    private String idNo;
    private String idType;
    private String phone;
    private String nationality;
    private String status;

    public Guest(String name, String idNo, String idType, String phone, String nationality, String status) {
        this.name = name;
        this.idNo = idNo;
        this.idType = idType;
        this.phone = phone;
        this.nationality = nationality;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
