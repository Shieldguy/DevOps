# Demo Project Integrate with Nexus3
You can integrate Java project with Nexus

## Gradle Integration
This demo created using Gradle 6.6.1 version.

### Configuration for build.gradle
- Add plugin
  ```
   plugins {
     ...
     id 'java'
     id 'java-library'
     id 'maven-publish'
     id 'publishing'
     ...
   }
  ```
- Change repositories to your nexus repo
  ```
   ...
   repositories {
     maven {
       url "http://{nexus_host}:{nexus_port}/repository/maven-group/"
       mavenContent {
         releasesOnly()
       }
     }
     maven {
       url "http://{nexus_host}:{nexus_port}/repository/maven-snapshot/"
       mavenContent {
         snapshotsOnly()
       }
     }
   }
   ...
  ```
- Add publishing 
  ```
   ...
   publishing {
     publications {
       mavenJava(MavenPublication) {
         artifact bootJar        // NOTICE. Spring boot executable jar should use this
         //from components.java  // NOTICE. General library jar should use this
   
         pom {
           name = '{project_name}'
           description = 'Your application description'
           url = 'http://example.com/library'
           licenses {
             license {
               name = 'The Apache License, Version 2.0'
               url = 'http://www.apache.org/licenses/LICENSE-2.0.txt'
             }
           }
           developers {
             developer {
               id = 'your_id_here'
               name = 'your_name_here'
               email = 'your_email@example.com'
             }
           }
           scm {
             connection = 'scm:git:git://github.com/your_repo_here.git'
             url = 'http://example.com/your_url_here'
           }
         }
       }
     }
   
     repositories {
       maven {
         name = "localMavenRepo"
         def releaseUrl = "http://{nexus_host}:{nexus_port}/repository/maven-release/"
         def snapshotUrl = "http://{nexus_host}:{nexus_port}/repository/maven-snapshot/"
         url = version.endsWith('SNAPSHOT') ? snapshotUrl: releaseUrl
         credentials {
           username = System.properties['repoUserId']
           password = System.properties['repoPasswd']
         }
       }
     }
   }
   ...
  ```

### Publich Command

- You can publish your jar file using following command.
  ```
  # gradle -DrepoUserID={nexus_id} -DrepoPasswd={nexus_pssword} publish
  ```

## Maven Integration

### Configuration for ~/.m2/settings.xml

- You need to create **~/.m2/settings.xml** file 
  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <settings xmlns="http://maven.apache.org/SETTINGS/1.1.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">
  
    <servers>
      <server>
        <id>nexus-snapshot</id>
        <username>your_nexus_id</username>
        <password>your_nexus_password</password>
      </server>
      <server>
        <id>nexus-release</id>
        <username>your_nexus_id</username>
        <password>your_nexus_password</password>
      </server>
    </servers>
  
    <mirrors>
      <mirror>
        <id>central</id>
        <name>central</name>
        <url>http://your_nexus_host:your_nexus_port/repository/maven-group/</url>
        <mirrorOf>*</mirrorOf>
      </mirror>
    </mirrors>
  
  </settings>
  ```
### Configuration for pom.xml

- You need to add following contents to the **pom.xml** of project.
   ```
  <project>
    ...
    <distributionManagement>
      <repository>
        <id>nexus-release</id>
        <name>Internal release repo</name>
        <url>http://your_nexus_host:your_nexus_port/repository/maven-release/</url>
      </repository>
      <snapshotRepository>
        <id>nexus-snapshot</id>
        <name>Internal snapshot repo</name>
        <url>http://your_nexus_host:your_nexus_port/repository/maven-snapshot/</url>
      </snapshotRepository>
    </distributionManagement>
    ...
  </project>
   ```

### Publich Command

- Run `# mvn deploy` command

### Password encryption

- You can reference : http://maven.apache.org/guides/mini/guide-encryption.html 

# Docker build

## Setup docker image tag

- Before build docker image, you should change tag information from `docker.env` file.
  ```
  TAG=0.0.0-SNAPSHOT
  ```

## Build and run

- You can build your docker image using following command.
  ```
  # docker-compose --env-file ./docker.env build
  ```
- You can run your docker image
  ```
  # docker-compose --env-file ./docker.env up -d
  ```

## Upload image

- If you don't authentication nexus docker repository, please login first
  ```
  # docker login  {docker_nexus_host}:{docker_nexus_port}
  ```
- If you can't login nexus docker registry, please add following to **/etc/docker/daemon.json**
  ```
  ...
  "insecure-registries": [
    "docker_nexus_host:docker_nexus_proxy_port",
    "docker_nexus_host:docker_nexus_hosted_port"
  ],
  ...
  ```
- Add tag name for the target images
  ```
  # docker images
    ....
  # docker tag {target_image_id} {docker_nexus_host}:{docker_nexus_port}/{target_image_id}:{target_tag}
  ```
- Upload tagged image
  ```
  # docker push {docker_nexus_host}:{docker_nexus_port}/{target_image_id}:{target_tag}
  ```

