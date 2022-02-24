group := test
java_version := 11
mandrel_version := 22.0
project := debugging-native
quarkus_version := 2.7.2.Final

app_properties = $(project)/src/main/resources/application.properties
pom = $(project)/pom.xml
runner = $(project)-1.0.0-SNAPSHOT-runner

ldd_cmd += ldd
ldd_cmd += /data/target/$(runner)

strings_cmd += strings
strings_cmd += /data/target/$(runner)
strings_cmd += |
strings_cmd += grep
strings_hello += $(strings_cmd)
strings_hello += Hello
strings_vm += $(strings_cmd)
strings_vm += core.VM

readelf_cmd += readelf
readelf_cmd += -SW
readelf_cmd += /data/target/$(runner)

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

run:
	docker run -i --rm -p 8080:8080 $(group)/$(project):1.0.0-SNAPSHOT
.PHONY: run

info: $(app_properties)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative \
		-Dquarkus.native.additional-build-args=--native-image-info
	cd $(project) && cat target/*/reports/target_info_*.txt
	cd $(project) && cat target/*/reports/native_library_info_*.txt
.PHONY: info

verbose: $(app_properties)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative \
		-Dquarkus.native.additional-build-args=--verbose
.PHONY: verbose

combine: $(app_properties)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative \
		-Dquarkus.native.additional-build-args=--native-image-info,--verbose
	cd $(project) && cat target/*/reports/target_info_*.txt
	cd $(project) && cat target/*/reports/native_library_info_*.txt
.PHONY: combine

build-tools:
	docker build -t fedora-tools:v1 ../tools
.PHONY: build-tools

run-tools:
	docker run --privileged -t -i --rm \
		-v $(PWD)/$(project):/data \
		-p 8080:8080 fedora-tools:v1 \
		/bin/bash
.PHONY: run-tools

inspect: $(runner) build-tools
	docker run -i -v $(PWD)/$(project):/data --rm fedora-tools:v1 /bin/bash -c \
		"$(ldd_cmd); $(strings_hello); $(strings_vm); $(readelf_cmd)"
.PHONY: inspect

reports: $(app_properties)
	cd $(project) && \
	./mvnw package -DskipTests -Pnative \
		-Dquarkus.native.enable-reports \
		-Dquarkus.native.additional-build-args=-H:PrintAnalysisCallTreeType=CSV
.PHONY: reports

# Workaround for https://github.com/oracle/graal/issues/4355
fix-links:
	docker run -i -v $(PWD)/$(project):/data --rm fedora-tools:v1 /bin/bash -c \
		"cd target/*-native-image-source-jar/reports \
			&& rm -f call_tree_vm.csv && ln -s call_tree_vm_$(project)-* call_tree_vm.csv \
			&& rm -f call_tree_direct_edges.csv && ln -s call_tree_direct_edges_$(project)-* call_tree_direct_edges.csv \
			&& rm -f call_tree_entry_points.csv && ln -s call_tree_entry_points_$(project)-* call_tree_entry_points.csv \
			&& rm -f call_tree_methods.csv && ln -s call_tree_methods_$(project)-* call_tree_methods.csv \
			&& rm -f call_tree_virtual_edges.csv && ln -s call_tree_virtual_edges_$(project)-* call_tree_virtual_edges.csv \
			&& rm -f call_tree_virtual_methods.csv && ln -s call_tree_virtual_methods_$(project)-* call_tree_virtual_methods.csv \
			&& rm -f call_tree_override_by_edges.csv && ln -s call_tree_override_by_edges_$(project)-* call_tree_override_by_edges.csv"
.PHONY: fix-links

neo4j:
	docker run \
	--detach \
	--rm \
	--name testneo4j \
	-p7474:7474 -p7687:7687 \
	--env NEO4J_AUTH=neo4j/${NEO_PASS} \
	neo4j:latest
.PHONY: neo4j

import:
	cd $(project) && \
	docker cp target/*-native-image-source-jar/reports testneo4j:/var/lib/neo4j/import
	docker cp ../import.cypher testneo4j:/var/lib/neo4j
	docker exec testneo4j bin/cypher-shell -u neo4j -p ${NEO_PASS} -f import.cypher
.PHONY: import

clean-neo4j:
	docker exec testneo4j bin/cypher-shell -u neo4j -p $(NEO_PASS) "MATCH(n) DETACH DELETE n" || true
	docker exec testneo4j bin/cypher-shell -u neo4j -p $(NEO_PASS) "DROP CONSTRAINT unique_vm_id" || true
	docker exec testneo4j bin/cypher-shell -u neo4j -p $(NEO_PASS) "DROP CONSTRAINT unique_method_id" || true
.PHONY: clean-neo4j

hello:
	curl -w '\n' http://localhost:8080/hello
.PHONY: hello

timestamp:
	curl -w '\n' http://localhost:8080/timestamp
.PHONY: timestamp

clean:
	rm -drf $(project)
.PHONY: clean
