package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {
   void add(User user);
   List<User> listUsers();

   List<User> findUser(String sign, String firstName);

   void deleteAll();

   void deleteUser(int id);
}
