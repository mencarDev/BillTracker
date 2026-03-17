/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package BillTrackPack;

/**
 *
 * @author Marvin
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class BillTracker 
{
    static String baseURL = "https://m.flsenate.gov/Bill/"; //A string that represents the base URL for bill search
    static String billNum; //A String that will be assigned the bill number
    static String billURL; //A string that will represent the URL for the desired bill

    //A method that concatenates the bill number with the bill year and concatenates the result to the baseURL to create a string for the URL
    public static void URLGenerate(String bn) 
    {
        String billYear = bn.concat("/2026");
        billURL = baseURL.concat(billYear);
    }

    //A method that turns the URL string into a URL object and opens a connection to the URL to extract the contents of the underlying HTML, building a String of those contents
    public static String getHTML(String urlString) throws IOException
    {
        StringBuilder htmlContent = new StringBuilder();
        URL url = new URL(billURL);
        URLConnection connection = url.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                htmlContent.append(line);
            }
        }
        catch (Exception e)
        {
            System.out.println("An unexpected error has ocurred");
        }

        return htmlContent.toString();

    }


    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the desired bill number");
        
        billNum = input.next();
        URLGenerate(billNum);
        
        try
        {
            String html = getHTML(billURL);
            System.out.println(html);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
