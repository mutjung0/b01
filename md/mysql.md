1. MySQL 스키마&계정 생성
워크벤치 root계정으로 접속해서
MANAGEMENT>Users and Privileges

Add Account>

Login Name: webuser
Limit to Hosts Matching: % > localhost
Password: webuser

Administrative Roles: 모두다선택

SCHEMAS > 


CREATE SCHEMA `webuser` ;

Schema Privileges > Add Entry... > Selected schema: webuser

2. build.gradle
3. application.properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/webuser?createDatabaseIfNotExist=true

spring.datasource.username=webuser
spring.datasource.password=webuser

#spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQLDialect


2024-11-10 22:22:02.119 ERROR 2948 --- [    Test worker] o.s.boot.SpringApplication               : Application run failed


org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'dataSourceScriptDatabaseInitializer' defined in class path resource [org/springframework/boot/autoconfigure/sql/init/DataSourceInitializationConfiguration.class]: Unsatisfied dependency expressed through method 'dataSourceScriptDatabaseInitializer' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception; nested exception is java.lang.IllegalStateException: Cannot load driver class: com.mysql.cj.jdbc.Driver

DataSourceTests 실행 : gradle 라이브러리 수정 안하고 했더니 드라이브가 없다고 나옴
