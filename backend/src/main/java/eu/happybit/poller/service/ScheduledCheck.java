package eu.happybit.poller.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import eu.happybit.poller.entity.Alert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

/**
 * Sceduled Task the sets up the url checks
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledCheck {
  private final AlertService alertService;


    /**
     * Every 10000 miliseconds (10 seconds) get the available alerts and spawn new availability checks
     */
  @Scheduled(fixedRate = 10000)
  @SchedulerLock(name = "Web service monitor task", lockAtMostFor = "20s", lockAtLeastFor = "10s")
  void startPolling() {
    log.info(">>> Running polling checks...");
    List<Alert> alerts = alertService.findAll();
    alerts.forEach(this::startAsyncCheck);
    log.info(">>> Finished firing polling checks.");
  }

    /**
     * Given an alert object initiate an asynchronous check of its availability.
     * @param alert
     */
  protected void startAsyncCheck(Alert alert) {
    CompletableFuture.supplyAsync(
            () -> {
              log.info(">>>> Checking {}", alert.getUrl());
              return alertService.checkUrl(alert);
            })
        .thenApply(
            status -> {
              log.info("result is {}", status);
              return alertService.saveNewStatus(alert, status);
            });
  }
}
