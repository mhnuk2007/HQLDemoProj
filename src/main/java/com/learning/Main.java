package com.learning;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Step 1: Setup Hibernate
        SessionFactory sf = new Configuration()
                .configure()
                .addAnnotatedClass(Laptop.class)
                .buildSessionFactory();

        Session session = sf.openSession();

        // Step 2: Fetch by Primary Key (uses SQL, not HQL)
        System.out.println("\nFetching Laptop with ID 2");
        Laptop l1 = session.get(Laptop.class, 2);
        System.out.println(l1);

        // Step 3: Fetch all laptops using HQL
        System.out.println("\nAll Laptops (HQL: from Laptop)");
        Query q1 = session.createQuery("FROM Laptop", Laptop.class);
        List<Laptop> allLaptops = q1.getResultList();
        allLaptops.forEach(System.out::println);

        // Step 4: Filter using HQL
        System.out.println("\nLaptops with 16 GB RAM");
        Query q2 = session.createQuery("FROM Laptop WHERE ram = 16", Laptop.class);
        List<Laptop> laptops16GB = q2.getResultList();
        laptops16GB.forEach(System.out::println);

        // Step 5: Parameterized query
        System.out.println("\nLaptops with 32 GB RAM (using parameter)");
        Query q3 = session.createQuery("FROM Laptop WHERE ram = ?1", Laptop.class);
        q3.setParameter(1, 32);
        List<Laptop> laptops32GB = q3.getResultList();
        laptops32GB.forEach(System.out::println);

        // Step 6: Projection query (select specific columns)
        System.out.println("\nProjection: Brand & RAM where RAM > 16");
        Query q4 = session.createQuery("SELECT brand, ram FROM Laptop WHERE ram > 16");
        List<Object[]> rows = q4.getResultList();
        for (Object[] row : rows) {
            System.out.println("Brand: " + row[0] + ", RAM: " + row[1]);
        }

        // Step 7: Clean up
        session.close();
        sf.close();
    }
}
