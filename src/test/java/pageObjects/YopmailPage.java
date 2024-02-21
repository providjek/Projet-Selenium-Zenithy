package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.yaml.snakeyaml.Yaml;
import runners.AuthRunner;
import utils.Tools;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class YopmailPage extends Page{
    private Yaml yaml = new Yaml();
    private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
    private Map<String, String> obj = yaml.load(inputStream);

    private String YOPMAIL_BASE_URL = obj.get("yp-url");
    @FindBy(id = "login")
    private WebElement emailInputYp;
    @FindBy(css = "button[class=md]")
    private WebElement btnConfYp;
    @FindBy(css = "iframe#ifinbox")
    private WebElement iframeElement;
    private final String  EMAIL_YP = "provi-testeur";
    private Tools tools = new Tools();

    public void openYpInNewTab(){
        this.tools.openAndUseNewTab(this.YOPMAIL_BASE_URL);
    }
    public void writeEmailYp(String emailYp){
        super.writeText(this.emailInputYp, emailYp);
    }
    public void clickBtnConfYp(){
        super.clickOn(this.btnConfYp);
    }
    public void openAMessageInYp(String sender){
        this.driver.switchTo().frame(this.iframeElement);
        List<WebElement> email_msg_all = this.driver.findElements(By.className("lmf"));
        for (WebElement element : email_msg_all) {
            if (element.getText().contains(sender)){
                element.click();
                break;
            }
        }
        //email_msg.stream().map(element -> element )).forEach(element -> System.out.println(System.out.println(element.getText()));

        // Revenir au contexte par défaut (la page principale)
        this.driver.switchTo().defaultContent();

    }

    public void confirmeMailYp(){

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
    }

}
