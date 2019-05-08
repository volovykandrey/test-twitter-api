import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import twitteruipages.LoginPage;
import twitteruipages.MainPage;

import java.util.concurrent.TimeUnit;

    public class TwitterBasicOperationUITests {

        private WebDriver driver;
        private LoginPage loginPage;
        private MainPage mainPage;
        private String tweetText = "This is test tweet for Agile Engine1";
        private String tweetTime = "now";
        private String duplicatedTweetMessage = "You have already sent this Tweet.";


        @BeforeSuite
        public void setUp() {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\avolovyk\\IdeaProjects\\drivers\\chromedriver.exe");
            driver = new ChromeDriver();

            driver.manage().window().maximize();
            driver.get("https://twitter.com/login");
            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
            loginPage = new LoginPage(driver);
            mainPage = new MainPage(driver);
            loginPage.logInMethod("testusername@gmail.com", "testpassword");
        }


        @Test (priority = 1)
        public void createTweetTest(){
            mainPage.createNewTweet(tweetText);
            Assert.assertEquals(mainPage.getTweetTextField(),tweetText,"Tweet status doesn't update");
        }

        @Test (priority = 2)
        public void createdTimeTest(){
            mainPage.getTweetTime();
            Assert.assertEquals(mainPage.getTweetTime(),tweetTime,"Tweet time isn't NOW");
        }

        @Test (priority = 3)
        public void duplicateTweetTest(){
        mainPage.createNewTweet(tweetText);
        Assert.assertEquals(mainPage.getDuplicatedTweetMessage(), duplicatedTweetMessage, "Doesn't display message: You have already sent this Tweet.");
        }

        @Test (priority = 4)
        public void deleteTweetTest(){
            mainPage.clickMoreTweetOptions();
            mainPage.clickDeleteTweet();
            mainPage.clickDeleteConfirmation();
            Assert.assertNotEquals(mainPage.getTweetTextField(),tweetText,"Tweet status is present but should be deleted");
        }


        @AfterSuite
        public void tearDown(){
            driver.quit();
        }

}
