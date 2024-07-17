package Controller;

import java.util.EventObject;

public class shipmentEvent extends EventObject {

    public String id;

    public String num;

    public shipmentEvent(Object source){
        super(source);
    }
    
}
