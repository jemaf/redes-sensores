import net.tinyos.message.*;
import java.util.*;

public class SensorBase {
    public static final short TOS_BCAST_ADDR = (short) 0xffff;
    public static final int REQUEST_MESSAGE = 0;
    public static final int DATA_MESSAGE = 1;

    public static Integer nodeId;
    public static Integer numberOfInteractions = 1;
    public static Integer timeCycle = 5;
    private static List<SensorType> sensorList = new ArrayList<SensorType>();

    //enum com dados da conversao de temperatura da placa sensora mts420
    public enum Temperature {
	
	//sensor voltage
    	FIVE (-40.1, -40.2),
    	FOUR (-39.8, -39.6),
    	THREEdFIVE (-39.7, -39.5),
    	THREE (-39.6, -39.3),
    	TWOdFIVE (-39.4, -38.9),
	
	//sensor bits
	FOUTEENBITS (0.01, 0.018),
    	TWELVEBITS (0.04, 0.072);
	
	private final double C; //celsius
	private final double F; //fahrenheit
	
	private Temperature(double C, double F) {
	    this.C = C;
	    this.F = F;
	}
	
	public double getC() { return C; }
	public double getF() {return F; }
    }
    
    //enum com dados da conversao de humidade da placa snesora mts420
    public enum RelativeHumidity {
	
	//sensor bits
	EIGHTBITS (-2.0468, 0.0367, -1.5955e-6),
    	TWELVEBITS (-2.0468, 0.5872, -4.0845e-4);
	
	private final double C1, C2, C3;
	
	private RelativeHumidity(double C1, double C2, double C3) {
	    this.C1 = C1;
	    this.C2 = C2;
	    this.C3 = C3;
	}
	
	public double getC1() { return C1; }
	public double getC2() { return C2; }
	public double getC3() { return C3; }
    }
    
    
    
    public static void main(String args[]) throws Exception{
        if (args.length < 3) {
            printHelpMenu();
            System.exit(0);
        }

        decodeArguments(args);

        //cria o objeto que enviará a mensagem para o SerialForwader
        MoteIF mif = new MoteIF();

        //define qual objeto escutará as mensagens recebidas pelo SerialForwarder
        mif.registerListener(new SensorPackage(),  new SensorHandler());

        int timeToSleep = timeCycle;
	for (int interaction = 1; interaction <= numberOfInteractions; interaction++) {
            System.out.println(String.format("Iniciando solicitacao numero %s", interaction));
            for (SensorType sensorType : sensorList) {
                System.out.println(String.format("Requisitando leituras de %s", sensorType.getDescription()));
                SensorPackage message = new SensorPackage();
                message.set_packetId(interaction);
                message.set_messageType(SensorBase.REQUEST_MESSAGE);
                message.set_nodeId(nodeId);
                message.set_destinationNodeId(nodeId);
                message.set_typeOfData(sensorType.getId());
                message.set_value(-1);

                try {
                    //envia a mensagem
                    mif.send(TOS_BCAST_ADDR, message);
                }
                catch(Exception exception) {
                	System.err.println(String.format("Erro ao enviar mensagem %s solicitando medicao de %s", interaction, sensorType.getDescription()));
                	throw exception;
                }
            }
            
            try {
            	Thread.sleep(timeToSleep * 1000);
            	timeToSleep += timeCycle;
            }catch(Exception ex) {
            	System.err.println("Erro no sleep da thread.");
            	System.exit(0);
            }
        }
	
		mif.deregisterListener(new SensorPackage(),  new SensorHandler());
		System.exit(0);
    }

    private static Integer decodeStringIntoInt(String id) {
        if (id != null && id.length() > 0) {
            try {
                return Integer.parseInt(id);
            } catch (Exception exception) {
                System.err.println(String.format("Identificador invalido : %s", id));
            }
        }
        System.err.println(String.format("Identificador vazio"));
        System.exit(0);
        return null;
    }

