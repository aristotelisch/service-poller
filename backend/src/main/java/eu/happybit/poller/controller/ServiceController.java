package eu.happybit.poller.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import eu.happybit.poller.domain.ServiceCreateResource;
import eu.happybit.poller.domain.ServiceResource;
import eu.happybit.poller.domain.ServiceUpdateResource;
import eu.happybit.poller.mapper.AlertMapper;
import eu.happybit.poller.service.AlertService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

  private final AlertService alertService;

  @GetMapping
  public ResponseEntity<List<ServiceResource>> index() {
    return ResponseEntity.ok(AlertMapper.MAPPER.map(alertService.getAllServices()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ServiceResource> show(@PathVariable Long id) {
    return ResponseEntity.ok(AlertMapper.MAPPER.map(alertService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ServiceResource> create(
      @RequestBody @Valid ServiceCreateResource newService) throws URISyntaxException {
    var savedAlert = alertService.save(newService);
    return ResponseEntity.created(new URI("/services/" + savedAlert.getId()))
        .body(AlertMapper.MAPPER.map(savedAlert));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ServiceResource> update(
      @RequestBody @Valid ServiceUpdateResource newService, @PathVariable Long id) {
    return ResponseEntity.ok(AlertMapper.MAPPER.map(alertService.update(newService, id)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    alertService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
