package pr2016.a05.sol1;

public class BasicElevatorImpl extends AbstractElevator {
       
    public BasicElevatorImpl() {
        super();
    }    

    @Override 
    protected void update(){
        if (this.isMoving()){
            Integer h = this.pending.higher(this.floor);
            Integer l = this.pending.lower(this.floor);
            if (h!=null && l!=null){
                this.up = h-this.floor < l-this.floor;
            } else {
                this.up = (h!=null);
            }
        }
     }
 }
