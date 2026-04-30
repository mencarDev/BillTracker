package BillTrackPack;

import java.util.ArrayList;
import java.util.Scanner;

public class BillInput
{
    public static void main(String[] args) {
        {
            Scanner input = new Scanner(System.in);
            ArrayList<Integer> billsTracked = new ArrayList<>();
            int specBill = 0;

            System.out.println("Please enter the bill numbers of the bills you would like to track. Enter -1 to stop.");


            while (specBill != -1)
            {
                specBill = input.nextInt();

                if (specBill < 0)
                {
                    continue;
                }
                else
                {
                    billsTracked.add(specBill);  
                }
            }

            System.out.println("Number of bills tracked: " + billsTracked.size() + "\n\nBills tracked: \n");
            for (int i = 0; i < billsTracked.size(); i++)
            {
                System.out.println(billsTracked.get(i));
            }
        }
    }
}