# Sonatype Nexus3 Repository Manager 

## Installation

**In updating**

## Setup as a Maven repository

### Nexus3 configuration

- Create Blob store for maven snapshot, release, proxy, group
  - This is optional. But recommend create each blob stores.
  - Move **Repository** -> **Blob Stores**
  - Tab **Create blob store**
  - Put the name each store

- Create Repository for docker snapshot, release, proxy, group
  - Move **Repository** -> **Repositories**
  - Tab **Create repository** for snapshot
    - Tab **maven (hosted)**
    - Put the **name** field. ex) `maven-snapshot`
    - Select `Snapshot` under the **Version policy**
    - Select **Blob store** above created for the snapshot
    - Select **Allow redeploy** under the **Deployment policy**
    - Click **Save** button
  - Tab **Create repository** for release
    - Tab **maven (hosted)**
    - Put the **name** field. ex) `maven-release`
    - Select `Release` under the **Version policy**
    - Select **Blob store** above created for the snapshot
    - Click **Save** button
  - Tab **Create repository** for proxy
    - Tab **maven (proxy)**
    - Put the **name** field. ex) `maven-central`
    - Select `Release` under the **Version policy**
    - Put `https://repo1.maven.org/maven2` to **Remote storage**
    - Set `1440` to **Maximum component age**
    - Select **Blob store** above created for the proxy
    - Click **Save** button
  - Tab **Create repository** for group
    - Tab **maven (group)**
    - Put the **name** field. ex) `maven-group`
    - Select **Blob store** above created for the group
    - Add `maven-snapshot`, `maven-release`, and `maven-central` to *Member* under the **Member repositories**
    - Click **Save** button

### Maven client configuration

- Put following information to **~/.m2/settings.xml** file.
  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <settings xmlns="http://maven.apache.org/SETTINGS/1.1.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">

    <servers>
      <server>
        <id>nexus-snapshots</id>
        <username>admin</username>
        <password>******</password>
      </server>
      <server>
        <id>nexus-releases</id>
        <username>admin</username>
        <password>******</password>
      </server>
    </servers>

    <mirrors>
      <mirror>
        <id>central</id>
        <name>central</name>
        <url>http://your-host:8081/repository/maven-group/</url>
        <mirrorOf>*</mirrorOf>
      </mirror>
    </mirrors>

  </settings>
  ```

### Project configuration

- Repository on **pom.xml**
  ```
  <project ...>
    ...
    <repositories>
      <repository>
        <id>maven-group</id>
        <url>http://your-host:8081/repository/maven-group/</url>
      </repository>
    </repositories>
    ...
  </project>
  ```
- Publish on **pom.xml**
  ```
  <project ...>
    ...
    <distributionManagement>
      <snapshotRepository>
        <id>nexus-snapshots</id>
        <url>http://your-host:8081/repository/maven-snapshots/</url>
      </snapshotRepository>
      <repository>
        <id>nexus-releases</id>
        <url>http://your-host:8081/repository/maven-releases/</url>
      </repository>
    </distributionManagement>
    ...
  </project>
  ```

### Run

- Command
  ```
  # mvn install
  or
  # mvn deploy
  ```

### Jenkins

**In updating**

## Setup as a Docker repository

### Nexus3 configuration

- Create Blob store for docker private, proxy, group
  - This is optional. But recommend create each blob stores.
  - Move **Repository** -> **Blob Stores**
  - Tab **Create blob store**
  - Put the name each store

- Create Repository for docker private, proxy, group
  - Move **Repository** -> **Repositories**
  - Tab **Create repository** for private
    - Tab **docker (hosted)**
    - Put the **name** field. ex) `docker-private`
    - Check **HTTP** and set port number. ex) `5000`
    - Check **Enable Docker V1 API**
    - Select **Blob store** above created for the private
    - Click **Save** button
  - Tab **Create repository** for proxy
    - Tab **docker (proxy)**
    - Put the **name** field. ex) `docker-hub`
    - Put `https://registry-1.docker.io` to **Remote storage**
    - Select **Use Docker Hub** under the **Docker Index**
    - Select **Blob store** above created for the proxy
    - Click **Save** button
  - Tab **Create repository** for group
    - Tab **docker (group)**
    - Put the **name** field. ex) `docker-group`
    - Check **HTTP** and set port number. ex) `5001`
    - Select **Blob store** above created for the group
    - Add `docker-private` and `docker-hub` to *Member* under the **Member repositories**
    - Click **Save** button

