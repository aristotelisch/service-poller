package eu.happybit.poller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import eu.happybit.poller.domain.ServiceResource;
import eu.happybit.poller.entity.Alert;

class AlertMapperTest {

    AlertMapper mapper;

    @BeforeEach
    void setUp() {
        mapper  = AlertMapper.MAPPER;
    }

    @Test
    @DisplayName("Should map all fields from Alert to ServiceResource")
    void shouldMapFromAlertAllFields() {
        Alert alert = new Alert();
        alert.setName("Service Name Example");
        alert.setUrl("http://domain.com");
        alert.setCreatedAt(LocalDateTime.now());
        alert.setUpdatedAt(LocalDateTime.now());

        ServiceResource serviceStatus = mapper.map(alert);

        assertNotNull(serviceStatus);
        assertEquals(alert.getStatus(), serviceStatus.getStatus());
        assertEquals(alert.getName(), serviceStatus.getName());
        assertEquals(alert.getUrl(), serviceStatus.getUrl());
        assertEquals(alert.getCreatedAt(), serviceStatus.getCreatedAt());
        assertEquals(alert.getUpdatedAt(), serviceStatus.getUpdatedAt());
    }
}
