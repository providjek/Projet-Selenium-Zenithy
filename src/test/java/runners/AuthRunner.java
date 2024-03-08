package runners;

import config.ConfigYaml;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import managers.WebDrivenSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;


@CucumberOptions(
        features = "./src/test/resources/features",
        glue = {"stepDefinitions"},
        tags = "@search-exercise",
        plugin = {
                "pretty",
                "html:target/cucumber—reports"
        }
)
public class AuthRunner extends AbstractTestNGCucumberTests {
    private ConfigYaml configYaml = new ConfigYaml();

    private final String BASE_URL = configYaml.getBaseUrl();
    private final WebDriver driver = WebDrivenSingleton.getInstance();

    @BeforeTest
    public void setup(){

        System.out.println("Before Test : Setup");
        //System.out.println("Param YML : "+url+"**"+username+"**"+password);
        this.driver.manage().window().maximize();
        //this.driver.get(this.BASE_URL);
    }
}
