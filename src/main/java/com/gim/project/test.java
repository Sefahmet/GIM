package com.gim.project;

import com.gim.project.Entity.WFSLayer;
import viewer.base.MapPanel;
import viewer.symbols.SymbolFactory;

import javax.swing.*;
import java.awt.*;

public class test {
    public static void main(String[] args) {
        String sUrl = "http://131.220.71.188:8080/geoserver/geo/ows";
        WFSLayer wfsLayer = new WFSLayer(sUrl, "fields");
        System.out.println(wfsLayer.getEnvelope());
        wfsLayer.WFSquery();


    }
}
