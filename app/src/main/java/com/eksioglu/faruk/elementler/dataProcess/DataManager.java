package com.eksioglu.faruk.elementler.dataProcess;

import android.content.Context;

import com.eksioglu.faruk.elementler.R;

import java.util.ArrayList;
import java.util.Locale;

public class DataManager {


    public static final String ALL = "";
    public static final String NONMETAL = "ametal";
    public static final String METAL = "metal";
    public static final String NOBLE_GAS = "soygaz";
    public static final String METALLOID = "yarimetal";
    public static final String HALOJEN = "halojen";
    public static final String TRANSITION_METALS = "gecismetal";
    public static final String LANTHANOID = "lanthanoids";
    public static final String ACTINOID = "actinoids";
    public static final String UNKNOWN = "unknown";



    private Context context;

    private String data;
    private ArrayList<ArrayList<String>> dataList;
    private int elementCount;

    public DataManager(String data, Context context) {
        this.data = data;
        this.context = context;
        if (data.equalsIgnoreCase("null")) return;
        dataList = getDataLines();
        if (dataList == null) System.out.println("datalist is null");
        elementCount = dataList.size()-1;
    }

    private ArrayList<ArrayList<String>> getDataLines(){
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<String> proResult = new ArrayList<>();
        int virgulSayisi = 0;

        if (data == null || data.equalsIgnoreCase("null")) {
            proResult.add("null");
            result.add(proResult);
            return result;
        }
        int indeks = -1;
        while (true){
            String aLine = data.substring(indeks+1, data.indexOf(';', indeks+1));
            indeks = data.indexOf(';', indeks+1);
            if (virgulSayisi == 0) virgulSayisi = findvirgulSayisi(aLine);
            proResult = splitLine(aLine, virgulSayisi);
            result.add(proResult);

            if (indeks == data.length()-1)
                break;
        }

        return result;
    }

    private ArrayList<String> getElementsOnLine(int elementNumber){
        if (elementNumber >= dataList.size()) return null;
        return dataList.get(elementNumber);
    }

    public String getElementsProperty(int elementNumber, String property){
        String result;
        int propertyIndis = 0;
        boolean isFind = false;


        if (dataList == null) return null;
        for (String m:dataList.get(0)){
            if (m.equalsIgnoreCase(property)){
                isFind = true;
                break;
            }
            propertyIndis++;
        }
        if (elementNumber >= dataList.size()) result = null;
        else if (isFind) result = getElementsOnLine(elementNumber).get(propertyIndis);
        else result = "have a problem";

        return result;
    }
    public String getElementsProperty(int elementNumber, int property){
        String result;
        boolean isFind = getElementsOnLine(0).size() > property;

        if (elementNumber >= dataList.size()) result = null;
        else if (isFind) result = getElementsOnLine(elementNumber).get(property);
        else result = "have a problem";

        return result;
    }
    public String getNameOnDeviceLanguage(int elementNumber){
        String language = Locale.getDefault().getDisplayLanguage();
        if (language.equalsIgnoreCase("türkçe")){
            return getElementsProperty(elementNumber, "turkishName");
        }else {
            return getElementsProperty(elementNumber, "name");
        }
    }
    public int getElementsType(int elementNumber){
        String type = getElementsProperty(elementNumber, "tur");
        int result = R.string.defaultValue;

        if (type.equalsIgnoreCase(METAL)){
            result = R.string.metal;
        }else if (type.equalsIgnoreCase(NONMETAL)){
            result = R.string.nonmetal;
        }else if (type.equalsIgnoreCase(NOBLE_GAS)){
            result = R.string.noble_gas;
        }else if (type.equalsIgnoreCase(METALLOID)){
            result = R.string.metalloid;
        }else if (type.equalsIgnoreCase(HALOJEN)){
            result = R.string.halojen;
        }else if (type.equalsIgnoreCase(TRANSITION_METALS)){
            result = R.string.transition_metals;
        }else if (type.equalsIgnoreCase(LANTHANOID)){
            result = R.string.lanthanoid;
        }else if (type.equalsIgnoreCase(ACTINOID)){
            result = R.string.actinoid;
        }else if (type.equalsIgnoreCase(UNKNOWN)){
            result = R.string.unknown;
        }

        return result;
    }
    public int getElementPhase(int elementNumber){
        String phase = getElementsProperty(elementNumber, "faz");
        int result = R.string.unknown;

        if (phase.equalsIgnoreCase("gas")){
            result = R.string.gas;
        }
        else if (phase.equalsIgnoreCase("solid")){
            result = R.string.solid;
        }
        else if (phase.equalsIgnoreCase("liquid")){
            result = R.string.liquid;
        }

        return result;
    }
    public String getElementMass(int elementNumber){
        String result;
        String mass = getElementsProperty(elementNumber, "kutle");

        if (mass.equalsIgnoreCase(UNKNOWN)){
            result = context.getResources().getString(R.string.unknown);
        }
        else {
            result = ""+ mass + " g/mol";
        }

        return result;
    }

