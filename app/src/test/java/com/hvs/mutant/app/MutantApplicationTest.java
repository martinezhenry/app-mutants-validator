package com.hvs.mutant.app;

import com.hvs.mutant.model.*;
import com.hvs.mutant.repository.SpecimenRepository;
import com.hvs.mutant.repository.StatsRepository;
import com.hvs.mutant.service.PersistenceService;
import com.hvs.mutant.shared.config.AppConfig;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.SocketUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MutantApplicationTest {

    private final String host = "localhost";
    private final String actionStats = "stats";
    private final String actionMutant = "mutant";

    @Autowired
    private AppConfig config;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Specimen specimen;

    @MockBean
    private StatsRepository statsRepository;

    @MockBean
    private SpecimenRepository specimenRepository;


    /***
     * Create Mongo Embedded instance to use in test
     * @return MongoTemplate instance
     * @throws IOException
     */
    @Bean
    @Primary
    public MongoTemplate mongoTemplate1() throws IOException {
        //String ip = "localhost";
        int randomPort = SocketUtils.findAvailableTcpPort();

        IMongodConfig mongoConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(host, randomPort, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(mongoConfig);
        mongodExecutable.start();
        //return new MongoTemplate(MongoClients.create(String.format(MONGO_DB_URL, ip, randomPort)),MONGO_DB_NAME);
        return new MongoTemplate(MongoClients.create(), "");
    }


    /**
     * Initial config
     */
    @BeforeEach
    void setUp() {
        specimen = new Specimen();
        String[] dna = new String[]{"AT", "AT"};
        specimen.setDna(dna);
        Mockito.when(specimenRepository.save(Mockito.any())).thenReturn(specimen);
        Mockito.when(statsRepository.save(Mockito.any())).thenReturn(new Stats(PersistenceService.STATS_ID, 0, 0, 0.0));

    }


    /**
     * Validate mutant service with valid DNA
     */
    @Test
    void mainValid() {


        // *** Testing Not Mutant DNA

        // Stats service & Mutant Service call
        Stats stats = this.restTemplate.getForObject("http://" + host + ":" + port + "/" + actionStats, Stats.class);
        Response response = this.restTemplate.postForObject("http://" + host + ":" + port + "/" + actionMutant, specimen, Response.class);

        // asserts to stats
        Assertions.assertNotNull(stats);
        Assertions.assertEquals(0, stats.getCountHumanDna());
        Assertions.assertEquals(0, stats.getCountMutantDna());

        // asserts to response
        Assertions.assertNotNull(response);
        Assertions.assertEquals(specimen, response.getSpecimen());
        Assertions.assertFalse(response.getSpecimen().isMutant());
        Assertions.assertEquals(config.getStatusToNotMutant(), response.getStatus());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertNotNull(response.getDatetime());
        Assertions.assertNotNull(response.getRequestId());


        // *** Testing Mutant DNA
        String[] dna = new String[]{"AAAA", "ATGA", "AATG", "ATGA"};
        int mutantSequence = 2;
        specimen.setDna(dna);
        specimen.setMutant(true);
        List<Sequence> sequences = new ArrayList<>();
        Sequence sequenceV = new Sequence();
        sequenceV.setType(SequenceType.VERTICAL);
        Sequence sequenceH = new Sequence();
        sequenceH.setType(SequenceType.HORIZONTAL);
        sequences.add(sequenceH);
        sequences.add(sequenceV);
        specimen.setSequences(sequences);
        response = this.restTemplate.postForObject("http://" + host + ":" + port + "/" + actionMutant, specimen, Response.class);

        // asserts to response
        Assertions.assertNotNull(response);
        Assertions.assertEquals(specimen, response.getSpecimen());
        Assertions.assertEquals(mutantSequence, response.getSpecimen().getSequences().size());
        Assertions.assertTrue(response.getSpecimen().isMutant());
        Assertions.assertEquals(config.getStatusToMutant(), response.getStatus());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertNotNull(response.getDatetime());
        Assertions.assertNotNull(response.getRequestId());

    }

}