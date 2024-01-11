package com.gim.project.Entity;

import geometry.Envelope;

import io.structures.Feature;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.util.IteratorIterable;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import viewer.base.Layer;
import viewer.symbols.BasicSymbolFactory;
import viewer.symbols.Symbol;
import viewer.symbols.SymbolFactory;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class WFSLayer  {
    public 	static Namespace geo = Namespace.getNamespace("https://www.geoinfo.uni-bonn.de/lehre");
    public static Namespace gml = Namespace.getNamespace("http://www.opengis.net/gml");
    public 	static Namespace xs  = Namespace.getNamespace("http://www.w3.org/2001/XMLSchema");
    public 	static Namespace wfs =	Namespace.getNamespace("http://www.opengis.net/wfs");
    public 	static Namespace xsi =	Namespace.getNamespace("http://www.w3.org/2001/XMLSchema-instance");
    public 	static Namespace ows =	Namespace.getNamespace("http://www.opengis.net/ows");


    private String wfsUrl;
    private String type;
    private Envelope envelope;

    public WFSLayer(String wfsUrl, String type) {
        this.wfsUrl = wfsUrl;
        this.type = type;
        this.envelope =  getExtentFromWFS();
    }

    public Envelope getExtentFromWFS() {
        Envelope env = null;
        try {
            String query ="?request=GetCapabilities&version=1.1.0&service=wfs";
            URL url = new URL(wfsUrl+query);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(conn.getInputStream());

            Element root = doc.getRootElement();
            IteratorIterable<Element> it = root.getDescendants(new
                    ElementFilter("FeatureType"));
            while (it.hasNext()){
                Element itNext = it.next();
                String text = itNext.getText();
                String title = itNext.getChildText("Title",wfs);

                if (title.equals(type)){
                    String lowCorner = itNext.getChild("WGS84BoundingBox", ows).getChildText("LowerCorner",ows);

                    String[] low =  lowCorner.split(" ");

                    Point p1 = point_from4326_to25832(Double.valueOf(low[1]), Double.valueOf(low[0]));

                    String upCorner = itNext.getChild("WGS84BoundingBox", ows).getChildText("UpperCorner",ows);
                    String[] up =  upCorner.split(" ");
                    Point p2 = point_from4326_to25832(Double.valueOf(up[1]), Double.valueOf(up[0]));
                    env =  new Envelope(p1.getX(),p2.getX(),p1.getY(), p2.getY());

                }
            }


        } catch (JDOMException| IOException e) {
            e.printStackTrace();
        }

        return env;
    }



    public List<FieldWFS> WFSquery(){
        List<FieldWFS> result = new ArrayList();
        try {
            String query ="?request=GetFeature&version=1.1.0&service=wfs&typename="+type;
            URL url = new URL(wfsUrl+query);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(conn.getInputStream());

            Element root = doc.getRootElement();
            IteratorIterable<Element> it = root.getDescendants(new ElementFilter(type));
            while (it.hasNext()){
                Element itNext = it.next();
                String srsName = itNext.getChild("geom",geo)
                        .getChild("MultiSurface",gml)
                        .getAttributeValue("srsName");
                String[] s = srsName.split(":");
                Integer srid = Integer.valueOf(s[s.length - 1]);
                String coords = itNext.getChild("geom",geo)
                        .getChild("MultiSurface", gml)
                        .getChild("surfaceMember", gml)
                        .getChild("Polygon", gml)
                        .getChild("exterior", gml)
                        .getChild("LinearRing", gml)
                        .getChildText("posList",gml);
                String[] co = coords.split(" ");
                List<Coordinate> geometry = new ArrayList();
                for(int i=0; i<co.length; i+=2){
                    Point p = point_from25832_to4326(Double.valueOf(co[i+1]), Double.valueOf(co[i]));
                    geometry.add(new Coordinate(p.getX(),p.getY()));
                }


                String field_id = itNext.getChildText("field_id",geo);
                String soil = itNext.getChildText("soil",geo);
                Integer fid  = Integer.valueOf(itNext.getChildText("fid",geo));
                result.add(new FieldWFS(fid,field_id,soil,srid,geometry));

            }




        } catch (JDOMException| IOException e) {
            e.printStackTrace();
        }

        return result;
    }




    /**
     * transforms point from epsg:4326 to epsg:25832
     * @param lat
     * @param lon
     * @return
     */
    public static Point transformation(double lat, double lon, String sourceCRSVal, String targetCRSVal) {
        Point p_target = null;
        try {

            CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:" + sourceCRSVal);
            CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:" + targetCRSVal);
            MathTransform transform = CRS.findMathTransform(sourceCRS,  targetCRS, true);
            GeometryFactory factory = new GeometryFactory();

            Coordinate [] coordinates= {new Coordinate(lat,lon)};
            org.locationtech.jts.geom.CoordinateSequence coordinateSequence = new org.locationtech.jts.geom.impl.CoordinateArraySequence(coordinates);
            Point p_source = new Point(coordinateSequence, factory);

            p_target =  (Point)JTS.transform(p_source, transform);
        } catch (MismatchedDimensionException | TransformException | FactoryException e) {
            e.printStackTrace();
        }
        return p_target;
    }

    public Point point_from4326_to25832(double lat, double lon) {
        return transformation(lat,lon,"4326","25832");
    }
    public Point point_from25832_to4326(double lat, double lon) {
        return transformation(lat,lon,"3044","4326");
    }


    public Envelope getEnvelope() {
        return envelope;
    }
}
