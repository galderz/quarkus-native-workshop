# Quarkus Native Workshop

## Prerequisites for the workshop
(Estimate time : 20 minutes)

Please find below the list of tools needed for this workshop.
It is strongly advised to install as much as possible prior to the workshop.

* Git >= 1.8.3.1 advised
* A Java IDE like Eclipse or at least a text editor like Vim
* JDK 11 installed
* Maven >= 3.6.3 advised, possibly with JAVA_HOME configured appropriately
* A favorite HTTP client like curl
* A container runtime, this could be Docker >= 1.13.1 or Podman.

Let's check whether some prerequisites are already installed on your machine, for instance like below:

```
[dev@quarkus-native-workshop]$ git --version
git version 2.34.1

[dev@quarkus-native-workshop]$ java --version
openjdk 11.0.14.1 2022-02-08
OpenJDK Runtime Environment Temurin-11.0.14.1+1 (build 11.0.14.1+1)
OpenJDK 64-Bit Server VM Temurin-11.0.14.1+1 (build 11.0.14.1+1, mixed mode)

[dev@quarkus-native-workshop]$ mvn --version
Apache Maven 3.8.4 (9b656c72d54e5bacbed989b64718c159fe39b537)
Maven home: /opt/boot-maven
Java version: 11.0.14.1, vendor: Eclipse Adoptium, runtime: /opt/adopt-11.0.14.1+1/Contents/Home
Default locale: en_GB, platform encoding: UTF-8
OS name: "mac os x", version: "12.2.1", arch: "x86_64", family: "mac"

[dev@quarkus-native-workshop]$ curl --version
curl 7.77.0 (x86_64-apple-darwin21.0) libcurl/7.77.0 (SecureTransport) LibreSSL/2.8.3 zlib/1.2.11 nghttp2/1.42.0

[dev@quarkus-native-workshop]$ docker --version
Docker version 20.10.11, build dea9396
```

Describing all the ways to have those prerequisites installed is beyond the scope of this workshop, still some useful links could be found below:
* [https://maven.apache.org/install.html](https://maven.apache.org/install.html)
* [https://sdkman.io/usage](https://sdkman.io/usage)
* [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)
* [https://medium.com/@gayanper/sdkman-on-windows-661976238042](https://medium.com/@gayanper/sdkman-on-windows-661976238042)
* [https://maven.apache.org/guides/getting-started/windows-prerequisites.html](https://maven.apache.org/guides/getting-started/windows-prerequisites.html)

As a next step, let's clone the workshop github project locally in a folder of your choice, let's call this folder `${CQ_WORKSHOP_DIRECTORY}` :

```
cd ${CQ_WORKSHOP_DIRECTORY}
git clone https://github.com/galderz/quarkus-native-workshop.git
```

Finally, it is highly recommended pulling the following container images in advance,
in order to reduce bandwidth requirements during the workshop:

```
docker pull quay.io/quarkus/ubi-quarkus-mandrel:21.3-java11
docker pull registry.access.redhat.com/ubi8/ubi-minimal:8.5
docker pull docker.io/library/neo4j:latest
```
