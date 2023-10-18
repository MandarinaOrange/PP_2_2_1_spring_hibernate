package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;



public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("toyota", "rav4")));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car("mersedes", "benzzzz")));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", new Car("mitsibishi", "autlander")));

      List<User> users = userService.findUser("toyota", "rav4");

      userService.showUsers(users);


      //userService.deleteUser(2);



      /*int comand = 1;
      String firstName, secondName, email, carModel, carSeries, sign;
      Scanner keyboard = new Scanner(System.in);
      while (comand != 0) {
         System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                 "1 - Add guy\n" +
                 "2 - Find guy\n" +
                 "3 - must be correcting, but nothing\n" +
                 "4 - Delete all strings in table\n" +
                 "5 - Show all users\n" +
                 "~~~~~~~~~~~~~~~~~~~~~~~~\n");
         System.out.println("Enter the comand: ");
         comand = Integer.parseInt(keyboard.nextLine());

         switch (comand) {
            case 1 :
               System.out.println("Please enter the name, surname, email, car model and car series: ");
               firstName = keyboard.nextLine();
               secondName = keyboard.nextLine();
               email = keyboard.nextLine();
               carModel = keyboard.nextLine();
               carSeries = keyboard.nextLine();
               userService.add(new User(firstName, secondName, email, new Car(carModel, carSeries)));
               break;

            case 2 :
               System.out.println("Please enter the sign and the 1st parameter for searching: ");
               System.out.println("Corrected signs: \n" +
                       "id     firstName    lastName     email    car");
               sign = keyboard.nextLine();
               firstName = keyboard.nextLine();
               List<User> foundUsers = userService.findUser(sign,firstName);
               showUsers(foundUsers);
               break;


            case 4 :
               userService.deleteAll();
               System.out.println("HAHAHA ALL WAS DELETED!\n" +
                       "~~~~~~~~~~~~~~~~~~~~~~~~");
               break;

            case 5 :
               showUsers(userService.listUsers());
               System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
               break;

            default:
               System.out.println("~~~~~~Please try again, stupid human!~~~~~~");
         }


      }*/


      context.close();
   }


}
