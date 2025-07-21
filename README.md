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
- 사용자의 감정 및 키워드를 기반으로 AI가 음악을 자동 생성하고 영상 및 코드워크 이미지 생성
- AI 모델은 Spring Boot 서버 내부에서 직접 실행되어, 별도의 AI 서버 없이도 통합 운영 가능  

---

## 기술 스택

| 구분 | 기술 |
|------|------|
| **Frontend** | React, HTML/CSS/JS|
| **Backend** | Spring Boot, Spring Data JPA, Spring Security |
| **AI 모델** | Music Transformer |
| **AI 개발 환경** | PyTorch, CUDA, CentOS |
| **Database** | MariaDB |
| **협업/개발 도구** | Git, GitHub, Visual Studio Code, IntelliJ, PyCharm |

---

## 사용 모델 및 데이터

- **Music Transformer**: 생성된 토큰 시퀀스를 MIDI 형태로 변환하고, Fluidsynth를 통해 WAV 파일로 렌더링
- **한국 대중음악 루프 사운드 데이터**: 저작권 문제가 없고 상업적으로 활용 가능한 AIHub의 한국 대중음악 루프 데이터

---

## AI 서비스 시나리오
<img width="1310" height="585" alt="image" src="https://github.com/user-attachments/assets/7c2021f7-f4c2-46a5-bde5-320f46eef8a7" />

1-1. 사용자가 분위기, 악기, 음악 길이 및 BPM을 체크박스를 통해 입력(초보자용) 完
1-2. 사용자가 A, A#, B, D 등과 같은 코드워크, 악기, 길이 및 BPM을 체크박스를 입력(전문가용) 未完
2. Spring Boot 서버에서 Python 기반 Music Transformer 모델을 직접 실행 (ProcessBuilder 활용)
3. 생성된 음악(wav)과 코드워크 이미지 파일 생성 및 DB에 저장
4. 결과 파일을 React 프론트에 반환하여 시각화 및 다운로드 기능 제공

---

## 서비스 주요 특징

- **AI 기반 쇼츠 영상용 BGM 자동 생성 및 다운로드 제공**  
- **코드워크 이미지 생성 및 파일 다운로드 제공** 
- **음악 생성 기능의 시각화 + 커뮤니티 기능**
---

## 주요 기능

- JWT 기반 로그인/회원가입
- 분위기, 악기 등 키워드 기반 음악 생성 요청
- AI 모델 결과 파일 서버 저장 및 사용자 제공
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

### 문제2: React에서 AI 결과 파일 미리보기 오류
- 서버에서 결과 파일 MIME type 지정 및 경로 보안 처리

---

## 느낀 점


---

## 시연 동영상


---

## 👥 팀원

| 역할 | 이름 | GitHub |
|------|------|--------|
| AI 모델 개발 및 PM(팀장) | 이동원 | - |
| AI 모델 및 데이터 전처리 | 전우혁 | - |
| 백엔드 & AI 연동 👋 | 가한솔 | https://github.com/kaa08 |
| AI 모델 및 데이터 전처리 | 전우혁 | - |
