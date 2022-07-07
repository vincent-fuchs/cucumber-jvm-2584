package my.project.dwh.domain.exception;

public class DwhNotFoundException extends RuntimeException {

  public DwhNotFoundException(Long id) {
    super("Dwh with code " + id + " does not exist");
  }
}

