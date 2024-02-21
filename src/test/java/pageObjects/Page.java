package pageObjects;

import managers.WebDrivenSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Page {
    protected  WebDriver driver = WebDrivenSingleton.getInstance();


    public void writeText(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void clickOn(WebElement element) {
            element.click();
    }
}
