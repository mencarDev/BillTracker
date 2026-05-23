package BillTrackPack;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class ReportBuilder
{
    static File newReport;

    public static void buildNewReport(ArrayList<Bill> b ) throws IOException
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");


        String reportName = "Report-" + LocalDateTime.now().format(formatter).toString() + ".txt";
        newReport = new File(reportName);

        FileWriter writer = new FileWriter(newReport, true);
        BufferedWriter bWriter = new BufferedWriter(writer);

        for (int i = 0; i < b.size() ; i++)
        {
            Bill currentBill = b.get(i);

            String billPrefix = currentBill.getPrefix();
            String billNumber = currentBill.getNumber();
            String billName = currentBill.getName();
            String SponsorTitle = currentBill.getTitle();
            String billSponsor = currentBill.getSponsor();

            bWriter.write(billPrefix + billNumber);
            bWriter.newLine();
            bWriter.write(billName);
            bWriter.newLine();
            bWriter.write(SponsorTitle + " " + billSponsor);
            bWriter.newLine();
            bWriter.newLine();

        }
        bWriter.close();
    }
}

class input
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
}

public class ConsolidatedBillTracker
{
    static ArrayList<String> billsTracked = new ArrayList<>();
    static JFrame frame = new JFrame("Florida Bill Track");
    static JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    static JTextField tf_entry = new JTextField(25);
    static JButton genReport = new JButton("Generate Report");
    //static JScrollPane scrollPane = new JScrollPane();
    static PlainDocument doc = (PlainDocument) tf_entry.getDocument();
    

    public static void main(String[] args) 
    {
        doc.setDocumentFilter(new TextfieldFilter());

        genReport.addActionListener(e -> {
            String inputText = tf_entry.getText().trim();
            if (!inputText.isEmpty()) {
            String[] tokens = inputText.split("\\s+");
            for (String token : tokens)
            billsTracked.add(token);
        }
    tf_entry.setText("");

    try 
    {
        for (int i = 0; i < billsTracked.size(); i++) {
            input.billNum = billsTracked.get(i);
            input.URLGenerate(input.billNum);
            input.textFileCreate(input.billUrl);
        }
        for (int i = 0; i < input.Bills.size(); i++)
            input.Bills.get(i).getInfo();

        ReportBuilder.buildNewReport(input.Bills);
        } 
        catch (IOException ex) 
        {
            System.getLogger(ConsolidatedBillTracker.class.getName()).log(System.Logger.Level.ERROR, "Error generating report", ex);
        }
    });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.add(panel1, BorderLayout.NORTH);
        panel1.add(tf_entry);
        panel1.add(genReport);
        frame.setVisible(true);

    }
}

