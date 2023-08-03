package com.br.joaoptgaino.schedulingservice.constants;

public enum SchedulingStatus {
    TODO("Todo"),
    CONFIRMED("Confirmed"),
    DOING("Doing"),
    DONE("Done"),
    CANCELLED("Cancelled");

    private final String status;

    SchedulingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static SchedulingStatus fromString(String value) {
        if (value != null) {
            for (SchedulingStatus schedulingStatus : SchedulingStatus.values()) {
                if (value.equalsIgnoreCase(schedulingStatus.status)) {
                    return schedulingStatus;
                }
            }
        }
        throw new IllegalArgumentException("Invalid SchedulingStatus status: " + value);
    }
}
