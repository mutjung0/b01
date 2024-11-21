# MySQL
## 1. 스키마 생성
Navigator>SCHEMAS > 마우스 오른쪽 클릭> Create Schema...
Name: webdb
[Apply]



```sql
CREATE SCHEMA `webdb` ;
```
## 2. 유저 생성 및 권한
Navigator>MANAGEMENT>Users and Privileges>
[Add Account] 
Login Name: webuser
{Tab}Schema Privileges>[Add Entry...] Selected schema: webdb
Privileges 선택해줌
([Select "ALL"])
[Apply]