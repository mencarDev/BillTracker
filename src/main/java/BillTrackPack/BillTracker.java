package BillTrackPack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class BillTracker
{
    static String baseURL = "https://m.flsenate.gov/Bill/"; //A string that represents the base URL for bill search
    static String billNum; //A String that will be assigned the bill number
    static String billStringURL; //A string that will represent the URL for the desired bill
    static URL billUrl; //A URL object for the bill's url on the Florida senate website

    public static void URLGenerate(String bn) throws IOException
    {
        String billYear = bn.concat("/2026");
        billStringURL = baseURL.concat(billYear);
        billUrl = new URL(billStringURL);
    }

    public static void parseBillData(String html)
    {
        Document doc = Jsoup.parse(html);

        Element billNumberE1 = doc.selectFirst("h1.bill-number");
        String billNumber = (billNumberE1 != null) ? billNumberE1.text() : "N/A";

        Element billName = doc.selectFirst(".bill-title");
        String billNameText = (billName != null) ? billName.text() : "N/A";

        Element sponsorE1 = doc.selectFirst("a[href^=/Senators/]");
        String sponsor = (sponsorE1 != null) ? sponsorE1.text() : "N/A";

        System.out.println("Bill Number: " + billNumber);
        System.out.println("Bill Name: " + billNameText);
        System.out.println("Sponsor: " + sponsor);
    }
    
    public static void textFileCreate(URL bu) throws IOException
    {
        URLConnection connection = billUrl.openConnection();
        {
            File file = new File("billHTML.txt");
            FileWriter writer = new FileWriter(file);
            StringBuilder webcontent = new StringBuilder();

            try (Scanner input = new Scanner(connection.getInputStream()))
            {
                while (input.hasNextLine())
                {
                    String line = input.nextLine();
                    webcontent.append(line);
                    System.out.println(line);
                }
            }
            catch (Exception e)
            {
                System.out.println("An unexpected error has ocurred");
            }

            writer.write(webcontent.toString());
            writer.close();

            parseBillData(webcontent.toString());
        };
    }

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the desired bill number");
        
        billNum = input.next();
        
        try
        {
            URLGenerate(billNum);
            textFileCreate(billUrl);
        }
        catch (IOException e)
        {
            System.out.println("An unexpected error has ocurred");
        }
    }

}
