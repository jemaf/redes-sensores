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
                    System.out.println(String.format("Recebida resposta da solicitacao %s, vinda do no %s, medindo o valor %s de %s", message.get_packetId(), message.get_nodeId(), decodeValue(message.get_value(), typeOfMessage), typeOfMessage.getDescription()));
                } else if (message.get_messageType() == SensorBase.REQUEST_MESSAGE) {
                	System.out.println("Pacote: " + message.get_packetId() + " request recebido");
                    // Nao faz nada quando recebe uma requisicao de leitura
                } else {
                    System.err.println("Recebido um tipo de pacote nao reconhecido... Abortando!");
                    System.exit(0);
                }
            }
        }

        private Integer decodeValue(int value, SensorType sensorType) {
            switch (sensorType) {
                case TEMPERATURE_DATA:
                    return value;
                case LIGHTNESS_DATA:
                    return value;
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
