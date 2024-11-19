# 포팅 매뉴얼
---
## 1. 개요
---
### 1-1. 프로젝트 개요
---
- 1형 당뇨를 가진 아동과 보호자를 위한 당뇨 관리 서비스. 아동은 게임 형식으로 가볍고 쉽게 당뇨를 관리할 수 있고, 보호자는 위급상황에 대한 알림과 실시간 혈당 체크로 아동 처할 수 있는 위험상황을 미연에 방지할 수 있다.

### 1-2. 개발 환경
---
#### Backend

- Java:
- Spring Boot:
- JPA:
- MySql: 8.0.39
- IntelliJ:

#### Frontend

- React: 18.3.1
- Typescript: 5.6.3
- Vite: 5.4.8
- Axios: 1.7.7
- Emotions: 11.13.3
- CapacitorJs: 6.0.0
- Zustand: 5.0.1
- Node.js: 20.15.0

#### Cicd

- Jenkins: 2.462.3-jdk17
- Docker: 27.3.1
- Nginx: 1.27.2
- Ubuntu: 22.04 Jammy Jellyfish

### 1-3. 프로젝트 툴

- 이슈 / 형상 관리 : GitLab
- 코드 리뷰 : GitLab
- 커뮤니케이션 : Notion, Mattermost
- 디자인 : Figma

### 1-4. 외부 서비스

- Google Oauth
- Chat GPT

## 2. 설정 파일
---
### 2-1. Api 서비스

#### 2-1-1. `.env` 파일

```
MYSQL_HOST=(localhost)
MYSQL_PORT=3306
MYSQL_DB=(db 이름 넣으세요)
MYSQL_USER=(user 넣으세요)
MYSQL_PASSWORD=(비밀번호 넣으세요)

DDL_AUTO=update
JWT_SECRET=secretkey
JWT_EXPIRATION=3153600000000
CLIENT_ID=407408718192.apps.googleusercontent.com

FIREBASE_CREDENTIALS_PATH=firebase/mongddang-65473-firebase-adminsdk-mvgzn-e811982383.json

CORS_URL=http://localhost:5173, https://your-server-domain

S3_ACCESSKEY=(S3_ACCESSKEY)
S3_SECRETKEY=(S3_SECRETKEY)
S3_BUCKETNAME=mongddang
S3_REGION=ap-southeast-2

MEAL_KEY=(교육청 급식 API 키)
MEAL_URL=https://open.neis.go.kr/hub/mealServiceDietInfo

SCHOOL_KEY=(학교 검색 키)
SCHOOL_URL=https://open.neis.go.kr/hub/schoolInfo

FCM_URL_PREFIX=https://fcm.googleapis.com/v1/projects/
FCM_URL_POSTFIX=/messages:send
FCM_PROJECT_ID=mongddang-65473
```

## 3. 배포

### 3-1. 준비사항

#### 3-1-1. Google cloud credential

