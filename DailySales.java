package SalesObjects;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class DailySales extends Item{
    
    private int SalesAmount;
    
    private String FileName;
    
    
    final static String DIR="C:\\Users\\bryan\\OneDrive\\Documents\\NetBeansProjects\\PurchaseManagementSystem\\DailySales";
    final static String DIRNAME=DIR+"\\";
    
    public DailySales(){}
    
    public int getSalesAmount()
    {
        return SalesAmount;
    }
    
    public void setSalesAmount(int SalesAmount)
    {
        this.SalesAmount=SalesAmount;
    }
    
    public String getFileName()
    {
        return FileName;
    }
    
    public void setFileName(String FileName)
    {
        this.FileName=FileName;
    }
    
    @Override
    public void view()
    {
        DailySales ds= new DailySales();
        ListDir();
        System.out.println("Select an entry to view(Input date as shown):\t(input 0 to back)");
        ds.setFileName(Sc.next());
        if (ds.getFileName().equals("0"))
        {}
        else 
        {
            if (CheckDir(ds.getFileName())==true)
            {
                DailySalesViewer(ds.getFileName());
            }
            else
            {
                System.out.println("Entry not found");
            }
            while (true)
            {
                System.out.println("Input any value to exit: ");
                String exit=Sc.next();
                if (exit.length()>=1)
                {
                    break;
                }
            }
        }  
    }
    
    @Override
    public void create()
    {      
        DailySales ds= new DailySales();
        Date thisdate = new Date();
        SimpleDateFormat day= new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("Y");
            
        String date=day.format(thisdate)+month.format(thisdate)+year.format(thisdate);
        ds.setFileName(DIRNAME+date+".txt");
            
        System.out.println();
        Item It= new Item();
        It.view();
        System.out.println("Create/append daily sales entry for today("+date+"): ");
        File f = new File(ds.getFileName());
        if (f.exists())
        {
            DailySalesViewer(date);
        }
        while (true)
        {
            System.out.println("Select Item ID: \t(input 0 to exit)");
            ds.setItemID(Sc.next());
            
            if (ds.getItemID().equals("0"))
            {
                break;
            }
            else
            {
                System.out.println("Input sales amount: ");
                ds.setSalesAmount(Sc.nextInt());
                String Line= ds.getItemID()+" "+ds.getItemName(ds.getItemID())+" "+ds.getSalesAmount();
                if (ds.getItemName(ds.getItemID()).equals("placeholder"))
                {
                    System.out.println("Item not found.");
                }
                else{WriteLine(ds.getFileName(),Line);}
                
                System.out.println(StockUpdate(ds.getItemID(),ds.getSalesAmount(),false));
            }
        }        
    }
    
    @Override
    public void edit()
    {       
        DailySales ds=new DailySales();
        ListDir();
        System.out.println("Select an entry to edit(Input date as shown):\t(input 0 to back)");
        ds.setFileName(Sc.next());
        String FilePath= DIRNAME+ds.getFileName()+".txt";
        String BufferFilePath= DIRNAME+"Buffer.txt";
        if (ds.getFileName().equals("0")){}
        else 
        {
            if (CheckDir(ds.getFileName())==false)
            {
                System.out.println("Entry not found");
            }
            else
            {
                DailySalesViewer(ds.getFileName());
                File orifile = new File(FilePath);
                System.out.println("1. Edit sales amount\n2. Add new item sales\n3. Delete an entry\nInput any other integer value to exit.)");
                
                int act= Sc.nextInt();
                switch (act)
                {
                    case 1 -> {
                        try(Scanner SalesScanner = new Scanner (orifile)){
                            while (SalesScanner.hasNextLine())
                            {
                                ds.setItemID(SalesScanner.next());
                                ds.setItemName(SalesScanner.next());
                                ds.setSalesAmount(SalesScanner.nextInt());
                                
                                System.out.println("Item ID: "+ds.getItemID()+"\tItem name: "+ds.getItemName()+"\nInitial sales amount: "+ds.getSalesAmount());
                                System.out.println("Updated sales amount: ");
                                int updated_sales_amount=Sc.nextInt();
                                
                                String Line=getItemID()+" "+getItemName()+" "+updated_sales_amount;
                                
                                WriteLine(BufferFilePath,Line);
                                System.out.println(StockUpdate(ds.getItemID(),updated_sales_amount-ds.getSalesAmount(),false));  
                            }
                        }
                        catch(IOException Ex)
                        {
                            System.out.println("error with scanner");
                        }
                        finally
                        {
                            Rename(FilePath);
                        }
                    }   
                    case 2 ->{
                        Item it=new Item();
                        it.view();
                        System.out.println("New Item ID: ");
                        ds.setItemID(Sc.next());
                        System.out.println("Sales amount: ");
                        ds.setSalesAmount(Sc.nextInt());
                        String Line= ds.getItemID()+" "+ds.getItemName(ds.getItemID())+" "+ds.getSalesAmount();
                        WriteLine(FilePath,Line);
                        System.out.println(StockUpdate(ds.getItemID(),ds.getSalesAmount(),false));
                    }
                    case 3 ->{
                        int tracker=0;
                        System.out.println("Delete Item ID sales entry: ");
                        String Item_ID=Sc.next();
                        try(Scanner SalesScanner = new Scanner (orifile)){
                            while (SalesScanner.hasNextLine())
                            {
                                ds.setItemID(SalesScanner.next());
                                ds.setItemName(SalesScanner.next());
                                ds.setSalesAmount(SalesScanner.nextInt());
                                
                                String Line=ds.getItemID()+" "+ds.getItemName()+" "+ds.getSalesAmount();
                                if (Item_ID.equals(ds.getItemID())){
                                    tracker++;
                                    System.out.println(StockUpdate(ds.getItemID(),ds.getSalesAmount(),true));
                                }
                                else{WriteLine(BufferFilePath,Line);}
                                
                            }
                        }
                        catch(IOException Ex)
                        {
                            System.out.println("error with scanner");
                        }
                        
                        Rename(FilePath);
                        if (tracker==0)
                        {
                            System.out.println("Item ID not found");
                        }
                    }
                    default ->{}      
                }
            }
        }
    }
    
    @Override
    public void delete()
    {
        DailySales ds= new DailySales();
        ListDir();
        System.out.println("Select an entry to view(Input date as shown):\t(input 0 to back)");
        ds.setFileName(Sc.next());
        if (ds.getFileName().equals("0")){}
        else 
        {
            if (CheckDir(ds.getFileName())==false)
            {
                System.out.println("Entry not found.");
            }
            else
            {
                DailySalesViewer(ds.getFileName());
                
                System.out.println("Confirm delete? This action is inreversible!\n\t1. Yes\n\t2. No(Exit)");
                String exit=Sc.next();
                if(exit.equals("1")){
                    String filepath=DIRNAME+ds.getFileName()+".txt";
                    File orifile = new File(filepath);
                    try(Scanner SalesScanner = new Scanner (orifile)){
                        while (SalesScanner.hasNextLine())
                        {
                            ds.setItemID(SalesScanner.next());
                            ds.setItemName(SalesScanner.next());
                            ds.setSalesAmount(SalesScanner.nextInt());
                                
                            System.out.println(StockUpdate(ds.getItemID(),ds.getSalesAmount(), true));
                        }
                    }
                    catch(IOException Ex)
                    {
                        System.out.println("Error");
                    }
                    if (orifile.delete()) 
                    {
                        System.out.println("Daily sales entry deleted. Stock updated.");
                    }
                    else 
                    {
                        System.out.println("Error with file handling(remove)");
                    }
       
                }
            }        
        } 
    }
    
    private static void ListDir()
    {
        System.out.print("""
                           ========================
                           Daily sales report dates
                           ========================
                           """);
        File salesdir= new File(DIR);
        String[]s = salesdir.list();
        
        int i = 1;
        for(String s1:s)
        {
            File f= new File(salesdir,s1);
            if(f.isFile())
            {
                String[] str=s1.split(".t",2);
                System.out.println(i + ". " + str[0]);
                i++;
            }
        }
    }
    
    private static boolean CheckDir(String choice)
    {
        boolean stat=false;
        File salesdir= new File(DIR);
        String[]s = salesdir.list();
        for(String s1:s)
        {
            File f= new File(salesdir,s1);
            if(f.isFile())
            {
                String[] str=s1.split(".t",2);
                if (str[0].equals(choice)==true)
                {
                    stat = true;
                }
            } 
        } 
        return stat;
    }
    
    private void DailySalesViewer(String path)
    {
        DailySales ds= new DailySales();
        ds.setFileName(path);
        String s = "=".repeat(45);
        System.out.format("""
                         %s
                         Daily sales entry: %s
                         %s
                         """,s, ds.getFileName(), s);
        System.out.println("Item ID\t\tItem Name\t\tSales");
        
        File DailySalesList = new File(DIRNAME+ds.getFileName()+".txt");
        
        try(Scanner itemscanner = new Scanner (DailySalesList);)
        {
            while(itemscanner.hasNextLine())
            {
                ds.setItemID(itemscanner.next());
                ds.setItemName(itemscanner.next());
                ds.setSalesAmount(itemscanner.nextInt());
                if(ds.getItemName().length()<=7)
                {
                    System.out.println(ds.getItemID()+"\t\t"+ds.getItemName()+"\t\t\t"+ds.getSalesAmount());
                }
                else 
                {
                    System.out.println(ds.getItemID()+"\t\t"+ds.getItemName()+"\t\t"+ds.getSalesAmount());
                }     
            } 
            
            System.out.println(s);
        }
        catch(IOException Ex)
        {
            System.out.println("Error reading file.");
        }
    }
    
    private static void Rename(String FilePath)
    {      
        File orifile = new File(FilePath);

        if (orifile.delete()){}
        else 
        {
            System.out.println("Error with file handling(remove)");
        }
        
        String Buffer=DIRNAME+"Buffer.txt";
        File tempfile = new File(Buffer);
        
        File rename = new File(FilePath);
        
        boolean flag=tempfile.renameTo(rename);
        
        if(flag==true){}
        else
        {
            System.out.println("Error with file handling(rename)");
        }      
    }   
}   

    

