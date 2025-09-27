package practica2;
public class HistorialBatalla {
    private int numero;
    private String fecha;
    private String participante1;
    private String participante2;
    private String ganador;
    
    public HistorialBatalla(int numero, String fecha, String participante1, String participante2, String ganador){
        this.numero = numero;
        this.fecha = fecha;
        this.participante1 = participante1;
        this.participante2 = participante2;
        this.ganador = ganador;
    }
    
    // Getters para acceder a los atributos BUSCAR PERSONAJES
    
    public int getNumero() {
        return numero;
    }

    public String getFecha() {
        return fecha;
    }
    
    public String getParticipante1() {
        return participante1;
    }

    public String getParticipante2() {
        return participante2;
    }

    public String getGanador() {
        return ganador;
    }
    
    @Override
    public String toString(){
        return String.format("Batalla #%d\nFecha: %s\nParticipantes: %s vs %s\nGanador: %s\n",
                numero, fecha, participante1, participante2, ganador);
    }
}
