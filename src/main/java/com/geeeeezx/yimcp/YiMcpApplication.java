package com.geeeeezx.yimcp;

import com.geeeeezx.yimcp.service.YiService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YiMcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(YiMcpApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider divinationTools(YiService yiService) {
		return  MethodToolCallbackProvider.builder().toolObjects(yiService).build();
	}

}
