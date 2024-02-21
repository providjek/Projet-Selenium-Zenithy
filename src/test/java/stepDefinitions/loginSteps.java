package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.WebDrivenSingleton;
import net.bytebuddy.build.Plugin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;
import runners.AuthRunner;

import java.io.InputStream;
import java.time.Duration;
import java.util.Map;

public class  loginSteps {

    //Lecture des données de conf depuis le fichier yaml
    private Yaml yaml = new Yaml();
    private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
    private Map<String, String> obj = yaml.load(inputStream);

    private WebDriver driver = WebDrivenSingleton.getInstance();
    private String LOGIN_URL = obj.get("log-url");
    private By EMAIL_LOG_ID = By.id("modalusername");
    private By PASSWD_LOG_ID = By.id("current-password");
    private By BTN_LOG_CSS = By.cssSelector("div.LoginModal_cta_bottom_box_button_login__5Fbwv button");
    private WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));

    private By MSG_ALERTE = By.cssSelector("div.Alert_iwrp__5q1xH.Alert_danger__Wsdhv");

    private By LOGOUT_ID = By.id("logout-link");


    @Given("Je suis sur la page de connexion")
    public void jeSuisSurLaPageDeConnexion() {
        this.driver.get(this.LOGIN_URL);

        System.out.println("Param YML : "+this.obj.get("base-url"));



    }

    @When("Je renseigne mon adresse {string} et mon mot de passe {string}")
    public void jeRenseigneMonAdresseEtMonMotDePasse(String arg0, String arg1) {
        this.driver.findElement(this.EMAIL_LOG_ID).sendKeys(arg0);
        this.driver.findElement(this.PASSWD_LOG_ID).sendKeys(arg1);
        this.driver.findElement(this.BTN_LOG_CSS).click();

    }

    @Then("Je suis connecté et rédirigé sur la page d'accueil")
    public void jeSuisConnectéEtRédirigéSurLaPageDAccueil() {
        this.wait.until(ExpectedConditions.urlContains("https://pathfinder.w3schools.com/"));
        Assert.assertEquals(this.driver.getCurrentUrl(), "https://pathfinder.w3schools.com/", "La redirection vers la page d'accueil a échouée");

    }

    @Given("Je suis connec")
    public void jeSuisConnec() {
        this.wait.until(ExpectedConditions.urlContains("https://pathfinder.w3schools.com/"));
        Assert.assertEquals(this.driver.getCurrentUrl(), "https://pathfinder.w3schools.com/", "La redirection vers la page d'accueil a échouée");
    }

    @When("Je me déconnecte")
    public void jeMeDéconnecte() {
        this.driver.findElement(this.LOGOUT_ID).click();
    }

    @Then("Je suis déconnecté et redirigé vers la page de connexionté à la plateforme")
    public void jeSuisDéconnectéEtRedirigéVersLaPageDeConnexiontéÀLaPlateforme() {
        this.wait.until(ExpectedConditions.urlContains(this.LOGIN_URL));
        Assert.assertEquals(this.driver.getCurrentUrl(), this.LOGIN_URL, "La redirection vers la page d'accueil a échouée");

    }

    @When("je connecte avec des informations invalides {string} et {string}")
    public void jeConnecteAvecDesInformationsInvalidesEmailEtMdp(String mail, String pass) {
        this.driver.findElement(this.EMAIL_LOG_ID).sendKeys(mail);
        this.driver.findElement(this.PASSWD_LOG_ID).sendKeys(pass);
        this.driver.findElement(this.BTN_LOG_CSS).click();
    }

    @Then("Le connexion echoue et je reçois le {string}")
    public void leConnexionEchoueEtJeReçoisLeMsg(String msg) throws InterruptedException {
        Thread.sleep(10000);
        this.wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(this.MSG_ALERTE)));
        this.wait.until(ExpectedConditions.textToBePresentInElement(this.driver.findElement(this.MSG_ALERTE), msg));
        Assert.assertEquals(msg, this.driver.findElement(this.MSG_ALERTE).getText());
    }
}
