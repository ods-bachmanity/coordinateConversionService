package GeoSpatialConverter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpStatus;
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
            return new ResponseEntity<String> (jsonResultStr, HttpStatus.OK);
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
            JSONObject jsonResultStr = geoTransMaster.doCoordinateTranslation(jsonObj.toString());
            return new ResponseEntity<String> (jsonResultStr.toString(), HttpStatus.OK);
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

    @RequestMapping("/ellipsoids")
    public String getEllipsoids() throws Exception
    {
        return geoTransMaster.retrieveAvailableEllipsoids().toString();
    }
    
    @RequestMapping("/datums")
    public String getDatums() throws Exception
    {
       return geoTransMaster.retrieveAvailableDatums().toString();
    }
}