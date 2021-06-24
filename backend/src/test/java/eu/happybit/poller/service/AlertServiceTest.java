package eu.happybit.poller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import eu.happybit.poller.domain.ServiceCreateResource;
import eu.happybit.poller.domain.ServiceStatus;
import eu.happybit.poller.domain.ServiceUpdateResource;
import eu.happybit.poller.entity.Alert;
import eu.happybit.poller.exception.ServiceNotFoundException;
import eu.happybit.poller.mapper.AlertMapper;
import eu.happybit.poller.repository.AlertRepository;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

  @InjectMocks AlertService alertService;

  @Mock AlertRepository alertRepository;

  @Test
  @DisplayName("Saves a new alert")
  void savesAnewAlert() {

    ServiceCreateResource newService =
        ServiceCreateResource.builder().name("Test Name").url("https://domain.com").build();

    alertService.save(newService);

    verify(alertRepository, times(1)).save(AlertMapper.MAPPER.map(newService));
  }

  @Test
  @DisplayName("Retrieves all services")
  void shouldRetrieveAllServices() {
    Alert alert = new Alert();
    alert.setName("Test Alert");
    alert.setStatus(ServiceStatus.OK);
    alert.setUrl("http://google.com");

    List<Alert> allAlerts = List.of(alert);
    when(alertRepository.findAll()).thenReturn(allAlerts);

    List<Alert> services = alertService.findAll();

    Assertions.assertEquals(1, services.size());
  }

  @Test
  @DisplayName("Deletes an existing service")
  void shouldDeleteExistingService() {
    Alert alert = new Alert();
    alert.setId(1L);
    alert.setName("Test Alert");
    alert.setStatus(ServiceStatus.OK);
    alert.setUrl("http://google.com");

    when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));

    alertService.delete(1L);

    verify(alertRepository, times(1)).delete(alert);
  }

  @Test
  @DisplayName("Deleting a non existing service throws an Exception")
  void shouldThrowExceptionWhenDeleteNonExistingService() {
    when(alertRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(
        ServiceNotFoundException.class,
        () -> {
          alertService.delete(1L);
        });

    verify(alertRepository, times(0)).delete(any());
  }

  @Test
  @DisplayName("FindById returns an alert when exists by id")
  void shouldReturnAnAlert() {
    Alert alert = new Alert();
    alert.setId(1L);
    alert.setName("Test Alert");
    alert.setStatus(ServiceStatus.OK);
    alert.setUrl("http://google.com");

    when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));
    Alert result = alertService.findById(1L);

    assertNotNull(result);
    assertEquals(alert.getUrl(), result.getUrl());
  }

  @Test
  @DisplayName("Finding by id that does not exist returns ServiceNotFoundException")
  void shouldThrowExceptionWhenIdDoesNotExist() {
    when(alertRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(
        ServiceNotFoundException.class,
        () -> {
          alertService.findById(3123123L);
        });
  }

  @Test
  @DisplayName("Updates the status of an alert")
  void shouldUpdateStatusOfAlert() {
    Alert alert = new Alert();
    alert.setId(1L);
    alert.setName("Test Alert");
    alert.setStatus(ServiceStatus.OK);
    alert.setUrl("http://google.com");

    alertService.saveNewStatus(alert, ServiceStatus.FAIL);

    verify(alertRepository, times(1)).save(alert);
  }

  @Test
  @DisplayName("Should Update an existing alert when given a valid ServiceUpdateResource")
  void shouldFindAnExistingAlertById() {
    ServiceUpdateResource resource =
        ServiceUpdateResource.builder().name("Test Name").url("https://example.com").build();
    LocalDateTime now = LocalDateTime.now();

    Alert existing = new Alert();
    existing.setId(1L);
    existing.setName("Test Alert");
    existing.setStatus(ServiceStatus.OK);
    existing.setCreatedAt(now);
    existing.setUrl("http://google.com");

    when(alertRepository.findById(1L)).thenReturn(Optional.of(existing));
    Alert result = alertService.update(resource, 1L);

    verify(alertRepository, atLeast(1)).save(any());
  }

  @Test
  @DisplayName("Should return OK when url is responding")
  void shouldReturnOKWhenUrlIsResponding() throws IOException {
    var now = LocalDateTime.now();
    var alert = new Alert();
    alert.setId(1L);
    alert.setName("Test Alert");
    alert.setStatus(ServiceStatus.OK);
    alert.setCreatedAt(now);
    alert.setUrl("http://ffasfas.fdg");

    ServiceStatus result = alertService.checkUrl(alert);

    assertEquals(ServiceStatus.FAIL, result);
  }
}
