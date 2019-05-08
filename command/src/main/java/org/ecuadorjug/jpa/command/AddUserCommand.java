package org.ecuadorjug.jpa.command;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.ecuadorjug.jpa.User;
import org.ecuadorjug.jpa.UserService;


/**
 * @author Kleber Ayala
 */

@Service
@Command(scope = "users", name = "add", description = "Add a User")
public class AddUserCommand implements Action {

    @Argument(index = 0, name = "name", description = "User Name", required = true, multiValued = false)
    String name;
    @Reference
    private UserService userService;

    @Override
    public Object execute() throws Exception {
        User user = new User();
        user.setUsername(name);
        userService.add(user);
        return null;
    }

}