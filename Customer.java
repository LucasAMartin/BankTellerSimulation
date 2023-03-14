import java.util.Random;

public class Customer implements Comparable<Customer> {

	private int processTime;
	private int priority;
	private int waitTime;

	// getter methods
	public int getProcessTime() {
		return processTime;
	}

	public int getPriority() {
		return priority;
	}

	public int getWaitTime() {
		return waitTime;
	}

	// setter methods
	public void setProcessTime(int processTimeI) {
		processTime = processTimeI;
	}

	public void setPriority(int priorityI) {
		priority = priorityI;
	}

	// methods for adding/subtracting one stat for each tick of the simulation
	public void incrementWaitTime() {
		waitTime++;
	}

	public void incrementPriority() {
		priority++;
	}

	public void decrementProcessTime() {
		processTime--;
	}

	// constructors, one takes process and priority as input, other takes no
	// arguments
	public Customer(int processTimeI, int priorityI) {
		processTime = processTimeI;
		priority = priorityI;
		waitTime = 0;
	}

	public Customer() {
		processTime = 0;
		priority = 0;
		waitTime = 0;
	}

	// comparable method compares the priority of two customers
	public int compareTo(Customer c) {
		if (priority == c.priority)
			return 0;
		else if (priority > c.priority)
			return 1;
		else
			return -1;
	}
}
