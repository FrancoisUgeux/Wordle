package be.he2b.atl.database.db;

import be.he2b.atl.database.dto.GameDto;
import be.he2b.atl.database.exception.DbException;
import be.he2b.atl.database.exception.DtoException;
import be.he2b.atl.database.specification.GameSpecification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author François Ugeux
 */
public class GameDB {

    private static final String recordName = "GAME";

    /**
     *
     * @return @throws DbException
     */
    public static List<GameDto> getAllGames() throws DtoException, DbException {
        List<GameDto> games = getCollection(new GameSpecification(0));
        return games;
    }

    /**
     *
     * @param sel
     * @return
     * @throws DbException
     */
    public static List<GameDto> getCollection(GameSpecification sel) throws DbException {
        List<GameDto> al = new ArrayList<>();
        try {
            String query = "Select id, userId, score, state, word, hardmode  FROM GAMES";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            String where = "";
            if (sel.getId() != 0) {
                where = where + " id = ? ";
            }
            if (sel.getUser() != 0) {
                if (!where.isEmpty()) {
                    where = where + " AND ";
                }
                where = where + " userId like ? ";
            }

            if (sel.getScore() != 0) { //voir la logique ici
                if (!where.isEmpty()) {
                    where = where + " AND ";
                }
                where = where + " score like ? ";
            }
            if(sel.getHardmode() != null && !sel.getHardmode().isEmpty()){
                if(!where.isEmpty()){
                    where = where + " AND ";
                }
                where = where + " hardmode like ?";
            }
            if (where.length() != 0) {
                where = " where " + where + " order by userId";
                query = query + where;
                stmt = connexion.prepareStatement(query);
                int i = 1;
                if (sel.getId() != 0) {
                    stmt.setInt(i, sel.getId());
                    i++;

                }
                if (sel.getUser() != 0) {
                    stmt.setString(i, sel.getUser() + "%");
                    i++;
                }
                if (sel.getScore() != 0) { //voir logique ici
                    stmt.setString(i, sel.getScore() + "%");
                    i++;
                }
                if (sel.getState() != null && !sel.getState().isEmpty()) {
                    stmt.setString(i, sel.getState() + "%");
                    i++;
                }
                if (sel.getWord() != null && !sel.getWord().isEmpty()) {
                    stmt.setString(i, sel.getWord() + "%");
                    i++;
                }
            } else {
                query = query + " Order by userId";
                stmt = connexion.prepareStatement(query);
            }
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new GameDto(rs.getInt("id"), rs.getInt("userId"), rs.getInt("score"), rs.getString("state"), rs.getString("word")));
            }
        } catch (DtoException ex) {
            throw new DbException("Instanciation de " + recordName + " impossible:\nDTOException: " + ex.getMessage());
        } catch (java.sql.SQLException eSQL) {
            throw new DbException("Instanciation de " + recordName + " impossible:\nSQLException: " + eSQL.getMessage());
        }
        return al;
    }

    public static void deleteDb(int id) throws DbException {
        try {
            java.sql.Statement stmt = DBManager.getConnection().createStatement();
            stmt.execute("delete from Games where id=" + id);
        } catch (Exception ex) {
            throw new DbException(recordName + ": suppression impossible\n" + ex.getMessage());
        }
    }

    public static void updateDb(GameDto record) throws DbException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();

            java.sql.PreparedStatement update;
            String sql = "Update GAMES set "
                    + "userId=?, "
                    + "score=? "
                    + "state=? "
                    + "word=? "
                    + "where id=?";
            update = connexion.prepareStatement(sql);
            update.setInt(1, record.getUser());
            update.setInt(2, record.getScore());
            update.setString(3, record.getState());
            update.setString(4, record.getWord());
            update.setInt(5, record.getId());
            update.executeUpdate();
        } catch (Exception ex) {
            throw new DbException(recordName + ", modification impossible:\n" + ex.getMessage());
        }
    }

    public static int insertDb(GameDto record) throws DbException {
        try {
            int num = SequenceDB.getNextNum("GAMES");
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = connexion.prepareStatement(
                    "Insert into GAMES(id, userId, score, state, word) "
                    + "values(?, ?, ?, ?, ?)");
            insert.setInt(1, num);
            insert.setInt(2, record.getUser());
            insert.setInt(3, record.getScore());
            insert.setString(4, record.getState());
            insert.setString(5, record.getWord());
            insert.executeUpdate();
            return num;
        } catch (Exception ex) {
            throw new DbException(recordName + ": ajout impossible\n" + ex.getMessage());
        }
    }
}
