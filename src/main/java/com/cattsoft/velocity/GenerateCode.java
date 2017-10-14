package com.cattsoft.velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import com.cattsoft.generation.Context;
import com.cattsoft.generation.GeneratorConfiguration;
import com.cattsoft.generation.JdbcConnection;
import com.cattsoft.generation.Table;

/**
 * Description: 根据表生成代码<br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author yuchunfang
 * 
 */
public class GenerateCode {
	/**
	 * 日志对象
	 */
	private Log logger = LogFactory.getLog(getClass());
	/**
	 * 用户当前目录
	 */
	private static final String USER_DIR = System.getProperty("user.dir");
	/**
	 * 文件分隔符
	 */
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * 
	 */
	private static GeneratorConfiguration generatorConfiguration;

	/**
	 * 
	 */
	private static Properties properties;

	/**
	 * 初始化
	 * 
	 * @throws IOException
	 * @throws JAXBException
	 */
	public GenerateCode() throws IOException, JAXBException {
		InputStream velocity = GenerateCode.class.getClassLoader().getResourceAsStream("velocity.properties");
		if (velocity != null) {
			Properties p = new Properties();
			p.load(velocity);
			velocity.close();
			Velocity.init(p);
		}
		InputStream config = GenerateCode.class.getClassLoader().getResourceAsStream("config.properties");
		if (config != null) {
			Properties p = new Properties();
			p.load(config);
			config.close();
			properties = p;
		}
		InputStream generatorConfig = GenerateCode.class.getClassLoader().getResourceAsStream("generatorConfig.xml");
		if (generatorConfig != null) {
			JAXBContext jaxbContext = JAXBContext.newInstance(GeneratorConfiguration.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			generatorConfiguration = (GeneratorConfiguration) unmarshaller.unmarshal(generatorConfig);
			generatorConfig.close();
		}
	}

	/**
	 * Description: 代码生成工具的主函数<br>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GenerateCode generateCode = new GenerateCode();
			if (CollectionUtils.isNotEmpty(generatorConfiguration.getList())) {
				for (Context context : generatorConfiguration.getList()) {
					generateCode.generate(context);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 代码生成<br>
	 * 
	 * @param context
	 *            上下文
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void generate(Context context) throws IOException, JAXBException {
		if (logger.isDebugEnabled()) {
			logger.debug("==========开始生成==========");
		}
		// 获取表名
		List<Table> list = context.getList();
		// 循环生成代码
		for (Table table : list) {
			// 设置上下文
			VelocityContext velocityContext = getContext(table);
			generateQB(table.getTableName(), velocityContext);
			generateComponent(table.getTableName(), velocityContext);
			generateService(table.getTableName(), velocityContext);
			generateServiceImpl(table.getTableName(), velocityContext);
			generateWeb(table.getTableName(), velocityContext);
			generateXxxMain(table.getTableName(), velocityContext);
			generateXxxList(table.getTableName(), velocityContext);
			generateXxxCreate(table.getTableName(), velocityContext);
			generateXxxUpdate(table.getTableName(), velocityContext);
			generateXxxDetail(table.getTableName(), velocityContext);
		}
		try {
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			File configFile = new File(USER_DIR + FILE_SEPARATOR + "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "generatorConfig.xml");
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			if (logger.isDebugEnabled()) {
				for (String string : warnings) {
					logger.debug(string);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (XMLParserException e) {
			e.printStackTrace();
		}
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("==========生成结束==========");
		}
	}

	/**
	 * Description: 生成QB<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateQB(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("XxxQB.vm");
			//
			File directory = new File(properties.getProperty("javaDestination") + properties.getProperty("rootPackage") + properties.getProperty("module") + properties.getProperty("entity")
					+ FILE_SEPARATOR + "querybean");
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToUpperCase(tableName) + "QB.java")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成component层代码<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateComponent(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("XxxComponent.vm");
			//
			File directory = new File(properties.getProperty("javaDestination") + properties.getProperty("rootPackage") + properties.getProperty("module") + properties.getProperty("component"));
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToUpperCase(tableName) + "Component.java")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成service接口代码<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateService(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("XxxService.vm");
			//
			File directory = new File(properties.getProperty("javaDestination") + properties.getProperty("rootPackage") + properties.getProperty("module") + properties.getProperty("service"));
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToUpperCase(tableName) + "Service.java")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成service实现类代码<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateServiceImpl(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("XxxServiceImpl.vm");
			//
			File directory = new File(properties.getProperty("javaDestination") + properties.getProperty("rootPackage") + properties.getProperty("module") + properties.getProperty("service"));
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToUpperCase(tableName) + "ServiceImpl.java")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成Action代码<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateWeb(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("XxxAction.vm");
			//
			File directory = new File(properties.getProperty("javaDestination") + properties.getProperty("rootPackage") + properties.getProperty("module") + properties.getProperty("web"));
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToUpperCase(tableName) + "Action.java")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成主页面代码<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateXxxMain(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("xxxMain.vm");
			//
			File directory = new File(properties.getProperty("jspDestination") + properties.getProperty("rootFolder") + properties.getProperty("module") + FILE_SEPARATOR
					+ properties.getProperty("function") + FILE_SEPARATOR);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToLowerCase(convertFirstLetterToUpperCase(tableName)) + "Main.jsp")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成列表页面<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateXxxList(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("xxxList.vm");
			//
			File directory = new File(properties.getProperty("jspDestination") + properties.getProperty("rootFolder") + properties.getProperty("module") + FILE_SEPARATOR
					+ properties.getProperty("function") + FILE_SEPARATOR);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToLowerCase(convertFirstLetterToUpperCase(tableName)) + "List.jsp")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成增加页面<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateXxxCreate(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("xxxCreate.vm");
			//
			File directory = new File(properties.getProperty("jspDestination") + properties.getProperty("rootFolder") + properties.getProperty("module") + FILE_SEPARATOR
					+ properties.getProperty("function") + FILE_SEPARATOR);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToLowerCase(convertFirstLetterToUpperCase(tableName)) + "Create.jsp")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成修改页面<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateXxxUpdate(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("xxxUpdate.vm");
			//
			File directory = new File(properties.getProperty("jspDestination") + properties.getProperty("rootFolder") + properties.getProperty("module") + FILE_SEPARATOR
					+ properties.getProperty("function") + FILE_SEPARATOR);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToLowerCase(convertFirstLetterToUpperCase(tableName)) + "Update.jsp")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 生成明细页面<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @param context
	 *            上下文
	 */
	private void generateXxxDetail(String tableName, VelocityContext context) {
		try {
			// 获取模板
			Template template = Velocity.getTemplate("xxxDetail.vm");
			//
			File directory = new File(properties.getProperty("jspDestination") + properties.getProperty("rootFolder") + properties.getProperty("module") + FILE_SEPARATOR
					+ properties.getProperty("function") + FILE_SEPARATOR);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, convertFirstLetterToLowerCase(convertFirstLetterToUpperCase(tableName)) + "Detail.jsp")));
			// 合并
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseErrorException e) {
			e.printStackTrace();
		}
		catch (MethodInvocationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 去除字符串的下划线，并把每部分的首字母转换为大写<br>
	 * 
	 * @param string
	 *            字符串
	 * @return 转换后的字符串
	 */
	public String convertFirstLetterToUpperCase(String string) {
		String newTableName = "";
		String[] str = string.split("_");
		for (String part : str) {
			part = part.toLowerCase();
			if (part.length() == 1) {
				newTableName += part.toUpperCase();
			}
			else if (part.length() > 1) {
				String firstLetter = part.substring(0, 1).toUpperCase();
				String otherLetter = part.substring(1);
				newTableName += firstLetter + otherLetter;
			}
		}
		return newTableName;
	}

	/**
	 * Description: 把字符串的首字母转换为小写<br>
	 * 
	 * @param string
	 *            字符串
	 * @return 转换后的字符串
	 */
	public String convertFirstLetterToLowerCase(String string) {
		String newString = "";
		if (string.length() == 1) {
			newString = string.toLowerCase();
		}
		else if (string.length() > 1) {
			String firstLetter = string.substring(0, 1).toLowerCase();
			String otherLetter = string.substring(1);
			newString = firstLetter + otherLetter;
		}
		return newString;
	}

	/**
	 * Description: 设置上下文<br>
	 * 
	 * @param tableName
	 *            表名
	 * @param properties
	 *            配置文件
	 * @return 上下文
	 * @throws JAXBException
	 * @throws IOException
	 */
	private VelocityContext getContext(Table table) throws IOException, JAXBException {
		VelocityContext velocityContext = new VelocityContext();
		List<String> primaryKeys = getPrimaryKey(table.getSchema(), table.getTableName());
		if (CollectionUtils.isNotEmpty(primaryKeys)) {
			velocityContext.put("hasPrimaryKey", true);
			velocityContext.put("primaryKeys", primaryKeys);
		}
		else {
			velocityContext.put("hasPrimaryKey", false);
		}
		List<Column> columns = getColumn(table.getSchema(), table.getTableName(), primaryKeys);
		velocityContext.put("columns", columns);
		String rootPackage = convertRootPackage(properties.getProperty("rootPackage"));
		if (StringUtils.isNotBlank(rootPackage)) {
			velocityContext.put("rootPackage", rootPackage);
		}
		velocityContext.put("userName", System.getProperty("user.name"));
		GenerateCode generateCode = new GenerateCode();
		velocityContext.put("generateCode", generateCode);
		velocityContext.put("module", properties.getProperty("module"));
		velocityContext.put("convert", properties.getProperty("convert"));
		velocityContext.put("upperTableName", convertFirstLetterToUpperCase(table.getTableName()));
		velocityContext.put("lowerTableName", convertFirstLetterToLowerCase(convertFirstLetterToUpperCase(table.getTableName())));
		return velocityContext;
	}

	/**
	 * Description: 获取数据库连接<br>
	 * 
	 * @return 数据库连接
	 */
	private Connection getConnection() {
		Connection connection = null;
		try {
			List<Context> list = generatorConfiguration.getList();
			JdbcConnection jdbcConnection = null;
			if (CollectionUtils.isNotEmpty(list)) {
				for (Context context : list) {
					if ("dev".endsWith(context.getId())) {
						jdbcConnection = context.getJdbcConnection();
					}
				}
			}
			Class.forName(jdbcConnection.getDriverClass());
			connection = DriverManager.getConnection(jdbcConnection.getConnectionURL(), jdbcConnection.getUserId(), jdbcConnection.getPassword());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Description: 获取表的主键信息<br>
	 * 
	 * @param schema
	 *            模式
	 * @param tableName
	 *            表名
	 * @return 主键信息
	 */
	private List<String> getPrimaryKey(String schema, String tableName) {
		List<String> list = new ArrayList<String>();
		try {
			Connection connection = getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getPrimaryKeys(null, schema.toUpperCase(), tableName.toUpperCase());
			while (resultSet.next()) {
				if (logger.isDebugEnabled()) {
					logger.debug(tableName.toUpperCase() + "表的主键" + resultSet.getString("COLUMN_NAME"));
				}
				list.add(convertFirstLetterToUpperCase(resultSet.getString("COLUMN_NAME")));
			}
			resultSet.close();
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Description: 获取表的列信息<br>
	 * 
	 * @param schema
	 *            模式
	 * @param tableName
	 *            表名
	 * @param primaryKeys
	 *            主键
	 * @return 列信息
	 */
	private List<Column> getColumn(String schema, String tableName, List<String> primaryKeys) {
		List<Column> list = new ArrayList<Column>();
		try {
			Connection connection = getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, schema.toUpperCase(), tableName.toUpperCase(), null);
			Column column = null;
			while (resultSet.next()) {
				if (logger.isDebugEnabled()) {
					logger.debug(tableName.toUpperCase() + "表的列名" + resultSet.getString("COLUMN_NAME"));
				}
				column = new Column();
				column.setColumnName(convertFirstLetterToUpperCase(resultSet.getString("COLUMN_NAME")));
				column.setColumnSize(resultSet.getInt("COLUMN_SIZE"));
				column.setNullable(resultSet.getInt("NULLABLE"));
				column.setRemarks(resultSet.getString("REMARKS"));
				if (logger.isDebugEnabled()) {
				}
				column.setNullable(resultSet.getInt("NULLABLE"));
				column.setTypeName(resultSet.getString("TYPE_NAME"));
				if (primaryKeys.contains(convertFirstLetterToUpperCase(resultSet.getString("COLUMN_NAME")))) {
					column.setIsprimaryKey(true);
				}
				else {
					column.setIsprimaryKey(false);
				}
				list.add(column);
			}
			resultSet.close();
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Description: 把配置文件中的根目录转换为xxx.xxx的形式<br>
	 * 
	 * @param rootPackage
	 *            根目录
	 * @return 转换后的根目录
	 */
	private String convertRootPackage(String rootPackage) {
		String newRootPackage = "";
		String[] str = rootPackage.split("/");
		for (String part : str) {
			part = part.toLowerCase();
			if (StringUtils.isNotBlank(part)) {
				newRootPackage += part + ".";
			}
		}
		return newRootPackage;
	}
}
