package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> findUser(String sign, String firstName) {

      List<User> foundUsers = null;

      if (sign.equals("lastName") || sign.equals("firstName") || sign.equals("email")){
         TypedQuery<User> findSignQuery = sessionFactory.getCurrentSession()
                 .createQuery("from User where " + sign + " = :"+ sign)
                 .setParameter(sign, firstName);
         foundUsers = findSignQuery.getResultList();
      } else if (sign.equals("id")) {
         long id = Long.parseLong(firstName);
         TypedQuery<User> findSignQuery = sessionFactory.getCurrentSession()
                 .createQuery("from User where " + sign + " = :"+ sign)
                 .setParameter(sign, id);
         foundUsers = findSignQuery.getResultList();
      } else if (sign.equals("car")) {
         Scanner scan = new Scanner(System.in);
         System.out.println("Enter the series: ");
         String series = scan.nextLine();

         TypedQuery<Car> findCarQuery = sessionFactory.getCurrentSession().createQuery("from Car where model = :model and series = :series")
                 .setParameter("model", firstName)
                 .setParameter("series", series);
         List<Car> findCarList = findCarQuery.getResultList();


         if (!findCarList.isEmpty()) {
            //Car findCar = findCarList.get(0);
            foundUsers = new ArrayList<>();
            for (Car findCar : findCarList) {

               List<User> ListUser = listUsers();
               User FindUser = ListUser.stream()
                       .filter(user -> user.getCar().equals(findCar))
                       .findAny()
                       .orElse(null);

               foundUsers.add(FindUser);
            }
            return foundUsers;
         }
      }


      return foundUsers;
   }

   public void deleteAll() {
      Query deleteQuery = sessionFactory.getCurrentSession()
              .createQuery("delete from User");
      deleteQuery.executeUpdate();
      deleteQuery = sessionFactory.getCurrentSession()
              .createQuery("delete from Car");
      deleteQuery.executeUpdate();
   }

}
