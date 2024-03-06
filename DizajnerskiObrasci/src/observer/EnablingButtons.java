package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EnablingButtons {
	
	private boolean deleteEnabled;
	private boolean editEnabled;
	private boolean bringToFrontEnabled;
	private boolean bringToBackEnabled;
	private boolean toBackEnabled;
	private boolean toFrontEnabled;
	
	//upravlja listom listenera i prosledjuje im changeEvent
	//ovde dodajemo i ukladnjamo listenere
	private PropertyChangeSupport propertyChangeSupport;
	
	//Kontruktor
	public EnablingButtons() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.removePropertyChangeListener(pcl);
	}

	//Setteri
	public void setDeleteEnabled(boolean deleteEnabled) {
		propertyChangeSupport.firePropertyChange("btnDelete", this.deleteEnabled, deleteEnabled);
		this.deleteEnabled = deleteEnabled;
	}

	public void setEditEnabled(boolean editEnabled) {
		propertyChangeSupport.firePropertyChange("btnEdit", this.editEnabled, editEnabled);
		this.editEnabled = editEnabled;
	}

	public void setBringToFrontEnabled(boolean bringToFrontEnabled) {
		propertyChangeSupport.firePropertyChange("btnToFront", this.bringToFrontEnabled, bringToFrontEnabled);
		this.bringToFrontEnabled = bringToFrontEnabled;
	}

	public void setBringToBackEnabled(boolean bringToBackEnabled) {
		propertyChangeSupport.firePropertyChange("btnToBack", this.bringToBackEnabled, bringToBackEnabled);
		this.bringToBackEnabled = bringToBackEnabled;
	}

	public void setToBackEnabled(boolean toBackEnabled) {
		propertyChangeSupport.firePropertyChange("btnBack", this.toBackEnabled, toBackEnabled);
		this.toBackEnabled = toBackEnabled;
	}

	public void setToFrontEnabled(boolean toFrontEnabled) {
		propertyChangeSupport.firePropertyChange("btnFront", this.toFrontEnabled, toFrontEnabled);
		this.toFrontEnabled = toFrontEnabled;
	}	
}
