# Stellt eine Java-Entwicklungsumgegbung (JDK) auf der Alpine Linux-Distribution bereit
FROM eclipse-temurin:17-jdk-alpine
# Definiert eine Variable
ARG JAR_FILE=target/*.jar
# JAR-Datei wird kopiert
COPY ${JAR_FILE} app.jar
# Anwendung wird gestartet, über <<docker run>> können anschließend weitere Java-Argumente angehangen werden
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]