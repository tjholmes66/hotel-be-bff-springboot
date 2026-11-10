package org.tomholmes.opensource.hotel.bff.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.hotel.bff.model.SeasonEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * These tests run against the live database and rely on the seeded data:
 * 8 seasons (ids 1-8), 2026 Winter through 2027 Fall.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SeasonRepositoryIntegrationTest
{
    private static final Long SEASON_ID = 1L;
    private static final String SEASON_CODE = "2026_WINTER";
    private static final String SEASON_NAME = "2026 Winter";

    @Autowired
    private SeasonRepository seasonRepository;

    @Test
    void findSeasonEntityById_returnsSeededSeason()
    {
        SeasonEntity seasonEntity = seasonRepository.findSeasonEntityById(SEASON_ID);

        assertThat(seasonEntity).isNotNull();
        assertThat(seasonEntity.getId()).isEqualTo(SEASON_ID);
        assertThat(seasonEntity.getSeasonCode()).isEqualTo(SEASON_CODE);
    }

    @Test
    void findAll_returnsSeededSeasons()
    {
        List<SeasonEntity> seasonEntities = seasonRepository.findAll();

        assertThat(seasonEntities).hasSizeGreaterThanOrEqualTo(8);
        assertThat(seasonEntities).extracting(SeasonEntity::getSeasonCode)
                .contains("2026_WINTER", "2026_SPRING", "2026_SUMMER", "2026_FALL",
                        "2027_WINTER", "2027_SPRING", "2027_SUMMER", "2027_FALL");
    }

    @Test
    void findById_returnsSeededSeason()
    {
        assertThat(seasonRepository.findById(1L))
                .hasValueSatisfying(seasonEntity -> assertThat(seasonEntity.getSeasonCode()).isEqualTo("2026_WINTER"));
        assertThat(seasonRepository.findById(8L))
                .hasValueSatisfying(seasonEntity -> assertThat(seasonEntity.getSeasonCode()).isEqualTo("2027_FALL"));
    }

    @Test
    void findById_returnsEmptyForUnknownId()
    {
        assertThat(seasonRepository.findById(999L)).isEmpty();
    }

    @Test
    void findBySeasonCode_returnsMatchingSeason()
    {
        assertThat(seasonRepository.findBySeasonCode("2026_SUMMER"))
                .hasValueSatisfying(seasonEntity -> assertThat(seasonEntity.getId()).isEqualTo(3L));
        assertThat(seasonRepository.findBySeasonCode("NONEXISTENT")).isEmpty();
    }

    @Test
    void findBySeasonNameContainingIgnoreCase_matchesCaseInsensitively()
    {
        List<SeasonEntity> seasonEntities = seasonRepository.findBySeasonNameContainingIgnoreCase("WINTER");

        assertThat(seasonEntities).extracting(SeasonEntity::getSeasonName)
                .contains("2026 Winter", "2027 Winter");
    }

    @Test
    void save_findById_roundTripsNewSeason()
    {
        SeasonEntity seasonEntity = new SeasonEntity();
        seasonEntity.setSeasonCode("2028_WINTER");
        seasonEntity.setSeasonName("2028 Winter");
        seasonEntity.setSeasonDescription("2028 Winter Season");
        seasonEntity.setStartDate(LocalDate.of(2028, 1, 1));
        seasonEntity.setEndDate(LocalDate.of(2028, 3, 31));

        SeasonEntity saved = seasonRepository.saveAndFlush(seasonEntity);

        assertThat(saved.getId()).isNotNull();
        assertThat(seasonRepository.findById(saved.getId()))
                .hasValueSatisfying(persisted -> {
                    assertThat(persisted.getSeasonCode()).isEqualTo("2028_WINTER");
                    assertThat(persisted.getSeasonName()).isEqualTo("2028 Winter");
                    assertThat(persisted.getStartDate()).isEqualTo(LocalDate.of(2028, 1, 1));
                    assertThat(persisted.getEndDate()).isEqualTo(LocalDate.of(2028, 3, 31));
                });
    }

    @Test
    void delete_removesSeason()
    {
        SeasonEntity seasonEntity = new SeasonEntity();
        seasonEntity.setSeasonCode("2029_WINTER");
        seasonEntity.setSeasonName("2029 Winter");
        seasonEntity.setSeasonDescription("2029 Winter Season");
        seasonEntity.setStartDate(LocalDate.of(2029, 1, 1));
        seasonEntity.setEndDate(LocalDate.of(2029, 3, 31));
        SeasonEntity saved = seasonRepository.saveAndFlush(seasonEntity);

        seasonRepository.delete(saved);
        seasonRepository.flush();

        assertThat(seasonRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void equalsAndHashCode_matchForSamePersistedSeason()
    {
        SeasonEntity first = seasonRepository.findById(SEASON_ID).orElseThrow();
        SeasonEntity second = seasonRepository.findById(SEASON_ID).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new SeasonEntity());
    }

    @Test
    void toString_includesFields()
    {
        SeasonEntity seasonEntity = seasonRepository.findById(SEASON_ID).orElseThrow();

        assertThat(seasonEntity.toString())
                .contains("SeasonEntity{")
                .contains("id=" + SEASON_ID)
                .contains("seasonCode='" + SEASON_CODE + "'")
                .contains("seasonName='" + SEASON_NAME + "'");
    }
}
