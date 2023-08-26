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


    // Constructor
    public PurchaseOrder(String OrderID, String ItemID, String ReqID, int OrderQuantity, int OrderStatus, String OrderDate, String SupplierID) {
        this.orderID = OrderID;
        this.orderQuantity = OrderQuantity;
        this.orderStatus = OrderStatus;
        this.orderDate = OrderDate;
        this.itemID = ItemID;
        this.reqID = ReqID;
        this.supplierID = SupplierID;

    }

    public PurchaseOrder() {}

    // Getters and Setters
    public String getOrderID() {
        return orderID;
    }
    public int getOrderQuantity() {
        return orderQuantity;
    }
    public int getOrderStatus() {
        return orderStatus;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public String getItemID() {
        return itemID;
    }
    public String getReqID() {
        return reqID;
    }
    public String getSupplierID() {
        return supplierID;
    }

    Scanner Sc = new Scanner(System.in);


    // View Purchase Order Method
    public void View() {
        Scanner Sc = new Scanner(System.in);
        displayOrder("View Order List");
        System.out.print("\nPress Enter to return...");
        Sc.nextLine();
        System.out.println();
    }


    // Create Purchase Order Method
    public void Create(String userID) {
        String orderID; int orderQuantity; String orderDate; String itemID = null; String reqID = null; String supplierID;
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

            if (selection == 1) {
                int reqQuantity = 0; int reqStatus; String reqDate = null;
                int count = PurchaseReq.selectiveDisplayReq("Approved Purchase Requisitions", 2, "Approved");
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
                        try (BufferedReader br = new BufferedReader(new FileReader("requisition.txt"))) {
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
                                            System.out.println("=============================================================================");
                                            System.out.println("#\tReq ID\t\tItem ID\t\tQuantity\tDate\t\t\t\tStatus\n");
                                            System.out.println(selection2 + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity + "\t\t\t" + reqDate + "\t\t\tApproved");
                                            System.out.println("=============================================================================");
                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }


                        System.out.print("Enter Order ID: ");
                        orderID = Sc.nextLine();
                        System.out.print("Enter Order Date (DD-MM-YYYY): ");
                        orderDate = Sc.nextLine();
                        System.out.print("Enter Supplier ID: ");
                        supplierID = Sc.nextLine();

                        orderQuantity = reqQuantity;

                        PurchaseOrder order = new PurchaseOrder(orderID, itemID, reqID, orderQuantity, 1, orderDate, supplierID);
                        PurchaseReq.EditFileLine(selection2, reqID, itemID, reqQuantity, 4, reqDate, userID);
                        PurchaseReq.ReplaceReq();

                        try {
                            FileWriter tfw = new FileWriter("order.txt", true);
                            tfw.write("\n" + order.getOrderID() + " " + order.getItemID() + " " + order.getReqID() + " " + order.getOrderQuantity() + " " + order.getOrderStatus() + " " + order.getOrderDate() + " " + userID + " " + order.getSupplierID());
                            tfw.close();
                        } catch (IOException e) {
                            System.out.println("\nAn error occurred.");
                            e.printStackTrace();
                        }

                        System.out.println("\nPurchase order successfully created.");
                        System.out.print("\nPress Enter to continue...");
                        Sc.nextLine();
                        System.out.println();
                        break;
                    }
                } while (selection2 != 0);

            } else if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else {
                System.out.println("Invalid Selection, please try again.");
            }
        } while (selection != 0);
    }
    public void Create() {}


    // Edit Purchase Order Method
    public void Edit(String userID) {
        int count = displayOrder("Edit Purchase Orders");
        int orderQuantity = 0; String itemID = null; String reqID = null;
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
                
                try (BufferedReader br = new BufferedReader(new FileReader("order.txt"))) {
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
                    e.printStackTrace();
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
    public void Edit() {}


    // Edit method
    static void EditFileLine(int selection, String orderID, String itemID, String reqID, int orderQuantity, int orderStatus, String orderDate, String userID, String supplierID) {

        try (BufferedReader rd = new BufferedReader(new FileReader("order.txt"));
             BufferedWriter wr = new BufferedWriter(new FileWriter("orderCarry.txt"))) {

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
            e.printStackTrace();
        }
    }


    // Delete Purchase Order Method
    public void Delete() {
        int count = displayOrder("Remove Purchase Order");
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


    // Delete Method
    static void DeleteFileLine(int selection) {

        try (BufferedReader rd = new BufferedReader(new FileReader("order.txt"));
             BufferedWriter wr = new BufferedWriter(new FileWriter("orderCarry.txt"))) {

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
            e.printStackTrace();
        }
    }


    // Replace Actual with Carry
    static void ReplaceOrder() {

        //Delete old order file
        File del = new File("order.txt");
        if (del.delete()) {
            System.out.print("\n");
        } else {
            System.out.println("Error.");
        }

        //Rename carry order
        String oldFilePath = "orderCarry.txt";
        String newFilePath = "order.txt";

        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        boolean renamed = oldFile.renameTo(newFile);

        if (renamed) {
            System.out.print("Changes application successful.");
        } else {
            System.out.println("Error.");
        }
    }


    // Purchase Order Approval
    public void OrderHandle() {
        String orderID = null; int orderQuantity = 0; int orderStatus; String orderDate = null; String itemID = null; String userID = null; String reqID = null; String supplierID = null;
        int count = selectiveDisplayOrder("Handle Purchase Order", 1, "Pending-");
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
                try (BufferedReader br = new BufferedReader(new FileReader("order.txt"))) {
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
                                    System.out.println("=====================================================================================================================");
                                    System.out.println("#\tOrder ID\tItem ID\t\tRequisition ID\t\tQuantity\t\tDate\t\t\tStatus\t\tSupplier ID\t\tRaised by\n");
                                    System.out.println(selection + "\t" + orderID + "\t\t" + itemID + "\t\t" + reqID + "\t\t\t\t" + orderQuantity + "\t\t\t\t" + orderDate + "\t\tPending\t\t" + supplierID + "\t\t" + userID);
                                    System.out.println("=====================================================================================================================");
                                    break;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
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


    // Display Order List Method
    static int displayOrder(String header) {
        int count = 1;
        try {
            String orderID; int orderQuantity; int orderStatus; String orderDate; String orderStatF = null; String itemID; String userID; String reqID; String supplierID;

            File fSc = new File("order.txt");
            Scanner fsc = new Scanner(fSc);

            //Print header
            System.out.println("=====================================================================================================================");
            System.out.println(header);
            System.out.println("=====================================================================================================================");
            System.out.println("#\tOrder ID\tItem ID\t\tRequisition ID\t\tQuantity\t\tDate\t\t\tStatus\t\tSupplier ID\t\tRaised by\n");

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
                System.out.println(count + "\t" + orderID + "\t\t" + itemID + "\t\t" + reqID + "\t\t\t\t" + orderQuantity + "\t\t\t\t" + orderDate + "\t\t" + orderStatF + "\t\t" + supplierID + "\t\t" + userID);
                count++;
            }
            fsc.close();

            System.out.println("=====================================================================================================================");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return count;
    }


    // Selective Display Order List Method
    static int selectiveDisplayOrder(String header, int selection, String orderStatF) {
        int count = 1;
        try {
            String orderID; int orderQuantity; int orderStatus; String orderDate; String itemID; String userID; String reqID; String supplierID;
            File fSc = new File("order.txt");
            Scanner fsc = new Scanner(fSc);

            //Print header
            System.out.println("=====================================================================================================================");
            System.out.println(header);
            System.out.println("=====================================================================================================================");
            System.out.println("#\tOrder ID\tItem ID\t\tRequisition ID\t\tQuantity\t\tDate\t\t\tStatus\t\tSupplier ID\t\tRaised by\n");

            while (fsc.hasNextLine()) {
                orderID = fsc.next();
                itemID = fsc.next();
                reqID = fsc.next();
                orderQuantity = Integer.parseInt(fsc.next());
                orderStatus = Integer.parseInt(fsc.next());
                orderDate = fsc.next();
                userID = fsc.next();
                supplierID = fsc.next();
                if (orderStatus == selection) {
                    System.out.println(count + "\t" + orderID + "\t\t" + itemID + "\t\t" + reqID + "\t\t\t\t" + orderQuantity + "\t\t\t\t" + orderDate + "\t\t" + orderStatF + "\t\t" + supplierID + "\t\t" + userID);
                    count++;
                }
            }
            fsc.close();
            System.out.println("=====================================================================================================================");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return count;
    }


    // Selected Display Order Method
    static void selectedDisplayOrder(int selection) {
        String orderID; int orderQuantity; int orderStatus; String orderDate; String orderStatF = null; String itemID; String reqID; String userID; String supplierID;
        System.out.println("\nSelected Purchase Order:");
        try (BufferedReader br = new BufferedReader(new FileReader("order.txt"))) {
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

                    System.out.println("=====================================================================================================================");
                    System.out.println("#\tOrder ID\tItem ID\t\tRequisition ID\t\tQuantity\t\tDate\t\t\tStatus\t\tSupplier ID\t\tRaised by\n");
                    switch (orderStatus) {
                        case 1 -> orderStatF = "Pending-";
                        case 2 -> orderStatF = "Completed";
                    }
                    System.out.println(selection + "\t" + orderID + "\t\t" + itemID + "\t\t" + reqID + "\t\t\t\t" + orderQuantity + "\t\t\t\t" + orderDate + "\t\t" + orderStatF + "\t\t" + supplierID + "\t\t" + userID);
                    System.out.println("=====================================================================================================================");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}