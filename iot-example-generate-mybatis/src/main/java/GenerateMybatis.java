import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iot.sh.util.file.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

@Slf4j
public class GenerateMybatis {

	public static void main(String[] args){
		try {
			GenerateMybatis generatorSqlmap = new GenerateMybatis();
			/**执行*/
			generatorSqlmap.generator();
			log.info("执行完成。。。。。。");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	/**获取映射文件*/
	public void generator() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		/**指定逆向工程配置文件*/
		String file=PathUtil.getFilePath("./iot-example-generate-mybatis/generate-mybatis-config.xml");
		log.info("读取配置mybatis config={}",file);
		File configFile = new File(file);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		/**MyBatisGenerator对象*/
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
}
