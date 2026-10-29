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
import java.time.LocalDate;
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
    @JoinColumn(name = "primary_guest_id", nullable = false)
    private GuestEntity primaryGuest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_status_id", nullable = false)
    private ReservationStatusEntity reservationStatus;

    @Column(name = "checkin_date", nullable = false)
    private LocalDate checkinDate;

    @Column(name = "checkout_date", nullable = false)
    private LocalDate checkoutDate;

    @Column(name = "number_adults")
    private Integer numberAdults;

    @Column(name = "number_children")
    private Integer numberChildren;

    @Column(name = "reservation_number")
    private String reservationNumber;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "actual_checkin")
    private LocalDateTime actualCheckin;

    @Column(name = "actual_checkin_by")
    private Long actualCheckinBy;

    @Column(name = "actual_checkout")
    private LocalDateTime actualCheckout;

    @Column(name = "actual_checkout_by")
    private Long actualCheckoutBy;

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

    public LocalDate getCheckinDate()
    {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate)
    {
        this.checkinDate = checkinDate;
    }

    public LocalDate getCheckoutDate()
    {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate)
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

    public String getReservationNumber()
    {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber)
    {
        this.reservationNumber = reservationNumber;
    }

    public LocalDateTime getActualCheckin()
    {
        return actualCheckin;
    }

    public void setActualCheckin(LocalDateTime actualCheckin)
    {
        this.actualCheckin = actualCheckin;
    }

    public LocalDateTime getActualCheckout()
    {
        return actualCheckout;
    }

    public void setActualCheckout(LocalDateTime actualCheckout)
    {
        this.actualCheckout = actualCheckout;
    }

    public ReservationStatusEntity getReservationStatus()
    {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatusEntity reservationStatus)
    {
        this.reservationStatus = reservationStatus;
    }

    public Long getActualCheckinBy()
    {
        return actualCheckinBy;
    }

    public void setActualCheckinBy(Long actualCheckinBy)
    {
        this.actualCheckinBy = actualCheckinBy;
    }

    public Long getActualCheckoutBy()
    {
        return actualCheckoutBy;
    }

    public void setActualCheckoutBy(Long actualCheckoutBy)
    {
        this.actualCheckoutBy = actualCheckoutBy;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        ReservationEntity that = (ReservationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(primaryGuest, that.primaryGuest) && Objects.equals(room, that.room) && Objects.equals(reservationStatus,
        that.reservationStatus) && Objects.equals(checkinDate, that.checkinDate) && Objects.equals(checkoutDate, that.checkoutDate) && Objects.equals(numberAdults,
        that.numberAdults) && Objects.equals(numberChildren, that.numberChildren) && Objects.equals(reservationNumber, that.reservationNumber) && Objects.equals(createdDate,
        that.createdDate) && Objects.equals(updatedDate, that.updatedDate) && Objects.equals(actualCheckin, that.actualCheckin) && Objects.equals(actualCheckinBy,
        that.actualCheckinBy) && Objects.equals(actualCheckout, that.actualCheckout) && Objects.equals(actualCheckoutBy, that.actualCheckoutBy);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, primaryGuest, room, reservationStatus, checkinDate, checkoutDate, numberAdults, numberChildren, reservationNumber, createdDate, updatedDate, actualCheckin,
        actualCheckinBy, actualCheckout, actualCheckoutBy);
    }

    @Override
    public String toString()
    {
        return "ReservationEntity{" + "id=" + id + ", primaryGuest=" + primaryGuest + ", room=" + room + ", reservationStatus=" + reservationStatus + ", checkinDate=" + checkinDate + ", checkoutDate=" + checkoutDate + ", numberAdults=" + numberAdults + ", numberChildren=" + numberChildren + ", reservationNumber='" + reservationNumber + '\'' + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", actualCheckin=" + actualCheckin + ", actualCheckinBy=" + actualCheckinBy + ", actualCheckout=" + actualCheckout + ", actualCheckoutBy=" + actualCheckoutBy + '}';
    }
}
