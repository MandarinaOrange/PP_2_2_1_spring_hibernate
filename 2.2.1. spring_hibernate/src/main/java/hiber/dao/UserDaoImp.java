package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@EnableTransactionManagement
@Transactional
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

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
            foundUsers = new ArrayList<>();
            for (Car findCar : findCarList) {

               List<User> listUser = listUsers();
               User findUser = listUser.stream()
                       .filter(user -> user.getCar().equals(findCar))
                       .findAny()
                       .orElse(null);

               foundUsers.add(findUser);
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

      User user = null;

      user =  sessionFactory.getCurrentSession().get(User.class, (long) id);

      sessionFactory.getCurrentSession().remove(user);

   }

}
