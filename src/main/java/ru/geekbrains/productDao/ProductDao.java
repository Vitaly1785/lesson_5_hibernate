package ru.geekbrains.productDao;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.product.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class ProductDao {
    EntityManagerFactory factory;
    EntityManager em;

    public ProductDao() {
        this.factory = new Configuration().configure("hibernate.xml").buildSessionFactory();
        this.em = factory.createEntityManager();
    }

    public Product findById(Long id){

        Query query = em.createQuery("select p from Product p where p.id = :id");
        query.setParameter("id", id);
        try {
            return (Product) query.getSingleResult();
        }catch (NoResultException e){
            System.out.println("Product not found");
            return null;
        }
    }

    public List<Product> findAll(){
        Query query = em.createQuery("select p from Product p");
        try{
            return query.getResultList();
        } catch (NoResultException e){
            System.out.println("Table is Empty");
            return null;
        }
    }

    public void delete(Long id){
        em.getTransaction().begin();
        Product product = findById(id);
        em.remove(product);
        em.getTransaction().commit();
    }

    public void saveOrUpdate(Product product){
        Query query = em.createQuery("select p from Product p where p.title = :title");
        query.setParameter("title", product.getTitle());
        em.getTransaction().begin();
        try {
            Product product1 = (Product) query.getSingleResult();
            if (product1 != null) {
                product1.setPrice(product.getPrice());
            }
        }catch (NoResultException e){
            em.persist(product);
            System.out.println("Product will be add");
        }
        em.getTransaction().commit();
    }
}
