package futbol;

import java.time.LocalDate;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		String[] mainMenu = {"Campeonato", "Partido amistoso", "Agregar equipo", "Eliminar equipo", "Agregar jugador", 
				"Eliminar jugador", "Salir"};
		Liga liga = Liga.crearRegular("Agrentina jóvenes", 10);
		Sportbook sportbook = new Sportbook("Betano");
		Partido partido;
		Apuesta apuesta;
		boolean salir = false;
		do {
			int acion=JOptionPane.showOptionDialog(null, "Por favor, elegí una opción", "Bienvenida a liga \"" 
					+ liga.getNombre()+"\"", 0, JOptionPane.QUESTION_MESSAGE, null, mainMenu, mainMenu[0]);
			String[] nombresEquipos=liga.toArrayString();	
			String eligido;
			switch (acion) {
			case 0:
				Campeonato campeonato = new Campeonato("Argentina", LocalDate.now().plusDays(10), liga.getEquipos());
				campeonato.ordenarAlAzar();
				do {
			      partido = campeonato.getSiguiente();
			      if (partido != null) {
				      campeonato.mostrarJuegos();
				      apuesta = new Apuesta(partido);
				      apuesta.apostar();
					  campeonato.jugarSiguiente();
					  apuesta.verificar();
					  liga.agregarPartido(partido);
					  sportbook.agregarApuesta(apuesta);
				  }
				} while (partido != null);
				break;
			case 1:
				String primero = (String)JOptionPane.showInputDialog(null, "Elegí el primer equipo",
						"Equipos para jugar un partido", 1, null, nombresEquipos, nombresEquipos[0]);
				nombresEquipos=eliminarDeArray(nombresEquipos, primero);
				String segundo = (String)JOptionPane.showInputDialog(null, "Elegí el segundo equipo", 
						"Un equipo rival para " + primero, 1, null, nombresEquipos, nombresEquipos[0]);
				partido = new Partido(liga.encontrarEquipo(primero), liga.encontrarEquipo(segundo), 
						LocalDate.now().plusDays(liga.getNumeroPartidos()));
				apuesta = new Apuesta(partido);
				apuesta.apostar();
				partido.jugar(true);
				apuesta.verificar();
				liga.agregarPartido(partido);
				sportbook.agregarApuesta(apuesta);
				break;
			case 2:
				JOptionPane.showMessageDialog(null, liga.mostrarEquipos() + "\nVamos a agregar una más.");
				liga.agregarEquipoManualmente();
				JOptionPane.showMessageDialog(null, liga.mostrarEquipos());
				break;
			case 3:
				eligido =(String)JOptionPane.showInputDialog(null, liga.mostrarEquipos() 
						+ "Elegí el un equipo para eliminar", "Equipo para eliminar", 1, null, nombresEquipos, nombresEquipos[0]);
				liga.eliminarEquipo(eligido);
				JOptionPane.showMessageDialog(null, liga.mostrarEquipos());
				break;
			case 4:
				eligido = (String)JOptionPane.showInputDialog(null, liga.mostrarEquipos()
						+ "Elegí un equipo para agregar un jugador",
						"Equipo para agregar un jugador", 1, null, nombresEquipos, nombresEquipos[0]);
                liga.encontrarEquipo(eligido).agregarJugadorManualmente();
                JOptionPane.showMessageDialog(null, liga.encontrarEquipo(eligido).mostrarJugadores());
				break;
			case 5:
				eligido = (String)JOptionPane.showInputDialog(null, liga.mostrarEquipos()
						+ "Elegí un equipo para eliminar un jugador ",
						"Equipo para eliminar un jugador", 1, null, nombresEquipos, nombresEquipos[0]);
                liga.encontrarEquipo(eligido).eliminarJugadorManualmente();
                JOptionPane.showMessageDialog(null, liga.encontrarEquipo(eligido).mostrarJugadores());
				break;
			case 6:
				salir = true;
				break;
			}	
		} while (! salir);
		JOptionPane.showMessageDialog(null,liga.mostrarResultados(),"Informe final de juegos", 1);
		JOptionPane.showMessageDialog(null,sportbook.mostrarInforme()+"\n\n¡Chao! ¡Nos vemos!","Informe final de apuestos", 1);
	}
	
	private static String[] eliminarDeArray(String[] todos, String excesivo) {
		if (todos.length<2) {
			return null;
		}
		String[] sinExcesivo = new String[todos.length-1];
		int salto = 0 ;
		for (int i=0; i<sinExcesivo.length; i++) {
			if (todos[i].equals(excesivo)) {
				salto=1;
			}
			sinExcesivo[i]=todos[i+salto];
		}
		return sinExcesivo;
	}
}
