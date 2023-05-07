package com.br.joaoptgaino.schedulingservice.constants;

public enum VehicleType {
    MOTORCYCLE("Motorcycle"),
    CAR("Car"),
    TRICYCLE("Tricycle");
    private final String type;

    VehicleType(String type) {
        this.type = type;
    }

    public String getName() {
        return type;
    }

    public static VehicleType fromString(String value) {
        if (value != null) {
            for (VehicleType vehicleType : VehicleType.values()) {
                if (value.equalsIgnoreCase(vehicleType.type)) {
                    return vehicleType;
                }
            }
        }
        throw new IllegalArgumentException("Invalid VehicleType value: " + value);
    }
}
