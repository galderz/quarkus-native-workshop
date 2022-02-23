project := debugging-native
pom = $(project)/pom.xml
quarkus_version := 2.7.1.Final

$(pom):
	mvn io.quarkus.platform:quarkus-maven-plugin:$(quarkus_version):create \
		-DprojectGroupId=org.acme \
		-DprojectArtifactId=$(project) \
		-Dextensions="resteasy,container-image-docker"

clean:
	rm -drf $(project)
.PHONY:clean
