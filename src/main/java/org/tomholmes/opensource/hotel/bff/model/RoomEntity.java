package org.tomholmes.opensource.hotel.bff.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "rooms")
@DynamicInsert
public class RoomEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Column(name = "room_name", length = 45)
    private String roomName;

    @Column(name = "room_description", columnDefinition = "text")
    private String roomDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_status", nullable = false)
    private RoomStatusEntity roomStatus;

    @Column(name = "occupants", nullable = false)
    private Integer occupants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bed_type", nullable = false)
    private BedTypeEntity bedType;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "udpated_date")
    private LocalDateTime udpatedDate;

    public LocalDateTime getCreatedDate()
    {
        return createdDate;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getRoomNumber()
    {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber)
    {
        this.roomNumber = roomNumber;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public String getRoomDescription()
    {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription)
    {
        this.roomDescription = roomDescription;
    }

    public RoomStatusEntity getRoomStatus()
    {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatusEntity roomStatus)
    {
        this.roomStatus = roomStatus;
    }

    public Integer getOccupants()
    {
        return occupants;
    }

    public void setOccupants(Integer occupants)
    {
        this.occupants = occupants;
    }

    public BedTypeEntity getBedType()
    {
        return bedType;
    }

    public void setBedType(BedTypeEntity bedType)
    {
        this.bedType = bedType;
    }

    public Long getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;
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

    public LocalDateTime getUdpatedDate()
    {
        return udpatedDate;
    }

    public void setUdpatedDate(LocalDateTime udpatedDate)
    {
        this.udpatedDate = udpatedDate;
    }

    public Integer getFloor()
    {
        return floor;
    }

    public void setFloor(Integer floor)
    {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        RoomEntity that = (RoomEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(floor, that.floor) && Objects.equals(roomNumber, that.roomNumber) && Objects.equals(roomName, that.roomName) && Objects.equals(
        roomDescription, that.roomDescription) && Objects.equals(roomStatus, that.roomStatus) && Objects.equals(occupants, that.occupants) && Objects.equals(bedType, that.bedType) && Objects.equals(
        createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(udpatedDate, that.udpatedDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, floor, roomNumber, roomName, roomDescription, roomStatus, occupants, bedType, createdBy, createdDate, updatedBy, udpatedDate);
    }

    @Override
    public String toString()
    {
        return "RoomEntity{" + "id=" + id + ", floor=" + floor + ", roomNumber=" + roomNumber + ", roomName='" + roomName + '\'' + ", roomDescription='" + roomDescription + '\'' + ", roomStatus=" + roomStatus + ", occupants=" + occupants + ", bedType=" + bedType + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", udpatedDate=" + udpatedDate + '}';
    }
}
