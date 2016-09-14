/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Djox
 */
@Entity
@Table(name = "zub")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zub.findAll", query = "SELECT z FROM Zub z"),
    @NamedQuery(name = "Zub.findById", query = "SELECT z FROM Zub z WHERE z.id = :id"),
    @NamedQuery(name = "Zub.findByOznaka", query = "SELECT z FROM Zub z WHERE z.oznaka = :oznaka"),
    @NamedQuery(name = "Zub.findByVilica", query = "SELECT z FROM Zub z WHERE z.vilica = :vilica"),
    @NamedQuery(name = "Zub.findByPozicija", query = "SELECT z FROM Zub z WHERE z.pozicija = :pozicija")})
public class Zub implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "oznaka")
    private Integer oznaka;
    @Size(max = 255)
    @Column(name = "vilica")
    private String vilica;
    @Size(max = 255)
    @Column(name = "pozicija")
    private String pozicija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zub1", fetch = FetchType.EAGER)
    private List<Intervencija> intervencijaList;

    public Zub() {
    }

    public Zub(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOznaka() {
        return oznaka;
    }

    public void setOznaka(Integer oznaka) {
        this.oznaka = oznaka;
    }

    public String getVilica() {
        return vilica;
    }

    public void setVilica(String vilica) {
        this.vilica = vilica;
    }

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }

    @XmlTransient
    public List<Intervencija> getIntervencijaList() {
        return intervencijaList;
    }

    public void setIntervencijaList(List<Intervencija> intervencijaList) {
        this.intervencijaList = intervencijaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zub)) {
            return false;
        }
        Zub other = (Zub) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return oznaka+" "+vilica+" "+pozicija;
    }
    
}
