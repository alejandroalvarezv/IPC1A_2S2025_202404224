package practica2;

public class Personaje {
    private static int contadorID = 1;
    private int id;
    private String nombre;
    private String arma;
    private int hp;
    private int ataque;
    private int velocidad;
    private int agilidad;
    private int defensa;

    public Personaje(String nombre, String arma, int hp, int ataque, int velocidad, int agilidad, int defensa) {
        this.id = contadorID++;
        this.nombre = nombre.toLowerCase();
        this.arma = arma;
        this.hp = hp;
        this.ataque = ataque;
        this.velocidad = velocidad;
        this.agilidad = agilidad;
        this.defensa = defensa;
    }

    public String toString() {
        return String.format("ID: %d | Nombre: %s | Arma: %s | HP: %d", id, nombre, arma, hp);
    }

    public String getNombre() {
        return nombre;
    }
}

