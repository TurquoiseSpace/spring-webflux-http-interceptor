

# Spring WebFlux HTTP Interceptor #


Plug & Play Module

parse & persist HTTP / REST API Calls



### Usage ###


Add jar (ant) or as dependency in pom.xml (maven), to your Java Spring WebFlux, Microservice, or Web Application

	<dependencies>
		<dependency>
			<groupId>com.github.TurquoiseSpace</groupId>
			<artifactId>spring-webflux-http-interceptor</artifactId>
			<version>0.0.5</version>
		</dependency>
	</dependencies>


Add (class level) annotation to the java class which contains main() method of the Web Application

	@ComponentScan(basePackages = { "open.source", "${application.base.package}" })
	public class SpringWebFluxApplication { }


Add (class level) annotation to java bean configuration class of the MongoDB Configuration

	@Configuration
	@EnableMongoAuditing
	@EnableReactiveMongoRepositories(value = { "open.source.exchange.repository.asynchronous", "<actual.application.base.package.containg.asynchronous.repositories>" })
	public class MongoReactiveConfig { }



### Developer Setup ###


Clone via HTTPS

	https://github.com/TurquoiseSpace/spring-webflux-http-interceptor.git


Clone via SSH

	git@github.com:TurquoiseSpace/spring-webflux-http-interceptor.git


Clone via GitHub CLI

	gh repo clone TurquoiseSpace/spring-webflux-http-interceptor



### Collaborate ###


Set User Name & Email

	git config user.name "Prakhar Makhija"

	git config user.email "matcdac@gmail.com"


Remember Git Remote Credentials

	git config credential.helper store


Forget Git Remote Credentials

	git config  --unset credential.helper



### Coding Conventions ###


`master` is the stable release branch

In order to contribute, checkout another branch or fork the repository

Feel free raise a Merge / Pull Request

If urgent, drop an email !



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

	gpg -ab target/spring-webflux-http-interceptor-0.0.5.jar


Verify

	gpg --verify pom.xml.asc

	gpg --verify target/spring-webflux-http-interceptor-0.0.5.jar.asc



### Maven Central OSSRH Sonatype ###


Package

	mvn -B package --file pom.xml


Publish

	mvn deploy -e -X

	mvn nexus-staging:release --settings /home/mafia/.m2/setting.xml --global-settings /space/tools/apache-maven-3.6.3/conf/settings.xml -DstagingRepositoryId=ossrh

	mvn deploy --settings /home/mafia/.m2/setting.xml --global-settings /space/tools/apache-maven-3.6.3/conf/settings.xml



