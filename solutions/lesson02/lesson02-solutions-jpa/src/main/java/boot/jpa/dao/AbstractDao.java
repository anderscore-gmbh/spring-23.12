package boot.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public abstract class AbstractDao<T, ID> {

    @PersistenceContext
    private EntityManager em;

    private Class<T> clazz;

    public AbstractDao(Class<T> clazz){
        this.clazz = clazz;
    }

    public void create(T entity){
        em.persist(entity);
    }

    public T find(ID id) {
        return em.find(clazz, id);
    }

    public List<T> findAll(){
        return em.createQuery("from " + clazz.getName()).getResultList();
    }

    public void update(T entity){
        em.merge(entity);
    }

    public void delete(T entity){
        em.remove(entity);
    }
}