package my.project.dwh.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import my.project.dwh.domain.model.Dwh;
import my.project.dwh.domain.model.DwhInfo;
import my.project.dwh.domain.port.RequestDwh;

@RestController
@RequestMapping("/api/v1/dwhs")
public class DwhResource {

  private RequestDwh requestDwh;

  public DwhResource(RequestDwh requestDwh) {
    this.requestDwh = requestDwh;
  }

  @GetMapping
  public ResponseEntity<DwhInfo> getDwhs() {
    return ResponseEntity.ok(requestDwh.getDwhs());
  }

  @GetMapping("/{code}")
  public ResponseEntity<Dwh> getDwhByCode(@PathVariable Long code) {
    return ResponseEntity.ok(requestDwh.getDwhByCode(code));
  }
}
