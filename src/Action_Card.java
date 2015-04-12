

/**
 *Kelas : Action_Card inherit dari Card
 *id=1 (ViewMap) id=2(Break) id=3(Repair)
 * @author Ramandika
*/
public class Action_Card extends Card{
	private int id; //1=ViewMap 2=break 3=repair

    /**
     *Ctor action card dengan parameter id kartu
     * @param i index jenis kartu
     */
    public Action_Card(int i) {
        id = i;
    }
    
    /**
     * Method untuk mendapatkan tipe kartu
     * @return 2
     */
    @Override
    public int getType(){
        return 2;
    }
    
    /**
     * Method untuk mendapatkan id kartu
     * @return id
     */
    @Override
    public int getID(){
        return id;
    }
    
    /**
     * MEthod untuk menampilkan kartu
     */
    @Override
    public void displayCard(){
        System.out.println("ini actioncard");
    }
}
