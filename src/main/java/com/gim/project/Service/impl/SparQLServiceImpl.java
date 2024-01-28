package com.gim.project.Service.impl;

import com.gim.project.Service.SparQLService;
import com.gim.project.config.GIMConfig;
import com.gim.project.operations.GIMQuery;
import lombok.RequiredArgsConstructor;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SparQLServiceImpl implements SparQLService {

    @Override
    public String translateDe2En(String deLabel) {
        if(deLabel==null){
            return null;
        }
        String sparqlQuery = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX nalt: <http://lod.nal.usda.gov/nalt/>\n" +
                "PREFIX xmpl: <http://www.co-ode.org/ontologies/ont.owl#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "select ?label\n" +
                "where{\n" +
                "  ?uri rdfs:label \""+deLabel+"\"@de.\n" +
                "  ?uri rdfs:label ?label .\n" +
                "  FILTER(LANG(?label) = \"en\")\n" +
                "}";
        GIMConfig gimConfig = new GIMConfig();
        GIMQuery gimQuery = new GIMQuery();

        List<QuerySolution> lst = gimQuery.sendQuery(sparqlQuery, gimConfig);


        //System.out.println(ResultSetFormatter.asText(rs));
        for (QuerySolution q :lst) {
            RDFNode s = q.get("label");
            String[] ss = s.toString().split("@");
            if(!ss[0].equals(deLabel)){

                return ss[0];
            }


        }
        return deLabel;
    }

    @Override
    public String getImage(String deLabel) {
        String sparqlQuery = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                            "PREFIX nalt: <http://lod.nal.usda.gov/nalt/>\n" +
                            "PREFIX xmpl: <http://www.co-ode.org/ontologies/ont.owl#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "SELECT ?image\n" +
                            "WHERE {\n" +
                            "  ?subject rdfs:label \""+deLabel+"\"@de.\n" +
                            "  ?subject rdfs:seeAlso ?image\n" +
                            "}";

        GIMConfig gimConfig = new GIMConfig();
        GIMQuery gimQuery = new GIMQuery();


        List<QuerySolution> lst= gimQuery.sendQuery(sparqlQuery, gimConfig);

        System.out.println("image istendi" + deLabel);
        //System.out.println(ResultSetFormatter.asText(rs));
        for (QuerySolution q :lst) {

            RDFNode s = q.get("image");
            System.out.println("image Returned from fuseki" + s.toString());
            return s.toString();
            }
        System.out.println("iamge bulunamadı") ;
        return null;
    }

    @Override
    public String getDescription(String deLabel) {
        String sparqlQuery = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                            "PREFIX nalt: <http://lod.nal.usda.gov/nalt/>\n" +
                            "PREFIX xmpl: <http://www.co-ode.org/ontologies/ont.owl#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "select ?comment\n" +
                            "where{\n" +
                            "  ?uri rdfs:label \""+deLabel+"\"@de.\n" +
                            "  ?uri rdfs:comment ?comment .\n" +
                            "\n" +
                            "}";
        GIMConfig gimConfig = new GIMConfig();
        System.out.println("gimQuery config oluşturuldu");
        GIMQuery gimQuery = new GIMQuery();
        System.out.println("gimQuery oluşturuldu");
        List<QuerySolution> lst  = gimQuery.sendQuery(sparqlQuery, gimConfig);

        System.out.println("Description istendi" + deLabel);
        //System.out.println(ResultSetFormatter.asText(rs));
        for (QuerySolution q :lst) {

            RDFNode s = q.get("comment");
            System.out.println("Description Returned from fuseki" + s.toString());
            String[] ss = s.toString().split("@");

            return ss[0];


        }
        System.out.println("Description bulunamadı") ;
        return null;
    }

    @Override
    public HashMap<String,String> getAllEnglishVersions() {
        String sparqlQuery = "\n" +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX nalt: <http://lod.nal.usda.gov/nalt/>\n" +
                "PREFIX xmpl: <http://www.co-ode.org/ontologies/ont.owl#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "SELECT  ?label_de ?label_en\n" +
                "WHERE {\n" +
                "  ?resource rdfs:label ?label_de.\n" +
                "  ?resource rdfs:label ?label_en.\n" +
                "  FILTER(LANG(?label_de) = 'de'&& LANG(?label_en) = 'en')\n" +
                "}\n";
        GIMConfig gimConfig = new GIMConfig();
        GIMQuery gimQuery = new GIMQuery();

        HashMap<String,String> dictionary = new HashMap<>();

        List<QuerySolution> lst = gimQuery.sendQuery(sparqlQuery, gimConfig);


        //System.out.println(ResultSetFormatter.asText(rs));
        for (QuerySolution q :lst) {
            String deLabelwde = q.get("label_de").toString();
            String[] tempDe = deLabelwde.split("@");
            String deLabel = tempDe[0];
            String enLabelwen = q.get("label_en").toString();
            String[] tempEn = enLabelwen.split("@");
            String enLabel = tempEn[0];
            if(!dictionary.keySet().contains(deLabel)){
                dictionary.put(deLabel,enLabel);
            }else{
                if(!deLabel.equals(enLabel)){
                    dictionary.put(deLabel,enLabel);
                }
            }

        }
        HashMap<String,String> deen_ende = new HashMap<>();

        for(Map.Entry<String,String > entry:dictionary.entrySet()){
            deen_ende.put(entry.getKey(),entry.getValue());
            if(!dictionary.keySet().contains(entry.getValue())) {
                deen_ende.put(entry.getValue(), entry.getKey());
            }
        }

        return deen_ende;
    }
}

