package org.ecuadorjug.jpa;

import java.util.List;


public interface UserService {

    List<User> list();

    User get(Long id);

    void add(User user);

    void remove(Long id);

}
