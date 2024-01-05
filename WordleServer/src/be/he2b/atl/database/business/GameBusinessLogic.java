package be.he2b.atl.database.business;

import be.he2b.atl.database.db.GameDB;
import be.he2b.atl.database.dto.GameDto;
import be.he2b.atl.database.exception.DbException;
import be.he2b.atl.database.specification.GameSpecification;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author François Ugeux
 */
public class GameBusinessLogic {

    static int add(GameDto game) throws DbException {
        return GameDB.insertDb(game);
    }

    static void delete(int id) throws DbException {
        GameDB.deleteDb(id);
    }

    static void update(GameDto game) throws DbException {
        GameDB.updateDb(game);
    }

    static GameDto findByUser(int userId) throws DbException {
        GameSpecification sel = new GameSpecification(userId, 0, null, null, null);
        Collection<GameDto> col = GameDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }
    
    static int findByUserId(int userId, Boolean difficulty) throws DbException {
        GameSpecification sel = new GameSpecification(userId, 0, null, null, difficulty.toString());
        Collection<GameDto> col = GameDB.getCollection(sel);
        return col.size();
    }


    static GameDto findById(int id) throws DbException {
        GameSpecification sel = new GameSpecification(id);
        Collection<GameDto> col = GameDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }

    static List<GameDto> findBySel(GameSpecification sel) throws DbException {
        List<GameDto> col = GameDB.getCollection(sel);
        return col;
    }

    static List<GameDto> findAll() throws DbException {
        GameSpecification sel = new GameSpecification(0);
        List<GameDto> col = GameDB.getCollection(sel);
        return col;
    }
}
