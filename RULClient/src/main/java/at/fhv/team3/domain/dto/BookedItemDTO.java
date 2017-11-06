package at.fhv.team3.domain.dto;

import at.fhv.team3.domain.Customer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by David on 10/30/2017.
 */
public class BookedItemDTO extends DTO {

    private int _bookingId;
    private Customer _customer;
    private Date _date;

    public BookedItemDTO(int id, Customer customer, Date date) {
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

    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_bookingId);
        allData.put("customer", _customer.getFirstName() + " " + _customer.getLastName());
        allData.put("date", _date.toString());
        return allData;
    }

    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if (data.get("customer").equals(_customer.getFirstName() + " " + _customer.getLastName()) && data.get("date").equals(_date.toString())) {
            return true;
        }
        return false;
    }
}
