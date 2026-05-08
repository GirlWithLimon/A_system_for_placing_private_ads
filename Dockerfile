FROM eclipse-temurin:25-jdk AS builder

RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

WORKDIR /app

COPY pom.xml .
COPY advertisementsystem/pom.xml ./advertisementsystem/
RUN mvn dependency:go-offline -B
COPY advertisementsystem/src ./advertisementsystem/src
RUN mvn -pl advertisementsystem -am clean package -DskipTests
FROM tomcat:11.0-jdk25
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /app/advertisementsystem/target/advertisementsystem.war /usr/local/tomcat/webapps/ROOT.war
RUN mkdir -p /usr/local/tomcat/logs/bookstore
EXPOSE 8080
CMD ["catalina.sh", "run"]