package oopsdemo2;

public class SavingsBank extends Account {
	
	private int min_bal; //can access within class
	protected int balance; //can access within class and sub class
	public SavingsBank(int accNo, String name, int mb, int b) {
		super(accNo, name);
		this.min_bal = mb;
		this.balance = b;
	}
	void display()
    {
            super.display(); // invoke super class method
            System.out.println ("Minimum Balance: "+min_bal);
            System.out.println ("Saving Balance: "+balance);
    }
	

}
