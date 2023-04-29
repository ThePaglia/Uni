package pr2016.a05.sol1;

public class AlternateElevatorImpl extends AbstractElevator {
    
    public AlternateElevatorImpl() {
        super();
    }
    
    @Override
    protected void update(){
        if (this.isMoving()){
           if (this.next()==null){
               this.up = !this.up;
           }
        }
     }
}
