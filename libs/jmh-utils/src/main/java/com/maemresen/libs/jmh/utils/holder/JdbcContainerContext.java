package com.maemresen.libs.jmh.utils.holder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.testcontainers.containers.JdbcDatabaseContainer;

@Getter
@RequiredArgsConstructor
public abstract class JdbcContainerContext<T extends JdbcDatabaseContainer<?>> {

  private final T container;

  public String getJdbcUrl() {
    return container.getJdbcUrl();
  }

  public String getUsername() {
    return container.getUsername();
  }

  public String getPassword() {
    return container.getPassword();
  }

  public void start() {
    this.container.start();
  }

  public void close() {
    if (container != null) {
      container.stop();
    }
  }
}