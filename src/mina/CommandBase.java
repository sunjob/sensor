package mina;





public class CommandBase {
	public static final int ACK_OK = 0;
	public static final int ACK_FAILED = 1;
	public static final int ACK_NOT_REGISTER = 1;
	public static final int ACK_SERVER_ERROR = 2;
	
	public static final int CMD_DIR_REQ = 0;
	public static final int CMD_DIR_ACK = 1;
	
	public static final int CMD_DIR_UP = 0;
	public static final int CMD_DIR_DOWN = 1;
	
	public static final int CMD_DATA_START_POS = 16;
	
	
	public static final byte[] CMD_HEADER_STR = {'E', 'G', 'U', 'A', 'R', 'D'};
	public static final byte EGUARD_PROTOCOL_VERSION = 1;
	
	protected ICmdParser m_oParser;

	//public static final String SESSION_VAR_IMEI = "imei";

	protected int m_iDir_Up_Down;
	public int getUp_Down_Dir()
	{
		return m_iDir_Up_Down;
	}
	public CommandBase(ICmdParser parser, byte[] data){
		m_oParser = parser;
		
		//InitDeviceConfig(session);
		//GetDeviceConfig(session, data);
		m_iDir_Up_Down = CMD_DIR_UP;
	}
	
	public CommandBase(ICmdParser parser)
	{
		m_iDir_Up_Down = CMD_DIR_DOWN;
		m_oParser = parser;
	}
}
