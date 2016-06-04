package ua.goit.java.model;

import java.time.*;

/**
 * Created by Администратор on 04.05.16.
 */
public class Employee {


    private int employeeID;
    private String firstName;
    private String lastName;
    private String birthDay_date;
    private int telephone;
    private float salary;
    private String position;

    public Employee(int employeeID, String firstName, String lastName, String birthDay_date, int telephone, float salary, String position) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay_date = birthDay_date;
        this.telephone = telephone;
        this.salary = salary;
        this.position = position;
    }

    public Employee()
    {

    }




    public int getID() {
        return employeeID;
    }

    public void setID(int ID) {
        this.employeeID= ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public void setBirthDay_date(String birthDay_date) {
        this.birthDay_date = birthDay_date;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDay_date() {
        return birthDay_date;
    }

    public int getTelephone() {
        return telephone;
    }

    public float getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                ", employeeID='" + employeeID + '\'' +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", birthday date=" + birthDay_date +
                ", telephone='" + telephone + '\'' +
                ", salary=" + salary +
                ", position='" + position + '\'' +
                     '}';
    }
}
