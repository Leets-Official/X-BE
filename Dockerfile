# OpenJDK 17 기반의 이미지 사용
FROM openjdk:17-jdk-alpine

# 빌드된 JAR 파일을 도커 이미지에 복사 (파일명을 정확히 기입)
COPY build/libs/X-0.0.1-SNAPSHOT.jar /app/app.jar

# 애플리케이션 실행 (prod 프로파일로 실행)
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/app.jar"]
