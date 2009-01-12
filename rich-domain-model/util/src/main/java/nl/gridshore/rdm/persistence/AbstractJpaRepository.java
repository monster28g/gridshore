/*
 * Copyright (c) 2008 JTeam B.V.
 * www.jteam.nl
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * JTeam B.V. ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with JTeam.
 */
package nl.gridshore.rdm.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AbstractJpaRepository<T extends BaseEntity> implements Repository<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<T> entityType;

    public AbstractJpaRepository(Class<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public void create(final T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(final T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void delete(final long id) {
        entityManager.remove(entityManager.getReference(entityType, id));
    }

    @Override
    public T findById(final long id) {
        return entityManager.find(entityType, id);
    }

    @Override
    public T load(final long id) {
        T entity = entityManager.find(entityType, id);
        if (entity == null) {
            throw new EntityNotFoundException(String.format("Entity [%s] with id [%s] not found", entityType, id));
        }
        return entity;
    }
}
