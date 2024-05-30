package alia.nazeel.kendoelements;

import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.List;

public class KendoDateTimePicker implements WebElement
{


    WebElement kendoDateTimePicker;
    WebElement calender;
    CustomWebDriverWait wait = new CustomWebDriverWait(Duration.ofSeconds(5));
    final private By inputBy = By.xpath(".//input");
    final private By dateTimePickerButton = By.xpath(".//span[@class=\"k-select\"]");
    final private By calendarBy = By.xpath("//div[contains(@class,\"k-datetime-container\")]");
    final private By timeSelector = By.cssSelector("kendo-timeselector");
    final private By setButton = By.cssSelector("button.k-time-accept");
    final private By cancelButton = By.cssSelector("button.k-time-cancel");

    final private By dateTimeContainorBy = By.cssSelector("div.k-datetime-container");
    String format = "dd/MM/YYYY HH:mm a";
    KendoButtonGroup buttonGroup;
    WebElement container;
    DateTimeFormatter formatter = DateTimeFormat.forPattern(format);

    public KendoDateTimePicker(WebElement kendoDateTimePicker)
    {
        this.kendoDateTimePicker = kendoDateTimePicker;
    }


    public KendoDateTimePicker()
    {
    }

    public void setDateTime(String dateTime)
    {
        WebElement dateTimeInput = getDateTimeInput();
        dateTimeInput.click();
        dateTime = dateTime.replaceAll("[/\\-: ]", "");
        dateTimeInput.sendKeys(Keys.HOME);
        dateTimeInput.sendKeys(dateTime);
    }

    public void selectDateTime(String dateTime)
    {
        DateTime datetime1 = DateTime.parse(dateTime, formatter);
        openDateTimeContainer();
        buttonGroup = new KendoButtonGroup(calender.findElement(By.cssSelector("div.k-datetime-buttongroup")));
        selectYear(datetime1.toString(DateTimeFormat.forPattern("YYYY")));
        selectMonth(datetime1.toString(DateTimeFormat.forPattern("MMM")));
        selectDate(datetime1.toString(DateTimeFormat.forPattern("MMMM dd, YYYY")));
        buttonGroup.selectButtonByName("Time");
        selectTime(datetime1.toString(DateTimeFormat.forPattern("HH")), datetime1.toString(DateTimeFormat.forPattern("mm")), datetime1.toString(DateTimeFormat.forPattern("a")));
        clickSetButton();
    }

    public void openDateTimeContainer()
    {
        getKendoDateTimePickerButton().click();
        wait.waitLoading();
        calender = kendoDateTimePicker.findElement(calendarBy);
    }

    private WebElement getKendoDateTimePickerButton()
    {
        return kendoDateTimePicker.findElement(dateTimePickerButton);
    }

    private WebElement getDateTimeInput()
    {
        return kendoDateTimePicker.findElement(inputBy);
    }


    void selectYear(String year)
    {

        WebElement yearButton = calender.findElement(By.cssSelector("span.k-button"));
        int thisYear = Integer.parseInt(yearButton.getText().replaceAll("\\D", ""));
        int targetYear = Integer.parseInt(year);
        yearButton.click();
        String yearXPath = String.format("//td[@role='gridcell' and contains(@title, '%s')]", year);
        String targetYearXPath = String.format("//span[@class='k-button k-bare k-title' and contains(text(),'%s')]", year);
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        do
        {
            switch (Integer.compare(targetYear, thisYear))
            {
                case -1 -> Utils.scroll("up", calender);
                case 1 -> Utils.scroll("down", calender);
            }

        } while ( Utils.isElementInvisible(By.xpath(targetYearXPath),DriverManager.getDriver()));
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement yearElement = kendoDateTimePicker.findElement(By.xpath(targetYearXPath));
        // yearElement.click();

    }




    void selectMonth(String month)
    {
        String monthXPath = String.format("//td[@role='gridcell' and contains(@title, '%s')]/span", month);
        WebElement monthElement = kendoDateTimePicker.findElement(By.xpath(monthXPath));
        monthElement.click();
    }

    void selectDate(String date)
    {
        String dateXPath = String.format("//td[@role='gridcell' and contains(@title, '%s')]", date);
        WebElement dateElement = calender.findElement(By.xpath(dateXPath));
        dateElement.click();
    }

    void selectTime(String hour, String minute, String period)
    {
        // hour, minute should be in 'hh', 'mm' format, e.g., '12', '00'
        // period should be 'AM' or 'PM'
        String hourXPath = String.format("//kendo-timelist[@data-timelist-index='0']//li/span[text()='%s']", hour);
        String minuteXPath = String.format("//kendo-timelist[@data-timelist-index='2']//li/span[text()='%s']", minute);
        String periodXPath = String.format("//kendo-timelist[@data-timelist-index='4']//li/span[text()='%s']", period);

        WebElement hourElement = calender.findElement(By.xpath(hourXPath));
        WebElement minuteElement = calender.findElement(By.xpath(minuteXPath));
        WebElement periodElement = calender.findElement(By.xpath(periodXPath));

        hourElement.click();
        minuteElement.click();
        periodElement.click();
    }

    void clickSetButton()
    {
        calender.findElement(setButton).click();
    }

    void clickCancelButton()
    {
        calender.findElement(cancelButton).click();
    }

    @Override
    public void click()
    {

    }

    @Override
    public void submit()
    {

    }

    @Override
    public void sendKeys(CharSequence... keysToSend)
    {

    }

    @Override
    public void clear()
    {

    }

    @Override
    public String getTagName()
    {
        return "";
    }

    @Override
    public String getDomProperty(String name)
    {
        return WebElement.super.getDomProperty(name);
    }

    @Override
    public String getDomAttribute(String name)
    {
        return WebElement.super.getDomAttribute(name);
    }

    @Override
    public String getAttribute(String name)
    {
        return "";
    }

    @Override
    public String getAriaRole()
    {
        return WebElement.super.getAriaRole();
    }

    @Override
    public String getAccessibleName()
    {
        return WebElement.super.getAccessibleName();
    }

    @Override
    public boolean isSelected()
    {
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        return false;
    }

    @Override
    public String getText()
    {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by)
    {
        return List.of();
    }

    @Override
    public WebElement findElement(By by)
    {
        return null;
    }

    @Override
    public SearchContext getShadowRoot()
    {
        return WebElement.super.getShadowRoot();
    }

    @Override
    public boolean isDisplayed()
    {
        return false;
    }

    @Override
    public Point getLocation()
    {
        return null;
    }

    @Override
    public Dimension getSize()
    {
        return null;
    }

    @Override
    public Rectangle getRect()
    {
        return null;
    }

    @Override
    public String getCssValue(String propertyName)
    {
        return "";
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException
    {
        return null;
    }
}
