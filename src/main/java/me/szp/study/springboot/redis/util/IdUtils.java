package me.szp.study.springboot.redis.util;


import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@Accessors(chain = true)
@Setter
public class IdUtils {

    /**
     * 注入RedisUtils
     */
    private final RedisUtils redisUtils;

    /**
     * 默认key前缀
     */
    private static final String DEFAULT_PREFIX = "HYZS-ID-";

    /**
     * 默认日期格式
     */
    private static final String DEFAULT_DATE_PATTERN = "yyyyMMdd";


    private static final String DEFAULT_DATE_PATTERN = "yyyyMMdd";



    /** 
     * 默认自增序列长度
     */
    private static final int DEFAULT_LENGTH = 4;

    /**
     * key前缀
     */
    private String prefix;

    /**
     * 日期格式
     */
    private String datePattern;

    /**
     * 过期时间
     */
    private long expire;

    /**
     * 自增序列位数
     */
    private int length;

    public IdUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    /**
     * 生成id
     *
     * @return id
     */
    public int getId() {

        if (!redisUtils.hasKey(getKey())) {
            return 0;
        }

        return (int) redisUtils.get(getKey());
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(int id) {
        redisUtils.set(getKey(), id);
    }

    /**
     * 生成编号字符串
     *
     * @param id id
     * @return 编号字符串
     */
    public String generateId(int id) {
        return getDate().concat(frontCompWithZero(id, getLength()));
    }


    /**
     * 获取key
     *
     * @return key
     */
    private String getKey() {
        if (this.prefix == null) {
            this.prefix = DEFAULT_PREFIX;
        }
        return this.prefix.concat(getDate());
    }

    private String getDate() {
        if (this.datePattern == null) {
            this.datePattern = DEFAULT_DATE_PATTERN;
        }
        return LocalDate.now().format(DateTimeFormatter.ofPattern(this.datePattern));
    }

    /**
     * 　　* 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     * <p>
     * 　　* @param sourceDate
     * <p>
     * 　　* @param formatLength
     * <p>
     * 　　* @return 重组后的数据
     * <p>
     */

    private String frontCompWithZero(int num, int formatLength) {
        return String.format("%0" + formatLength + "d", num);
    }

    /**
     * 获取自增序列长度
     *
     * @return 自增序列长度
     */
    private int getLength() {
        return length == 0 ? DEFAULT_LENGTH : length;
    }

    public void clear() {
        redisUtils.del(getKey());
    }
}
