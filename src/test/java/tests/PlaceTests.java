package tests;

import base.BaseTest;
import endpoints.PlaceAPI;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.AddPlace;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;

public class PlaceTests extends BaseTest {

    String placeId;

    @Test (priority = 1)
    public void testAddPlace() {
        AddPlace place = new AddPlace();
        place.setAccuracy(50);
        place.setName("Dream works house");
        place.setPhone_number("(+91) 983 893 3937");
        place.setAddress("29, side layout, cohen 09");
        place.setWebsite("http://google.com");
        place.setLanguage("Telugu-IN");
        place.setTypes(Arrays.asList("National park", "Park"));

        AddPlace.Location location = new AddPlace.Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        place.setLocation(location);

        Response response = PlaceAPI.addPlace(place);

        response.then().log().all()
                .statusCode(200)
                .body("status", equalTo("OK"))
                .body("place_id", not(isEmptyOrNullString()));

        placeId = response.jsonPath().getString("place_id");
    }

    @Test (priority = 2, dependsOnMethods = "testAddPlace")
    public void testGetPlace() {
        PlaceAPI.getPlace(placeId)
                .then().log().all()
                .statusCode(200)
                .body("name", equalTo("Dream works house"))
                .body("address", equalTo("29, side layout, cohen 09"))
                .body("language", equalTo("Telugu-IN"));
    }

    @Test (priority = 3, dependsOnMethods = "testGetPlace")
    public void testUpdatePlace() {
        String newAddress = "70 Summer walk, USA";
        PlaceAPI.updatePlace(placeId, newAddress)
                .then().log().all()
                .statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        // Re-validate with GET
        PlaceAPI.getPlace(placeId)
                .then().log().all()
                .statusCode(200)
                .body("address", equalTo(newAddress));
    }

    @Test (priority = 4, dependsOnMethods = "testUpdatePlace")
    public void testDeletePlace() {
        PlaceAPI.deletePlace(placeId)
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("OK"));
    }
}
