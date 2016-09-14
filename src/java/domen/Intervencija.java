/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Djox
 */
@Entity
@Table(name = "intervencija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intervencija.findAll", query = "SELECT i FROM Intervencija i"),
    @NamedQuery(name = "Intervencija.findByDatum", query = "SELECT i FROM Intervencija i WHERE i.intervencijaPK.datum = :datum"),
    @NamedQuery(name = "Intervencija.findByZub", query = "SELECT i FROM Intervencija i WHERE i.intervencijaPK.zub = :zub"),
    @NamedQuery(name = "Intervencija.findByUsluga", query = "SELECT i FROM Intervencija i WHERE i.intervencijaPK.usluga = :usluga")})
public class Intervencija implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntervencijaPK intervencijaPK;
    @JoinColumn(name = "datum", referencedColumnName = "datum", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pregled pregled;
    @JoinColumn(name = "zub", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Zub zub1;
    @JoinColumn(name = "usluga", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usluga usluga1;

    public Intervencija() {
    }

    public Intervencija(IntervencijaPK intervencijaPK) {
        this.intervencijaPK = intervencijaPK;
    }

    public Intervencija(Date datum, int zub, int usluga) {
        this.intervencijaPK = new IntervencijaPK(datum, zub, usluga);
    }

    public IntervencijaPK getIntervencijaPK() {
        return intervencijaPK;
    }

    public void setIntervencijaPK(IntervencijaPK intervencijaPK) {
        this.intervencijaPK = intervencijaPK;
    }

    public Pregled getPregled() {
        return pregled;
    }

    public void setPregled(Pregled pregled) {
        this.pregled = pregled;
    }

    public Zub getZub1() {
        return zub1;
    }

    public void setZub1(Zub zub1) {
        this.zub1 = zub1;
    }

    public Usluga getUsluga1() {
        return usluga1;
    }

    public void setUsluga1(Usluga usluga1) {
        this.usluga1 = usluga1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intervencijaPK != null ? intervencijaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intervencija)) {
            return false;
        }
        Intervencija other = (Intervencija) object;
        if ((this.intervencijaPK == null && other.intervencijaPK != null) || (this.intervencijaPK != null && !this.intervencijaPK.equals(other.intervencijaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return zub1.toString()+"  "+usluga1.toString();
    }
    
}
