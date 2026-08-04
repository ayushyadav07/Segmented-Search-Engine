package com.example.distributed_search_engine;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DistributedSearchEngineApplicationTests {

	@Test
	void contextLoads() {
	}

}
