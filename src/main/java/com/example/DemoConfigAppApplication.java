package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// If -D option is provided then it takes precedence. (-Dconfiguration.projectName=Spring)
// Else if env variable is setup then configuration would be set to it. (export CONFIGURATION_PROJECTNAME=BOOT)
// Else if application.yml or application.properties is present in the folder where the jar is present then the value is taken from that application.yml
// Otherwise, use the default from application.yml

@SpringBootApplication
@EnableConfigurationProperties
public class DemoConfigAppApplication {
	
	@Value("${configuration.projectName}")
	void setProjectName(String projectName) {
		System.out.println("Project Name from config file: " + projectName);
	}
	
	@Autowired
	void printEnv(Environment env) {
		System.out.println("Project Name from Environment: " + env.getProperty("configuration.projectName"));
	}
	
	@Autowired
	void configurationPojo(ConfigurationProjectProperties cp) {
		System.out.println("Project Name from POJO: " + cp.getProjectName());		
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoConfigAppApplication.class, args);
	}

	@Component
	@ConfigurationProperties("configuration")
	public class ConfigurationProjectProperties {
		private String projectName;

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			if (projectName == "BOOT")
				this.projectName = "Hello";
			else
				this.projectName = projectName;
		}	

	}

	
}
