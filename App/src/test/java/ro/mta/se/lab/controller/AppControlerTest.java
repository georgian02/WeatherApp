package ro.mta.se.lab.controller;

import org.json.JSONObject;
import ro.mta.se.lab.model.AppModel;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class AppControlerTest {

    private AppControler test;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        test=new AppControler();
    }


    @org.junit.jupiter.api.Test
    void init() {
        Vector<AppModel> lista_orase=new Vector<>();
        Vector<String> lista_coduri_tari=new Vector<>();
        this.test.init(System.getProperty("user.dir")+"\\src\\main\\resources\\"+"test_init.txt");
        lista_orase =test.getLista_orase();
        lista_coduri_tari= test.getLista_coduri_tari();
        assertEquals(lista_orase.get(0).getCod_oras(),"RU");
        assertEquals(lista_orase.get(0).getNume_oras(),"Razvilka");
        assertEquals(lista_orase.get(0).getId_oras(),"819827");
        assertEquals(lista_orase.get(0).getLat(),"55.591667");
        assertEquals(lista_orase.get(0).getLon(),"37.740833");
        assertEquals(lista_coduri_tari.get(0),"RU");
    }

    @org.junit.jupiter.api.Test
    void get_json_weather() {
        test.init(System.getProperty("user.dir")+"\\src\\main\\resources\\"+"init.txt");
        JSONObject test_obj=test.get_json_weather("Tarascon");

        String local_string=test_obj.get("coord").toString();
        JSONObject loc_json=new JSONObject(local_string);
        String lat=loc_json.get("lat").toString();
        String lon=loc_json.get("lon").toString();
        assertEquals(lat,"43.8058");
        assertEquals(lon,"4.6603");

        local_string=test_obj.get("id").toString();

        assertEquals(local_string,"2973393");
    }



}