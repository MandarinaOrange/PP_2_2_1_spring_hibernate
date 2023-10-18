package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> findUser(String sign, String firstName) {return userDao.findUser(sign, firstName);}



   @Transactional
   @Override
   public void deleteAll() {userDao.deleteAll();}

   @Override
   public void deleteUser(int id) {
      userDao.deleteUser(id);
   }

   @Override
   public void showUsers(List<User> listUsers) {

      if (listUsers != null) {
         System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
         for (User user : listUsers) {
            System.out.println("Id = "+user.getId());
            System.out.println("First Name = "+user.getFirstName());
            System.out.println("Last Name = "+user.getLastName());
            System.out.println("Email = "+user.getEmail());
            System.out.println("Car: " + user.getCar());

            System.out.println();
         }
      } else {
         System.out.println("~~~~No Users!~~~~");
      }

   }

}
