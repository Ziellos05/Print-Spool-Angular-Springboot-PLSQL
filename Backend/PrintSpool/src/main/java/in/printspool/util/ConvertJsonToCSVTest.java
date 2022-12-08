package in.printspool.util;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.json.*;
public class ConvertJsonToCSVTest {
   public static void main(String[] args) throws JSONException {
      String jsonArrayString = "{\"fileName\": [{\"first name\": \"Ravi\",\"last name\": \"Chandra\",\"location\": \"Bangalore\"}]}";
      JSONObject output;
      try {
         output = new JSONObject(jsonArrayString);
         JSONArray docs = output.getJSONArray("fileName");
         File file = new File("EmpDetails.csv");
         String csv = CDL.toString(docs);
         FileUtils.writeStringToFile(file, csv);
         System.out.println(jsonArrayString);
         System.out.println("Data has been Sucessfully Writeen to "+ file);
         System.out.println(csv);
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
}
