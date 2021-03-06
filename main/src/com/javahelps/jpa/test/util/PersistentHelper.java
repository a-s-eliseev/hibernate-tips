package com.javahelps.jpa.test.util;

import com.javahelps.jpa.test.PersistenceUnitInfoImpl;
import com.javahelps.jpa.test.model.Post;
import com.javahelps.jpa.test.model.PostComment;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.*;

public class PersistentHelper {

    public static EntityManager getEntityManager(Class[] persistentClasses) {
        Map<String, Object> options = new HashMap<>();
        options.put(DRIVER, "com.mysql.jdbc.Driver");
        options.put(URL, "jdbc:mysql://localhost:3306/world?characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=UTC");
        options.put(DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        options.put(USER, "root");
        options.put(PASS, "root");
        options.put(HBM2DDL_AUTO, "create-drop");
        options.put(SHOW_SQL, true);

        PersistenceUnitInfoImpl persistenceUnitInfo = new PersistenceUnitInfoImpl(persistentClasses);

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(persistenceUnitInfo, options);

        return entityManagerFactory.createEntityManager();
    }
}
