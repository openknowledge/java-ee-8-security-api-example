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

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Exception to be thrown when the password does not match.
 */
public class WrongPasswordException extends Exception {

  private final String username;

  /**
   * @param username the given username.
   * @throws NullPointerException
   */
  public WrongPasswordException(final String username) {
    super();
    this.username = notNull(username, "username must not be null");
  }

  @Override
  public String getMessage() {
    return String.format("Wrong password for user %s", username);
  }

  public String getUsername() {
    return username;
  }
}
