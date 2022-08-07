# GDSC-WEB-BackEnd

## You can start Project
## *** 저희는 초보 GIT 사용자이기 때문에 GIT CLIENT를 사용 하겠습니다.
>### **1.먼저 작업 폴더를 만들어 주세요**
> ![image](https://user-images.githubusercontent.com/84506708/151740653-310e95b4-9efb-4584-83f1-c7a023d23f6b.png)

>### **2.git clone https://github.com/GDSC-Daejin/GDSC-WEB-BackEnd.git**
>
>###또는 GIT CLIENT를 통해서 clone 해주세요
> ![image](https://user-images.githubusercontent.com/84506708/151740853-e4c2ac0a-f4e7-43f8-ba38-8ce05b45ea9c.png)
>![image](https://user-images.githubusercontent.com/84506708/151740922-93db4661-f914-4308-9048-303bbe7f6f92.png)
> ### **3. 작업 영역, branch를 만들어 주세요**
> ![image](https://user-images.githubusercontent.com/84506708/151740973-892e9177-dc3c-4cc2-bbc1-412f7cf1dbbe.png)
> ![image](https://user-images.githubusercontent.com/84506708/151741047-3f9c1e79-ebb5-4e04-bf35-e38f732225c5.png)
>### **4. commit 메세지는 규칙에 맞춰주세요**
> 
>###
> Jason Notion에 작성된 내용입니다. 
> 
> ###Git Flow (Branch) : 
>- develop : 요걸 기본으로 갈 생각입니다.
>- release/(version_number) : ex) Release/0.1.0
>- feature/(Feature_name) / (Short Description): ex)feature/admin
>- hotfix/(Issue_number) or Issue/(Issue_number): 사용할 일이 없기를..
>
> ### **Commit Convention**
>- feat : 새로운 기능에 대한 커밋
>- fix : 버그 수정에 대한 커밋
>- build : 빌드 관련 파일 수정에 대한 커밋
>- chore : 그 외 자잘한 수정에 대한 커밋
>- ci : CI관련 설정 수정에 대한 커밋
>- docs : 문서 수정에 대한 커밋
>- style : 코드 스타일 혹은 포맷 등에 관한 커밋
>- refactor : 코드 리팩토링에 대한 커밋
>- test : 테스트 코드 수정에 대한 커밋
>ex) feat: navigation 추가 / fix: 모바일 환경 사진 버그 수정


 
>### **5. 자신의 Branch push 후 develop Branch 합치길(Merge) 원하면 pull Request를 작성해주세요**
>######  pull request 할 때에는 간단한 설명과 어떤게 바뀌었는지 알 수 있는 간단한 제목이 필요해요 
> ###### pull request 한 후에는 branch를 삭제해주는게 좋은데 저희는 브랜치 2개 정도 사용해서 하나는 git pull로 계속 동기화 해보고 
> ###### 다른 브랜치는 브랜치 삭제하고 다시 가져오는걸로... 원하는 걸로 사용하시면 되겠습니다. 
> ![image](https://user-images.githubusercontent.com/84506708/151741104-c177cddd-2821-4c6a-a92f-f2be6d3760be.png)
> ![image](https://user-images.githubusercontent.com/84506708/151741190-dc680d59-370d-40d8-8d89-9b803bf29a79.png)
> ![image](https://user-images.githubusercontent.com/84506708/151741253-0e271933-fe7d-461f-9774-18e908806ce5.png)

>### **6. develop branch 가 merge 되면 git pull 을 해주세요 안하면 차후 merge 때 coflict 가 일어 날 수 있어요** 
> 
>###
>> Image

# **apllication-properties**
```properties
## apllication-properties
### apllication-properties 입니다. github에는 배포를 하기위해서 올라가 있지 
# 않고 developer contributor 전용 입니다. 
### src/main/resources에 추가해주세요
# 차후 본인 환경에 맞게 조절 해주세요 

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url=jdbc:mysql://localhost:3306/gdsc_web?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul
spring.datasource.username= {username}
spring.datasource.password= {password}
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.server.port = 8080
spring.server.servlet.context-path= /
spring.server.servlet.encoding.charset = UTF-8
spring.server.servlet.enabled=true
spring.server.servlet.force=true

spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
spring.devtools.restart.enabled = true
spring.jpa.open-in-view=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.naming.use-new-id-generator-mappings= false
spring.jackson.serialization.fail-on-empty-beans= false

```