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
        Element originChamber;
        Element billName;
        Element sponsor;

        String chamber;
        String billPrefix;
        String billNumber;
        String billNam;
        String sponsorTitle;
        String billSponsor;

        Document doc = Jsoup.parse(html);
        

        originChamber = doc.selectFirst("title");
        billName = doc.selectFirst("h1");


        chamber = (originChamber != null) ? originChamber.text().trim().split(" ")[0] : "N/A";

        billPrefix = (chamber.equals("Senate")) ? "SB" : "HB";
        sponsorTitle = (chamber.equals("Senate")) ? "Sen." : "Rep.";
        String billNameText = (billName != null) ? billName.text() : "N/A";
        billNam = billNameText.substring(billNameText.indexOf(":") + 1).trim();
        //billNum = billNameText.substring(0, billNameText.indexOf(":") + 1).trim();
        billNumber = billNum;

        if (chamber.equals("Senate"))
        {
            sponsor = doc.selectFirst("a[href^=/Senators/]");
            billSponsor = (sponsor != null) ? sponsor.text() : "N/A";
            //System.out.println("Bill Sponsor: Sen. " + billSponsor);
        }
        else if (chamber.equals("House"))
        {
            sponsor = doc.selectFirst("p:contains(GENERAL BILL by) span");
            billSponsor = (sponsor != null) ? sponsor.text() : "N/A";
            //System.out.println("Bill Sponsor: Rep. " + billSponsor);
        }
        else
        {
            billSponsor = "N/A";
        }

        Bill bill = new Bill(billPrefix, billNumber, billNam, sponsorTitle, billSponsor);
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
        //Scanner input = new Scanner(System.in);
        ArrayList<String> billsTracked = new ArrayList<>();
        String specBill = "null";
            

        //System.out.println("Please enter the bill numbers of the bills you would like to track. Enter -1 to stop.");


        /*while (!(specBill.equals("-1")))
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
        */

        try 
        {
            for (int i = 0; i < billsTracked.size(); i++) {
                billNum = billsTracked.get(i);
                URLGenerate(inputNew.billNum);
                textFileCreate(inputNew.billUrl);
            }
            for (int i = 0; i < inputNew.Bills.size(); i++)
                inputNew.Bills.get(i).getInfo();
            ReportBuilderNew.buildNewReport(inputNew.Bills);
            } 
            catch (IOException ex) 
            {
                System.getLogger(ConsolidatedBillTracker.class.getName()).log(System.Logger.Level.ERROR, "Error generating report", ex);
            }
        }
}
