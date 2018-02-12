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
    
    @RequestMapping(value = "/doConversion", method = RequestMethod.POST)
    public ResponseEntity<String> doConversionPost(@RequestBody String jsonStr)
    {
        try
        {
            JSONObject jsonObj = new JSONObject(jsonStr);
            String jsonResultStr = geoTransMaster.doConversion(jsonObj.toString()).toString();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
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

    @RequestMapping(value = "/doTranslation", method = RequestMethod.POST)
    public ResponseEntity<String> doTranslationPost(@RequestBody String jsonStr)
    {
        try
        {
            JSONObject jsonObj = new JSONObject(jsonStr);
            String jsonResultStr = geoTransMaster.doCoordinateTranslation(jsonObj.toString()).toString();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
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

    @RequestMapping(value = "/ellipsoids", method = RequestMethod.GET)
    public ResponseEntity<String> getEllipsoids() throws Exception
    {
        String jsonResultStr = geoTransMaster.retrieveAvailableEllipsoids().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/datums", method = RequestMethod.GET)
    public ResponseEntity<String> getDatums() throws Exception
    {
        String jsonResultStr = geoTransMaster.retrieveAvailableDatums().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/coordinateTypes", method = RequestMethod.GET)
    public ResponseEntity<String> getCoordinateTypes() throws Exception
    {
        String jsonResultStr = geoTransMaster.retrieveAvailableCoordinateTypes().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String> (jsonResultStr, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getEndpoints() throws Exception
    {
        String resultStr = "Endpoints:\n/ellipsoids\n/datums\n/coordinateTypes\n/doTranslation\n/doConversion\n";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String> (resultStr, httpHeaders, HttpStatus.OK);
    }
}