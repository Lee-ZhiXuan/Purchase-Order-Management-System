/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SalesObjects;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class DailySales extends Item{
    
    private String ItemID;
    private String ItemName;
    private int sales_amount;
    private String FileName;
    
    final static String DIRNAME="C:\\Users\\bryan\\OneDrive\\Documents\\NetBeansProjects\\PurchaseManagementSystem\\DailySales\\";
    DailySales(){}
    
    @Override
    public void View()
    {
        ListDir();
        System.out.println("Select an entry to view(Input date as shown):\t(input 0 to back)");
        FileName = Sc.next();
        if (FileName.equals("0"))
        {
            
        }
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
    public void Create()
    {
        
        Date thisdate = new Date();
        SimpleDateFormat day= new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("Y");
            
        String date=day.format(thisdate)+month.format(thisdate)+year.format(thisdate);
        String filename=DIRNAME+date+".txt";
            
        System.out.println();
        Item It= new Item();
        It.View();
        System.out.println("Create daily sales entry for today("+date+"): ");
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
                WriteSalesLine(filename,Line);
                System.out.println(StockUpdate(ItemID,sales_amount,false));
            }
        }
        
    }
    
    @Override
    public void Edit()
    {       
        ListDir();
        System.out.println("Select an entry to view(Input date as shown):\t(input 0 to back)");
        FileName = Sc.next();
        String FilePath= DIRNAME+FileName+".txt";
        if (FileName.equals("0"))
        {
            
        }
        else 
        {
            
            if (CheckDir(FileName)==false)
            {
                System.out.println("Entry not found");
            }
            else
            {
                DailySalesViewer(FileName);
                File DailySalesList = new File(FilePath);
                System.out.println("1. Edit sales entry\n(Input any other value to exit.)");
                
                int act= Sc.nextInt();
                if (act==1){
                    Item It= new Item();
                    It.View();
                    try(Scanner SalesScanner = new Scanner (DailySalesList);){
                            
                        while (SalesScanner.hasNextLine())
                        {
                            ItemID=SalesScanner.next();
                            ItemName=SalesScanner.next();
                            sales_amount=SalesScanner.nextInt();
                            System.out.println("Item ID: ");
                            String NewItemID= Sc.next();
                            System.out.println("Sales amount: ");
                            int new_sales_amount=Sc.nextInt();
                                
                            if (NewItemID.equals(ItemID))
                            {
                                if (sales_amount==new_sales_amount)
                                {
                                    
                                }
                                else if (sales_amount>new_sales_amount)
                                {
                                    StockUpdate(ItemID,sales_amount-new_sales_amount, true);
                                }
                                else 
                                {
                                    StockUpdate(ItemID, new_sales_amount-sales_amount,false);
                                }
                                String Line=ItemID+" "+ItemName+" "+new_sales_amount;
                                FileName=DIRNAME+"Buffer.txt";
                                WriteLine(FileName,Line);
                            }
                            else 
                            {
                                String Line=NewItemID+" "+ItemName+" "+sales_amount;
                                FileName=DIRNAME+"Buffer.txt";
                                WriteLine(FileName,Line);
                            }
                        }
                            

                    }
                    catch(IOException Ex)
                    {
                        System.out.println("Error with file scanner.");
                    }
                    finally
                    {
                        Rename(DIRNAME+FileName+".txt");
                    }
                }
                else{}
            }
            
        }
    }
    
    @Override
    public void Delete()
    {
        ListDir();
        System.out.println("Select an entry to view(Input date as shown):\t(input 0 to back)");
        FileName = Sc.next();
        if (FileName.equals("0"))
        {
            
        }
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
                int exit=Sc.nextInt();
                if(exit==1){
                    String filepath="C:\\Users\\bryan\\OneDrive\\Documents\\NetBeansProjects\\PurchaseManagementSystem\\DailySales\\"+FileName+".txt";
                    File orifile = new File(filepath);
                    try(Scanner SalesScanner = new Scanner (filepath)){
                        while (SalesScanner.hasNextLine())
                        {
                            ItemID=SalesScanner.next();
                            ItemName=SalesScanner.next();
                            sales_amount=SalesScanner.nextInt();
                                
                            System.out.println(StockUpdate(ItemID,sales_amount, true));
                        }
                    }
                    catch(java.util.InputMismatchException e)
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
        System.out.println("Daily sales report dates:");
        File salesdir= new File("C:\\Users\\bryan\\OneDrive\\Documents\\NetBeansProjects\\PurchaseManagementSystem\\DailySales");
        String[]s = salesdir.list();
        for(String s1:s)
        {
            File f= new File(salesdir,s1);
            if(f.isFile())
            {
                String[] str=s1.split(".t",2);
                System.out.println(str[0]);
            }
        }
    }
    
    private static boolean CheckDir(String choice)
    {
        boolean stat=false;
        File salesdir= new File("C:\\Users\\bryan\\OneDrive\\Documents\\NetBeansProjects\\PurchaseManagementSystem\\DailySales");
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
        System.out.println("Daily sales entry: " +path);
        System.out.println("=========================");
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
            
        }
        catch(IOException Ex)
        {
            System.out.println("Error reading file.");
        }
    }
    
    protected static void WriteSalesLine(String filename, String Line)
    {
        try(FileWriter Fw = new FileWriter(filename,true);BufferedWriter Bw= new BufferedWriter(Fw);)
        {
            Bw.newLine();
            Bw.write(Line);
            Bw.flush();
        }
        catch(IOException Ex)
        {
            System.out.println("Error with file handling.(Write)");
        }
    }
    
    private static void Rename(String FilePath)
    {
        
        File orifile = new File(FilePath);

        if (orifile.delete()) 
        {
            
        }
        else 
        {
            System.out.println("Error with file handling(remove)");
        }
        
        String Buffer=DIRNAME+"Buffer.txt";
        File tempfile = new File(Buffer);
        
        File rename = new File(FilePath);
        
        boolean flag=tempfile.renameTo(rename);
        
        if(flag==true)
        {
            
        }
        else
        {
            System.out.println("Error with file handling(rename)");
        }
        
    }
    
}   

    

