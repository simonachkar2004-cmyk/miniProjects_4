package com.example.miniproject_4;

import javafx.beans.property.*;
import java.time.LocalDate;

public class client {

    private final StringProperty name;
    private final StringProperty city;
    private final StringProperty orderNumber;
    private final ObjectProperty<LocalDate> date;
    private final DoubleProperty price;

    public client(String name, String city, String orderNumber, LocalDate date, double price) {
        this.name = new SimpleStringProperty(name);
        this.city = new SimpleStringProperty(city);
        this.orderNumber = new SimpleStringProperty(orderNumber);
        this.date = new SimpleObjectProperty<>(date);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    public String getCity() { return city.get(); }
    public void setCity(String value) { city.set(value); }
    public StringProperty cityProperty() { return city; }

    public String getOrderNumber() { return orderNumber.get(); }
    public void setOrderNumber(String value) { orderNumber.set(value); }
    public StringProperty orderNumberProperty() { return orderNumber; }

    public LocalDate getDate() { return date.get(); }
    public void setDate(LocalDate value) { date.set(value); }
    public ObjectProperty<LocalDate> dateProperty() { return date; }

    public double getPrice() { return price.get(); }
    public void setPrice(double value) { price.set(value); }
    public DoubleProperty priceProperty() { return price; }
}