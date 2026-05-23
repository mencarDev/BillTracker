package BillTrackPack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportBuilder
{
    static File newReport;

    public static void buildReport(ArrayList<Bill> b ) throws IOException
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

    public static void main(String[] args)
    {

    }

}