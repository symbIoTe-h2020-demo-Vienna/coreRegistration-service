/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.h2020.symbiote.core;

import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;
import fr.inrialpes.exmo.align.parser.AlignmentParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.semanticweb.owl.align.AlignmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 *
 * @author jab
 */
public class Registry {

//    private static final Logger LOGGER = LoggerFactory.getLogger(Registry.class);
    private static Log log = LogFactory.getLog( Registry.class );
    private long modelIdCounter = 0;
    private long platformIdCounter = 0;
    private long mappingIdCounter = 0;
    private final TripleStore tripleStore;

    public Registry(TripleStore tripleStore) {
        log.info("Creating Registry, loading stored data");
        this.tripleStore = tripleStore;
        List<String> data = tripleStore.loadDataFromDataset();
        setUpCounter( data );
        log.info("Data loaded! Counted >>> [" + modelIdCounter + "] models <<< >>> [" + platformIdCounter + "] platforms <<< >>> [" + mappingIdCounter + "] mappings");
    }

    private void setUpCounter( List<String> data ) {
        modelIdCounter = data.stream().filter( s -> s.startsWith(Ontology.MODELS_GRAPH + "/")).count();
        mappingIdCounter = data.stream().filter( s -> s.startsWith(Ontology.MAPPING_GRAPH + "/")).count();
        platformIdCounter = data.stream().filter( s -> s.startsWith(Ontology.PLATFORMS_GRAPH + "/")).count();
    }

    public long registerModel(String rdf, RDFFormat format) {
        long modelId = modelIdCounter++;
        tripleStore.insertGraph(Ontology.getModelGraphURI(modelId), rdf, format);
        log.debug(String.format("model registered: modelId={}, format={}, rdf={}", modelId, format, rdf));
        return modelId;
    }

    public long registerPlatform(long modelId, String metadata, RDFFormat format) {
        long platformId = platformIdCounter++;
        tripleStore.insertGraph(Ontology.getPlatformGraphURI(platformId), metadata, format);
        tripleStore.insertGraph(Ontology.PLATFORMS_GRAPH, Ontology.getPlatformMetadata(platformId, modelId), format);
        log.debug(String.format("platform registered: platformId={}, modelId={}, format={}, rdf={}", platformId, modelId, format, metadata));
        return platformId;
    }

    public long registerMapping(long modelId1, long modelId2, String mapping) throws UnsupportedEncodingException {
        // use library to parse mapping file to RDF, then read RDFXML into store
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out, "UTF-8")), true)) {
            AlignmentParser parser = new AlignmentParser(0);
            parser.initAlignment(null);
            try {
                parser.parseString(mapping).render(new RDFRendererVisitor(writer));
            } catch (AlignmentException e) {
                log.error("Couldn't load the alignment:", e);
            }
            writer.flush();
        }
        String mappingRDF = out.toString();
        Model model = ModelFactory.createDefaultModel();
        model.read(new ByteArrayInputStream(out.toByteArray()), null, RDFFormat.RDFXML.toString());

        long mappingId = mappingIdCounter++;
        tripleStore.insertGraph(Ontology.getMappingGraphURI(mappingId), model, RDFFormat.RDFXML);
        tripleStore.insertGraph(Ontology.MAPPING_GRAPH, Ontology.getMappingMetadata(modelId1, modelId2, mappingId), RDFFormat.NTriples);
        log.debug(String.format("mapping registered: modelId1={}, modelId2={}, mapping={}", modelId1, modelId2, mappingRDF));
        return mappingId;
    }

}
