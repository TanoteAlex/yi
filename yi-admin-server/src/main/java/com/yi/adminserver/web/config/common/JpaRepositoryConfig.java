package com.yi.adminserver.web.config.common;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "adminEntityManagerFactory", transactionManagerRef = "adminTransactionManager", basePackages = {
		"com.yi.core.*.dao", })
// 设置Repository所在位置
public class JpaRepositoryConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private DataSource adminDataSource;

	/**
	 * 我们通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
	 *
	 * @return
	 */
	@Bean(name = "adminEntityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean adminEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(adminDataSource).properties(getVendorProperties())
				.packages("com.yi.core.*.domain.entity") // 设置实体类所在位置
				.persistenceUnit("adminPersistenceUnit").build();
	}

	private Map<String, Object> getVendorProperties() {
		HibernateSettings hibernateSettings = new HibernateSettings();
		return jpaProperties.getHibernateProperties(hibernateSettings);
	}

	/**
	 * EntityManagerFactory类似于Hibernate的SessionFactory,mybatis的SqlSessionFactory
	 * 总之,在执行操作之前,我们总要获取一个EntityManager,这就类似于Hibernate的Session, mybatis的sqlSession.
	 *
	 * @param builder
	 * @return
	 */
	@Bean(name = "adminEntityManagerFactory")
	@Primary
	public EntityManagerFactory adminEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return this.adminEntityManagerFactoryBean(builder).getObject();
	}

	/**
	 * 配置事务管理器
	 *
	 * @return
	 */
	@Bean(name = "adminTransactionManager")
	@Primary
	public PlatformTransactionManager writeTransactionManager(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(adminEntityManagerFactory(builder));
	}
}
