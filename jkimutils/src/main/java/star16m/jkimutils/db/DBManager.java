package star16m.jkimutils.db;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import star16m.jkimutils.Application.ApplicationConfig;

public class DBManager {
	
	private Configuration configuration;
	private SqlSessionFactory sqlSessionFactory;
	
	private static DBManager dbManager;
	private DBManager(Configuration configuration, SqlSessionFactory sqlSessionFactory){
		this.configuration = configuration;
		this.sqlSessionFactory = sqlSessionFactory;
	}
	public static synchronized DBManager getInstance(ApplicationConfig config) {
		if (dbManager == null) {
			DataSource dataSource = new PooledDataSource(config.getDriverName(), config.getDriverUrl(), config.getUserName(), config.getPassword());  
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			Environment environment = new Environment("Main", transactionFactory, dataSource);
			Configuration configuration = new Configuration(environment);
			configuration.addMappers("star16m.jkimutils.db.mapper");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
			dbManager = new DBManager(configuration, sqlSessionFactory);
		}
		return dbManager;
	}
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	public SqlSession getSQLSession() {
		SqlSession session = sqlSessionFactory.openSession();
		return session;
	}
	public SqlSessionFactory getSqlSessionFactory() {
		return this.sqlSessionFactory;
	}
}
