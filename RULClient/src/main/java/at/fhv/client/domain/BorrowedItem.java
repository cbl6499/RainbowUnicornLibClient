package at.fhv.client.domain;

import java.util.Date;

/**
 * Created by ClemensB on 05.11.17.
 */
public class BorrowedItem {

    private int _borrowedId;
    private Date _borrowedDate;
    private ExternalLib _externalLib;
    private Customer _customer;


    public BorrowedItem(int id, Date borrowedDate, ExternalLib externalLib, Customer customer) {
        _borrowedId = id;
        _borrowedDate = borrowedDate;
        _externalLib = externalLib;
        _customer = customer;

    }

    public void setBorrowedId(int id){
        _borrowedId = id;
    }

    public int getBorrowedId(){
        return _borrowedId;
    }

    public void setBorrowedDate(Date borrowedDate){
        _borrowedDate = borrowedDate;
    }

    public Date getBorrowedDate(){
        return _borrowedDate;
    }

    public void setExternalLib(ExternalLib lib){
        _externalLib = lib;
    }

    public ExternalLib getExternalLib(){
        return _externalLib;
    }

    public void setCustomer(Customer customer){
        _customer = customer;
    }

    public Customer getCustomer(){
        return _customer;
    }

    public void setId(int id) {
        setBorrowedId(id);
    }

    public int getId() {
        return getBorrowedId();
    }
}
