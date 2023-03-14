import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Simulation {

	// class fields
	static int counter = 0;
	static int departedCustomers = 0;
	static int waitTime = 0;
	static int numTellers = 0;
	static Random rand = new Random();
	// create the heap and teller list
	static Heap<Customer> heap = new Heap<Customer>();
	static ArrayList<Customer> tellers = new ArrayList<Customer>();

	public static void main(String[] args) {

		// get the num of tellers from user, and start the timer
		numTellers = getTellers();
		Timer timer = new Timer(100, new TimerListener());
		timer.start();
		while (true) {
		}
	}

	// the timer calls the method which runs one3 round of the sim
	private static class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Tick " + counter + "\n***********************");
			counter++;
			iterateBank();
		}

		public void iterateBank() {
			// 50% chance to generate a customer each tick
			int chanceNewCustomer = rand.nextInt(2);
			// if the num of current customer is at 200, new customer will not be created
			if (chanceNewCustomer == 1 && (heap.size() + tellers.size() < 200)) {
				heap.insert(newCustomer());
			}
			// after a new customer has either arrived or not, the tellers are checked
			checkTellers();
			// afterwards, wait time and priority for customers not with a teller is
			// incremented
			Iterator<Customer> itr = heap.iterator();
			Customer previous = null;
			if (itr.hasNext()) {
				previous = itr.next();
			}
			// for each person check if the name matches the name to be changed
			while (itr.hasNext()) {
				previous.incrementWaitTime();
				previous.incrementPriority();
				previous = itr.next();
			}
		}
	}

	// method for creation of a new customer
	public static Customer newCustomer() {
		// random procvess time between 5 and 20
		int randProcess = rand.nextInt(16) + 5;
		// random priority between 1 and 10
		int randPriority = rand.nextInt(10) + 1;
		// print stats of new customer
		System.out.println("New Customer has arrived with priority (" + randPriority + ") and transaction time ("
				+ randProcess + ")");
		// if customer are waiting, print how many are currently waiting
		if (!heap.isEmpty()) {
			System.out.println(heap.size() + " customers currently waiting");
		}
		return new Customer(randProcess, randPriority);

	}

	// method for removal of a completed customer
	public static void removeCustomer(Customer customer) {
		// increment num of serviced customers
		departedCustomers++;
		// add wait time to wait time total
		waitTime += customer.getWaitTime();
		// print stats for customer, including wait time and average wait time for all
		// customers
		System.out.println("Customer leaves, customer waited (" + customer.getWaitTime() + ") units of time."
				+ "\nCustomers serviced: " + departedCustomers + " \nAverage wait time: "
				+ (waitTime / departedCustomers));
		tellers.remove(customer);
		// if a teller is open and a customer is waiting, move a customer to a teller
		if (tellers.size() < numTellers && !heap.isEmpty()) {
			serviceCustomer();
		}
	}

	// get num of tellers from user
	public static int getTellers() {
		System.out.print("Enter a number of tellers: ");
		Scanner keyboard = new Scanner(System.in);
		return keyboard.nextInt();
	}

	public static void checkTellers() {
		// if a teller is open and a customer is waiting, move a customer to a teller
		if (tellers.size() < numTellers && !heap.isEmpty()) {
			serviceCustomer();
		}
		if (!tellers.isEmpty()) {
			for (int i = 0; i < tellers.size(); i++) {
				if (tellers.get(i) == null)
					break;
				// increment through the tellers, decrementing process time and removing them if
				// they are completed
				tellers.get(i).decrementProcessTime();
				if (tellers.get(i).getProcessTime() == 0) {
					removeCustomer(tellers.get(i));
				}
			}
		}
	}

	// add a customer to a teller
	public static void serviceCustomer() {
		tellers.add(heap.max());
		if (heap.max() != null) {
			System.out.println("Customer of priority (" + heap.max().getPriority() + ") now being serviced");
		}
		// adds the customer of highest priority from heap, then delete from heap
		heap.deleteMaximum();
	}
}
