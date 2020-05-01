package eventorganiser.dir.UtilityFunctions.Maps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventorganiser.dir.DBMethods.DBClasses.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


public class MapFunctions {


    public static String makeEventLocGoogleMapsApiQryStr(Event event) {

        // Base URI
        String uriBase = "https://maps.googleapis.com/maps/api/geocode/json?address=";

        // Convert event loc details in 'address' query parameter. Example of final format needed for 'address' param for API is: address=4+Blackberry+Hill+null+Bristol+BS16+1DB&

        String address = event.getEventLocSt1() + "+";
        if(event.getEventLocSt2().isBlank())
            address += event.getEventLocSt2() + "+";
        address += event.getEventLocCity() + "+" + event.getEventLocPost();
        address = address.replace(" ", "+");
//        ArrayList<String> uriQryLi = new ArrayList<>();
//        uriQryLi.add(evntJson.path("eventLocSt1").textValue() + "+");
//        if ( evntJson.path("eventLocSt2").textValue().isBlank() ) {
//            uriQryLi.add(evntJson.path("evntLocSt2").textValue() + "+"); }
//        uriQryLi.add(evntJson.path("eventLocCity").textValue() + "+");
//        uriQryLi.add(evntJson.path("eventLocPost").textValue());
//        String address = String.join("", uriQryLi);
//        String addressStr = address.replace(" ", "+");

        // Concatenating query string
        String uriQry = "?address=" + address; 

        // returning full API req in the following format: https://maps.googleapis.com/maps/api/geocode/json?address=<address>&key=<key>
        return uriBase + uriQry;
    }

    public static String getEventLocAndLngFromGoogleMapsApi(String uri) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        JsonNode locJson = mapper.readTree(response.getBody());
        String lat = locJson.findPath("lat").toString();
        String lng= locJson.findPath("lng").toString();
        return "?lat=" + lat + "&lng=" + lng;
    }
}
