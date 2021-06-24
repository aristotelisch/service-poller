package eu.happybit.poller.controller;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import eu.happybit.poller.domain.ServiceCreateResource;
import eu.happybit.poller.domain.ServiceStatus;
import eu.happybit.poller.entity.Alert;
import eu.happybit.poller.service.AlertService;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceControllerIT {

  private static final String OPENAPI_PATH = "openapi/openapi.yaml";

  @Autowired MockMvc mockMvc;

  @MockBean AlertService alertService;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("Successfully retrieves a new service")
  void successfullyRetrieve2Services() throws Exception {
    Alert alert = new Alert();
    alert.setName("Test website");
    alert.setUrl("http://domain.com");
    alert.setStatus(ServiceStatus.OK);
    alert.setUpdatedAt(LocalDateTime.now());
    alert.setCreatedAt(LocalDateTime.now());

    Mockito.when(alertService.findAll()).thenReturn(List.of(alert));

    this.mockMvc
        .perform(get("/services").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(openApi().isValid(OPENAPI_PATH));
  }

  @Test
  @DisplayName("When there are no available services return an empty list")
  void returnEmptyListWhenNoServicesAvailable() throws Exception {

    this.mockMvc
        .perform(get("/services").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(openApi().isValid(OPENAPI_PATH))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  @DisplayName("When creating alert with invalid url format return bad request")
  void badRequestResponseOnInvalidUrlFormat() throws Exception {
    ServiceCreateResource serviceCreateResource =
        ServiceCreateResource.builder().name("Test Name").url("invalidUrlFormat").build();

    this.mockMvc
        .perform(
            post("/services")
                .content(objectMapper.writeValueAsString(serviceCreateResource))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.errors[0]").value("Invalid url format"))
        .andExpect(openApi().isValid(OPENAPI_PATH));
  }

  @Test
  @DisplayName("When Deleting and alert that exists return no content")
  void deletingExistingAlertReturnsNoContent() throws Exception {

    this.mockMvc.perform(delete("/services/10").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(openApi().isValid(OPENAPI_PATH));
  }
}
