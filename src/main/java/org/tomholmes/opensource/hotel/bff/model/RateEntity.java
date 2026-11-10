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
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "rates")
@DynamicInsert
public class RateEntity implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private SeasonEntity season;

    @Column(name = "rate", precision = 10, scale = 2)
    private BigDecimal amount;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public RoomEntity getRoom()
    {
        return room;
    }

    public void setRoom(RoomEntity room)
    {
        this.room = room;
    }

    public SeasonEntity getSeason()
    {
        return season;
    }

    public void setSeason(SeasonEntity season)
    {
        this.season = season;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        RateEntity that = (RateEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(room, that.room) && Objects.equals(season, that.season) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, room, season, amount);
    }

    @Override
    public String toString()
    {
        return "RateEntity{" + "id=" + id + ", room=" + room + ", season=" + season + ", amount=" + amount + '}';
    }
}
