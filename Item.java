
package SalesObjects;

import java.io.*;
import java.util.*;


public class Item implements SalesObject{
    private String ItemID;
    private String ItemName;
    private double ItemPrice;
    private int ItemStock;
    private int ItemLim;
    
    
    Scanner Sc = new Scanner(System.in);
    
    Item()
    {}
    
    @Override
    public void View()
    {
        System.out.println("\n=========================");
        System.out.println("\tItem List");
        System.out.println("=========================");
        System.out.println("Item ID\t\tItem Name\t\tItem Price\t\tItem Stock\t\tItem Restock Limit\n");
            
        try{
            
            
            File itemlist = new File("Item.txt");
            Scanner itemscanner = new Scanner (itemlist);
            
            while (itemscanner.hasNextLine())
            {
                ItemID = itemscanner.next();
                ItemName=itemscanner.next();
                ItemPrice=itemscanner.nextDouble();
                ItemStock=itemscanner.nextInt();
                ItemLim=itemscanner.nextInt();
                if (ItemLim==0)
                {
                    
                }
                else
                {
                    if (ItemName.length()<=7)
                    {
                        String ItemLine= ItemID+"\t\t"+ItemName+"\t\t\t"+ItemPrice+"\t\t\t"+ItemStock+"\t\t\t"+ItemLim;
                        System.out.println(ItemLine);
                    }
                    else 
                    {
                        String ItemLine= ItemID+"\t\t"+ItemName+"\t\t"+ItemPrice+"\t\t\t"+ItemStock+"\t\t\t"+ItemLim;
                        System.out.println(ItemLine);
                    }
                }
                
            }
            
        }
        catch(IOException Ex)
        {
            System.out.println("Error with file handling");
        }
        
        
        
        
    }
    
    @Override
    public void Create()
    {
        while(true){
            Item It = new Item();
            It.View();
            System.out.println();
            System.out.println("New Item Entry:\n");
            
            System.out.println("Item ID:\t(Input 0 to back)");
            ItemID= Sc.nextLine();
            if (ItemID.equals("0"))
            {
                break;
            }
            System.out.println("Item name:");
            ItemName= Sc.nextLine();
            System.out.println("Item price:");
            ItemPrice= Sc.nextDouble();
            System.out.println("Item stock:");
            ItemStock= Sc.nextInt();
            System.out.println("Item restock amount:");
            ItemLim= Sc.nextInt();    
            String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim ;
        
            It.WriteLine("Item.txt",Line);
            
        }

    }
    
    @Override
    public void Edit()
    {
        while(true){
            Item It = new Item();
            It.View();
            System.out.println("Select Item ID to edit: \t(Input 0 to back)");
            Sc.next();
            String EditItemID= Sc.nextLine();
            if (EditItemID.equals("0"))
            {
                break;
            }
            else
            {
                
                try{
            
                    File itemlist = new File("Item.txt");
                    Scanner itemscanner = new Scanner (itemlist);
            
                    int counter=0;
                    int tracker=0;
                    while (itemscanner.hasNextLine())
                    {
                        ItemID = itemscanner.next();
                        ItemName=itemscanner.next();
                        ItemPrice=itemscanner.nextDouble();
                        ItemStock=itemscanner.nextInt();
                        ItemLim=itemscanner.nextInt();
                        tracker++;
                        if (EditItemID.equals(ItemID))
                        {
                            String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim ;
                            It.WriteLine("Buffer.txt",Line);
                        } 
                        else 
                        {
                            String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim ;
                            It.WriteLine("Buffer.txt",Line);
                            counter++;
                        }
                    }
                    It.Rename();
                    if (tracker!=counter)
                    {
                        System.out.println("Item listed as deleted.");
                    }
                    else
                    {
                        System.out.println("Item ID not found");
                    }
                
                
                }
                catch(IOException Ex)
                {
                    System.out.println("Error with file handling.");
                }
            }
            
        }
        
    }
    
    @Override
    public void Delete()
    {
        while(true)
        {
            Item It = new Item();
            It.View();
            System.out.println("Delete item ID: \t(Input 0 to back)");
            String DelItem= Sc.nextLine();
            if (DelItem.equals("0"))
            {
                break;
            }
            else
            {
                try{
            
                    File itemlist = new File("Item.txt");
                    Scanner itemscanner = new Scanner (itemlist);
                    
            
                    while (itemscanner.hasNextLine())
                    {
                        ItemID = itemscanner.next();
                        ItemName=itemscanner.next();
                        ItemPrice=itemscanner.nextDouble();
                        ItemStock=itemscanner.nextInt();
                        ItemLim=itemscanner.nextInt();
                        if (DelItem.equals(ItemID))
                        {
                            ItemLim=0;
                    
                            String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim ;
                            It.WriteLine("Buffer.txt",Line);
                    
                        }
                        else 
                        {
                            String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim ;
                            It.WriteLine("Buffer.txt",Line);
                        }
                    }
                    It.Rename();
                    System.out.println("Item listed as deleted.");
                    
                }
                catch(IOException Ex)
                {
                    System.out.println("Error with file handling.");
                }
                
            }
        }
        
    }
    
    
    
    private void WriteLine(String filename, String Line)
    {
        
        try(FileWriter Fw = new FileWriter(filename,true);
            BufferedWriter Bw= new BufferedWriter(Fw);)
        {
            Bw.newLine();
            Bw.write(Line);
            Bw.flush();
        }
        catch(IOException Ex)
        {
            System.out.println("Error with file handling.");
        }
        
    }
    
    private void Rename()
    {
        
        File orifile = new File("Item.txt");

        if (orifile.delete()) 
        {
            System.out.println("Delete Successful.");
        }
        else 
        {
            System.out.println("Delete Failed");
        }
        
        File tempfile = new File("Buffer.txt");
        
        File rename = new File("Item.txt");
        
        boolean flag=tempfile.renameTo(rename);
        
        if(flag==true)
        {
            System.out.println("File renamed successfully.");
        }
        else
        {
            System.out.println("Error renaming file");
        }
        
    }
    
    
}
