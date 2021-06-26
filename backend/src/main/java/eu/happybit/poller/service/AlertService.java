package eu.happybit.poller.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import eu.happybit.poller.domain.ServiceCreateResource;
import eu.happybit.poller.domain.ServiceStatus;
import eu.happybit.poller.domain.ServiceUpdateResource;
import eu.happybit.poller.entity.Alert;
import eu.happybit.poller.exception.ServiceNotFoundException;
import eu.happybit.poller.mapper.AlertMapper;
import eu.happybit.poller.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Business logic for User created alerts
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlertService {
  public static final int CONNECT_TIMEOUT = 4500;
  public static final int READ_TIMEOUT = 4500;

  private final AlertRepository alertRepository;

  /**
   * Get all alerts from database.
   * @return a List of Alert objects
   */
  public List<Alert> findAll() {
    return alertRepository.findAll();
  }

  /**
   * Save a new alert
   * @param newService The new Service request object
   * @return The saved alert
   */
  public Alert save(ServiceCreateResource newService) {
    return alertRepository.save(AlertMapper.MAPPER.map(newService));
  }

  /**
   * Delete an alert by database id.
   * @param id The unique database id of an alert
   */
  public void delete(Long id) {
    Alert alert = alertRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));
    alertRepository.delete(alert);
  }

  /**
   * Checks the url of the given alert for availability status.
   * @param alert The alert database object
   * @return a ServiceStatus enum. Can be OK or FAIL
   */
  public ServiceStatus checkUrl(Alert alert) {
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) new URL(alert.getUrl()).openConnection();
      connection.setConnectTimeout(CONNECT_TIMEOUT);
      connection.setReadTimeout(READ_TIMEOUT);
      connection.setRequestMethod(HttpMethod.HEAD.name());
      return connection.getResponseCode() == HttpStatus.OK.value()
          ? ServiceStatus.OK
          : ServiceStatus.FAIL;
    } catch (IOException ex) {
      log.error(ex.getLocalizedMessage());
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
    return ServiceStatus.FAIL;
  }

  /**
   * Update an existing alert status with the given one
   * @param alert An existing alert
   * @param status The new status
   * @return The new updated alert object
   */
  public Alert saveNewStatus(Alert alert, ServiceStatus status) {
    log.info(">>>> Result for {} was {}", alert.getUrl(), status);
    alert.setStatus(status);
    return alertRepository.save(alert);
  }

  /**
   * Update a limited set of fields of an alert object.
   * It is used by the UI in order to update the name and/or the url
   * @param newService The newService request object
   * @param id The alert database id
   * @return the newly saved alert object
   */
  public Alert update(ServiceUpdateResource newService, Long id) {
    Alert alert = AlertMapper.MAPPER.map(newService);
    return alertRepository
        .findById(id)
        .map(
            existingAlert -> {
              alert.setId(id);
              alert.setCreatedAt(existingAlert.getCreatedAt());
              return alertRepository.save(alert);
            })
        .orElseGet(() -> alertRepository.save(alert));
  }

  /**
   * Find an alert by database id.
   * @param id The unique database id
   * @return The Alert object else throw a ServiceNotFoundException
   */
  public Alert findById(Long id) {
    return alertRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));
  }
}
