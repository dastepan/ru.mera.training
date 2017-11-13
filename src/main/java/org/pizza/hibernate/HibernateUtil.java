package org.pizza.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class HibernateUtil {
    private  SessionFactory sessionFactory;

    private void init() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public Session getNewSession(){
        return sessionFactory.openSession();
    }
}
