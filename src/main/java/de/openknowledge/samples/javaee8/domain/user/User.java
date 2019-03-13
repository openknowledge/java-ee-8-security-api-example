/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package de.openknowledge.samples.javaee8.domain.user;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * An entity that represents an user;
 */
@Entity
@Table(name = "TAB_USER")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  @TableGenerator(name = "TABLE_GEN", table = "SEQUENCE_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "USR_SEQ", initialValue = 1000, allocationSize = 1)
  @Column(name = "USR_ID", nullable = false)
  private Long id;

  @Column(name = "USR_USERNAME", nullable = false)
  private String username;

  @Column(name = "USR_PASSWORD", nullable = false)
  private String password;

  @Column(name = "USR_FIRST_NAME", nullable = false)
  private String firstName;

  @Column(name = "USR_LAST_NAME", nullable = false)
  private String lastName;

  @ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "TAB_ROLE", joinColumns = @JoinColumn(name = "ROL_USR_ID"))
  @Column(name = "ROL_NAME", nullable = false)
  private Set<UserRoles> roles;

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public Set<UserRoles> getRoles() {
    return Collections.unmodifiableSet(roles);
  }

  public void setRoles(final Set<UserRoles> roles) {
    this.roles = roles;
  }

  @Override
  public int hashCode() {
    if (id == null) {
      return new Object().hashCode();
    } else {
      return id.hashCode();
    }
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }
    if (object == null
      || !(object.getClass().isAssignableFrom(getClass()) && getClass().isAssignableFrom(object.getClass()))) {
      return false;
    }
    User user = (User)object;
    return getId() != null && getId().equals(user.getId());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "#" + id;
  }
}
