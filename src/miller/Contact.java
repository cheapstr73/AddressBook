package miller;
import java.io.Serializable;

public class Contact implements Serializable{
    private String firstName, lastName, email1, email2, phone1, phone2;

     Contact(String fname, String lname, String email1, String email2, String phone1, String phone2){

        this.firstName = fname;
        this.lastName = lname;
        this.email1 = email1;
        this.email2 = email2;
        this.phone1 = phone1;
        this.phone2 = phone2;
    }

    public void setFirstName(String fname) { this.firstName = fname; }
    public void setLastName(String lname) { this.lastName = lname; }
    public void setemail1(String email1) { this.email1 = email1; }
    public void setEmail2(String email2) {this.email2 = email2; }
    public void setPhone1(String phone1) { this.phone1 = phone1; }
    public void setPhone2(String phone2) { this.phone2 = phone2; }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEMail1() { return email1; }
    public String getEmail2() { return email2; }
    public String getPhone1() { return phone1; }
    public String getPhone2() { return phone2; }
}
