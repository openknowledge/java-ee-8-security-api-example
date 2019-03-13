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
package de.openknowledge.samples.javaee8.infrastructure.security;

import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import de.openknowledge.samples.javaee8.domain.user.AuthenticationService;
import de.openknowledge.samples.javaee8.domain.user.User;
import de.openknowledge.samples.javaee8.domain.user.UserNotFoundException;
import de.openknowledge.samples.javaee8.domain.user.UserService;
import de.openknowledge.samples.javaee8.domain.user.WrongPasswordException;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

public class UserIdentityStore implements IdentityStore {

  private static final Logger LOGGER = Logger.getLogger(UserIdentityStore.class.getName());

  @Inject
  private UserService userService;

  @Inject
  private AuthenticationService authenticationService;

  @Override
  public CredentialValidationResult validate(Credential credential) {
    if (credential instanceof UsernamePasswordCredential) {
      String caller = ((UsernamePasswordCredential)credential).getCaller();
      try {
        User user = authenticationService.authenticate(caller, ((UsernamePasswordCredential)credential).getPasswordAsString());

        return new CredentialValidationResult(caller, user.getRoles().stream().map(Enum::toString).collect(Collectors.toSet()));
      } catch (UserNotFoundException e) {
        LOGGER.log(Level.WARNING, "User {0} not found", caller);
      } catch (WrongPasswordException e) {
        LOGGER.log(Level.WARNING, "Wrong password for user {0}", caller);
      }
      return INVALID_RESULT;
    }

    if (credential instanceof CallerOnlyCredential) {
      String caller = ((CallerOnlyCredential)credential).getCaller();
      try {
        User user = userService.find(caller);

        return new CredentialValidationResult(caller, user.getRoles().stream().map(Enum::toString).collect(Collectors.toSet()));
      } catch (UserNotFoundException e) {
        LOGGER.log(Level.WARNING, "User {0} not found", caller);
        return INVALID_RESULT;
      }
    }

    return NOT_VALIDATED_RESULT;
  }
}
