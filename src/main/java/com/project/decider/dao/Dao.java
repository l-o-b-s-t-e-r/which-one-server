package com.project.decider.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Dao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Return the persistent instance of the given entity class with the given identifier,
     * or null if there is no such persistent instance.
     *
     * @param entityClass a persistent class
     * @param id          an identifier
     * @return a persistent instance or null
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> entityClass, Serializable id) {
        T entity = currentSession().get(entityClass, id);
        return entity;
    }

    /**
     * Return all persistent instances of the given entity class,
     * or null if there is no such persistent instance.
     *
     * @param entityClass a persistent class
     * @return List of persistent instances or null
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> all(Class<T> entityClass) {
        List<T> entities = currentSession().createCriteria(entityClass).list();
        return entities;
    }

    /**
     * Persist the given transient instance.
     *
     * @param entity a transient instance of a persistent class
     * @return saved entity
     */
    public <T> T save(T entity) {
        currentSession().saveOrUpdate(entity);
        return entity;
    }

    public <T> T update(T entity) {
        currentSession().update(entity);
        return entity;
    }

    /**
     * Persists collection of entities wrapped in list
     *
     * @param entities - list entities to save
     */
    public <T> Collection<T> saveAll(Collection<T> entities) {
        Session session = currentSession();
        List<T> entitiesList = new ArrayList<>(entities);
        for (int i = 0; i < entities.size(); i++) {
            session.save(entitiesList.get(i));
            if (i % 20 == 0) {
                session.flush();
                session.clear();
            }
        }
        return entitiesList;
    }

    /**
     * Remove a persistent instance from the datastore.
     *
     * @param entity - the instance to be removed
     */

    public <T> void delete(T entity) {
        currentSession().delete(entity);
    }

    /**
     * Return the persistent instance of the given entity class with the given parameters,
     * It uses HQL. If there are there several entities that meet given parameters, returns first item.
     *
     * @param hql        a hql query
     * @param parameters pairs of key/value parameters which will be added to "where" part of the query
     * @return a persistent instance or null
     */
    public <T> T getBy(String hql, Map<String, String> parameters) {
        Query query = currentSession().createQuery(hql);
        query.setProperties(parameters);
        T entity =  (T) query.uniqueResult();
        return entity;
    }

    public void update(String sql, Map<String, List<Serializable>> parametrs){
        Query query = currentSession().createSQLQuery(sql);
        for (Map.Entry<String, List<Serializable>> s : parametrs.entrySet())
            query.setParameterList(s.getKey(), s.getValue());

        query.executeUpdate();
    }

    /**
     * Return the persistent instances of the given entity class which meet given parameters,
     *
     * @param hql        a hql query
     * @param parameters pairs of key/value parameters which will be added to "where" part of the query
     * @return list of persistent instances or null
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getAllBy(String hql, Map<String, String> parameters) {
        Query query = currentSession().createQuery(hql);
        query.setProperties(parameters);
        List<T> entities = (List<T>) query.list();
        return entities;
    }

    ;

    /**
     * Return the persistent instance of the given entity class with the given parameters,
     * It uses Criterians. If there are there several entities that meet given parameters, returns first item.
     *
     * @param criteria criterion to match the search against, for creations of Criterion use
     *                   Restrictions class e.g Restrictions.eq(propertyName, value)
     * @return a persistent instance or null
     */
    public <T> T getBy(DetachedCriteria criteria) {
        Criteria executableCriteria = criteria.getExecutableCriteria(currentSession());
        T entity = (T) executableCriteria.uniqueResult();
        return entity;
    }


    /**
     * Return the persistent instances of the given entity class with the given parameters,
     * It uses Criterians.
     *
     * @param criteria criterion to match the search against, for creations of Criterion use
     *                   Restrictions class e.g Restrictions.eq(propertyName, value)
     * @return list of persistent instances or null
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getAllBy(DetachedCriteria criteria) {
        Criteria executableCriteria = criteria.getExecutableCriteria(currentSession());
        List<T> entities = executableCriteria.list();
        return entities;
    }

    public <T> List<T> getByCriterionWithOrderBy(Class<T> entityClass, Criterion criterion, int maxResult, String orderByPropertyName) {
        Criteria criteria = currentSession().createCriteria(entityClass).addOrder(Order.desc(orderByPropertyName))
                .setMaxResults(maxResult)
                .add(criterion);

        return criteria.list();
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}