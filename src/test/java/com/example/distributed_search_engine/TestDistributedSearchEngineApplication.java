package com.example.distributed_search_engine;

import org.springframework.boot.SpringApplication;

public class TestDistributedSearchEngineApplication {

	public static void main(String[] args) {
		SpringApplication.from(DistributedSearchEngineApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
