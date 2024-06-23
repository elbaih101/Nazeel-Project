package alia.nazeel.kendoelements;

import alia.nazeel.templates.CustomWebElement;
import org.openqa.selenium.WebElement;

public class KendoSwitch extends CustomWebElement {
    WebElement zwitch;


    public KendoSwitch(WebElement zwitch) {
        super(zwitch);
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
