import sun.security.util.BitArray;
import java.nio.ByteBuffer;
import java.util.Map;

/*** Mensagens uteis no sistema ***/
class Utils {

    /** Constantes **/
    static final int tempo_espera_ack = 2000;
    static final int numero_de_conexao_inicial = 1;
    static final int tempo_espera_servidor = 10000;
    static final int tamanho_maximo_arquivo = 100000000;
    static final int tempo_verifica_conexoes = 50;
    static final int tamanho_pacote = 524;
    static final int tamanho_util_pacote = 512;
    static final int porta_MIN = 1023;
    static final int porta_MAX = 65535;
    static final float chance_perda_cliente = 0.2f;
    static final float chance_perda_servidor = 0f;
    static final int CWND_inicial = 512;
    static final int SS_THRESH_inicial = 10000;
    static final int numero_max_seq = 102400;
    static final int numero_sequencia_inicial_cliente = 12345;
    static final int numero_sequencia_inicial_servidor = 4321;
    static final int tempo_espera_finack = 2000;

    /*** Mensagens do Cliente ***/
    static final String prefixo_cliente = "[CLIENTE]-->";
    static final String inicio_cliente = "Iniciando cliente TCPow.";
    static final String requisicao_paramentros = "Defina os parametros... 'Ip Porta Diretorio-do-arquivo'";
    static final String cliente_muitos_parametros = "O cliente requer somente"
            +" 3 parametros: 'ip porta arquivo'.";
    static final String formato_n_errado = "O parametro 'PORTA' encontrou um erro no dado passado.";
    static final String cliente_poucos_parametros = "O cliente requer obrigatoriamente"
            +" 3 parametros: 'ip porta arquivo'.";
    static final String hostname_invalido = "O hostname passado é invalido.";
    static final String arquivo_inexistente = "O Pacote passado não existe.";
    static final String erro_na_leitura = "Houve um erro na leitura do arquivo";
    static String dados_definidos(String ip, int porta, String caminho)
    {
        return "Dados do cliente definidos: IP = '"+ip +"' Porta = '"
                +porta+"' Pacote = '"+caminho.substring(caminho.lastIndexOf("/") + 1, caminho.length()) +"'.";
    }
    static final String arquivo_muito_grande = "O arquivo escolhido é muito grande, o tamanho maximo é 100MB.";
    static final String tempo_esgotado = "Sem resposta do servidor por 10 segundos.";
    static final String pacote_perdido = "Pacote Perdido no envio.";
    static final String finack_perdido = "FINACK perdido.";
    static String janela(int tamanho, int cwnd, int thresh, Map<Integer, Estado> janela)
    {
        String retorno = "\n\n" +prefixo_cliente +"|- Tamanho da janela = "+tamanho +"\n";
        retorno = retorno.concat(prefixo_cliente +"|- CWND ="+cwnd +" SS_THRESH ="+thresh +"\n");
        retorno = retorno.concat(prefixo_cliente +"|- INICIO JANELA    \n");
        for (Map.Entry i : janela.entrySet()) retorno = retorno.concat(prefixo_cliente +"   |- ("+i.getKey()+") - " +i.getValue().toString()  +"\n");
        retorno = retorno.concat(prefixo_cliente +"|- FIM JANELA \n\n");
        return retorno;
    }
    static String seq_esperado(int esperado, int recebido)
    {
        return "Numero de sequencia esperado -->  " +esperado +" / " + recebido +"  <-- recebido.";
    }

    /*** Mensagens do servidor ***/
    static final String prefixo_servidor = "[SERVIDOR]-->";
    static final String inicio_servidor = "Iniciando servidor TCPow.";
    static final String requisicao_parametros_servidor = "Defina os parametros... 'Porta Diretorio-de-saida'";
    static final String parametro_faltando = "O servidor requer obrigatoriamente " +
            "2 parametros: 'porta diretorio'.";
    static final String muitos_parametros = "O servidor requer somente " +
            "2 parametros: 'porta diretorio'.";
    static final String nome_diretorio_invalido = "O nome do diretorio é invalido, não deve conter"
            +" / \\ | : \" < > ? *";
    static String dados_definidos_servidor(int porta, String diretorio)
    {
        return "Dados do servidor definidos: Porta = '"+porta +"' Diretorio de saida = '/"+diretorio +"/'.";
    }
    static String servidor_iniciado(int porta)
    {
        return "Servidor iniciado na porta " +porta;
    }
    static String conexao_encerrada(int id)
    {
        return "Conexão "+id +"foi encerrada.";
    }
    static String arquivo_salvo(String diretorio)
    {
        return "Arquivo salvo em: " + diretorio + " com sucesso.";
    }
    static String sem_conexao_cliente(int con)
    {
        return "10 segundos sem receber pacotes da conexão " +con +", encerrando conexão.";
    }
    static String prefixo_duplicado = "[DUPLICADO]-->";
    static String rejeitado = "--REJEITADO--";
    static String erro_escrita = "Houve um erro na escrita do arquivo.";
    static String extensao_arquivo = ".file";


