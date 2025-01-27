from datetime import date
def main():
    print("Bienvenido al sistema de gestión de admisiones UTPL:")
    carreras = ["Quimica", "Fisiorehabilitacion", "Medicina"]
    puntajes_minimos = [80, 90, 85]
    cupos = [80, float('inf'), 80]
    fecha_limite = date(2025, 1, 28,)
    admitidos_quimica = []
    puntajes_quimica = []
    admitidos_fisi = []
    admitidos_medicina = []
    puntajes_medicina = []
    puntajes_totales_medicina = []
    cont_quimi = 0
    cont_fisi = 0
    cont_medi = 0
    while True:
        nombre = input("Ingrese su nombre (o 'salir' para finalizar): ").strip()
        if nombre.lower() == 'salir':
            break
        if not verificar_fecha_limite(fecha_limite):
            print("El período de inscripción ha finalizado.")
            continue
        examen_admision = obtener_puntaje()
        opcion_carrera = seleccionar_carrera(carreras)
        
        if opcion_carrera == -1:
            continue
        carrera_seleccionada = carreras[opcion_carrera]
        puntaje_minimo = puntajes_minimos[opcion_carrera]
        if examen_admision < puntaje_minimo:
            print(f"No cumple con el puntaje mínimo para la carrera {carrera_seleccionada}.")
            continue
        if carrera_seleccionada == "Quimica":
            cont_quimi = registrar_admitido(admitidos_quimica, puntajes_quimica, cont_quimi, cupos[0], nombre, examen_admision)
        elif carrera_seleccionada == "Fisiorehabilitacion":
            admitidos_fisi.append(nombre)
            cont_fisi += 1
        elif carrera_seleccionada == "Medicina":
            puntaje_total = calcular_puntaje_total(examen_admision)
            cont_medi = registrar_admitido(admitidos_medicina, puntajes_medicina, puntajes_totales_medicina, cont_medi, cupos[2], nombre, examen_admision, puntaje_total)
    mostrar_resultados("Quimica", admitidos_quimica, puntajes_quimica)
    mostrar_resultados("Fisiorehabilitacion", admitidos_fisi, [])
    mostrar_resultados("Medicina", admitidos_medicina, puntajes_totales_medicina)
def verificar_fecha_limite(fecha_limite):
    return date.today() <= fecha_limite
def obtener_puntaje():
    while True:
        try:
            puntaje = int(input("Ingrese el puntaje de su examen de admisión: "))
            if 0 <= puntaje <= 100:
                return puntaje
            else:
                print("Número inválido. Intente nuevamente.")
        except ValueError:
            print("Entrada no válida. Intente nuevamente.")
def seleccionar_carrera(carreras):
    print("\nOpciones de carreras:")
    for i, carrera in enumerate(carreras, 1):
        print(f"{i}. {carrera}")
    try:
        opcion = int(input("Seleccione el número de carrera que desea estudiar: ")) - 1
        if 0 <= opcion < len(carreras):
            return opcion
        else:
            print("Opción no válida.")
            return -1
    except ValueError:
        print("Entrada no válida.")
        return -1
def calcular_puntaje_total(puntaje_base):
    puntaje_adicional = 0
    if input("¿Es abanderado del bachillerato? (si/no): ").strip().lower() == "si":
        puntaje_adicional += 5
    if input("¿Su bachillerato es afín a Medicina? (si/no): ").strip().lower() == "si":
        puntaje_adicional += 2
    if input("¿Tiene una capacidad especial menor al 35%? (si/no): ").strip().lower() == "si":
        puntaje_adicional += 1
    return puntaje_base + puntaje_adicional

def registrar_admitido(admitidos, puntajes, contador, cupo_max, nombre, puntaje):
    if contador < cupo_max:
        admitidos.append(nombre)
        puntajes.append(puntaje)
        return contador + 1
    else:
        print("No hay más cupos disponibles.")
        return contador
def registrar_admitido(admitidos, puntajes, puntajes_totales, contador, cupo_max, nombre, puntaje, puntaje_total):
    if contador < cupo_max:
        admitidos.append(nombre)
        puntajes.append(puntaje)
        puntajes_totales.append(puntaje_total)
        return contador + 1
    else:
        print("No hay más cupos disponibles para Medicina.")
        return contador
def mostrar_resultados(carrera, admitidos, puntajes):
    print(f"\nAdmitidos en {carrera}:")
    for i, nombre in enumerate(admitidos):
        if puntajes:
            print(f"{nombre} con un puntaje de {puntajes[i]}")
        else:
            print(nombre)
if __name__ == "__main__":
    main()

