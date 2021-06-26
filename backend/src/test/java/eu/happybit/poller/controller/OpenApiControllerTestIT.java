package eu.happybit.poller.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpenApiControllerTestIT {

  private static final String OPENAPI_URL = "/actuator/openapi";

  @Autowired MockMvc mockMvc;

  /**
   * Test if the openapi endpoint returns 200
   * @throws Exception
   */
  @Test
  void show() throws Exception {
    this.mockMvc.perform(get(OPENAPI_URL)).andExpect(status().isOk());
  }
}
