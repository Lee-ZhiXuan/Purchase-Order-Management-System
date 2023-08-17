import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

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
            String reqID; String itemID; int reqQuantity; String reqStatus; String reqStatF = "";

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

            System.out.print("\nPress Enter to continue...");
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
        int selection = 0;

        do {
            //Print header
            System.out.println("\n==========================");
            System.out.println("Create Purchase Requisition");
            System.out.println("==========================");

            System.out.println("Create new Requisition: 1");
            System.out.println("Back: 0");
            System.out.print("\nSelection: ");
            selection = Sc.nextInt();
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
                    System.out.println("Successfully written to file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                System.out.println("Item successfully created.");
                System.out.println();

            }
            else {
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
            String reqID; String itemID; int reqQuantity; String reqStatus;

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
                if (Objects.equals(reqStatus, "1")) {
                    System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\tPending");
                    count++;
                }
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
            System.out.print("\nSelection: ");
            selection = Sc.nextInt();

            if (selection < 0 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            }
            else {
                try {
                    File fSc = new File("requisition.txt");
                    Scanner fsc = new Scanner(fSc);
                    String reqID; String itemID; int reqQuantity;
                    String newReqID; String newItemID; int newReqQuantity; int newReqStatus;

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

                                    System.out.println("No.\tReq ID\t\tItem ID\t\tQuantity\tStatus\n");
                                    System.out.println(selection + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\tPending\n");

                                    //Call edit field method
                                    newReqID = "T0001";
                                    newItemID = "TI0001";
                                    newReqQuantity = 50;
                                    newReqStatus = 0;
                                    PurchaseReq.EditFileLine(selection, newReqID, newItemID, newReqQuantity, newReqStatus);

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
                if (count != selection) {
                    wr.write(line);
                } else {
                    wr.write(reqID + " " + itemID + " " + reqQuantity + " " + reqStatus);
                }
                wr.newLine();
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
            String reqID; String itemID; int reqQuantity; String reqStatus;

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
                if (Objects.equals(reqStatus, "1")) {
                    System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\tPending");
                    count++;
                }
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
            System.out.print("\nSelection: ");
            selection = Sc.nextInt();
            Sc.nextLine();

            if (selection < 0 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            }
            else {
                try {
                    File fSc = new File("requisition.txt");
                    Scanner fsc = new Scanner(fSc);
                    String reqID; String itemID; int reqQuantity;

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

                                    System.out.println("No.\tReq ID\t\tItem ID\t\tQuantity\tStatus\n");
                                    System.out.println(selection + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\tPending\n");

                                    //Call delete field method
                                    System.out.println("Do you wish to delete the selected purchase requisition?");
                                    System.out.println("Type out YES to confirm.");
                                    System.out.print("\nConfirmation: ");
                                    String confirmation = Sc.nextLine();
                                    if (Objects.equals(confirmation, "YES")) {
                                        DeleteFileLine(selection);
                                        return;
                                    } else {
                                        System.out.println("Deletion cancelled.\n");
                                    }

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
                    wr.write(line);
                    wr.newLine();
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Replace original file with carry file
        File oldReq = new File("requisition.txt");

    }
}
