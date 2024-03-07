package pageObjects;

import config.ConfigYaml;
import managers.WebDrivenSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Tools;
import java.time.Duration;
import java.util.List;

public class HomePage extends Page {

    @FindBy(id = "top-nav-bar-iframe")
    private WebElement headerIframe;
    @FindBy(id = "logout-link")
    private WebElement logoutContEl;
    private WebDriver driver = WebDrivenSingleton.getInstance();
    private Tools tools = new Tools();
    private ConfigYaml configYaml = new ConfigYaml();
    private final String AUTH_URL = configYaml.getHomeUrl();
    private String LOGIN_URL = configYaml.getLogUrl();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    @FindBy(id = "tnb-google-search-input")
    private WebElement searchFieldEl;
    private By SEARCH_FIELD_BY = By.id("tnb-google-search-input");
    @FindBy(id = "tnb-google-search-submit-btn")
    private WebElement btnSubmtSearch;
    @FindBy(xpath = "//*[@id=\"___gcse_0\"]/div/div/div[1]/div[6]/div[2]/div/div/div[1]/div[2]/div/div[1]/div/a")
    private WebElement selectEx;
    @FindBy(id = "top-nav-bar-iframe")
    private WebElement iframeTopNav;
    private By TOP_NAV_IFRAME = By.cssSelector("iframe#top-nav-bar-iframe");

    private By RESULT_BY = By.cssSelector("div.gsc-results-wrapper-overlay.gsc-results-wrapper-visible");
    @FindBy(css = "div.gsc-results-wrapper-overlay.gsc-results-wrapper-visible")
    private WebElement resultEl;
    @FindBy(css = "#assignmentcontainer input")
    private List<WebElement> inputAnswerExo1;
    @FindBy(id = "answerbutton")
    private WebElement answerExo1Btn;

    public void writeSearch(String val){
        super.writeText(searchFieldEl, val);
    }
    public void clickOnBtnSbmtSearch(){
        super.clickOn(btnSubmtSearch);
    }
    public void clickFrameTop(){
        super.clickOn(iframeTopNav);
    }
    public void clickOnExercise(){
        super.clickOn(selectEx);
    }


    public void answerExo1() {
        System.out.println("dddddddddd"+this.inputAnswerExo1.size());
        for(int i = 0; i < this.inputAnswerExo1.size(); i++){
            if(i % 2 == 0){
                super.writeText(this.inputAnswerExo1.get(i),"<");
            }else{
                super.writeText(this.inputAnswerExo1.get(i),">");
            }
        }
    }

    public List<WebElement> getInputAnswerExo1() {
        return inputAnswerExo1;
    }

    public void clickOnAnswerExo1(){
        super.clickOn(this.answerExo1Btn);
    }
    public void swicthToHeaderIframe(){
        this.tools.waitVisibilityofElement(By.id("top-nav-bar-iframe"));
        this.driver.switchTo().frame(headerIframe);
    }
    public void clickOnLogoutBtn(){
        super.clickOn(this.logoutContEl);
    }
    public WebElement getHeaderIframe() {
        return headerIframe;
    }
    public WebElement getLogoutContEl() {
        return logoutContEl;
    }

    public WebElement getIframeTopNav() {
        return iframeTopNav;
    }

    public By getTOP_NAV_IFRAME() {
        return TOP_NAV_IFRAME;
    }

    public By getRESULT_BY() {
        return RESULT_BY;
    }

    public WebElement getResultEl() {
        return resultEl;
    }

    public By getSEARCH_FIELD_BY() {
        return SEARCH_FIELD_BY;
    }
}
