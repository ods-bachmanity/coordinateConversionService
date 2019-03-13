package GeoSpatialConverter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import mil.nga.ods.geotrans.GeoTransMaster;

@RestController
public class geoSpatialConverter
{
    private GeoTransMaster geoTransMaster = new GeoTransMaster();
    private static final String API_PREFIX = "/v2/ods/coordinateConversion";

    @RequestMapping(value = API_PREFIX + "/doConversion", method = RequestMethod.POST)
    public ResponseEntity<String> doConversionPost(@RequestBody String jsonStr)
    {
        try
        {
            //JSONObject jsonObj = new JSONObject(jsonStr);
            //String jsonResultStr = geoTransMaster.doConversion(jsonObj.toString()).toString();
            JSONObject jsonRequestObj = new JSONObject(jsonStr);
            JSONObject jsonReturnObj = geoTransMaster.doConversion(jsonRequestObj.toString());
            jsonReturnObj.put("ODS", odsProcessorUtilities.getOdsProcessorJson("success"));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<String> (jsonReturnObj.toString(), httpHeaders, HttpStatus.OK);
        }
         catch (JSONException ex)
        {
             return new ResponseEntity<String> (ex.toString(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<String> (e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = API_PREFIX + "/doTranslation", method = RequestMethod.POST)
    public ResponseEntity<String> doTranslationPost(@RequestBody String jsonStr)
    {
        try
        {
            //JSONObject jsonObj = new JSONObject(jsonStr);
            //String jsonResultStr = geoTransMaster.doCoordinateTranslation(jsonObj.toString()).toString();
            JSONObject jsonRequestObj = new JSONObject(jsonStr);
            JSONObject jsonReturnObj = geoTransMaster.doCoordinateTranslation(jsonRequestObj.toString());
            jsonReturnObj.put("ODS", odsProcessorUtilities.getOdsProcessorJson("success"));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<String> (jsonReturnObj.toString(), httpHeaders, HttpStatus.OK);
        }
         catch (JSONException ex)
        {
             return new ResponseEntity<String> (ex.toString(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<String> (e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = API_PREFIX + "/ellipsoids", method = RequestMethod.GET)
    public ResponseEntity<String> getEllipsoids() throws Exception
    {
        String jsonResultStr = geoTransMaster.retrieveAvailableEllipsoids().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = API_PREFIX + "/datums", method = RequestMethod.GET)
    public ResponseEntity<String> getDatums() throws Exception
    {
        String jsonResultStr = geoTransMaster.retrieveAvailableDatums().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = API_PREFIX + "/coordinateTypes", method = RequestMethod.GET)
    public ResponseEntity<String> getCoordinateTypes() throws Exception
    {
        String jsonResultStr = geoTransMaster.retrieveAvailableCoordinateTypes().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = API_PREFIX + "/sourceCoordinateInputByType", method = RequestMethod.GET)
    public ResponseEntity<String> getSourceCoordinateInputByType() throws Exception
    {
        String jsonResultStr = geoTransMaster.retrieveSourceCoordinateInputByType().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = API_PREFIX, method = RequestMethod.GET)
    public ResponseEntity<String> getEndpoints() throws Exception
    {
        String resultStr = "Endpoints:\n/coordinateTypes\n/datums\n/doTranslation\n/doConversion\n/ellipsoids\n/sourceCoordinateInputByType\n/health";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String> (resultStr, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = API_PREFIX + "/health", method = RequestMethod.GET)
    public ResponseEntity<String> getHealthCheck() throws Exception
    {
        // Initialized return values for ODS structure.
        String statusStr = "I'm doing science and I'm still alive.";

        // Create object to store return and make the call
        JSONObject jsonReturnObj = new JSONObject();
        jsonReturnObj.put("ODS", odsProcessorUtilities.getOdsProcessorJson(statusStr));
        HttpHeaders httpHeaders = new HttpHeaders();

        // Prepare return
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String> (jsonReturnObj.toString(), httpHeaders, HttpStatus.OK);
    }
}