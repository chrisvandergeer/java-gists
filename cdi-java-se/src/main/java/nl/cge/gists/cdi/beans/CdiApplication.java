package nl.cge.gists.cdi.beans;

import nl.cge.gists.cdi.domein.Huishouden;
import nl.cge.gists.cdi.domein.Leefsituatie;

public class CdiApplication {

    public Leefsituatie execute(Huishouden huishouden) {
        return huishouden.bepaalLeefsituatie();
    }
}
