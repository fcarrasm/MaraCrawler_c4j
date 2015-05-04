package es.maracrowler.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class MaraUtils {

    /**
     * write data in a file.
     * 
     * @param fileName
     * @param sb
     * @param inLine
     */
	public static void writeFileLine(String fileName, String sb, boolean inLine) {
		File fout = new File(fileName);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fout,true);
		 	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		    System.out.println("[writeFileLine] writing {"+sb+"} in {"+fileName+"}");
		    
   		bw.write(sb);
   		if(!inLine){
   			bw.newLine();
   		}
    		
		 	bw.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Get properties
	 * 
	 * @param fileName
	 * @param propertie
	 * 
	 * @return propertie value
	 */
	public static String getPropertie (String fileName, String propertie){
		Properties prop = new Properties();
		InputStream input = null;
	 
		String result = "";
		try {
	 
			input = new FileInputStream(fileName);
	 
			// load a properties file
			prop.load(input);
	 
			// get the property value
			result = prop.getProperty(propertie);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	 /**
     * Convert a millisecond duration to a string format
     * 
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    public static String getDurationBreakdown(long millis)
    {
        if(millis < 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return(sb.toString());
    }
}
