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
package de.openknowledge.samples.javaee8.application;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("protected/ping")
public class PingResource {

  @GET
  @Produces("text/plain")
  @SecurityRequirement(name = "BearerAuth")
  public String ping() {
    return "ping";
  }

  @GET
  @Path("admin")
  @Produces("text/plain")
  @SecurityRequirement(name = "BearerAuth")
  @RolesAllowed("ADMIN")
  public String pingAdmin() {
    return "ping admin";
  }
}
