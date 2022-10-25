import java.util.HashMap;
import java.util.Scanner;

public class User {
    protected static HashMap<String, User> users = new HashMap<String, User>();

    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String country;

    public User() {
        Scanner userScanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String userNameInput = userScanner.next();
        userName = userNameInput.trim();

        System.out.print("Enter your password: ");
        String passwordInput = userScanner.next();
        password = passwordInput.trim();
        userScanner.nextLine();

        System.out.print("Enter your full name: ");
        String fullNameInput = userScanner.nextLine();
        fullName = fullNameInput.trim();

        System.out.print("Enter your phone number: ");
        String phoneNumberInput = userScanner.nextLine();
        phoneNumber = phoneNumberInput.trim();

        System.out.print("Enter your address: ");
        String addressInput = userScanner.nextLine();
        address = addressInput.trim();

        System.out.print("Enter your country: ");
        String countryInput = userScanner.next();
        country = countryInput.trim();

        users.put(userName, this);
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String toString() {
        return "User(" + userName + "," + password + "," + fullName + "," + phoneNumber
                + "," + address + "," + country + ")";
    }

    public void queryAccountInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("a. Get account's username");
        System.out.println("b. Get account's password");
        System.out.println("c. Get account's full name");
        System.out.println("d. Get account's phone number");
        System.out.println("e. Get account's address");
        System.out.println("f. Get account's country");
        System.out.println("g. Go back\n");
        System.out.print("What would you like to query? ");
        char querySelection = scanner.next().charAt(0);
        while (querySelection != 'g') {
            switch (querySelection) {

                case 'a':
                    System.out.println("Your account's username is " + getUserName());
                    break;

                case 'b':
                    System.out.println("Your account's password is " + getPassword());
                    break;

                case 'c':
                    System.out.println("Your account's full name is " + getFullName());
                    break;

                case 'd':
                    System.out.println("Your account's phone number is " + getPhoneNumber());
                    break;

                case 'e':
                    System.out.println("Your account's address is " + getAddress());
                    break;

                case 'f':
                    System.out.println("Your account's country is " + getCountry());
                    break;

                default:
                    System.out.println("Invalid selection. Please try again!");
            }
            System.out.println();
            System.out.print("What would you like to query? ");
            querySelection = scanner.next().charAt(0);
        }
    }
}