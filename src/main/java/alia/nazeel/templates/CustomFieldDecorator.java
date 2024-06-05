package alia.nazeel.templates;

import alia.nazeel.kendoelements.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this Class Extends the custom Field Decorator then can be used inside the BasePom in replace of the Default Field decorator
 * PageFactory.initElements(new CustomFieldDecorator(new DefaultElementLocatorFactory(driver)), this);
 * enabling the user of creating custom FindBys Annotations for The Custom WebElements
 */
public class CustomFieldDecorator extends DefaultFieldDecorator
{
    private static final Logger logger = LoggerFactory.getLogger(CustomFieldDecorator.class);
    // Just add the new Custom Web Element Class to this List and it Should work like charm
    //Note The Custom WebElement Should have a constructor that takes a WebElement
    List<Class<?>> elements = Collections.unmodifiableList(new ArrayList<>()
    {{
        add(KendoGrid.class);
        add(KendoDateTimePicker.class);
        add(KendoButtonGroup.class);
        add(KendoComboBox.class);
        add(ChipsMultiselect.class);
        add(KendoGrid.class);
        add(CustomWebElement.class);
        add(KendoGrid.class);
        add(SwalPopUp.class);
        add(MultiLangTextField.class);
        add(KendoSwitch.class);
        add(Select.class);
    }});


    public CustomFieldDecorator(ElementLocatorFactory factory)
    {
        super(factory);
    }

    /**
     *
     * @param loader The class loader that was used for the page object
     * @param field The field that may be decorated.
     * @return decorated Object of the element locater and theClass of custom elment
     */
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
            for (Class<?> element : elements)
            {
                if (field.getType().equals(element))
                {
                    return CustomProxy(element, loader, locator);
                }
            }
        }
        return super.decorate(loader, field);
    }
    /**
     * This method creates a proxy instance of the specified class using the provided WebElement as an argument.
     * It finds a constructor that takes a WebElement as an argument and creates a new instance using that constructor.
     *
     * @param clazz  The class of the proxy instance to be created.
     * @param loader The class loader to be used for loading the specified class.
     * @param locator The locator that provides the WebElement to be used for creating the proxy instance.
     * @return A new instance of the specified class, initialized with the provided WebElement.
     * @see ElementLocator
     * @see WebElement
     */
    private <T> T CustomProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator)
    {
        try
        {
            WebElement proxy = proxyForLocator(loader, locator);

            // Find a constructor that takes a WebElement as an argument
            Constructor<T> constructor = clazz.getConstructor(WebElement.class);

            // Create a new instance using the found constructor
            return constructor.newInstance(proxy);
        } catch (Exception e)
        {
            // Handle exceptions such as NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
          logger.error(e.getMessage(), e);
        throw new RuntimeException("Failed to create proxy instance", e);
        }
    }
}

