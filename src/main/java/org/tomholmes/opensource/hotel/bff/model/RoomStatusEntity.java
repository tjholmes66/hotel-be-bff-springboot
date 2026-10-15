package org.tomholmes.opensource.hotel.bff.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "room_status")
@DynamicInsert
public class RoomStatusEntity implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_status_code", length = 45, nullable = false)
    private String roomStatusCode;

    @Column(name = "room_status_name", length = 45, nullable = false)
    private String roomStatusName;

    @Column(name = "room_status_description", length = 45, nullable = false)
    private String roomStatusDescription;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRoomStatusCode()
    {
        return roomStatusCode;
    }

    public void setRoomStatusCode(String roomStatusCode)
    {
        this.roomStatusCode = roomStatusCode;
    }

    public String getRoomStatusName()
    {
        return roomStatusName;
    }

    public void setRoomStatusName(String roomStatusName)
    {
        this.roomStatusName = roomStatusName;
    }

    public String getRoomStatusDescription()
    {
        return roomStatusDescription;
    }

    public void setRoomStatusDescription(String roomStatusDescription)
    {
        this.roomStatusDescription = roomStatusDescription;
    }

    public Long getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate)
    {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        RoomStatusEntity that = (RoomStatusEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(roomStatusCode, that.roomStatusCode) && Objects.equals(roomStatusName, that.roomStatusName) && Objects.equals(roomStatusDescription,
        that.roomStatusDescription) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(
        updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, roomStatusCode, roomStatusName, roomStatusDescription, createdBy, createdDate, updatedBy, updatedDate);
    }

    @Override
    public String toString()
    {
        return "RoomStatusEntity{" + "id=" + id + ", roomStatusCode='" + roomStatusCode + '\'' + ", roomStatusName='" + roomStatusName + '\'' + ", roomStatusDescription='" + roomStatusDescription + '\'' + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + '}';
    }
}
