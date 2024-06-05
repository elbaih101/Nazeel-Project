package alia.nazeel.kendoelements;

import org.openqa.selenium.WebElement;

public class KendoSwitch {
    WebElement zwitch;


    public KendoSwitch(WebElement zwitch) {
        this.zwitch = zwitch;
    }


    public boolean isOn(){
        return zwitch.getAttribute("class").contains("k-switch-on");
    }

    public void switchOn(){
        if(!isOn())
            zwitch.click();
    }
    public void switchOf(){
        if(isOn())
            zwitch.click();
    }
    public void click(){
        zwitch.click();
    }
}
