package mina;

import org.apache.mina.core.session.IoSession;


public interface ICmdParser {
	public int GetByeAckFlag(CommandBase cmd);
	public boolean OnAfter_Ack(IoSession session,CommandBase cmd);
	
	
	public void setSerialNum(byte[] serial);
	public byte[] getSerialNum();
	
	
	//public CacheContext getCacheContext();
	
	public void UpdatePushTask();
	
}


