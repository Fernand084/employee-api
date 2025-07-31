# Usa una imagen con Java 21
FROM eclipse-temurin:21-jdk-alpine

# Crea un directorio de trabajo
WORKDIR /app

# Copia el archivo jar compilado
COPY target/*.jar app.jar

# Expón el puerto del container
EXPOSE 8080

# Comando para correr la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
