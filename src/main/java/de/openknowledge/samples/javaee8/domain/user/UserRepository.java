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

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.lang3.Validate.notNull;

@ApplicationScoped
class UserRepository implements Serializable {

  private static final Logger LOG = Logger.getLogger(UserRepository.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  public User find(final String username) {
    notNull(username, "username must not be null");

    LOG.log(Level.FINE, "Locating user with username {0}", username);

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> cq = cb.createQuery(User.class);

    Root<User> root = cq.from(User.class);

    cq.select(root).where(cb.equal(root.get(User_.username), username));

    TypedQuery<User> query = entityManager.createQuery(cq);

    return query.getSingleResult();
  }
}
