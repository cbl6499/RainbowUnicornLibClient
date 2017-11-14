package at.fhv.team3.domain.dto;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by David on 10/30/2017.
 */
public class BorrowedItemDTO extends DTO {

    private int _borrowedId;
    private Date _borrowedDate;
    private ExternalLibDTO _externalLib;
    private CustomerDTO _customer;
    private BookDTO _book;
    private DvdDTO _dvd;
    private MagazineDTO _magazine;


    public BorrowedItemDTO(int id, Date borrowedDate, DTO borrower, DTO dto) {
        _borrowedId = id;
        _borrowedDate = borrowedDate;

        if (dto instanceof BookDTO) {
            _externalLib = (ExternalLibDTO) borrower;
        } else {
            _customer = (CustomerDTO) borrower;
        }

        if (dto instanceof BookDTO) {
            _book = (BookDTO) dto;
        } else if (dto instanceof DvdDTO) {
            _dvd = (DvdDTO) dto;
        } else {
            _magazine = (MagazineDTO) dto;
        }
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

    public void setExternalLib(ExternalLibDTO lib){
        _externalLib = lib;
    }

    public ExternalLibDTO getExternalLib(){
        return _externalLib;
    }

    public void setCustomer(CustomerDTO customer){
        _customer = customer;
    }

    public CustomerDTO getCustomer(){
        return _customer;
    }

    public void setId(int id) {
        setBorrowedId(id);
    }

    public int getId() {
        return getBorrowedId();
    }

    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_borrowedId);
        allData.put("externalLib", _externalLib.toString());
        allData.put("date", _borrowedDate.toString());
        allData.put("customer", _customer.toString());
        if (_book != null) {
            allData.put("book", _book.toString());
        } else if (_dvd != null) {
            allData.put("dvd", _dvd.toString());
        } else if (_magazine != null) {
            allData.put("magazine", _magazine.toString());
        } else {
            return null;
        }
        return allData;
    }

    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if(data.get("externalLib").equals(_externalLib.getName()) && data.get("date").equals(_borrowedDate.toString()) && data.get("customer").equals(_customer.getFirstName() + " " + _customer.getLastName())){
            return true;
        }
        return false;
    }


    public BookDTO get_book() {
        return _book;
    }

    public void set_book(BookDTO _book) {
        this._book = _book;
    }

    public DvdDTO get_dvd() {
        return _dvd;
    }

    public void set_dvd(DvdDTO _dvd) {
        this._dvd = _dvd;
    }

    public MagazineDTO get_magazine() {
        return _magazine;
    }

    public void set_magazine(MagazineDTO _magazine) {
        this._magazine = _magazine;
    }
}
