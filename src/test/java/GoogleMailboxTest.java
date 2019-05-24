import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.MailBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class GoogleMailboxTest {
    private WebDriver driver;
    private MailBox mailBox;

    private Properties properties;

    @BeforeClass
    public void setUp() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(properties.getProperty("url"));
        System.setProperty("webdriver.chrome.driver", properties.getProperty("chromedriverPath"));
        this.driver = new ChromeDriver(new ChromeOptions());
        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        mailBox = PageFactory.initElements(driver, MailBox.class);
    }

    @AfterClass
    public void tearDown() {
        mailBox.logOff();
        driver.quit();
    }

    @Test
    public void testLogin() {
        mailBox.openPage(properties.getProperty("url"));
        Assert.assertTrue(mailBox.login(properties.getProperty("login"), properties.getProperty("password")),
                "Couldn't login");
    }
}
