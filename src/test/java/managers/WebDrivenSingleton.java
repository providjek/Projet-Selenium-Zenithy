package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDrivenSingleton {
    private static WebDriver driver;

    public static WebDriver getInstance(){
        if (driver == null){

            driver = new ChromeDriver();
        }
        return driver;
    }

    public static void destroy(){
        driver.quit();
        driver = null;
    }
}
