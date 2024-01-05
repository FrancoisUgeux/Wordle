package be.he2b.atl.database.business;

import be.he2b.atl.database.db.DBManager;
import be.he2b.atl.database.dto.GameDto;
import be.he2b.atl.database.dto.UserDto;
import be.he2b.atl.database.exception.BusinessException;
import be.he2b.atl.database.exception.DbException;
import be.he2b.atl.database.exception.DtoException;
import be.he2b.atl.database.specification.UserSpecification;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author François Ugeux
 */
public class AdminModel implements AdminFacade {

    @Override
    public List<UserDto> getUsers() throws BusinessException {
        try {
            DBManager.startTransaction();
            List<UserDto> col = UserBusinessLogic.findAll();
            DBManager.validateTransaction();
            return col;
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des Users inaccessible! \n" + msg);
            }
        }
    }

    @Override
    public UserDto getUser(int userId) throws BusinessException {
        try {
            DBManager.startTransaction();
            UserDto user = UserBusinessLogic.findById(userId);
            DBManager.validateTransaction();
            return user;
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des Users inaccessible! \n" + msg);
            }
        }
    }

    @Override
    public UserDto getUser(String userName) throws BusinessException {
        try {
            DBManager.startTransaction();
            UserDto user = UserBusinessLogic.findByName(userName);
            DBManager.validateTransaction();
            return user;
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des Users inaccessible! \n" + msg);
            }
        }
    }
    
public int countGameByDifficulty(int userId, Boolean difficulty) throws BusinessException{
        try{
            DBManager.startTransaction();
            int hardGame = GameBusinessLogic.findByUserId(userId, difficulty);
            return hardGame;
        }catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Problème compte partie difficile! \n" + msg);
            }
        }
    }


    @Override
    public int addUser(String userName) throws BusinessException {
        try {
            System.out.println("addUser adminModel");
            DBManager.startTransaction();
            if(UserBusinessLogic.findByName(userName) != null){
                UserDto user = UserBusinessLogic.findByName(userName);
                return user.getId();
            }
            UserDto user = new UserDto(userName);
            int id = UserBusinessLogic.add(user);
            DBManager.validateTransaction();
            return id;
        } catch (DbException | DtoException ex) {
            try {
                DBManager.cancelTransaction();
                throw new BusinessException(ex.getMessage());
            } catch (DbException ex1) {
                throw new BusinessException(ex1.getMessage());
            }
        }
    }
    
    @Override
    public int addGame(int userId, int score, String state, String word) throws BusinessException{
        try {
            DBManager.startTransaction();
            GameDto game = new GameDto(userId, score, state, word);
            int id = GameBusinessLogic.add(game);
            DBManager.validateTransaction();
            return id;
        } catch (DbException | DtoException ex) {
            try {
                DBManager.cancelTransaction();
                throw new BusinessException(ex.getMessage());
            } catch (DbException ex1) {
                throw new BusinessException(ex1.getMessage());
            }
        }
    }

    @Override
    public void removeUser(UserDto user) throws BusinessException {
        try {
            if (user.isPersistant()) {
                DBManager.startTransaction();
                UserBusinessLogic.delete(user.getId());
                DBManager.validateTransaction();
            } else {
                throw new BusinessException("User: impossible de supprimer un user inexistant!");
            }
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Suppression de User impossible! \n" + msg);
            }
        }
    }
    @Override
    public void removeGame(GameDto game) throws BusinessException {
        try {
            if (game.isPersistant()) {
                DBManager.startTransaction();
                UserBusinessLogic.delete(game.getId());
                DBManager.validateTransaction();
            } else {
                throw new BusinessException("Game: impossible de supprimer une game inexistante!");
            }
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Suppression de Game impossible! \n" + msg);
            }
        }
    }

    @Override
    public void updateUser(UserDto user) throws BusinessException {
        try {
            DBManager.startTransaction();
            UserBusinessLogic.update(user);
            DBManager.validateTransaction();
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Mise à jour de User impossible! \n" + msg);
            }
        }
    }
    
    @Override
    public void updateGame(GameDto game) throws BusinessException {
        try {
            DBManager.startTransaction();
            GameBusinessLogic.update(game);
            DBManager.validateTransaction();
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Mise à jour de Game impossible! \n" + msg);
            }
        }
    }

    @Override
    public UserDto getRandomUser() throws BusinessException {
        List<UserDto> users = getUsers();
        if (users.isEmpty()) {
            throw new BusinessException("Aucun utilisateur présent");
        }
        Collections.shuffle(users);
        return users.get(0);
    }

    public static Collection<UserDto> getSelectedUsers(UserSpecification sel) throws BusinessException {
        try {
            DBManager.startTransaction();
            Collection<UserDto> col = UserBusinessLogic.findBySel(sel);
            DBManager.validateTransaction();
            return col;
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des Users inaccessible! \n" + msg);
            }
        }
    }
}
