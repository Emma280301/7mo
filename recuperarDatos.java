package application;

public class recuperarDatos {

	private String id, temperatura, ambiente, suelo, fecha, hora;

	public recuperarDatos(String id, String temperatura, String ambiente, String suelo, String fecha, String hora) {
		this.id = id;
		this.temperatura = temperatura;
		this.ambiente = ambiente;
		this.suelo = suelo;
		this.fecha = fecha;
		this.hora = hora;
	}

	public String getId() {
		return id;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public String getSuelo() {
		return suelo;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	
}
