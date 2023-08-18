import java.util.Objects;
import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;

public class PurchaseReq extends Item {
    private String reqID;
    private int reqQuantity;
    private int reqStatus;

    // Constructor
    public PurchaseReq(String ReqID, String itemID, int ReqQuantity, int ReqStatus) {
        super(itemID);
        this.reqID = ReqID;
        this.reqQuantity = ReqQuantity;
        this.reqStatus = ReqStatus;
    }

    // Getters and Setters
    public String getReqID() {
        return reqID;
    }
    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public int getReqQuantity() {
        return reqQuantity;
    }
    public void setReqQuantity(int reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public int getReqStatus() {
        return reqStatus;
    }
    public void setReqStatus(int reqStatus) {
        this.reqStatus = reqStatus;
    }


    // View Purchase Requisition Method
    static void ViewPurchaseReq() {
        try {
            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);
            Scanner Sc = new Scanner(System.in);
            String reqID; String itemID; int reqQuantity; String reqStatus; String reqStatF = null;

            //Print header
            System.out.println("\n=========================");
            System.out.println("View Purchase Requisition");
            System.out.println("=========================");
            System.out.println("Req ID\t\tItem ID\t\tQuantity\tStatus\n");

            while (fsc.hasNextLine()) {
                reqID = fsc.next();
                itemID = fsc.next();
                reqQuantity = Integer.parseInt(fsc.next());
                reqStatus = fsc.next();
                switch (reqStatus) {
                    case "1" -> reqStatF = "Pending";
                    case "2" -> reqStatF = "Approved";
                    case "3" -> reqStatF = "Rejected";
                }
                System.out.println(reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\t" + reqStatF);
            }
            fsc.close();

            System.out.print("\nPress Enter to return...");
            Sc.nextLine();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println();
    }


    // Create Purchase Requisition Method
    static void AddPurchaseReq() {
        Scanner Sc = new Scanner(System.in);
        int selection;

        do {
            //Print header
            System.out.println("\n==========================");
            System.out.println("Create Purchase Requisition");
            System.out.println("==========================");

            System.out.println("Create new Requisition: 1");
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
                System.out.print("Enter Requisition ID: ");
                String inputReqID = Sc.nextLine();
                System.out.print("Enter Item ID: ");
                String inputItemID = Sc.nextLine();
                System.out.print("Enter Requisition Quantity: ");
                int inputReqQuantity = Sc.nextInt();
                Sc.nextLine();

                PurchaseReq req = new PurchaseReq(inputReqID, inputItemID, inputReqQuantity, 1);

                try {
                    FileWriter tfw = new FileWriter("requisition.txt", true);
                    tfw.write("\n" + req.getReqID() + " " + req.getItemID() + " " + req.getReqQuantity() + " " + req.getReqStatus());
                    tfw.close();
                } catch (IOException e) {
                    System.out.println("\nAn error occurred.");
                    e.printStackTrace();
                }

                System.out.println("\nPurchase requisition successfully created.");

                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();

            } else if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else {
                System.out.println("Invalid Selection, please try again.");
            }
        } while (selection != 0);
    }


    // Edit Purchase Requisition Method
    static void EditPurchaseReq() {
        int count = 1;
        try {
            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);
            String reqID; String itemID; int reqQuantity; String reqStatus; String reqStatF = null;

            //Print header
            System.out.println("\n=========================");
            System.out.println("Edit Purchase Requisition");
            System.out.println("=========================");
            System.out.println("No.\tReq ID\t\tItem ID\t\tQuantity\tStatus\n");

            while (fsc.hasNextLine()) {
                reqID = fsc.next();
                itemID = fsc.next();
                reqQuantity = Integer.parseInt(fsc.next());
                reqStatus = fsc.next();
                switch (reqStatus) {
                    case "1" -> reqStatF = "Pending";
                    case "2" -> reqStatF = "Approved";
                    case "3" -> reqStatF = "Rejected";
                }
                System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\t" + reqStatF);
                count++;
            }
            fsc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Scanner Sc = new Scanner(System.in);
        int selection;

        do {
            System.out.println("Select the purchase requisition you wish to edit.");
            System.out.println("Back: 0");
            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }
            Sc.nextLine();

            if (selection < 0 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            }
            else {
                try {
                    File fSc = new File("requisition.txt");
                    Scanner fsc = new Scanner(fSc);
                    String reqID; String itemID; int reqQuantity; int reqStatus; String reqStatF = null;

                    //Print header
                    System.out.println("\nSelected Purchase Requisition:");
                    try (BufferedReader br = new BufferedReader(new FileReader("requisition.txt"))) {
                        String line;
                        int currentRow = 0;

                        while ((line = br.readLine()) != null) {
                            currentRow++;

                            if (currentRow == selection + 1) {
                                String[] data = line.split(" ");
                                if (data.length >= 4) {
                                    reqID = data[0];
                                    itemID = data[1];
                                    reqQuantity = Integer.parseInt(data[2]);
                                    reqStatus = Integer.parseInt(data[3]);

                                    System.out.println("No.\tReq ID\t\tItem ID\t\tQuantity\tStatus\n");
                                    switch (reqStatus) {
                                        case 1 -> reqStatF = "Pending";
                                        case 2 -> reqStatF = "Approved";
                                        case 3 -> reqStatF = "Rejected";
                                    }
                                    System.out.println(selection + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\t" + reqStatF + "\n");
                                } else {
                                    System.out.println("Invalid data format in line " + currentRow);
                                }
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    fsc.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                //Call edit field method
                String newReqID; String newItemID; int newReqQuantity; int newReqStatus;
                newReqID = "T0001";
                newItemID = "TI0001";
                newReqQuantity = 50;
                newReqStatus = 1;
                PurchaseReq.EditFileLine(selection, newReqID, newItemID, newReqQuantity, newReqStatus);
                ReplaceReq();
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }


    //Edit method
    static void EditFileLine(int selection, String reqID, String itemID, int reqQuantity, int reqStatus) {

        try (BufferedReader rd = new BufferedReader(new FileReader("requisition.txt"));
             BufferedWriter wr = new BufferedWriter(new FileWriter("requisitionCarry.txt"))) {

            String line;
            int count = 0;

            while ((line = rd.readLine()) != null) {
                if (count != 0) {
                    wr.newLine();
                }
                if (count != selection) {
                    wr.write(line);
                } else {
                    wr.write(reqID + " " + itemID + " " + reqQuantity + " " + reqStatus);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    // Delete Purchase Requisition Method
    static void RemovePurchaseReq() {
        int count = 1;
        try {
            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);
            String reqID; String itemID; int reqQuantity; String reqStatus; String reqStatF = null;

            //Print header
            System.out.println("\n===========================");
            System.out.println("Remove Purchase Requisition");
            System.out.println("===========================");
            System.out.println("No.\tReq ID\t\tItem ID\t\tQuantity\tStatus\n");

            while (fsc.hasNextLine()) {
                reqID = fsc.next();
                itemID = fsc.next();
                reqQuantity = Integer.parseInt(fsc.next());
                reqStatus = fsc.next();
                switch (reqStatus) {
                    case "1" -> reqStatF = "Pending";
                    case "2" -> reqStatF = "Approved";
                    case "3" -> reqStatF = "Rejected";
                }
                System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\t" + reqStatF);
                count++;
            }
            fsc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Scanner Sc = new Scanner(System.in);
        int selection;

        do {
            System.out.println("Select the purchase requisition you wish to remove.");
            System.out.println("Back: 0");
            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }
            Sc.nextLine();

            if (selection < 0 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            }
            else {
                try {
                    File fSc = new File("requisition.txt");
                    Scanner fsc = new Scanner(fSc);
                    String reqID; String itemID; int reqQuantity; int reqStatus; String reqStatF = null;

                    //Print header
                    System.out.println("\nSelected Purchase Requisition:");
                    try (BufferedReader br = new BufferedReader(new FileReader("requisition.txt"))) {
                        String line;
                        int currentRow = 0;

                        while ((line = br.readLine()) != null) {
                            currentRow++;

                            if (currentRow == selection + 1) {
                                String[] data = line.split(" ");
                                if (data.length >= 4) {
                                    reqID = data[0];
                                    itemID = data[1];
                                    reqQuantity = Integer.parseInt(data[2]);
                                    reqStatus = Integer.parseInt(data[3]);

                                    System.out.println("No.\tReq ID\t\tItem ID\t\tQuantity\tStatus\n");
                                    switch (reqStatus) {
                                        case 1 -> reqStatF = "Pending";
                                        case 2 -> reqStatF = "Approved";
                                        case 3 -> reqStatF = "Rejected";
                                    }
                                    System.out.println(selection + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\t" + reqStatF + "\n");
                                } else {
                                    System.out.println("Invalid data format in line " + currentRow);
                                }
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    fsc.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                //Call delete field method
                System.out.println("Do you wish to delete the selected purchase requisition?");
                System.out.println("Type out YES to confirm.");
                System.out.print("\nConfirmation: ");
                String confirmation = Sc.nextLine();
                if (Objects.equals(confirmation, "YES")) {
                    DeleteFileLine(selection);
                    ReplaceReq();
                } else {
                    System.out.println("Deletion cancelled.\n");
                }
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }


    //Delete method
    static void DeleteFileLine(int selection) {

        try (BufferedReader rd = new BufferedReader(new FileReader("requisition.txt"));
             BufferedWriter wr = new BufferedWriter(new FileWriter("requisitionCarry.txt"))) {

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


    //Replace actual with carry
    static void ReplaceReq() {

        //Delete old requisition file
        File del = new File("requisition.txt");
        if (del.delete()) {
            System.out.print("\n");
        } else {
            System.out.println("Error.");
        }

        //Rename carry requisition
        String oldFilePath = "requisitionCarry.txt";
        String newFilePath = "requisition.txt";

        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        boolean renamed = oldFile.renameTo(newFile);

        if (renamed) {
            System.out.print("Changes application successful.");
        } else {
            System.out.println("Error.");
        }
    }
}
