package at.fhv.team3.domain.dto;

import java.util.HashMap;

/**
 * Created by Christoph on 13.11.2017.
 */
public class OverdueNoticeDTO extends DTO {

    private int _id;
    private int _borrowedItemId;

    public OverdueNoticeDTO(int id, int borrowedItemId) {
        _id = id;
        _borrowedItemId = borrowedItemId;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getId() {
        return 0;
    }

    public int getBorrowedItemId() {
        return _borrowedItemId;
    }

    public void setBorrowedItemId(int borrowedItemId) {
        this._borrowedItemId = borrowedItemId;
    }

    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", "" + _id);
        allData.put("borrowedItemId","" + _borrowedItemId);
        return allData;
    }

    public boolean equals(DTO dto) {
        return false;
    }

    public String toString() {
        HashMap<String, String> map = getAllData();
        StringBuilder sb = new StringBuilder();
        sb.append(map.get("id") + " ");
        sb.append(map.get("borrowedItemId"));

        return sb.toString();
    }
}
