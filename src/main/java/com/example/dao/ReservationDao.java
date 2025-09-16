package com.example.dao;

import com.example.entity.Reservation;
import com.example.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class ReservationDao extends GenericDao<Reservation> {

    private EntityManagerFactory factory;

    public ReservationDao(EntityManagerFactory factory) {
        super(Reservation.class);
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
    public List<Reservation> find(User userId) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> q = cb.createQuery(Reservation.class);

        Root<Reservation> c = q.from(Reservation.class);
        ParameterExpression<User> paramId = cb.parameter(User.class);
        q.select(c).where(cb.equal(c.get("user"), paramId));
        TypedQuery<Reservation> query = em.createQuery(q);
        query.setParameter(paramId, userId);

        return query.getResultList();
    }

}
