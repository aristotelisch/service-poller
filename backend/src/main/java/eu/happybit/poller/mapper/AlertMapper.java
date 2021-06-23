package eu.happybit.poller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import eu.happybit.poller.domain.ServiceCreateResource;
import eu.happybit.poller.domain.ServiceResource;
import eu.happybit.poller.domain.ServiceUpdateResource;
import eu.happybit.poller.entity.Alert;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlertMapper {

  AlertMapper MAPPER = Mappers.getMapper(AlertMapper.class);

  Alert map(ServiceCreateResource serviceCreateResource);
  Alert map(ServiceUpdateResource serviceUpdateResource);

  ServiceResource map(Alert alert);

  List<ServiceResource> map(List<Alert> alerts);
}
