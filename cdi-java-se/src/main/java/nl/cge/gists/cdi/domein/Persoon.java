package nl.cge.gists.cdi.domein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Persoon {

    private String naam;
    private LocalDate geboortedatum;
    private Adres adres;

    public Integer getLeeftijd() {
        return Period.between(geboortedatum, LocalDate.now()).getYears();
    }
}
