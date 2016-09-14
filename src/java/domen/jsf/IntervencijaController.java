package domen.jsf;

import domen.Intervencija;
import domen.jsf.util.JsfUtil;
import domen.jsf.util.JsfUtil.PersistAction;
import domen.beans.IntervencijaFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("intervencijaController")
@SessionScoped
public class IntervencijaController implements Serializable {

    @EJB
    private domen.beans.IntervencijaFacade ejbFacade;
    private List<Intervencija> items = null;
    private Intervencija selected;

    public IntervencijaController() {
    }

    public Intervencija getSelected() {
        return selected;
    }

    public void setSelected(Intervencija selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getIntervencijaPK().setDatum(selected.getPregled().getDatum());
        selected.getIntervencijaPK().setUsluga(selected.getUsluga1().getId());
        selected.getIntervencijaPK().setZub(selected.getZub1().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setIntervencijaPK(new domen.IntervencijaPK());
    }

    private IntervencijaFacade getFacade() {
        return ejbFacade;
    }

    public Intervencija prepareCreate() {
        selected = new Intervencija();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("IntervencijaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("IntervencijaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("IntervencijaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Intervencija> getItems() {
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

    public Intervencija getIntervencija(domen.IntervencijaPK id) {
        return getFacade().find(id);
    }

    public List<Intervencija> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Intervencija> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Intervencija.class)
    public static class IntervencijaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IntervencijaController controller = (IntervencijaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "intervencijaController");
            return controller.getIntervencija(getKey(value));
        }

        domen.IntervencijaPK getKey(String value) {
            domen.IntervencijaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new domen.IntervencijaPK();
            key.setDatum(java.sql.Date.valueOf(values[0]));
            key.setZub(Integer.parseInt(values[1]));
            key.setUsluga(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(domen.IntervencijaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getDatum());
            sb.append(SEPARATOR);
            sb.append(value.getZub());
            sb.append(SEPARATOR);
            sb.append(value.getUsluga());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Intervencija) {
                Intervencija o = (Intervencija) object;
                return getStringKey(o.getIntervencijaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Intervencija.class.getName()});
                return null;
            }
        }

    }

}
