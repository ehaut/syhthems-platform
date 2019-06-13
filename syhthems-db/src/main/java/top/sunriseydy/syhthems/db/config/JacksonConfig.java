package top.sunriseydy.syhthems.db.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.support.NullValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import top.sunriseydy.syhthems.db.model.BaseModel;

import java.io.IOException;

/**
 * Jackson配置类
 *
 * @author SunriseYDY
 * @date 2019-03-15 18:30
 */
@Configuration
public class JacksonConfig {
    private final Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Autowired
    public JacksonConfig(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        this.jackson2ObjectMapperBuilder = jackson2ObjectMapperBuilder;
    }

    /**
     * 自定义Jackson2ObjectMapperBuilderCustomizer
     * <p>采取内部静态类是防止Spring Bean循环依赖问题</p>
     *
     * @see Jackson2ObjectMapperBuilderCustomizer
     */
    @Configuration
    static class CustomJackson2ObjectMapperBuilderCustomizer {
        @Bean
        public Jackson2ObjectMapperBuilderCustomizer customJackson() {
            return builder -> builder
                    // 在序列化时将日期转化为时间戳
                    .featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .featuresToEnable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                    // 在序列化枚举对象时使用toString方法
                    .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                    // 在反序列化枚举对象时使用toString方法
                    .featuresToEnable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                    .featuresToEnable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                    // 日期和时间格式："yyyy-MM-dd HH:mm:ss"
                    // .simpleDateFormat(BaseConstants.DATETIME_FORMAT)
                    .createXmlMapper(false)
                    .timeZone("GMT+8:00")
                    ;
        }
    }

    /**
     * 主要的ObjectMapper，用于Controller数据，过滤掉Model的基础字段。
     *
     * @return ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper commonObjectMapper() {
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        // 添加字段过滤器
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider
                .addFilter("cache", SimpleBeanPropertyFilter
                        .serializeAllExcept(BaseModel.CREATE_TIME,
                                BaseModel.LAST_UPDATE_TIME,
                                BaseModel.LAST_UPDATE_BY));
        objectMapper.setFilterProvider(filterProvider);
        return objectMapper;
    }

    /**
     * 不过滤基础字段的通用 ObjectMapper
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper withoutFilterObjectMapper() {
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        // 添加字段过滤器,不进行过滤
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider
                .addFilter("cache", SimpleBeanPropertyFilter
                        .serializeAll());
        objectMapper.setFilterProvider(filterProvider);
        return objectMapper;
    }

    /**
     * Redis缓存使用的ObjectMapper，包含了字段类型定义和基础字段
     *
     * @return ObjectMapper
     * @see GenericJackson2JsonRedisSerializer
     */
    @Bean
    public ObjectMapper cacheObjectMapper() {
        ObjectMapper objectMapper = this.withoutFilterObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.registerModule(new SimpleModule().addSerializer(new CustomNullValueSerializer(null)));
        return objectMapper;
    }

    /**
     * 自定义的序列化器，用于添加字段的类型定义
     *
     * @see GenericJackson2JsonRedisSerializer
     */
    private class CustomNullValueSerializer extends StdSerializer<NullValue> {

        private static final long serialVersionUID = 1999052150548658808L;
        private final String classIdentifier;

        /**
         * @param classIdentifier can be {@literal null} and will be defaulted to {@code @class}.
         */
        CustomNullValueSerializer(@Nullable String classIdentifier) {

            super(NullValue.class);
            this.classIdentifier = StringUtils.hasText(classIdentifier) ? classIdentifier : "@class";
        }

        @Override
        public void serialize(NullValue value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {

            jgen.writeStartObject();
            jgen.writeStringField(classIdentifier, NullValue.class.getName());
            jgen.writeEndObject();
        }
    }
}
