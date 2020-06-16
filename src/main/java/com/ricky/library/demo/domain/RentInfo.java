package com.ricky.library.demo.domain;

import com.ricky.library.demo.util.type.BaseEntity;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table rent_info
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class RentInfo extends BaseEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rent_info.info_id
     *
     * @mbggenerated
     */
    private Integer infoId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rent_info.reader_id
     *
     * @mbggenerated
     */
    private String readerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rent_info.borrow_date
     *
     * @mbggenerated
     */
    private Date borrowDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rent_info.return_date
     *
     * @mbggenerated
     */
    private Date returnDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rent_info.book_ISBN
     *
     * @mbggenerated
     */
    private String bookIsbn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rent_info.book_id
     *
     * @mbggenerated
     */
    private String bookId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rent_info.list_id
     *
     * @mbggenerated
     */
    private String listId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rent_info.rent_agent
     *
     * @mbggenerated
     */
    private String rentAgent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rent_info.info_id
     *
     * @return the value of rent_info.info_id
     *
     * @mbggenerated
     */
    public Integer getInfoId() {
        return infoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rent_info.info_id
     *
     * @param infoId the value for rent_info.info_id
     *
     * @mbggenerated
     */
    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rent_info.reader_id
     *
     * @return the value of rent_info.reader_id
     *
     * @mbggenerated
     */
    public String getReaderId() {
        return readerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rent_info.reader_id
     *
     * @param readerId the value for rent_info.reader_id
     *
     * @mbggenerated
     */
    public void setReaderId(String readerId) {
        this.readerId = readerId == null ? null : readerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rent_info.borrow_date
     *
     * @return the value of rent_info.borrow_date
     *
     * @mbggenerated
     */
    public Date getBorrowDate() {
        return borrowDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rent_info.borrow_date
     *
     * @param borrowDate the value for rent_info.borrow_date
     *
     * @mbggenerated
     */
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rent_info.return_date
     *
     * @return the value of rent_info.return_date
     *
     * @mbggenerated
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rent_info.return_date
     *
     * @param returnDate the value for rent_info.return_date
     *
     * @mbggenerated
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rent_info.book_ISBN
     *
     * @return the value of rent_info.book_ISBN
     *
     * @mbggenerated
     */
    public String getBookIsbn() {
        return bookIsbn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rent_info.book_ISBN
     *
     * @param bookIsbn the value for rent_info.book_ISBN
     *
     * @mbggenerated
     */
    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn == null ? null : bookIsbn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rent_info.book_id
     *
     * @return the value of rent_info.book_id
     *
     * @mbggenerated
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rent_info.book_id
     *
     * @param bookId the value for rent_info.book_id
     *
     * @mbggenerated
     */
    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rent_info.list_id
     *
     * @return the value of rent_info.list_id
     *
     * @mbggenerated
     */
    public String getListId() {
        return listId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rent_info.list_id
     *
     * @param listId the value for rent_info.list_id
     *
     * @mbggenerated
     */
    public void setListId(String listId) {
        this.listId = listId == null ? null : listId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rent_info.rent_agent
     *
     * @return the value of rent_info.rent_agent
     *
     * @mbggenerated
     */
    public String getRentAgent() {
        return rentAgent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rent_info.rent_agent
     *
     * @param rentAgent the value for rent_info.rent_agent
     *
     * @mbggenerated
     */
    public void setRentAgent(String rentAgent) {
        this.rentAgent = rentAgent == null ? null : rentAgent.trim();
    }
}