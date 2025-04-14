# IMAGEN MODELO
FROM eclipse-temurin:21-jdk

#INFORMAR EL PUERTO EN DONDE SE EJECUTA EL CONTENEDOR
EXPOSE 8080

#DEFINIR DIRECTORIO RAIZ DE NUESTRO CONTENEDOR
WORKDIR /root

#COPIAR Y PEGAR ARCHIVOS DENTRO DEL CONTENEDOR
#copiando las dependencias
COPY ./pom.xml /root

#maven envebido , para no instlar el mvn
COPY ./.mvn /root/.mvn

#ejecutable del maveb
COPY ./mvnw /root

#DESCARGAR LAS DEPENDENCIAS
#descarga las dependencias dentro del contenedor, mas no construya el proyecto
RUN ./mvnw dependency:go-offline

#COPIAR EL CODIGO FUENTE DENTRO DEL CONTENEDOR
COPY ./src  /root/src

#CONSTRUIR NUESTRA APLICACION
#genera una carpeta target y generar un archivo.jar(se usa para elevantar dentro del contenedor)
#usamos el de linux
RUN ./mvnw clean install -DskipTests

#LEVANTAR NUESTRA APLICACION CUANDO EL CONTENEDOR INICIE
#es el archivo ejecutable de tu aplicación Java(JAR)
ENTRYPOINT [ "java","/jar","/root/target/citamedica-0.0.1-SNAPSHOT.jar" ]
#va a ejeuctar un comando solo cuando inicie

