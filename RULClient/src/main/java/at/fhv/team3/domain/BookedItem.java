package at.fhv.team3.domain;

import java.util.Date;

/**
 * Created by ClemensB on 05.11.17.
 */
public class BookedItem {

    private int _bookingId;
    private Customer _customer;
    private Date _date;

    public BookedItem(int id, Customer customer, Date date) {
        _bookingId = id;
        _customer = customer;
        _date = date;
    }

    public void setBookingId(int id){
        _bookingId = id;
    }

    public int getBookingId(){
        return _bookingId;
    }

    public void setCustomer(Customer customer){
        _customer = customer;
    }

    public Customer getCustomer(){
        return _customer;
    }

    public void setDate(Date date){
        _date = date;
    }

    public Date getdate(){
        return _date;
    }

    public void setId(int id) {
        setBookingId(id);
    }

    public int getId() {
        return getBookingId();
    }
}
