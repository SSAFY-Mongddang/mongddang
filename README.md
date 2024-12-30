![image](https://github.com/user-attachments/assets/a2a9f53e-87cf-4c61-807b-e6a1686f1d2b)

# ✨나의 혈당 메이트, 몽땅(MongDDang)✨

---

### 1️⃣ 프로젝트 개요

✨ **개발 기간**

| 개발기간 | 2024.10.14 ~ 2024.11.19 (6주) |
| ---- | ---------------------------- |

✨ **팀원 소개**

| 팀원  | 역할                                                                   |
| --- | -------------------------------------------------------------------- |
| 김민진 | 팀장, FE, UI/UX 디자인, 기능구현(메인페이지, 알림(FCM), 약 관리, 약 등록, 일일 퀘스트, 식사, 수면, 운동 등록 및 수정, 메뉴페이지(보호자메인))   |
| 권대호 | FE, Infra(CI/CD), 기능 구현(회원관리(로그인, 어린이 보호자 연결), 어린이/보호자 화면 기록, 일일 기록) |
| 조선미 | FE, 디자인 컴포넌트 개발, 기능구현(회원관리(회원가입, 프로필), 도감, 업적, 주간리포트), 갤럭시 워치 기능     |
| 나혜림 | BE, 기능구현(vital, 삼성 헬스 데이터 연동), 안드로이드(capacitor 용 AccessTokenplugin, BloodGlucosePlugin, SamsungHealthPlugin, foregroundPlugin 개발), FE(삼성헬스 & foreground Setting 컴포넌트) 크롤링                                                   |
| 변서원 | BE, 기능구현(유저, fcm 알림, mealplan, 구글 소셜로그인+jwt+spring security, 급식표 ocr & 공공api 연결(급식)), 에셋 디자인, 몽땅 세계관 구성                                                |
| 유영한 | BE, 기능구현(게임, 기록, 복약, vital, tts&stt 구현), ai(주간 레포트 프롬프팅& 몽땅 캐릭터 성격 프롬프팅) FE(몽땅과 채팅 및 음성 대화 기능,앱 미디어 세팅)                                                        |

✨ **기획 의도**

| 1형 당뇨, 4년 사이 `26%` 증가                                               |
| ------------------------------------------------------------------- |
| 소아 1형 당뇨의 경우 습관 형성이 필요하지만, 심리적으로 취약하기도 하고 실시간 혈당이 보호자와 연결의 필요성이 있음  |
| 갤럭시 워치를 활용하여 실시간 혈당을 공유하고 앱을 통해 아이 뿐 아니라 보호자도 함께 공유할 수 있는 앱을 만들기 위함 |

✨ **목표**

아이들의 혈당 관리 습관을 만들고 보호자의 불안감 해소

---

### 2️⃣ 서비스 기능 소개

✨ **워치**

- 실시간 혈당 알림

✨ **스마트폰**

<img src="https://github.com/user-attachments/assets/5022d35e-ede6-43d4-b114-cf2d3fa0761a" width=200 heigth=400/> <img src="https://github.com/user-attachments/assets/dd8182f1-dd00-40a0-810d-40bc33a36a3a" width=200 heigth=400/> <img src="https://github.com/user-attachments/assets/9e2b0d17-a988-4b67-b3ed-511d111893f5" width=200 heigth=400/>

- 혈당관리와 관련한 퀘스트 제공

- 코인과 캐릭터로 보상 지급, 동기 부여

<img src="https://github.com/user-attachments/assets/f0beabd9-cff6-4bde-9405-23c3e0183ba2" width=200 heigth=400/> <img src="https://github.com/user-attachments/assets/7dc55864-5f5c-401b-8ac6-032c873ed335" width=200 heigth=400/>

- 피보호자의 습관형성 상태 확인

<img src="https://github.com/user-attachments/assets/e8aa0efc-252d-40aa-b126-57b7eb55557c" width=200 heigth=400/>

- 피보호자의 혈당 리포트 제공

- 응급상황 실시간 알림

---

### 3️⃣ Figma

## ![img](https://github.com/user-attachments/assets/219cf8fb-61e6-43a8-bc66-41d4fcf83084)


### 3️⃣ ERD

## ![img](https://github.com/user-attachments/assets/86f10298-0eba-44ad-98bb-8c64824f31a3)

### 4️⃣ 기술 스택

![tech](https://github.com/user-attachments/assets/7113b0c0-ca0f-4d05-bcc6-90cfdaf7f6c5)

### 5️⃣ 아키텍처

## ![architecture](https://github.com/user-attachments/assets/21a70537-0814-44ff-8cff-6f352cc55996)

### 6️⃣ 기대효과

- 몽땅과의 대화로 인한 어린이 정서적 안정

- 이상 혈당에 대한 빠른 대처 가능

- 갤럭시 워치 활용성 증대

- 보호자 불안 해소 및 부담 경감

- 게임화 된 건강관리로 인한 지속적 습관 형성

---

### API

##### User

![api1](https://github.com/user-attachments/assets/7a265de4-2aa1-46c3-a526-4bcd8ea59290)

##### FCM

![api2](https://github.com/user-attachments/assets/1f650722-1216-4560-a958-03d8f1b7cded)

##### Notificatioin

![api3](https://github.com/user-attachments/assets/f173c674-45c5-4fc8-b3de-365a53eaa34b)

##### Game

![api4](https://github.com/user-attachments/assets/a3dae63c-1d7b-4c60-baa0-0ea133d70ee8)

##### Record

![api5](https://github.com/user-attachments/assets/ee8d3459-4436-4c4f-ba3c-6f3dd0b99a8e)

##### Medication

![api6](https://github.com/user-attachments/assets/401eb970-ed22-4b09-936a-874d23b3b646)

##### vital

![api7](https://github.com/user-attachments/assets/a1ccd675-9f8f-41bf-9674-6745bb8584d7)
