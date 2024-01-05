
package be.he2b.atl.database.controller;

import be.he2b.atl.database.business.AdminFacade;
import be.he2b.atl.database.dto.UserDto;
import be.he2b.atl.database.exception.BusinessException;
import be.he2b.atl.database.view.View;
import java.util.List;

/**
 * not used for now, need to be updated to use
 * @author François Ugeux
 */
/**
 * The <code> Controller </code>, accepts input from console and converts it to
 * commands for the model or view. This controller asks input from the view
 * until the model ends.
 */
public class Controller {

    private final AdminFacade model;
    private final View view;

    /**
     *
     * Constructs the model controller for a console view.
     *
     * @param model model to play.
     *
     * @param view console view.
     *
     */
    public Controller(AdminFacade model, View view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        try {
            view.displayWelcome();
            
            List<UserDto> users = model.getUsers();
            view.displayAllUsers(users);
            
            view.displayAddUser("ZZZ","Zorglub");
            int userId = model.addUser("Zorglub");
            
            view.displayGetUserById(userId);
            UserDto user = model.getUser(userId);
            view.displayUser(user);
            
            view.displayUpdateUser(userId);
            user.setName("Bob Leponge");
            model.updateUser(user);
            
            view.displayGetByName("Bob Leponge");
            user = model.getUser("Bob Leponge");
            view.displayUser(user);
            
            view.displayRemoveUser(userId);
            model.removeUser(user);
            
            view.displayAllUsers(users);
        } catch (BusinessException ex) {
            view.displayError(ex.getMessage());
        }

    }

}
