package utils;

import managers.WebDrivenSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class Tools {
    private WebDriver driver = WebDrivenSingleton.getInstance();
    private WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(40));

    public void waitVisibilityofElement(By byWebElement){
        wait.until(ExpectedConditions.visibilityOfElementLocated(byWebElement));
    }
    public void waitTextElementToBeVisible(WebElement webElement,String text ){
        this.wait.until(ExpectedConditions.textToBePresentInElement(webElement, text));
    }
    public void waitTextElementToBeVisible(By elBy,String text ){
        this.wait.until(ExpectedConditions.textToBePresentInElement(this.driver.findElement(elBy), text));
    }
    public void waitUrlContains(String url){
        wait.until(ExpectedConditions.urlContains(url));
    }
    public void waitUrlMacthes(String url){
        wait.until(ExpectedConditions.urlMatches(url));
    }
    public WebElement waitPresenceOfElement(By byEl){
       return this.wait.until(ExpectedConditions.presenceOfElementLocated(byEl));
    }
    public void openAndUseNewTab(String url){
         /*
        /   *Caster l'objet driver en JsExecutor, qui permet d'executer des script JS et executer le script
        /       la methode executeScript().
        /   *Naviguer entre les differents onglets
         */
        ((JavascriptExecutor) this.driver).executeScript("window.open()");
        // Récuperation des identifiants de tous les onglets
        Set<String> idTabsSet = this.driver.getWindowHandles();

        //Recuperation de l'actuel identifiant
        String oldTabId = driver.getWindowHandle();
        String newTabid;

        // Naviguer vers le nouvel onglet
        for (String idTab : idTabsSet) {
            if (!idTab.equals(oldTabId)) {
                newTabid = idTab;
                driver.switchTo().window(newTabid);
                break;
            }
        }
        this.driver.get(url);
    }


}
