package com.mkanchwala.lambda.copy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

public class LambdaFunctionHandler implements RequestHandler<S3Event, Object> {

    @Override
    public Object handleRequest(S3Event s3event, Context context) {
    	LambdaLogger logger = context.getLogger();
    	logger.log("Input: " + s3event);
    	S3EventNotificationRecord record = s3event.getRecords().get(0);
        String srcBucket = record.getS3().getBucket().getName();
        
        // Object key may have spaces or unicode non-ASCII characters.
        String srcKey = record.getS3().getObject().getKey();

        // TODO: implement your handler
        return null;
    }
    
	public static Connection getconnection() {
		String IP ="<YOUR_RDS_INSTANCE_IP>";
		String dbUser = "<USERNAME>";
		String dbPwd = "<PASSWORD>";
		String dbName = "<DATABASE_NAME>";
		String connectionUrl = "jdbc:mysql://" + IP + ":3306/" + dbName;
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
			System.out.println("Connection Established");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in Connection" + e.getLocalizedMessage());
		}
		return conn;
	}

}
