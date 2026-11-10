package org.tomholmes.opensource.hotel.bff.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "seasons")
@DynamicInsert
public class SeasonEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "season_code", length = 45, nullable = false)
    private String seasonCode;

    @Column(name = "season_name", length = 45, nullable = false)
    private String seasonName;

    @Column(name = "season_description", length = 45, nullable = false)
    private String seasonDescription;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSeasonCode()
    {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode)
    {
        this.seasonCode = seasonCode;
    }

    public String getSeasonName()
    {
        return seasonName;
    }

    public void setSeasonName(String seasonName)
    {
        this.seasonName = seasonName;
    }

    public String getSeasonDescription()
    {
        return seasonDescription;
    }

    public void setSeasonDescription(String seasonDescription)
    {
        this.seasonDescription = seasonDescription;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        SeasonEntity that = (SeasonEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(seasonCode, that.seasonCode) && Objects.equals(seasonName, that.seasonName) && Objects.equals(seasonDescription,
        that.seasonDescription) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, seasonCode, seasonName, seasonDescription, startDate, endDate);
    }

    @Override
    public String toString()
    {
        return "SeasonEntity{" + "id=" + id + ", seasonCode='" + seasonCode + '\'' + ", seasonName='" + seasonName + '\'' + ", seasonDescription='" + seasonDescription + '\'' + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }

}

