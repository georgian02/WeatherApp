package ro.mta.se.lab.model;

import javafx.fxml.FXML;

import java.awt.image.AbstractMultiResolutionImage;

public class AppModel {
    private String nume_oras;
    private String id_oras;
    private String lat;
    private String lon;
    private String cod_oras;
    private String icon;

    public AppModel(String nume_oras, String id_oras, String lat, String lon, String cod_oras, String icon)
    {
        this.cod_oras=cod_oras;//de fapt e cod tara
        this.id_oras=id_oras;
        this.lat=lat;
        this.lon=lon;
        this.nume_oras=nume_oras;
        this.icon=icon;
    }

    public String getNume_oras()
    {
        return this.nume_oras;
    }
    public String getId_oras()
    {
        return this.id_oras;
    }
    public String getLat()
    {
        return this.lat;
    }
    public String getLon()
    {
        return this.lon;
    }
    public String getCod_oras()
    {
        return this.cod_oras;
    }
    public String getIcon(){return  this.icon;}

    public void setNume_oras(String nume_oras)
    {
        this.nume_oras=nume_oras;
    }
    public void setId_oras(String id_oras)
    {
        this.id_oras=id_oras;
    }
    public void setLat(String lat)
    {
        this.lat=lat;
    }
    public void setLon(String lon)
    {
        this.lon=lon;
    }
    public void setCod_oras( String cod_oras)
    {
        this.cod_oras=cod_oras;
    }
    public void setIcon(String icon){this.icon=icon;}


}
