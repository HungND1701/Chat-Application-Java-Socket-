package event;

public class PublicEvent {
    
    private static PublicEvent ínstance;
    private EventImageView eventImageView;
    private EventChat eventChat;
    private EventLogin eventLogin;
    private EventMain eventMain;
    
    private PublicEvent(){
        
    }
            
    public static PublicEvent getInstance(){
        if(ínstance==null){
            ínstance = new PublicEvent();
        }
        return ínstance;
    }
    
    public void addEventImageView(EventImageView event){
        this.eventImageView = event;
    }
    
     public EventImageView getEventImageView() {
        return eventImageView;
    }
    
    public EventChat addEventChat(EventChat event) {
        return this.eventChat = event;
    } 
    
    public EventChat getEventChat() {
        return eventChat;
    } 
    
    public EventLogin addEventLogin(EventLogin event) {
        return this.eventLogin = event;
    } 
    
    public EventLogin getEvenLogin() {
        return eventLogin;
    } 
    
    public EventMain addEventMain(EventMain event) {
        return this.eventMain = event;
    } 
    
    public EventMain getEventMain() {
        return eventMain;
    } 
}
