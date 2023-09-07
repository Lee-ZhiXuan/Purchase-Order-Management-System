package Java_OOP_Assignment;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class DailySales extends Item{
    
    private String ItemID;
    private String ItemName;
    private int sales_amount;
    private String FileName;
    
    
    final static String DIR="Daily Sales Text File";
    final static String DIRNAME=DIR+"\\";
    
    public DailySales(){}
    
    @Override
    public void view()
    {
        ListDir();
        System.out.println("Select an entry to view(Input date as shown):\t(input 0 to back)");
        FileName = Sc.next();
        if (FileName.equals("0"))
        {}
        else 
        {
            if (CheckDir(FileName)==true)
            {
                DailySalesViewer(FileName);
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
        Date thisdate = new Date();
        SimpleDateFormat day= new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("Y");
            
        String date=day.format(thisdate)+month.format(thisdate)+year.format(thisdate);
        String filename=DIRNAME+date+".txt";
            
        System.out.println();
        Item It= new Item();
        It.view();
        System.out.println("Create/append daily sales entry for today("+date+"): ");
        File f = new File(filename);
        if (f.exists())
        {
            DailySalesViewer(date);
        }
        while (true)
        {
            System.out.println("Select Item ID: \t(input 0 to exit)");
            ItemID=Sc.next();
            
            if (ItemID.equals("0"))
            {
                break;
            }
            else
            {
                System.out.println("Input sales amount: ");
                sales_amount=Sc.nextInt();
                String Line= ItemID+" "+getItemName(ItemID)+" "+sales_amount;
                if (getItemName(ItemID).equals("placeholder"))
                {
                    
                }
                else{WriteLine(filename,Line);}
                
                System.out.println(StockUpdate(ItemID,sales_amount,false));
            }
        }        
    }
    
    @Override
    public void edit()
    {       
        ListDir();
        System.out.println("Select an entry to edit(Input date as shown):\t(input 0 to back)");
        FileName = Sc.next();
        String FilePath= DIRNAME+FileName+".txt";
        String BufferFilePath= DIRNAME+"Buffer.txt";
        if (FileName.equals("0")){}
        else 
        {
            if (CheckDir(FileName)==false)
            {
                System.out.println("Entry not found");
            }
            else
            {
                DailySalesViewer(FileName);
                File orifile = new File(FilePath);
                System.out.println("1. Edit sales amount\n2. Add new item sales\n3. Delete an entry\nInput any other integer value to exit.)");
                
                int act= Sc.nextInt();
                switch (act)
                {
                    case 1 -> {
                        try(Scanner SalesScanner = new Scanner (orifile)){
                            while (SalesScanner.hasNextLine())
                            {
                                ItemID=SalesScanner.next();
                                ItemName=SalesScanner.next();
                                sales_amount=SalesScanner.nextInt();
                                
                                System.out.println("Item ID: "+ItemID+"\tItem name: "+ItemName+"\nInitial sales amount: "+sales_amount);
                                System.out.println("Updated sales amount: ");
                                int updated_sales_amount=Sc.nextInt();
                                
                                String Line=ItemID+" "+ItemName+" "+updated_sales_amount;
                                
                                WriteLine(BufferFilePath,Line);
                                System.out.println(StockUpdate(ItemID,updated_sales_amount-sales_amount,false));  
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
                        ItemID=Sc.next();
                        System.out.println("Sales amount: ");
                        sales_amount=Sc.nextInt();
                        String Line= ItemID+" "+getItemName(ItemID)+" "+sales_amount;
                        WriteLine(FilePath,Line);
                        System.out.println(StockUpdate(ItemID,sales_amount,false));
                    }
                    case 3 ->{
                        int tracker=0;
                        System.out.println("Delete Item ID sales entry: ");
                        String Item_ID=Sc.next();
                        try(Scanner SalesScanner = new Scanner (orifile)){
                            while (SalesScanner.hasNextLine())
                            {
                                ItemID=SalesScanner.next();
                                ItemName=SalesScanner.next();
                                sales_amount=SalesScanner.nextInt();
                                
                                String Line=ItemID+" "+ItemName+" "+sales_amount;
                                if (Item_ID.equals(ItemID)){
                                    tracker++;
                                    System.out.println(StockUpdate(ItemID,sales_amount,true));
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
        ListDir();
        System.out.println("Select an entry to view(Input date as shown):\t(input 0 to back)");
        FileName = Sc.next();
        if (FileName.equals("0")){}
        else 
        {
            if (CheckDir(FileName)==false)
            {
                System.out.println("Entry not found.");
            }
            else
            {
                DailySalesViewer(FileName);
                
                System.out.println("Confirm delete? This action is inreversible!\n\t1. Yes\n\t2. No(Exit)");
                String exit=Sc.next();
                if(exit.equals("1")){
                    String filepath=DIRNAME+FileName+".txt";
                    File orifile = new File(filepath);
                    try(Scanner SalesScanner = new Scanner (orifile)){
                        while (SalesScanner.hasNextLine())
                        {
                            ItemID=SalesScanner.next();
                            ItemName=SalesScanner.next();
                            sales_amount=SalesScanner.nextInt();
                                
                            System.out.println(StockUpdate(ItemID,sales_amount, true));
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
        String s = "=".repeat(45);
        System.out.format("""
                         %s
                         Daily sales entry: %s
                         %s
                         """,s, path, s);
        System.out.println("Item ID\t\tItem Name\t\tSales");
        
        File DailySalesList = new File(DIRNAME+path+".txt");
        
        try(Scanner itemscanner = new Scanner (DailySalesList);)
        {
            while(itemscanner.hasNextLine())
            {
                ItemID=itemscanner.next();
                ItemName=itemscanner.next();
                sales_amount=itemscanner.nextInt();
                if(ItemName.length()<=7)
                {
                    System.out.println(ItemID+"\t\t"+ItemName+"\t\t\t"+sales_amount);
                }
                else 
                {
                    System.out.println(ItemID+"\t\t"+ItemName+"\t\t"+sales_amount);
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

    

