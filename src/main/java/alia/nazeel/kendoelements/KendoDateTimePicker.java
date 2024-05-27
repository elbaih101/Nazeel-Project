package alia.nazeel.kendoelements;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.*;

import java.util.Arrays;
import java.util.List;

public class KendoDateTimePicker implements WebElement {


    WebElement kendoDateTimePicker;
    By inputBy = By.xpath(".//input");
    By dateTimePickerButton = By.xpath(".//span[@class=\"k-select\"]");
    By dateTimeContainorBy = By.cssSelector("div.k-datetime-container");
    String format;
    KendoButtonGroup buttonGroup;
    WebElement container ;
    DateTimeFormatter formatter = DateTimeFormat.forPattern(format);

    public KendoDateTimePicker(WebElement kendoDateTimePicker) {
        this.kendoDateTimePicker = kendoDateTimePicker;
    }


    public KendoDateTimePicker() {
    }

    public void setDateTime(String dateTime) {
        WebElement dateTimeInput = getDateTimeInput();
        dateTimeInput.click();
        dateTime = dateTime.replaceAll("[/\\-: ]", "");
        dateTimeInput.sendKeys(Keys.HOME);
        dateTimeInput.sendKeys(dateTime);
    }

    public void selectDateTime(String dateTime) {
        openDateTimeContainer();
        container =kendoDateTimePicker.findElement(dateTimeContainorBy);
        buttonGroup =new KendoButtonGroup(container.findElement(By.cssSelector("div.k-datetime-buttongroup")));
        List <String>spitedDateTime=Arrays.stream(dateTime.split(" ",1)).toList();
        String date= spitedDateTime.getFirst();
        String time = spitedDateTime.getLast();
        
    }

    public void openDateTimeContainer() {
        getKendoDateTimePickerButton().click();
    }

    private WebElement getKendoDateTimePickerButton() {
        return kendoDateTimePicker.findElement(dateTimePickerButton);
    }

    private WebElement getDateTimeInput() {
        return kendoDateTimePicker.findElement(inputBy);
    }

    @Override
    public void click() {

    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getTagName() {
        return "";
    }

    @Override
    public String getDomProperty(String name) {
        return WebElement.super.getDomProperty(name);
    }

    @Override
    public String getDomAttribute(String name) {
        return WebElement.super.getDomAttribute(name);
    }

    @Override
    public String getAttribute(String name) {
        return "";
    }

    @Override
    public String getAriaRole() {
        return WebElement.super.getAriaRole();
    }

    @Override
    public String getAccessibleName() {
        return WebElement.super.getAccessibleName();
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by) {
        return List.of();
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public SearchContext getShadowRoot() {
        return WebElement.super.getShadowRoot();
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public String getCssValue(String propertyName) {
        return "";
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }
}
