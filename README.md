

# Spring WebFlux HTTP Interceptor #


Plug & Play Module

parse & persist HTTP / REST API Calls



### Usage ###


Add dependency as jar (ant) or in pom.xml (maven), to your Java microservice or web application

	<properties>
		<turquoise.space.version>0.0.2</turquoise.space.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>space.turquoise</groupId>
			<artifactId>spring-webflux-http-interceptor</artifactId>
			<version>${turquoise.space.version}</version>
		</dependency>
	</dependencies>


Add annotation to the java class which contains main() method

	@ComponentScan(basePackages = { "open.source", "<actual.application.base.package.containg.this.class.with.main.method>" })


Add the following java bean config class to your project

	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.data.mongodb.MongoDbFactory;
	import org.springframework.data.mongodb.config.EnableMongoAuditing;
	import org.springframework.data.mongodb.core.convert.DbRefResolver;
	import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
	import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
	import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
	import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
	@Configuration
	@EnableMongoAuditing
	@EnableReactiveMongoRepositories(value = { "open.source.exchange.repository.asynchronous", "<actual.application.base.package.containg.asynchronous.repositories>" })
	public class MongoReactiveConfig {
		// config which just mentions based on MongoReactiveDataAutoConfiguration
		// https://stackoverflow.com/questions/39785004/mongodb-escape-dots-in-map-key
		@Bean
	    public MappingMongoConverter mongoConverter(MongoDbFactory mongoFactory, MongoMappingContext mongoMappingContext) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoFactory);
		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		mongoConverter.setMapKeyDotReplacement("-");
		return mongoConverter;
	    }
	}



### Developer Setup ###


Clone via HTTPS

	https://github.com/TurquoiseSpace/spring-webflux-http-interceptor.git


Clone via SSH

	git@github.com:TurquoiseSpace/spring-webflux-http-interceptor.git


Clone via GitHub CLI

	gh repo clone TurquoiseSpace/spring-webflux-http-interceptor



### Branch ###


`master` is the stable release branch



### Collaborate ###


Set User Name & Email

	git config user.name "Prakhar Makhija"

	git config user.email "matcdac@gmail.com"


Remember Credentials

	git config credential.helper store


Forget Credentials

	git config  --unset credential.helper



### Binaries ###


Compile & Build

	mvn -Dmaven.artifact.threads=25 clean eclipse:eclipse -DdownloadSources=true dependency:go-offline install


Package

	mvn -B package --file pom.xml


Publish

	mvn deploy -e -X

	mvn deploy --settings /home/mafia/.m2/setting.xml --global-settings /space/tools/apache-maven-3.6.3/conf/settings.xml



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



### Signing ###


Sign

	gpg -ab pom.xml

	gpg -ab target/spring-webflux-http-interceptor-0.0.3.jar


Verify

	gpg --verify pom.xml.asc

	gpg --verify target/spring-webflux-http-interceptor-0.0.3.jar.asc



