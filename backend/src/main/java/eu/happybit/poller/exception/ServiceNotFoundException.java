package eu.happybit.poller.exception;

public class ServiceNotFoundException extends RuntimeException {
  public ServiceNotFoundException(Long id) {
    super("Could not find Service with id " + id);
  }
}
