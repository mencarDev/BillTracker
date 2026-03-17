/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package BillTrackPack;

/**
 *
 * @author Marvin
 */

import java.util.Scanner;


class HTMLpull
{
    static String baseURL = "https://m.flsenate.gov/Bill/";

    public static void elementRetrieve(String bn) 
    {
        String billYear = bn.concat("/2026");
        String billURL = baseURL.concat(billYear);
    }
}

public class BillTracker 
{

    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
    }
}
