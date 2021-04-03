package domain;

import java.util.Vector;
import javax.persistence.*;

@Entity
public class Deporte {

	
	@Id
	@GeneratedValue
	private int id;
	private String nombre;
	private boolean activo;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Event> eventos=new Vector<Event>();
	
	
	public Deporte(String pNombre) {
		this.nombre=pNombre;
		this.activo=true;
		this.eventos=new Vector<Event>();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public boolean getActivo() {
		return activo;
	}


	public void setActivo(boolean pactivo) {
		this.activo = pactivo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Vector<Event> getEventos() {
		return eventos;
	}

	
	public void setEventos(Vector<Event> eventos) {
		this.eventos = eventos;
	}
	
	
	public void addEvent(Event pEvento)  {
			this.eventos.add(pEvento);
        
	}
	
	
	public boolean removeEvent(Event pEvento) {
		try {
			this.eventos.remove(pEvento);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	

	public String toString(){
		return nombre+"";
	}
	
}
