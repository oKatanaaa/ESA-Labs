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
public class ShopDaoImpl implements ShopDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;


    @Override
    public Shop get(Integer id) {
        return em.find(Shop.class, id);
    }

    @Override
    public List<Shop> getAll() {
        TypedQuery<Shop> getAllQuery = em.createQuery("select s from Shop s", Shop.class);
        List<Shop> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(Shop shop) {
        em.persist(shop);
    }

    @Override
    public void update(Shop shop) {
        em.merge(shop);
    }

    @Override
    public void delete(Shop shop) {
        em.remove(em.contains(shop) ? shop : em.merge(shop));
    }
}
