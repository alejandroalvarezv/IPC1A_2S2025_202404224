package practica2;
public class Batalla {


    private static boolean batallaFinalizada = false;

    public static void simularBatalla(Personaje p1, Personaje p2, VentanaBatalla ventana) {
        batallaFinalizada = false;

        Thread t1 = new Thread(() -> ejecutarTurnos(p1, p2, ventana));
        Thread t2 = new Thread(() -> ejecutarTurnos(p2, p1, ventana));

        t1.start();
        t2.start();
    }

    private static synchronized void ejecutarTurnos(Personaje atacante, Personaje defensor, VentanaBatalla ventana) {
        while (!batallaFinalizada && defensor.getHp() > 0 && atacante.getHp() > 0) {
            try {
                Thread.sleep(1000 / atacante.getVelocidad());

                boolean esquiva = Math.random() < (defensor.getAgilidad() / 20.0);
                if (esquiva) {
                    ventana.appendToBitacora(atacante.getNombre() + " ataca a " + defensor.getNombre() + " - Falló (esquiva)");
                    continue;
                }

                int daño = atacante.getAtaque() - defensor.getDefensa();
                if (daño < 1) daño = 1;

                int nuevoHP = defensor.getHp() - daño;
                defensor.setHp(nuevoHP);

                ventana.appendToBitacora(atacante.getNombre() + " ataca a " + defensor.getNombre()
                        + " - Daño: " + daño + " | HP restante: " + Math.max(0, nuevoHP));

                if (nuevoHP <= 0 && !batallaFinalizada) {
                    batallaFinalizada = true;
                    ventana.appendToBitacora("💀 " + defensor.getNombre() + " ha sido derrotado.");
                    ventana.appendToBitacora("🏆 Ganador: " + atacante.getNombre());

                    // Guardar en historial (ya usas un Vector en Ventana)
                    Ventana.agregarNombreAlHistorial(defensor.getNombre());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
