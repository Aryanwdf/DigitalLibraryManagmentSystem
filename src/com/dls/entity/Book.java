package com.dls.entity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Book implements Comparable<Object> {

    private String name;
    private int id;
    private String isbn;
    private String author;
    private String publisher;
    private String remark;
    private String status;
    private int pages;
    private int year;
    private double price;
    private Date date;

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Book)) {
            return false;
        }
        Book book = (Book) object;
        return this.id == book.id;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    @Override
    public int compareTo(Object object) {
        Book book = (Book) object;
        return Integer.compare(id, book.getId());
    }

    public String getName() {
        return name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTotalPages() {
        return pages;
    }

    public void setTotalPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getEntryDateStr() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public Date getEntryDate() {
        return this.date;
    }

    public void setEntryDate(Date date) {
        this.date = date;
    }

}
