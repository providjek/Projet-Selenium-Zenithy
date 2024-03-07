package pageObjects;

import managers.WebDrivenSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Tools;


public class ConfirmationPage extends Page {
    @FindBy(css = "div.Alert_iwrp__5q1xH.Alert_success__g430w")
    private WebElement alerteMsg;
    private final By ALERT_MSG = By.cssSelector("div.Alert_iwrp__5q1xH.Alert_success__g430w");
    @FindBy(css = "div.Alert_iwrp__5q1xH.Alert_success__g430w")
    private WebElement confMsgEl;
    @FindBy(css = "div.Alert_iwrp__5q1xH.Alert_success__g430w")
    private WebElement msgTextContEL;
    private WebDriver driver = WebDrivenSingleton.getInstance();
    private Tools tools = new Tools();


    public void waitVisibilityofAlerte(){
        this.tools.waitVisibilityofElement(this.ALERT_MSG);
    }

    public String getConfMsgText(){
        return this.msgTextContEL.getText();
    }



}
