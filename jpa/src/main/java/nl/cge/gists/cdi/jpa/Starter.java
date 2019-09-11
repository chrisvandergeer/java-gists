package nl.cge.gists.cdi.jpa;

import nl.cge.gists.cdi.jpa.model.Geslacht;
import nl.cge.gists.cdi.jpa.model.Gezin;
import nl.cge.gists.cdi.jpa.model.Persoon;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.Month.*;

public class Starter {

    public static void main(String... args) {

        Persoon chris = Persoon.builder()
                .voornaam("Chris")
                .achternaam("Geer, van der")
                .geslacht(Geslacht.M)
                .geboortedatum(LocalDate.of(1974, FEBRUARY, 26))
                .build();
        Persoon hanna = Persoon.builder()
                .voornaam("Hanna")
                .achternaam("Boeyenga - Geer, van der")
                .geslacht(Geslacht.V)
                .geboortedatum(LocalDate.of(1978, DECEMBER, 23))
                .build();
        Persoon floor = Persoon.builder()
                .voornaam("Floor")
                .achternaam("Geer, van der")
                .geslacht(Geslacht.V)
                .geboortedatum(LocalDate.of(2001, FEBRUARY, 13))
                .build();
        Persoon mees = Persoon.builder()
                .voornaam("Mees")
                .achternaam("Geer, van der")
                .geslacht(Geslacht.M)
                .geboortedatum(LocalDate.of(2004, AUGUST, 29))
                .build();

        Gezin vanDerGeer = Gezin.builder()
                .ouder1(chris)
                .ouder2(hanna)
                .kinderen(Arrays.asList(floor, mees))
                .build();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("gists-jpa-pu");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(vanDerGeer);
        entityManager.getTransaction().commit();

        List<Persoon> result = entityManager.createQuery("select p from Persoon p", Persoon.class).getResultList();
        System.out.println(String.format("rows found: %s", result.size()));

        entityManager.close();
        factory.close();

    }
}
