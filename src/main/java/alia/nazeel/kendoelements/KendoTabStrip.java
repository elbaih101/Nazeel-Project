package alia.nazeel.kendoelements;

import alia.nazeel.templates.CustomWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class KendoTabStrip extends CustomWebElement {


    WebElement strip;
    List<WebElement> tabs;
    WebElement tabPanel;

    public KendoTabStrip(WebElement strip) {
        super(strip);
        this.strip = strip;
        tabs = strip.findElements(By.cssSelector("li"));
    }


    public void selectTabByIndex(int index) {
        tabs.get(index).click();
        setTabPanel();
    }

    private void setTabPanel() {
        tabPanel = strip.findElement(By.cssSelector("div[role=\"tabpanel\"]"));
    }

    public void selectTabByText(String text) {
        tabs.stream().filter(t -> t.getText().equals(text)).findFirst().orElseThrow().click();
        setTabPanel();
    }

    public WebElement getTabPanel() {
        return tabPanel;
    }

}
