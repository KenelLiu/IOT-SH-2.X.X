nohup java -Dspring.config.location=file:./application.yml -jar springboot-1.0.0.jar &

# nohup java -Xms128m -Xmx1024m -jar xxx.jar >/dev/null 2>&1 &
# -XX:MetaspaceSize=128m：元空间默认大小
# -XX:MaxMetaspaceSize=256m：元空间最大大小
# -Xms2048m：堆初始值
# -Xmx2048m：堆最大值
# -Xmn256m：新生代大小
# -Xss256k：栈最大深度大小
# -XX:SurvivorRatio=8：新生代分区比例 8:2
# -XX:+UseConcMarkSweepGC：指定使用的垃圾收集器，这里使用CMS收集器
# -XX:+PrintGCDetails：打印详细的GC日志
# -XX:+UseCodeCacheFlushing
# -XX:+UseParallelGC 
# -XX:-UseAdaptiveSizePolicy  
# -XX:ReservedCodeCacheSize=512m
# http://180.167.232.10:38000/
#-XX:PermSize/-XX:MaxPermSize
#-XX:MetaspaceSize/-XX:MaxMetaspaceSize
#echo 0 | sudo tee /proc/sys/kernel/yama/ptrace_scope 在linux使用jdk jmap 需要设置ptrace_scope=0
#example:
#nohup java -Xmx8g -Xms8g  -XX:+UseCodeCacheFlushing -XX:+UseG1GC -XX:MetaspaceSize=2G -XX:MaxMetaspaceSize=2G  -XX:ReservedCodeCacheSize=1536m