package eu.happybit.poller.domain;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ServiceCreateResource {

  @Size(max = 250)
  private String name;

  @Size(max = 100)
  @URL(message = "Invalid url format")
  private String url;
}
