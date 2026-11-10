package org.tomholmes.opensource.hotel.bff.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.tomholmes.opensource.hotel.bff.model.RateEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.model.SeasonEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * These tests run against the live database and rely on the seeded data:
 * 16 rates (ids 1-16) covering rooms 1-2 and seasons 1-8.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RateRepositoryIntegrationTest
{
    private static final Long RATE_ID = 1L;
    private static final Long ROOM_ID = 1L;
    private static final Long OTHER_ROOM_ID = 2L;
    private static final Long ROOM_WITHOUT_RATES_ID = 3L;
    private static final Long SEASON_ID = 1L;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EntityManager entityManager;

    private RoomEntity room(Long roomId)
    {
        return roomRepository.findById(roomId).orElseThrow();
    }

    private SeasonEntity season(Long seasonId)
    {
        return seasonRepository.findById(seasonId).orElseThrow();
    }

    private RateEntity rate(RoomEntity roomEntity, SeasonEntity seasonEntity, String amount)
    {
        RateEntity rateEntity = new RateEntity();
        rateEntity.setRoom(roomEntity);
        rateEntity.setSeason(seasonEntity);
        rateEntity.setAmount(new BigDecimal(amount));
        return rateEntity;
    }

    @Test
    void findById_returnsSeededRate()
    {
        RateEntity rateEntity = rateRepository.findById(RATE_ID).orElseThrow();

        assertThat(rateEntity.getRoom().getId()).isEqualTo(ROOM_ID);
        assertThat(rateEntity.getSeason().getId()).isEqualTo(SEASON_ID);
        assertThat(rateEntity.getAmount()).isEqualByComparingTo("100.00");
    }

    @Test
    void findById_returnsEmptyForUnknownId()
    {
        assertThat(rateRepository.findById(999L)).isEmpty();
    }

    @Test
    void findAll_returnsSeededRates()
    {
        List<RateEntity> rateEntities = rateRepository.findAll();

        assertThat(rateEntities).hasSizeGreaterThanOrEqualTo(16);
        assertThat(rateEntities).extracting(RateEntity::getId).contains(RATE_ID, 16L);
    }

    @Test
    void findByRoomId_returnsRatesForRoom()
    {
        List<RateEntity> rateEntities = rateRepository.findByRoomId(ROOM_ID);

        assertThat(rateEntities).hasSize(8);
        assertThat(rateEntities).extracting(rateEntity -> rateEntity.getSeason().getId())
                .containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
    }

    @Test
    void findByRoomId_returnsEmptyForRoomWithoutRates()
    {
        assertThat(rateRepository.findByRoomId(ROOM_WITHOUT_RATES_ID)).isEmpty();
    }

    @Test
    void findBySeasonId_returnsRatesForSeason()
    {
        List<RateEntity> rateEntities = rateRepository.findBySeasonId(SEASON_ID);

        assertThat(rateEntities).hasSize(2);
        assertThat(rateEntities).extracting(rateEntity -> rateEntity.getRoom().getId())
                .containsExactlyInAnyOrder(ROOM_ID, OTHER_ROOM_ID);
        assertThat(rateEntities).allSatisfy(rateEntity -> assertThat(rateEntity.getAmount()).isEqualByComparingTo("100.00"));
    }

    @Test
    void findByRoom_IdAndSeason_Id_returnsMatchingRate()
    {
        assertThat(rateRepository.findByRoom_IdAndSeason_Id(ROOM_ID, SEASON_ID))
                .hasValueSatisfying(rateEntity -> assertThat(rateEntity.getId()).isEqualTo(RATE_ID));
    }

    @Test
    void findByRoom_IdAndSeason_Id_returnsEmptyWhenNothingMatches()
    {
        assertThat(rateRepository.findByRoom_IdAndSeason_Id(ROOM_WITHOUT_RATES_ID, SEASON_ID)).isEmpty();
    }

    @Test
    void findRateEntityById_returnsMatchingRate()
    {
        RateEntity rateEntity = rateRepository.findRateEntityById(16L);

        assertThat(rateEntity).isNotNull();
        assertThat(rateEntity.getRoom().getId()).isEqualTo(OTHER_ROOM_ID);
        assertThat(rateEntity.getSeason().getId()).isEqualTo(8L);
        assertThat(rateEntity.getAmount()).isEqualByComparingTo("160.00");
    }

    @Test
    void save_persistsNewRateForExistingRoomAndSeason()
    {
        RateEntity saved = rateRepository.saveAndFlush(rate(room(ROOM_WITHOUT_RATES_ID), season(SEASON_ID), "99.99"));

        assertThat(saved.getId()).isNotNull();

        entityManager.clear();

        RateEntity persisted = rateRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getRoom().getId()).isEqualTo(ROOM_WITHOUT_RATES_ID);
        assertThat(persisted.getSeason().getId()).isEqualTo(SEASON_ID);
        assertThat(persisted.getAmount()).isEqualByComparingTo("99.99");
    }

    @Test
    void uniqueConstraint_rejectsDuplicateRoomAndSeason()
    {
        RateEntity duplicate = rate(room(ROOM_ID), season(SEASON_ID), "100.00");

        assertThatThrownBy(() -> rateRepository.saveAndFlush(duplicate))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void delete_removesRate()
    {
        RateEntity rateEntity = rateRepository.findById(16L).orElseThrow();

        rateRepository.delete(rateEntity);
        rateRepository.flush();

        assertThat(rateRepository.findById(16L)).isEmpty();
    }

    @Test
    void equalsAndHashCode_matchForSamePersistedRate()
    {
        RateEntity first = rateRepository.findById(RATE_ID).orElseThrow();
        RateEntity second = rateRepository.findById(RATE_ID).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new RateEntity());
    }

    @Test
    void toString_includesFields()
    {
        RateEntity rateEntity = rateRepository.findById(RATE_ID).orElseThrow();

        assertThat(rateEntity.toString())
                .contains("RateEntity{")
                .contains("id=" + RATE_ID)
                .contains("amount=100.00");
    }
}
