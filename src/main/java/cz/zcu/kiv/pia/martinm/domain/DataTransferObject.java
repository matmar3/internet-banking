package cz.zcu.kiv.pia.martinm.domain;

import java.io.Serializable;

/**
 * Base interface for all entities to make implementation of generic dao easier.
 *
 * @param <PK> - represents type of the entity's primary key.
 *
 * Date: 06.12.2018
 *
 * @author Martin Matas
 */
public interface DataTransferObject<PK extends Serializable> {

    /**
     *
     * @return  primary key of the instance
     */
    PK getId();

}
