# 야:경 (AI)

야:경 AI repository 입니다.

---

### 프로젝트 구조

```
├─gradle
│  └─wrapper
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─example
    │  │          └─KU_2024_hackathon
    │  │              ├─configuration
    │  │              ├─controller
    │  │              ├─dto
    │  │              ├─exception
    │  │              └─service
    │  └─resources
    └─test
        └─java
            └─com
                └─example
                    └─KU_2024_hackathon
```

###

---

### API 명세서

/api/create

    request:
    {
        "question1": "string",
        "answer1": "string",
        "question2": "string",
        "answer2": "string",
        "question3": "string",
        "answer3": "string"
    }

    response:
    {
        "emotion": "string",
        "image": "string",
        "text": "string"
    }

###

---

### 배포 관련 설정

${environment}  
BUCKET_NAME: AWS S3 Bucket name  
ACCESS_KEY: AWS S3 Access Key  
SECRET_KEY: AWS S3 Secret key  
REGION: AWS S3 Region  
OPENAI_API_KEY: OpenAI api key  

###
