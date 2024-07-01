# Base image
FROM tomcat:9.0-jre11-slim

# WAR 파일을 Docker 이미지에 복사
COPY build/libs/*.war /usr/local/tomcat/webapps/app.war

# Tomcat을 실행
CMD ["catalina.sh", "run"]