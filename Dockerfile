# OpenJDK 17 기반의 이미지 사용
FROM openjdk:17-jdk-alpine

# 빌드된 JAR 파일을 도커 이미지에 복사
COPY build/libs/*.jar /app/app.jar  # JAR 파일 이름을 지정하여 복사

# 애플리케이션 실행 (prod 프로파일로 실행)
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]  # 경로를 /app/app.jar로 수정
