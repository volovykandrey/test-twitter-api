package restapitwitter;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonExtractor {
    public String jsonToString(Response res, String data) {
        String response = res.asString();
        JsonPath js = new JsonPath(response);
        return js.get(data).toString();
    }
}