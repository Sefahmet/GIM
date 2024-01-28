package com.gim.project.operations;

import com.gim.project.config.GIMConfig;
import lombok.RequiredArgsConstructor;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GIMQuery {

    private String format;

    /**
     * This method sends a query to Fuseki (The SPARQL-Endpoint of Apache Jena)
     * @param sparqlQueryString contains a SPARQL query in a String
     * @param gimConfig contains the configuration of the virtual machine.
     */
    public List<QuerySolution> sendQuery(String sparqlQueryString, GIMConfig gimConfig) {


        // connect to Fuseki
        RDFConnection conn = RDFConnectionFactory.connect(
                "http://" + gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort() + "/ds/");

        // create a Query
        Query query = QueryFactory.create(sparqlQueryString);

        // send query and get result
        QueryExecution qExec = conn.query(query) ;

        // get the query type
        int queryType = query.getQueryType();

        switch (queryType) {

            // ASK-SPARQL-Query
            case Query.QueryTypeAsk:

                // fill a boolean with data from the query
                boolean result = qExec.execAsk();


                break;

            // CONSTRUCT-SPARQL-Query
            case Query.QueryTypeConstruct:

                // save the response data to a model/graph
                Model results = qExec.execConstruct();

                // create a string writer to convert the model to a string
                StringWriter outConstruct = new StringWriter();

                // write model with outputFormat to string writer
                results.write(outConstruct, format);

                // convert string writer to string

                break;

            // DESCRIBE-SPARQL-Query
            case Query.QueryTypeDescribe:

                // save the response data to a model/graph
                results = qExec.execDescribe();

                StringWriter outDescribe = new StringWriter();

                results.write(outDescribe, format);


                break;

            // SELECT-SPARQL-Query
            case Query.QueryTypeSelect:

                // save the response data to a result set
                ResultSet rs = qExec.execSelect() ;
                List<QuerySolution> querySolutions = ResultSetFormatter.toList(rs);
                qExec.close();
                conn.close();
                return querySolutions;

        }

        qExec.close();
        conn.close();
        return null;

    }

    /**
     * This method sets the format for the CONSTRUCT output of a SPARQL query.
     * Accept formats can be: "turtle", "rdf/xml", "json-ld", "n-triples", "n3", "n-quads", "rdf/json", "trig", "trix"
     * @param format contains a format for the output
     */
    public void setConstructFormat(String format) {

        this.format = format;

    }

}
