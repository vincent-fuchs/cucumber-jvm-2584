package my.project.dwh.repository.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.project.dwh.domain.model.Dwh;

@Table(name = "T_DWH", schema = "TEST")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DwhEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "TEST_SEQ")
  @SequenceGenerator(name="TEST_SEQ",sequenceName="TEST.SEQ_T_DWH", allocationSize=1, initialValue = 1)
  @Column(name = "TECH_ID")
  private Long techId;

  @Column(name = "CODE")
  private Long code;

  @Column(name = "DESCRIPTION")
  private String description;

  public Dwh toModel() {
    return Dwh.builder().code(code).description(description).build();
  }
}
