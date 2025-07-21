# 🎵 Music Transformer 모델을 이용한 쇼츠 BGM 생성 서비스 개발

> **프로젝트명**: AIMuse  
> **기간**: 2024년 9월 ~ 12월  
> **참여인원**: 4명  
> **담당 역할**: 백엔드 개발 및 AI 모델 연동  
<br>

---

## 전체적인 프로젝트 소개
<img width="1483" height="755" alt="image" src="https://github.com/user-attachments/assets/77cffb50-a970-4200-a656-728400d7211d" />

- Music Transformer 모델을 활용하여 **쇼츠 맞춤용 BGM 생성**하는 웹 기반 서비스  
- 사용자의 감정 및 키워드를 기반으로 AI가 음악을 자동 생성하고 영상 및 midi 이미지  
- AI 모델은 Spring Boot 서버 내부에서 직접 실행되어, 별도의 AI 서버 없이도 통합 운영 가능  

---

## 기술 스택

| 구분 | 기술 |
|------|------|
| **Frontend** | React, HTML/CSS/JS|
| **Backend** | Spring Boot, JPA, Spring Security |
| **AI 연동 방식** | ProcessBuilder (Spring → Python 스크립트 직접 실행) |
| **AI 모델** | Music Transformer |
| **AI 개발 환경** | PyTorch, CUDA, CentOS |
| **Database** | MariaDB |
| **협업/개발 도구** | Git, GitHub, Visual Studio Code, IntelliJ, PyCharm |

---

## 사용 모델

- **Music Transformer**: 키워드 및 감정을 기반으로 음악 생성

---

## AI 서비스 시나리오
<img width="1310" height="585" alt="image" src="https://github.com/user-attachments/assets/7c2021f7-f4c2-46a5-bde5-320f46eef8a7" />

1. 사용자가 감정 및 키워드를 입력하고 쇼츠 길이를 설정
2. Spring Boot 서버에서 Python 기반 Music Transformer 모델을 직접 실행 (ProcessBuilder 활용)
3. 생성된 음악(MID/WAV/MP3)과 썸네일 이미지 파일을 저장
4. 결과 파일을 React 프론트에 반환하여 시각화 및 다운로드 기능 제공

---

## 서비스 주요 특징

- **Spring Boot 내부에서 AI 모델 직접 실행 (Flask 서버 불필요)**
- **CentOS 환경에서 모델 실행 경로 및 환경 변수 안정화**
- **음악 생성 + 편집 + 이미지 생성까지 전체 자동화**
- **모바일 및 데스크탑 대응 UI**
- **JWT 기반 로그인/회원 관리 + 관리자 페이지 구성**

---

## 주요 기능

- 감정 키워드 기반 음악 생성 요청
- AI 모델 결과 파일 서버 저장 및 사용자 제공
- React 기반 시각적 피드백 및 다운로드
- 사용자 게시판 및 후기 등록 기능
- 관리자 전용 유저 관리 / 게시물 관리 기능

---

## 웹 페이지 구성

> 1. 사용자 페이지  
<img width="1053" height="581" alt="image" src="https://github.com/user-attachments/assets/c2809133-8f09-4482-a556-d93f1a6227d3" />

> 2. 관리자 페이지  
<img width="1053" height="581" alt="image" src="https://github.com/user-attachments/assets/a7addb7c-35c3-4056-b0a3-377c07fe81a5" />

---

## 주요 개발 기능

- **React + Redux**: 전역 상태 관리
- **Axios**: Spring API 서버와 통신
- **Dropzone**: 음악 생성 요청용 UI
- **React-player**: 음악 미리 듣기
- **Sharp**: 이미지 최적화
- **Spring Security + JWT**: 로그인/회원가입/인가 처리
- **JPA**: MariaDB 연동 및 CRUD 처리
- **ProcessBuilder**: AI 모델 직접 실행 및 결과 파일 수신

---

## 트러블슈팅

### 문제1: Python 모델을 Spring Boot에서 실행할 때 경로 인식 불가
- CentOS 환경에서 절대 경로 지정 + 실행권한 부여로 해결

### 문제2: ProcessBuilder로 실행한 모델이 중간에 종료되는 현상
- 표준 출력/에러 스트림 수집 및 로그 저장 처리 추가

### 문제3: React에서 AI 결과 파일 미리보기 오류
- 서버에서 결과 파일 MIME type 지정 및 경로 보안 처리

---

## 느낀 점

AI 모델을 별도 Flask 서버 없이 Spring Boot에 통합하는 시도는 처음이었지만,  
직접 프로세스를 실행하고 입출력을 제어하면서 **백엔드와 AI 시스템 간의 깊은 연동 구조**를 경험할 수 있었습니다.  

또한 음악 생성이라는 비정형 데이터 처리에서 프론트, 백엔드, 모델 간 흐름을 설계하며  
**실제 서비스를 구축하는 전체 아키텍처 경험**이 가능했습니다.

---

## 시연 동영상

> (추후 링크 첨부 예정)

---

## 👥 팀원

| 역할 | 이름 | GitHub |
|------|------|--------|
| AI 모델 개발 및 PM(팀장) | 이동원 | - |
| AI 모델 및 데이터 전처리 | 전우혁 | - |
| 백엔드 & AI 연동 👋 | 가한솔 | https://github.com/kaa08 |
| AI 모델 및 데이터 전처리 | 전우혁 | - |