[Capgo/capacitor-social-login/docs/setup_google.md](https://github.com/Cap-go/capacitor-social-login/blob/main/docs/setup_google.md#using-google-login-on-android) 참고

### 3-2. 배포

#### 3-2-1. EC2 서버에 Docker 설치

[Docker Docs](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository) 참고

##### App service

###### Docker compose

```
# Version top-level element is obsolete
# https://docs.docker.com/reference/compose-file/version-and-name/

name: mongddang

services:
  db:
    image: mysql:8.0.39
    volumes:
      - mysql_data:/var/lib/mysql
      - mysql_config:/etc/mysql/conf.d
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: ${MYSQL_DB}
      MYSQL_USER: ${MYSQL_USER}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}
      TZ: Asia/Seoul
      LANG: C.UTF-8
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      timeout: 20s
      retries: 10
    ports:
      - "3306:3306"
    networks:
      - mongddang_net
    container_name: mongddang_db

  webserver:
    image: nginx:latest
    ports:
      - 80:80
      - 443:443
    restart: always
    environment:
      TZ: Asia/Seoul
    volumes:
      - ./nginx/conf/:/etc/nginx/conf.d/:ro
      - certbot_www:/var/www/certbot/:ro
      - certbot_conf:/etc/nginx/ssl/:ro
    networks:
      - mongddang_net
    container_name: nginx

  certbot:
    image: certbot/certbot:latest
    volumes:
      - certbot_www:/var/www/certbot:rw
      - certbot_conf:/etc/letsencrypt/:rw
    networks:
      - mongddang_net
    container_name: certbot

  backend:
    # build:
    #   context: ./backend/mongddang
    #   dockerfile: Dockerfile
    image: vaaastlake/mongddang_backend
    restart: always
    depends_on:
      db:
        condition: service_healthy
    env_file:
      - .env
    environment:
      TZ: Asia/Seoul
      CORS_URL: ${CORS_URL}
      # MySql
      MYSQL_HOST: ${MYSQL_HOST}
      MYSQL_PORT: ${MYSQL_PORT}
      MYSQL_DB: ${MYSQL_DB}
      MYSQL_USER: ${MYSQL_USER}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}
      DDL_AUTO: ${DDL_AUTO}
      # JWT
      JWT_SECRET: ${JWT_SECRET}
      JWT_EXPIRATION: ${JWT_EXPIRATION}
      CLIENT_ID: ${CLIENT_ID}
      # Firebase
      FIREBASE_CREDENTIALS_PATH: ${FIREBASE_CREDENTIALS_PATH}
      # School
      MEAL_KEY: ${MEAL_KEY} 
      MEAL_URL: ${MEAL_URL} 
      SCHOOL_KEY: ${SCHOOL_KEY} 
      SCHOOL_URL: ${SCHOOL_URL}
      # ChatGPT
      OPENAI_API_KEY: ${OPENAI_API_KEY}
      OPENAI_URL: ${OPENAI_URL}
      # FCM
      FCM_URL_PREFIX: ${FCM_URL_PREFIX}
      FCM_URL_POSTFIX: ${FCM_URL_POSTFIX}
      FCM_PROJECT_ID: ${FCM_PROJECT_ID}
    expose:
      - "8080"
    # volumes:
    #   - ../firebase:/app/firebase
    networks:
      - mongddang_net
    container_name: mongddang_backend

volumes:
  mysql_data:
    name: mysql_data
  mysql_config:
    name: mysql_config
  certbot_www:
    name: certbot_www
  certbot_conf:
    name: certbot_conf

networks:
  mongddang_net:
    driver: bridge
```

###### DinD Jenkins

```dockercompose
name: jenkins_dind

services:
  jenkins:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: jenkins
    restart: on-failure
    networks:
      - jenkins
    environment:
      TZ: Asia/Seoul
      DOCKER_HOST: tcp://docker:2376
      DOCKER_CERT_PATH: /certs/client
      DOCKER_TLS_VERIFY: 1
    ports:
      - "8080:8080"
      - "50000:50000"
    volumes:
      - jenkins_home:/var/jenkins_home
      - jenkins_certs:/certs/client:ro
    depends_on:
      - docker

  docker:
    image: docker:dind
    container_name: jenkins-docker
    privileged: true
    restart: unless-stopped
    networks:
      jenkins:
        aliases:
          - docker
    environment:
      DOCKER_TLS_CERTDIR: /certs
    volumes:
      - jenkins_home:/var/jenkins_home
      - jenkins_certs:/certs/client
    ports:
      - "2376:2376"

volumes:
  jenkins_home:
    name: jenkins_home
  jenkins_certs:
    name: jenkins_certs

networks:
  jenkins:
    name: jenkins
    driver: bridge
```

##### Jenkins Pipeline

```
pipeline {
    agent any

    tools {
        gradle 'gradle-8.10.2'
        jdk 'jdk21'
    }

    environment {
        REPOSITRY =  'vaaastlake/mongddang_backend'
        BACKEND_DIR = 'backend/mongddang'
        BACKEND_IMG = 'mongddang_backend'
        PROJECT_DIR = './S11P31D207'
    }

    triggers {
        pollSCM 'H 12,5,23 * * 1-5'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout processing...'
                script {
                    // mongddang 디렉토리에서 변화가 있을 때만 빌드
                    def changes = sh(script: "git diff --name-only HEAD HEAD~1", returnStdout: true).trim()
                    if (changes.contains("${BACKEND_DIR}")) {
                        echo "Changes in ${BACKEND_DIR} directory, proceeding with build."
                    } else {
                        echo "No changes in ${BACKEND_DIR} directory, skipping build."
                        currentBuild.result = 'NOT_BUILT'
                        error("No changes in ${BACKEND_DIR} directory. Halting pipeline.")
                    }

                    // 코드 체크아웃
                    checkout scm
                }
                echo 'Checkout done'
            }
        }

        stage('firebase json key download') {
            steps {
                dir("${BACKEND_DIR}") {
                    withCredentials([file(credentialsId: 'fcm_certification', variable: 'certificationFile')]) {
                        script {
                            echo "download certification.json"
                            sh 'cp -f $certificationFile ./src/main/resources/certification.json'
                        }
                    }
                }
            }
        }

        stage('Builds Backend .JAR') {
          steps {
            dir("${BACKEND_DIR}") {
              script {
                def currentBranch = env.BRANCH_NAME ?: 'unknown'
                def targetBranch = env.CHANGE_TARGET ?: 'N/A'

                echo "Current Branch: ${currentBranch}"
                if (targetBranch) {
                    echo "Merge Target Branch: ${targetBranch}"
                } else {
                    echo "Not a merge request build"
                }

                echo 'mongddang server 입니다.'
                sh 'pwd'
                sh 'ls -al'

                sh 'chmod +x ./gradlew'
                sh './gradlew clean build -x test'
              }
            }
          }
        }
        stage('Test') {
            steps {
                echo 'No test here.'
            }
        }
        stage('Build Backend docker image and push') {
          steps {
            dir("${BACKEND_DIR}") {
              script {
                docker.withRegistry('', 'docker-cred') {
                  def dockerImage = docker.build("${REPOSITRY}:${BUILD_NUMBER}", "--target prod -f ./Dockerfile ./")
                  dockerImage.push()
                  dockerImage.push('latest')
                }
              }
            }
          }
        }
        stage('Deploy to EC2') {
            stages {
                stage('Garbage Collect') {
                    steps {
                        echo 'Garbage Collecting....'
                        sshPublisher(
                          continueOnError: true, failOnError: false,
                          publishers: [
                            sshPublisherDesc(
                              configName: "mongddang-ssh",
                              verbose: true,
                              transfers: [
                                sshTransfer(
                                  execCommand: """
                                    cd S11P31D207
                                    docker compose down backend
                                    docker image prune -af
                                    docker compose ps -a
                                    docker ps -a
                                  """),
                              ]
                            )
                          ]
                        )
                        echo 'Garbage Collecting is done.'
                    }
                }
                stage('Deploy') {
                    steps {
                        echo 'Start deploying....'
                        sshPublisher(
                          continueOnError: true, failOnError: false,
                          publishers: [
                            sshPublisherDesc(
                              configName: "mongddang-ssh",
                              verbose: true,
                              transfers: [
                                sshTransfer(
                                  execCommand: """
                                    cd S11P31D207
                                    docker compose up -d backend
                                    docker compose ps -a
                                    docker ps -a
                                  """),
                              ]
                            )
                          ]
                        )
                        echo 'Deploying is done.'
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Build Success.'
            dir("${PROJECT_DIR}") {
                script {
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    def Commit_Hash = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    def Commit_Message = sh(script: "git show -s --pretty=%B", returnStdout: true).trim()
                    def Build_Duration = currentBuild.durationString
                    
                    def sourceBranch = 'N/A'
                    def targetBranch = env.BRANCH_NAME
                    def changeLog = ""

                    // dev 브랜치의 머지 상황일 때만 머지 정보 수집
                    if (env.BRANCH_NAME == 'dev' && Commit_Message.contains('Merge')) {
                        try {
                            // 머지된 브랜치 이름 추출 (소스 브랜치)
                            sourceBranch = sh(
                                script: """
                                    git log -1 --merges --pretty=format:'%s' | grep -o "from .* into" | sed 's/from //' | sed 's/ into//'
                                """,
                                returnStdout: true
                            ).trim()
                            
                            // 현재 머지 커밋과 이전 머지 커밋 찾기
                            def currentMergeCommit = sh(
                                script: "git log --merges -n 1 --pretty=format:'%H'",
                                returnStdout: true
                            ).trim()
                            
                            def previousMergeCommit = sh(
                                script: "git log --merges -n 2 --pretty=format:'%H' | tail -n 1",
                                returnStdout: true
                            ).trim()
                            
                            // 두 머지 커밋 사이의 모든 커밋 가져오기
                            def changes = sh(
                                script: """
                                    git log ${previousMergeCommit}..${currentMergeCommit} --pretty=format:'%h - %s [%an]' --no-merges
                                """,
                                returnStdout: true
                            ).trim()
                            
                            if (changes) {
                                changeLog = changes.split('\n').collect { "- ${it}" }.join('\n')
                            } else {
                                changeLog = "No changes in this merge"
                            }
                        } catch (Exception e) {
                            echo "Failed to get merge info: ${e.getMessage()}"
                            changeLog = "Unable to retrieve change log"
                        }
                    } else {
                        // 일반 푸시의 경우 마지막 머지 이후 커밋 정보만 가져오기
                        try {
                            // 마지막 머지 커밋 해시 찾기
                            def lastMergeCommit = sh(
                                script: "git log --merges -n 1 --pretty=format:'%H'",
                                returnStdout: true
                            ).trim()
                            
                            // 마지막 머지 이후의 커밋들 가져오기
                            def changes = sh(
                                script: """
                                    git log ${lastMergeCommit}..HEAD --pretty=format:'%h - %s [%an]'
                                """,
                                returnStdout: true
                            ).trim()
                            
                            if (changes) {
                                changeLog = changes.split('\n').collect { "- ${it}" }.join('\n')
                            } else {
                                changeLog = "No changes found since last merge"
                            }
                        } catch (Exception e) {
                            changeLog = "Unable to retrieve change log"
                            echo "Failed to get change log: ${e.getMessage()}"
                        }
                    }
                    
                    mattermostSend(color: 'good',
                    message: """# Build Success :white_check_mark:

---

# Job: 
- Job Name: ${env.JOB_NAME} 
- Build Number: #${env.BUILD_NUMBER}

---

# Branch Info:
- Source Branch: ${sourceBranch}
- Target Branch: ${targetBranch}

---

# Build Info:
- Author: ${Author_ID}(${Author_Name})
- Commit: ${Commit_Hash}
- Message: ${Commit_Message}
- Duration: ${Build_Duration}

---

# Changes:
${changeLog}

---

# Build URL: ${env.BUILD_URL}
""",
                    endpoint: 'https://meeting.ssafy.com/hooks/ekk4hmxtsjn9irbhug1qhjqz8w',
                    failOnError: true
                    )
                }
            }
            cleanWs()
        }
        failure {
            echo 'Build Fail.'
            dir("${PROJECT_DIR}") {
                script {
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    def Commit_Hash = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    def Commit_Message = sh(script: "git show -s --pretty=%B", returnStdout: true).trim()
                    def Build_Duration = currentBuild.durationString
                    
                    // 현재 브랜치 정보
                    def Current_Branch = env.BRANCH_NAME ?: env.GIT_BRANCH
                    // PR 타겟 브랜치
                    def Target_Branch = env.CHANGE_TARGET ?: 'N/A'
                    
                    // 에러 로그 수집
                    def errorLog = manager.build.getLog(1000).join('\n')
                    def truncatedError = (errorLog.length() > 500) ? errorLog.substring(0, 500) + "..." : errorLog
                    
                    mattermostSend(color: 'danger',
                        message: """# Build Failed :x:

---

# Job:
- Job name: ${env.JOB_NAME} 
- Build number: #${env.BUILD_NUMBER}

---

# Branch Info:
- Current Branch: ${Current_Branch}
- Target Branch: ${Target_Branch}

---

# Build Info:
- Author: ${Author_ID}(${Author_Name})
- Commit: ${Commit_Hash}
- Message: ${Commit_Message}
- Duration: ${Build_Duration}

---

# Error Log:
${truncatedError}

---

# Build URL: ${env.BUILD_URL}
""",
                        endpoint: 'https://meeting.ssafy.com/hooks/ekk4hmxtsjn9irbhug1qhjqz8w',
                        failOnError: true
                    )
                }
            }
            cleanWs()
        }
        always {
            echo 'everything is done'
        }
    }
}
```

##### Nginx

```
server {
    listen 80;
    listen [::]:80;

    server_name mijung.store www.mijung.store jenkins.mijung.store swagger.mijung.store;
    server_tokens off;

    location /.well-known/acme-challenge/ {
        root /var/www/certbot;
    }

    location / {
        return 301 https://$host$request_uri;
    }

    # Disallow all bots
    location /robots.txt {
        return 200 "User-agent: *\nDisallow: /";
    }
}

server {
    listen 80;
    listen 443 ssl;
    server_name k11d207.p.ssafy.io;

    return 301 $scheme://mijung.store$request_uri;

    # Disallow all bots
    location /robots.txt {
        return 200 "User-agent: *\nDisallow: /";
    }
}

server {
    listen 443 default_server ssl;
    http2 on;
    listen [::]:443 ssl http2;

    server_name mijung.store;

    ssl_certificate /etc/nginx/ssl/live/mijung.store/fullchain.pem;
    ssl_certificate_key /etc/nginx/ssl/live/mijung.store/privkey.pem;

    location /api/ {
        proxy_pass http://mongddang_backend:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        # Nginx 응답 헤더 재구성 시 Authorization 헤더가 생략되지 않도록 설정.
        add_header Access-Control-Expose-Headers 'Authorization' always;
    }

    # location / {
    #     root /usr/share/nginx/html;  # React 앱의 빌드 파일 위치
    #     index index.html;
    #     try_files $uri $uri/ /index.html;  # React Router를 위한 설정
    # }

    # Disallow all bots
    location /robots.txt {
        return 200 "User-agent: *\nDisallow: /";
    }
}

# jenkins https reverse proxy
server {
    listen 443 ssl http2;
    server_name jenkins.mijung.store;

    ssl_certificate /etc/nginx/ssl/live/jenkins.mijung.store/fullchain.pem;
    ssl_certificate_key /etc/nginx/ssl/live/jenkins.mijung.store/privkey.pem;

    location / {
        proxy_pass http://jenkins:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        # WebSocket support
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";

        # Required for Jenkins websocket agents
        # proxy_set_header   Connection        $connection_upgrade;
        proxy_max_temp_file_size 0;

        # this is the maximum upload size
        client_max_body_size       10m;
        client_body_buffer_size    128k;

        # Additional settings
        proxy_connect_timeout      90;
        proxy_send_timeout         90;
        proxy_read_timeout         90;
        proxy_buffering off;

        # Required for HTTP CLI commands
        proxy_request_buffering    off;
    }

    # Disallow all bots
    location /robots.txt {
        return 200 "User-agent: *\nDisallow: /";
    }
}

# sub-domain for swagger
server {
    listen 443 ssl http2;
    server_name swagger.mijung.store;

    ssl_certificate /etc/nginx/ssl/live/swagger.mijung.store/fullchain.pem;
    ssl_certificate_key /etc/nginx/ssl/live/swagger.mijung.store/privkey.pem;

    location / {
        
           rewrite ^/$ /swagger-ui/index.html permanent;

           proxy_pass http://mongddang_backend:8080;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
    }

    # Disallow all bots
    location /robots.txt {
        return 200 "User-agent: *\nDisallow: /";
    }
}
```

##### SSL, Let's Encrypt

[How to Set Up letsencrypt with Nginx on Docker](https://phoenixnap.com/kb/letsencrypt-docker) 참고
