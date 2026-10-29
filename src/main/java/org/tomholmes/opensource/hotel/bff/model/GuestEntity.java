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
@Table(name = "guests")
@DynamicInsert
public class GuestEntity implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "guest_email", length = 45, nullable = false)
    private String guestEmail;

    @Column(name = "guest_phone", length = 45, nullable = false)
    private String guestPhone;

    @Column(name = "guest_adut")
    private Boolean guestAdut;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Long getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getGuestEmail()
    {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail)
    {
        this.guestEmail = guestEmail;
    }

    public String getGuestPhone()
    {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone)
    {
        this.guestPhone = guestPhone;
    }

    public Boolean getGuestAdut()
    {
        return guestAdut;
    }

    public void setGuestAdut(Boolean guestAdut)
    {
        this.guestAdut = guestAdut;
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
        GuestEntity that = (GuestEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(guestEmail, that.guestEmail) && Objects.equals(
        guestPhone, that.guestPhone) && Objects.equals(guestAdut, that.guestAdut) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate) && Objects.equals(
        updatedBy, that.updatedBy) && Objects.equals(updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, firstName, lastName, guestEmail, guestPhone, guestAdut, createdBy, createdDate, updatedBy, updatedDate);
    }

    @Override
    public String toString()
    {
        return "GuestEntity{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", guestEmail='" + guestEmail + '\'' + ", guestPhone='" + guestPhone + '\'' + ", guestAdut=" + guestAdut + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + '}';
    }
}
