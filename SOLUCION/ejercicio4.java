
import java.time.LocalDate;
import java.util.Scanner;

public class ejercicio4 {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String nombre, carreraSeleccionada;
        String carreras[] = {"Quimica", "Fisiorehabilitacion", "Medicina"};
        int[] puntajesMinimos = {80, 90, 85};
        int[] cupos = {80, Integer.MAX_VALUE, 80};
        int opcionCarrera = 0, examenAdmision, puntajeMinimo, contQuimi = 0, contFisi = 0, contMedi = 0, puntajeTotal = 0;
        String[] admitidosQuimica = new String[cupos[0]];
        int[] puntajesQuimica = new int[cupos[0]];
        String[] admitidosMedicina = new String[cupos[2]];
        int[] puntajesMedicina = new int[cupos[2]];
        int[] puntajesTotalesMedicina = new int[cupos[2]];
        String[] admitidosFisi = new String[500];
        LocalDate fechaLimite = LocalDate.of(2025, 1, 28);
        System.out.println("Bienvenido al sistema de admision de la utpl: ");
        while (true) {
            System.out.print("Ingrese su nombre (o 'salir' para finalizar): ");
            nombre = teclado.next();
            if (nombre.equalsIgnoreCase("Salir")) {
                break;
            }
            if (!verificarFechaLimite(fechaLimite)) {
                System.out.println("El periodo de inscripcion ha finalizado..");
                break;
            }
            examenAdmision = obtenerPuntaje(teclado);
            opcionCarrera = opcionCarrera(teclado, carreras, opcionCarrera);
            if (opcionCarrera == -1) {
                continue;
            }
            carreraSeleccionada = carreras[opcionCarrera];
            puntajeMinimo = puntajesMinimos[opcionCarrera];
            if (examenAdmision < puntajeMinimo) {
                System.out.println("No cumple con el puntaje minimo para la carrera " + carreraSeleccionada);
                continue;
            }
            switch (carreraSeleccionada) {
                case "Quimica":
                    contQuimi = registrarAdmitido(admitidosQuimica, puntajesQuimica, contQuimi, cupos[0], nombre, examenAdmision);
                    break;
                case "Fisiorehabilitacion":
                    admitidosFisi[contFisi++] = nombre;
                    break;
                case "Medicina":
                    puntajeTotal = calcularPuntajeTotal(teclado, puntajeTotal);
                    contMedi = registrarAdmitido(admitidosMedicina, puntajesMedicina, puntajesTotalesMedicina, contMedi, cupos[2], nombre, examenAdmision, puntajeTotal);
                    break;
            }
        }
        mostrarResutados("Quimica", admitidosQuimica, puntajesQuimica, contQuimi);
        mostrarResutados("Fisiorehabilitacion", admitidosFisi, null, contFisi);
        mostrarResutados("Medicina", admitidosMedicina, puntajesMedicina, contMedi);
    }

    public static boolean verificarFechaLimite(LocalDate fechaLimite) {
        return !LocalDate.now().isAfter(fechaLimite);
    }

    public static int obtenerPuntaje(Scanner teclado) {
        int puntaje;
        do {
            System.out.print("Ingrese el puntaje de se examen de admision: ");
            puntaje = teclado.nextInt();
            if (puntaje < 0 || puntaje > 100) {
                System.out.println("Numero invalido. Intente nuevamente");
            }
        } while (puntaje < 0 || puntaje > 100);
        return puntaje;
    }

    public static int opcionCarrera(Scanner teclado, String carreras[], int opcionCarrera) {
        System.out.println("\nOpciones de carreras");
        for (int i = 0; i < carreras.length; i++) {
            System.out.println((i + 1) + "." + carreras[i]);
        }
        System.out.println("Seleccione el numero de carrera que desea estudiar: ");
        opcionCarrera = teclado.nextInt() - 1;
        if (opcionCarrera < 0 || opcionCarrera >= carreras.length) {
            System.out.println("Opcion no valida");
            return -1;
        }
        return opcionCarrera;
    }

    public static int calcularPuntajeTotal(Scanner teclado, int puntajeBase) {
        int puntajeAdicional = 0;
        System.out.println("¿Es abanderado del bachillerato (Si/No): ");
        if (teclado.next().equalsIgnoreCase("Si")) {
            puntajeAdicional += 5;
        }
        System.out.println("¿Su bachillerato es a fin a medicina? (Si/No): ");
        if (teclado.next().equalsIgnoreCase("Si")) {
            puntajeAdicional += 2;
        }
        System.out.println("¿Tiene una capacidad especial menor al 35%? (Si/No): ");
        if (teclado.next().equalsIgnoreCase("Si")) {
            puntajeAdicional += 1;
        }
        return puntajeBase + puntajeAdicional;
    }

    public static int registrarAdmitido(String admitidos[], int puntajes[], int contador, int cupoMax, String nombre, int puntaje) {
        if (contador < cupoMax) {
            admitidos[contador] = nombre;
            puntajes[contador] = puntaje;
            return contador + 1;
        } else {
            System.out.println("No hay mas cupos disponibles");
        }
        return contador;
    }

    public static int registrarAdmitido(String[] admitidos, int[] puntajes, int[] puntajesTotales, int contador, int cupoMax, String nombre, int puntaje, int puntajeTotal) {
        if (contador < cupoMax) {
            admitidos[contador] = nombre;
            puntajes[contador] = puntaje;
            puntajesTotales[contador] = puntajeTotal;
            return contador + 1;
        } else {
            System.out.println("No hay más cupos disponibles para Medicina.");
            return contador;
        }
    }
    public static void mostrarResutados(String carrera, String admitidos[], int puntajes[], int contador){
        System.out.println("\nAdmitidos en " + carrera);
        for (int i = 0; i < contador; i++) {
            if (puntajes != null){
            System.out.println(admitidos[i] + " con un puntaje de " + puntajes[i]);
            }else
                System.out.println(admitidos[i]);
        }
    }
}
