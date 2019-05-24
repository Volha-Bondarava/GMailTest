package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailBox {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "gb_70")
    private WebElement loginButton;

    @FindBy(id = "identifierId")
    private WebElement loginField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passField;

    @FindBy(id = "identifierNext")
    private WebElement loginNextButton;

    @FindBy(id = "passwordNext")
    private WebElement passNextButton;

    @FindBy(xpath = "//a[contains(@title, 'Google Account: ')]")
    private WebElement accountButton;

    @FindBy (id = "gb_71")
    private WebElement signOutButton;

    public MailBox(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 5);
    }

    public void logOff() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@title, 'Google Account: ')]")));
        accountButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gb_71")));
        signOutButton.click();
    }

    public void openPage(String url) {
        driver.get(url);
    }

    public boolean login(String login, String password) {
        String curUrl = driver.getCurrentUrl();
        loginButton.click();
        loginField.sendKeys(login);
        loginNextButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passField.sendKeys(password);
        passNextButton.click();
        return !driver.getCurrentUrl().equals(curUrl);
    }


}
