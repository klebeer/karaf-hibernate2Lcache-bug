package org.ecuadorjug.jpa.command;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.ecuadorjug.jpa.User;
import org.ecuadorjug.jpa.UserService;

import java.util.List;


/**
 * @author Kleber Ayala
 */

@Service
@Command(scope = "users", name = "list", description = "List User")
public class ListUserCommand implements Action {


    @Reference
    UserService userService;

    @Override
    public Object execute() throws Exception {

        List<User> userList = userService.list();
        userList.forEach(System.out::println);
        return null;
    }

}