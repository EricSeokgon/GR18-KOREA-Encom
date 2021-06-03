package ncomegf.bpw._config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ncomegf")
public class PersistenceJPAConfig{

   @Resource(name="db1")
   DataSource dataSource;
   
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em 
        = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource);
      em.setPackagesToScan(new String[] { "ncomegf" });

      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(additionalProperties());

      return em;
   }
//   @Bean
//   public DataSource dataSource(){
//	   
//
////	   DataSource dataSource
////		BasicDataSource basicDataSource = new BasicDataSource();
////		basicDataSource.setDriverClassName(Config.getInstance().getString("db1.driver"));
////		basicDataSource.setUrl(Config.getInstance().getString("db1.url"));
////		basicDataSource.setUsername(Config.getInstance().getString("db1.id"));
////		basicDataSource.setPassword(Config.getInstance().getString("db1.pwd"));
////		basicDataSource.setMaxActive(Config.getInstance().getInt("db1.maxActive"));
////		basicDataSource.setMaxIdle(Config.getInstance().getInt("db1.maxIdle"));
////		basicDataSource.setMaxWait(Config.getInstance().getLong("db1.maxWait"));
//       return dataSource;
//   }
   
   @Bean
   public PlatformTransactionManager transactionManager() {
       JpaTransactionManager transactionManager = new JpaTransactionManager();
       transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

       return transactionManager;
   }
	
   
   //==========
	@Bean
	public TransactionInterceptor transactionAdvice() {
		NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();
		RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
		txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

		HashMap<String, TransactionAttribute> txMethods = new HashMap<String, TransactionAttribute>();
		txMethods.put("*", txAttribute);
		txAttributeSource.setNameMap(txMethods);

		return new TransactionInterceptor(transactionManager(), txAttributeSource);
	}

	@Bean
	public Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* ncomegf.bpw..*Service.*(..))");

		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
	
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
       return new PersistenceExceptionTranslationPostProcessor();
   }

   Properties additionalProperties() {
       Properties properties = new Properties();
       properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
       properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
          
       return properties;
   }

}
