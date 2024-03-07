package stepDefinitions;

import config.ConfigYaml;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.WebDrivenSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.yaml.snakeyaml.Yaml;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import runners.AuthRunner;
import utils.Tools;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RExercisesSteps {
    private Yaml yaml = new Yaml();
    private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
    private Map<String, String> obj = yaml.load(inputStream);

    private ConfigYaml configYaml = new ConfigYaml();

    private final String AUTH_URL = this.configYaml.getLogUrl();
    private By EMAIL_LOG_ID = By.id("modalusername");
    private By PASSWD_LOG_ID = By.id("current-password");
    private By BTN_LOG_CSS = By.cssSelector("div.LoginModal_cta_bottom_box_button_login__5Fbwv button");
    private String BASE_URL = this.configYaml.getBaseUrl();
    private WebDriver driver = WebDrivenSingleton.getInstance();
    private String LOGIN_URL = obj.get("log-url");
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    private By SEARCH_ID = By.id("tnb-google-search-input");
    private By BTN_SBMT_SEAECH_ID = By.id("tnb-google-search-submit-btn");
    private By SELECT_EX_PATH = By.xpath("//*[@id=\"___gcse_0\"]/div/div/div[1]/div[6]/div[2]/div/div/div[1]/div[2]/div/div[1]/div/a");
    private String idTabSing;
    private String idTabExe;
    private LoginPage loginPage = new LoginPage();
    private HomePage homePage= new HomePage();
    private Tools tools = new Tools();


//    @Before(order = 1)
//    public void login(){
//        System.out.println("login is successful");
//        this.driver.get(this.LOGIN_URL);
//        this.driver.findElement(this.EMAIL_LOG_ID).sendKeys("provi-test5@yopmail.com");
//        this.driver.findElement(this.PASSWD_LOG_ID).sendKeys("P@ss1234");
//        this.driver.findElement(this.BTN_LOG_CSS).click();
//   }

    @Given("Je suis connecté et sur la page d'acceuil" )
    public void jeSuisConnectéEtSurLaPageDAcceuil() throws InterruptedException {
        System.out.println("login is successful");
        this.driver.get(this.LOGIN_URL);
        this.loginPage.writeEmail("provi-test5@yopmail.com");
        this.loginPage.writePasswd("P@ss1234");
        this.loginPage.clickOnBtnLogin();

        //this.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tnb-google-search-input")));
        this.tools.waitUrlContains(this.AUTH_URL);
        Assert.assertEquals(this.driver.getCurrentUrl(), this.AUTH_URL);
        //this.driver.get(this.AUTH_URL);
    }

    @When("Je recherce {string} dans la plateforme")
    public void jeRecherceDansLaPlateforme(String arg0) {
        WebElement iframeElement = this.tools.waitPresenceOfElement(this.homePage.getTOP_NAV_IFRAME());
        driver.switchTo().frame(this.homePage.getIframeTopNav());
        //WebElement searchEl = this.tools.waitPresenceOfElement(this.homePage.getSEARCH_FIELD_BY()); // this.wait.until(ExpectedConditions.presenceOfElementLocated(this.SEARCH_ID));
        this.homePage.writeSearch(arg0);
        this.homePage.clickOnBtnSbmtSearch();
        this.driver.switchTo().defaultContent();
    }

    @Then("J'ai des résultats d'elements à la fin de la recherche")
    public void jAiDesRésultatsDElementsÀLaFinDeLaRecherche() {
        WebElement iframeElement = this.tools.waitPresenceOfElement(this.homePage.getTOP_NAV_IFRAME());
        driver.switchTo().frame(this.homePage.getIframeTopNav());
        WebElement resultEl = this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.gsc-results-wrapper-overlay.gsc-results-wrapper-visible")));
        Assert.assertTrue(this.homePage.getResultEl().isDisplayed(), "L'élément est présent sur la page.");
    }

    @And("Je choisis l'Exercice {int}")
    public void jeChoisisLExercice(int arg0) {
        //WebElement iframeElement = this.tools.waitPresenceOfElement(By.xpath("//*[@id=\"___gcse_0\"]/div/div/div[1]/div[6]/div[2]/div/div/div[1]/div[2]/div/div[1]/div/a"));
        this.homePage.clickOnExercise();

    }

    @Given("J'ai choisi et ouvert {string}")
    public void jAiChoisiEtOuvert(String arg0) {


        //Recuperation de l'actuel identifiant
        this.idTabSing = driver.getWindowHandle();
        Set<String> idTabsSet = this.driver.getWindowHandles();
        // Naviguer vers le nouvel onglet
        for (String idTab : idTabsSet) {
            if (!idTab.equals(this.idTabSing)) {
                this.idTabExe = idTab;
                driver.switchTo().window(this.idTabExe);
                break;
            }
        }

        //this.wait.until(ExpectedConditions.urlContains("https://www.w3schools.com/html/exercise.asp"));
        this.tools.waitUrlContains(this.BASE_URL+"html/exercise.asp");
        System.out.println(this.driver.getCurrentUrl());
        Assert.assertEquals(this.driver.getCurrentUrl(), this.BASE_URL+"html/exercise.asp");
    }

    @When("Je reponds aux questionnaires de l'exercice")
    public void jeRepondsAuxQuestionnairesDeLExercice() {

        //List<WebElement> answersInput = this.driver.findElements(By.cssSelector("#assignmentcontainer input"));
        List<WebElement> answersInput = this.homePage.getInputAnswerExo1();
        this.homePage.answerExo1();

//        for(int i = 0; i < answersInput.size(); i++){
//            if(i % 2 == 0){
//            answersInput.get(i).sendKeys("<");
//         }else{
//                answersInput.get(i).sendKeys(">");
//            }
//
//        }
    }

    @And("Je valides mes réponses")
    public void jeValidesMesRéponses() {
        this.homePage.clickOnAnswerExo1();
    }

    @Then("Je peux voir mon score sur l'exercice")
    public void jePeuxVoirMonScoreSurLExercice() {
    }
}
