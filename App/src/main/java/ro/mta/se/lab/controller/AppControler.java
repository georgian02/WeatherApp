package ro.mta.se.lab.controller;


import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import ro.mta.se.lab.model.AppModel;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Vector;

public class AppControler {

    @FXML
    private ComboBox city_box;
    @FXML
    private ComboBox country_box;
    @FXML
    private Label iconLbl;
    @FXML
    private Label TemperatureLbl;
    @FXML
    private Label HumidityLbl;
    @FXML
    private Label WindLbl;
    @FXML
    private Label DescriptionLbl;

    private Vector<AppModel> lista_orase=new Vector<>();
    private Vector<String> lista_coduri_tari=new Vector<>();

    public Vector<AppModel> getLista_orase(){
        return this.lista_orase;
    }
    public Vector<String> getLista_coduri_tari(){
        return this.lista_coduri_tari;
    }

    @FXML
    private void initialize()
    {
        init(System.getProperty("user.dir")+"\\src\\main\\resources\\"+"init.txt");
        setCountry_box();
        set_city_box();
        get_weather();
    }



    public void init(String filename)
    {
        String nume_oras;
        String id_oras;
        String lat;
        String lon;
        String cod_oras;
        //id, nume, lat, lon, country_code
        try {
            Scanner s=new Scanner((new File(filename)));
            while(s.hasNext())
            {
                id_oras=s.next();
                nume_oras=s.next();
                lat=s.next();
                lon=s.next();
                cod_oras=s.next();
                AppModel oras=new AppModel(nume_oras,id_oras,lat,lon,cod_oras,"none");
                lista_orase.add(oras);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i=0;i<lista_orase.size();i++)
        {
            int ok=0;
            for(int j=0;j<lista_coduri_tari.size();j++) {
                if (lista_coduri_tari.get(j).equals(lista_orase.get(i).getCod_oras()))
                {
                    ok = 1;
                    break;
                }
            }

            if(ok==0) lista_coduri_tari.add(lista_orase.get(i).getCod_oras());
        }

        for(int i=0;i<lista_coduri_tari.size();i++)
            System.out.println(lista_coduri_tari.get(i)+" ");

    }

    public void setCountry_box()
    {
        for(int i=0;i<lista_coduri_tari.size();i++)
            country_box.getItems().add(lista_coduri_tari.get(i));
    }

    public void set_city_box()
    {
        country_box.setOnAction((actionEvent ->
        {
            String country_code=country_box.getValue().toString();
            city_box.getItems().removeAll(city_box.getItems());

            for(int i=0;i<lista_orase.size();i++)
            {
                if(lista_orase.get(i).getCod_oras().equals(country_code))
                {
                    city_box.getItems().add(lista_orase.get(i).getNume_oras());
                }
            }

        }));
    }

    public JSONObject get_json_weather(String city)
    {

        String urlString="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="
                +"e91aa5b9f4c9bc01b39a97905eed76d5"+"&units=metric";

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String buffer="";
        HttpURLConnection con;
        try {
            con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while((line=in.readLine())!=null)
            {
                buffer=buffer+line;
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return (new JSONObject(buffer));
    }

    public void get_weather()
    {
        city_box.setOnAction(actionEvent ->
        {
            String city_name=city_box.getValue().toString();


            set_labels(get_json_weather(city_name));


        });
    }

    public void set_labels(JSONObject obj)
    {
        String local_string=obj.get("weather").toString();
        JSONArray local=new JSONArray(local_string);
        String description=local.getJSONObject(0).get("description").toString();
        DescriptionLbl.setText(description);

        String icon=local.getJSONObject(0).get("icon").toString(); //add lbl
        iconLbl.setGraphic(new ImageView("http://openweathermap.org/img/w/" + icon+ ".png"));
        iconLbl.setScaleX(2.5);
        iconLbl.setScaleY(2.5);

        local_string=obj.get("main").toString();
        JSONObject loc_json=new JSONObject(local_string);
        String humidity=loc_json.get("humidity").toString();

        HumidityLbl.setText(humidity+"%");

        String temperature=loc_json.get("temp_max").toString();
        TemperatureLbl.setText(temperature+"°C");

        local_string=obj.get("wind").toString();
        loc_json=new JSONObject(local_string);
        String wind=loc_json.get("speed").toString();
        WindLbl.setText(wind+"km/h");

        try {
            print_log_file();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void print_log_file() throws IOException {
        FileWriter out=new FileWriter(System.getProperty("user.dir")+"\\src\\main\\resources\\log_file.txt",true);
        out.write(country_box.getValue().toString()+"\t\t");
        out.write(city_box.getValue().toString()+"\t");
        out.write(TemperatureLbl.getText()+"\t\t");
        out.write(HumidityLbl.getText()+"\t\t ");
        out.write(WindLbl.getText()+"\t");
        out.write(DescriptionLbl.getText()+"\n");
        out.close();
    }

}
