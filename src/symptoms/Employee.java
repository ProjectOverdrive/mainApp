/**
 * Created by Colten on 4/11/17.
 */

public class Employee extends Customer {

    private static int employeeID;
    private int hourlyPay;


    public Employee() {
        // MUST STAY EMPTY
        // Needed by Ormlite to save object to database
    }

    public Employee(String firstName, String lastName, String phoneNumber, String streetAddress,
                    String city, String state, String zipcode, String email, int hourlyPay) {

        super(firstName, lastName, phoneNumber, streetAddress, city, state, zipcode, email);
        this.hourlyPay = hourlyPay;
        employeeID += 1;

    }

    public static int getEmployeeID() {
        return employeeID;
    }

    public static void setEmployeeID(int employeeID) {
        Employee.employeeID = employeeID;
    }

    public int getHourlyPay() {
        return hourlyPay;
    }

    public void setHourlyPay(int hourlyPay) {
        this.hourlyPay = hourlyPay;
    }
}
