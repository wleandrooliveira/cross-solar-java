package com.crossover.techtrial.controller;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;


/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private PanelController panelController;
  
  @Autowired
  private TestRestTemplate template;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(panelController).build();
  }

  @Test
  public void testPanelShouldBeRegistered() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
        "{\"serial\": \"232323\", \"longitude\": \"54.123232\"," 
            + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
        "/api/register", panel, Panel.class);
    Assert.assertEquals(202,response.getStatusCode().value());
  }

  //Implement new test

  @Test
  public void testPanelShouldSaveHourlyElectricity() throws Exception {
    LocalDateTime now = LocalDateTime.now();
    String panel = "{\"id\":\"2\"}";
    HttpEntity<Object> hourly_electricity = getHttpEntity(
            "{\"panel\": " + panel + ", \"generatedElectricity\": \"453\","
                    + " \"readingAt\":"+"\"" + now.toString() + "\" }");
    /**/
    ResponseEntity<HourlyElectricity> response = template.postForEntity(
            "/api/panels/2/hourly", hourly_electricity, HourlyElectricity.class);
    HourlyElectricity hourly = response.getBody() == null ? new HourlyElectricity() : response.getBody();
    hourly.hashCode();
    hourly.equals(new HourlyElectricity());
    hourly.toString();
    Assert.assertEquals(200,response.getStatusCode().value());

  }

  @Test
  public void testPanelShouldGetHourlyElectricity() throws Exception {
    testPanelShouldBeRegistered();
    ResponseEntity<HourlyElectricity> response = template.getForEntity(
            "/api/panel/1234567890123456/hourly", HourlyElectricity.class);
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testPanelShouldNotGetHourlyElectricity() throws Exception {
    ResponseEntity<HourlyElectricity> response = template.getForEntity(
            "/api/panel/1/hourly", HourlyElectricity.class);
    Assert.assertEquals(404,response.getStatusCode().value());
  }

  @Test
  public void testPanelShouldGetAllDailyElectricityFromYesterday() throws Exception {
    ResponseEntity<DailyElectricity[]> response = template.getForEntity(
            "/api/panel/1234567890123456/daily",  DailyElectricity[].class);
    DailyElectricity daily = response.getBody()[0];
    daily.toString();
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }
}
