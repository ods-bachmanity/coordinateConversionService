package GeoSpatialConverter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class odsProcessorUtilities
{
    private static final String ODS_PROCESSOR_NAME = "coordinateConversion";
    private static final String ODS_PROCESSOR_VERSION = "2.0.0";

    /**
     * Method for generating JSON object that contains the values required for .
     * filling out the ODS.processor structure for the EMC return by the calling
     * service.
     * 
     * @return JSONObject containing array result of ODS processor information.
     * @throws JSONException
     * @since ODS sprint 10.2
     */
    public static JSONObject getOdsProcessorJson(String status) throws JSONException
    {
        JSONObject processorJSON = new JSONObject();  // Store processor information
        JSONObject processorsJSON = new JSONObject();  // Store processors
        JSONObject odsJSON = new JSONObject();
        
        processorJSON.put("status", status);
        processorJSON.put("timestamp", getEMCTimestamp());
        processorJSON.put("version", ODS_PROCESSOR_VERSION);
        
        processorsJSON.put(ODS_PROCESSOR_NAME, processorJSON);
        odsJSON.put("Processors", processorsJSON);
        
        return odsJSON;
    }

    private static String getEMCTimestamp()
    {
        return ZonedDateTime                // Represent a moment as perceived in the wall-clock time used by the people of a particular region ( a time zone).
                .now(                       // Capture the current moment.
                        ZoneId.of( "UTC" )  // Specify the time zone using proper Continent/Region name. Never use 3-4 character pseudo-zones such as PDT, EST, IST. 
                    )                       // Returns a `ZonedDateTime` object. 
                    .format(                // Generate a `String` object containing text representing the value of our date-time object. 
                        DateTimeFormatter.ofPattern( "uuuu-MM-dd'T'HH:mm:ss.SSSxxx" )  // Meet the example EMC field requirement 2018-05-24T16:36:32.937+00:00
                    );
    }
}