package hu.alkfejl.model;

import javafx.beans.property.*;

public class Contract {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty pos = new SimpleStringProperty();
    private BooleanProperty partTime = new SimpleBooleanProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPos() {
        return pos.get();
    }

    public StringProperty posProperty() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos.set(pos);
    }

    public boolean isPartTime() {
        return partTime.get();
    }

    public BooleanProperty partTimeProperty() {
        return partTime;
    }

    public void setPartTime(boolean partTime) {
        this.partTime.set(partTime);
    }
}
