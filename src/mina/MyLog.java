package mina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MyLog {
	private static final Logger log = LoggerFactory.getLogger(MyLog.class);
	public static void log_cmd(byte[] data){
		//int cmd_type = CommandParser.getCommandType(data);
		//System.out.println("cmd_type:" + cmd_type);
	}
	
	public static void log_data(String sPrefix, byte[] data){
		int iLen = data.length;
		
		log.debug(sPrefix + "length:" + iLen);
		StringBuilder sDebug = new StringBuilder();
		String sHex;
		for(int i = 0; i < iLen; i++){
			sHex = String.format("0x%02x", data[i]&0xff);
			sDebug.append(sHex);
			//sDebug.append(Integer.toHexString(data[i]&0xff));
			sDebug.append(" ");
		}
		log.debug(sDebug.toString());
		log.debug(" ");
	}
	public static void log_output(byte[] data){
		//log_data("<<<<<<output<<<<<", data);
	}
	public static void log_input(byte[] data){
		//log_cmd(data);
		//log_data(">>>>>>input>>>>>>", data);
	}
}
