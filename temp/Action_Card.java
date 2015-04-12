
public class Action_Card extends Card{
	private int id; //1=ViewMap 2=break 3=repair
    public Action_Card(int i) {
        id = i;
    }
    
    @Override
    public int getType(){
        return 2;
    }
    
    @Override
    public int getID(){
        return id;
    }
    
    @Override
    public void displayCard(){
        System.out.println("ini actioncard");
    }
}