    /*** Mensagens gerais ***/
    static final String porta_reservada = "A porta escolhida é reservada ao sistema.";
    static final String porta_invalida = "A porta escolhida é invalida.";
    static final String fim_de_conexao = "Encerrando a conexão";
    static String handshake(int hs, Pacote handshake)
    {
        String retorno = "";
        retorno = retorno.concat("[Handshake "+hs +"° via] SEQ = '" +handshake.getNumero_de_sequencia()
                +"', ACK = '"+handshake.getNumero_ack()
                +"', ID = '"+handshake.getId() +"'");
        if(handshake.isSYN()) retorno = retorno.concat(", SYN");
        if(handshake.isACK()) retorno = retorno.concat(", ACK");
        if(handshake.isFIN()) retorno = retorno.concat(", FIN");
        retorno = retorno.concat(".");
        return retorno;
    }
    static String conexao_criada(int id)
    {
        return "Conexão criada ID = '"+id +"'.";
    }
    static String pacote_enviado(Pacote pacote){
        String retorno = "";
        retorno = retorno.concat("[Pacote enviado]" +" SEQ = '" +pacote.getNumero_de_sequencia()
                +"', ACK = '"+pacote.getNumero_ack()
                +"', ID = '"+pacote.getId() +"'");
        if(pacote.isSYN()) retorno = retorno.concat(", SYN");
        if(pacote.isACK()) retorno = retorno.concat(", ACK");
        if(pacote.isFIN()) retorno = retorno.concat(", FIN");
        retorno = retorno.concat(".");
        return retorno;
    }
    static String pacote_recebido(Pacote pacote)
    {
        String retorno = "";
        retorno = retorno.concat("[Pacote recebido]" +" SEQ = '" +pacote.getNumero_de_sequencia()
                +"', ACK = '"+pacote.getNumero_ack()
                +"', ID = '"+pacote.getId() +"'");
        if(pacote.isSYN()) retorno = retorno.concat(", SYN");
        if(pacote.isACK()) retorno = retorno.concat(", ACK");
        if(pacote.isFIN()) retorno = retorno.concat(", FIN");
        retorno = retorno.concat(".\n");
        return retorno;
    }
    static final String reenvio_pacote = "[REENVIO]-->";

    /*** Funçoes uteis ***/
    static Pacote cria_pacote(byte[] data)
    {
        Pacote retorno = new Pacote();

        byte[] seq = new byte[4];
        System.arraycopy(data, 0, seq, 0, 4);
        ByteBuffer bb = ByteBuffer.wrap(seq);
        retorno.setNumero_de_sequencia(bb.getInt());
        bb.clear();

        byte[] ack_n = new byte[4];
        System.arraycopy(data, 4 , ack_n, 0 , 4);
        bb = ByteBuffer.wrap(ack_n);
        retorno.setNumero_ack(bb.getInt());
        bb.clear();

        byte[] id = new byte[2];
        System.arraycopy(data, 8 , id , 0 , 2);
        bb = ByteBuffer.wrap(id);
        retorno.setId(bb.getShort());
        bb.clear();

        byte[] rest = new byte[2];
        System.arraycopy(data, 10, rest , 0 , 2);

        retorno.setACK(isSet(rest[1], 2));
        retorno.setSYN(isSet(rest[1], 1));
        retorno.setFIN(isSet(rest[1], 0));

        byte[] payload = new byte[512];
        System.arraycopy(data, 12 , payload, 0 , 512);
        retorno.setConteudo(payload);
        bb.clear();

        return retorno;

    }
    static boolean isSet(byte value, int bit)
    {
        return (value&(1<<bit))!=0;
    }
    static byte[] cria_pacote(Pacote pac)
    {
        byte[] seq_n = ByteBuffer.allocate(4).putInt(pac.getNumero_de_sequencia()).array();
        byte[] ack_n = ByteBuffer.allocate(4).putInt(pac.getNumero_ack()).array();
        byte[] id = ByteBuffer.allocate(2).putShort(pac.getId()).array();
        boolean ack = pac.isACK();
        boolean syn = pac.isSYN();
        boolean fin = pac.isFIN();

        BitArray rec = new BitArray(16);
        rec.set(0,false);
        rec.set(1,false);
        rec.set(2,false);
        rec.set(3,false);
        rec.set(4,false);
        rec.set(5,false);
        rec.set(6,false);
        rec.set(7,false);
        rec.set(8,false);
        rec.set(9,false);
        rec.set(10,false);
        rec.set(11,false);
        rec.set(12,false);
        rec.set(13, ack);
        rec.set(14, syn);
        rec.set(15, fin);

        byte[] rec_ = rec.toByteArray();

        byte[] cabecalho = new byte[12];

        System.arraycopy(seq_n, 0, cabecalho, 0, seq_n.length);
        System.arraycopy(ack_n, 0, cabecalho, 4, ack_n.length);
        System.arraycopy(id, 0, cabecalho, 8, id.length);
        System.arraycopy(rec_, 0, cabecalho, 10, rec_.length);

        if(pac.getConteudo() != null)
        {
            byte[] data = new byte[524];
            System.arraycopy(cabecalho, 0 , data , 0, cabecalho.length);
            System.arraycopy(pac.getConteudo(), 0 , data , 12 , pac.getConteudo().length);
            return data;
        }

        return cabecalho;
    }

}
