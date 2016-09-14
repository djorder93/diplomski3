/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domen.Medsestra;
import domen.jsf.util.JsfUtil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import kontroler.Kontroler;

/**
 *
 * @author radovanovic
 */
@ManagedBean(name = "mbLogin")
@SessionScoped
public class MbLogin implements Serializable {

    /**
     * Creates a new instance of MbLogin
     */
    Medsestra ms;

    public MbLogin() {
        ms = new Medsestra();
    }

    public Medsestra getMs() {
        return ms;
    }

    public void setMs(Medsestra ms) {
        this.ms = ms;
    }

    public String login() {

        FacesContext context = FacesContext.getCurrentInstance();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomski3PU");
        EntityManager em = emf.createEntityManager();

        try {
            ms = em.createNamedQuery("Medsestra.login", Medsestra.class).setParameter("username", ms.getUsername()).setParameter("password", ms.getPassword()).getSingleResult();
            if (ms == null) {
                JsfUtil.addErrorMessageLogin("Login error user doesn't exist!");
                return null;
            } else {
                JsfUtil.addSuccessMessageLogin("Welcom " + ms.toString());
                HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
                session.setAttribute("logged", ms);
                em.close();
                emf.close();
                return "/stranice/template.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessageLogin("Login error user doesn't exist!");
            em.close();
            emf.close();
            return null;
        }
    }

    public String logout() {

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?redirexted?faces-redirect=true";
    }

    public String loggedUser() {
        //System.out.println("Ulogovani korisnik: " + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logged").toString());
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logged").toString();
    }

    public Medsestra getLogged() {
        return (Medsestra) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logged");
    }
}
