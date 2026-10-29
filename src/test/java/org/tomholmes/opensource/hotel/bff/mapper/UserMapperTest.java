package org.tomholmes.opensource.hotel.bff.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.tomholmes.opensource.hotel.bff.dto.UserRecord;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest
{
    private UserMapper userMapper;

    @BeforeEach
    void setUp()
    {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    private UserEntity userEntity(long id, String username)
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEnabled(true);
        userEntity.setUsername(username);
        userEntity.setFirstName("Test");
        userEntity.setLastName("User");
        userEntity.setCreatedBy(10L);
        userEntity.setCreatedDate(LocalDateTime.of(2026, 1, 2, 3, 4, 5));
        userEntity.setUpdatedBy(20L);
        userEntity.setUpdatedDate(LocalDateTime.of(2026, 2, 3, 4, 5, 6));
        userEntity.setEmail(username + "@example.com");
        userEntity.setCellPhone("555-0101");
        userEntity.setKeycloakId("keycloak-" + id);
        return userEntity;
    }

    @Test
    void toRecord_mapsAllFields()
    {
        UserEntity userEntity = userEntity(1L, "test");

        UserRecord userRecord = userMapper.toRecord(userEntity);

        assertThat(userRecord).isNotNull();
        assertThat(userRecord.id()).isEqualTo(1L);
        assertThat(userRecord.enabled()).isTrue();
        assertThat(userRecord.username()).isEqualTo("test");
        assertThat(userRecord.firstName()).isEqualTo("Test");
        assertThat(userRecord.lastName()).isEqualTo("User");
        assertThat(userRecord.createdBy()).isEqualTo(10L);
        assertThat(userRecord.createdDate()).isEqualTo(LocalDateTime.of(2026, 1, 2, 3, 4, 5));
        assertThat(userRecord.updatedBy()).isEqualTo(20L);
        assertThat(userRecord.updatedDate()).isEqualTo(LocalDateTime.of(2026, 2, 3, 4, 5, 6));
        assertThat(userRecord.email()).isEqualTo("test@example.com");
        assertThat(userRecord.cellPhone()).isEqualTo("555-0101");
        assertThat(userRecord.keycloakId()).isEqualTo("keycloak-1");
    }

    @Test
    void toRecord_withNull_returnsNull()
    {
        assertThat(userMapper.toRecord((UserEntity) null)).isNull();
    }

    @Test
    void toRecordList_mapsAllEntitiesPreservingOrder()
    {
        UserEntity first = userEntity(1L, "first");
        UserEntity second = userEntity(2L, "second");

        List<UserRecord> userRecords = userMapper.toRecordList(List.of(first, second));

        assertThat(userRecords).hasSize(2);
        assertThat(userRecords).extracting(UserRecord::id).containsExactly(1L, 2L);
        assertThat(userRecords).extracting(UserRecord::username).containsExactly("first", "second");
    }

    @Test
    void toRecordList_withEmptyList_returnsEmptyList()
    {
        assertThat(userMapper.toRecordList(List.of())).isEmpty();
    }

    @Test
    void toRecordList_withNull_returnsNull()
    {
        assertThat(userMapper.toRecordList(null)).isNull();
    }
}