    public int getElementsBackgrounColor(int elementNumber){
        int result;
        String tur = "";
        int propertyIndis = 0;
        boolean isFind = false;


        if (dataList == null) return 0;
        for (String m:dataList.get(0)){
            if (m.equalsIgnoreCase("tur")){
                isFind = true;
                break;
            }
            propertyIndis++;
        }
        if (elementNumber >= dataList.size());
        else if (isFind){
            tur = getElementsOnLine(elementNumber).get(propertyIndis);
        }

        result = getTypeBackgrounColorId(tur);

        return result;
    }
    public int getTypeBackgrounColorId(String type){
        int result = R.color.colorAccent;
        if (type == null){
            return R.color.colorAccent;
        }else if (type.equalsIgnoreCase("metal")){
            result = R.color.elementBackground_metal;
        }else if (type.equalsIgnoreCase("ametal")){
            result = R.color.elementBackground_ametal;
        }else if (type.equalsIgnoreCase("soygaz")){
            result = R.color.elementBackground_soygaz;
        }else if (type.equalsIgnoreCase("yarimetal")){
            result = R.color.elementBackground_yarimetal;
        }else if (type.equalsIgnoreCase("halojen")){
            result = R.color.elementBackground_halojen;
        }else if (type.equalsIgnoreCase("gecismetal")){
            result = R.color.elementBackground_gecismetal;
        }else if (type.equalsIgnoreCase("lanthanoids")){
            result = R.color.elementBackground_lanthanoids;
        }else if (type.equalsIgnoreCase("actinoids")){
            result = R.color.elementBackground_actinoids;
        }else if (type.equalsIgnoreCase("unknown")){
            result = R.color.elementBakcground_unknown;
        }


        return result;
    }

    public void setData(String data){
        this.data = data;
        System.out.println(data);
        dataList = getDataLines();
        if (dataList == null) System.out.println("datalist is null");
        elementCount = dataList.size()-1;
    }
    public int getElementCount(){
        return elementCount;
    }

    private ArrayList<String> splitLine(String line, int virgulSayisi){
        ArrayList<String> result = new ArrayList<>();

        int virgulIndis = -1;
        int endIndis;
        for (int i = 0; i<virgulSayisi; i++){
            endIndis = line.indexOf(',', virgulIndis+1);
            if (endIndis == -1) endIndis = line.length();
            String eklenecekVeri = line.substring(virgulIndis+1, endIndis);
            result.add(eklenecekVeri);
            virgulIndis = line.indexOf(',', virgulIndis+1);
        }

        return result;
    }
    private int findvirgulSayisi(String line){
        int virgulSayisi = 0;

        int indeks = 0;
        while (true){
            indeks = line.indexOf(",", indeks+1);
            virgulSayisi++;
            if (indeks == -1){
                break;
            }
        }
        return virgulSayisi;
    }
}
