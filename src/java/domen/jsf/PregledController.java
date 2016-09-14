package domen.jsf;

import domen.Pregled;
import domen.jsf.util.JsfUtil;
import domen.jsf.util.JsfUtil.PersistAction;
import domen.beans.PregledFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("pregledController")
@RequestScoped
public class PregledController implements Serializable {

    @EJB
    private domen.beans.PregledFacade ejbFacade;
    private List<Pregled> items = null;
    private Pregled selected;

    public PregledController() {
    }

    public Pregled getSelected() {
        return selected;
    }

    public void setSelected(Pregled selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PregledFacade getFacade() {
        return ejbFacade;
    }

    public void datFilter(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('pregledi').filter()");
    }

    public Pregled prepareCreate() {
        selected = new Pregled();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PregledCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PregledUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PregledDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Pregled> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Pregled getPregled(java.util.Date id) {
        return getFacade().find(id);
    }

    public List<Pregled> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Pregled> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Pregled.class)
    public static class PregledControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PregledController controller = (PregledController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pregledController");
            return controller.getPregled(getKey(value));
        }

        java.util.Date getKey(String value) {
            java.util.Date key;
            key = java.sql.Date.valueOf(value);
            return key;
        }

        String getStringKey(java.util.Date value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pregled) {
                Pregled o = (Pregled) object;
                return getStringKey(o.getDatum());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pregled.class.getName()});
                return null;
            }
        }

    }

}
