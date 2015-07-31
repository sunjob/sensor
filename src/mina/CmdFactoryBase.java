package mina;

import org.apache.mina.core.session.IoSession;




public class CmdFactoryBase {
	public static final String SESSION_PARAM_CMD_FACTORY = "cmd_factory";
	public enum MONITOR_CMD_TYPE{
		UNKNOWN_CMD(-1),
		MONITOR_CMD_LOGIN(1),
		MONITOR_CMD_PARAM_DOWN(2),
		MONITOR_CMD_HEART_BEAT(3),
		MONITOR_CMD_SWITCH_INFO(4),

		MONITOR_CMD_MSG_PUSH_TEXT(6),
		MONITOR_CMD_MSG_PUSH_TEXT_REPORT(7),
		MONITOR_CMD_IMG_UPLOAD(8),
		MONITOR_CMD_SYN_PARAM(9),
		
		FIRMWARE_UPDATE_NOTIFY(10),
		FIRMWARE_UPDATE_REQ(11),
		FIRMWARE_UPDATE_TRANS(12),
		
		WEB_FIRMWARE_UPDATE_NOTIFY(64),
		
		MONITOR_CMD_BYE(255);
		
		
		private final int _val;
		private MONITOR_CMD_TYPE(int val)
		{
			_val = val;
		}
		
		public int getValue()
		{
			return this._val;
		}
		
		public static MONITOR_CMD_TYPE valueOf(int val){
			switch(val)
			{
			case 1:
				return MONITOR_CMD_LOGIN;
			case 2:
				return MONITOR_CMD_PARAM_DOWN;
			case 3:
				return MONITOR_CMD_HEART_BEAT;
			case 4:
				return MONITOR_CMD_SWITCH_INFO;
			case 6:
				return MONITOR_CMD_MSG_PUSH_TEXT;
			case 7:
				return MONITOR_CMD_MSG_PUSH_TEXT_REPORT;
			case 8:
				return MONITOR_CMD_IMG_UPLOAD;
			case 9:
				return MONITOR_CMD_SYN_PARAM;
			case 10:
				return FIRMWARE_UPDATE_NOTIFY;
			case 11:
				return FIRMWARE_UPDATE_REQ;
			case 12:
				return FIRMWARE_UPDATE_TRANS;
			case 64:
				return WEB_FIRMWARE_UPDATE_NOTIFY;
			case 255:
				return MONITOR_CMD_BYE;
			default:
				return UNKNOWN_CMD;
				
			}
		}
	}
	
	
	public static MONITOR_CMD_TYPE getCommandType(byte[] data) {
		int command = data[7] & 0xFF;
		
		
		return MONITOR_CMD_TYPE.valueOf(command);
	}
	
	public static CmdFactoryBase SelectCmdFactory(IoSession session, Object message)
	{
		CmdFactoryBase factory = null;
		byte[] data = DataConvertor.toByteArray(message);
		MONITOR_CMD_TYPE eCmdType = getCommandType(data);
		if(MONITOR_CMD_TYPE.UNKNOWN_CMD == eCmdType){
			
				//log.debug("Not expected first cmd type");
				return null;
		}
		switch(eCmdType){
		case MONITOR_CMD_LOGIN:
			//log.debug("login factory");
			//factory = new LoginCmdFactory(data); 
			break;	
		
		case MONITOR_CMD_SWITCH_INFO:
			//log.debug("switch factory");
			//factory = new SwitchCmdFactory(data);
			break;
			
		case MONITOR_CMD_HEART_BEAT:
			//log.debug("heart beat factory");
			//factory = new HeartBeatCmdFactory(data);
			break;
			
		case MONITOR_CMD_MSG_PUSH_TEXT_REPORT:
			//log.debug("msg push ack factory");
			//factory = new MsgPushReportFactory(data);
			break;
			
		case MONITOR_CMD_IMG_UPLOAD:
			//log.debug("img upload factory");
			//factory = new ImgUploadFactory(data);
			break;
			
		case MONITOR_CMD_SYN_PARAM:
			//log.debug("Syn Param cmd factory");
			//factory = new SynParamCmdFactory(data);
			break;
		case WEB_FIRMWARE_UPDATE_NOTIFY:
			//log.debug("rpc FmUpdate Notify factory");
			//factory = new Rpc_FmUpdateNotifyFactory(data);
			break;
		case FIRMWARE_UPDATE_REQ:
			//log.debug("firmware update trans factory");
			//factory = new FirmwareUpdateFactory(data);
			break;
		}

		
		
		return factory;
	}
	
	protected byte[] m_oData = null;
	
	public  CommandBase CreateCommand(IoSession session, Object message)
	{

		m_oData = DataConvertor.toByteArray(message);
		CommandBase cmd = null;

			
			
			MyLog.log_data(">>>>>>>>recv", m_oData);
		return cmd;
	}
	
	
	
}
