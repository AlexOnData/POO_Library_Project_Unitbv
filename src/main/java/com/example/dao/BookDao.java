package com.example.dao;

import com.example.entity.Book;
import com.example.entity.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookDao extends GenericDao<Book>  {

    private EntityManagerFactory factory;

    public BookDao(EntityManagerFactory factory) {
        super(Book.class);
        this.factory = factory;
    }

    @Override
    public EntityManager getEntityManager() {
        try {
            return factory.createEntityManager();
        } catch (Exception ex) {
            System.out.println("The entity manager cannot be created!");
            return null;
        }
    }

    // for login
    public List<Book> find(Integer id) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> q = cb.createQuery(Book.class);

        Root<Book> c = q.from(Book.class);
        ParameterExpression<Integer> paramId = cb.parameter(Integer.class);
        q.select(c).where(cb.equal(c.get("id"), paramId));
        TypedQuery<Book> query = em.createQuery(q);
        query.setParameter(paramId, id);

        List<Book> results = query.getResultList();
        return results;
    }

    public List<Book> find(String author) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> q = cb.createQuery(Book.class);

        Root<Book> c = q.from(Book.class);
        ParameterExpression<String> paramName = cb.parameter(String.class);
        q.select(c).where(cb.equal(c.get("author"), paramName));
        TypedQuery<Book> query = em.createQuery(q);
        query.setParameter(paramName, author);

        return query.getResultList();
    }

}
