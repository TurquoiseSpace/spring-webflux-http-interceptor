

# Spring WebFlux HTTP Interceptor #


Plug & Play Module

[![Maven Central](https://img.shields.io/maven-central/v/com.github.TurquoiseSpace/spring-webflux-http-interceptor.svg?label=Maven%20Central)](https://repo1.maven.org/maven2/com/github/TurquoiseSpace/spring-webflux-http-interceptor/)

[![Java Doc](https://javadoc.io/badge2/com.github.TurquoiseSpace/spring-webflux-http-interceptor/javadoc.svg)](https://javadoc.io/doc/com.github.TurquoiseSpace/spring-webflux-http-interceptor)

[Sonatype](https://search.maven.org/artifact/com.github.TurquoiseSpace/spring-webflux-http-interceptor)

[MVN Repository](https://mvnrepository.com/artifact/com.github.TurquoiseSpace/spring-webflux-http-interceptor)

[Maven Central Search](https://search.maven.org/search?q=g:%22com.github.TurquoiseSpace%22%20AND%20a:%22spring-webflux-http-interceptor%22)



### Description ###


Parse Asynchronous HTTP / Non Blocking REST API Calls
& Persist in MongoDB



### Usage ###


Add jar (ant) or as dependency in pom.xml (maven), to your Java Spring WebFlux, Microservice, or Web Application

	<dependencies>
		<dependency>
			<groupId>com.github.TurquoiseSpace</groupId>
			<artifactId>spring-webflux-http-interceptor</artifactId>
			<version>0.0.8</version>
		</dependency>
	</dependencies>


Add (class level) annotation to the java class which contains main() method of the Web Application

	@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
	@ComponentScan(basePackages = { "open.source", "${application.base.package}" })
	public class SpringWebFluxApplication { }


Add (class level) annotation to java bean configuration class of the MongoDB Configuration

	@Configuration
	@EnableMongoAuditing
	@EnableReactiveMongoRepositories(value = { "open.source.exchange.repository.asynchronous", "${application.asynchronous.repositories.package}" })
	public class MongoReactiveConfig { }



### How to verify if it working ###


After adding the above dependency and embedding the specified changes, hit an API of your application

query MongoDB using a client of your choice (CLI / Studio3T / Robo3T), for documents in the below collections

	applicationcontext

	informationexchange



### Up Next - Improvements to be Picked ###


Persist

- Request Body (Issue : One subscriber only)

- Response Body (Hidden : within DataBufferFactory.java)

- Multipart Data



### Contribute (Collaborate) - Developer Setup ###


Pre-requisite Tools (Softwares)

	JDK 1.8
	
	Apache Maven
	
	Git CLI
	
	MongoDB Server
	
	Robo3T
	
	Java IDE - Eclipse / intelliJ
	
	Postman / cURL


Clone via

`HTTPS`

	git clone https://github.com/TurquoiseSpace/spring-webflux-http-interceptor.git

`SSH`

	git clone git@github.com:TurquoiseSpace/spring-webflux-http-interceptor.git

`GitHub CLI`

	gh repo clone TurquoiseSpace/spring-webflux-http-interceptor


Set User Name & Email

	git config user.name "Prakhar Makhija"

	git config user.email "matcdac@gmail.com"


Remember Git Remote Credentials

	git config credential.helper store


Forget Git Remote Credentials

	git config  --unset credential.helper



### Help - Starting off with "Spring WebFlux HTTP Interceptor" ###


The actual code is located inside

	./src/main/java/

with the base package as

	open.source.exchange

and child packages (shows the call heirarchy from top to bottom)
and in java files

	interceptor
		ReactiveApiInterceptor.java
	service
		InformationExchangeService.java
		ParserHelper.java
	utility.asynchronous
		MonoCallSynchronousExecutor.java
	repository.asynchronous
		InformationExchangeRepoAsync.java
	parser
		ApplicationContextParser.java
		WebSessionParser.java
		LocaleContextParser.java
		ServerHttpRequestParser.java
		ServerHttpResponseParser.java
		PrincipalParser.java
		PartParser.java
	entity
		InformationExchange.java
	model
		Information.java
	enumeration
		ExchangeInformationType.java
		TimeEvent.java
		TimeUnit.java



### Coding Conventions ###


`master` is the stable release branch

In order to contribute, checkout another branch or fork the repository

Feel free raise a Merge / Pull Request

If its urgent, kindly drop an email



### Binaries ###


Compile & Build

	mvn -Dmaven.artifact.threads=25 clean eclipse:eclipse -DdownloadSources=true dependency:go-offline install --settings /home/mafia/.m2/setting.xml --global-settings /space/tools/apache-maven-3.6.3/conf/settings.xml



### PGP Signature ###


Check Version

	gpg --version


Setup

	gpg --gen-key


List Keys

	gpg --list-keys

	gpg --list-secret-keys


Sync

	gpg --keyserver http://keyserver.ubuntu.com --send-keys <key>

	gpg --keyserver http://keyserver.ubuntu.com --recv-keys <key>



### Signing (integrated functionality via maven plugin) ###


Sign

	gpg -ab pom.xml

	gpg -ab target/spring-webflux-http-interceptor-0.0.8.jar


Verify

	gpg --verify pom.xml.asc

	gpg --verify target/spring-webflux-http-interceptor-0.0.8.jar.asc



### Maven Central OSSRH Sonatype ###


Package

	mvn -B package --file pom.xml


Publish

	mvn deploy -e -X

	mvn nexus-staging:release --settings /home/mafia/.m2/setting.xml --global-settings /space/tools/apache-maven-3.6.3/conf/settings.xml -DstagingRepositoryId=ossrh

	mvn deploy --settings /home/mafia/.m2/setting.xml --global-settings /space/tools/apache-maven-3.6.3/conf/settings.xml



