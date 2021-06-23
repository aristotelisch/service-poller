package eu.happybit.poller.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import eu.happybit.poller.entity.Alert;

@Repository
public interface AlertRepository extends PagingAndSortingRepository<Alert, Long> {
  List<Alert> findAll();
}
