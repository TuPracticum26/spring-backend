package com.sap.documentmgn;

import com.sap.documentmgn.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestSecurityConfig.class)
class DocumentmgnApplicationTests {

	@Test
	void contextLoads() {
	}

}
