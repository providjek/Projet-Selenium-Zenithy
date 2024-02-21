package runners;

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
        tags = "@search-exercise"
)
public class AuthRunner extends AbstractTestNGCucumberTests {
    private Yaml yaml = new Yaml();
    private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
    private Map<String, String> obj = yaml.load(inputStream);

    private final String BASE_URL = obj.get("base-url");
    private final WebDriver driver = WebDrivenSingleton.getInstance();

    @BeforeTest
    public void setup(){

        System.out.println("Before Test : Setup");
        //System.out.println("Param YML : "+url+"**"+username+"**"+password);
        this.driver.manage().window().maximize();
        //this.driver.get(this.BASE_URL);
    }
}
