package br.com.gsete.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequestScoped
public class EntityManagerProducer {
	 
    @PersistenceContext
    private EntityManager entityManager;
 
    @Produces
    @RequestScoped
    public EntityManager producerEntityManager() {
        return entityManager;
    }
}
