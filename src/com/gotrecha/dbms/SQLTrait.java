package com.gotrecha.dbms;

import com.google.common.collect.Lists;
import com.gotrecha.dbms.connection.ConnectionManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dustin on 4/26/14.
 */
public interface SQLTrait {
	public static final int QUERY = 1;
	public static final int UPDATE = 2;

	default Object executeTypedStatement(String sql,Object[] parameters,Class output) throws SQLException,ClassNotFoundException,IllegalAccessException,InstantiationException{
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);

		setParameters(statement,parameters);

		ResultSet rs = statement.executeQuery();

		List<String> columnNames = Lists.newArrayList();
		List values = Lists.newArrayList();

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		for(int i=1;i<=columnCount;i++){
			columnNames.add(metaData.getColumnName(i));
		}

		while(rs.next()){
			Object outputObj = output.newInstance();
			for(int i=1;i<=columnCount;i++){
				String objectName = columnNames.get(i-1);
				Object value = rs.getObject(i);
				Field fieldToUse = null;

				for (Field field : output.getDeclaredFields()) {
					Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
					for(Annotation annotation:fieldAnnotations){
						if(annotation instanceof com.gotrecha.dbms.annotations.Field){
							String classFieldName = ((com.gotrecha.dbms.annotations.Field) annotation).sqlFieldName();
							if(classFieldName.equals(objectName)){
								fieldToUse = field;
								fieldToUse.setAccessible(true);
								fieldToUse.set(outputObj,value);
								fieldToUse.setAccessible(false);
								break;
							}
						}
					}
				}
			}
			values.add(outputObj);
		}
		rs.close();
		ConnectionManager.releaseConnection(connection);
//		if(values.size() == 1){
//			return values.get(0);
//		}
		return values;
	}

	default int executeUpdate(){

		return 0;
	}

	default void setParameters(PreparedStatement statement, Object[] parameters) throws SQLException{
		int i=1;
		for(Object param:parameters){
			statement.setObject(i,param);
			i++;
		}
	}
}
