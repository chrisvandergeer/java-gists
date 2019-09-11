package nl.cge.gists.cdi.beans;

import nl.cge.gists.cdi.domein.Adres;
import nl.cge.gists.cdi.domein.Huishouden;
import nl.cge.gists.cdi.domein.Leefsituatie;
import nl.cge.gists.cdi.domein.Persoon;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CdiApplicationTest {

    private WeldContainer container;

    @BeforeAll
    void setup() {
        Weld weld = new Weld();
        container = weld.initialize();
    }

    @AfterAll
    void tearDown() {
        container.close();
    }

    @MethodSource("huishoudens")
    @ParameterizedTest
    void test(Huishouden huishouden, Leefsituatie expLeefsituatie) {
        assertEquals(expLeefsituatie, huishouden.bepaalLeefsituatie());
    }

    private Stream<Arguments> huishoudens() {
        return Stream.of(
                Arguments.of(createHuishoudenZonderPartnerZonderKinderen(), Leefsituatie.ALLEENSTAAND),
                Arguments.of(createHuishoudenMetPartnerZonderKinderen(), Leefsituatie.PARTNER),
                Arguments.of(createHuishoudenZonderPartnerMetKinderen(), Leefsituatie.ALLEENSTAAND_MET_KINDEREN),
                Arguments.of(createHuishoudenMetKinderenOpAnderAdres(), Leefsituatie.ALLEENSTAAND),
                Arguments.of(createHuishoudenMetPartnerMetKinderen(), Leefsituatie.PARTNER_MET_KINDEREN),
                Arguments.of(createHuishoudenMetPartnerMetKinderenBovenDe18Jaar(), Leefsituatie.PARTNER)
        );
    }

    private Huishouden createHuishoudenMetPartnerMetKinderenBovenDe18Jaar() {
        Huishouden huishouden = createHuishouden();
        huishouden.getKinderen().forEach(k -> k.setGeboortedatum(LocalDate.now().minusYears(19)));
        return huishouden;
    }

    private Huishouden createHuishoudenMetPartnerMetKinderen() {
        return createHuishouden();
    }

    private Huishouden createHuishoudenZonderPartnerMetKinderen() {
        Huishouden huishouden = createHuishouden();
        huishouden.setPartner(null);
        return huishouden;
    }

    private Huishouden createHuishoudenZonderPartnerZonderKinderen() {
        Huishouden huishouden = createHuishouden();
        huishouden.setPartner(null);
        huishouden.setKinderen(new ArrayList<>());
        return huishouden;
    }

    private Huishouden createHuishoudenMetPartnerZonderKinderen() {
        Huishouden huishouden = createHuishouden();
        huishouden.setKinderen(new ArrayList<>());
        return huishouden;
    }

    private Huishouden createHuishoudenMetKinderenOpAnderAdres() {
        Huishouden huishouden = createHuishouden();
        huishouden.setPartner(null);
        huishouden.getKinderen().forEach(kind -> kind.getAdres().setPostcodeHuisnummer(UUID.randomUUID().toString()));
        return huishouden;
    }

    /**
     * @return huishouden met partner, een kind > 18 en een kind < 18
     */
    private Huishouden createHuishouden() {
        Huishouden huishouden = Huishouden.builder()
                .hoofdbewoner(Persoon.builder()
                        .naam("Kees Jansen")
                        .geboortedatum(LocalDate.now().minusYears(45))
                        .adres(Adres.builder()
                                .postcodeHuisnummer("7335JE 42")
                                .build())
                        .build())
                .partner(Persoon.builder()
                        .naam("Janita Pietersen - Jansen")
                        .geboortedatum(LocalDate.now().minusYears(42))
                        .adres(Adres.builder()
                                .postcodeHuisnummer("7335JE 42")
                                .build())
                        .build())
                .kinderen(Arrays.asList(
                        Persoon.builder()
                                .naam("Floortje Jansen")
                                .geboortedatum(LocalDate.now().minusYears(19))
                                .adres(Adres.builder()
                                        .postcodeHuisnummer("7335JE 42")
                                        .build())
                                .build(),
                        Persoon.builder()
                                .naam("Jan Jansen")
                                .geboortedatum(LocalDate.now().minusYears(15))
                                .adres(Adres.builder()
                                        .postcodeHuisnummer("7335JE 42")
                                        .build())
                                .build()
                ))
                .build();
        return huishouden;
    }
}