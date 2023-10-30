package com.example.altriaphone.Object;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product implements Serializable {
private int id, num_of_mc, num_of_sc, ppi;
    private String name, brand, resolution, os, chipset, card_slot, rom, ram, pic_mc, pic_sc, jack, battery;
    private double size, price, screenarea;
    private String link, img;
    private Date announced;

    // Constructor
    public Product(int id, String name, String brand, String announced, double size, String resolution, String os, String chipset, String card_slot, String rom, String ram, int num_of_mc, String pic_mc, int num_of_sc, String pic_sc, String jack, String battery, double price, double screenarea, int ppi, String link, String img) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM");
            this.announced = format.parse(announced);
        } catch (ParseException e ){
            e.printStackTrace();
        }
        this.size = size;
        this.resolution = resolution;
        this.os = os;
        this.chipset = chipset;
        this.card_slot = card_slot;
        this.rom = rom;
        this.ram = ram;
        this.num_of_mc = num_of_mc;
        this.pic_mc = pic_mc;
        this.num_of_sc = num_of_sc;
        this.pic_sc = pic_sc;
        this.jack = jack;
        this.battery = battery;
        this.price = price;
        this.screenarea = screenarea;
        this.ppi = ppi;
        this.link = link;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public int getNum_of_mc() {
        return num_of_mc;
    }

    public int getNum_of_sc() {
        return num_of_sc;
    }

    public int getPpi() {
        return ppi;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Date getAnnounced() {
        return announced;
    }

    public String getResolution() {
        return resolution;
    }

    public String getOs() {
        return os;
    }

    public String getChipset() {
        return chipset;
    }

    public String getCard_slot() {
        return card_slot;
    }

    public String getRom() {
        return rom;
    }

    public String getRam() {
        return ram;
    }

    public String getPic_mc() {
        return pic_mc;
    }

    public String getPic_sc() {
        return pic_sc;
    }

    public String getJack() {
        return jack;
    }

    public String getBattery() {
        return battery;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public double getScreenarea() {
        return screenarea;
    }
    public String getLink() {
        return link;
    }
    public String getImg() { return img; }
}
