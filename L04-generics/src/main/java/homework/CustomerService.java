package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customers;

    public CustomerService() {
        this.customers =new TreeMap<>(new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return (int)(o1.getScores() - o2.getScores());
            }
        });
    }

    public Map.Entry<Customer, String> getSmallest() {
        Customer key = this.customers.firstEntry().getKey();
        return Map.entry(new Customer(key.getId(), key.getName(), key.getScores()), this.customers.firstEntry().getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = this.customers.higherEntry(customer);
        if (higherEntry == null) {
            return null;
        }
        Customer key = higherEntry.getKey();
        return Map.entry(new Customer(key.getId(), key.getName(), key.getScores()), higherEntry.getValue());
    }

    public void add(Customer customer, String data) {
        this.customers.put(customer, data);
    }
}
