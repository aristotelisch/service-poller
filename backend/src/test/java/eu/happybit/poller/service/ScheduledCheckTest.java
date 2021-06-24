package eu.happybit.poller.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import eu.happybit.poller.domain.ServiceStatus;
import eu.happybit.poller.entity.Alert;
import eu.happybit.poller.repository.AlertRepository;

@ExtendWith(MockitoExtension.class)
class ScheduledCheckTest {

    @InjectMocks
    private ScheduledCheck scheduledCheck;

    @Mock
    private AlertService alertService;

    @Test
    @DisplayName("Polling task creates expected checks and updates result")
    void startPolling() {
        Alert alert = new Alert();
        alert.setName("Test Alert");
        alert.setStatus(ServiceStatus.OK);
        alert.setUrl("http://dfaskdla.fdg");

        List<Alert> allAlerts = List.of(alert);
        when(alertService.findAll()).thenReturn(allAlerts);
        when(alertService.checkUrl(alert)).thenReturn(ServiceStatus.OK);
        scheduledCheck.startPolling();

        verify(alertService,timeout(100).times(1)).checkUrl(alert);
        verify(alertService,timeout(100).times(1)).saveNewStatus(alert, ServiceStatus.OK);
    }
}
