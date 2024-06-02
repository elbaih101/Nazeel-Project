package alia.nazeel.templates;

import alia.nazeel.kendoelements.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * this Class Extends the custom Field Decorator then can be used inside the BasePom in replace of the Default Field decorator
 * PageFactory.initElements(new CustomFieldDecorator(new DefaultElementLocatorFactory(driver)), this);
 * enabling the user of creating custom FindBys Annotations for The Custom WebElements
 */
public class CustomFieldDecorator extends DefaultFieldDecorator
{

    public CustomFieldDecorator(ElementLocatorFactory factory)
    {
        super(factory);
    }

    @Override
    public Object decorate(ClassLoader loader, Field field)
    {
        if (field.isAnnotationPresent(FindBy.class))
        {
            ElementLocator locator = factory.createLocator(field);

            if (locator == null)
            {
                return null;
            }
            /*
             * Here We enter the required new Custom Elements and Then Get their proxies
             **/
            if (field.getType().equals(CustomWebElement.class))
            {
                return proxyForCustomWebElement(loader, locator);
            } else if (field.getType().equals(KendoDateTimePicker.class))
            {
                return proxyForKendoDateTimePicker(loader, locator);
            } else if (field.getType().equals(KendoButtonGroup.class))
            {
                return proxyForKendoButtonGroup(loader, locator);
            } else if (field.getType().equals(KendoComboBox.class))
            {
                return proxyForKendoComboBox(loader, locator);
            } else if (field.getType().equals(ChipsMultiselect.class))
            {
                return proxyForChipsMultiselect(loader, locator);
            }else if (field.getType().equals(KendoGrid.class))
            {
                return proxyForKendoGrid(loader, locator);
            }else if (field.getType().equals(KendoDropDownList.class))
            {
                return proxyForKendoDropDownList(loader, locator);
            }else if (field.getType().equals(MultiLangTextField.class))
            {
                return proxyForMultiLangTextField(loader, locator);
            }else if (field.getType().equals(KendoMultiSelect.class))
            {
                return proxyForKendoMultiSelect(loader, locator);
            } else if (field.getType().equals(WebElement.class))
            {
                return proxyForWebElement(loader, locator);
            }
        }
        return super.decorate(loader, field);
    }

    private Object proxyForMultiLangTextField(ClassLoader loader, ElementLocator locator) {
        WebElement proxy =  proxyForLocator(loader, locator);
        return new MultiLangTextField(proxy);
    }

    /**
     * Creates a KendoMultiSelect proxy for handling the annotations of Find By.
     *
     * @param loader  the class loader handling the Pom Page
     * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
     * @return a new instance of KendoMultiSelect with the provided WebElement proxy
     */
    private Object proxyForKendoMultiSelect(ClassLoader loader, ElementLocator locator)
    {
        WebElement proxy =  proxyForLocator(loader, locator);
        return new KendoMultiSelect(proxy);
    }

    /**
 * Creates a KendoGrid proxy for handling the annotations of Find By.
 *
 * @param loader  the class loader handling the Pom Page
 * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
 * @return a new instance of KendoGrid with the provided WebElement proxy
 */
private KendoGrid proxyForKendoGrid(ClassLoader loader, ElementLocator locator)
{
    WebElement proxy = proxyForLocator(loader, locator);
    return new KendoGrid(proxy);
}

    /**
 * Creates a KendoDropDownList proxy for handling the annotations of Find By.
 *
 * @param loader  the class loader handling the Pom Page
 * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
 * @return a new instance of KendoDropDownList with the provided WebElement proxy
 */
private KendoDropDownList proxyForKendoDropDownList(ClassLoader loader, ElementLocator locator)
{
    WebElement proxy =  proxyForLocator(loader, locator);
    return new KendoDropDownList(proxy);
}

    /**
 * Creates a KendoComboBox proxy for handling the annotations of Find By.
 *
 * @param loader  the class loader handling the Pom Page
 * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
 * @return a new instance of KendoComboBox with the provided WebElement proxy
 */
private KendoComboBox proxyForKendoComboBox(ClassLoader loader, ElementLocator locator)
{
    WebElement proxy =  proxyForLocator(loader, locator);
    return new KendoComboBox(proxy);
}

    /**
 * Creates a KendoButtonGroup proxy for handling the annotations of Find By.
 *
 * @param loader  the class loader handling the Pom Page
 * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
 * @return a new instance of KendoButtonGroup with the provided WebElement proxy
 */
private KendoButtonGroup proxyForKendoButtonGroup(ClassLoader loader, ElementLocator locator)
{
    WebElement proxy = proxyForLocator(loader, locator);
    return new KendoButtonGroup(proxy);
}

    /**
 * Custom ChipsMultiselect Proxy For Handling the Annotations of Find By
 *
 * @param loader  the class loader handling the Pom Page
 * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
 * @return a new instance of ChipsMultiselect with the provided WebElement proxy
 */
private ChipsMultiselect proxyForChipsMultiselect(ClassLoader loader, ElementLocator locator)
{
    WebElement proxy = proxyForLocator(loader, locator);
    return new ChipsMultiselect(proxy);
}

    /**
     * Custom WebElement Proxy For Handling the Annotations of Find By
     *
     * @param loader  the class loader handling the Pom Page
     * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
     * @return a custom Web element
     */
    private CustomWebElement proxyForCustomWebElement(ClassLoader loader, ElementLocator locator)
    {
        WebElement proxy =  proxyForLocator(loader, locator);
        return new CustomWebElement(proxy);
    }

    /**
     * Custom KendoDateTimePicker Proxy For Handling the Annotations of Find By
     *
     * @param loader  the class loader handling the Pom Page
     * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
     * @return a new instance of KendoDateTimePicker with the provided WebElement proxy
     */
    private KendoDateTimePicker proxyForKendoDateTimePicker(ClassLoader loader, ElementLocator locator)
    {
        WebElement proxy = proxyForLocator(loader, locator);
        return new KendoDateTimePicker(proxy);
    }

    /**
     * Creates a WebElement proxy for handling the annotations of Find By.
     *
     * @param loader  the class loader handling the Pom Page
     * @param locator the Locator Defined in the field of the find by annotation @FindBy(<Css="">)
     * @return a new instance of WebElement with the provided WebElement proxy
     */
    private WebElement proxyForWebElement(ClassLoader loader, ElementLocator locator)
    {
        return  proxyForLocator(loader, locator);
    }
}

