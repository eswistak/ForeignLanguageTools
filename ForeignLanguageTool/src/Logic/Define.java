/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import static java.net.URLEncoder.encode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Document;

/**
 *
 * @author Tim Kelly
 */

public class Define {
    private static List<String> LangCodes;
    private Define() {
        setLangCodes();
    }
    public static void setLangCodes(){
        String locNames = "window.LanguageDisplays.localNames = [";
        try{
            String items = sendGet("https://ssl.gstatic.com/inputtools/js/ln/16/en.js");
            items = items.substring(items.indexOf(locNames)+locNames.length(), items.indexOf("];"));
            LangCodes = new ArrayList<String>(Arrays.asList(items.split(",(?![^(]*\\))")));
        }catch(Exception exception){
            System.out.println(exception.toString());
        }
    }
    public static List<String> getLangCodes(){
        return LangCodes;
    }
    public static String getDefinition(String sourceLang, String targetLang, String sourceText){
        String items = "";
        try{
            String url="https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + sourceLang + "&tl=" + targetLang + "&dt=t&q=" + encode(sourceText, "UTF-8");
            try{
                items = sendGet(url);
                //items = items.substring(items.indexOf(locNames)+locNames.length(), items.indexOf("];"));
                return items;
            }catch(Exception exception){
                System.out.println(exception.toString());
            }
        }catch(UnsupportedEncodingException error){
            System.out.println(error.toString());
        }
        return items;
    }
    // HTTP GET request
    private static String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connect = (HttpURLConnection) obj.openConnection();
        connect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        BufferedReader in = new BufferedReader(
            new InputStreamReader(connect.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();

        return response.toString();

    }
    
   
    
    
}
