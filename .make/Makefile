group := test
java_version := 11
mandrel_version := 22.0
project := debugging-native
quarkus_version := 2.7.2.Final

app_properties = $(project)/src/main/resources/application.properties
pom = $(project)/pom.xml
runner = $(project)/target/$(project)-1.0.0-SNAPSHOT-runner

run: $(runner)
	docker run -i --rm -p 8080:8080 $(group)/$(project):1.0.0-SNAPSHOT

$(runner): $(app_properties)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative

$(app_properties): $(pom)
	echo "quarkus.native.container-build=true" >> $@
	echo "quarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel:${mandrel_version}-java$(java_version)" >> $@
	echo "quarkus.container-image.build=true" >> $@
	echo "quarkus.container-image.group=$(group)" >> $@

$(pom):
	mvn io.quarkus.platform:quarkus-maven-plugin:$(quarkus_version):create \
		-DprojectGroupId=org.acme \
		-DprojectArtifactId=$(project) \
		-Dextensions="resteasy,container-image-docker"

get:
	curl -w '\n' http://localhost:8080/hello
.PHONY: get

clean:
	rm -drf $(project)
.PHONY: clean
