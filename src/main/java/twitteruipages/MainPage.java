package twitteruipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By tweetField = By.xpath("//div[@class='tweet-box rich-editor']");
    private By tweetButton = By.xpath("//button[@class='tweet-action EdgeButton EdgeButton--primary js-tweet-btn']");
    private By tweetTextField = By.xpath("//p[@class='TweetTextSize  js-tweet-text tweet-text']");
    private By tweetTime = (By.xpath("//span[@class='_timestamp js-short-timestamp js-relative-timestamp']"));
    private By duplicatedTweet = (By.xpath("//span[@class='message-text']"));
    private By moreTweetOptions = (By.xpath("//div[@class='tweet-box rich-editor']"));
    private By deleteTweet = (By.xpath("//button[@class='dropdown-link']"));
    private By deleteConfirmation = By.xpath("//button[@class='EdgeButton EdgeButton--danger delete-action']");


    public MainPage createNewTweet(String tweet){
        driver.findElement(tweetField).sendKeys(tweet);
        this.clickTweetButton();
        return  new MainPage(driver);
    }


    public MainPage clickTweetButton(){
        driver.findElement(tweetButton).click();
        return  new MainPage(driver);
    }

    public MainPage clickMoreTweetOptions(){
        driver.findElement(moreTweetOptions).click();
        return  new MainPage(driver);
    }

    public MainPage clickDeleteTweet(){
        driver.findElement(deleteTweet).click();
        return  new MainPage(driver);
    }

    public MainPage clickDeleteConfirmation(){
        driver.findElement(deleteConfirmation).click();
        return  new MainPage(driver);
    }


    public String getTweetTextField(){
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(tweetTextField));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(tweetTextField).getText();
    }

    public String getTweetTime(){
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(tweetTime));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(tweetTime).getText();
    }

    public String getDuplicatedTweetMessage(){
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(tweetTime));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(duplicatedTweet).getText();
    }
}
