package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car("lada", "kalina")));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("toyota", "rav4")));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car("mersedes", "benzzzz")));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", new Car("mitsibishi", "autlander")));

      List<User> users = userService.findUser("toyota", "rav4");

      userService.showUsers(users);

      System.out.println("===============================================");


      //
      users = userService.listUsers();

      userService.showUsers(users);

      System.out.println("===============================================");

      userService.deleteUser(2);

      users = userService.listUsers();

      userService.showUsers(users);



      context.close();
   }


}
