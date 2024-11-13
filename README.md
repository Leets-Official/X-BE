# X-BE

## Leets 클론코딩 3조 X입니다.



## 👥 BE 팀원 소개

<div align="center">

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/hyxklee" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/77369759?v=4" width="100px;" alt="이강혁"/><br />
        <b>이강혁</b> <br>(3기)
      </a>
      <p>ERD 설계 <br> API 명세서 작성 <br> 초기 세팅 <br> 구글 소셜 로그인 <br> 유저 API 개발 <br> 팔로우 API 개발 <br> 이미지 업로드 기능</p>
    </td>
    <td align="center">
      <a href="https://github.com/seokjun01" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/177178015?v=4" width="100px;" alt="문석준"/><br />
        <b>문석준</b> <br>(4기)
      </a>
      <p>ERD 설계 <br> API 명세서 작성 <br> CI/CD 파이프라인 구축 <br> 포스트 API 개발</p>
    </td>
    <td align="center">
      <a href="https://github.com/koreaioi" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/147616203?v=4" width="100px;" alt="송우석"/><br />
        <b>송우석</b> <br>(4기)
      </a>
      <p>ERD 설계 <br> API 명세서 작성 <br> 채팅 서비스 구현</p>
    </td>
  </tr>
</table>

</div>




## ⚙️ 기술 스택

### 백엔드

![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/spring%20data%20jpa-6DB33F?style=for-the-badge&logo=&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-010101?style=for-the-badge&logo=&logoColor=white)

### 데이터베이스

![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-569A31?style=for-the-badge&logo=redis&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS%20S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white)

### 배포 환경

![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/Github%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업 도구

![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![GitHub](https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=github&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)

---

## 📐 ERD 설계

프로젝트의 핵심 구조를 설명하는 **ERD**는 아래 이미지와 같습니다.

![ERD 이미지](https://github.com/user-attachments/assets/0b661f0c-dfbd-4283-8a8e-19269a28e413)

---

## 📌 구현 기능

### 🔑 인증 (Authentication)
- **JWT 기반 인증**: 보안을 위해 JWT(Json Web Token)를 활용하여 사용자의 로그인 및 인증 상태를 유지
- **구글 소셜 로그인**: 구글 계정을 통한 간편한 소셜 로그인 제공

### 👤 사용자 관리 (USER)
- **회원가입 및 로그인**: 이메일과 비밀번호로 회원가입과 로그인 기능 구현
- **유저 정보 관리**: 사용자 프로필 및 팔로우 상태를 생성 및 관리할 수 있는 API 제공
- **팔로우 및 언팔로우**: 사용자 간 팔로우 및 언팔로우 기능으로 소셜 상호작용 지원

### 🏠 홈 화면 (HOME)
- **타임라인 피드**: 팔로우한 사용자의 게시물을 실시간 피드 형식으로 제공
- **추천 게시물** : Home 추천 게시물 목록이 조회됩니다.

### 📄 프로필 관리 (PROFILE)
- **프로필 사진 업로드**: 프로필 사진 업로드 및 변경 기능 제공
- **개인 정보 수정**: 닉네임, 소개글 등 프로필 정보 수정 가능
- **활동 내역 확인**: 게시물 수, 팔로워/팔로잉 수 등 사용자 활동 한눈에 확인

### ✏️ 게시물 관리 (POST)
- **게시물 작성, 답글, 리포스트, 삭제**: 사용자가 게시물을 작성, 답글달기, 리포스트 및 삭제할 수 있는 기능
- **좋아요 및 댓글 기능**: 좋아요 및 댓글 기능을 통해 사용자 간 상호작용 가능
- **미디어 업로드**: 사진 업로드 기능으로 콘텐츠 표현 강화

### 💬 채팅 (Chat)
- **실시간 채팅**: WebSocket과 메세지 큐 Redis를 사용하여 1:1 실시간 채팅 구현
- **채팅 기록 저장**: MongoDB를 사용해 채팅 메세지를 저장하고, 사용자에게 대화 기록 제공 ,데이터 특징을 고려하여, RDBMS와 NoSQL를 사용




---