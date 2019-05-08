package restapitwitter;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TwitterPublicAPI {

    private String statuses = "/statuses/home_timeline.json";
    private String updateStatus = "/statuses/update.json";
    private String destroyStatuses = "/statuses/destroy/";
    public long uniqueStatus;

    RequestHelper requestHelper = new RequestHelper();

    public TwitterPublicAPI() {
        try {
            uniqueStatus = requestHelper.getCurrentEpochTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Map getStatusKeyValue(){
       // BasicConfigurator.configure();
        Response response = given()
                .spec(requestHelper.initSpec())
                .when()
                .get(statuses);

        JSONArray jsonResponse = new JSONArray(response.asString());

        Map<String, Object> mapStatusValue = new HashMap<String, Object>();
        mapStatusValue.put("created_at",jsonResponse.getJSONObject(0).getString("created_at"));
        mapStatusValue.put("text",jsonResponse.getJSONObject(0).getString("text"));
        mapStatusValue.put("retweet_count",jsonResponse.getJSONObject(0).getInt("retweet_count"));
        mapStatusValue.put("id_str",jsonResponse.getJSONObject(0).getString("id_str"));
        mapStatusValue.put("StatusCode",String.valueOf(response.getStatusCode()));

        return mapStatusValue;
    }

    public static void main(String[] args) {
        TwitterPublicAPI twitterPublicAPI = new TwitterPublicAPI();
       // twitterPublicAPI.getStatusKeyValue();
        twitterPublicAPI.postDublicateStatus("Agile tests12334");

    }

    public Map postUpdateStatus(String StatusText) {

        Response response = given()
                    .spec(requestHelper.initSpec())
                    .queryParam("status", StatusText)
                    .when()
                    .post(updateStatus);

        JSONObject jsonResponse = new JSONObject(response.asString());

        Map<String, Object> mapStatusValue = new HashMap<String, Object>();
        mapStatusValue.put("id_str",jsonResponse.getString("id_str"));
        mapStatusValue.put("text",jsonResponse.getString("text"));
        mapStatusValue.put("StatusCode",String.valueOf(response.getStatusCode()));
        
        return mapStatusValue;
    }

    public Map postDublicateStatus(String StatusText) {

        Response response = given()
                .spec(requestHelper.initSpec())
                .queryParam("status", StatusText)
                .when()
                .post(updateStatus);

        Map<String, Object> mapStatusValue = new HashMap<String, Object>();
        mapStatusValue.put("StatusCode",String.valueOf(response.getStatusCode()));
        mapStatusValue.put("code",String.valueOf(response.getBody().jsonPath().getInt("errors[0].code")));
        mapStatusValue.put("message",response.getBody().jsonPath().getString("errors[0].message"));

        System.out.println(String.valueOf(response.getStatusCode()));

        return mapStatusValue;
    }


    public Map postDestroyStatusesByID(String Status) {
        //BasicConfigurator.configure();

        Response response = given()
                .spec(requestHelper.initSpec())
                .when().post(destroyStatuses + Status + ".json");

        JSONObject jsonResponse = new JSONObject(response.asString());
        Map<String, Object> mapStatusValue = new HashMap<String, Object>();
        mapStatusValue.put("id_str",jsonResponse.getString("id_str"));
        mapStatusValue.put("text",jsonResponse.getString("text"));
        mapStatusValue.put("StatusCode",String.valueOf(response.getStatusCode()));

        return mapStatusValue;
    }


}
