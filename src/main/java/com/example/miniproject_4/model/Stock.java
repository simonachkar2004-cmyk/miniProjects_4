package com.example.miniproject_4.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Stock {

    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final StringProperty category;
    private final StringProperty supplier;
    private final ObjectProperty<LocalDate> lastUpdated;

    public Stock(int id, String name, int quantity, double price,
                 String category, String supplier, LocalDate lastUpdated) {

        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.category = new SimpleStringProperty(category);
        this.supplier = new SimpleStringProperty(supplier);
        this.lastUpdated = new SimpleObjectProperty<>(lastUpdated);
    }

    public Stock(String name, int quantity, double price,
                 String category, String supplier, LocalDate lastUpdated) {
        this(0, name, quantity, price, category, supplier, lastUpdated);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    public int getQuantity() { return quantity.get(); }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public IntegerProperty quantityProperty() { return quantity; }

    public double getPrice() { return price.get(); }
    public void setPrice(double price) { this.price.set(price); }
    public DoubleProperty priceProperty() { return price; }

    public String getCategory() { return category.get(); }
    public void setCategory(String category) { this.category.set(category); }
    public StringProperty categoryProperty() { return category; }

    public String getSupplier() { return supplier.get(); }
    public void setSupplier(String supplier) { this.supplier.set(supplier); }
    public StringProperty supplierProperty() { return supplier; }

    public LocalDate getLastUpdated() { return lastUpdated.get(); }
    public void setLastUpdated(LocalDate date) { this.lastUpdated.set(date); }
    public ObjectProperty<LocalDate> lastUpdatedProperty() { return lastUpdated; }
}