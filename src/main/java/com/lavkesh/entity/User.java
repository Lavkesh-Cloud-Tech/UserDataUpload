package com.lavkesh.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "l_user", schema = "flyway")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"userId"})
@ToString
public class User {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "first_name")
  private String firstName = "";

  @Column(name = "middle_name")
  private String middleName = "";

  @Column(name = "last_name")
  private String lastName = "";

  @Column(name = "email")
  private String email;

  @Column(name = "role")
  private String role;

  @Column(name = "active")
  private boolean active;
}
