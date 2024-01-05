package be.he2b.atl.database.business;


import be.he2b.atl.database.dto.GameDto;
import be.he2b.atl.database.dto.UserDto;
import be.he2b.atl.database.exception.BusinessException;
import java.util.List;

/**
 * Administrator facade.
 * François Ugeux
 */
public interface AdminFacade {

    /**
     * Returns a list of all users.
     *
     * @return a list of all users.
     * @throws BusinessException if the query failed.
     */
    List<UserDto> getUsers() throws BusinessException;

    /**
     * Returns the unique user with the given id.
     *
     * @param id user's id.
     * @return the unique user with the given id.
     * @throws BusinessException
     */
    UserDto getUser(int id) throws BusinessException;

    /**
     * Returns the last user with the given name.
     *
     * @param name user's name.
     * @return the last user with the given name.
     * @throws be.he2b.atl.database.exception.BusinessException
     * @throwsBusinessException if the query failed
     */
    UserDto getUser(String name) throws BusinessException;

    /**
     * Creates a user and insert it in the database.Returns the user's id.
     *
     * @param name user's name.
     * @return the user's id.
     * @throws BusinessException
     */
    int addUser(String name) throws BusinessException;
    
    /**
     * Create a game and insert it in the database.Returns the game's id.
     * 
     * @param userID
     * @param score
     * @param state
     * @param word
     * @return 
     * @throws BusinessException 
     */
    int addGame(int userID, int score, String state, String word) throws BusinessException;

    /**
     * Removes the given user.
     *
     * @param user user to delete.
     * @throws BusinessException
     */
    void removeUser(UserDto user) throws BusinessException;
    
    /**
     * Removes the given game.
     *
     * @param game game to delete.
     * @throws BusinessException
     */
    void removeGame(GameDto game) throws BusinessException;
    
    

    /**
     * Updates the given user.
     *
     * @param current
     * @throws BusinessException
     */
    void updateUser(UserDto current) throws BusinessException;
    
    /**
     * Updates the given game.
     *
     * @param current
     * @throws BusinessException
     */
    void updateGame(GameDto current) throws BusinessException;

    /**
     * Returns a random user from the database.
     *
     * @return a random user from the database.
     * @throws BusinessException
     */
    UserDto getRandomUser() throws BusinessException;

}
