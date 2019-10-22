package com.yi.adminserver.web.config.common;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 定时任务线程池
 * 
 * @author xuyh
 *
 */
@EnableScheduling
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

	/**
	 * 
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// 参数传入一个size为20的线程池
		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(50));
	}

}
