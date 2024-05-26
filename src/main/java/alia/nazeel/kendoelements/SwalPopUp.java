package alia.nazeel.kendoelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SwalPopUp {

    WebElement popup;
    By contentBy = By.id("swal2-content");
    By confirmBy=By.xpath(".//button[contains(@class,\"swal2-confirm\")]");
    By cancelBy=By.xpath(".//button[contains(@class,\"swal2-cancel\")]");
    By denyBy =By.xpath(".//button[contains(@class,\"swal2-deny\")]");
    WebElement content;

    public SwalPopUp(WebElement popup) {
        this.popup = popup;
    }

    public WebElement getContent(){
        return content= popup.findElement(contentBy);
    }

    public void confirm(){
        popup.findElement(confirmBy).click();
    }
    public void cancel(){
        popup.findElement(cancelBy).click();
    }
    public void deny(){
        popup.findElement(denyBy).click();
    }


}
