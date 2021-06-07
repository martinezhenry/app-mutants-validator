package com.hvs.mutant.app;

import com.hvs.mutant.model.Response;
import com.hvs.mutant.model.Specimen;
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
import org.springframework.http.HttpStatus;
import org.springframework.util.SocketUtils;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantApplicationInvalidTest {

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
    private PersistenceService persistenceService;


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

    @BeforeEach
    void setUp() {
        specimen = new Specimen();
        String[] dna = new String[]{"AT", "AT"};
        specimen.setDna(dna);


    }

    @Test
    void mainInvalid() {
        String[] dna = new String[]{"AAAA", "ATGAT", "AATG", "ATGA"};
        int mutantSequence = 2;
        specimen.setDna(dna);
        specimen.setMutant(true);

        Response response = this.restTemplate.postForObject("http://" + host + ":" + port + "/" + actionMutant, specimen, Response.class);
        Assertions.assertNotNull(response);
        Assertions.assertNull(response.getSpecimen());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertNotNull(response.getDatetime());
        Assertions.assertNotNull(response.getRequestId());


        String message = "Exception Test";
        Mockito.doThrow(new RuntimeException(message)).when(persistenceService).saveSpecimen(Mockito.any());
        dna = new String[]{"AAAA", "ATGA", "AATG", "ATGA"};
        specimen.setDna(dna);
        response = this.restTemplate.postForObject("http://" + host + ":" + port + "/" + actionMutant, specimen, Response.class);
        Assertions.assertNotNull(response);
        Assertions.assertNull(response.getSpecimen());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals(message, response.getMessage());
        Assertions.assertNotNull(response.getDatetime());
        Assertions.assertNotNull(response.getRequestId());


    }
}

