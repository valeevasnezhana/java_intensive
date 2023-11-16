package classes;

import java.util.StringJoiner;

public class Car {
    private String model;
    private Double engineCapacity;
    private Long mileage;

    public Car() {
        this.model = "Default Model";
        this.engineCapacity = 0.0;
        this.mileage = 0L;
    }

    public Car(String model, Double engineCapacity, Long mileage) {
        this.model = model;
        this.engineCapacity = engineCapacity;
        this.mileage = mileage;
    }

    public Long drive(Long value) {
        this.mileage += value;
        return mileage;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("model='" + model + "'")
                .add("engineCapacity=" + engineCapacity)
                .add("mileage=" + mileage)
                .toString();
    }
}
