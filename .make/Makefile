group := test
java_version := 11
mandrel_version := 22.0
project := debugging-native
quarkus_version := 2.7.2.Final

app_properties = $(project)/src/main/resources/application.properties
pom = $(project)/pom.xml
runner = $(target)/$(project)-1.0.0-SNAPSHOT-runner
target = $(project)/target

ldd_cmd += ldd
ldd_cmd += /data/$(runner)

strings_cmd += strings
strings_cmd += /data/$(runner)
strings_cmd += |
strings_cmd += grep
strings_hello += $(strings_cmd)
strings_hello += Hello
strings_vm += $(strings_cmd)
strings_vm += core.VM

readelf_cmd += readelf
readelf_cmd += -SW
readelf_cmd += /data/$(runner)

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

info: $(pom)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative \
		-Dquarkus.native.additional-build-args=--native-image-info
	cat $(target)/*/reports/target_info_*.txt
	cat $(target)/*/reports/native_library_info_*.txt
.PHONY: info

verbose: $(pom)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative \
		-Dquarkus.native.additional-build-args=--verbose
.PHONY: verbose

combine: $(pom)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative \
		-Dquarkus.native.additional-build-args=--native-image-info,--verbose
	cat $(target)/*/reports/target_info_*.txt
	cat $(target)/*/reports/native_library_info_*.txt
.PHONY: combine

build-tools:
	docker build -t fedora-tools:v1 ../tools
.PHONY: build-tools

run-tools:
	docker run --privileged -t -i --rm \
		-v $(PWD)/debugging-native:/data \
		-p 8080:8080 fedora-tools:v1 \
		/bin/bash
.PHONY: run-tools

inspect: $(runner) build-tools
	docker run -i -v $(PWD):/data --rm fedora-tools:v1 /bin/bash -c \
		"$(ldd_cmd); $(strings_hello); $(strings_vm); $(readelf_cmd)"
.PHONY: inspect

reports: $(pom)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative \
		-Dquarkus.native.enable-reports
.PHONY: reports

neo4j:
	docker run \
	--detach \
	--rm \
	--name testneo4j \
	-p7474:7474 -p7687:7687 \
	--env NEO4J_AUTH=neo4j/${NEO_PASS} \
	neo4j:latest
.PHONY: neo4j

get:
	curl -w '\n' http://localhost:8080/hello
.PHONY: get

clean:
	rm -drf $(project)
.PHONY: clean
