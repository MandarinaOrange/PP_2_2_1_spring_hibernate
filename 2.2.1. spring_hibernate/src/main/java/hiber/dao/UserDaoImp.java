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
   public List<User> findUser(String model, String series) {

      List<User> foundUsers = null;

         TypedQuery<Car> findCarQuery = sessionFactory.getCurrentSession().createQuery("from Car where model = :model and series = :series")
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

      Query deleteQuery = sessionFactory.getCurrentSession().createQuery("delete from User where id =:" + id).setParameter(id, id);
      deleteQuery.executeUpdate();
   }

}
