package be.he2b.atl.database.dto;

import be.he2b.atl.database.exception.DtoException;

/**
 *
 * @author François Ugeux
 */
public class UserDto extends EntityDto<Integer> {

    private String name;

    /**
     * constructeur d'un user persistant
     *
     * @param id
     * @param name
     * @throws DtoException
     */
    public UserDto(Integer id, String name) throws DtoException {
        this(name);
        this.id = id;
    }

    /**
     * constructeur d'un user non persistant
     *
     * @param name
     * @throws DtoException
     */
    public UserDto(String name) throws DtoException {
        if (name == null) {
            throw new DtoException("l'attributs name est obligatoire");
        }
        this.name = name;

    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[UserDto] (" + getId() + ") " + getName();
    }

}
