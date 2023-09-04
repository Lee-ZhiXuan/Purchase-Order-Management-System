package beta_version;

import java.util.*;
import java.io.*;

class PurchaseOrder implements SalesObject{
    private String orderID;
    private int orderQuantity;
    private int orderStatus;
    private String orderDate;
    private String itemID;
    private String reqID;
    private String supplierID;
    private String userID;
    private PurchaseReq purchaseReq; //Aggregation with purchase requisition

    final static String filePath = "Order.Txt";
    final static String filePath2 = "Order_Buffer.Txt";
    final static String filePath3 = "Requisition.Txt";
    
    // Constructor
    public PurchaseOrder(String OrderID, int OrderStatus, String OrderDate, String SupplierID, String UserID, PurchaseReq PurchaseReq) {
        this.orderID = OrderID;
        this.orderStatus = OrderStatus;
        this.orderDate = OrderDate;
        this.supplierID = SupplierID;
        this.userID = UserID;
        this.purchaseReq = PurchaseReq;

    }

    public PurchaseOrder() {}

    // Getters and Setters
    public String getOrderID() {
        return orderID;
    }
    public int getOrderStatus() {
        return orderStatus;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public String getSupplierID() {
        return supplierID;
    }
    public String getUserID() {
        return userID;
    }

    Scanner Sc = new Scanner(System.in);


    // View purchase order method
    @Override
    public void view() {
        Scanner sc = new Scanner(System.in);
        displayOrder();
        System.out.print("\nPress Enter to return...");
        sc.nextLine();
        System.out.println();
    }


    @Override
    // Create purchase order method
    public void create(String userID) {
        int selection;

        do {
            //Print header
            System.out.println("\n===========================");
            System.out.println("Create Purchase Order");
            System.out.println("===========================");
            System.out.println("Create new Order: 1");
            System.out.println("Back: 0");

            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }

            Sc.nextLine();
            System.out.println();

            switch (selection) {
                case 1 -> {
                    int reqQuantity = 0; int reqStatus = 0; String reqDate = null;
                    int count = PurchaseReq.selectiveDisplayReq(2, "Approved");
                    int selection2;
                    do {
                        System.out.println("Select the purchase requisition you wish to handle.");
                        System.out.println("Back: 0");
                        try {
                            System.out.print("\nSelection: ");
                            selection2 = Sc.nextInt();
                        } catch (InputMismatchException e) {
                            selection2 = 999;
                        }
                        Sc.nextLine();
                        
                        if (selection2 == 0) {
                            System.out.println("Back to previous menu.\n");
                        } else if (selection2 < 1 || selection2 >= count) {
                            System.out.println("Invalid selection, please try again.");
                        } else {
                            
                            //Print header
                            System.out.println("\nSelected Purchase Requisition:");
                            try (BufferedReader br = new BufferedReader(new FileReader(filePath3))) {
                                String line; int currentRow = 0;
                                
                                while ((line = br.readLine()) != null) {
                                    String[] data = line.split(" ");
                                    if (data.length >= 4) {
                                        reqID = data[0];
                                        itemID = data[1];
                                        reqQuantity = Integer.parseInt(data[2]);
                                        reqStatus = Integer.parseInt(data[3]);
                                        reqDate = data[4];
                                        userID = data[5];
                                        if (reqStatus == 2) {
                                            currentRow++;
                                            if (currentRow == selection2) {
                                                String string = "=".repeat(80);
                                                System.out.format("""
                                                            %s
                                                            %-5s %-10s %-10s %-10s %-15s %-12s %-10s
                                                            %s
                                                            """, string,"No.", "Req ID", "Item ID", "Quantity", "Date", "Status", "Raised By", string);
                                                            System.out.format("\n%-5d %-10s %-10s %-10s %-15s %-12s %-10s", selection2, reqID, itemID, reqQuantity, reqDate, "Approved", userID);
                                                System.out.println("\n" + string);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                            }
                            
                            
                            System.out.print("Enter Order ID: ");
                            orderID = Sc.nextLine();
                            if (PurchaseReq.checker(filePath, orderID)) {
                                System.out.println("Order ID already exists.");
                                System.out.print("\nPress Enter to return...");
                                Sc.nextLine();
                                break;
                            }
                            System.out.print("Enter Order Date (DD-MM-YYYY): ");
                            orderDate = Sc.nextLine();
                            System.out.print("Enter Supplier ID: ");
                            supplierID = Sc.nextLine();
                            
                            orderQuantity = reqQuantity;

                            PurchaseReq req = new PurchaseReq(reqID, itemID, reqQuantity, reqStatus, reqDate, userID);
                            PurchaseOrder order = new PurchaseOrder(orderID, 1, orderDate, supplierID, userID, req);
                            PurchaseReq.EditFileLine(selection2, reqID, itemID, reqQuantity, 4, reqDate, userID);
                            PurchaseReq.ReplaceReq();

                            order.create();
                            
                            System.out.println("\nPurchase order successfully created.");
                            System.out.print("\nPress Enter to continue...");
                            Sc.nextLine();
                            System.out.println();
                            break;
                        }
                    } while (selection2 != 0);
                }
                case 0 -> System.out.println("Back to previous menu.\n");
                default -> System.out.println("Invalid Selection, please try again.");
            }
        } while (selection != 0);
    }
    @Override
    public void create() {
        try (FileWriter tfw = new FileWriter(filePath, true)) {
            tfw.write("\n" + getOrderID() + " " + purchaseReq.getItemID() + " " + purchaseReq.getReqID() + " " + purchaseReq.getReqQuantity() + " " + getOrderStatus() + " " + getOrderDate() + " " + getUserID() + " " + getSupplierID());
        } catch (IOException e) {
            System.out.println("\nAn error occurred.");
        }
    }


    // Edit purchase order method
    @Override
    public void edit(String userID) {
        int count = displayOrder();
        int selection;

        do {
            System.out.println("Select the purchase order you wish to edit.");
            System.out.println("Back: 0");

            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }
            Sc.nextLine();

            if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else if (selection < 1 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            } else {
                selectedDisplayOrder(selection);
                
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line; int currentRow = 0;

                    while ((line = br.readLine()) != null) {
                        currentRow++;

                        if (currentRow == selection + 1) {
                            String[] data = line.split(" ");
                            itemID = data[1];
                            reqID = data[2];
                            orderQuantity = Integer.parseInt(data[3]);
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                }

                //Call edit field method
                System.out.print("Enter Order ID: ");
                String newOrderID = Sc.nextLine();
                System.out.print("Enter new date (DD-MM-YYYY): ");
                String newOrderDate = Sc.nextLine();
                System.out.println("\nPending  = 1");
                System.out.println("Completed = 2");
                System.out.print("Enter Order Status: ");
                int newOrderStatus = Sc.nextInt();
                Sc.nextLine();
                System.out.print("Enter new supplier ID: ");
                String newSupplierID = Sc.nextLine();

                PurchaseOrder.EditFileLine(selection, newOrderID, itemID, reqID, orderQuantity, newOrderStatus, newOrderDate, userID, newSupplierID);
                ReplaceOrder();
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }
    @Override
    public void edit() {}
    // Edit line in text file method
    static void EditFileLine(int selection, String orderID, String itemID, String reqID, int orderQuantity, int orderStatus, String orderDate, String userID, String supplierID) {

        try (BufferedReader rd = new BufferedReader(new FileReader(filePath));
             BufferedWriter wr = new BufferedWriter(new FileWriter(filePath2))) {

            String line;
            int count = 0;

            while ((line = rd.readLine()) != null) {
                if (count != 0) {
                    wr.newLine();
                }
                if (count != selection) {
                    wr.write(line);
                } else {
                    wr.write(orderID + " " + itemID + " " + reqID + " " + orderQuantity + " " + orderStatus + " " + orderDate + " " + userID + " " + supplierID);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }


    // Delete purchase order method
    @Override
    public void delete() {
        int count = displayOrder();
        int selection;

        do {
            System.out.println("Select the purchase order you wish to remove.");
            System.out.println("Back: 0");
            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }
            Sc.nextLine();

            if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else if (selection < 1 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            } else {
                selectedDisplayOrder(selection);

                //Call delete field method
                System.out.println("Do you wish to delete the selected purchase order?");
                System.out.println("Type out YES to confirm.");
                System.out.print("\nConfirmation: ");
                String confirmation = Sc.nextLine();
                if (Objects.equals(confirmation, "YES")) {
                    DeleteFileLine(selection);
                    ReplaceOrder();
                } else {
                    System.out.println("Deletion cancelled.");
                }
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }
    // Delete line in text file method
    static void DeleteFileLine(int selection) {

        try (BufferedReader rd = new BufferedReader(new FileReader(filePath));
             BufferedWriter wr = new BufferedWriter(new FileWriter(filePath2))) {

            String line;
            int count = 0;

            while ((line = rd.readLine()) != null) {
                if (count != selection) {
                    if (count != 0) {
                        wr.newLine();
                    }
                    wr.write(line);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }


    // Replace actual text file with buffer text file
    static void ReplaceOrder() {

        //Delete old order file
        File del = new File(filePath);
        if (del.delete()) {
            System.out.print("\n");
        } else {
            System.out.println("Error.");
        }

        //Rename carry order
        File oldFile = new File(filePath2);
        File newFile = new File(filePath);

        boolean renamed = oldFile.renameTo(newFile);

        if (renamed) {
            System.out.print("Changes application successful.");
        } else {
            System.out.println("Error.");
        }
    }


    // Purchase order approval method
    public void OrderHandle() {
        String orderID = null; int orderQuantity = 0; int orderStatus; String orderDate = null; String itemID = null; String userID = null; String reqID = null; String supplierID = null;
        int count = selectiveDisplayOrder();
        int selection; int selection2 = 0;

        do {
            System.out.println("Select the purchase order you wish to handle.");
            System.out.println("Back: 0");
            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }
            Sc.nextLine();

            if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else if (selection < 1 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            } else {

                //Print header
                System.out.println("\nSelected Purchase Order:");
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line; int currentRow = 0;

                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(" ");
                        if (data.length >= 4) {
                            orderID = data[0];
                            itemID = data[1];
                            reqID = data[2];
                            orderQuantity = Integer.parseInt(data[3]);
                            orderStatus = Integer.parseInt(data[4]);
                            orderDate = data[5];
                            userID = data[6];
                            supplierID = data[7];
                            selection2++;
                            if (orderStatus == 1) {
                                currentRow++;
                                if (currentRow == selection) {
                                    String string = "=".repeat(104);
                                    System.out.format("""
                                   %s
                                   %-5s %-10s %-10s %-15s %-10s %-15s %-12s %-10s %-10s
                                   %s
                                   """, string,"No.","Order ID", "Item ID", "Requisition ID", "Quantity", "Date", "Status","Supplier", "Raised By", string);
                                    System.out.format("\n%-5s %-10s %-10s %-15s %-10s %-15s %-12s %-10s %-10s", selection, orderID, itemID, reqID, orderQuantity, orderDate,"Pending-",supplierID, userID);
                                    System.out.println("\n" + string);
                                    break;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                }

                //Call edit field method
                int selection3;
                System.out.println("Pending : 1");
                System.out.println("Completed: 2");
                try {
                    System.out.print("\nSelection: ");
                    selection3 = Sc.nextInt();
                } catch (InputMismatchException e) {
                    selection3 = 999;
                }
                Sc.nextLine();

                PurchaseOrder.EditFileLine(selection2, orderID, itemID, reqID, orderQuantity, selection3, orderDate, userID, supplierID);
                ReplaceOrder();
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }


    // Display order list method
    static int displayOrder() {
        int count = 1;
        try {
            String orderID; int orderQuantity; int orderStatus; String orderDate; String orderStatF = null; String itemID; String userID; String reqID; String supplierID;

            File fSc = new File(filePath);
            //Print header
            try (Scanner fsc = new Scanner(fSc)) {
                //Print header
                String string = "=".repeat(104);
                System.out.format("""
                                   %s
                                   %-5s %-10s %-10s %-15s %-10s %-15s %-12s %-10s %-10s
                                   %s
                                   """, string,"No.","Order ID", "Item ID", "Requisition ID", "Quantity", "Date", "Status","Supplier", "Raised By", string);
                
                while (fsc.hasNextLine()) {
                    orderID = fsc.next();
                    itemID = fsc.next();
                    reqID = fsc.next();
                    orderQuantity = Integer.parseInt(fsc.next());
                    orderStatus = Integer.parseInt(fsc.next());
                    orderDate = fsc.next();
                    userID = fsc.next();
                    supplierID = fsc.next();
                    switch (orderStatus) {
                        case 1 -> orderStatF = "Pending-";
                        case 2 -> orderStatF = "Completed";
                    }
                    System.out.format("\n%-5s %-10s %-10s %-15s %-10s %-15s %-12s %-10s %-10s", count, orderID, itemID, reqID, orderQuantity, orderDate,orderStatF,supplierID, userID);
                    count++;
                }
                
                System.out.println("\n" + string);
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return count;
    }
    // Selective display order list method
    static int selectiveDisplayOrder() {
        int count = 1;
        try {
            String orderID; int orderQuantity; int orderStatus; String orderDate; String itemID; String userID; String reqID; String supplierID;
            File fSc = new File(filePath);
            Scanner fsc = new Scanner(fSc);

            //Print header
            String string = "=".repeat(104);
            System.out.format("""
                                   %s
                                   %-5s %-10s %-10s %-15s %-10s %-15s %-12s %-10s %-10s
                                   %s
                                   """, string,"No.","Order ID", "Item ID", "Requisition ID", "Quantity", "Date", "Status","Supplier", "Raised By", string);

            while (fsc.hasNextLine()) {
                orderID = fsc.next();
                itemID = fsc.next();
                reqID = fsc.next();
                orderQuantity = Integer.parseInt(fsc.next());
                orderStatus = Integer.parseInt(fsc.next());
                orderDate = fsc.next();
                userID = fsc.next();
                supplierID = fsc.next();
                if (orderStatus == 1) {
                    System.out.format("\n%-5s %-10s %-10s %-15s %-10s %-15s %-12s %-10s %-10s", count, orderID, itemID, reqID, orderQuantity, orderDate,"Pending-",supplierID, userID);
                    count++;
                }
            }
            fsc.close();
            System.out.println("\n" + string);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return count;
    }
    // Selected display order list method
    static void selectedDisplayOrder(int selection) {
        String orderID; int orderQuantity; int orderStatus; String orderDate; String orderStatF = null; String itemID; String reqID; String userID; String supplierID;
        System.out.println("\nSelected Purchase Order:");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line; int currentRow = 0;

            while ((line = br.readLine()) != null) {
                currentRow++;

                if (currentRow == selection + 1) {
                    String[] data = line.split(" ");
                    orderID = data[0];
                    itemID = data[1];
                    reqID = data[2];
                    orderQuantity = Integer.parseInt(data[3]);
                    orderStatus = Integer.parseInt(data[4]);
                    orderDate = data[5];
                    userID = data[6];
                    supplierID = data[7];
                    
                    String string = "=".repeat(104);
                    System.out.format("""
                                   %s
                                   %-5s %-10s %-10s %-15s %-10s %-15s %-12s %-10s %-10s
                                   %s
                                   """, string,"No.","Order ID", "Item ID", "Requisition ID", "Quantity", "Date", "Status","Supplier", "Raised By", string);
                    switch (orderStatus) {
                        case 1 -> orderStatF = "Pending-";
                        case 2 -> orderStatF = "Completed";
                    }
                    System.out.format("\n%-5s %-10s %-10s %-15s %-10s %-15s %-12s %-10s %-10s", selection, orderID, itemID, reqID, orderQuantity, orderDate,orderStatF,supplierID, userID);
                    System.out.println("\n" + string);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}