## Workshop 24

export MYSQL_USER = ?
export MYSQL_PASSWORD = ?

## Configure Spriing Boot App to connection to Railway

2. Make sure the application.properties setup with the following attributes:

```

spring.datasource.url=${MYSQL_APP_URL} including 'jdbc:' at the front
spring.datasource.username=${MYSQL_APP_USER}
spring.datasource.password=${MYSQL_APP_PW}
#old driver 
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```