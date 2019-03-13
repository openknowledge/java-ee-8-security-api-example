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
package de.openknowledge.samples.javaee8.application.auth;

import java.util.Date;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * DTO that provides an JWT authorization token and additional information as response for a successful authentication.
 */
public class AuthorizationToken {

  private String token;

  private String token_type = "Bearer";

  private String subject;

  private String issuer;

  private long issued_at;

  private long expires_at;

  private long expires_in;

  public AuthorizationToken() {
  }

  public AuthorizationToken(final String token, final String subject, final String issuer, final Date issuedAt, final Date expiresAt) {
    this.token = notNull(token, "token must not be null");
    this.subject = notNull(subject, "subject must not be null");
    this.issuer = notNull(issuer, "issuer must not be null");
    this.issued_at = notNull(issuedAt, "issuedAt must not be null").getTime();
    this.expires_at = notNull(expiresAt, "expiresAt must not be null").getTime();
    this.expires_in = (expiresAt.getTime() - issuedAt.getTime()) / 1000;
  }

  public String getToken() {
    return token;
  }

  public String getToken_type() {
    return token_type;
  }

  public String getSubject() {
    return subject;
  }

  public String getIssuer() {
    return issuer;
  }

  public long getIssued_at() {
    return issued_at;
  }

  public long getExpires_at() {
    return expires_at;
  }

  public long getExpires_in() {
    return expires_in;
  }

  @Override
  public String toString() {
    return "AuthorizationToken{" +
      "token='" + token + '\'' +
      ", token_type='" + token_type + '\'' +
      ", subject='" + subject + '\'' +
      ", issuer='" + issuer + '\'' +
      ", issued_at=" + issued_at +
      ", expires_at=" + expires_at +
      ", expires_in=" + expires_in +
      "} " + super.toString();
  }
}
