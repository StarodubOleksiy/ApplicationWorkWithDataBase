package ua.goit.java;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.goit.java.controllers.*;
import ua.goit.java.model.jdbc.JDBCEmployeeDao;
import ua.goit.java.model.EmployeeDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App
{
    private EmployeeController employeeController;
    private DishController dishController;
    private BookingController bookingController;
    private StorageController storageController;

    public static void main( String[] args ) throws IOException
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        App main = context.getBean(App.class);
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
         while(true)
       {
        System.out.println("Enter the word to table you want to work:");
        System.out.println("Employees-If you want to work with employees:");
        System.out.println("Dishes-If you want to work with dishes:");
        System.out.println("Booking-If you want to work with booking:");
        System.out.println("Storage-If you want to work with storage:");
           String command = br.readLine().toLowerCase();
           if(command.equals("employees"))
           {
               while (true) {
                   System.out.println("Enter the word what are you going to do:");
                   System.out.println("Add - to add new employee into data base:");
                   System.out.println("Delete -  to delete new employee into data base:");
                   System.out.println("View -  to view all employees into data base:");
                   System.out.println("Find -  to find employee by name:");
                   String newCommand = br.readLine().toLowerCase();
                   if (newCommand.equals("add")) {
                       main.employeeController.createEmployee();
                   } else if (newCommand.equals("delete")) {
                       main.employeeController.deleteEmployeeById();
                   } else if (newCommand.equals("view")) {
                    main.employeeController.getAllEmployees().forEach(System.out::println);
                   } else if (newCommand.equals("find")) {
                       System.out.println(main.employeeController.getEmployeesByName());
                   } else break;
               }

           } else if(command.equals("dishes"))
           {
               while (true) {
                   System.out.println("Enter the word what are you going to do:");
                   System.out.println("Add - to add new dish into data base:");
                   System.out.println("Delete -  to delete new dish into data base:");
                   System.out.println("View -  to view all dishes into data base:");
                   System.out.println("Find -  to find dish by name:");
                   String newCommand = br.readLine().toLowerCase();
                   if (newCommand.equals("add")) {
                       main.dishController.createDish();
                   } else if (newCommand.equals("delete")) {
                       main.dishController.deleteDishById();
                   } else if (newCommand.equals("view")) {
                       main.dishController.getAllDishees().forEach(System.out::println);
                   } else if (newCommand.equals("find")) {
                       System.out.println(main.dishController.getDishByName());
                   } else break;
               }
           }  else if(command.equals("booking"))
           {
               while (true) {
                   System.out.println("Add -  to add new booking into data base:");
                   System.out.println("SetClose -  to set booking close:");
                   System.out.println("Delete -  to delete booking from data base:");
                   System.out.println("ViewOpen -  to view all open booking into data base:");
                   System.out.println("ViewClose -  to view all close booking into data base:");
                  String newCommand = br.readLine().toLowerCase();
                   if (newCommand.equals("add")) {
                       main.bookingController.createBooking();
                   } else if(newCommand.equals("setclose")) {
                       main.bookingController.setCloseBooking();
                   } else if(newCommand.equals("delete")) {
                       main.bookingController.deleteBooking();
                   } else if(newCommand.equals("viewopen")) {
                       main.bookingController.getOpenBooking().forEach(System.out::println);
                   }  else if (newCommand.equals("viewclose")) {
                       main.bookingController.getCloseBooking().forEach(System.out::println);
                   }
                   else break;
               }

           } else if(command.equals("storage"))
           {
               while (true) {
                   System.out.println("Add -  to add new ingradient into data base:");
                   System.out.println("Delete -  to delete ingradient from data base:");
                   System.out.println("View -  to view all ingradients into data base:");
                   System.out.println("Find -  to find ingradient by name:");
                   System.out.println("Change -  to change numerosity of ingradients in storage:");
                   System.out.println("Ended -  to view all ingradients into data base that ended:");
                   String newCommand = br.readLine().toLowerCase();
                  if (newCommand.equals("add")) {
                       main.storageController.createStorage();
                   } else if(newCommand.equals("delete")) {
                       main.storageController.deleteIngradient();
                   } else if (newCommand.equals("view")) {
                       main.storageController.getAllIngradients().forEach(System.out::println);
                   } else if (newCommand.equals("find")) {
                       System.out.println(main.storageController.getIngradientByName());
                   } else if (newCommand.equals("change")) {
                      main.storageController.changeNumerosity();
                  }  else if (newCommand.equals("ended")) {
                      main.storageController.getEndedIngradients().forEach(System.out::println);
                  } else break;

               }

           }

           else break;
        }

        main.employeeController.getAllCookedMeals().forEach(System.out::println);
        System.out.println("===================================================");
        System.out.println(main.employeeController.getCookedMealsByNumber(222));
    }

    public void setDishController(DishController dishController) {
        this.dishController = dishController;
    }

    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public void setBookingController(BookingController bookingController) {
        this.bookingController = bookingController;
    }

    public void setStorageController(StorageController storageController) {
        this.storageController = storageController;
    }
}
