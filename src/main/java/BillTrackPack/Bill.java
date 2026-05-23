package BillTrackPack;

public class Bill
{
    private String prefix;
    private String number;
    private String name;
    private String title;
    private String sponsor;

    //Constructor
    public Bill(String p, String no, String na, String t, String spo)
    {
        prefix = p;
        number = no;
        name = na;
        title = t;
        sponsor = spo;
    }

    //Accessors
    public String getPrefix()
    {
        return prefix;
    }

    public String getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public String getTitle()
    {
        return title;
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
    public void setPrefix(String p)
    {
        prefix = p;
    }

    public void setNumber(String no)
    {
        number = no;
    }

    public void setName(String na)
    {
        name = na;
    }

    public void setTitle(String t)
    {
        title = t;
    }

    public void setSponsor(String spo)
    {
        sponsor = spo;
    }
}