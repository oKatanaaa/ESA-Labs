package com.example.lab1.DAO;

import com.example.lab1.models.Driver;
import com.example.lab1.models.Shop;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class DriverDaoImpl implements DriverDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public Driver get(Integer id) {
        return em.find(Driver.class, id);
    }

    @Override
    public List<Driver> getAll() {
        TypedQuery<Driver> getAllQuery = em.createQuery("select d from Driver d", Driver.class);
        List<Driver> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(Driver driver) {
        em.persist(driver);
    }

    @Override
    public void update(Driver driver) {
        em.merge(driver);
    }

    @Override
    public void delete(Driver driver) {
        em.remove(em.contains(driver) ? driver : em.merge(driver));
    }

}
