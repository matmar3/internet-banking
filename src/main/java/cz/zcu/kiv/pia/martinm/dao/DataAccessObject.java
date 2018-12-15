package cz.zcu.kiv.pia.martinm.dao;

import cz.zcu.kiv.pia.martinm.domain.DataTransferObject;

import java.io.Serializable;

/**
 * Date: 15.12.2018
 *
 * @author Martin Matas
 */
public interface DataAccessObject<E extends DataTransferObject<PK>, PK extends Serializable> {

    /**
     * Either inserts new or updates existing instance.
     *
     * @param instance to be persisted
     * @return persisted instance
     */
    E save(E instance);

    /**
     *
     * @param id - id of entity
     * @return instance with the given id or null if not found
     */
    E findOne(PK id);

    /**
     * Removes the given entity from persistence.
     *
     * @param id if of the entity instance
     */
    void delete(PK id);

}
