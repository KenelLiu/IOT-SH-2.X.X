1. 使用maven管理,将文件[openoffice相关jar ]进行本地安装
mvn install:install-file -Dfile=spire.pdf.free-5.1.0.jar -DgroupId=e-iceblue -DartifactId=spire.pdf.free -Dversion=5.1.0  -Dpackaging=jar
mvn install:install-file -Dfile=spire.doc.free-5.2.0.jar -DgroupId=e-iceblue -DartifactId=spire.doc.free -Dversion=5.2.0  -Dpackaging=jar
mvn install:install-file -Dfile=hutool-all-5.5.8.jar -DgroupId=com.hutool -DartifactId=hutool-all -Dversion=5.5.8  -Dpackaging=jar

