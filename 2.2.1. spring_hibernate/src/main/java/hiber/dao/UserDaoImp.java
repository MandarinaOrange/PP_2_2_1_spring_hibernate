package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
   public List<User> findUser(String model, String series) {

      List<User> foundUsers = null;

      Session session = sessionFactory.openSession();

         TypedQuery<Car> findCarQuery = session.createQuery("from Car where model = :model and series = :series")
                 .setParameter("model", model)
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

      session.getTransaction().commit();

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

   @Override
   public void deleteUser(int id) {
      Session session = sessionFactory.getCurrentSession();

      User user = null;

      // user = (User) session.get(User.class, (long)id);

      //session.getTransaction().commit();



      try {
         session.beginTransaction();

         user = (User) session.get(User.class, id);

         session.delete(user);

         session.getTransaction().commit();
      }
      catch (HibernateException e) {

         session.getTransaction().rollback();
      }
      System.out.println(user);
      //session.delete(user);
      //session.getTransaction().commit();
   }

}
