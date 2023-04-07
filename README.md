# TWO ( Take With OTT )
<img src="https://user-images.githubusercontent.com/120348468/229669605-f752d0ff-0600-4683-af86-14b7011a9e12.png" width="200" height="200"/>

# 프로젝트 소개
![화면 캡처 2023-04-04 113432](https://user-images.githubusercontent.com/120348468/229672009-8f68a0f8-79fe-4d63-a3df-c5a8bb7a76c9.png)
전세계 OTT 시장이 점점 커짐에 따라 이용자수가 늘고있다


![화면 캡처 2023-04-04 113442](https://user-images.githubusercontent.com/120348468/229672111-89462ed7-7613-4142-8f6a-d89cb8c2d5ed.png)
그에 따른 불만사항을 조사한 결과 가격부담이 가장 컸다 <br/> 이를 토대로 OTT 서비스를 이용할때 분할결제를 통하여 가격 부담을 완화하고 <br/>  좋아하는 컨텐츠를 추천 받을수있는 앱을 개발하였다

# 프로젝트 팀원
정가을 :  프로젝트 기획 , 화면기획서 작성 , 안드로이드 개발 <br/>
김정웅 :  테이블 및 API 설계 , Flask Framework로 백앤드 개발 <br/>
김대연 :  데이터 분석 및 안드로이드 개발 <br/>

# ⚒️ Tools
<img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white"> <img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=for-the-badge&logo=Visual Studio Code&logoColor=white"/> <img src="https://img.shields.io/badge/Jupyter notebook-F37626?style=for-the-badge&logo=Jupyter&logoColor=white"/><br/><img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white"/>  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"> <img src="https://img.shields.io/badge/Google Colab-F9AB00?style=for-the-badge&logo=Google Colab&logoColor=white">

# 클라우드 ( Back- end )
Flask Framework 를 이용한 API 서버개발 <br/> MySQL을 이용한 DB관리 <br/> POSTMAN을 이용한 API 설계 및 테스트 <br/> Google Firebase RealtimeDatabase 를 이용한 실시간 채팅 구현

# 데이터 분석
TMDB API 를 이용해 데이터 가져오기 <br/> google colab 이용 <br/> pandas 라이브러리를 이용한 데이터 분석 <br/> PyMySQL을 이용한 데이터 삽입

# 협업툴 / DevOPs
Slack을 이용한 팀원간 회의 및 아이디어 공유 <br/> GitHub contributors를 통한 레파지토리 공유 및 소스코드 관리 <br/> AWS Lambda 를 이용한 serverless 배포 <br/> GitHub Actions를 이용한 자동 배포

# 안드로이드 ( Front - end )
KaKao oven 을 이용한 화면기획서 구성 <br/> android studio 를 이용한 프론트앤드 개발 <br/> Bootpay API 라이브러리로 결제 구현

# 인공지능 
Surprise 라이브러리의 SVD ( Singular Value Decomposition ) 와 KNN ( K-Nearest Neighbors ) 을 하이드브리드해서 유저 경험 기반 추천 시스템 구현

# TWO 시연영상
https://user-images.githubusercontent.com/120348468/229675154-9021f6e8-5304-41c6-87a5-32f037d267e3.mp4

# 프로젝트 진행시 에러 해결
**GitHub Aws Key open** <br/>
프로젝트를 진행 중 레파지토리가 public으로 된 상태에서 config 파일안에 AWS의 Key값이 들어가니 Key가 노출되는 현상이 발생하였다 <br/> 이를 해결한 방법은 AWS Key를 재발급 받고 AWS IAM에서 유저권한을 다시 재설정 하였다 <br/> <br/>
**API 서버 에러** <br/>
AWS Key open으로 인해서 S3에서 사진을 지우는 과정에서 권한이 없다는 에러가 발생했다 <br/> 이는 키를 재발급 받음으로써 해결했다 <br/> <br/>
**안드로이드** <br/>
Retrofit 라이브러리를 이용한 회원가입 진행부분에서 사진이 없을때 에러가 발생했다 <br/> 회원가입시 사진을 지정하지 않을 경우 기본이미지로 설정하도록 API를 재설계하였다 <br/> <br/>
**추천 시스템** <br/>
추천 시스템 구현시에 SVD 모델 한개만을 사용하여 구현을 했더니 데이터의 총량이 적은게 문제가 되어 <br/> 모든 추천 스코어를 동일하게 뿌리는 현상이 발생했다 <br/> 
이를 해결하기 위해 SVD와 KNN을 하이브리드해서 사용하였다 <br/> <br/>
**AWS Lambda** <br/>
자동배포도중 에러가 발생하였는데 이는 AWS Lambda에 Surprise 라이브러리를 계층으로 만들고 사용하려고하니 <br/>
람다의 쓰레드와 호환성 문제가 발생하여 사용을 할 수 없었다 <br/> 이를 해결하기위해서 도커 이미지로 만들어서 ECR ( Elastic Container Registry ) 로 수동 배포 후 <br/>
다시 GitHub Actions 로 CI/CD 환경을 구축해서 자동배포하였다.
