package hiber.service;

import hiber.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();


    List<User> findUser(String sign, String firstName);


    void deleteAll();

    void deleteUser(int id);

    public void showUsers(List<User> listUsers);
}
