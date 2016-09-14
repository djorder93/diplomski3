/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Djox
 */
@Embeddable
public class IntervencijaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zub")
    private int zub;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usluga")
    private int usluga;

    public IntervencijaPK() {
    }

    public IntervencijaPK(Date datum, int zub, int usluga) {
        this.datum = datum;
        this.zub = zub;
        this.usluga = usluga;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getZub() {
        return zub;
    }

    public void setZub(int zub) {
        this.zub = zub;
    }

    public int getUsluga() {
        return usluga;
    }

    public void setUsluga(int usluga) {
        this.usluga = usluga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datum != null ? datum.hashCode() : 0);
        hash += (int) zub;
        hash += (int) usluga;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntervencijaPK)) {
            return false;
        }
        IntervencijaPK other = (IntervencijaPK) object;
        if ((this.datum == null && other.datum != null) || (this.datum != null && !this.datum.equals(other.datum))) {
            return false;
        }
        if (this.zub != other.zub) {
            return false;
        }
        if (this.usluga != other.usluga) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.IntervencijaPK[ datum=" + datum + ", zub=" + zub + ", usluga=" + usluga + " ]";
    }
    
}
