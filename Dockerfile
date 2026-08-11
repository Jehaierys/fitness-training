FROM eclipse-temurin:25-jre

WORKDIR /app

COPY build/libs/*.jar app.jar

# Java 25 restricts native access by default, so we need to enable it for Netty to optimize its perfomance
ENTRYPOINT ["java", "--enable-native-access=ALL-UNNAMED", "-jar", "app.jar"]