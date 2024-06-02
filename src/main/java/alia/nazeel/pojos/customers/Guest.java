package alia.nazeel.pojos.customers;

import org.openqa.selenium.WebElement;

public class Guest {
    private String name;
    private String idNo;
    private String idType;
    private String phone;
    private String nationality;
    private String status;
    private String compNote;
    private String propNote;
    private String docName;
    private WebElement Document;

    public Guest(String name, String idNo, String idType, String phone, String nationality, String status) {
        this.name = name;
        this.idNo = idNo;
        this.idType = idType;
        this.phone = phone;
        this.nationality = nationality;
        this.status = status;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public WebElement getDocument() {
        return Document;
    }

    public void setDocument(WebElement document) {
        Document = document;
    }

    public String getCompNote() {
        return compNote;
    }

    public void setCompNote(String compNote) {
        this.compNote = compNote;
    }

    public String getPropNote() {
        return propNote;
    }

    public void setPropNote(String propNote) {
        this.propNote = propNote;
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
