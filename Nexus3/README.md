# Sonatype Nexus3 Repository Manager 

## Installation

**In updating**

## Setup as a Maven repository

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

**In updating**

## References
- [Sonatype Nexus3 OSS](https://www.sonatype.com/nexus/repository-oss)
- [Nexus3 Installation](https://www.fosslinux.com/27838/installing-sonatype-nexus-repository-oss-on-centos-7.htm)
- [Nexus3 Arm64 docker container image](https://hub.docker.com/r/klo2k/nexus3)
- [Nexus3 Arm64 Dockerfile](https://github.com/klo2k/nexus3-docker)
