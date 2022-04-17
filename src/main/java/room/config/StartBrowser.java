package room.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import room.utils.ConfiguratorSupport;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class StartBrowser {

    public static WebDriver driver;
    public ConfiguratorSupport cs = new ConfiguratorSupport("config.properties");
    public static ExtentReports extent;
    public static ExtentTest parentTest;
    public static ExtentTest childTest;
    ExtentSparkReporter htmlReporter;
    String method;

    @BeforeTest
    public void generatereport() {
        htmlReporter = new ExtentSparkReporter("Reports/AutomationReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void methodName(Method method) {
        parentTest = extent.createTest(method.getName());
    }

    @BeforeClass
    public void beforeClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
        extent.flush();
    }
}
