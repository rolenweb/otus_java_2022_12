package homework;


import java.util.LinkedList;

public class CustomerReverseOrder {
    LinkedList<Customer> customers;

    public CustomerReverseOrder() {
        this.customers = new LinkedList<>();
    }

    public void add(Customer customer) {
        this.customers.add(customer);
    }

    public Customer take() {
        return this.customers.pollLast();
    }
}
