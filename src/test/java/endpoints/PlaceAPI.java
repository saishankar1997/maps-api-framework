package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.AddPlace;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PlaceAPI {
    private static final String KEY = utils.ConfigLoader.getApiKey();

    public static Response addPlace(AddPlace place) {
        return given()
                .queryParam("key", KEY)
                .contentType(ContentType.JSON)
                .body(place)
        .when()
                .post("/maps/api/place/add/json");
    }

    public static Response getPlace(String placeId) {
        return given()
                .queryParam("key", KEY)
                .queryParam("place_id", placeId)
        .when()
                .get("/maps/api/place/get/json");
    }

    public static Response updatePlace(String placeId, String newAddress) {
        Map<String, String> payload = new HashMap<>();
        payload.put("place_id", placeId);
        payload.put("address", newAddress);
        payload.put("key", KEY);

        return given()
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .put("/maps/api/place/update/json");
    }

    public static Response deletePlace(String placeId) {
        Map<String, String> payload = new HashMap<>();
        payload.put("place_id", placeId);

        return given()
                .queryParam("key", KEY)
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .post("/maps/api/place/delete/json");
    }
}
