-- 카테고리 정보
INSERT INTO category(Category_ID,Category_Name,MODIFIED_AT,uploadDate)VALUES
(0,
"Backend",
NOW(),NOW());

INSERT INTO category(Category_ID,Category_Name,MODIFIED_AT,uploadDate)VALUES
(1,
"Frontend",
NOW(),NOW());

INSERT INTO category(Category_ID,Category_Name,MODIFIED_AT,uploadDate)VALUES
(2,
"ML",
NOW(),NOW());

INSERT INTO category(Category_ID,Category_Name,MODIFIED_AT,uploadDate)VALUES
(3,
"Android",
NOW(),NOW());

INSERT INTO category(Category_ID,Category_Name,MODIFIED_AT,uploadDate)VALUES
(4,
"Design",
NOW(),NOW());

INSERT INTO category(Category_ID,Category_Name,MODIFIED_AT,uploadDate)VALUES
(5,
"Beginner",
NOW(),NOW());

INSERT INTO member
(USER_ID,
email,
EMAIL_VERIFIED_YN,
MODIFIED_AT,
password,
PROFILE_IMAGE_URL,
providerType,
role,
uploadDate,
username,
MEMBER_INFO_ID)
VALUES(
"test",
"test@mail.com",
"Y",
NOW(),
"password",
"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRj5S7Pg5Gi3ytwuz5n38i_U71U_E1oFuN5eg&usqp=CAU",
1,
1,
NOW(),
"test",
null);

INSERT INTO memberinfo
(MEMBER_INFO_ID,
POSITION_TYPE,
StudentID,
birthday,
GDSC_GENERATION,
GIT_EMAIL,
HashTag,
introduce,
major,
MODIFIED_AT,
nickname,
phoneNumber,
uploadDate,
USER_ID,
BLOG_URL,
ETC_URL,
GIT_URL)
VALUES
(1,
1,
20171910,
NOW(),
0,
"gudcks0305",
"gdsc,gdsc-web",
"임시소개",
"컴퓨터공학",
NOW(),
"임시이름",
"010-1234-5678",
NOW(),
"test",
"http://blog.com",
"http://etc.com",
"http://github.com");

/* Member member = Member.builder()
                .userId("test")
                .email("test@mail.com")
                .password("password")
                .username("test")
                .profileImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRj5S7Pg5Gi3ytwuz5n38i_U71U_E1oFuN5eg&usqp=CAU")
                .build();
        MemberInfo memberInfo = MemberInfo.builder()
                .birthday(now)
                .blogUrl("http://blog.com")
                .gitHubUrl("http://github.com")
                .etcUrl("http://etc.com")
                .generation(1)
                .hashTag("gdsc,gdsc-web")
                .major("컴퓨터공학")
                .nickname("임시이름")
                .phoneNumber("010-1234-5678")
                .StudentID("123456789")
                .introduce("임시소개")
                .member(member)
                .build();*/
