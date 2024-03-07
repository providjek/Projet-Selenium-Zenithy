package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.WebDrivenSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.yaml.snakeyaml.Yaml;
import pageObjects.ConfirmationPage;
import pageObjects.SingUpPage;
import pageObjects.YopmailPage;
import runners.AuthRunner;
import utils.Tools;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AuthSteps {

    private Yaml yaml = new Yaml();
    private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
    private Map<String, String> obj = yaml.load(inputStream);
    private String REG_URL = obj.get("sign-url");
    private String HOME_URL = obj.get("auth-url");
    private WebDriver driver = WebDrivenSingleton.getInstance();
    private String idTabSing;
    private String idTabYp;
    private ConfirmationPage confirmationPage = new ConfirmationPage();
    private SingUpPage singUpPage = new SingUpPage();
    private YopmailPage yopmailPage = new YopmailPage();
    private Tools tools = new Tools();



    @Given("^Je suis sur la (page d'inscription)")
    public void jeSuisSurLaPageDInscription(String inscription) {
        System.out.println(inscription);
        this.singUpPage.getSingUpPage();
        Assert.assertEquals(this.driver.getCurrentUrl(), this.REG_URL);
    }

    @When("^Je renseigne mes informations valides$")
    public void jeRenseigneMesInformationsValides(DataTable dataTable) {
        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
        System.out.println(dataList.get(0));
        this.singUpPage.writeEmail(dataList.get(0).get("email"));
        this.singUpPage.writePasswd(dataList.get(0).get("pwd"));
        this.singUpPage.clickSingUpBtn();
        this.singUpPage.writeFname(dataList.get(0).get("fname"));
        this.singUpPage.writeLname(dataList.get(0).get("lname"));
        this.singUpPage.clickContSignUpBtn();
        this.idTabSing = this.driver.getWindowHandle();
    }

    @And("Je confirme mon inscription grâce au lien reçu par mail de {} à {string}")
    public void jeConfirmeMonInscriptionGrâceAuLienReçuParMail(String sender, String email) throws InterruptedException {
        this.yopmailPage.openYpInNewTab();
        this.idTabYp = this.driver.getWindowHandle();
        this.yopmailPage.writeEmailYp(email);
        this.yopmailPage.clickBtnConfYp();
        this.yopmailPage.openAMessageInYp(sender);
        this.yopmailPage.confirmeMailYp();

    }

    @Then("Mon compte devrait être activé")
    public void monCompteDevraitÊtreActive() {
        goToConfirmationTab(this.driver, this.idTabYp, this.idTabSing);
        this.confirmationPage.waitVisibilityofAlerte();
        Assert.assertEquals(this.confirmationPage.getConfMsgText(), "Success! Your email has been verified.");
    }
    @And("Je devrais être redirigé vers la page d'accueil")
    public void jeDevraisÊtreRedirigéVersLaPageDAccueil() {

        this.driver.switchTo().window(this.idTabSing);
        this.tools.waitUrlContains(this.HOME_URL);
        Assert.assertEquals(this.driver.getCurrentUrl(), this.HOME_URL, "La redirection vers la page d'accueil a échouée");
    }

    private static void goToConfirmationTab(WebDriver driver, String idTab1, String idTab2){
        Set<String> idTabsSet = driver.getWindowHandles();
        // Naviguer vers le nouvel onglet
        for (String idTab : idTabsSet) {
            if (!idTab.equals(idTab1) && !idTab.equals(idTab2)) {
                driver.switchTo().window(idTab);
                break;
            }
        }
    }

}
