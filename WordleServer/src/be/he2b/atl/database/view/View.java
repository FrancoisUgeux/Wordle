
package be.he2b.atl.database.view;

import be.he2b.atl.database.business.AdminFacade;
import be.he2b.atl.database.dto.UserDto;
import java.util.List;

/**
 * Only implemented later for test purpose
 * @author François Ugeux
 */
public class View {

    private final AdminFacade game;

    /**
     * Constructs the console view.
     *
     * @param game imodel of the app.
     */
    public View(AdminFacade game) {
        this.game = game;
    }

    public void displayUser(UserDto user) {
        System.out.print("\t" + user.getId());
        //System.out.print("\t" + user.getLogin());
        System.out.println("\t" + user.getName());
        System.out.println("");
    }

    public void displayWelcome() {
        System.out.println("\tBienvenue dans la demo JDBC\n");
    }

    public void displayAllUsers(List<UserDto> users) {
        System.out.println("\tLa liste des utilisateurs en DB est :\n");
        for (UserDto user : users) {
            System.out.print("\t" + user.getId());
            //System.out.print("\t" + user.getLogin());
            System.out.println("\t" + user.getName());
        }
        System.out.println("");
    }

    public void displayAddUser(String login, String name) {
        System.out.println("\tAjout de l'utilisateur " + login + " " + name + "\n");
    }

    public void displayGetUserById(int id) {
        System.out.println("\tRecherche de l'utilisateur d'ID " + id + "\n");
    }

    public void displayUpdateUser(int id) {
        System.out.println("\tMise à jour de l'utilisateur d'ID " + id + "\n");
    }

    public void displayGetByName(String name) {
        System.out.println("\tRecherche des utilisateurs de nom " + name + "\n");
    }

    public void displayRemoveUser(int id) {
        System.out.println("\tSuppression de l'utilisateur d'ID " + id + "\n");
    }

    public void displayError(String message) {
        System.out.println("ERREUR\t" + message);
    }

}
