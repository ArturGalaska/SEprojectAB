package com.decorator;

public class PriorityAlertDecorator extends AlertDecorator {
    private String priority;
    public PriorityAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
        setPriority(decoratedAlert);
        createAlert();
    }
    @Override
    public void createAlert(){
        if(this.priority!=null&&this.priority.equals("HIGH")){
            System.out.print("Alert priority: "+this.priority+" |  ");
        }else{
            return;
        }
    }
    public void setPriority(Alert alert){
        if(alert.getCondition().equals(
            "Consistent increase in blood pressure occured")||alert.getCondition().equals(
                "Consistent decrease in blood pressure occured")){
            this.priority="HIGH";
        }
    }
}