    private static void decodeArguments(String[] args) {
        for (String argument : args) {
            if (argument.charAt(0) == '-') {
                switch (argument.charAt(1)) {
                    case 'i':
                        nodeId = decodeStringIntoInt(argument.substring(2));
                        break;
                    case 'n':
                        numberOfInteractions = decodeStringIntoInt(argument.substring(2));
                    case 'v':
                        timeCycle = decodeStringIntoInt(argument.substring(2));
                        break;
                    case 'l':
                        sensorList.add(SensorType.LIGHTNESS_DATA);
                        break;
                    case 't':
                        sensorList.add(SensorType.TEMPERATURE_DATA);
                        break;
                    case 'u':
                        sensorList.add(SensorType.MOISTURE_DATA);
                        break;
                    default:
                        System.out.println(String.format("WARN| Opcao nao reconhecida: %s", argument));
                        break;

		}
            }
        }

        validateInit();
    }

    private static void validateInit() {
        if (nodeId == null) {
            System.err.println("Identificador do no base nao informado.");
            System.exit(0);
        }
    }
    private static void printHelpMenu() {
        System.out.println("Para executar o no base da rede, forneca os seguintes argumentos:\n\n"+
                           "java SensorBase -iX -vY -t -u -l\n\n" +
                           "-iX -> (Obrigatorio) Inicializa o no com identificar inteiro X\n" +
                           "-nY -> Inicializa a base com o numero de Y interacoes\n" +
                           "-vZ -> Inicializa o no com tempo de rotacao de Z segundos\n" +
                           "-l -> Ativa medicao de temperatura\n" +
                           "-t -> Ativa medicao de temperatura\n" +
                           "-u -> Ativa medicao de umidade"
	);
    }


    public enum SensorType {
        TEMPERATURE_DATA(10, "Temperatura"), LIGHTNESS_DATA(11, "Luminosidade"), MOISTURE_DATA(12, "Umidade"), LOCALITY_DATA(13, "Localizacao"), PRESSURE_DATA(14, "Pressao");

        private int id;
        private String description;

        SensorType(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return this.id;
        }

        public String getDescription() {
            return this.description;
        }

        public static SensorType getById(int id) {
            SensorType[] types = SensorType.values();
            for (SensorType type : types) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

    }

    public static class SensorHandler implements MessageListener {
        public void messageReceived(int to, Message packet) {
            if (!(packet instanceof SensorPackage)) {
                System.err.println("Recebida mensagem em formato nao suportado... Abortando!");
                System.exit(0);
            }

            SensorPackage message = (SensorPackage) packet;
            
            System.out.println("Pacote: " + message.get_packetId() + " recebido");
            
            if (message != null && message.get_destinationNodeId() == nodeId) {
                SensorType typeOfMessage = SensorType.getById(message.get_typeOfData());
                if (typeOfMessage == null) {
                    System.err.println("Recebido pacote de tipo invalido... Abortando!");
                    System.exit(0);
                }

                if (message.get_messageType() == SensorBase.DATA_MESSAGE) {
                    System.out.println(String.format("Recebida resposta da solicitacao %s, vinda do no %s, medindo o valor %s de %s", message.get_packetId(), message.get_nodeId(), decodeValue((double)message.get_value(), typeOfMessage), typeOfMessage.getDescription()));
                } else if (message.get_messageType() == SensorBase.REQUEST_MESSAGE) {
                	System.out.println("Pacote: " + message.get_packetId() + " request recebido");
                    // Nao faz nada quando recebe uma requisicao de leitura
                } else {
                    System.err.println("Recebido um tipo de pacote nao reconhecido... Abortando!");
                    System.exit(0);
                }
            }
        }

        private Double decodeValue(Double value, SensorType sensorType) {
	    
	    //supondo parametros 12bits, 3.5v
	    
            switch (sensorType) {
                case TEMPERATURE_DATA:	//T = volt + bits * SO
                    return Temperature.THREEdFIVE.getC() + Temperature.TWELVEBITS.getC() * value;
                case LIGHTNESS_DATA: // RH = c1 + c2 * SO + c3 * SO^2
                    return RelativeHumidity.TWELVEBITS.getC1() + RelativeHumidity.TWELVEBITS.getC2() * value + RelativeHumidity.TWELVEBITS.getC1() * Math.pow(value,2);
                case MOISTURE_DATA:
                    return value;
                case LOCALITY_DATA:
                    return value;
                case PRESSURE_DATA:
                    return value;
                default:
                    return null;
            }
        }
    }
}
