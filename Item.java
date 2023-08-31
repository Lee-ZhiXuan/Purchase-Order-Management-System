
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
        System.out.println("Item ID\t\tItem Name\t\tItem Price\t\tItem Stock\t\tItem Restock Limit");
            
        File itemlist = new File("Item.txt");
        
            
        try(Scanner itemscanner = new Scanner (itemlist);){
            
            
            
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
    
    //unused but from interface for other class
    @Override 
    public void Create(String unused){}
    
    @Override
    public void Create()
    {
        while(true){
            View();
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
            
            String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim;
        
            WriteLine("Item.txt",Line);
            
        }

    }
    
    @Override
    public void Edit()
    {
        int tracker=0;
        View();
        while (true)
        {
            System.out.println("Select Item ID to edit: \t(Input 0 to back)");
            String EditItemID= Sc.nextLine();    
            if (EditItemID.equals("0"))
            {
                break;
            }
            else
            {
                File itemlist = new File("Item.txt");
                try(Scanner itemscanner = new Scanner (itemlist);){
            
                    while (itemscanner.hasNextLine())
                    {
                        ItemID = itemscanner.next();
                        ItemName=itemscanner.next();
                        ItemPrice=itemscanner.nextDouble();
                        ItemStock=itemscanner.nextInt();
                        ItemLim=itemscanner.nextInt();
                        String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim;
                    
                    
                        if (EditItemID.equals(ItemID))
                        {
                            tracker++;
                            System.out.println("Original item info:");
                            System.out.println(Line);
                            System.out.println("Input edited info:");
                            System.out.println("Item name:");
                            ItemName= Sc.nextLine();
                            System.out.println("Item price:");
                            ItemPrice= Sc.nextDouble();
                            System.out.println("Item stock:");
                            ItemStock= Sc.nextInt();
                            System.out.println("Item restock amount:");
                            ItemLim= Sc.nextInt();
                        
                            Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim;
                            
                        } 
                        
                        WriteLine("Buffer.txt",Line);
                    }

                }
                catch(IOException Ex)
                {
                    System.out.println("Error with file handling.");
                }
                finally
                {
                    if(tracker!=0)
                    {
                        System.out.println("Item ID edited");
                    }
                    else
                    {
                        System.out.println("Item ID not found");
                    }
                
                }
            }
        }
        Rename();
        
    }
    
    @Override
    public void Delete()
    {
        while(true)
        {
            int tracker=0;
            View();
            System.out.println("Delete item ID: \t(Input 0 to back)");
            String DelItem= Sc.nextLine();
            if (DelItem.equals("0"))
            {
                break;
            }
            else
            {
                File itemlist = new File("Item.txt");
                try(Scanner itemscanner = new Scanner (itemlist);){
            
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
                            tracker++;
                            String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim;
                            WriteLine("Buffer.txt",Line);
                    
                        }
                        else 
                        {
                            String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim;
                            WriteLine("Buffer.txt",Line);
                        }
                    }
                    
                    
                }
                catch(IOException Ex)
                {
                    System.out.println("Error with file handling.");
                }
                finally{
                    Rename();
                    if (tracker==0)
                    {
                        System.out.println("Item ID not found.");
                    }
                    else
                    {
                        System.out.println("Item listed as deleted.");
                    }
                }
            }
        }
        
    }
    
    
    
    protected static void WriteLine(String filename, String Line)
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
    
    private static void Rename()
    {
        
        File orifile = new File("Item.txt");

        if (orifile.delete()) 
        {
            
        }
        else 
        {
            System.out.println("Error with file handling(remove)");
        }
        
        File tempfile = new File("Buffer.txt");
        
        File rename = new File("Item.txt");
        
        boolean flag=tempfile.renameTo(rename);
        
        if(flag==true)
        {
            
        }
        else
        {
            System.out.println("Error with file handling(rename)");
        }
        
    }
    
    protected String StockUpdate(String itemId, int stock, boolean mode)
    {
        int tracker=0;
        
        File itemlist = new File("Item.txt");
        try(Scanner itemscanner = new Scanner (itemlist);)
        {
            while (itemscanner.hasNextLine())
            {
                ItemID = itemscanner.next();
                ItemName=itemscanner.next();
                ItemPrice=itemscanner.nextDouble();
                ItemStock=itemscanner.nextInt();
                ItemLim=itemscanner.nextInt();
                String Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim;                
                if (itemId.equals(ItemID))
                {
                    tracker++;
                    if (mode==true)
                    {
                        ItemStock=ItemStock+stock;
                    }
                    else
                    {
                        ItemStock=ItemStock-stock;
                    }
                    
                    Line=ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim;
                }
                WriteLine("Buffer.txt",Line);
            }
        }
        catch(IOException Ex)
        {
            System.out.println("Error with file handling.");
        }
        finally
        {
            Rename();
            if(tracker!=0)
            {
                return "Stock updated.";
            }
            else return "Item ID not found";
        }
    }
}
