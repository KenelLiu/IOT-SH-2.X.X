@echo off
start javaw -Xms2g -Xmx2g -XX:+UseCodeCacheFlushing -XX:+UseG1GC -XX:ReservedCodeCacheSize=512m -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -Dspring.config.location=file:./application.yml -jar assess-1.0.jar
exit