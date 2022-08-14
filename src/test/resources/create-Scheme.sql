CREATE TABLE category
(
    category_id   INT AUTO_INCREMENT NOT NULL,
    category_name VARCHAR(30)        NOT NULL,
    modified_at   datetime           NULL,
    upload_date   datetime           NULL,
    CONSTRAINT pk_category PRIMARY KEY (category_id)
);


CREATE TABLE likes
(
    like_id     INT AUTO_INCREMENT NOT NULL,
    user_id     INT                NULL,
    post_id     BIGINT             NULL,
    upload_date datetime           NULL,
    CONSTRAINT pk_likes PRIMARY KEY (like_id)
);




CREATE TABLE member
(
    user_id           VARCHAR(64)  NOT NULL,
    username          VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    email_verified_yn VARCHAR(1),
    profile_image_url VARCHAR(512),
    role              VARCHAR(255),
    provider_type     VARCHAR(255),
    member_info_id    INT,
    modified_at       TIMESTAMP,
    upload_date       TIMESTAMP,
    CONSTRAINT pk_member PRIMARY KEY (user_id)
);



CREATE TABLE member_info
(
    member_info_id  INT AUTO_INCREMENT NOT NULL,
    user_id         VARCHAR(64),
    gdsc_generation INT,
    introduce       VARCHAR(255),
    nickname        VARCHAR(30),
    phone_number    VARCHAR(30),
    major           VARCHAR(255),
    git_email       VARCHAR(30),
    studentid       VARCHAR(30),
    position_type   INT,
    hash_tag        VARCHAR(255),
    git_url         VARCHAR(255),
    blog_url        VARCHAR(255),
    etc_url         VARCHAR(255),
    birthday        TIMESTAMP,
    modified_at     TIMESTAMP,
    upload_date     TIMESTAMP,
    CONSTRAINT pk_memberinfo PRIMARY KEY (member_info_id)
);


CREATE TABLE member_scrap_post
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_id     INT,
    post_id     BIGINT,
    upload_date TIMESTAMP,
    CONSTRAINT pk_memberscrappost PRIMARY KEY (id)
);



CREATE TABLE post
(
    post_id              BIGINT AUTO_INCREMENT NOT NULL,
    image_path           VARCHAR(255),
    title                VARCHAR(255),
    content              CLOB,
    view                 INT     DEFAULT 0     NOT NULL,
    user_id              INT                   NOT NULL,
    tmp_store            BOOLEAN DEFAULT FALSE,
    category_category_id INT,
    post_hash_tags       VARCHAR(255),
    modified_at          TIMESTAMP,
    upload_date          TIMESTAMP,
    blocked              BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_post PRIMARY KEY (post_id)
);


CREATE TABLE post_block
(
    block_post_id BIGINT AUTO_INCREMENT NOT NULL,
    post_id       BIGINT,
    upload_date   TIMESTAMP,
    CONSTRAINT pk_postblock PRIMARY KEY (block_post_id)
);



CREATE TABLE user_refresh_token
(
    user_id       VARCHAR(64) NOT NULL,
    refresh_token VARCHAR(256),
    CONSTRAINT pk_user_refresh_token PRIMARY KEY (user_id)
);



CREATE TABLE warn_description
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    title             VARCHAR(255),
    content           CLOB,
    from_user_user_id VARCHAR(64),
    to_user           VARCHAR(64),
    upload_date       TIMESTAMP,
    CONSTRAINT pk_warndescription PRIMARY KEY (id)
);

ALTER TABLE warn_description
    ADD CONSTRAINT FK_WARNDESCRIPTION_ON_FROMUSER_USER FOREIGN KEY (from_user_user_id) REFERENCES member (user_id);

ALTER TABLE warn_description
    ADD CONSTRAINT FK_WARNDESCRIPTION_ON_TO_USER FOREIGN KEY (to_user) REFERENCES member (user_id);

ALTER TABLE post
    ADD CONSTRAINT FK_POST_ON_CATEGORY_CATEGORY FOREIGN KEY (category_category_id) REFERENCES category (category_id);

ALTER TABLE post
    ADD CONSTRAINT FK_POST_ON_USER FOREIGN KEY (user_id) REFERENCES member_info (member_info_id);

ALTER TABLE member_scrap_post
    ADD CONSTRAINT FK_MEMBERSCRAPPOST_ON_POST FOREIGN KEY (post_id) REFERENCES post (post_id);

ALTER TABLE member_scrap_post
    ADD CONSTRAINT FK_MEMBERSCRAPPOST_ON_USER FOREIGN KEY (user_id) REFERENCES member_info (member_info_id);

ALTER TABLE post_block
    ADD CONSTRAINT FK_POSTBLOCK_ON_POST FOREIGN KEY (post_id) REFERENCES post (post_id);
    ALTER TABLE member
    ADD CONSTRAINT uc_member_user UNIQUE (user_id);

ALTER TABLE member
    ADD CONSTRAINT FK_MEMBER_ON_MEMBER_INFO FOREIGN KEY (member_info_id) REFERENCES member_info (member_info_id);

ALTER TABLE member_info
    ADD CONSTRAINT uc_memberinfo_nickname UNIQUE (nickname);

ALTER TABLE member_info
    ADD CONSTRAINT FK_MEMBERINFO_ON_USER FOREIGN KEY (user_id) REFERENCES member (user_id);
ALTER TABLE category
    ADD CONSTRAINT uc_category_category_name UNIQUE (category_name);
ALTER TABLE likes
    ADD CONSTRAINT FK_LIKES_ON_POST FOREIGN KEY (post_id) REFERENCES post (post_id);

ALTER TABLE likes
    ADD CONSTRAINT FK_LIKES_ON_USER FOREIGN KEY (user_id) REFERENCES member_info (member_info_id);

ALTER TABLE user_refresh_token
    ADD CONSTRAINT uc_user_refresh_token_user UNIQUE (user_id);
