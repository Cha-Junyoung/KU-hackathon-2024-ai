# 아:경 (AI)

아:경 AI repository 입니다.

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
    │  │              │   ├─S3Config.java          // to use S3 Storage
    │  │              │   ├─SwaggerConfig.java    
    │  │              │   └─WebConfig.java
    │  │              ├─controller
    │  │              │   └─imageTextController    // route request to generate text and image
    │  │              ├─dto
    │  │              │   ├─imageTextRequestDto.java    // request Dto
    │  │              │   └─imageTextResponseDto.java   // response Dto
    │  │              ├─exception
    │  │              └─service
    │  │                  ├─imageTextService.java       
    │  │                  ├─OpenAIService.java          // generate image, text, emotion
    │  │                  └─S3Service.java              // save image
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

<div align=center><h2>기술 스택</h2></div>

<div align=center> 
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
    <img src="https://img.shields.io/badge/openAI-412991?style=for-the-badge&logo=OpenAI&logoColor=white">
    
    
</div>
