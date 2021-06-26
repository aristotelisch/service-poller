package eu.happybit.poller.controller;

import java.io.IOException;
import java.nio.file.Files;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import eu.happybit.poller.exception.OpenApiException;
import lombok.extern.slf4j.Slf4j;

/**
 * Serve openapi specification
 */
@Slf4j
@RestController
@RequestMapping("/actuator/openapi")
public class OpenApiController {

  final ResourceLoader resourceLoader;

  private static final String OPENAPI_PATH = "openapi/openapi.yaml";

  public OpenApiController(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  /**
   * Get the openapi specification yml file
   * @return The openapi specification file
   * @throws IOException
   */
  @GetMapping
  @ResponseBody
  public String show() throws IOException {
    var resource = new ClassPathResource(OPENAPI_PATH).getFile();

    if (!resource.exists()) {
      log.error("Openapi specification not found");
      throw new OpenApiException("Openapi specification not found");
    }

    return new String(Files.readAllBytes(resource.toPath()));
  }
}
