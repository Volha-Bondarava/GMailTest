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

    private Properties mailProperties;
    private Properties loginProperties;

    @BeforeClass
    public void setUp() {
        mailProperties = new Properties();
        loginProperties = new Properties();
        try {
            mailProperties.load(new FileInputStream("src/test/resources/mail.properties"));
            loginProperties.load(new FileInputStream("src/test/resources/login.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver", mailProperties.getProperty("chromedriverPath"));
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
        mailBox.openPage(mailProperties.getProperty("url"));
        Assert.assertTrue(mailBox.login(loginProperties.getProperty("login"), loginProperties.getProperty("password")),
                "Couldn't login");
    }
}
