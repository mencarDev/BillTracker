package BillTrackPack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class InputOutput
{
    static String baseURL = "https://m.flsenate.gov/Bill/"; //A string that represents the base URL for bill search
    static String billNum; //A String that will be assigned the bill number
    static String billStringURL; //A string that will represent the URL for the desired bill
    static URL billUrl; //A URL object for the bill's url on the Florida senate website
    static ArrayList<Bill> Bills = new ArrayList<>();

    public static void URLGenerate(String bn) throws IOException
    {
        String billYear = bn.concat("/2026");
        billStringURL = baseURL.concat(billYear);
        billUrl = new URL(billStringURL);
    }

    public static void parseBillData(String html)
    {
        String billSponsor;
        String billNum;
        String billNam;
        
        Document doc = Jsoup.parse(html);
        

        Element originChamber = doc.selectFirst("title");
        String chamber = (originChamber != null) ? originChamber.text().trim().split(" ")[0] : "N/A";

        Element billName = doc.selectFirst("h1");
        String billNameText = (billName != null) ? billName.text() : "N/A";

        billNam = billNameText.substring(billNameText.indexOf(":") + 1).trim();
        billNum = billNameText.substring(0, billNameText.indexOf(":") + 1).trim();

        //System.out.println("Bill Number: " + billNameText.substring(0, billNameText.indexOf(":") + 1).trim());
        //System.out.println("Bill Name: " + billNameText.substring(billNameText.indexOf(":") + 1).trim());
        
        if (chamber.equals("Senate"))
        {
            Element sponsorE1 = doc.selectFirst("a[href^=/Senators/]");
            billSponsor = (sponsorE1 != null) ? sponsorE1.text() : "N/A";
            //System.out.println("Bill Sponsor: Sen. " + billSponsor);
        }
        else if (chamber.equals("House"))
        {
            Element sponsorE2 = doc.selectFirst("p:contains(GENERAL BILL by) span");
            billSponsor = (sponsorE2 != null) ? sponsorE2.text() : "N/A";
            //System.out.println("Bill Sponsor: Rep. " + billSponsor);
        }
        else
        {
            billSponsor = "N/A";
        }

        Bill bill = new Bill(billNum, billNam, billSponsor);
        Bills.add(bill);

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
                    //System.out.println(line);
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

    public static void generateReport() throws IOException
    {

    }

    public static void main(String[] args)
    {

        Scanner input = new Scanner(System.in);
        ArrayList<String> billsTracked = new ArrayList<>();
        String specBill = "null";
            

        System.out.println("Please enter the bill numbers of the bills you would like to track. Enter -1 to stop.");


        while (!(specBill.equals("-1")))
        {
            specBill = input.next();

            if (specBill.equals("-1"))
            {
                break;
            }
            else
            {
                billsTracked.add(specBill);  
            }
        }


        try
        {
            for (int i = 0; i < billsTracked.size(); i++)
            {
                URLGenerate(billsTracked.get(i));
                textFileCreate(billUrl);
            }
   
        }
        catch (IOException e)
        {
            System.out.println("An unexpected error has ocurred");
        }

        for (int i = 0; i < Bills.size(); i++)
        {
            Bills.get(i).getInfo();
        }
    }
}
