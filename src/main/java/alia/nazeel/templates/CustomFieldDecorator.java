package alia.nazeel.templates;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * this Class Extends the custom Field Decorator then can be used inside the BasePom in replace of the Default Field decorator
 * PageFactory.initElements(new CustomFieldDecorator(new DefaultElementLocatorFactory(driver)), this);
 * enabling the user of creating custom FindBys Annotations for The Custom WebElements
 */
public class CustomFieldDecorator extends DefaultFieldDecorator {
    private static final Logger logger = LoggerFactory.getLogger(CustomFieldDecorator.class);
    //any custom Element Class have to extend from the CustomWebElement Class
    //ignore below comments are to be deleted

    // Just add the new Custom Web Element Class to this List and it Should work like charm
    //Note The Custom WebElement Should have a constructor that takes a WebElement
//    List<Class<?>> elementsClasses = Collections.unmodifiableList(new ArrayList<>() {{
//        add(KendoGrid.class);
//        add(KendoDateTimePicker.class);
//        add(KendoButtonGroup.class);
//        add(KendoComboBox.class);
//        add(ChipsMultiselect.class);
//        add(KendoGrid.class);
//        add(CustomWebElement.class);
//        add(KendoGrid.class);
//        add(SwalPopUp.class);
//        add(MultiLangTextField.class);
//        add(KendoSwitch.class);
//        add(Select.class);
//    }});


    public CustomFieldDecorator(ElementLocatorFactory factory) {
        super(factory);
    }

    /**
     * @param loader The class loader that was used for the page object
     * @param field  The field that may be decorated.
     * @return decorated Object of the element locater and theClass of custom elment
     */
    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (field.isAnnotationPresent(FindBy.class)) {
            ElementLocator locator = factory.createLocator(field);

            if (locator == null) {
                return null;
            }

            // Handle custom elements
            if (CustomWebElement.class.isAssignableFrom(field.getType())) {
                return CustomProxy(field.getType(), loader, locator);
            }

            // Handle List<CustomWebElement>
            else if (List.class.isAssignableFrom(field.getType())) {
                // Extract the generic type of the List
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType parameterizedType) {
                    Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
                    Class<?> type = (Class<?>) listType;
                    if (CustomWebElement.class.isAssignableFrom(type)) {
                        return CustomListProxy(type, loader, locator);
                    }

                }
            }
        }

        return super.decorate(loader, field);
    }

    /**
     * This method creates a proxy instance of the specified class using the provided WebElement as an argument.
     * It finds a constructor that takes a WebElement as an argument and creates a new instance using that constructor.
     *
     * @param clazz   The class of the proxy instance to be created.
     * @param loader  The class loader to be used for loading the specified class.
     * @param locator The locator that provides the WebElement to be used for creating the proxy instance.
     * @return A new instance of the specified class, initialized with the provided WebElement.
     * @see ElementLocator
     * @see WebElement
     */
    private <T> T CustomProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator) {
        try {
            WebElement proxy = proxyForLocator(loader, locator);

            // Find a constructor that takes a WebElement as an argument
            Constructor<T> constructor = clazz.getConstructor(WebElement.class);

            // Create a new instance using the found constructor
            return constructor.newInstance(proxy);
        } catch (Exception e) {
            // Handle exceptions such as NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to create proxy instance", e);
        }
    }

    private <T> List<T> CustomListProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator) {
        try {
            List<WebElement> proxy = proxyForListLocator(loader, locator);
            List<T> customElements = new ArrayList<>();
            // Find a constructor that takes a WebElement as an argument
            Constructor<T> constructor = clazz.getConstructor(WebElement.class);
            for (WebElement element : proxy) {
                customElements.add(constructor.newInstance(element));
            }
            // Create a new instance using the found constructor
            return customElements;
        } catch (Exception e) {
            // Handle exceptions such as NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to create proxy instance", e);
        }
    }
}

