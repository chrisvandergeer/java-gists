package nl.cge.gists.cdi.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Persoon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate geboortedatum;

    @Column(nullable = false)
    private String voornaam;

    @Column(nullable = false)
    private String achternaam;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Geslacht geslacht;

}
