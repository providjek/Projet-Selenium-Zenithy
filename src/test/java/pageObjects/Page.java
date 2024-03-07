package pageObjects;

import managers.WebDrivenSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class Page {
    protected  WebDriver driver = WebDrivenSingleton.getInstance();


    protected Page(){
        PageFactory.initElements(this.driver, this);
    }
    public void writeText(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void clickOn(WebElement element) {
            element.click();
    }
}
