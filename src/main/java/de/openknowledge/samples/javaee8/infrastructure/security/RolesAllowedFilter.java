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

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;

/**
 * Implementation for https://github.com/eclipse-ee4j/jaxrs-api/issues/563
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class RolesAllowedFilter implements ContainerRequestFilter {

  @Context
  private ResourceInfo resourceInfo;

  @Inject
  private SecurityContext securityContext;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    // see https://github.com/eclipse-ee4j/jaxrs-api/issues/563
    PermitAll permitAll = resourceInfo.getResourceClass().getAnnotation(PermitAll.class);
    if (resourceInfo.getResourceMethod().getAnnotation(PermitAll.class) != null) {
      permitAll = resourceInfo.getResourceMethod().getAnnotation(PermitAll.class);
    }
    if (permitAll != null) {
      // PermitAll overrides RolesAllowed
      return;
    }

    RolesAllowed rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
    if (resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class) != null) {
      rolesAllowed = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class);
    }

    if (rolesAllowed != null && Arrays.stream(rolesAllowed.value()).noneMatch(s -> securityContext.isCallerInRole(s))) {
      requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
    }
  }
}
