package top.sunriseydy.syhthems.db.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

import static org.apache.ibatis.io.Resources.getResourceAsStream;

/**
 * 运行 Mybatis Generator
 *
 * @author SunriseYDY
 * @date 2019-03-07 09:38
 */
public class Generator {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config =
                cp.parseConfiguration(getResourceAsStream("generator/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println("-----Warning----- " + warning);
        }
    }
}
