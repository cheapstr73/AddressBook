package miller;
import java.io.Serializable;

public class Contact implements Serializable{
    private String firstName, midName, lastName, suffix, address, city, state, email1, email1Type, email2, email2Type, phone1, phone1Type, phone2, phone2Type;

     Contact(String fname, String mname, String lname, String suffix, String address, String city, String state, String email1, String email1Type, String email2, String email2Type, String phone1, String phone1Type, String phone2, String phone2Type){

        this.firstName = fname;
        this.midName = mname;
        this.lastName = lname;
        this.suffix = suffix;
        this.address = address;
        this.city = city;
        this.state = state;
        this.email1 = email1;
        this.email1Type = email1Type;
        this.email2 = email2;
        this.email2Type = email2Type;
        this.phone1 = phone1;
        this.phone1Type = phone1Type;
        this.phone2 = phone2;
        this.phone2Type = phone2Type;
    }

    public void setFirstName(String fname) { this.firstName = fname; }
    public void setMidName(String mname) { this.midName = mname; }
    public void setLastName(String lname) { this.lastName = lname; }
    public  void setSuffix(String suffix) { this.suffix = suffix; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setemail1(String email1) { this.email1 = email1; }
    public void setEmail1Type(String email1Type) { this.email1Type = email1Type; }
    public void setEmail2(String email2) {this.email2 = email2; }
    public void setEmail2Type(String email2Type) { this.email2Type = email2Type; }
    public void setPhone1(String phone1) { this.phone1 = phone1; }
    public void setPhone1Type(String phone1Type) { this.phone1Type = phone1Type; }
    public void setPhone2(String phone2) { this.phone2 = phone2; }
    public void setPhone2Type(String phone2Type) { this.phone2Type = phone2Type; }

    public String getFirstName() { return firstName; }
    public String getMidName() { return midName; }
    public String getLastName() { return lastName; }
    public String getSuffix() { return suffix; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getEMail1() { return email1; }
    public String getEmail1Type() { return email1Type; }
    public String getEmail2() { return email2; }
    public String getEmail2Type() { return email2Type; }
    public String getPhone1() { return phone1; }
    public String getPhone1Type() { return phone1Type; }
    public String getPhone2() { return phone2; }
    public String getPhone2Type() { return phone2Type; }
}
