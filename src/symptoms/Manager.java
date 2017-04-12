/**
 * Created by Colten on 4/12/17.
 */
public class Manager extends Employee {

    private int managerID;

    public Manager() {
        // MUST STAY EMPTY
        // Needed by Ormlite to save object to database
    }

    public Manager(String firstName, String lastName, String phoneNumber, String streetAddress,
                   String city, String state, String zipcode, String email, int hourlyPay) {
        super(firstName, lastName, phoneNumber, streetAddress, city, state, zipcode, email, hourlyPay);

        managerID += 1;


    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }
}
