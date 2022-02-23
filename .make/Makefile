project := debugging-native
pom = $(project)/pom.xml
quarkus_version := 2.7.1.Final

$(pom):
	mvn io.quarkus.platform:quarkus-maven-plugin:$(quarkus_version):create \
		-DprojectGroupId=org.acme \
		-DprojectArtifactId=$(project) \
		-Dextensions="resteasy"
