package org.tomholmes.opensource.hotel.bff.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bed_types")
public class BedTypeEntity implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bed_type", length = 45, nullable = false)
    private String bedType;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getBedType()
    {
        return bedType;
    }

    public void setBedType(String bedType)
    {
        this.bedType = bedType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        BedTypeEntity that = (BedTypeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(bedType, that.bedType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, bedType);
    }

    @Override
    public String toString()
    {
        return "BedTypeEntity{" + "id=" + id + ", bedType='" + bedType + '\'' + '}';
    }
}
