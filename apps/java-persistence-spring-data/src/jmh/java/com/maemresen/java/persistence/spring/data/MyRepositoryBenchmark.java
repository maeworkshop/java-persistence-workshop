package com.maemresen.java.persistence.spring.data;

import com.maemresen.java.persistence.spring.data.entity.Circuit;
import com.maemresen.java.persistence.spring.data.repository.CircuitRepository;
import com.maemresen.libs.jmh.utils.BaseBenchmark;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
public class MyRepositoryBenchmark extends BaseBenchmark {

  private static final int FORK_COUNT = 1;
  private static final int WARMUP_COUNT = 2;
  private static final int ITERATION_COUNT = 5;


  @Override
  protected Class<?> getSpringApplicationClass() {
    return Application.class;
  }

  @Override
  protected void beforeSetup() {
    System.setProperty("spring.datasource.url", jdbcContainerContext.getJdbcUrl());
    System.setProperty("spring.datasource.username", jdbcContainerContext.getUsername());
    System.setProperty("spring.datasource.password", jdbcContainerContext.getPassword());
  }

  @Override
  protected void afterSetup() {
  }

  @Benchmark
  @BenchmarkMode(Mode.All)
  @Fork(value = FORK_COUNT)
  @Warmup(iterations = WARMUP_COUNT)
  @Measurement(iterations = ITERATION_COUNT)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void findAll() throws InterruptedException {
    List<Circuit> byIdNotIn = context.getBean(CircuitRepository.class).findByCircuitIdNotIn(Set.of(
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        80,
        24,
        25,
        26,
        27,
        28,
        29,
        30,
        31,
        32,
        33,
        34,
        35,
        36,
        37,
        38,
        39,
        40,
        41,
        42,
        43,
        44,
        45,
        46,
        47,
        48,
        49,
        50,
        51,
        52,
        53,
        54,
        55,
        56,
        57,
        58,
        59,
        60,
        61,
        62,
        63,
        64,
        65,
        66,
        67,
        68,
        69,
        70,
        71,
        73,
        75,
        76,
        77,
        78,
        79
    ));
    System.out.println(byIdNotIn.stream().map(Circuit::getCircuitId).toList());
  }
}