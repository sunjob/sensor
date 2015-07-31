package mina;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.net.SocketAddress;  



public class TCPreciver {
	   private static final int BUFSIZE=1024;  
	   final static int BUFFER_SIZE = 52;
	   final static String cmd_diaoyue1 = "FF FF FF FF 01 F0 90 00 00 08 01 89";
	    /** 
	     * @param args 
	     */  
	    public static void main(String[] args) {  
	        //Test for correct # of args  
	       // if(args.length!=1){  
	        //    throw new IllegalArgumentException("Parameter(s):<Port>");  
	       // }  
	        int servPort = 60000;  
	        int size;  
	        ServerSocket servSocket =null;  
	        int recvMsgSize=0;  
	        byte[] receivBuf=new byte[BUFFER_SIZE]; 
	        String data;
	        
	        //test();
	        test1();
	        try {  
	            servSocket=new ServerSocket(servPort);  
	            while(true){  
	                Socket clientSocket=servSocket.accept();  
	                SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();  
	                System.out.println("Handling client at "+ clientAddress);  
	                  
	                InputStream in =clientSocket.getInputStream();  
	                OutputStream out= clientSocket.getOutputStream();  
	               
	                
	                
	                //receive until client close connection,indicate by -l return  
	                while((recvMsgSize=in.read(receivBuf))!=-1){  
	                    String receivedData=new String(receivBuf.toString());  
	                   // data = bytesToHexString(receivBuf);
	                   // AnalysisData(data);
	                    out.write(receivBuf, 0, recvMsgSize);  
	                }  
	                clientSocket.close();  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();   
	        }  
	          
	  
	    }  
	    
	
	    public static void test1(){
	    	String cmd = "testsend";
	    	String[] cmds = new String[cmd.length()];
	    	char[] temp = cmd.toCharArray();
	    	
	    	System.out.println(Integer.toHexString((int)(temp[0])));
	    	
	    	for(int i = 0;i<cmd.length();i++)
	    		cmds[i]= String.valueOf(Integer.toHexString((int)(temp[i])));
//	    	System.out.println(StringUtils.join(cmds," "));
	    	
	    }
	    
	    public static void test(){
	    	 System.out.println('A'/10);
		        
		        //Create a server socket to accept client connection request  
		       
		        
	    	 String[] cmds = cmd_diaoyue1.split(" ");
		        byte[] aaa = new byte[cmds.length];
		        int j = 0;
		        for (String b : cmds) {
		            if (b.equals("FF")) {
		                aaa[j++] = -1;
		            } else {
		                aaa[j++] = Integer.valueOf(b, 16).byteValue();
		            }
		        }
		        
	    	 
		        String test1 = "90";
		        String test2 = "ffffffff01f0000000309034"; 
		        
		        String temp = test1.substring(0, 1);
		        String temp2= test1.substring(1, 2);
		        
		        AnalysisData(test2);
		        
		        int i = Integer.valueOf(test1).intValue();
		        //int j = Integer.valueOf(test2).intValue();
		        
		        int l = str2hex(temp);
		        int m = str2hex(temp2);
		        int sum = (l<<4)+m;
		        
		        
		        
		        System.out.println("the test result is "+((i/10<<4)+i%10));
		        
		        
		        System.out.println("the test result is "+"----"+(sum&0x80));
		        
	    }
	    
	    public static void AnalysisData(String data){
 	    	boolean started = false;
	    	String XHJ_address;
	 	    String ZXJ_address;
	 	    
	 	  
	 	    
	 	    int east[] 	= new int[5];  //1绿，2黄，3红，0灭  方向顺序 左直右人人
	 	    int west[]	= new int[5];		
	 	    int north[]	= new int[5];
	 	    int sourth[]= new int[5];

	 	  // ff ff ff ff 01 f0 00 00 00 30 90 34
	    	char[] mdata = data.toCharArray();
	    	for(int i= 0;i<8;i++){
	    		if(mdata[i] == 'f'){
	    			started = true;
	    		}
	    	}
	    	if(started == true){
	    		XHJ_address = data.substring(8, 10);
	    		ZXJ_address = data.substring(10, 12);
	    		//String temp = data.substring(19, 20);
	    		//String temp1 = data.substring(20, 21);
	    		//int t = Integer.valueOf(temp).intValue();
	    		//int t1 = Integer.valueOf(temp1).intValue();
	    		
	    		String temp	 = data.substring(20, 22);
	    		String temp1 = data.substring(22, 24);
/*	    		
	    		int t = Integer.valueOf(temp).intValue();
	    		if(t == 0){
	    			east[0] = 0;
	    			east[1] = 0;
	    		}
	    		else if(t > 10){
	    			east[0] = t/40+1;
	    			if(t%20 == 0){
	    				east[1] = 0;
	    			}
	    			int t1 = t%10/4;
	    			if(t1 == 0)
	    				east[1] = 3;
	    			east[1] = t1;
	    		}else{
	    			east[0] = 0;
	    			int t1 = t%10/4;
	    			if(t1 == 0)
	    				east[1] = 3;
	    			east[1] = t1;
	    		}
*/
	    		if((str2hex(temp)&0x80)>0){
	    			east[0] = 3;              
	    		}else if((str2hex(temp)&0x40)>0){
	    			east[0] = 2;
	    		}else if((str2hex(temp)&0x20)>0){
	    			east[0] = 1;
	    		}else{
	    			east[0] = 0;
	    		}
	    		
	    		if((str2hex(temp)&0x10)>0){
	    			east[1] = 3;
	    		}else if((str2hex(temp)&0x08)>0){
	    			east[1] = 2;
	    		}else if((str2hex(temp)&0x04)>0){
	    			east[1] = 1;
	    		}else{
	    			east[1] = 0;
	    		}
	    		
	    		if((str2hex(temp1)&0x80)>0){
	    			east[2] = 3;
	    		}else if((str2hex(temp1)&0x40)>0){
	    			east[2] = 2;
	    		}else if((str2hex(temp1)&0x20)>0){
	    			east[2] = 1;
	    		}else{
	    			east[2] = 0;
	    		}
	    		
	    		if((str2hex(temp1)&0x10)>0){
	    			east[3] = 3;
	    		}else if((str2hex(temp1)&0x08)>0){
	    			east[3] = 1;
	    		}else{
	    			east[3] = 0;
	    		}
	    		
	    		if((str2hex(temp1)&0x04)>0){
	    			east[4] = 3;
	    		}else if((str2hex(temp1)&0x02)>0){
	    			east[4] = 1;
	    		}else{
	    			east[4] = 0;
	    		}
	    	}
	    	
	    	for(int i=0;i<5;i++){
	    		System.out.println("east  "+i+"is"+east[i]);
	    	}
	    }
	  
	    public static String bytesToHexString(byte[] src){  
	        StringBuilder stringBuilder = new StringBuilder("");  
	        if (src == null || src.length <= 0) {  
	            return null;  
	        }  
	        for (int i = 0; i < src.length; i++) {  
	            int v = src[i] & 0xFF;  
	            String hv = Integer.toHexString(v);  
	            if (hv.length() < 2) {  
	                stringBuilder.append(0);  
	            }  
	            stringBuilder.append(hv);  
	        }  
	        System.out.println("---"+stringBuilder.toString()+"---");
	        
	        return stringBuilder.toString();  
	    }  
	    
	    public static int str2hex(String str){
	    	String temp = str.substring(0, 1);
		    String temp2= str.substring(1, 2);
	    	int l = str2hexfun(temp);
	        int m = str2hexfun(temp2);
	        int sum = (l<<4)+m;
	        
			return sum;
	    	
	    }
	    
	    public static int str2hexfun(String str){
	    	
	    	if(str.equals("a")){
	    		return 10;
	    	}else if(str.equals("b"))
	    		return 11;
	    	else if(str.equals("c"))
	    		return 12;
	    	else if(str.equals("d"))
	    		return 13;
	    	else if(str.equals("e"))
	    		return 14;
	    	else if(str.equals("f"))
	    		return 15;
	    	else{
	    		int i = Integer.valueOf(str).intValue();
	    	return i;
	    	}
	    }
	    public static String str2HexStr(String str)    
	    {      
	      
	        char[] chars = "0123456789ABCDEF".toCharArray();      
	        StringBuilder sb = new StringBuilder("");    
	        byte[] bs = str.getBytes();      
	        int bit;      
	            
	        for (int i = 0; i < bs.length; i++)    
	        {      
	            bit = (bs[i] & 0x0f0) >> 4;      
	            sb.append(chars[bit]);      
	            bit = bs[i] & 0x0f;      
	            sb.append(chars[bit]);    
	            sb.append(' ');    
	        }      
	        return sb.toString().trim();      
	    }    
}
