package BillTrackPack;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import static BillTrackPack.BillInput.billsTracked;

public class AppInterface
{
    static JFrame frame = new JFrame("Florida Bill Track");
    static JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    static JTextField tf_entry = new JTextField(25);
    static JButton genReport = new JButton("Generate Report");
    //static JScrollPane scrollPane = new JScrollPane();
    static PlainDocument doc = (PlainDocument) tf_entry.getDocument();
    

    public static void main(String[] args) 
    {
        doc.setDocumentFilter(new TextfieldFilter());

        genReport.addActionListener(e -> 
        {
            String input = tf_entry.getText().trim();
            if (!input.isEmpty())
            {
                String[] tokens = input.split("\\s+");
                for (String token : tokens)
                    billsTracked.add(Integer.parseInt(token));
            }
            System.out.println("Current bill list: " + billsTracked);
            tf_entry.setText(" ");
            });
    

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,350);
        frame.add(panel1, BorderLayout.NORTH);
        panel1.add(tf_entry);
        panel1.add(genReport);
        frame.setVisible(true);   

    }
    

}