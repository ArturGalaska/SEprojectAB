package com.decorator;

public class AlertDecorator implements Alert{
    final Alert decoratedAlert;

    public AlertDecorator(Alert decoratedAlert) {
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public String getPatientId() {
        return decoratedAlert.getPatientId();
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }

    @Override
    public void createAlert() {
        decoratedAlert.createAlert();
    }
}
