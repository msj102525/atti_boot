# 현재 스프링 부트 애플리케이션 Dockerfile

# 기존의 톰캣 10 버전 이미지 사용
FROM tomcat:10-jdk17-openjdk-slim

# 작업 디렉토리 설정
WORKDIR /usr/local/tomcat

# 호스트의 WAR 파일을 이미지로 복사합니다.
COPY build/libs/atti_boot-0.0.1-SNAPSHOT.war webapps/ROOT.war

# Docker 컨테이너에서 사용할 포트를 노출합니다.
EXPOSE 8080
