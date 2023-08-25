import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n   Phone: " + phoneNumber + "\n   Email: " + emailAddress + "\n";
    }
}

class AddressBook implements Serializable {
    private ArrayList<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            System.out.println("\nContact removed!\n");
        } else {
            System.out.println("\nInvalid index!\n");
        }
    }

    public void displayContacts() {
        System.out.println("\nEnter the choice for contact: ");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i));
        }
    }
}

public class AddressBookSys {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = loadAddressBook(); // Load existing data if available

        while (true) {
            System.out.println("1. Add Contact");
            System.out.println("2. Display Contacts");
            System.out.println("3. Remove Contact");
            System.out.println("4. Exit");
            System.out.print("\nChoose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); 
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String emailAddress = scanner.nextLine();
                    Contact newContact = new Contact(name, phoneNumber, emailAddress);
                    addressBook.addContact(newContact);
                    saveAddressBook(addressBook); // Save updated data
                    System.out.println("\nContact added!\n");
                    break;
                case 2:
                    addressBook.displayContacts();
                    break;
                case 3:
                    addressBook.displayContacts();
                    System.out.print("\nEnter the index of the contact to remove: ");
                    int index = scanner.nextInt();
                    addressBook.removeContact(index - 1);
                    saveAddressBook(addressBook); // Save updated data
                    break;
                case 4:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("\nInvalid choice!\n");
            }
        }
    }

    private static AddressBook loadAddressBook() {
        try {
            FileInputStream fileIn = new FileInputStream("addressBook.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            AddressBook addressBook = (AddressBook) in.readObject();
            in.close();
            fileIn.close();
            return addressBook;
        } catch (IOException | ClassNotFoundException e) {
            return new AddressBook(); // Return a new AddressBook if loading fails
        }
    }

    private static void saveAddressBook(AddressBook addressBook) {
        try {
            FileOutputStream fileOut = new FileOutputStream("addressBook.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(addressBook);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
