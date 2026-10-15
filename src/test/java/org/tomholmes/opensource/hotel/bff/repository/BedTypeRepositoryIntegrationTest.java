package org.tomholmes.opensource.hotel.bff.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BedTypeRepositoryIntegrationTest
{

    @Autowired
    private BedTypeRepository bedTypeRepository;

    @Test
    void findAll_returnsSeededBedTypes() {
        List<BedTypeEntity> bedTypeEntities = bedTypeRepository.findAll();

        assertThat(bedTypeEntities).hasSizeGreaterThanOrEqualTo(9);
        assertThat(bedTypeEntities).extracting(BedTypeEntity::getBedType)
                .contains("Twin/Single", "Twin XL", "Single King", "California King");
    }

    @Test
    void findById_returnsSeededBedType() {
        assertThat(bedTypeRepository.findById(1L))
                .hasValueSatisfying(bedTypeEntity -> assertThat(bedTypeEntity.getBedType()).isEqualTo("Twin/Single"));
        assertThat(bedTypeRepository.findById(9L))
                .hasValueSatisfying(bedTypeEntity -> assertThat(bedTypeEntity.getBedType()).isEqualTo("California King"));
    }

    @Test
    void findByBedType_returnsMatchingBedType() {
        assertThat(bedTypeRepository.findByBedType("Single King"))
                .hasValueSatisfying(bedTypeEntity -> assertThat(bedTypeEntity.getId()).isEqualTo(7L));
        assertThat(bedTypeRepository.findByBedType("Nonexistent")).isEmpty();
    }

    @Test
    void findByBedTypeContainingIgnoreCase_matchesCaseInsensitively() {
        List<BedTypeEntity> bedTypeEntities = bedTypeRepository.findByBedTypeContainingIgnoreCase("KING");

        assertThat(bedTypeEntities).extracting(BedTypeEntity::getBedType)
                .contains("Single King", "Double King");
    }

    @Test
    void save_findById_roundTripsNewBedType() {
        BedTypeEntity bedTypeEntity = new BedTypeEntity();
        bedTypeEntity.setBedType("Bunk Bed");

        BedTypeEntity saved = bedTypeRepository.save(bedTypeEntity);

        assertThat(saved.getId()).isNotNull();
        assertThat(bedTypeRepository.findById(saved.getId()))
                .hasValueSatisfying(persisted -> assertThat(persisted.getBedType()).isEqualTo("Bunk Bed"));
    }

    @Test
    void delete_removesBedType() {
        BedTypeEntity bedTypeEntity = new BedTypeEntity();
        bedTypeEntity.setBedType("Sofa Bed");
        BedTypeEntity saved = bedTypeRepository.save(bedTypeEntity);
        bedTypeRepository.flush();

        bedTypeRepository.delete(saved);
        bedTypeRepository.flush();

        assertThat(bedTypeRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void equalsAndHashCode_areIdBased() {
        BedTypeEntity first = bedTypeRepository.findById(1L).orElseThrow();
        BedTypeEntity second = bedTypeRepository.findById(1L).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new BedTypeEntity());
    }

}
