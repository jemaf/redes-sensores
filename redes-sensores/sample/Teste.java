import net.tinyos.message.*;
public class Teste {
    public static final short TOS_BCAST_ADDR = (short) 0xffff;

	public static void main(String args[]){
		//prepara o pacote para ser enviado --> coloca as informações nos campos
		GeoSensoresMsg msg = new GeoSensoresMsg();
		msg.set_nodeid(0);
		msg.set_msgid(2);
		msg.set_timesleep(2000);
		msg.set_timeawake(2000);

		//cria o objeto que enviará a mensagem para o SerialForwader
		MoteIF mif = new MoteIF();

		//define qual objeto escutará as mensagens recebidas pelo SerialForwarder
		mif.registerListener(new GeoSensoresMsg(),  new Handler());

		try{
			//envia a mensagem para o nó 1
			mif.send(TOS_BCAST_ADDR,msg);
		}
		catch(Exception ex){
			System.err.println("Erro!");
		}
		System.out.println("\nDado Enviado!");	

		while(true){} //Gambiarra para travar o programa, logicamente vcs faram uma thread
	}

	public static class Handler implements MessageListener{
		public void messageReceived(int to, Message m){
			System.out.println("Recebi do nó sensor!");
		}
	}

}
