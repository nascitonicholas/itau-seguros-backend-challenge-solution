# itau-seguros-backend-challenge-solution
Repository created with the aim of solving Itau's public challenge for the insurance area. Challenge -> https://github.com/itausegdev/backend-challenge

http://localhost:3000/d/cdibucdgm6q68b/insurance-products?orgId=1

global:
scrape_interval: 15s

scrape_configs:
- job_name: 'prometheus'
  static_configs:
    - targets: ['localhost:9090']

- job_name: 'itau-seguros-backend-challenge-solution'
  metrics_path: '/actuator/prometheus'
  static_configs:
    - targets: ['172.18.64.1:8080']


spring.application.name=insurance-product-challenge

# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/segurosdb?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuracoes do JPA
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Redis
spring.redis.host=localhost
spring.redis.port=6379

# Micrometer
management.endpoints.web.exposure.include=*
management.endpoint.metrics.enabled=true
management.metrics.export.prometheus.enabled=true

# Configuracao basica do log
logging.level.root=INFO
logging.level.br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge=DEBUG