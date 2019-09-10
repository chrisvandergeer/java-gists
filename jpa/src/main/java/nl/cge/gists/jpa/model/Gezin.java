package nl.cge.gists.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Gezin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Persoon ouder1;

    @OneToOne(cascade = CascadeType.ALL)
    private Persoon ouder2;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Persoon> kinderen = new ArrayList<>();

}
