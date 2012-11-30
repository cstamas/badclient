# Bad Client

A quasi HTTP Client doing GET request and abruptly dropping connection on server.
Meant to be used in testing.

Usage:
```
java -jar nexus-badclient-tool-1.0-SNAPSHOT.jar <URL>
```

Examples:

### Case A -- REST Resources (XStreamRepresentation)
CLI:

```
[cstamas@zaphod badclient]$ java -jar nexus-badclient-tool-1.0-SNAPSHOT.jar http://localhost:8081/nexus/service/local/repositories/public/content/
Buffer size: 195984
```

Nexus log should say:
Client closed connection early (UA="Nexus-BadClient/1.0", URI="http://localhost:8081/nexus/service/local/repositories/public/content/", IP=127.0.0.1): IOException: Broken pipe

### Case B -- Content (StorageFileItemRepresentation)
CLI:
```
[cstamas@zaphod badclient]$ java -jar nexus-badclient-tool-1.0-SNAPSHOT.jar http://localhost:8081/nexus/content/groups/public/log4j/log4j/1.2.13/log4j-1.2.13.jar
Buffer size: 195984
```

Nexus log should say:
Client closed connection early (UA="Nexus-BadClient/1.0", URI="http://localhost:8081/nexus/content/groups/public/log4j/log4j/1.2.13/log4j-1.2.13.jar", IP=127.0.0.1): IOException: Broken pipe

### Case C -- Velocity (VelocityRepresentation)
Preparation: either check CLI output to have set small buffer size (edit the code if needed), or make "index page" huge, like this:
```
$ cd sonatype-work/nexus/storage/releases
$ for (( i=1; i<1000; i++ )); do mkdir dir$i; done
```
This will increase releases repo but public group index pages as well.

CLI:
```
[cstamas@zaphod badclient]$ java -jar nexus-badclient-tool-1.0-SNAPSHOT.jar http://localhost:8081/nexus/content/groups/public/
Buffer size: 195984
```

Nexus log should say:
Client closed connection early (UA="Nexus-BadClient/1.0", URI="http://localhost:8081/nexus/content/groups/public/", IP=127.0.0.1): IOException: Broken pipe



Have fun,  
~t~
