package stepDefinitions;

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


public class authSteps {

    private Yaml yaml = new Yaml();
    private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
    private Map<String, String> obj = yaml.load(inputStream);

    private String REG_URL = obj.get("sign-url");
    private WebDriver driver = WebDrivenSingleton.getInstance();
    private final By EMAIL_INPUT_ID = By.id("modalusername");
    private final By PASSWD_INPUT_ID = By.id("new-password");
    private final By BTN_SING_CLASS = By.className("LoginModal_cta_bottom_box_button_login__5Fbwv");
    private final By F_NAME_INPUT_ID = By.id("modal_first_name");
    private final By L_NAME_INPUT_ID  = By.id("modal_last_name");
    private final By BTN_CONTINUE_PATH = By.xpath("//*[@id=\"root\"]/div/div/div[4]/div[1]/div/div[3]/div[1]/button");

    /*
       ----->>> Variables Yopmail
     */
    private String YOPMAIL_BASE_URL = "https://yopmail.com/fr/";
    private final By EMAIL_INPUT_YP_ID = By.id("login");
    private final  By BTN_CONF_YP_CSS = By.cssSelector("button[class=md]");
    private final String  EMAIL_YP = "provi-testeur";


    /*
    /
     */
    private String idTabSing;
    private String idTabYp;
    private String idTabConfEmail;


    @Given("^Je suis sur la (page d'inscription)")
    public void jeSuisSurLaPageDInscription(String inscription) {
        System.out.println(inscription);
        this.driver.get(this.REG_URL);
    }

    @When("^Je renseigne mes informations valides$")
    public void jeRenseigneMesInformationsValides(DataTable dataTable) {

        //..Attendre jusqu'a 5s au moins si un elt n'a pas été trouver
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
        System.out.println(dataList.get(0));
        this.driver.findElement(this.EMAIL_INPUT_ID).sendKeys(dataList.get(0).get("email"));
        this.driver.findElement(this.PASSWD_INPUT_ID).sendKeys(dataList.get(0).get("pwd"));
        this.driver.findElement(this.BTN_SING_CLASS).click();
        this.driver.findElement(this.F_NAME_INPUT_ID).sendKeys(dataList.get(0).get("fname"));
        this.driver.findElement(this.L_NAME_INPUT_ID).sendKeys(dataList.get(0).get("lname"));
        this.driver.findElement(this.BTN_CONTINUE_PATH).click();

    }

    @And("Je confirme mon inscription grâce au lien reçu par mail à {string}")
    public void jeConfirmeMonInscriptionGrâceAuLienReçuParMail(String email) throws InterruptedException {


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //Ouvrir Yopmail dans un nouvel onglet
        /*
        /   *Caster l'objet driver en JsExecutor, qui permet d'executer des script JS et executer le script
        /       la methode executeScript().
        /   *Naviguer entre les differents onglets
         */
        ((JavascriptExecutor) this.driver).executeScript("window.open()");
        // Récuperation des identifiants de tous les onglets
        Set<String> idTabsSet = this.driver.getWindowHandles();

        //Recuperation de l'actuel identifiant
        this.idTabSing = driver.getWindowHandle();

        // Naviguer vers le nouvel onglet
        for (String idTab : idTabsSet) {
            if (!idTab.equals(this.idTabSing)) {
                this.idTabYp = idTab;
                driver.switchTo().window(this.idTabYp);
                break;
            }
        }
        this.driver.get(this.YOPMAIL_BASE_URL);
        this.driver.findElement(this.EMAIL_INPUT_YP_ID).sendKeys(email);
        this.driver.findElement(this.BTN_CONF_YP_CSS).click();
        //Thread.sleep(10000);

        // Basculer vers le contexte de l'iframe qui contient les messages reçus
        WebElement iframeElement = driver.findElement(By.cssSelector("iframe#ifinbox"));
        driver.switchTo().frame(iframeElement);

        List<WebElement> email_msg_all = this.driver.findElements(By.className("lmf"));
        //System.out.println(this.driver.getPageSource());
        for (WebElement element : email_msg_all) {
            if (element.getText().contains("W3schools")){
                element.click();
                break;
            }
        }
        //email_msg.stream().map(element -> element )).forEach(element -> System.out.println(System.out.println(element.getText()));

        // Revenir au contexte par défaut (la page principale)
        this.driver.switchTo().defaultContent();

        //NNaviguer vers le contexte du message selectionné
        WebElement iframeMessage = driver.findElement(By.cssSelector("iframe#ifmail"));
        driver.switchTo().frame(iframeMessage);

        List<WebElement> aMessage = this.driver.findElements(By.cssSelector("#mail a"));
        //System.out.println(this.driver.getPageSource());
        for (WebElement element : aMessage) {
            if (element.getText().contains("Verify email")){
                element.click();
                break;
            }
        }
        // Revenir au contexte par défaut (la page principale)
        //driver.switchTo().defaultContent();
        //driver.switchTo().window(this.);
    }

    @Then("Mon compte devrait être activé")
    public void monCompteDevraitÊtreActive() {

        Set<String> idTabsSet = this.driver.getWindowHandles();
        // Naviguer vers le nouvel onglet
        for (String idTab : idTabsSet) {
            if (!idTab.equals(this.idTabYp) && !idTab.equals(this.idTabSing)) {
                this.idTabConfEmail = idTab;
                driver.switchTo().window(this.idTabConfEmail);
                break;
            }
        }

        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.Alert_iwrp__5q1xH.Alert_success__g430w")));
        String confMsg = this.driver.findElement(By.cssSelector("div.Alert_iwrp__5q1xH.Alert_success__g430w")).getText();
        Assert.assertEquals(confMsg, "Success! Your email has been verified.");


    }

    @And("Je devrais être redirigé vers la page d'accueil")
    public void jeDevraisÊtreRedirigéVersLaPageDAccueil() {
        this.driver.switchTo().window(this.idTabSing);
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(this.obj.get("auth-url")));
        Assert.assertEquals(this.driver.getCurrentUrl(), this.obj.get("auth-url"), "La redirection vers la page d'accueil a échouée");
    }

}
