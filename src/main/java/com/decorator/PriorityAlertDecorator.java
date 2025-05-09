package com.decorator;

public class PriorityAlertDecorator extends AlertDecorator {
    private String priority;
    public PriorityAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
        setPriority(decoratedAlert);
    }
    @Override
    public void sendAlert(){
        if(this.priority.equals("HIGH"))
            System.out.println("Alert priority: "+this.priority);
        decoratedAlert.sendAlert();
    }
    public void setPriority(Alert alert){
        if(alert.getCondition().equals("increase")||alert.getCondition().equals("decrease")){
            this.priority="HIGH";
        }
    }
}
