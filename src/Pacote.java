import java.io.Serializable;

@SuppressWarnings("SameParameterValue")
class Pacote implements Serializable
{
    private static final long serialVersionUID = 1L;

    /*** Atributos importantes do arquivo ***/
    /*** Cabeçalho ***/
    /***
     * numero_de_sequencia      - Representa qual é o primeiro byte do segmento de dados.
     * numero_ack               - Representa o proximo numero de sequencia que o receptor está esperando.
     * id                       - Representa o id da conexão.
     * ACK                      - Bit que informa se o numero_ack está habilitado.
     * SYN                      - Bit que informa quando o pacote é de handshake.
     * FIN                      - Bit que informa quando o pacote é de fim de conexão.
     * conteudo                 - Array de bytes com o conteudo do pacote.
     * ***/
    private int numero_de_sequencia;
    private int numero_ack;
    private short id;
    private boolean ACK;
    private boolean SYN;
    private boolean FIN;
    private byte[] conteudo;

    /*** Construtor ***/
    Pacote()
    {
        this.numero_de_sequencia = 0;
        this.numero_ack = 0;
        this.id = 0;
        this.ACK = false;
        this.SYN = false;
        this.FIN = false;
        this.conteudo = null;
    }
    /*** Getter's e Setter's ***/
    int getNumero_de_sequencia()
    {
        return numero_de_sequencia;
    }
    void setNumero_de_sequencia(int numero_de_sequencia)
    {
        this.numero_de_sequencia = numero_de_sequencia;
    }
    int getNumero_ack()
    {
        return numero_ack;
    }
    void setNumero_ack(int numero_ack)
    {
        this.numero_ack = numero_ack;
    }
    short getId()
    {
        return id;
    }
    void setId(short id)
    {
        this.id = id;
    }
    boolean isACK()
    {
        return ACK;
    }
    void setACK(boolean ACK)
    {
        this.ACK = ACK;
    }
    boolean isSYN()
    {
        return SYN;
    }
    void setSYN(boolean SYN)
    {
        this.SYN = SYN;
    }
    boolean isFIN()
    {
        return FIN;
    }
    void setFIN(boolean FIN)
    {
        this.FIN = FIN;
    }
    byte[] getConteudo()
    {
        return conteudo;
    }
    void setConteudo(byte[] conteudo)
    {
        this.conteudo = conteudo;
    }
    public String toString()
    {
        return "ID: "+this.id + "| SEQ: " +this.numero_de_sequencia + " | ACK n: "+this.numero_ack
                +" | A : " +this.ACK + " | S: " +this.SYN + " | F: "+this.FIN;
    }
}