package cz.zcu.kiv.pia.martinm.dao;

import cz.zcu.kiv.pia.martinm.domain.DataTransferObject;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Date: 15.12.2018
 *
 * @author Martin Matas
 */
public class GenericDaoJpa<E extends DataTransferObject<PK>, PK extends Serializable> implements DataAccessObject<E, PK> {

    protected EntityManager entityManager;
    protected Class<E> persistedClass;

    /**
     *
     * @param em entity manager
     * @param persistedClass entity type to be persisted by this instance
     */
    public GenericDaoJpa(EntityManager em, Class<E> persistedClass) {
        this.entityManager = em;
        this.persistedClass = persistedClass;
    }

    @Override
    public E save(E instance) {
        if(instance.getId() == null) {
            entityManager.persist(instance);
            return instance;
        } else {
            return entityManager.merge(instance);
        }

    }

    @Override
    public E findOne(PK id) {
        return entityManager.find(persistedClass, id);

    }

    @Override
    public void delete(PK id) {
        E en = entityManager.find(persistedClass, id);
        if(en != null) {
            entityManager.remove(en);
        }

    }

}
