package com.example.AsyncThreadHell.taskexecutor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
public class ExecutorConfiguration implements AsyncConfigurer {

    //////////////////////////////////////////////////////////////////////////////////
    // ENTIRE BODY OF THIS CLASS IS OPTIONAL, ONLY CLASS ANNOTATIONS ABOVE REQUIRED //
    //////////////////////////////////////////////////////////////////////////////////

//    private static Logger logger = LoggerFactory.getLogger(MagicMosaicApplication.class);
//
//    @Value("${spring.task.execution.thread-name-prefix}")
//    String namePrefix;
//
//    @Value("${spring.task.execution.pool.core-size}")
//    int coreSize;
//
//    @Value("${spring.task.execution.pool.max-size}")
//    int maxSize;
//
//    @Bean
//    public ThreadPoolTaskExecutor taskExecutor() {
//        logger.info("[INFO] Initialize ThreadPoolTaskExecutor (ExecutorConfiguration) with pool size = "+maxSize);
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setThreadNamePrefix("TaskExecutor-");
//        executor.setCorePoolSize(coreSize);     //default: 1
//        executor.setMaxPoolSize(maxSize);       // default: Integer.MAX_VALUE
//        //executor.setQueueCapacity(500);       // default: Integer.MAX_VALUE
//        //executor.setKeepAliveSeconds(120);    // default: 60 seconds
//        return executor;
//    }

}

