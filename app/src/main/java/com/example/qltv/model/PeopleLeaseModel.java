package com.example.qltv.model;

import java.util.List;

public class PeopleLeaseModel {

    private String id;
    private String name;
    private String numberPhone;
    private int sumViolate;
    private List<idBook> idBooks;

    public PeopleLeaseModel() {
    }

    public PeopleLeaseModel(String id, String name, String numberPhone, int sumViolate, List<idBook> idBook) {
        this.id = id;
        this.name = name;
        this.numberPhone = numberPhone;
        this.sumViolate = sumViolate;
        this.idBooks = idBook;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public List<idBook> getIdBooks() {
        return idBooks;
    }

    public void setIdBooks(List<idBook> idBooks) {
        this.idBooks = idBooks;
    }

    public int getSumViolate() {
        return sumViolate;
    }

    public void setSumViolate(int sumViolate) {
        this.sumViolate = sumViolate;
    }

    public static class idBook {
        private String id;
        private String startDay;
        private String endDay;

        public idBook(String id, String startDay, String endDay) {
            this.id = id;
            this.startDay = startDay;
            this.endDay = endDay;
        }

        public idBook() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStartDay() {
            return startDay;
        }

        public void setStartDay(String startDay) {
            this.startDay = startDay;
        }

        public String getEndDay() {
            return endDay;
        }

        public void setEndDay(String endDay) {
            this.endDay = endDay;
        }
    }
}
