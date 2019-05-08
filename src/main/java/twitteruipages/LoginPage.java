package twitteruipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private By loginField = By.xpath("//div[@class='signin-wrapper']//input[@name='session[username_or_email]']");
    private By passwordField = By.xpath("//div[@class='signin-wrapper']//input[@name='session[password]']");
    private By logInButton = By.xpath("//button[@type='submit']");


    public LoginPage typeUsername(String username){
        driver.findElement(loginField).sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password){
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public LoginPage clickLogInButton(){
        driver.findElement(logInButton).click();
        return  new LoginPage(driver);
    }

    public LoginPage logInMethod(String username, String password){
        this.typeUsername(username);
        this.typePassword(password);
        this.clickLogInButton();
        return new LoginPage(driver);
    }

}