### Docker client configuration

- Linux
  - Add **/etc/docker/daemon.json** file and put following content.
  ```
  {
    "insecure-registries": [
      "your-repo:8082",
      "your-repo:8083"
    ],
    "disable-legacy-registry": true
  }
  ```
  - Restart docker service `sudo systemctl restart docker.service`

- Windows / MacOS
  - Add **Insecure registries** of **Config** -> **Daemon**

- Jenkins
  - ** In updating **


### Authentication

- Run command 
  ```
  # docker login ${repo_hostname}:5000
  Username: admin
  Password: ********
  ....
  Login Succeeded
  #
  # docker login ${repo_hostname}:5001
  Username: admin
  Password: ********
  ....
  Login Succeeded
  ```

### Image pull from docker-group

- Pull image
  ```
  # docker pull ${repo_hostname}:5001/httpd:2.4-alpine
  ```

### Image push to docker-private
- Push image
  ```
  # docker tag ${target_image}:${target_tag} ${repo_hostname}:5000/${target_image}:${target_tag}
  # docker push ${repo_hostname}:5000/${target_image}:${target_tag}
  ```

## Setup as a Npm repository

### Nexus3 configuration

- Create Blob store for maven private, proxy, group
  - This is optional. But recommend create each blob stores.
  - Move **Repository** -> **Blob Stores**
  - Tab **Create blob store**
  - Put the name each store

- Create Repository for docker private, proxy, group
  - Move **Repository** -> **Repositories**
  - Tab **Create repository** for private
    - Tab **npm (hosted)**
    - Put the **name** field. ex) `npm-hosted`
    - Select **Blob store** above created for the snapshot
    - Select **Allow redeploy** under the **Deployment policy**
    - Click **Save** button
  - Tab **Create repository** for proxy
    - Tab **npm (proxy)**
    - Put the **name** field. ex) `npm-registry`
    - Put `http://registry.npmjs.org/` to **Remote storage**
    - Select **Blob store** above created for the proxy
    - Click **Save** button
  - Tab **Create repository** for group
    - Tab **npm (group)**
    - Put the **name** field. ex) `npm-group`
    - Select **Blob store** above created for the group
    - Add `npm-hosted`, and `npm-registry` to *Member* under the **Member repositories**
    - Click **Save** button

### Project configuration

- Generator base64 password
  ```
  # echo -n 'admin:*******' | openssl base64
  ```
- Put following information with above base64 password to project **.npmrc** file when the project download dependencies.
  ```
  registry=http://your-host:8081/repository/npm-group/_auth=YWRtaW46YWRtaW4xMjM=
  ```
- Add user to **~/.npmrc** if you run `npm publish`
- Set `email=yourid@email.com` to project **.npmrc** if publish from CI
- Change **package.json**, if project publish
  ```
  {
    ...
    "publishConfig": {
      "registry": "http://your-host:8081/repository/npm-hosted/"
    }
    ...
  }
  ```

### Run

- Command
  ```
  # npm install
  or
  # npm publish
  ```

### Install npm packages globally

- Run command
  ```
  # npm --registry http://your-host:8081/repository/npm-group/ install -g ${own_package}
  ```

### Jenkins

**In updating**


## References
- [Sonatype Nexus3 OSS](https://www.sonatype.com/nexus/repository-oss)
- [Using Nexus 3 as your repository - Part 1: Maven artifacts](https://blog.sonatype.com/using-nexus-3-as-your-repository-part-1-maven-artifacts)
- [Using Nexus 3 as your repository - Part 2: NPM packages](https://blog.sonatype.com/using-nexus-3-as-your-repository-part-2-npm-packages)
- [Using Nexus 3 as your repository - Part 3: Docker images](https://blog.sonatype.com/using-nexus-3-as-your-repository-part-3-docker-images)
- [Nexus3 Installation](https://www.fosslin3x.com/27838/installing-sonatype-nexus-repository-oss-on-centos-7.htm)
- [Nexus3 Arm64 docker container image](https://hub.docker.com/r/klo2k/nexus3)
- [Nexus3 Arm64 Dockerfile](https://github.com/klo2k/nexus3-docker)
