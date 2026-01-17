package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResultRow {

    private final StringProperty variable;
    private final StringProperty valor;
    private final StringProperty estado;

    public ResultRow(String variable, String valor, String estado) {
        this.variable = new SimpleStringProperty(variable);
        this.valor = new SimpleStringProperty(valor);
        this.estado = new SimpleStringProperty(estado);
    }

    public String getVariable() {
        return variable.get();
    }

    public StringProperty variableProperty() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable.set(variable);
    }

    public String getValor() {
        return valor.get();
    }

    public StringProperty valorProperty() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor.set(valor);
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }
}
