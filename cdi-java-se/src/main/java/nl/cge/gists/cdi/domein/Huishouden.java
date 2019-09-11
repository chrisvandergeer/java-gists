package nl.cge.gists.cdi.domein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static nl.cge.gists.cdi.domein.Leefsituatie.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Huishouden {

    private Persoon hoofdbewoner;
    private Persoon partner;
    private List<Persoon> kinderen;

    public Leefsituatie bepaalLeefsituatie() {
        if (heeftPartner()) {
            return heeftKinderen() ? PARTNER_MET_KINDEREN : PARTNER;
        }
        return heeftKinderen() ? ALLEENSTAAND_MET_KINDEREN : ALLEENSTAAND;
    }

    public boolean heeftKinderen() {
        return kinderen.stream()
                .filter(p -> p.getLeeftijd() < 18)
                .map(Persoon::getAdres)
                .anyMatch(adres -> hoofdbewoner.getAdres().equals(adres));
    }

    public boolean heeftPartner() {
        return this.partner != null;
    }

}
