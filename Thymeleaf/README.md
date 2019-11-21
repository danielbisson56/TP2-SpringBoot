# **Spring boot with Thymeleaf**

<img style="float: left;" src="./Img/ThymeleafIcon.jpg">

## **<u>Préroquis :</u>**

### **<u>Java :</u>**

On doit télécharger et installer java 8 pour Windows X32 ou X64.

<a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">Download java 8u231 </a>

<img style="float: left;" src="./Img/JavaDownloadPage.JPG">

### **<u>Ide Spring boot :</u>**

Il faut télécharger et installer l'ide Spring Tools 4 for Eclipse permettant de simplifier l'utilisation de Spring.

<a href="https://spring.io/tools">Download Spring tools 4</a>

<img style="float: left;" src="./Img/Ide.jpg">

## **<u>Configuration de Spring tools 4:</u>**



## **<u>Programme :</u>**

**<u>pom.xml</u>**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>	
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.1.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>	
	<groupId>com.SpringBoot</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>SpringBoot</name>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-solr</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>				
	</dependencies>	
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>	
</project>
```

<u>**Application.java**</u>

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

**<u>HomeController.java</u>**

```java
package com.example.demo;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public ModelAndView save(@ModelAttribute User user) {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("user-data");
    	modelAndView.addObject("user", user);	
    	return modelAndView;
    }
}
```

### **<u>Erreurs possibles :</u>**

Il ne faut pas nommé le nom de votre classe Controller parce que cela interfère avec le tag @Controller.

**<u>User.java</u>**

```java
package com.example.demo;

public class User {
	String name;
	
	String email;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
```



