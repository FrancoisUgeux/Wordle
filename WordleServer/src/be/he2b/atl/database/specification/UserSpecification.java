
package be.he2b.atl.database.specification;

/**
 *
 * @author François Ugeux
 */
public class UserSpecification {

    private int id;
    private String name;

    /**
     *
     * @param login
     * @param name
     */
    public UserSpecification(String name) {
        this.id = 0;
        this.name = name;
    }

    /**
     *
     * @param id
     */
    public UserSpecification(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

