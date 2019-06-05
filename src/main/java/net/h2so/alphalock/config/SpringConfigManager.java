package net.h2so.alphalock.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;

/**
 * @Description Spring配置
 * @Auther mikicomo
 * @Date 2019-06-04 15:51
 */
@Configuration
@ComponentScan(basePackages = "net.h2so.alphalock")
@PropertySource(value = { "classpath:lock.properties"}, ignoreResourceNotFound = true)
@EnableAspectJAutoProxy
public class SpringConfigManager {

    @Autowired
    private AlphaConfig alphaConfig;

    @Bean
    public RedissonClient redissonClient() throws Exception {
        Config config = new Config();
        if (alphaConfig.getClusterNodeAddress() != null) {
            config.useClusterServers().setPassword(alphaConfig.getPassword())
                    .addNodeAddress(alphaConfig.getClusterNodeAddress());
        } else {
            config.useSingleServer().setAddress(alphaConfig.getAddress())
                    .setDatabase(alphaConfig.getDatabase())
                    .setPassword(alphaConfig.getPassword());
        }

        Codec codec = (Codec) ClassUtils.forName(alphaConfig.getCodec(), ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec);
        config.setEventLoopGroup(new NioEventLoopGroup());

        return Redisson.create(config);
    }
}
