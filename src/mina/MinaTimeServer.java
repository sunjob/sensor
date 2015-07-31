package mina;


import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.charset.Charset;  
  
  
  
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;  
import org.apache.mina.core.session.IdleStatus;  
import org.apache.mina.filter.codec.ProtocolCodecFilter;  
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;  
import org.apache.mina.filter.logging.LoggingFilter;  
import org.apache.mina.transport.socket.nio.NioSocketAcceptor; 

public class MinaTimeServer {
	private static final int PORT= 8234;  
    
    public static void main(String[] args) throws IOException {  
  
        IoAcceptor acceptor = new NioSocketAcceptor();  
        acceptor.setHandler(  new TimeServerHandler() );  
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );  
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new MyBytesCodecFactory()));
        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("iso8859-1")))); 
        
        
        
          
        acceptor.getSessionConfig().setReadBufferSize(1024);
                acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );  
          
        acceptor.bind(new InetSocketAddress(PORT)); 
        
    }
}
