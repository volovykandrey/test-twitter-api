package restapitwitter;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.oauth;

public class RequestHelper {

    private RequestSpecification spec;

    private String ConsumerKey = "test";
    private String ConsumerSecret = "test";
    private String AccessToken = "3367303659-test";
    private String TokenSecret = "test";
    private String BaseUri = "https://api.twitter.com/1.1/";


    public RequestSpecification initSpec(){

        spec = new RequestSpecBuilder()
                .setAuth(oauth(ConsumerKey, ConsumerSecret, AccessToken, TokenSecret))
                .setBaseUri(BaseUri)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
        return spec;
    }

    public long getCurrentEpochTime() throws ParseException {
        Date today = Calendar.getInstance().getTime();

        SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");

        String currentTime = crunchifyFormat.format(today);
        long epochTime;

        Date date = crunchifyFormat.parse(currentTime);

        epochTime = date.getTime();

        //log("Current Time in Epoch: " + epochTime);

        return epochTime;

    }

    public String createdDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss z yyyy");
        Date createDate = null;
        try {
            createDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd,yyyy HH:mm");

       return sdf2.format(createDate);

    }


}
