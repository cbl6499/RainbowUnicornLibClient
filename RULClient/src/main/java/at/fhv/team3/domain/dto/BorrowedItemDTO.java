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
    private int _extendCount;


    public BorrowedItemDTO(int id, Date borrowedDate, DTO borrower, DTO dto) {
        _borrowedId = id;
        _borrowedDate = borrowedDate;

        if (dto instanceof ExternalLibDTO) {
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

    public int getExtendCount(){
        return _extendCount;
    }

    public void setExtendCount(int count){
        _extendCount = count;
    }

    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_borrowedId);
        if (_externalLib != null) {
            allData.put("externalLib", _externalLib.toString());
        } else if (_customer != null) {
            allData.put("customer", _customer.toString());
        }
        allData.put("date", _borrowedDate.toString());
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

    public String toString() {
        HashMap<String, String> map = getAllData();
        StringBuilder sb = new StringBuilder();
        sb.append(map.get("id") + " ");
        if (map.containsKey("externalLib")) {
            sb.append(map.get("externalLib") + " ");
        } else if (map.containsKey("externalLib")) {
            sb.append(map.get("customer") + " ");
        }

        sb.append(map.get("date") + " ");

        if (map.containsKey("book")) {
            sb.append(map.get("book"));
        } else if (map.containsKey("dvd")) {
            sb.append(map.get("dvd"));
        } else if (map.containsKey("magazine")) {
            sb.append(map.get("magazine"));
        } else {
            sb.append("ITEM CAN`T BE READ PROPPERLY CLASS BORROWEDITEM, METHODE: TOSTRING");
        }
        return sb.toString();
    }

}
