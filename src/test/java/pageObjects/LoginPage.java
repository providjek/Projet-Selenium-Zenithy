package pageObjects;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.WebDrivenSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.yaml.snakeyaml.Yaml;
import runners.AuthRunner;
import utils.Tools;

import java.io.InputStream;
import java.time.Duration;
import java.util.Map;

public class LoginPage extends Page{

    private Yaml yaml = new Yaml();
    private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
    private Map<String, String> obj = yaml.load(inputStream);
    private WebDriver driver = WebDrivenSingleton.getInstance();
    private String LOGIN_URL = obj.get("log-url");
    @FindBy(id = "modalusername")
    private WebElement emailInputLog;
    @FindBy(id = "current-password")
    private WebElement passwdInputLog;
    @FindBy(css = "div.LoginModal_cta_bottom_box_button_login__5Fbwv button")
    private WebElement btnLog;
    private WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));

    @FindBy(css = "div.Alert_iwrp__5q1xH.Alert_danger__Wsdhv")
    private WebElement msgAlerte;
    private By MSG_ALERTE = By.cssSelector("div.Alert_iwrp__5q1xH.Alert_danger__Wsdhv");
    private Tools tools = new Tools();
    private HomePage homePage = new HomePage();

    public void getLogPage(){
        this.driver.get(this.LOGIN_URL);
    }

    public void writeEmail(String email){
        super.writeText(this.emailInputLog, email);
    }
    public void writePasswd(String pass){
        super.writeText(this.passwdInputLog, pass);
    }
    public void clickOnBtnLogin(){
        super.clickOn(this.btnLog);
    }

    public String getMsgAlerte(){
        return this.msgAlerte.getText();
    }

    public String getLOGIN_URL() {
        return LOGIN_URL;
    }

    public WebElement getEmailInputLog() {
        return emailInputLog;
    }

    public WebElement getPasswdInputLog() {
        return passwdInputLog;
    }

    public WebElement getBtnLog() {
        return btnLog;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public By getMSG_ALERTE() {
        return MSG_ALERTE;
    }
}
