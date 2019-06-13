import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import restapitwitter.RequestHelper;
import restapitwitter.TwitterPublicAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TwitterBasicOperationsApiTests {
    TwitterPublicAPI twitterPublicAPI = new TwitterPublicAPI();
    RequestHelper requestHelper = new RequestHelper();

    @BeforeGroups("newTweetShouldBeAdded")
    public void createNewTweet(){
        twitterPublicAPI.postUpdateStatus("Test Status: " + twitterPublicAPI.uniqueStatus);
    }


    @Test (priority = 1)
    public void verifyPostStatusText() {
        Map<String, Object> hashMap;
        hashMap = twitterPublicAPI.postUpdateStatus("Test Status: " + twitterPublicAPI.uniqueStatus);
        Assert.assertEquals(hashMap.get("text"),"Test Status: " + twitterPublicAPI.uniqueStatus,"Post Status Text Is Incorrect" );
        Assert.assertEquals(hashMap.get("StatusCode"),"200","Status Code Is Not Equal 200 OK");
    }

    @Test (priority = 2, groups = "newTweetShouldBeAdded")
    public void verifyGetStatusFields() {
        Map<String, Object> hashMap;
        hashMap = twitterPublicAPI.getStatusKeyValue();
        String tweetText = String.valueOf(hashMap.get("text"));
        Object retweetsCount = hashMap.get("retweet_count");
        String createdTime = requestHelper.createdDate(String.valueOf(hashMap.get("created_at")));

        DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date();
        String dateStr = dateFormat.format(date);

        Assert.assertEquals(tweetText,"Test Status: " + twitterPublicAPI.uniqueStatus, "Status Text Is Incorrect");
        Assert.assertEquals(retweetsCount,0, "Retweets Count Is More Than Zero ");
        Assert.assertEquals(hashMap.get("StatusCode"),"200","Status Code Is Not Equal 200 OK");
        Assert.assertEquals(createdTime,dateStr,"Date Is Not Equal To Created_At Value");
    }

    @Test(priority = 3, groups = "newTweetShouldBeAdded")
    public void verifyRemovedTweet(){
        Map<String, Object> hashMap1;
        hashMap1 = twitterPublicAPI.getStatusKeyValue();
        String tweet_id = String.valueOf(hashMap1.get("id_str"));

        Map<String, Object> hashMap2;
        hashMap2 = twitterPublicAPI.postDestroyStatusesByID(tweet_id);

        Assert.assertEquals(tweet_id,hashMap2.get("id_str"), "Tweet Id Does Not Match");
        Assert.assertEquals(hashMap2.get("StatusCode"),"200","Status Code Is Not Equal 200 OK");

        Map<String, Object> hashMap3;
        hashMap3 = twitterPublicAPI.getStatusKeyValue();

        Assert.assertNotEquals(hashMap3.get("id_str"),tweet_id, "Tweet Is Present But Should Be Removed");
        createNewTweet();
    }

    @Test (priority = 4)
    public void checkDuplicateStatusMessage(){
        twitterPublicAPI.postUpdateStatus("Test Status: " + twitterPublicAPI.uniqueStatus);
        Map<String, Object> hashMap;
        hashMap = twitterPublicAPI.postDublicateStatus("Test Status: " + twitterPublicAPI.uniqueStatus);
        String errorCode = String.valueOf(hashMap.get("code"));
        String message = String.valueOf(hashMap.get("code"));
        String statusCode = String.valueOf(hashMap.get("StatusCode"));
        Assert.assertEquals(hashMap.get(statusCode),"403","Status Code Is Not Equal 403 OK");
        Assert.assertEquals(hashMap.get(errorCode),"187","Error Code Is Not Equal 187");
        Assert.assertEquals(hashMap.get(message),"Status is a duplicate.","Error message is wrong");

    }

    @AfterMethod
    public void removeTweet(){
        twitterPublicAPI.postDestroyStatusesByID(String.valueOf(twitterPublicAPI.getStatusKeyValue().get("id_str")));
    }
}
