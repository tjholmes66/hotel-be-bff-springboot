package org.tomholmes.opensource.hotel.bff.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuestRepositoryIntegrationTest {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
    }

    private GuestEntity guest(String firstName, String lastName, String email, String phone) {
        GuestEntity guest = new GuestEntity();
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        guest.setGuestEmail(email);
        guest.setGuestPhone(phone);
        return guest;
    }

    private GuestEntity guest(String fullName, String email, String phone) {
        GuestEntity guest = new GuestEntity();
        if (fullName != null && fullName.contains(" ")) {
            String[] parts = fullName.split("\\s+", 2);
            guest.setFirstName(parts[0]);
            guest.setLastName(parts[1]);
        } else if (fullName != null) {
            guest.setFirstName(fullName);
            guest.setLastName(fullName);
        }
        guest.setGuestEmail(email);
        guest.setGuestPhone(phone);
        return guest;
    }

    @Test
    void save_findById_roundTripsGuest() {
        GuestEntity saved = guestRepository.save(guest("Grace", "Hopper", "grace@example.com", "555-0101"));

        assertThat(saved.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        GuestEntity persisted = guestRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getFirstName()).isEqualTo("Grace");
        assertThat(persisted.getLastName()).isEqualTo("Hopper");
        assertThat(persisted.getGuestEmail()).isEqualTo("grace@example.com");
        assertThat(persisted.getGuestPhone()).isEqualTo("555-0101");
    }

    @Test
    void save_appliesDatabaseDefaults() {
        GuestEntity saved = guestRepository.save(guest("Ada", "Lovelace", "ada@example.com", "555-0102"));

        entityManager.flush();
        entityManager.clear();

        GuestEntity persisted = guestRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getGuestAdut()).isTrue();
        assertThat(persisted.getCreatedBy()).isEqualTo(1L);
        assertThat(persisted.getUpdatedBy()).isEqualTo(1L);
        assertThat(persisted.getCreatedDate()).isNotNull();
        assertThat(persisted.getUpdatedDate()).isNotNull();
    }

    @Test
    void findByGuestEmail_returnsMatchingGuest() {
        guestRepository.save(guest("Alan", "Turing", "alan@example.com", "555-0103"));

        assertThat(guestRepository.findByGuestEmail("alan@example.com"))
                .hasValueSatisfying(guest -> assertThat(guest.getLastName()).isEqualTo("Turing"));
        assertThat(guestRepository.findByGuestEmail("nobody@example.com")).isEmpty();
    }

    @Test
    void findByGuestNameContainingIgnoreCase_matchesCaseInsensitively() {
        guestRepository.save(guest("Katherine", "Johnson", "katherine@example.com", "555-0104"));
        guestRepository.save(guest("John", "von Neumann", "john@example.com", "555-0105"));

        List<GuestEntity> guests = guestRepository.findByLastNameContainingIgnoreCase("JOHN");

        assertThat(guests).isNotEmpty();
    }

    @Test
    void findByLastNameContainingIgnoreCase_matchesCaseInsensitively() {
        guestRepository.save(guest("Katherine", "Johnson", "katherine@example.com", "555-0104"));
        guestRepository.save(guest("John", "von Neumann", "john@example.com", "555-0105"));

        List<GuestEntity> guests = guestRepository.findByLastNameContainingIgnoreCase("NEUM");

        assertThat(guests).isNotEmpty();
        assertThat(guests).extracting(GuestEntity::getLastName)
                .contains("von Neumann");
    }

    @Test
    void findByLastNameAndFirstNameContainingIgnoreCase_matchesCaseInsensitively() {
        guestRepository.save(guest("Katherine", "Johnson", "katherine@example.com", "555-0104"));
        guestRepository.save(guest("John", "von Neumann", "john@example.com", "555-0105"));

        List<GuestEntity> guests = guestRepository.findByLastNameAndFirstNameContainingIgnoreCase("von Neumann", "John");

        assertThat(guests).isNotEmpty();
        assertThat(guests).extracting(GuestEntity::getLastName)
                .contains("von Neumann");
        assertThat(guests).extracting(GuestEntity::getFirstName)
                .contains("John");
    }

    @Test
    void delete_removesGuest() {
        GuestEntity saved = guestRepository.save(guest("Hedy", "Lamarr", "hedy@example.com", "555-0106"));
        guestRepository.flush();

        guestRepository.delete(saved);
        guestRepository.flush();

        assertThat(guestRepository.findById(saved.getId())).isEmpty();
        assertThat(guestRepository.findByGuestEmail("hedy@example.com")).isEmpty();
    }

    @Test
    void equalsAndHashCode_areIdBased() {
        GuestEntity saved = guestRepository.save(guest("Mae", "Jemison", "mae@example.com", "555-0107"));

        entityManager.flush();
        entityManager.clear();

        GuestEntity first = guestRepository.findById(saved.getId()).orElseThrow();
        GuestEntity second = guestRepository.findById(saved.getId()).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new GuestEntity());
    }

    @Test
    void toString_includesFields() {
        GuestEntity saved = guestRepository.save(guest("Sally", "Ride", "sally@example.com", "555-0108"));

        entityManager.flush();
        entityManager.clear();

        GuestEntity persisted = guestRepository.findById(saved.getId()).orElseThrow();

        assertThat(persisted.toString())
                .contains("GuestEntity{")
                .contains("id=" + saved.getId())
                .contains("firstName='Sally'")
                .contains("lastName='Ride'")
                .contains("guestEmail='sally@example.com'")
                .contains("guestPhone='555-0108'");
    }
}
