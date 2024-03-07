package stepDefinitions;

import config.ConfigYaml;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.WebDrivenSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import utils.Tools;

public class  LoginSteps {

    ConfigYaml configYaml = new ConfigYaml();
    private WebDriver driver = WebDrivenSingleton.getInstance();
    private String LOGIN_URL = configYaml.getLogUrl();
    private String HOME_URL = configYaml.getHomeUrl();
    private Tools tools = new Tools();
    private HomePage homePage = new HomePage();
    private LoginPage loginPage = new LoginPage();


    @Given("Je suis sur la page de connexion")
    public void jeSuisSurLaPageDeConnexion() {
        this.loginPage.getLogPage();
    }

    @When("Je renseigne mon adresse {string} et mon mot de passe {string}")
    public void jeRenseigneMonAdresseEtMonMotDePasse(String arg0, String arg1) {
        this.loginPage.writeEmail(arg0);
        this.loginPage.writePasswd(arg1);
        this.loginPage.clickOnBtnLogin();
    }
    @Then("Je suis connecté et rédirigé sur la page d'accueil")
    public void jeSuisConnectéEtRédirigéSurLaPageDAccueil() {
        this.tools.waitUrlContains(this.HOME_URL);
        Assert.assertEquals(this.driver.getCurrentUrl(), this.HOME_URL, "La redirection vers la page d'accueil a échouée");
    }
    @Given("Je suis connec")
    public void jeSuisConnec() {
        this.tools.waitUrlContains(this.HOME_URL);
        Assert.assertEquals(this.driver.getCurrentUrl(), this.HOME_URL, "La redirection vers la page d'accueil a échouée");
    }
    @When("Je me déconnecte")
    public void jeMeDéconnecte() {
        this.homePage.swicthToHeaderIframe();
        this.homePage.clickOnLogoutBtn();
    }
    @Then("Je suis déconnecté et redirigé vers la page de connexionté à la plateforme")
    public void jeSuisDéconnectéEtRedirigéVersLaPageDeConnexiontéÀLaPlateforme() {
        this.tools.waitUrlMacthes(this.LOGIN_URL);
        Assert.assertEquals(this.driver.getCurrentUrl(), this.LOGIN_URL+"/logout", "La redirection vers la page d'accueil a échouée");
    }
    @When("je connecte avec des informations invalides {string} et {string}")
    public void jeConnecteAvecDesInformationsInvalidesEmailEtMdp(String mail, String pass) {
        this.loginPage.writeEmail(mail);
        this.loginPage.writePasswd(pass);
        this.loginPage.clickOnBtnLogin();
    }
    @Then("Le connexion echoue et je reçois le {string}")
    public void leConnexionEchoueEtJeReçoisLeMsg(String msg) throws InterruptedException {
        Thread.sleep(10000);
        this.tools.waitVisibilityofElement(this.loginPage.getMSG_ALERTE());
        this.tools.waitTextElementToBeVisible(this.loginPage.getMSG_ALERTE(), msg);
        Assert.assertEquals(msg, this.loginPage.getMsgAlerte());
    }
}
