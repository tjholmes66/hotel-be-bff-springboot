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
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reservations", uniqueConstraints = {
@UniqueConstraint(name = "uq_guest_room_date", columnNames = { "primary_guest", "room_id", "checkin_date" }) })
@DynamicInsert
public class ReservationEntity implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_guest", nullable = false)
    private GuestEntity primaryGuest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @Column(name = "checkin_date", nullable = false)
    private LocalDateTime checkinDate;

    @Column(name = "checkout_date", nullable = false)
    private LocalDateTime checkoutDate;

    @Column(name = "number_adults")
    private Integer numberAdults;

    @Column(name = "number_children")
    private Integer numberChildren;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

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

    public GuestEntity getPrimaryGuest()
    {
        return primaryGuest;
    }

    public void setPrimaryGuest(GuestEntity primaryGuest)
    {
        this.primaryGuest = primaryGuest;
    }

    public RoomEntity getRoom()
    {
        return room;
    }

    public void setRoom(RoomEntity room)
    {
        this.room = room;
    }

    public LocalDateTime getCheckinDate()
    {
        return checkinDate;
    }

    public void setCheckinDate(LocalDateTime checkinDate)
    {
        this.checkinDate = checkinDate;
    }

    public LocalDateTime getCheckoutDate()
    {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDateTime checkoutDate)
    {
        this.checkoutDate = checkoutDate;
    }

    public Integer getNumberAdults()
    {
        return numberAdults;
    }

    public void setNumberAdults(Integer numberAdults)
    {
        this.numberAdults = numberAdults;
    }

    public Integer getNumberChildren()
    {
        return numberChildren;
    }

    public void setNumberChildren(Integer numberChildren)
    {
        this.numberChildren = numberChildren;
    }

    public LocalDateTime getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate)
    {
        this.createdDate = createdDate;
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
        ReservationEntity that = (ReservationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(primaryGuest, that.primaryGuest) && Objects.equals(room, that.room) && Objects.equals(checkinDate, that.checkinDate) && Objects.equals(
        checkoutDate, that.checkoutDate) && Objects.equals(numberAdults, that.numberAdults) && Objects.equals(numberChildren, that.numberChildren) && Objects.equals(createdDate,
        that.createdDate) && Objects.equals(updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, primaryGuest, room, checkinDate, checkoutDate, numberAdults, numberChildren, createdDate, updatedDate);
    }

    @Override
    public String toString()
    {
        return "ReservationEntity{" + "id=" + id + ", primaryGuest=" + primaryGuest + ", room=" + room + ", checkinDate=" + checkinDate + ", checkoutDate=" + checkoutDate + ", numberAdults=" + numberAdults + ", numberChildren=" + numberChildren + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + '}';
    }
}
