package pageObjects;

import config.ConfigYaml;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.WebDrivenSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.yaml.snakeyaml.Yaml;
import runners.AuthRunner;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SingUpPage extends Page{

        private Yaml yaml = new Yaml();
        private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
        private Map<String, String> obj = yaml.load(inputStream);
        private String REG_URL = obj.get("sign-url");


        private WebDriver driver = WebDrivenSingleton.getInstance();
        @FindBy(id = "modalusername")
        private  WebElement emailInput ;
        @FindBy(id = "new-password")
        private WebElement passwdInput;
        @FindBy(className = "LoginModal_cta_bottom_box_button_login__5Fbwv")
        private WebElement btnSingUp;
        @FindBy(id = "modal_first_name")
        private WebElement fNameInput;
        @FindBy(id = "modal_last_name")
        private WebElement lNameInput;
        @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[4]/div[1]/div/div[3]/div[1]/button")
        private WebElement btnContinueSignUp;


        private String idTabSing;
        private String idTabYp;
        private String idTabConfEmail;


        public void getSingUpPage(){
            this.driver.get(this.REG_URL);
        }

        public void writeEmail(String email){
            super.writeText(emailInput, email);
        }
        public void writePasswd(String passwd){
            super.writeText(passwdInput, passwd);
        }
        public void clickSingUpBtn(){
            super.clickOn(this.btnSingUp);
        }
        public void writeFname(String fName){
            super.writeText(this.fNameInput, fName);
        }
        public void writeLname(String lName){
            super.writeText(this.lNameInput, lName);
        }
        public void clickContSignUpBtn(){
            super.clickOn(this.btnContinueSignUp);
        }



}
