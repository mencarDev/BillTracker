package BillTrackPack;

public class Bill
{
    private String number;
    private String name;
    private String sponsor;

    //Constructor
    public Bill(String no, String na, String spo)
    {
        number = no;
        name = na;
        sponsor = spo;
    }

    //Accessors
    public String getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public String getSponsor()
    {
        return sponsor;
    }

    public void getInfo()
    {
        System.out.println("Bill number: " + number);
        System.out.println("Bill name: " + name);
        System.out.println("Bill sponsor: " + sponsor);
        System.out.println();
    }

    //Mutators
    public void setNumber(String no)
    {
        number = no;
    }

    public void setName(String na)
    {
        name = na;
    }

    public void setSponsor(String spo)
    {
        sponsor = spo;
    }
}