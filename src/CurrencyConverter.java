import org.json.JSONObject;
import org.omg.CORBA.Request;

import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {

        //USD, CAD, INR, EUR, HKY, etc;

        String fromCode, toCode;
        double amount;
        String token = "18566a937cabc3ba3114c51ae48ea6c0";

        Scanner sc =  new Scanner(System.in);

        System.out.println("Currency converting FROM?");
        fromCode = sc.nextLine().toUpperCase(Locale.ROOT);

        System.out.println("Currency converting TO?");
        toCode = sc.nextLine().toUpperCase(Locale.ROOT);

        System.out.println("Enter Amount: ");
        amount = sc.nextDouble();

        sendHttpGETRequest(fromCode, toCode, amount, token);

        System.out.println("Thank you!!");

    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount, String token) throws IOException {
        String GET_URL = "https://rates.hirak.site/rate.php?from="+fromCode+"&to="+toCode+"&token="+token;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }in.close();

            JSONObject obj = new JSONObject(response.toString());
            String exchangeRate = obj.getString("rate");
            System.out.println("Exchange Rate is: " + exchangeRate);
            System.out.println(amount + " " + fromCode + " = " + amount/Double.parseDouble(exchangeRate) + " " + toCode);
        }
        else {
            System.out.println("GET request failed!!");
        }

    }
}