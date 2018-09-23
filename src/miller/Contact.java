package miller;

public class Contact {
    private String firstName, lastName, eMail, phone;

     Contact(String fname, String lname, String email, String phone){

        this.firstName = fname;
        this.lastName = lname;
        this.eMail = email;
        this.phone = phone;
    }

    public void setFirstName(String fname) { this.firstName = fname; }
    public void setLastName(String lname) { this.lastName = lname; }
    public void setEMail(String email) { this.eMail = email; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEMail() { return eMail; }
    public String getPhone() { return phone; }
}
