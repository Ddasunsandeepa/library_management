/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.util.tblmodel;

import java.sql.Date;

/**
 *
 * @author mcs
 */
public class Barrow_detailTM {
   private String  barrowid;
   private String  bookid;
   private String  memberid;
   private Date  barrowdate;
   private int returned;
    

public Barrow_detailTM(String  barrowid,String  bookid,String  memberid,Date barrowdate,int  returned){
this.barrowid=barrowid;
this.bookid=bookid;
this.memberid=memberid;
this.barrowdate=barrowdate;
this.returned=returned;



}

    public String getBarrowid() {
        return barrowid;
    }

    
    public void setBarrowid(String barrowid) {
        this.barrowid = barrowid;
    }

    
    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    
    public String getMemberid() {
        return memberid;
    }

    
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    
    public Date getBarrowdate() {
        return barrowdate;
    }

    public void setBarrowdate(Date barrowdate) {
        this.barrowdate = barrowdate;
    }

    
    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }
}