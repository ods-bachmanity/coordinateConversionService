package GeoSpatialConverter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class odsProcessorUtilities
{
    // Config file to read properties from
    private static final String CONFIG_FILE_NAME = "config.properties";
    // Back up values in case config file can't be read.
    private static final String ODS_PROCESSOR_NAME = "default_name";
    private static final String ODS_PROCESSOR_VERSION = "default_version";

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

        Properties properties = getConfigProperties(CONFIG_FILE_NAME);

        // Get values from properties if they exist, or use defaults
        String processorName = (properties.containsKey("ODS_PROCESSOR_NAME")) ? properties.getProperty("ODS_PROCESSOR_NAME") : ODS_PROCESSOR_NAME;
        String processorVersion = (properties.containsKey("ODS_PROCESSOR_VERSION")) ? properties.getProperty("ODS_PROCESSOR_VERSION") : ODS_PROCESSOR_VERSION;

        processorJSON.put("status", status);
        processorJSON.put("timestamp", getEMCTimestamp());
        processorJSON.put("version", processorVersion);


        processorsJSON.put(processorName, processorJSON);
        odsJSON.put("Processors", processorsJSON);

        return odsJSON;
    }

    /**
     * Method for generating a timestamp string that matches the timestamp 
     * format expected by the EMC.  
     * 
     * @return String containing timestamp in EMC format.  ex: 2019-03-13T19:25:29.175+00:00
     * @since ODS sprint 10.2
     */
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

    /**
     * Method for loading a properties file into a Properties object to be
     * returned to the caller.  Will attempt to load from file passed in,
     * otherwise an empty Properties object will be returned.
     * 
     * @return Properties object containing values loaded from propertiesFile.
     * @since ODS sprint 10.2
     */
    private static Properties getConfigProperties(String propertiesFile)
    {
        Properties prop = new Properties();
        InputStream input = null;

        try
        {
            input = odsProcessorUtilities.class.getClassLoader().getResourceAsStream(propertiesFile);
            if(input==null)
            {
                System.out.println("Sorry, unable to find " + propertiesFile);
                return prop;
            }

            //load a properties file from class path, inside static method
            prop.load(input);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(input!=null)
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }
}