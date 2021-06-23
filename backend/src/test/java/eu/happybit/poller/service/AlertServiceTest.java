package eu.happybit.poller.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import eu.happybit.poller.domain.ServiceCreateResource;
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
}
