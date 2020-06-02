package br.com.joacirjunior.corebanking.controller.api;

import java.util.Date;

public class Meta {

    private Date currentDate;

    public Meta() {
        this.currentDate = new Date();
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

}
