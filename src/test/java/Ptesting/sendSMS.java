package Ptesting;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

// ATTN: Not working if in Sonova network. With external iPhone hotspot connection it works.

public class sendSMS {
    public static void main(String args[]) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        Result result = junit.run(sendSMS.class); // Replace "SampleTest" with the name of your class
        if (result.getFailureCount() > 0) {
            System.out.println("Test failed.");
            System.exit(1);
        } else {
            System.out.println("Test finished successfully.");
            System.exit(0);
        }
    }

    @Test
    public void testSendSms() {
        try {
            // Construct data
            String apiKey = "apikey=" + "kA2cmmXDzGQ-0DiRVCgyoItPgZAxSix3nSSgicueHu";
            String message = "&message=" + "This is a new test message";
            String sender = "&sender=" + "isunpro";
            String numbers = "&numbers=" + "0041764997935";
            String start = "/send/?";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            //return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            //return "Error "+e;
        }
    }

}
