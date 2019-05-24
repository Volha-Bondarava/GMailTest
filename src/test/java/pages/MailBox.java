package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

    @FindBy(xpath = "//a[@class='gb_x gb_Ca gb_f']")
    private WebElement accountButton;

    @FindBy(id = "gb_71")
    private WebElement signOutButton;

    @FindBy(xpath = "//*[@title='Google apps']")
    private WebElement googleAppsButton;

    @FindBy(xpath = "//*[text()='Gmail']")
    private WebElement gmailAppButton;

    @FindBy(name = "to")
    private WebElement mailToField;

    @FindBy(name = "subjectbox")
    private WebElement mailSubjectField;

    @FindBy(xpath = "//*[@class='Am Al editable LW-avf']")
    private WebElement mailBodyField;

    @FindBy(xpath = "//*[@class='T-I J-J5-Ji T-I-KE L3']")
    private WebElement createMailButton;

    @FindBy(xpath = "//*[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")
    private WebElement sendButton;

    @FindBy(xpath = "//*[@class='Cp']//tbody/tr")
    private WebElement mail;

    @FindBy(xpath = "//*[@class='bog']")
    private WebElement theme;

    @FindBy(xpath = "//*[@role='checkbox']")
    private WebElement checkBox;

    @FindBy(xpath = "//*[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']")
    private WebElement deleteButton;

    public MailBox(WebDriver driver) {
        this.driver = driver;
        this.driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(this.driver, 10);
    }

    public void logOff() {
        wait.until(ExpectedConditions.visibilityOf(accountButton));
        accountButton.click();
        wait.until(ExpectedConditions.visibilityOf(signOutButton));
        signOutButton.click();
    }

    public void openPage(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void login(String login, String password) {
        loginButton.click();
        loginField.sendKeys(login);
        loginNextButton.click();
        wait.until(ExpectedConditions.visibilityOf(passField));
        passField.sendKeys(password);
        passNextButton.click();
    }

    public void openGmailApp() {
        wait.until(ExpectedConditions.visibilityOf(googleAppsButton));
        googleAppsButton.click();
        wait.until(ExpectedConditions.visibilityOf(gmailAppButton));
        gmailAppButton.click();
    }

    public void sendMail(String to, String subject, String message) {
        createMailButton.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(mailToField, mailSubjectField, mailBodyField, sendButton));
        mailToField.sendKeys(to);
        mailSubjectField.sendKeys(subject);
        mailBodyField.sendKeys(message);
        sendButton.click();
        sleep(3);
    }

    public boolean containsMail(String theme, String messagePart) {
        driver.navigate().refresh();
        ArrayList<WebElement> messages = new ArrayList<>(driver.findElements(By.xpath("//*[@class='Cp']//tbody/tr")));
        for (WebElement message : messages) {
            var text = message.getText();
            if (text.contains(theme) && text.contains(messagePart)) {
                return true;
            }
        }
        return false;
    }

    public void deleteMailMessage(String theme, String messagePart) {
        ArrayList<WebElement> messages = new ArrayList<>(driver.findElements(By.xpath("//*[@class='Cp']//tbody/tr")));
        for (int i = 0; i < messages.size(); i++) {
            var text = messages.get(i).getText();
            if (text.contains(theme) && text.contains(messagePart)) {
                var xpath = String.format("//*[@class='Cp']//tbody/tr[%s]//*[@role='checkbox']", i + 1);
                driver.findElement(By.xpath(xpath)).click();
            }
        }
        sleep(3);
        wait.until(ExpectedConditions.visibilityOf(deleteButton));
        deleteButton.click();
        sleep(3);
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
