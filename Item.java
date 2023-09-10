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
    String filePath = "Item.txt";
    String filePath2 = "Item_Buffer.txt";
    
    public Item(){}
    public Item(String ItemID, String ItemName, double ItemPrice, int ItemStock, int ItemLim)
    {
        this.ItemID=ItemID;
        this.ItemName=ItemName;
        this.ItemPrice=ItemPrice;
        this.ItemStock=ItemStock;
        this.ItemLim=ItemLim;
    }
    
    public String getItemID()
    {
        return ItemID;
    }
    
    public void setItemID(String ItemID)
    {
        this.ItemID=ItemID;
    }
    
    public String getItemName()
    {
        return ItemName;
    }
    
    public void setItemName(String ItemName)
    {
        this.ItemName=ItemName;
    }
    
    public double getItemPrice()
    {
        return ItemPrice;
    }
    
    public void setItemPrice(double ItemPrice)
    {
        this.ItemPrice=ItemPrice;
    }
    
    public int getItemStock()
    {
        return ItemStock;
    }
    
    public void setItemStock(int ItemStock)
    {
        this.ItemStock=ItemStock;
    }
    
    public int getItemLim()
    {
        return ItemLim;
    }
    
    public void setItemLim(int ItemLim)
    {
        this.ItemLim=ItemLim;
    }
    
    @Override
    public String toString()
    {
        return ItemID+" "+ItemName+" "+ItemPrice+" "+ItemStock+" "+ItemLim;
        
    }
    @Override
    public void view()
    {
        String s = "=".repeat(81);
        System.out.format("""
                          %s
                          Item ID\t\tItem Name\tItem Price\tItem Stock\tItem Restock Limit
                          %s
                          """, s, s);
            
        File itemlist = new File(filePath);
         
        try(Scanner itemscanner = new Scanner (itemlist);){
   
            while (itemscanner.hasNextLine())
            {
                Item it= new Item();
                it.setItemID(itemscanner.next());
                it.setItemName(itemscanner.next());
                it.setItemPrice(itemscanner.nextDouble());
                it.setItemStock(itemscanner.nextInt());
                it.setItemLim(itemscanner.nextInt());
                if (it.getItemLim()==0){}
                else
                {
                    if (it.getItemName().length()<=7)
                    {
                        String ItemLine= it.getItemID()+"\t\t"+it.getItemName()+"\t\t\t"+it.getItemPrice()+"\t\t"+it.getItemStock()+"\t\t"+it.getItemLim();    
                        System.out.println(ItemLine);
                    }
                    else 
                    {
                        String ItemLine= it.getItemID()+"\t\t"+it.getItemName()+"\t\t"+it.getItemPrice()+"\t\t"+it.getItemStock()+"\t\t"+it.getItemLim();
                        System.out.println(ItemLine);
                    }
                    

                }   
            }
            System.out.println(s);
        }
        catch(IOException Ex)
        {
            System.out.println("Error with file handling");
        }
    }
    
    @Override
    public void create()
    {
        Item it=new Item();
        while(true){
            it.view();
            System.out.println();
            System.out.println("New Item Entry:\n");
            
            String itemID= newID();
            System.out.println("Item ID: "+ itemID);
            it.setItemID(itemID);
            
            System.out.println("Item name:\t(Input 0 to back)");
            it.setItemName(Sc.nextLine());
            
            
            
            while (it.getItemName().equals("0")){
                if(checkItem(it.getItemName())==true)
                {
                    System.out.println("Item exists, pls reinput");
                    System.out.println("Item name:\t(Input 0 to back)");
                    
                }
                else{
                    break;
                }
                it.setItemName(Sc.nextLine());
            }
      
            if(it.getItemName().equals("0"))
            {
                break;
            }
            
            
            
            System.out.println("Item price:");
            it.setItemPrice(Sc.nextDouble());
            System.out.println("Item stock:");
            it.setItemStock(Sc.nextInt());
            System.out.println("Item restock amount:");
            it.setItemLim(Sc.nextInt());
            Sc.nextLine();
        
            WriteLine(filePath,it.toString());       
        }
    }
    
    @Override
    public void edit()
    {
        int tracker=0;
        Item it= new Item();
        
        while (true)
        {
            it.view();
            System.out.println("Select Item ID to edit: \t(Input 0 to back)");
            String EditItemID= Sc.nextLine();    
            if (EditItemID.equals("0"))
            {
                break;
            }
            else
            {
                File itemlist = new File(filePath);
                try(Scanner itemscanner = new Scanner (itemlist);){
            
                    while (itemscanner.hasNextLine())
                    {
                        it.setItemID(itemscanner.next());
                        it.setItemName(itemscanner.next());
                        it.setItemPrice(itemscanner.nextDouble());
                        it.setItemStock(itemscanner.nextInt());
                        it.setItemLim(itemscanner.nextInt());

                        if (EditItemID.equals(it.getItemID()))
                        {
                            tracker++;
                            System.out.println("Original item info:");
                            System.out.println(it.toString());
                            System.out.println("Input edited info:");
                            System.out.println("Item name:");
                            it.setItemName(Sc.nextLine());
                            
                            System.out.println("Item price:");
                            it.setItemPrice(Sc.nextDouble());
                            System.out.println("Item stock:");
                            it.setItemStock(Sc.nextInt());
                            System.out.println("Item restock amount:");
                            it.setItemLim(Sc.nextInt());
                            
                            Sc.nextLine();
                            
                        } 
                        
                        WriteLine(filePath2,it.toString());
                          
                    }
                    
                    
                    if(tracker!=0)
                    {
                        System.out.println("Item ID edited");
                    }
                    else
                    {
                        System.out.println("Item ID not found");
                    }
                    
                    tracker=0;
                }
                catch(IOException Ex)
                {
                    System.out.println("Error with file handling.");
                }
                
            }
            Rename(); 
        }
           
    }
    
    @Override
    public void delete()
    {
        Item it= new Item();
        while(true)
        {
            int tracker=0;
            it.view();
            System.out.println("Delete item ID: \t(Input 0 to back)");
            String DelItem= Sc.nextLine();
            if (DelItem.equals("0"))
            {
                break;
            }
            else
            {
                File itemlist = new File(filePath);
                try(Scanner itemscanner = new Scanner (itemlist);){
            
                    while (itemscanner.hasNextLine())
                    {
                        it.setItemID(itemscanner.next());
                        it.setItemName(itemscanner.next());
                        it.setItemPrice(itemscanner.nextDouble());
                        it.setItemStock(itemscanner.nextInt());
                        it.setItemLim(itemscanner.nextInt());
                        if (DelItem.equals(it.getItemID()))
                        {
                            it.setItemLim(0);
                            tracker++;
                            WriteLine(filePath2,it.toString());                    
                        }
                        else 
                        {
                            WriteLine(filePath2,it.toString());
                        }
                    } 
                    
                }
                catch(IOException Ex)
                {
                    System.out.println("Error with file handling.");
                }
            }
            
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
    
    private void Rename()
    {
        File orifile = new File(filePath);

        if (orifile.delete()){}
        else 
        {
            System.out.println("Error with file handling(remove)");
        }
        File tempfile = new File(filePath2);
        File rename = new File(filePath);
        boolean flag=tempfile.renameTo(rename);
        
        if(flag==true){}
        else
        {
            System.out.println("Error with file handling(rename)");
        }  
    }
    
    protected String StockUpdate(String itemId, int stock, boolean mode)
    {
        Item it= new Item();
        int tracker=0;
        
        File itemlist = new File(filePath);
        try(Scanner itemscanner = new Scanner (itemlist);)
        {
            while (itemscanner.hasNextLine())
            {
                it.setItemID(itemscanner.next());
                it.setItemName(itemscanner.next());
                it.setItemPrice(itemscanner.nextDouble());
                it.setItemStock(itemscanner.nextInt());
                it.setItemLim(itemscanner.nextInt());              
                if (itemId.equals(it.getItemID()))
                {
                    tracker++;
                    if (mode==true)
                    {
                        it.setItemStock(it.getItemStock()+stock);
                    }
                    else
                    {
                        it.setItemStock(it.getItemStock()-stock);
                    }
                    
                }
                WriteLine(filePath2,it.toString());
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
    
    public String getItemName(String itemid)
    {
        Item it= new Item();
        String itemname="placeholder";
        File itemlist = new File(filePath);
        try(Scanner itemscanner = new Scanner (itemlist);)
        {
            while (itemscanner.hasNextLine())
            {
                it.setItemID(itemscanner.next());
                it.setItemName(itemscanner.next());
                it.setItemPrice(itemscanner.nextDouble());
                it.setItemStock(itemscanner.nextInt());
                it.setItemLim(itemscanner.nextInt());
                
                if(itemid.equals(it.getItemID()))
                {
                    itemname=it.getItemName();
                    break;
                }
            }
        }
        catch(IOException Ex){}
        
        return itemname;
    }
    
    public void viewRestockItems()
    {
        Item it= new Item();
        System.out.println("\n=========================");
        System.out.println("\tItem List with low stock");
        System.out.println("=========================");
        System.out.println("Item ID\t\tItem Name\t\tItem Price\t\tItem Stock\t\tItem Restock Limit");
            
        File itemlist = new File("Item.txt");
        
        try(Scanner itemscanner = new Scanner (itemlist);){
            
            
            
            while (itemscanner.hasNextLine())
            {
                it.setItemID(itemscanner.next());
                it.setItemName(itemscanner.next());
                it.setItemPrice(itemscanner.nextDouble());
                it.setItemStock(itemscanner.nextInt());
                it.setItemLim(itemscanner.nextInt());
                if (ot.getItemLim()==0)
                {
                    
                }
                else
                {
                    if (it.getItemStock()<it.getItemLim()){
                        if (it.getItemName().length()<=7)
                        {
                            String ItemLine= it.getItemID()+"\t\t"+it.getItemName()+"\t\t\t"+it.getItemPrice()+"\t\t\t"+it.getItemStock()+"\t\t\t"+it.getItemLim();
                            System.out.println(ItemLine);
                        }
                        else 
                        {
                            String ItemLine= it.getItemID()+"\t\t"+it.getItemName()+"\t\t"+it.getItemPrice()+"\t\t\t"+it.getItemStock()+"\t\t\t"+it.getItemLim();
                            System.out.println(ItemLine);
                        }
                    }
                }
                
            }
            
        }
        catch(IOException Ex)
        {
            System.out.println("Error with file handling");
        }
    }
    
    private String newID(){
        Item it= new Item();
        int ctr=1;
        File itemlist = new File("Item.txt");
          
        try(Scanner itemscanner = new Scanner (itemlist);)
        {
            while (itemscanner.hasNextLine()){
                
                it.setItemID(itemscanner.next());
                it.setItemName(itemscanner.next());
                it.setItemPrice(itemscanner.nextDouble());
                it.setItemStock(itemscanner.nextInt());
                it.setItemLim(itemscanner.nextInt());
                ctr++;
            }
        }
        catch(IOException Ex){
            System.out.println("Error reading file.");
        }
        String newID=String.format("%04d",ctr);
        return "IT" + newID;

    }
    
    private boolean checkItem(String item_name)
    {
        Item it= new Item();
        File itemlist = new File("Item.txt");
          
        try(Scanner itemscanner = new Scanner (itemlist);){
            
            while (itemscanner.hasNextLine())
            {
                it.setItemID(itemscanner.next());
                it.setItemName(itemscanner.next());
                it.setItemPrice(itemscanner.nextDouble());
                it.setItemStock(itemscanner.nextInt());
                it.setItemLim(itemscanner.nextInt());
                
                if (item_name.equals(it.getItemName()))
                {
                    return true;
                }
            }
        }
        catch(IOException Ex){
            System.out.println("Error reading file.");
        }
        return false;
    }
}
