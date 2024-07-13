package com.maemresen.java.persistence.spring.data;

import com.maemresen.java.persistence.spring.data.repository.CustomerRepository;
import com.maemresen.libs.jmh.utils.BaseBenchmark;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class MyRepositoryBenchmark extends BaseBenchmark {

  private CustomerRepository customerRepository;

  @Override
  protected Class<?> getSpringApplicationClass() {
    return Application.class;
  }

  @Override
  protected void postSetup() {
    customerRepository = context.getBean(CustomerRepository.class);
  }

  @Benchmark
  @BenchmarkMode(Mode.SingleShotTime)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void measureQueryExecutionTime() {
    customerRepository.findAll(); // Adjust the ID as needed
  }
}